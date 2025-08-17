package app;

//Post.java
import java.io.Serializable;
import java.time.LocalDateTime;

public class Post implements Serializable {
 private static final long serialVersionUID = 1L;
 
 private String id;
 private String authorId;
 // Use enum for strong typing
 private PostType type; // DONATE or NEED
 private String title;
 private String description;
 private LocalDateTime timestamp;
 // Added optional image path for social-media style posts
 private String imagePath; // relative or absolute path to image (may be null)
 
 // Constructors
 public Post() {}
 
 public Post(String id, String authorId, PostType type, String title, String description) {
     this(id, authorId, type, title, description, null);
 }
 
 public Post(String id, String authorId, PostType type, String title, String description, String imagePath) {
     this.id = id;
     this.authorId = authorId;
     this.type = type;
     this.title = title;
     this.description = description;
     this.imagePath = imagePath;
     this.timestamp = LocalDateTime.now();
 }
 
 // Backwards-compatible constructor accepting String type
 public Post(String id, String authorId, String typeStr, String title, String description, String imagePath) {
     this(id, authorId, PostType.valueOf(typeStr.toUpperCase()), title, description, imagePath);
 }
 
 // Getters and Setters
 public String getId() { return id; }
 public void setId(String id) { this.id = id; }
 
 public String getAuthorId() { return authorId; }
 public void setAuthorId(String authorId) { this.authorId = authorId; }
 
 public PostType getType() { return type; }
 public void setType(PostType type) { this.type = type; }
 
 public String getTitle() { return title; }
 public void setTitle(String title) { this.title = title; }
 
 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }
 
 public LocalDateTime getTimestamp() { return timestamp; }
 public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
 
 public String getImagePath() { return imagePath; }
 public void setImagePath(String imagePath) { this.imagePath = imagePath; }
 
 @Override
 public String toString() {
     return "Post{id='" + id + "', authorId='" + authorId + "', type='" + (type != null ? type.name() : "") + "', title='" + title + "'" +
            (imagePath != null ? ", imagePath='" + imagePath + "'" : "") + "}";
 }
}
