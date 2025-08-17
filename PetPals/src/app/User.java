package app;

//User.java
import java.io.Serializable;

public class User implements Serializable {
 private static final long serialVersionUID = 1L;
 
 private String id;
 private String username;
 private String passwordHash;
 private String role; // ADMIN or USER
 private String contact;
 
 // Constructors
 public User() {}
 
 public User(String id, String username, String passwordHash, String role, String contact) {
     this.id = id;
     this.username = username;
     this.passwordHash = passwordHash;
     this.role = role;
     this.contact = contact;
 }
 
 // Getters and Setters
 public String getId() { return id; }
 public void setId(String id) { this.id = id; }
 
 public String getUsername() { return username; }
 public void setUsername(String username) { this.username = username; }
 
 public String getPasswordHash() { return passwordHash; }
 public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
 
 public String getRole() { return role; }
 public void setRole(String role) { this.role = role; }
 
 public String getContact() { return contact; }
 public void setContact(String contact) { this.contact = contact; }
 
 @Override
 public String toString() {
     return "User{id='" + id + "', username='" + username + "', role='" + role + "'}";
 }
}
