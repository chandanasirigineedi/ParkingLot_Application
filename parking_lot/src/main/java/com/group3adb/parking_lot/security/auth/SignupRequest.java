package com.group3adb.parking_lot.security.auth;

import java.util.List;
import com.group3adb.parking_lot.model.Authority;

import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private List<String> authorities;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String geocode;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<String> getAuthorities() {
      return this.authorities;
    }
    
    public void setAuthorities(List<String>  authorities) {
      this.authorities= authorities;
    }

    public String getGeocode() {
        return geocode;
    }
 
    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }
}