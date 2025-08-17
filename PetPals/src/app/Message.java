package app;

//Message.java
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
 private static final long serialVersionUID = 1L;
 
 private String id;
 private String requestId;
 private String senderId;
 private String content;
 private LocalDateTime timestamp;
 
 // Constructors
 public Message() {}
 
 public Message(String id, String requestId, String senderId, String content) {
     this.id = id;
     this.requestId = requestId;
     this.senderId = senderId;
     this.content = content;
     this.timestamp = LocalDateTime.now();
 }
 
 // Getters and Setters
 public String getId() { return id; }
 public void setId(String id) { this.id = id; }
 
 public String getRequestId() { return requestId; }
 public void setRequestId(String requestId) { this.requestId = requestId; }
 
 public String getSenderId() { return senderId; }
 public void setSenderId(String senderId) { this.senderId = senderId; }
 
 public String getContent() { return content; }
 public void setContent(String content) { this.content = content; }
 
 public LocalDateTime getTimestamp() { return timestamp; }
 public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
 
 @Override
 public String toString() {
     return "Message{id='" + id + "', requestId='" + requestId + "', senderId='" + senderId + 
            "', content='" + content + "', timestamp=" + timestamp + "}";
 }
}
