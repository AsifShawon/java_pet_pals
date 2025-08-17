package app;

//Notification.java
import java.io.Serializable;
import java.time.LocalDateTime;

public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String userId; // Who should receive this notification
    private String type; // NEW_REQUEST, NEW_MESSAGE, REQUEST_ACCEPTED, REQUEST_REJECTED
    private String title;
    private String message;
    private String relatedId; // ID of related request, post, or message
    private LocalDateTime timestamp;
    private boolean isRead;
    
    // Constructors
    public Notification() {}
    
    public Notification(String id, String userId, String type, String title, String message, String relatedId) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.title = title;
        this.message = message;
        this.relatedId = relatedId;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getRelatedId() { return relatedId; }
    public void setRelatedId(String relatedId) { this.relatedId = relatedId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    
    @Override
    public String toString() {
        return "Notification{id='" + id + "', userId='" + userId + "', type='" + type + 
               "', title='" + title + "', timestamp=" + timestamp + ", isRead=" + isRead + "}";
    }
}
