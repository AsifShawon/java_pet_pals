package app;

//Request.java
import java.io.Serializable;

public class Request implements Serializable {
 private static final long serialVersionUID = 1L;
 
 private String id;
 private String fromUserId;
 private String toUserId;
 private String postId;
 private String status; // PENDING, ACCEPTED, REJECTED
 
 // Constructors
 public Request() {}
 
 public Request(String id, String fromUserId, String toUserId, String postId) {
     this.id = id;
     this.fromUserId = fromUserId;
     this.toUserId = toUserId;
     this.postId = postId;
     this.status = "PENDING";
 }
 
 // Getters and Setters
 public String getId() { return id; }
 public void setId(String id) { this.id = id; }
 
 public String getFromUserId() { return fromUserId; }
 public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }
 
 public String getToUserId() { return toUserId; }
 public void setToUserId(String toUserId) { this.toUserId = toUserId; }
 
 public String getPostId() { return postId; }
 public void setPostId(String postId) { this.postId = postId; }
 
 public String getStatus() { return status; }
 public void setStatus(String status) { this.status = status; }
 
 @Override
 public String toString() {
     return "Request{id='" + id + "', fromUserId='" + fromUserId + "', toUserId='" + toUserId + 
            "', postId='" + postId + "', status='" + status + "'}";
 }
}
