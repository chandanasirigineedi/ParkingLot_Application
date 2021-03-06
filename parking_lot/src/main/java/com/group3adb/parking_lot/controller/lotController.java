package com.group3adb.parking_lot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.group3adb.parking_lot.exception.LotExists;
import com.group3adb.parking_lot.exception.ResourceNotFoundException;
import com.group3adb.parking_lot.model.ParkingLot;
import com.group3adb.parking_lot.repository.ParkingLotRepository;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController 
@RequestMapping(value="/api" , produces = MediaType.APPLICATION_JSON_VALUE )
public class lotController {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping("/lots")
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAll();
    }

    @GetMapping("/lots/{id}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable(value = "id") String ParkingLotId)
        throws ResourceNotFoundException {
        ParkingLot parkingLot = parkingLotRepository.findById(ParkingLotId)
          .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found for this id :: " + ParkingLotId));
        return ResponseEntity.ok().body(parkingLot);
    }
    
    
    @PostMapping("/lots")
    @PreAuthorize("hasRole('ADMIN')")
    public ParkingLot createParkingLot(@Valid @RequestBody ParkingLot parkingLot) throws LotExists {
        
        if (parkingLotRepository.findByGeocode(parkingLot.getGeocode())==null){
            return parkingLotRepository.save(parkingLot);
        }
        else{
            
                throw new LotExists("Lot exists");
            
        }
    }
    
    @PutMapping("/lots/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingLot> updateParkingLot(@PathVariable(value = "id") String ParkingLotId,
         @Valid @RequestBody ParkingLot parkingLotDetails) throws ResourceNotFoundException {
        ParkingLot parkingLot = parkingLotRepository.findById(ParkingLotId)
        .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found for this id :: " + ParkingLotId));

        // parkingLot.setAddress(parkingLotDetails.getAddress());
        parkingLot.setSlots(parkingLotDetails.getSlots());
        // parkingLot.setGeocode(parkingLotDetails.getGeocode());
        final ParkingLot updatedParkingLot = parkingLotRepository.save(parkingLot);
        return ResponseEntity.ok(updatedParkingLot);
    }

    @DeleteMapping("/lots/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> deleteParkingLot(@PathVariable(value = "id") String ParkingLotId)
         throws ResourceNotFoundException {
        ParkingLot parkingLot = parkingLotRepository.findById(ParkingLotId)
       .orElseThrow(() -> new ResourceNotFoundException("ParkingLot not found for this id :: " + ParkingLotId));

        parkingLotRepository.delete(parkingLot);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}