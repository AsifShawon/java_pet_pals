package app;

//UserDashboardPanel.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.io.File;

public class UserDashboardPanel extends JPanel {
 private MainFrame mainFrame;
 private AppController appController;
 
 private JLabel welcomeLabel;
 private JButton logoutButton;
 private JTabbedPane tabbedPane;
 
 // Posts tab components
 private JTable postsTable;
 private DefaultTableModel postsTableModel;
 private JButton createPostButton;
 private JButton refreshPostsButton;
 private JButton requestButton;
 private JTextField searchField;
 private JComboBox<String> typeFilter;
 
 // My Posts tab components
 private JTable myPostsTable;
 private DefaultTableModel myPostsTableModel;
 private JButton refreshMyPostsButton;
 private JButton deleteMyPostButton;
 private JButton viewRequestsForPostButton; // new button to view requests for selected my-post
 
 // My Requests tab components
 private JTable myRequestsTable;
 private DefaultTableModel myRequestsTableModel;
 private JButton refreshMyRequestsButton;
 
 // Profile tab components
 private JTextField profileUsernameField;
 private JTextField profileContactField;
 private JPasswordField newPasswordField;
 private JPasswordField confirmPasswordField;
 private JButton updateProfileButton;
 
 // Notification components
 private JTable notificationsTable;
 private DefaultTableModel notificationsTableModel;
 private JButton refreshNotificationsButton;
 private JButton markAsReadButton;

 public UserDashboardPanel(MainFrame mainFrame, AppController appController) {
     this.mainFrame = mainFrame;
     this.appController = appController;
     
     initializeComponents();
     // Use WindowBuilder-friendly initializer
     initComponents();
     setupEventHandlers();
     styleComponents();
     loadData();
     initSocialFeatures();
 }
 
 // WindowBuilder-friendly UI initialization using GroupLayout.
 // This method creates the high-level structure (top panel and tabbed content)
 // while keeping the detailed panels (created by createPostsPanel(), createMyPostsPanel(), etc.)
 // in separate methods so WindowBuilder can edit them easily.
 private void initComponents() {
    // topPanel
    JPanel topPanel = new JPanel();
    UIStyleHelper.styleCardPanel(topPanel);
    topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

    JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftTopPanel.setBackground(UIStyleHelper.CARD_COLOR);
    leftTopPanel.add(welcomeLabel);

    JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightTopPanel.setBackground(UIStyleHelper.CARD_COLOR);
    rightTopPanel.add(logoutButton);

    // Put left and right into topPanel
    topPanel.setLayout(new BorderLayout());
    topPanel.add(leftTopPanel, BorderLayout.WEST);
    topPanel.add(rightTopPanel, BorderLayout.EAST);

    // Initialize tabbed pane and tabs
    tabbedPane = new JTabbedPane();
    JPanel postsPanel = createPostsPanel();
    JPanel myPostsPanel = createMyPostsPanel();
    JPanel myRequestsPanel = createMyRequestsPanel();
    JPanel notificationsPanel = createNotificationsPanel();
    JPanel profilePanel = createProfilePanel();

    tabbedPane.addTab("All Posts", IconHelper.createHomeIcon(), postsPanel, "Browse all available posts");
    tabbedPane.addTab("My Posts", IconHelper.createPostsIcon(), myPostsPanel, "Manage your posts");
    tabbedPane.addTab("My Requests", IconHelper.createRequestsIcon(), myRequestsPanel, "View your requests");
    tabbedPane.addTab("Notifications", IconHelper.createNotificationIcon(), notificationsPanel, "View notifications");
    tabbedPane.addTab("Profile", IconHelper.createProfileIcon(), profilePanel, "Edit your profile");

    UIStyleHelper.styleTabbedPane(tabbedPane);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    // Horizontal group
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(topPanel)
            .addComponent(tabbedPane)
    );

    // Vertical group
    layout.setVerticalGroup(
        layout.createSequentialGroup()
            .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(tabbedPane)
    );

    // Ensure components are initialized for WindowBuilder preview
    this.revalidate();
    this.repaint();
 }
 
 private void initializeComponents() {
    User currentUser = appController.getCurrentUser();
    welcomeLabel = new JLabel("Welcome, " + (currentUser != null ? currentUser.getUsername() : "Unknown"));
    welcomeLabel.setFont(UIStyleHelper.getHeaderFont());
    logoutButton = new JButton("Logout", IconHelper.createLogoutIcon());
    
    tabbedPane = new JTabbedPane();
    
    // Posts table
    postsTableModel = new DefaultTableModel(new String[]{"ID", "Author", "Type", "Title", "Description", "Date"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    postsTable = new JTable(postsTableModel);
    
    // My Posts table
    myPostsTableModel = new DefaultTableModel(new String[]{"ID", "Type", "Title", "Description", "Date"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    myPostsTable = new JTable(myPostsTableModel);
    
    // My Requests table
    myRequestsTableModel = new DefaultTableModel(new String[]{"ID", "Post Title", "Status", "To User"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    myRequestsTable = new JTable(myRequestsTableModel);
    
    // Buttons
    createPostButton = new JButton("Create New Post", IconHelper.createCreateIcon());
    refreshPostsButton = new JButton("Refresh", IconHelper.createRefreshIcon());
    requestButton = new JButton("Request Selected Post", IconHelper.createSendIcon());
    refreshMyPostsButton = new JButton("Refresh My Posts", IconHelper.createRefreshIcon());
    deleteMyPostButton = new JButton("Delete Selected Post", IconHelper.createDeleteIcon());
    viewRequestsForPostButton = new JButton("View Requests", IconHelper.createViewIcon());
    refreshMyRequestsButton = new JButton("Refresh Requests", IconHelper.createRefreshIcon());
    
    // Search and filter components
    searchField = new JTextField(20);
    typeFilter = new JComboBox<>(new String[]{"All", "DONATE", "NEED"});
    
    // Profile components - Made much wider with bigger font
    profileUsernameField = new JTextField(50); // Increased from 30 to 50 columns
    profileContactField = new JTextField(50);
    newPasswordField = new JPasswordField(50);
    confirmPasswordField = new JPasswordField(50);
    updateProfileButton = new JButton("Update Profile", IconHelper.createUpdateIcon());
    
    // Set much larger preferred size for better visibility
    java.awt.Dimension extraWideField = new java.awt.Dimension(600, 40); // Increased width and height
    profileUsernameField.setPreferredSize(extraWideField);
    profileContactField.setPreferredSize(extraWideField);
    newPasswordField.setPreferredSize(extraWideField);
    confirmPasswordField.setPreferredSize(extraWideField);
    
    // Set larger font for better readability
    Font fieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    profileUsernameField.setFont(fieldFont);
    profileContactField.setFont(fieldFont);
    newPasswordField.setFont(fieldFont);
    confirmPasswordField.setFont(fieldFont);
    
    // Load current user data into profile fields
    if (currentUser != null) {
        profileUsernameField.setText(currentUser.getUsername());
        profileContactField.setText(currentUser.getContact());
        profileUsernameField.setEditable(false); // Username should not be editable
    }
}
 
 private void setupLayout() {
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
     
     // All Posts tab
     JPanel postsPanel = createPostsPanel();
     tabbedPane.addTab("All Posts", IconHelper.createHomeIcon(), postsPanel, "Browse all available posts");
     
     // My Posts tab
     JPanel myPostsPanel = createMyPostsPanel();
     tabbedPane.addTab("My Posts", IconHelper.createPostsIcon(), myPostsPanel, "Manage your posts");
     
     // My Requests tab
     JPanel myRequestsPanel = createMyRequestsPanel();
     tabbedPane.addTab("My Requests", IconHelper.createRequestsIcon(), myRequestsPanel, "View your requests");
     
     // Profile tab
     JPanel profilePanel = createProfilePanel();
     tabbedPane.addTab("Profile", IconHelper.createProfileIcon(), profilePanel, "Edit your profile");
     
     UIStyleHelper.styleTabbedPane(tabbedPane);
     contentPanel.add(tabbedPane, BorderLayout.CENTER);
     
     add(contentPanel, BorderLayout.CENTER);
 }
 
 private JPanel createPostsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Search and filter panel
     JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
     UIStyleHelper.styleCardPanel(searchPanel);
     searchPanel.add(new JLabel("🔍 Search:"));
     searchPanel.add(searchField);
     searchPanel.add(new JLabel("📂 Type:"));
     searchPanel.add(typeFilter);
     searchPanel.add(refreshPostsButton);
     
     panel.add(searchPanel, BorderLayout.NORTH);
     
     // Posts table
     JScrollPane scrollPane = new JScrollPane(postsTable);
     scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     // Button panel
     JPanel buttonPanel = new JPanel(new FlowLayout());
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     buttonPanel.add(createPostButton);
     buttonPanel.add(requestButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private JPanel createMyPostsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Title
     JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
     UIStyleHelper.stylePanel(titlePanel);
     JLabel title = new JLabel("My Posts");
     UIStyleHelper.styleLabel(title, UIStyleHelper.LabelStyle.HEADER);
     titlePanel.add(title);
     titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     panel.add(titlePanel, BorderLayout.NORTH);
     
     JScrollPane scrollPane = new JScrollPane(myPostsTable);
     scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     JPanel buttonPanel = new JPanel(new FlowLayout());
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     buttonPanel.add(refreshMyPostsButton);
     buttonPanel.add(deleteMyPostButton);
     buttonPanel.add(viewRequestsForPostButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private JPanel createMyRequestsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Title and instruction
     JPanel titlePanel = new JPanel();
     titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
     UIStyleHelper.stylePanel(titlePanel);
     
     JLabel title = new JLabel("My Requests");
     UIStyleHelper.styleLabel(title, UIStyleHelper.LabelStyle.HEADER);
     title.setAlignmentX(Component.LEFT_ALIGNMENT);
     
     JLabel instruction = new JLabel("💡 Double-click on a request to view and send messages");
     UIStyleHelper.styleLabel(instruction, UIStyleHelper.LabelStyle.STANDARD);
     instruction.setForeground(UIStyleHelper.TEXT_SECONDARY);
     instruction.setAlignmentX(Component.LEFT_ALIGNMENT);
     
     titlePanel.add(title);
     titlePanel.add(Box.createVerticalStrut(5));
     titlePanel.add(instruction);
     titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     panel.add(titlePanel, BorderLayout.NORTH);
     
     JScrollPane scrollPane = new JScrollPane(myRequestsTable);
     scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     JPanel buttonPanel = new JPanel(new FlowLayout());
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     buttonPanel.add(refreshMyRequestsButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private JPanel createNotificationsPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(panel);
     
     // Notifications table
     String[] columnNames = {"ID", "Type", "Title", "Message", "Time", "Status"};
     notificationsTableModel = new DefaultTableModel(columnNames, 0) {
         @Override
         public boolean isCellEditable(int row, int column) {
             return false;
         }
     };
     
     notificationsTable = new JTable(notificationsTableModel);
     UIStyleHelper.styleTable(notificationsTable);
     notificationsTable.getColumnModel().getColumn(0).setMaxWidth(0);
     notificationsTable.getColumnModel().getColumn(0).setMinWidth(0);
     notificationsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
     notificationsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
     notificationsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
     notificationsTable.getColumnModel().getColumn(3).setPreferredWidth(300);
     notificationsTable.getColumnModel().getColumn(4).setPreferredWidth(150);
     notificationsTable.getColumnModel().getColumn(5).setPreferredWidth(80);
     
     // Double-click to handle notification
     notificationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override
         public void mouseClicked(java.awt.event.MouseEvent e) {
             if (e.getClickCount() == 2) {
                 handleNotificationClick();
             }
         }
     });
     
     JScrollPane scrollPane = new JScrollPane(notificationsTable);
     scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
     panel.add(scrollPane, BorderLayout.CENTER);
     
     // Button panel
     JPanel buttonPanel = new JPanel(new FlowLayout());
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
     
     refreshNotificationsButton = new JButton("Refresh", IconHelper.createRefreshIcon());
     UIStyleHelper.styleSecondaryButton(refreshNotificationsButton);
     
     markAsReadButton = new JButton("Mark as Read");
     UIStyleHelper.stylePrimaryButton(markAsReadButton);
     
     buttonPanel.add(refreshNotificationsButton);
     buttonPanel.add(markAsReadButton);
     
     panel.add(buttonPanel, BorderLayout.SOUTH);
     
     return panel;
 }
 
 private JPanel createProfilePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    UIStyleHelper.stylePanel(panel);
    
    // Main profile card - made wider
    JPanel profileCard = new JPanel(new GridBagLayout());
    UIStyleHelper.styleCardPanel(profileCard);
    profileCard.setPreferredSize(new Dimension(900, 500)); // Increased card size
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(20, 30, 20, 30); // Increased padding
    
    // Title
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    JLabel titleLabel = new JLabel("Profile Settings");
    UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.HEADER);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    profileCard.add(titleLabel, gbc);
    
    // Username field
    gbc.gridwidth = 1;
    gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
    JLabel usernameLabel = new JLabel("Username:");
    UIStyleHelper.styleLabel(usernameLabel, UIStyleHelper.LabelStyle.STANDARD);
    usernameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14)); // Bigger label font
    profileCard.add(usernameLabel, gbc);
    gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL; // Allow field to expand
    gbc.weightx = 1.0; // Give extra horizontal space to the field
    profileCard.add(profileUsernameField, gbc);
    
    // Contact field
    gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
    gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    JLabel contactLabel = new JLabel("Contact:");
    UIStyleHelper.styleLabel(contactLabel, UIStyleHelper.LabelStyle.STANDARD);
    contactLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    profileCard.add(contactLabel, gbc);
    gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    profileCard.add(profileContactField, gbc);
    
    // New Password field
    gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
    gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    JLabel newPasswordLabel = new JLabel("New Password:");
    UIStyleHelper.styleLabel(newPasswordLabel, UIStyleHelper.LabelStyle.STANDARD);
    newPasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    profileCard.add(newPasswordLabel, gbc);
    gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    profileCard.add(newPasswordField, gbc);
    
    // Confirm Password field
    gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
    gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
    UIStyleHelper.styleLabel(confirmPasswordLabel, UIStyleHelper.LabelStyle.STANDARD);
    confirmPasswordLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    profileCard.add(confirmPasswordLabel, gbc);
    gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    profileCard.add(confirmPasswordField, gbc);
    
    // Update button
    gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    gbc.insets = new Insets(30, 30, 20, 30);
    profileCard.add(updateProfileButton, gbc);
    
    // Center the card
    JPanel centerPanel = new JPanel(new GridBagLayout());
    UIStyleHelper.stylePanel(centerPanel);
    centerPanel.add(profileCard, new GridBagConstraints());
    
    panel.add(centerPanel, BorderLayout.CENTER);
    
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
     
     createPostButton.addActionListener(e -> showCreatePostDialog());
     refreshPostsButton.addActionListener(e -> loadPostsData());
     requestButton.addActionListener(e -> requestSelectedPost());
     
     refreshMyPostsButton.addActionListener(e -> loadMyPostsData());
     deleteMyPostButton.addActionListener(e -> deleteSelectedMyPost());
     viewRequestsForPostButton.addActionListener(e -> showRequestsForSelectedPost());
     
     refreshMyRequestsButton.addActionListener(e -> loadMyRequestsData());
     
     refreshNotificationsButton.addActionListener(e -> loadNotificationsData());
     markAsReadButton.addActionListener(e -> markSelectedNotificationAsRead());
     
     updateProfileButton.addActionListener(e -> updateProfile());
     
     // Search functionality
     searchField.addActionListener(e -> filterPosts());
     typeFilter.addActionListener(e -> filterPosts());
 }
 
 private void styleComponents() {
     UIStyleHelper.styleButton(logoutButton);
     UIStyleHelper.styleSuccessButton(createPostButton);
     UIStyleHelper.styleButton(refreshPostsButton);
     UIStyleHelper.styleButton(requestButton);
     UIStyleHelper.styleButton(refreshMyPostsButton);
     UIStyleHelper.styleDangerButton(deleteMyPostButton);
     UIStyleHelper.styleButton(refreshMyRequestsButton);
     UIStyleHelper.styleButton(updateProfileButton);
     
     UIStyleHelper.styleInputField(searchField);
     UIStyleHelper.styleInputField(profileUsernameField);
     UIStyleHelper.styleInputField(profileContactField);
     UIStyleHelper.styleInputField(newPasswordField);
     UIStyleHelper.styleInputField(confirmPasswordField);
     
     UIStyleHelper.styleTable(postsTable);
     UIStyleHelper.styleTable(myPostsTable);
     UIStyleHelper.styleTable(myRequestsTable);
 }
 
 private void loadData() {
     // Ensure welcome label shows the currently logged-in user (panel may be reused across logins)
     User currentUser = appController.getCurrentUser();
     welcomeLabel.setText("Welcome, " + (currentUser != null ? currentUser.getUsername() : "Unknown"));
     loadPostsData();
     loadMyPostsData();
     loadMyRequestsData();
     loadNotificationsData();
     updateNotificationBadge();
 }
 
 private void loadPostsData() {
     postsTableModel.setRowCount(0);
     Collection<Post> posts = appController.getAllPosts();
     User current = appController.getCurrentUser();
     String currentId = current != null ? current.getId() : null;
     for (Post post : posts) {
         // Skip archived donate posts for users who are neither donor nor adopter
         if (appController.isPostArchivedForCurrentUser(post)) continue;

         // Don't show user's own posts in the all posts view
         if (currentId == null || post.getAuthorId().equals(currentId)) continue;

         postsTableModel.addRow(new Object[]{
             post.getId(),
             getUsernameById(post.getAuthorId()),
             post.getType(),
             post.getTitle(),
             post.getDescription(),
             post.getTimestamp().toString()
         });
     }
 }
 
 private void loadMyPostsData() {
     myPostsTableModel.setRowCount(0);
     Collection<Post> posts = appController.getAllPosts();
     User currentUser = appController.getCurrentUser();
     
     for (Post post : posts) {
         if (post.getAuthorId().equals(currentUser.getId())) {
             myPostsTableModel.addRow(new Object[]{
                 post.getId(),
                 post.getType(),
                 post.getTitle(),
                 post.getDescription(),
                 post.getTimestamp().toString()
             });
         }
     }
 }
 
 private void loadMyRequestsData() {
     myRequestsTableModel.setRowCount(0);
     Collection<Request> requests = appController.getCurrentUserRequests();
     
     for (Request request : requests) {
         Post post = getPostById(request.getPostId());
         String postTitle = post != null ? post.getTitle() : "Unknown";
         String toUsername = getUsernameById(request.getToUserId());
         
         myRequestsTableModel.addRow(new Object[]{
             request.getId(),
             postTitle,
             request.getStatus(),
             toUsername
         });
     }
 }
 
 private void filterPosts() {
     String searchText = searchField.getText().toLowerCase();
     String selectedType = (String) typeFilter.getSelectedItem();
     
     postsTableModel.setRowCount(0);
     Collection<Post> posts = appController.getAllPosts();
     
     for (Post post : posts) {
         // Skip archived donate posts for users who are neither donor nor adopter
         if (appController.isPostArchivedForCurrentUser(post)) continue;

         User current = appController.getCurrentUser();
         String currentId = current != null ? current.getId() : null;
         if (currentId == null || post.getAuthorId().equals(currentId)) continue;

         boolean matchesSearch = searchText.isEmpty() || 
             post.getTitle().toLowerCase().contains(searchText) ||
             post.getDescription().toLowerCase().contains(searchText);
        
        boolean matchesType = "All".equals(selectedType) || post.getType().toString().equalsIgnoreCase(selectedType);
        
        if (matchesSearch && matchesType) {
            postsTableModel.addRow(new Object[]{
                post.getId(),
                getUsernameById(post.getAuthorId()),
                post.getType(),
                post.getTitle(),
                post.getDescription(),
                post.getTimestamp().toString()
            });
        }
     }
 }
 
 private void showCreatePostDialog() {
     JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Create New Post", true);
     dialog.setSize(550, 620);
     dialog.setLocationRelativeTo(this);
     dialog.setLayout(new BorderLayout());

     JPanel panel = new JPanel(new GridBagLayout());
     UIStyleHelper.stylePanel(panel);
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(8, 10, 8, 10);
     gbc.fill = GridBagConstraints.HORIZONTAL;

     JComboBox<String> typeCombo = new JComboBox<>(new String[]{"DONATE", "NEED"});
     JTextField titleField = new JTextField(22);
     JTextArea descriptionArea = new JTextArea(5, 22);
     descriptionArea.setLineWrap(true);
     descriptionArea.setWrapStyleWord(true);
     JScrollPane descScrollPane = new JScrollPane(descriptionArea);

     JTextField imagePathField = new JTextField(22);
     imagePathField.setEditable(false);
     JButton browseButton = new JButton("Select Image");
     UIStyleHelper.styleSecondaryButton(browseButton);

     browseButton.addActionListener(ev -> {
         JFileChooser chooser = new JFileChooser();
         int res = chooser.showOpenDialog(dialog);
         if (res == JFileChooser.APPROVE_OPTION) {
             File selected = chooser.getSelectedFile();
             imagePathField.setText(selected.getAbsolutePath());
         }
     });

     int y = 0;
     gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; panel.add(new JLabel("Type:"), gbc);
     gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panel.add(typeCombo, gbc);

     gbc.gridx = 0; gbc.gridy = ++y; gbc.anchor = GridBagConstraints.EAST; panel.add(new JLabel("Title:"), gbc);
     gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panel.add(titleField, gbc);

     gbc.gridx = 0; gbc.gridy = ++y; gbc.anchor = GridBagConstraints.NORTHEAST; panel.add(new JLabel("Description:"), gbc);
     gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panel.add(descScrollPane, gbc);

     gbc.gridx = 0; gbc.gridy = ++y; gbc.anchor = GridBagConstraints.EAST; panel.add(new JLabel("Image:"), gbc);
     JPanel imageSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
     imageSelectPanel.setOpaque(false);
     imageSelectPanel.add(imagePathField);
     imageSelectPanel.add(Box.createHorizontalStrut(5));
     imageSelectPanel.add(browseButton);
     gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; panel.add(imageSelectPanel, gbc);

     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     buttonPanel.setOpaque(false);
     JButton createButton = new JButton("Create Post");
     JButton cancelButton = new JButton("Cancel");
     UIStyleHelper.styleSuccessButton(createButton);
     UIStyleHelper.styleButton(cancelButton);

     createButton.addActionListener(e -> {
         String type = (String) typeCombo.getSelectedItem();
         String title = titleField.getText().trim();
         String description = descriptionArea.getText().trim();
         String imagePath = imagePathField.getText().isBlank() ? null : imagePathField.getText();

         if (title.isEmpty() || description.isEmpty()) {
             JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
             return;
         }

         appController.createPost(type, title, description, imagePath);
         loadMyPostsData();
         loadPostsData();
         NotificationUtil.showNotification("Post created!", mainFrame);
         dialog.dispose();
     });
     cancelButton.addActionListener(e -> dialog.dispose());

     buttonPanel.add(createButton);
     buttonPanel.add(cancelButton);

     dialog.add(panel, BorderLayout.CENTER);
     dialog.add(buttonPanel, BorderLayout.SOUTH);
     dialog.setVisible(true);
 }
 
 // Social media style detail popup for a post
 private void showPostDetailsDialog(Post post) {
     JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Post Details", true);
     dialog.setSize(600, 500);
     dialog.setLocationRelativeTo(this);
     dialog.setLayout(new BorderLayout());

     JPanel header = new JPanel(new BorderLayout());
     header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
     header.setBackground(UIStyleHelper.CARD_COLOR);
     JLabel titleLabel = new JLabel(post.getTitle() + "  (" + post.getType() + ")");
     UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.HEADER);
     JLabel authorLabel = new JLabel("by " + getUsernameById(post.getAuthorId()));
     UIStyleHelper.styleLabel(authorLabel, UIStyleHelper.LabelStyle.SECONDARY);
     header.add(titleLabel, BorderLayout.WEST);
     header.add(authorLabel, BorderLayout.EAST);

     JPanel center = new JPanel();
     center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
     center.setBackground(UIStyleHelper.CARD_COLOR);
     center.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

     if (post.getImagePath() != null) {
         try {
             ImageIcon icon = new ImageIcon(post.getImagePath());
             Image scaled = icon.getImage().getScaledInstance(520, -1, Image.SCALE_SMOOTH);
             JLabel imgLabel = new JLabel(new ImageIcon(scaled));
             imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
             center.add(imgLabel);
             center.add(Box.createVerticalStrut(15));
         } catch (Exception ex) {
             // ignore
         }
     }

     JTextArea descArea = new JTextArea(post.getDescription());
     descArea.setWrapStyleWord(true);
     descArea.setLineWrap(true);
     descArea.setEditable(false);
     descArea.setOpaque(false);
     descArea.setFont(UIStyleHelper.getStandardFont());
     JScrollPane descScroll = new JScrollPane(descArea);
     descScroll.setBorder(BorderFactory.createTitledBorder("Description"));
     center.add(descScroll);

     JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     footer.setBackground(UIStyleHelper.CARD_COLOR);
     JButton closeBtn = new JButton("Close");
     UIStyleHelper.styleButton(closeBtn);
     closeBtn.addActionListener(e -> dialog.dispose());
     footer.add(closeBtn);

     dialog.add(header, BorderLayout.NORTH);
     dialog.add(center, BorderLayout.CENTER);
     dialog.add(footer, BorderLayout.SOUTH);
     dialog.setVisible(true);
 }

 // Add mouse double-click listeners to open details
 private void enablePostDetailsInteraction() {
     postsTable.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override public void mouseClicked(java.awt.event.MouseEvent e) {
             if (e.getClickCount() == 2) {
                 int row = postsTable.getSelectedRow();
                 if (row >= 0) {
                     // fixed cast placement
                     String postId = (String) postsTableModel.getValueAt(row, 0);
                     Post post = getPostById(postId);
                     if (post != null) showPostDetailsDialog(post);
                 }
             }
         }
     });
     myPostsTable.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override public void mouseClicked(java.awt.event.MouseEvent e) {
             if (e.getClickCount() == 2) {
                 int row = myPostsTable.getSelectedRow();
                 if (row >= 0) {
                     // fixed cast placement
                     String postId = (String) myPostsTableModel.getValueAt(row, 0);
                     Post post = getPostById(postId);
                     if (post != null) showPostDetailsDialog(post);
                 }
             }
         }
     });
     
     // Add double-click listener for My Requests table to allow messaging
     myRequestsTable.addMouseListener(new java.awt.event.MouseAdapter() {
         @Override 
         public void mouseClicked(java.awt.event.MouseEvent e) {
             if (e.getClickCount() == 2) {
                 int row = myRequestsTable.getSelectedRow();
                 if (row >= 0) {
                     String requestId = (String) myRequestsTableModel.getValueAt(row, 0);
                     String postTitle = (String) myRequestsTableModel.getValueAt(row, 1);
                     showMessagingDialogForRequester(requestId, postTitle);
                 }
             }
         }
     });
 }

 // Call this in constructor after tables initialized
 private void initSocialFeatures() {
     enablePostDetailsInteraction();
 }

 // Helper methods
 private String getUsernameById(String userId) {
     User user = appController.getUserById(userId);
     return user != null ? user.getUsername() : "Unknown";
 }
 
 private Post getPostById(String postId) {
     return appController.getPostById(postId);
 }
 
 private void loadNotificationsData() {
     notificationsTableModel.setRowCount(0);
     Collection<Notification> notifications = appController.getUserNotifications();
     for (Notification notification : notifications) {
         String status = notification.isRead() ? "Read" : "Unread";
         notificationsTableModel.addRow(new Object[]{
             notification.getId(),
             notification.getType(),
             notification.getTitle(),
             notification.getMessage(),
             notification.getTimestamp().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
             status
         });
     }
 }
 
 private void markSelectedNotificationAsRead() {
     int selectedRow = notificationsTable.getSelectedRow();
     if (selectedRow == -1) {
         JOptionPane.showMessageDialog(this, "Please select a notification to mark as read.", "No Selection", JOptionPane.WARNING_MESSAGE);
         return;
     }
     String notificationId = (String) notificationsTableModel.getValueAt(selectedRow, 0);
     appController.markNotificationAsRead(notificationId);
     loadNotificationsData();
     updateNotificationBadge();
     NotificationUtil.showNotification("Notification marked as read", mainFrame);
 }
 
 private void handleNotificationClick() {
     int selectedRow = notificationsTable.getSelectedRow();
     if (selectedRow == -1) return;
     
     String notificationId = (String) notificationsTableModel.getValueAt(selectedRow, 0);
     String type = (String) notificationsTableModel.getValueAt(selectedRow, 1);
     
     // Mark as read first
     appController.markNotificationAsRead(notificationId);
     
     // Handle notification based on type
     if ("NEW_REQUEST".equals(type) || "NEW_MESSAGE".equals(type)) {
         // Switch to My Posts tab to handle the request
         tabbedPane.setSelectedIndex(1); // My Posts tab
         loadMyPostsData();
     } else if ("REQUEST_ACCEPTED".equals(type) || "REQUEST_REJECTED".equals(type)) {
         // Switch to My Requests tab
         tabbedPane.setSelectedIndex(2); // My Requests tab
         loadMyRequestsData();
     }
     
     loadNotificationsData();
     updateNotificationBadge();
 }
 
 private void updateNotificationBadge() {
     int unreadCount = appController.getUnreadNotificationCount();
     if (unreadCount > 0) {
         // Update the tab title to show notification count
         int notificationTabIndex = 3; // Notifications tab
         tabbedPane.setTitleAt(notificationTabIndex, "Notifications (" + unreadCount + ")");
     } else {
         tabbedPane.setTitleAt(3, "Notifications");
     }
 }
 
 public void refreshData() {
     loadData();
 }

 // Added back lost methods after refactor
 private void requestSelectedPost() {
     int selectedRow = postsTable.getSelectedRow();
     if (selectedRow == -1) {
         JOptionPane.showMessageDialog(this, "Please select a post to request.", "No Selection", JOptionPane.WARNING_MESSAGE);
         return;
     }
     String postId = (String) postsTableModel.getValueAt(selectedRow, 0);
     String title = (String) postsTableModel.getValueAt(selectedRow, 3);
     
     // Create dialog for request with message
     showRequestDialog(postId, title);
 }
 
 private void showRequestDialog(String postId, String postTitle) {
     JDialog requestDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Send Request", true);
     requestDialog.setLayout(new BorderLayout());
     requestDialog.setSize(500, 400);
     requestDialog.setLocationRelativeTo(this);
     
     // Header
     JPanel headerPanel = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(headerPanel);
     headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
     
     JLabel titleLabel = new JLabel("Request for: " + postTitle);
     UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.HEADER);
     headerPanel.add(titleLabel, BorderLayout.CENTER);
     
     // Message input
     JPanel messagePanel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(messagePanel);
     messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     
     JLabel messageLabel = new JLabel("Message (optional):");
     UIStyleHelper.styleLabel(messageLabel, UIStyleHelper.LabelStyle.STANDARD);
     messagePanel.add(messageLabel, BorderLayout.NORTH);
     
     JTextArea messageArea = new JTextArea(8, 30);
     messageArea.setLineWrap(true);
     messageArea.setWrapStyleWord(true);
     messageArea.setText("Write a message to the pet owner explaining why you want to adopt this pet...");
     messageArea.setForeground(Color.GRAY);
     messageArea.addFocusListener(new java.awt.event.FocusAdapter() {
         @Override
         public void focusGained(java.awt.event.FocusEvent e) {
             if (messageArea.getText().equals("Write a message to the pet owner explaining why you want to adopt this pet...")) {
                 messageArea.setText("");
                 messageArea.setForeground(Color.BLACK);
             }
         }
         @Override
         public void focusLost(java.awt.event.FocusEvent e) {
             if (messageArea.getText().trim().isEmpty()) {
                 messageArea.setText("Write a message to the pet owner explaining why you want to adopt this pet...");
                 messageArea.setForeground(Color.GRAY);
             }
         }
     });
     UIStyleHelper.styleInputField(messageArea);
     JScrollPane scrollPane = new JScrollPane(messageArea);
     scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
     messagePanel.add(scrollPane, BorderLayout.CENTER);
     
     // Buttons
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     UIStyleHelper.stylePanel(buttonPanel);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
     
     JButton cancelButton = new JButton("Cancel");
     UIStyleHelper.styleSecondaryButton(cancelButton);
     cancelButton.addActionListener(e -> requestDialog.dispose());
     
     JButton sendButton = new JButton("Send Request");
     UIStyleHelper.stylePrimaryButton(sendButton);
     sendButton.addActionListener(e -> {
         try {
             Request request = appController.createRequest(postId);
             if (request != null) {
                 // Send initial message if provided
                 String messageText = messageArea.getText().trim();
                 if (!messageText.isEmpty()) {
                     appController.sendMessage(request.getId(), messageText);
                 }
                 loadMyRequestsData();
                 NotificationUtil.showNotification("Request sent successfully!", mainFrame);
                 requestDialog.dispose();
             } else {
                 JOptionPane.showMessageDialog(requestDialog, "Cannot request your own post or post not found.", "Error", JOptionPane.ERROR_MESSAGE);
             }
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(requestDialog, "Error creating request: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
     });
     
     buttonPanel.add(cancelButton);
     buttonPanel.add(Box.createHorizontalStrut(10));
     buttonPanel.add(sendButton);
     
     requestDialog.add(headerPanel, BorderLayout.NORTH);
     requestDialog.add(messagePanel, BorderLayout.CENTER);
     requestDialog.add(buttonPanel, BorderLayout.SOUTH);
     
     requestDialog.setVisible(true);
 }
 
 private void deleteSelectedMyPost() {
     int selectedRow = myPostsTable.getSelectedRow();
     if (selectedRow == -1) {
         JOptionPane.showMessageDialog(this, "Please select a post to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
         return;
     }
     String postId = (String) myPostsTableModel.getValueAt(selectedRow, 0);
     String title = (String) myPostsTableModel.getValueAt(selectedRow, 2);
     int confirm = JOptionPane.showConfirmDialog(this,
             "Are you sure you want to delete the post: " + title + "?",
             "Confirm Delete",
             JOptionPane.YES_NO_OPTION);
     if (confirm == JOptionPane.YES_OPTION) {
         appController.deletePost(postId);
         loadMyPostsData();
         loadPostsData();
         NotificationUtil.showNotification("Post deleted successfully!", mainFrame);
     }
 }
 
 private void updateProfile() {
     String contact = profileContactField.getText().trim();
     String newPassword = new String(newPasswordField.getPassword());
     String confirmPassword = new String(confirmPasswordField.getPassword());
     if (contact.isEmpty()) {
         JOptionPane.showMessageDialog(this, "Contact field cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
         return;
     }
     User currentUser = appController.getCurrentUser();
     currentUser.setContact(contact);
     if (!newPassword.isEmpty()) {
         if (!newPassword.equals(confirmPassword)) {
             JOptionPane.showMessageDialog(this, "Passwords do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         if (newPassword.length() < 3) {
             JOptionPane.showMessageDialog(this, "Password must be at least 3 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         String hashedPassword = PasswordUtil.hashPassword(newPassword);
         currentUser.setPasswordHash(hashedPassword);
     }
     appController.updateUser(currentUser);
     newPasswordField.setText("");
     confirmPasswordField.setText("");
     NotificationUtil.showNotification("Profile updated successfully!", mainFrame);
 }
 
 // Show requests for selected my-post (for the post owner)
 private void showRequestsForSelectedPost() {
     int selectedRow = myPostsTable.getSelectedRow();
     if (selectedRow == -1) {
         JOptionPane.showMessageDialog(this, "Please select one of your posts to view incoming requests.", "No Selection", JOptionPane.WARNING_MESSAGE);
         return;
     }
     String postId = (String) myPostsTableModel.getValueAt(selectedRow, 0);
     showRequestsDialogForPost(postId);
 }
 
 private void showRequestsDialogForPost(String postId) {
     JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Requests for Post", true);
     dialog.setSize(700, 500);
     dialog.setLocationRelativeTo(this);
     dialog.setLayout(new BorderLayout());

     // Table of requests
     DefaultTableModel reqModel = new DefaultTableModel(new String[]{"ID", "From", "Status"}, 0) {
         @Override public boolean isCellEditable(int r, int c) { return false; }
     };
     JTable reqTable = new JTable(reqModel);
     JScrollPane reqScroll = new JScrollPane(reqTable);
     dialog.add(reqScroll, BorderLayout.WEST);
     reqScroll.setPreferredSize(new Dimension(260, 400));

     // Right panel: messages and actions
     JPanel rightPanel = new JPanel(new BorderLayout());
     rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

     JTextArea messagesArea = new JTextArea();
     messagesArea.setEditable(false);
     JScrollPane msgScroll = new JScrollPane(messagesArea);
     rightPanel.add(msgScroll, BorderLayout.CENTER);

     JPanel actionPanel = new JPanel(new BorderLayout());
     JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     JButton acceptBtn = new JButton("Accept");
     JButton rejectBtn = new JButton("Reject");
     JButton refreshBtn = new JButton("Refresh");
     buttonsPanel.add(acceptBtn);
     buttonsPanel.add(rejectBtn);
     buttonsPanel.add(refreshBtn);

     JPanel sendPanel = new JPanel(new BorderLayout());
     JTextField messageField = new JTextField();
     JButton sendBtn = new JButton("Send");
     sendPanel.add(messageField, BorderLayout.CENTER);
     sendPanel.add(sendBtn, BorderLayout.EAST);

     actionPanel.add(buttonsPanel, BorderLayout.NORTH);
     actionPanel.add(sendPanel, BorderLayout.SOUTH);

     rightPanel.add(actionPanel, BorderLayout.SOUTH);

     dialog.add(rightPanel, BorderLayout.CENTER);

     // Load requests for post
     java.util.List<Request> requests = new java.util.ArrayList<>(appController.getRequestsForPost(postId));
     // Add to table
     for (Request r : requests) {
         User fromUser = appController.getUserById(r.getFromUserId());
         String fromName = fromUser != null ? fromUser.getUsername() : r.getFromUserId();
         reqModel.addRow(new Object[]{r.getId(), fromName, r.getStatus()});
     }

     // Helpers to load messages for selected request
     Runnable loadMessagesForSelected = () -> {
         int sel = reqTable.getSelectedRow();
         messagesArea.setText("");
         if (sel >= 0) {
             String reqId = (String) reqModel.getValueAt(sel, 0);
             java.util.Collection<Message> msgs = appController.getMessagesForRequest(reqId);
             for (Message m : msgs) {
                 String sender = m.getSenderId();
                 if ("SYSTEM".equals(sender)) sender = "System";
                 else {
                     User u = appController.getUserById(sender);
                     sender = u != null ? u.getUsername() : sender;
                 }
                 messagesArea.append(sender + ": " + m.getContent() + "\n");
             }
         }
     };

     reqTable.getSelectionModel().addListSelectionListener(e -> loadMessagesForSelected.run());

     // Send message action
     sendBtn.addActionListener(e -> {
         int sel = reqTable.getSelectedRow();
         if (sel < 0) { JOptionPane.showMessageDialog(dialog, "Select a request first.", "No Selection", JOptionPane.WARNING_MESSAGE); return; }
         String reqId = (String) reqModel.getValueAt(sel, 0);
         String text = messageField.getText().trim();
         if (text.isEmpty()) return;
         appController.sendMessage(reqId, text);
         messageField.setText("");
         loadMessagesForSelected.run();
     });

     // Accept/Reject actions (only enabled if current user is the post owner)
     acceptBtn.addActionListener(e -> {
         int sel = reqTable.getSelectedRow();
         if (sel < 0) return;
         String reqId = (String) reqModel.getValueAt(sel, 0);
         appController.updateRequestStatus(reqId, "ACCEPTED");
         reqModel.setValueAt("ACCEPTED", sel, 2);
         loadMessagesForSelected.run();
     });
     rejectBtn.addActionListener(e -> {
         int sel = reqTable.getSelectedRow();
         if (sel < 0) return;
         String reqId = (String) reqModel.getValueAt(sel, 0);
         appController.updateRequestStatus(reqId, "REJECTED");
         reqModel.setValueAt("REJECTED", sel, 2);
     });

     refreshBtn.addActionListener(e -> {
         // reload statuses/messages
         reqModel.setRowCount(0); // Clear existing rows
         java.util.List<Request> refreshedRequests = new java.util.ArrayList<>(appController.getRequestsForPost(postId));
         for (Request r : refreshedRequests) {
             User fromUser = appController.getUserById(r.getFromUserId());
             String fromName = fromUser != null ? fromUser.getUsername() : r.getFromUserId();
             reqModel.addRow(new Object[]{r.getId(), fromName, r.getStatus()});
         }
         loadMessagesForSelected.run();
     });

     dialog.setVisible(true);
 }
 
 // Show messaging dialog for requester (from My Requests tab)
 private void showMessagingDialogForRequester(String requestId, String postTitle) {
     JDialog messagingDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Messages for: " + postTitle, true);
     messagingDialog.setSize(600, 500);
     messagingDialog.setLocationRelativeTo(this);
     messagingDialog.setLayout(new BorderLayout());
     
     // Header
     JPanel headerPanel = new JPanel(new BorderLayout());
     UIStyleHelper.styleCardPanel(headerPanel);
     headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
     
     JLabel titleLabel = new JLabel("Messages for: " + postTitle);
     UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.HEADER);
     headerPanel.add(titleLabel, BorderLayout.CENTER);
     
     // Messages area
     JTextArea messagesArea = new JTextArea();
     messagesArea.setEditable(false);
     messagesArea.setLineWrap(true);
     messagesArea.setWrapStyleWord(true);
     UIStyleHelper.styleInputField(messagesArea);
     JScrollPane messagesScrollPane = new JScrollPane(messagesArea);
     messagesScrollPane.setBorder(BorderFactory.createTitledBorder("Conversation"));
     
     // Message input
     JPanel inputPanel = new JPanel(new BorderLayout());
     UIStyleHelper.stylePanel(inputPanel);
     inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
     
     JTextField messageField = new JTextField();
     UIStyleHelper.styleInputField(messageField);
     messageField.setBorder(BorderFactory.createTitledBorder("Type your message"));
     
     JButton sendButton = new JButton("Send");
     UIStyleHelper.stylePrimaryButton(sendButton);
     sendButton.setPreferredSize(new Dimension(80, 40));
     
     inputPanel.add(messageField, BorderLayout.CENTER);
     inputPanel.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
     
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     buttonPanel.setBackground(UIStyleHelper.BACKGROUND_COLOR);
     buttonPanel.add(sendButton);
     inputPanel.add(buttonPanel, BorderLayout.SOUTH);
     
     // Load and display messages
     Runnable loadMessages = () -> {
         messagesArea.setText("");
         Collection<Message> messages = appController.getMessagesForRequest(requestId);
         for (Message message : messages) {
             String senderName;
             if ("SYSTEM".equals(message.getSenderId())) {
                 senderName = "System";
             } else {
                 User sender = appController.getUserById(message.getSenderId());
                 senderName = sender != null ? sender.getUsername() : "Unknown";
             }
             messagesArea.append(senderName + ": " + message.getContent() + "\n");
         }
         if (messagesArea.getText().isEmpty()) {
             messagesArea.setText("No messages yet. Start the conversation!");
         }
         // Scroll to bottom
         messagesArea.setCaretPosition(messagesArea.getDocument().getLength());
     };
     
     // Initial load
     loadMessages.run();
     
     // Send message action
     sendButton.addActionListener(e -> {
         String messageText = messageField.getText().trim();
         if (!messageText.isEmpty()) {
             appController.sendMessage(requestId, messageText);
             messageField.setText("");
             loadMessages.run();
         }
     });
     
     // Enter key to send
     messageField.addActionListener(e -> sendButton.doClick());
     
     // Close button
     JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     UIStyleHelper.stylePanel(bottomPanel);
     bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
     
     JButton closeButton = new JButton("Close");
     UIStyleHelper.styleSecondaryButton(closeButton);
     closeButton.addActionListener(e -> messagingDialog.dispose());
     bottomPanel.add(closeButton);
     
     messagingDialog.add(headerPanel, BorderLayout.NORTH);
     messagingDialog.add(messagesScrollPane, BorderLayout.CENTER);
     
     JPanel southPanel = new JPanel(new BorderLayout());
     southPanel.add(inputPanel, BorderLayout.CENTER);
     southPanel.add(bottomPanel, BorderLayout.SOUTH);
     messagingDialog.add(southPanel, BorderLayout.SOUTH);
     
     messagingDialog.setVisible(true);
 }
}