package app;

//LoginPanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
 private MainFrame mainFrame;
 private AppController appController;
 
 private JTextField usernameField;
 private JPasswordField passwordField;
 private JButton loginButton;
 private JButton signupButton;
 private JLabel titleLabel;
 private JLabel subtitleLabel;
 
 public LoginPanel(MainFrame mainFrame, AppController appController) {
     this.mainFrame = mainFrame;
     this.appController = appController;
     
     initializeComponents();
     //setupLayout();
     initComponents();
     setupEventHandlers();
     styleComponents();
 }
 
 // WindowBuilder-friendly UI init
 private void initComponents() {
     // Create background panel with image
     UIStyleHelper.BackgroundImagePanel backgroundPanel = 
         new UIStyleHelper.BackgroundImagePanel("E:\\JAVA\\PetPals\\image\\pet_bg.jpg");
     backgroundPanel.setOpacity(0.2f); // 20% opacity for subtle effect
     backgroundPanel.setLayout(new BorderLayout());

     // Main container with padding
     JPanel mainContainer = new JPanel(new GridBagLayout());
     mainContainer.setOpaque(false); // Make transparent to show background
     UIStyleHelper.stylePanelWithBackground(mainContainer, null);

     // Login card
     JPanel loginCard = new JPanel();
     loginCard.setLayout(new BoxLayout(loginCard, BoxLayout.Y_AXIS));
     UIStyleHelper.styleCardPanel(loginCard);
     loginCard.setPreferredSize(new Dimension(400, 700));

     // Header section
     JPanel headerPanel = new JPanel();
     headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
     headerPanel.setBackground(UIStyleHelper.CARD_COLOR);
     headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

     titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

     headerPanel.add(titleLabel);
     headerPanel.add(Box.createVerticalStrut(10));
     headerPanel.add(subtitleLabel);

     // Form section
     JPanel formPanel = new JPanel(new GridBagLayout());
     formPanel.setBackground(UIStyleHelper.CARD_COLOR);
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(15, 20, 15, 20);

     // Username field
     gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
     JLabel usernameLabel = new JLabel("Username");
     formPanel.add(usernameLabel, gbc);

     gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
     formPanel.add(usernameField, gbc);

     // Password field
     gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
     JLabel passwordLabel = new JLabel("Password");
     formPanel.add(passwordLabel, gbc);

     gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
     formPanel.add(passwordField, gbc);

     // Admin login hint
     gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
     gbc.insets = new Insets(10, 20, 5, 20);
    //  JLabel adminHintLabel = new JLabel("Admin Login: username='admin', password='admin'");
    //  adminHintLabel.setFont(UIStyleHelper.getSmallFont());
    //  adminHintLabel.setForeground(UIStyleHelper.TEXT_SECONDARY);
    //  adminHintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    //  formPanel.add(adminHintLabel, gbc);

     // Button section
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
     buttonPanel.setBackground(UIStyleHelper.CARD_COLOR);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

     loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
     signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);

     buttonPanel.add(loginButton);
     buttonPanel.add(Box.createVerticalStrut(15));
     buttonPanel.add(signupButton);

     // Assemble the card
     loginCard.add(headerPanel);
     loginCard.add(formPanel);
     loginCard.add(buttonPanel);

     // Center the card
     GridBagConstraints mainGbc = new GridBagConstraints();
     mainContainer.add(loginCard, mainGbc);

     // Add main container to background panel
     backgroundPanel.add(mainContainer, BorderLayout.CENTER);

     setLayout(new BorderLayout());
     add(backgroundPanel, BorderLayout.CENTER);
 }
 
 private void initializeComponents() {
     usernameField = new JTextField(20);
     passwordField = new JPasswordField(20);
     loginButton = new JButton("Login");
     signupButton = new JButton("Create Account");
     
     titleLabel = new JLabel("PetPals");
     subtitleLabel = new JLabel("Pet Adoption & Donation System");
 }
 
 private void setupEventHandlers() {
     loginButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             handleLogin();
         }
     });
     
     signupButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             mainFrame.showSignupPanel();
         }
     });
     
     // Allow Enter key to trigger login
     passwordField.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             handleLogin();
         }
     });
     
     usernameField.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             passwordField.requestFocus();
         }
     });
 }
 
 private void styleComponents() {
     UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.TITLE);
     titleLabel.setForeground(UIStyleHelper.PRIMARY_COLOR);
     
     UIStyleHelper.styleLabel(subtitleLabel, UIStyleHelper.LabelStyle.SECONDARY);
     
     UIStyleHelper.styleInputField(usernameField);
     UIStyleHelper.styleInputField(passwordField);
     
     UIStyleHelper.stylePrimaryButton(loginButton);
     UIStyleHelper.styleSecondaryButton(signupButton);
     
     // Set placeholder text
     usernameField.setToolTipText("Enter your username");
     passwordField.setToolTipText("Enter your password");
 }
 
 private void handleLogin() {
     String username = usernameField.getText().trim();
     String password = new String(passwordField.getPassword());
     
     if (username.isEmpty() || password.isEmpty()) {
         showErrorDialog("Please enter both username and password.");
         return;
     }
     
     // Show loading state
     loginButton.setText("Logging in...");
     loginButton.setEnabled(false);
     
     // Perform login in a separate thread to avoid blocking UI
     SwingUtilities.invokeLater(() -> {
         try {
             if (appController.login(username, password)) {
                 NotificationUtil.showNotification("Login successful!", mainFrame);
                 User currentUser = appController.getCurrentUser();
                 
                 // Clear fields
                 usernameField.setText("");
                 passwordField.setText("");
                 
                 if ("ADMIN".equals(currentUser.getRole())) {
                     mainFrame.showAdminDashboard();
                 } else {
                     mainFrame.showUserDashboard();
                 }
             } else {
                 showErrorDialog("Invalid username or password. Please try again.");
             }
         } finally {
             // Reset button state
             loginButton.setText("Login");
             loginButton.setEnabled(true);
         }
     });
 }
 
 private void showErrorDialog(String message) {
     JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
     passwordField.selectAll();
     passwordField.requestFocus();
 }
 
 public void resetFields() {
     usernameField.setText("");
     passwordField.setText("");
     usernameField.requestFocus();
 }
}
