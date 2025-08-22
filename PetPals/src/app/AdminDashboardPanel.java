package app;

//AdminDashboardPanel.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class AdminDashboardPanel extends JPanel {
 private MainFrame mainFrame;
 private AppController appController;
 
 private JLabel welcomeLabel;
 private JButton logoutButton;
 private JTabbedPane tabbedPane;
 
 // Users tab components
 private JTable usersTable;
 private DefaultTableModel usersTableModel;
 private JButton deleteUserButton;
 private JButton refreshUsersButton;
 
 // Posts tab components
 private JTable postsTable;
 private DefaultTableModel postsTableModel;
 private JButton deletePostButton;
 private JButton refreshPostsButton;
 
 // Requests tab components
 private JTable requestsTable;
 private DefaultTableModel requestsTableModel;
 private JButton refreshRequestsButton;
 
 // Statistics components
 private JLabel totalUsersLabel;
 private JLabel totalPostsLabel;
 private JLabel totalRequestsLabel;
 
 public AdminDashboardPanel(MainFrame mainFrame, AppController appController) {
     this.mainFrame = mainFrame;
     this.appController = appController;
     
     initializeComponents();
     // WindowBuilder-friendly initializer
     initComponents();
     setupEventHandlers();
     styleComponents();
     loadData();
 }
 
 // WindowBuilder-friendly entry point so Eclipse WindowBuilder can find UI creation
 private void initComponents() {
     setLayout(new BorderLayout());
     UIStyleHelper.stylePanel(this);
     
     // Top panel with welcome message and logout button
     JPanel topPanel = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(topPanel);
     topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

     JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
     leftTopPanel.setBackground(UIStyleHelper.CARD_COLOR);
     leftTopPanel.add(welcomeLabel);

     JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     rightTopPanel.setBackground(UIStyleHelper.CARD_COLOR);
     rightTopPanel.add(logoutButton);

     topPanel.add(leftTopPanel, BorderLayout.WEST);
     topPanel.add(rightTopPanel, BorderLayout.EAST);
     
     add(topPanel, BorderLayout.NORTH);
     
     // Main content with padding
     JPanel contentPanel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(contentPanel);
     contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

     // Create clean tabbed pane with helper methods
     tabbedPane = new JTabbedPane();
     tabbedPane.addTab("Statistics", IconHelper.createStatisticsIcon(), createStatisticsPanel(), "View system statistics");
     tabbedPane.addTab("Users", IconHelper.createUsersIcon(), createUsersPanel(), "Manage users");
     tabbedPane.addTab("Posts", IconHelper.createPostsIcon(), createPostsPanel(), "Manage posts");
     tabbedPane.addTab("Requests", IconHelper.createRequestsIcon(), createRequestsPanel(), "View requests");

     UIStyleHelper.styleTabbedPane(tabbedPane);
     contentPanel.add(tabbedPane, BorderLayout.CENTER);
     
     add(contentPanel, BorderLayout.CENTER);
 }
 
 private void initializeComponents() {
     User currentUser = appController.getCurrentUser();
     welcomeLabel = new JLabel("Welcome, Administrator: " + (currentUser != null ? currentUser.getUsername() : "Unknown"));
     welcomeLabel.setFont(UIStyleHelper.getHeaderFont());
     logoutButton = new JButton("Logout", IconHelper.createLogoutIcon());
     
     tabbedPane = new JTabbedPane();
     
     // Initialize table models
     usersTableModel = new DefaultTableModel(new String[]{"ID", "Username", "Role", "Contact"}, 0) {
         @Override
         public boolean isCellEditable(int row, int column) {
             return false;
         }
     };
     usersTable = new JTable(usersTableModel);
     
     postsTableModel = new DefaultTableModel(new String[]{"ID", "Author", "Type", "Title", "Description"}, 0) {
         @Override
         public boolean isCellEditable(int row, int column) {
             return false;
         }
     };
     postsTable = new JTable(postsTableModel);
     
     requestsTableModel = new DefaultTableModel(new String[]{"ID", "From User", "To User", "Post ID", "Status"}, 0) {
         @Override
         public boolean isCellEditable(int row, int column) {
             return false;
         }
     };
     requestsTable = new JTable(requestsTableModel);
     
     // Buttons
     deleteUserButton = new JButton("Delete Selected User", IconHelper.createDeleteIcon());
     refreshUsersButton = new JButton("Refresh Users", IconHelper.createRefreshIcon());
     deletePostButton = new JButton("Delete Selected Post", IconHelper.createDeleteIcon());
     refreshPostsButton = new JButton("Refresh Posts", IconHelper.createRefreshIcon());
     refreshRequestsButton = new JButton("Refresh Requests", IconHelper.createRefreshIcon());
     
     // Statistics labels
     totalUsersLabel = new JLabel("Total Users: 0");
     totalPostsLabel = new JLabel("Total Posts: 0");
     totalRequestsLabel = new JLabel("Total Requests: 0");
 }
 
 private JPanel createStatisticsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Welcome header
     JPanel headerPanel = new JPanel();
     UIStyleHelper.stylePanel(headerPanel);
     headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
     JLabel welcomeMsg = new JLabel("📊 Dashboard Overview");
     UIStyleHelper.styleLabel(welcomeMsg, UIStyleHelper.LabelStyle.TITLE);
     welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
     headerPanel.add(welcomeMsg);
     panel.add(headerPanel, BorderLayout.NORTH);
     
     // Stats cards container with better spacing
     JPanel statsContainer = new JPanel(new GridLayout(1, 3, 30, 30));
     UIStyleHelper.stylePanel(statsContainer);
     statsContainer.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
     
     // Create enhanced stat cards
     JPanel usersCard = createEnhancedStatCard("👥", "Users", totalUsersLabel, UIStyleHelper.PRIMARY_COLOR);
     JPanel postsCard = createEnhancedStatCard("📝", "Posts", totalPostsLabel, UIStyleHelper.SUCCESS_COLOR);
     JPanel requestsCard = createEnhancedStatCard("📋", "Requests", totalRequestsLabel, UIStyleHelper.WARNING_COLOR);
     
     statsContainer.add(usersCard);
     statsContainer.add(postsCard);
     statsContainer.add(requestsCard);
     
     panel.add(statsContainer, BorderLayout.CENTER);
     
     return panel;
 }
 
 private JPanel createEnhancedStatCard(String icon, String title, JLabel valueLabel, Color accentColor) {
     JPanel card = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(card);
     card.setPreferredSize(new Dimension(250, 150));
     card.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createLineBorder(accentColor.brighter(), 2),
         BorderFactory.createEmptyBorder(20, 20, 20, 20)
     ));
     
     // Icon and title
     JPanel topSection = new JPanel(new BorderLayout());
     topSection.setOpaque(false);
     
     JLabel iconLabel = new JLabel(icon);
     iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
     iconLabel.setForeground(accentColor);
     iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
     
     JLabel titleLabel = new JLabel(title);
     UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.HEADER);
     titleLabel.setForeground(accentColor);
     titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
     
     topSection.add(iconLabel, BorderLayout.NORTH);
     topSection.add(titleLabel, BorderLayout.CENTER);
     
     // Value
     valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
     valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
     valueLabel.setForeground(UIStyleHelper.TEXT_PRIMARY);
     
     card.add(topSection, BorderLayout.CENTER);
     card.add(valueLabel, BorderLayout.SOUTH);
     
     return card;
 }
 
 private JPanel createUsersPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Enhanced title section
     JPanel titlePanel = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(titlePanel);
     titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
     
     JLabel title = new JLabel("👥 User Management");
     UIStyleHelper.styleLabel(title, UIStyleHelper.LabelStyle.HEADER);
     titlePanel.add(title, BorderLayout.WEST);
     
     // Quick stats in title area
     JLabel userCount = new JLabel("(" + appController.getAllUsers().size() + " users)");
     UIStyleHelper.styleLabel(userCount, UIStyleHelper.LabelStyle.SECONDARY);
     titlePanel.add(userCount, BorderLayout.EAST);
     
     panel.add(titlePanel, BorderLayout.NORTH);
     
     // Table with enhanced styling
     JScrollPane scrollPane = new JScrollPane(usersTable);
     scrollPane.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createEmptyBorder(10, 20, 10, 20),
         BorderFactory.createLineBorder(UIStyleHelper.BORDER_COLOR, 1)
     ));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     // Enhanced button panel
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
     buttonPanel.add(refreshUsersButton);
     buttonPanel.add(Box.createHorizontalStrut(10));
     buttonPanel.add(deleteUserButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private JPanel createPostsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Enhanced title section
     JPanel titlePanel = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(titlePanel);
     titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
     
     JLabel title = new JLabel("📝 Posts Management");
     UIStyleHelper.styleLabel(title, UIStyleHelper.LabelStyle.HEADER);
     titlePanel.add(title, BorderLayout.WEST);
     
     // Quick stats in title area
     JLabel postCount = new JLabel("(" + appController.getAllPosts().size() + " posts)");
     UIStyleHelper.styleLabel(postCount, UIStyleHelper.LabelStyle.SECONDARY);
     titlePanel.add(postCount, BorderLayout.EAST);
     
     panel.add(titlePanel, BorderLayout.NORTH);
     
     JScrollPane scrollPane = new JScrollPane(postsTable);
     scrollPane.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createEmptyBorder(10, 20, 10, 20),
         BorderFactory.createLineBorder(UIStyleHelper.BORDER_COLOR, 1)
     ));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
     buttonPanel.add(refreshPostsButton);
     buttonPanel.add(Box.createHorizontalStrut(10));
     buttonPanel.add(deletePostButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private JPanel createRequestsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Enhanced title section
     JPanel titlePanel = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(titlePanel);
     titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
     
     JLabel title = new JLabel("📋 Requests Management");
     UIStyleHelper.styleLabel(title, UIStyleHelper.LabelStyle.HEADER);
     titlePanel.add(title, BorderLayout.WEST);
     
     // Quick stats in title area
     JLabel requestCount = new JLabel("(" + appController.getAllRequests().size() + " requests)");
     UIStyleHelper.styleLabel(requestCount, UIStyleHelper.LabelStyle.SECONDARY);
     titlePanel.add(requestCount, BorderLayout.EAST);
     
     panel.add(titlePanel, BorderLayout.NORTH);
     
     JScrollPane scrollPane = new JScrollPane(requestsTable);
     scrollPane.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createEmptyBorder(10, 20, 10, 20),
         BorderFactory.createLineBorder(UIStyleHelper.BORDER_COLOR, 1)
     ));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));
     buttonPanel.add(refreshRequestsButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private void setupEventHandlers() {
     logoutButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             appController.logout();
             mainFrame.showLoginPanel();
             NotificationUtil.showNotification("You have been logged out", mainFrame);
         }
     });
     
     refreshUsersButton.addActionListener(e -> loadUsersData());
     refreshPostsButton.addActionListener(e -> loadPostsData());
     refreshRequestsButton.addActionListener(e -> loadRequestsData());
     
     deleteUserButton.addActionListener(e -> deleteSelectedUser());
     deletePostButton.addActionListener(e -> deleteSelectedPost());
 }
 
 private void styleComponents() {
     UIStyleHelper.styleButton(logoutButton);
     UIStyleHelper.styleDangerButton(deleteUserButton);
     UIStyleHelper.styleButton(refreshUsersButton);
     UIStyleHelper.styleDangerButton(deletePostButton);
     UIStyleHelper.styleButton(refreshPostsButton);
     UIStyleHelper.styleButton(refreshRequestsButton);
     
     UIStyleHelper.styleTable(usersTable);
     UIStyleHelper.styleTable(postsTable);
     UIStyleHelper.styleTable(requestsTable);
     
     totalUsersLabel.setFont(UIStyleHelper.getLargeFont());
     totalPostsLabel.setFont(UIStyleHelper.getLargeFont());
     totalRequestsLabel.setFont(UIStyleHelper.getLargeFont());
 }
 
 private void loadData() {
     loadUsersData();
     loadPostsData();
     loadRequestsData();
     updateStatistics();
 }
 
 private void loadUsersData() {
     usersTableModel.setRowCount(0);
     Collection<User> users = appController.getAllUsers();
     for (User user : users) {
         usersTableModel.addRow(new Object[]{
             user.getId(),
             user.getUsername(),
             user.getRole(),
             user.getContact()
         });
     }
 }
 
 private void loadPostsData() {
     postsTableModel.setRowCount(0);
     Collection<Post> posts = appController.getAllPosts();
     for (Post post : posts) {
         User author = appController.getUserById(post.getAuthorId());
         String authorName = author != null ? author.getUsername() : "Unknown";
         
         postsTableModel.addRow(new Object[]{
             post.getId(),
             authorName,
             post.getType(),
             post.getTitle(),
             post.getDescription()
         });
     }
 }
 
 private void loadRequestsData() {
     requestsTableModel.setRowCount(0);
     Collection<Request> requests = appController.getAllRequests();
     for (Request request : requests) {
         requestsTableModel.addRow(new Object[]{
             request.getId(),
             request.getFromUserId(),
             request.getToUserId(),
             request.getPostId(),
             request.getStatus()
         });
     }
 }
 
 private void updateStatistics() {
     totalUsersLabel.setText("Total Users: " + appController.getAllUsers().size());
     totalPostsLabel.setText("Total Posts: " + appController.getAllPosts().size());
     totalRequestsLabel.setText("Total Requests: " + appController.getAllRequests().size());
 }
 
 private void deleteSelectedUser() {
     int selectedRow = usersTable.getSelectedRow();
     if (selectedRow == -1) {
         JOptionPane.showMessageDialog(this, "Please select a user to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
         return;
     }
     
     String userId = (String) usersTableModel.getValueAt(selectedRow, 0);
     String username = (String) usersTableModel.getValueAt(selectedRow, 1);
     
     // Prevent deleting admin user
     if ("admin".equals(username)) {
         JOptionPane.showMessageDialog(this, "Cannot delete admin user.", "Cannot Delete", JOptionPane.ERROR_MESSAGE);
         return;
     }
     
     int confirm = JOptionPane.showConfirmDialog(this, 
         "Are you sure you want to delete user: " + username + "?", 
         "Confirm Delete", 
         JOptionPane.YES_NO_OPTION);
     
     if (confirm == JOptionPane.YES_OPTION) {
         appController.deleteUser(userId);
         loadUsersData();
         updateStatistics();
         NotificationUtil.showNotification("User deleted successfully", mainFrame);
     }
 }
 
 private void deleteSelectedPost() {
     int selectedRow = postsTable.getSelectedRow();
     if (selectedRow == -1) {
         JOptionPane.showMessageDialog(this, "Please select a post to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
         return;
     }
     
     String postId = (String) postsTableModel.getValueAt(selectedRow, 0);
     String title = (String) postsTableModel.getValueAt(selectedRow, 3);
     
     int confirm = JOptionPane.showConfirmDialog(this, 
         "Are you sure you want to delete post: " + title + "?", 
         "Confirm Delete", 
         JOptionPane.YES_NO_OPTION);
     
     if (confirm == JOptionPane.YES_OPTION) {
         appController.deletePost(postId);
         loadPostsData();
         updateStatistics();
         NotificationUtil.showNotification("Post deleted successfully", mainFrame);
     }
 }
 
 public void refreshData() {
     loadData();
 }
}