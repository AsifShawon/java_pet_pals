package app;

//AppController.java
import java.util.*;
import java.util.stream.Collectors;

public class AppController {
 private DataStore dataStore;
 private User currentUser;
 
 public AppController() {
     this.dataStore = DataStore.getInstance();
 }
 
 // Authentication methods
 public boolean login(String username, String password) {
     User user = dataStore.findUserByUsername(username);
     // Return false immediately if user not found
     if (user == null) {
         return false;
     }
     // Verify password for all users (including admin)
     if (PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
         this.currentUser = user;
         return true;
     }
     return false;
 }
 
 public boolean signup(String username, String password, String contact) {
     // Check if username already exists
     if (dataStore.findUserByUsername(username) != null) {
         return false;
     }
     
     // Create new user with USER role
     String id = UUID.randomUUID().toString();
     String hashedPassword = PasswordUtil.hashPassword(password);
     User newUser = new User(id, username, hashedPassword, "USER", contact);
     dataStore.addUser(newUser);
     return true;
 }
 
 public void logout() {
     this.currentUser = null;
 }
 
 // Getters
 public User getCurrentUser() {
     return currentUser;
 }
 
 // Post methods
 public Post createPost(String type, String title, String description) {
     return createPost(type, title, description, null);
 }
 
 public Post createPost(String type, String title, String description, String imagePath) {
     if (currentUser == null) return null;
     
     String id = UUID.randomUUID().toString();
     Post post = new Post(id, currentUser.getId(), type, title, description, imagePath);
     dataStore.addPost(post);
     return post;
 }
 
 public Collection<Post> getAllPosts() {
     return dataStore.getAllPosts();
 }
 
 public Collection<Post> getPostsByType(String type) {
     return dataStore.findPostsByType(type);
 }
 
 public Collection<Post> searchPosts(String query) {
     return dataStore.getAllPosts().stream()
             .filter(post -> post.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            post.getDescription().toLowerCase().contains(query.toLowerCase()))
             .collect(Collectors.toList());
 }
 
 // Request methods
 public Request createRequest(String postId) {
     if (currentUser == null) return null;
     
     Post post = dataStore.findPost(postId);
     if (post == null) return null;
     
     // Don't allow users to request their own posts
     if (post.getAuthorId().equals(currentUser.getId())) {
         return null;
     }
     
     String id = UUID.randomUUID().toString();
     Request request = new Request(id, currentUser.getId(), post.getAuthorId(), postId);

     // Behavior:
     // - If the post is a NEED (someone needs a pet), a user creating a request is offering to help/donate -> auto-accept the request.
     // - If the post is a DONATE (someone is giving away a pet), requests remain PENDING so the poster and requester can message and negotiate; poster can accept/deny later.
     if (post.getType() != null && post.getType().toString().equalsIgnoreCase("NEED")) {
         request.setStatus("ACCEPTED");
         dataStore.addRequest(request);
         // Add a simple system message recording auto-accept
         String msgId = UUID.randomUUID().toString();
         Message sysMsg = new Message(msgId, request.getId(), "SYSTEM", "This offer was auto-accepted.");
         dataStore.addMessage(sysMsg);
         
         // Create notification for post owner (auto-accepted)
         createNotification(post.getAuthorId(), "REQUEST_ACCEPTED", 
                          "Request Auto-Accepted", 
                          "Your request for '" + post.getTitle() + "' has been automatically accepted.", 
                          request.getId());
     } else {
         // Leave as PENDING (Request constructor sets PENDING by default)
         dataStore.addRequest(request);
         
         // Create notification for post owner (new request)
         createNotification(post.getAuthorId(), "NEW_REQUEST", 
                          "New Adoption Request", 
                          currentUser.getUsername() + " wants to adopt '" + post.getTitle() + "'", 
                          request.getId());
     }
     return request;
 }
 
 public Collection<Request> getCurrentUserRequests() {
     if (currentUser == null) return Collections.emptyList();
     return dataStore.findRequestsByUser(currentUser.getId());
 }
 
 public void updateRequestStatus(String requestId, String status) {
     Request request = dataStore.findRequest(requestId);
     if (request != null && 
         (currentUser.getId().equals(request.getToUserId()) || 
          currentUser.getRole().equals("ADMIN"))) {
         String oldStatus = request.getStatus();
         request.setStatus(status);
         dataStore.updateRequest(request);
         
         // Create notification for the requester if status changed
         if (!oldStatus.equals(status)) {
             Post post = dataStore.findPost(request.getPostId());
             String postTitle = post != null ? post.getTitle() : "a post";
             
             if ("ACCEPTED".equals(status)) {
                 createNotification(request.getFromUserId(), "REQUEST_ACCEPTED", 
                                  "Request Accepted!", 
                                  "Your request for '" + postTitle + "' has been accepted!", 
                                  requestId);
             } else if ("REJECTED".equals(status)) {
                 createNotification(request.getFromUserId(), "REQUEST_REJECTED", 
                                  "Request Rejected", 
                                  "Your request for '" + postTitle + "' has been rejected.", 
                                  requestId);
             }
         }
     }
 }
 
 // Message methods
 public Message sendMessage(String requestId, String content) {
     if (currentUser == null) return null;
     
     Request request = dataStore.findRequest(requestId);
     if (request == null) return null;
     
     // Check if user is part of the request
     if (!currentUser.getId().equals(request.getFromUserId()) && 
         !currentUser.getId().equals(request.getToUserId())) {
         return null;
     }
     
     String id = UUID.randomUUID().toString();
     Message message = new Message(id, requestId, currentUser.getId(), content);
     dataStore.addMessage(message);
     
     // Create notification for the other participant in the conversation
     String recipientId;
     if (currentUser.getId().equals(request.getFromUserId())) {
         recipientId = request.getToUserId();
     } else {
         recipientId = request.getFromUserId();
     }
     
     Post post = dataStore.findPost(request.getPostId());
     String postTitle = post != null ? post.getTitle() : "a post";
     
     createNotification(recipientId, "NEW_MESSAGE", 
                      "New Message", 
                      "You have a new message about '" + postTitle + "'", 
                      requestId);
     
     return message;
 }
 
 public Collection<Message> getMessagesForRequest(String requestId) {
     Request request = dataStore.findRequest(requestId);
     if (request == null) return Collections.emptyList();
     
     // Check if current user is part of the request
     if (!currentUser.getId().equals(request.getFromUserId()) && 
         !currentUser.getId().equals(request.getToUserId()) &&
         !currentUser.getRole().equals("ADMIN")) {
         return Collections.emptyList();
     }
     
     return dataStore.findMessagesByRequest(requestId);
 }
 
 // Admin methods
 public Collection<User> getAllUsers() {
     if (currentUser != null && currentUser.getRole().equals("ADMIN")) {
         return dataStore.getAllUsers();
     }
     return Collections.emptyList();
 }

 public Collection<Request> getAllRequests() {
     if (currentUser != null && currentUser.getRole().equals("ADMIN")) {
         return dataStore.getAllRequests();
     }
     return Collections.emptyList();
 }

 // Get requests for a post (if current user is the post owner)
 public Collection<Request> getRequestsForPost(String postId) {
     if (currentUser == null) return Collections.emptyList();
     
     Post post = dataStore.findPost(postId);
     if (post == null || !post.getAuthorId().equals(currentUser.getId())) {
         return Collections.emptyList();
     }
     
     return dataStore.findRequestsByPost(postId);
 }

 // Determine if a post should be treated as archived for the current user.
 // A DONATE post that already has an ACCEPTED request should be hidden from the "All Posts" view
 // for users who are neither the donor (post author) nor the adopter (accepted request.fromUser).
 public boolean isPostArchivedForCurrentUser(Post post) {
        if (post == null || post.getType() == null) return false;
        // Only donate posts are archived after acceptance
        if (!"DONATE".equalsIgnoreCase(post.getType().toString())) return false;

        Collection<Request> reqs = dataStore.findRequestsByPost(post.getId());
        for (Request r : reqs) {
            if ("ACCEPTED".equalsIgnoreCase(r.getStatus())) {
                // If no current user, treat as archived (hide from anonymous viewers)
                if (currentUser == null) return true;
                // Admins should see everything
                if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) return false;
                // Previously allowed donor/adopter to still see; change: hide accepted posts from all non-admin users
                return true;
            }
        }
        return false;
 }

 public void deleteUser(String userId) {
     if (currentUser != null && currentUser.getRole().equals("ADMIN")) {
         dataStore.deleteUser(userId);
     }
 }
 
 // Additional methods for user functionality
 public void deletePost(String postId) {
     Post post = dataStore.findPost(postId);
     if (post != null && 
         (currentUser.getId().equals(post.getAuthorId()) || 
          currentUser.getRole().equals("ADMIN"))) {
         dataStore.deletePost(postId);
     }
 }
 
 public void updateUser(User user) {
     if (currentUser != null && 
         (currentUser.getId().equals(user.getId()) || 
          currentUser.getRole().equals("ADMIN"))) {
         dataStore.updateUser(user);
     }
 }
 
 public User getUserById(String userId) {
     return dataStore.findUser(userId);
 }
 
 public Post getPostById(String postId) {
     return dataStore.findPost(postId);
 }
 
 // Notification methods
 public void createNotification(String userId, String type, String title, String message, String relatedId) {
     String id = UUID.randomUUID().toString();
     Notification notification = new Notification(id, userId, type, title, message, relatedId);
     dataStore.addNotification(notification);
 }
 
 public Collection<Notification> getUserNotifications() {
     if (currentUser == null) return Collections.emptyList();
     return dataStore.findNotificationsByUser(currentUser.getId());
 }
 
 public Collection<Notification> getUnreadUserNotifications() {
     if (currentUser == null) return Collections.emptyList();
     return dataStore.findUnreadNotificationsByUser(currentUser.getId());
 }
 
 public void markNotificationAsRead(String notificationId) {
     dataStore.markNotificationAsRead(notificationId);
 }
 
 public int getUnreadNotificationCount() {
     if (currentUser == null) return 0;
     return (int) dataStore.findUnreadNotificationsByUser(currentUser.getId()).size();
 }
}