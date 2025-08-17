package app;

//DataStore.java
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DataStore implements Serializable {
 private static final long serialVersionUID = 1L;
 
 private static final String DATA_FILE = "pet_adoption_data.dat";
 
 // Using ConcurrentHashMap for thread safety
 private Map<String, User> users;
 private Map<String, Post> posts;
 private Map<String, Request> requests;
 private Map<String, Message> messages;
 private Map<String, Notification> notifications;
 
 // Singleton instance
 private static DataStore instance;
 
 private DataStore() {
     users = new ConcurrentHashMap<>();
     posts = new ConcurrentHashMap<>();
     requests = new ConcurrentHashMap<>();
     messages = new ConcurrentHashMap<>();
     notifications = new ConcurrentHashMap<>();
     loadAll();
 }
 
 public static synchronized DataStore getInstance() {
     if (instance == null) {
         instance = new DataStore();
     }
     return instance;
 }
 
 // Load all data from file
 public void loadAll() {
     try {
         if (Files.exists(Paths.get(DATA_FILE))) {
             try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                 DataStore loadedData = (DataStore) ois.readObject();
                 
                 this.users = loadedData.users != null ? loadedData.users : new ConcurrentHashMap<>();
                 this.posts = loadedData.posts != null ? loadedData.posts : new ConcurrentHashMap<>();
                 this.requests = loadedData.requests != null ? loadedData.requests : new ConcurrentHashMap<>();
                 this.messages = loadedData.messages != null ? loadedData.messages : new ConcurrentHashMap<>();
                 this.notifications = loadedData.notifications != null ? loadedData.notifications : new ConcurrentHashMap<>();
             }
         } else {
             // Initialize with default admin user if file doesn't exist
             initializeDefaultData();
         }
     } catch (Exception e) {
         System.err.println("Error loading data (possible file format change or corruption): " + e.getMessage());
         // Backup existing data file if present
         try {
             if (Files.exists(Paths.get(DATA_FILE))) {
                 String backupName = DATA_FILE + ".bak." + System.currentTimeMillis();
                 Files.move(Paths.get(DATA_FILE), Paths.get(backupName), StandardCopyOption.REPLACE_EXISTING);
                 System.err.println("Backed up corrupt data file to: " + backupName);
             }
         } catch (Exception ex) {
             System.err.println("Failed to backup data file: " + ex.getMessage());
         }
         // Initialize empty collections and default data
         users = new ConcurrentHashMap<>();
         posts = new ConcurrentHashMap<>();
         requests = new ConcurrentHashMap<>();
         messages = new ConcurrentHashMap<>();
         initializeDefaultData();
     }
 }
 
 // Save all data to file
 public void saveAll() {
     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
         oos.writeObject(this);
     } catch (IOException e) {
         System.err.println("Error saving data: " + e.getMessage());
     }
 }
 
 // Initialize with default admin user
 private void initializeDefaultData() {
     // Create a default admin user (password is "admin" - properly hashed)
     String hashedPassword = PasswordUtil.hashPassword("admin");
     User admin = new User("1", "admin", hashedPassword, "ADMIN", "admin@example.com");
     users.put(admin.getId(), admin);
     saveAll();
 }
 
 // User CRUD operations
 public void addUser(User user) {
     users.put(user.getId(), user);
     saveAll();
 }
 
 public User findUser(String id) {
     return users.get(id);
 }
 
 public User findUserByUsername(String username) {
     return users.values().stream()
             .filter(user -> user.getUsername().equals(username))
             .findFirst()
             .orElse(null);
 }
 
 public Collection<User> getAllUsers() {
     return users.values();
 }
 
 public void updateUser(User user) {
     users.put(user.getId(), user);
     saveAll();
 }
 
 public void deleteUser(String id) {
     users.remove(id);
     saveAll();
 }
 
 // Post CRUD operations
 public void addPost(Post post) {
     posts.put(post.getId(), post);
     saveAll();
 }
 
 public Post findPost(String id) {
     return posts.get(id);
 }
 
 public Collection<Post> getAllPosts() {
     return posts.values();
 }
 
 public Collection<Post> findPostsByAuthor(String authorId) {
     return posts.values().stream()
             .filter(post -> post.getAuthorId().equals(authorId))
             .toList();
 }
 
 public Collection<Post> findPostsByType(String type) {
     return posts.values().stream()
             .filter(post -> post.getType().equals(type))
             .toList();
 }
 
 public void updatePost(Post post) {
     posts.put(post.getId(), post);
     saveAll();
 }
 
 public void deletePost(String id) {
     posts.remove(id);
     saveAll();
 }
 
 // Request CRUD operations
 public void addRequest(Request request) {
     requests.put(request.getId(), request);
     saveAll();
 }
 
 public Request findRequest(String id) {
     return requests.get(id);
 }
 
 public Collection<Request> getAllRequests() {
     return requests.values();
 }
 
 public Collection<Request> findRequestsByUser(String userId) {
     return requests.values().stream()
             .filter(request -> request.getFromUserId().equals(userId) || request.getToUserId().equals(userId))
             .toList();
 }
 
 public Collection<Request> findRequestsByPost(String postId) {
     return requests.values().stream()
             .filter(request -> request.getPostId().equals(postId))
             .toList();
 }
 
 public void updateRequest(Request request) {
     requests.put(request.getId(), request);
     saveAll();
 }
 
 public void deleteRequest(String id) {
     requests.remove(id);
     saveAll();
 }
 
 // Message CRUD operations
 public void addMessage(Message message) {
     messages.put(message.getId(), message);
     saveAll();
 }
 
 public Message findMessage(String id) {
     return messages.get(id);
 }
 
 public Collection<Message> getAllMessages() {
     return messages.values();
 }
 
 public Collection<Message> findMessagesByRequest(String requestId) {
     return messages.values().stream()
             .filter(message -> message.getRequestId().equals(requestId))
             .sorted((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()))
             .toList();
 }
 
 public void updateMessage(Message message) {
     messages.put(message.getId(), message);
     saveAll();
 }
 
 public void deleteMessage(String id) {
     messages.remove(id);
     saveAll();
 }
 
 // Notification CRUD operations
 public void addNotification(Notification notification) {
     notifications.put(notification.getId(), notification);
     saveAll();
 }
 
 public Notification findNotification(String id) {
     return notifications.get(id);
 }
 
 public Collection<Notification> getAllNotifications() {
     return notifications.values();
 }
 
 public Collection<Notification> findNotificationsByUser(String userId) {
     return notifications.values().stream()
             .filter(notification -> notification.getUserId().equals(userId))
             .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()))
             .toList();
 }
 
 public Collection<Notification> findUnreadNotificationsByUser(String userId) {
     return notifications.values().stream()
             .filter(notification -> notification.getUserId().equals(userId) && !notification.isRead())
             .sorted((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()))
             .toList();
 }
 
 public void updateNotification(Notification notification) {
     notifications.put(notification.getId(), notification);
     saveAll();
 }
 
 public void deleteNotification(String id) {
     notifications.remove(id);
     saveAll();
 }
 
 public void markNotificationAsRead(String id) {
     Notification notification = notifications.get(id);
     if (notification != null) {
         notification.setRead(true);
         updateNotification(notification);
     }
 }
}
