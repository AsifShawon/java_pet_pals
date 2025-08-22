package app;

//SignupPanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupPanel extends JPanel {
 private MainFrame mainFrame;
 private AppController appController;
 
 private JTextField usernameField;
 private JPasswordField passwordField;
 private JPasswordField confirmPasswordField;
 private JTextField contactField;
 private JButton signupButton;
 private JButton backButton;
 private JLabel titleLabel;
 private JLabel subtitleLabel;
 
 public SignupPanel(MainFrame mainFrame, AppController appController) {
     this.mainFrame = mainFrame;
     this.appController = appController;
     
     initializeComponents();
     initComponents();
     setupEventHandlers();
     styleComponents();
 }
 
 private void initComponents() {
     setLayout(new BorderLayout());
     
     // Create background panel with image
     UIStyleHelper.BackgroundImagePanel backgroundPanel = 
         new UIStyleHelper.BackgroundImagePanel("E:\\JAVA\\PetPals\\image\\pet_bg.jpg");
     backgroundPanel.setOpacity(0.2f); // 20% opacity for subtle effect
     backgroundPanel.setLayout(new BorderLayout());
     
     JPanel mainContainer = new JPanel(new GridBagLayout());
     mainContainer.setOpaque(false); // Make transparent to show background
     UIStyleHelper.stylePanelWithBackground(mainContainer, null);
     
     JPanel signupCard = new JPanel();
     signupCard.setLayout(new BoxLayout(signupCard, BoxLayout.Y_AXIS));
     UIStyleHelper.styleCardPanel(signupCard);
     signupCard.setPreferredSize(new Dimension(650, 700));
     
     JPanel headerPanel = new JPanel();
     headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
     headerPanel.setBackground(UIStyleHelper.CARD_COLOR);
     headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
     
     titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     
     headerPanel.add(titleLabel);
     headerPanel.add(Box.createVerticalStrut(10));
     headerPanel.add(subtitleLabel);
     
     JPanel formPanel = new JPanel(new GridBagLayout());
     formPanel.setBackground(UIStyleHelper.CARD_COLOR);
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(12, 20, 12, 20);
     
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
     
     // Confirm Password field
     gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
     JLabel confirmPasswordLabel = new JLabel("Confirm Password");
     formPanel.add(confirmPasswordLabel, gbc);
     
     gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL;
     formPanel.add(confirmPasswordField, gbc);
     
     // Contact field
     gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
     JLabel contactLabel = new JLabel("Contact (Email/Phone)");
     formPanel.add(contactLabel, gbc);
     
     gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL;
     formPanel.add(contactField, gbc);
     
     // Button section
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
     buttonPanel.setBackground(UIStyleHelper.CARD_COLOR);
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     
     signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
     backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
     
     buttonPanel.add(signupButton);
     buttonPanel.add(Box.createVerticalStrut(15));
     buttonPanel.add(backButton);
     
     // Assemble the card
     signupCard.add(headerPanel);
     signupCard.add(formPanel);
     signupCard.add(buttonPanel);
     
     // Center the card
     GridBagConstraints mainGbc = new GridBagConstraints();
     mainContainer.add(signupCard, mainGbc);
     
     // Add main container to background panel
     backgroundPanel.add(mainContainer, BorderLayout.CENTER);
     
     add(backgroundPanel, BorderLayout.CENTER);
 }
 
 private void initializeComponents() {
     usernameField = new JTextField(20);
     passwordField = new JPasswordField(20);
     confirmPasswordField = new JPasswordField(20);
     contactField = new JTextField(20);
     signupButton = new JButton("Create Account");
     backButton = new JButton("Back to Login");
     
     titleLabel = new JLabel("Create Account");
     subtitleLabel = new JLabel("Join the PetPals community");
 }
 
 private void setupEventHandlers() {
     signupButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             handleSignup();
         }
     });
     
     backButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             mainFrame.showLoginPanel();
         }
     });
     
     // Add Enter key navigation
     usernameField.addActionListener(e -> passwordField.requestFocus());
     passwordField.addActionListener(e -> confirmPasswordField.requestFocus());
     confirmPasswordField.addActionListener(e -> contactField.requestFocus());
     contactField.addActionListener(e -> handleSignup());
 }
 
 private void styleComponents() {
     UIStyleHelper.styleLabel(titleLabel, UIStyleHelper.LabelStyle.HEADER);
     titleLabel.setForeground(UIStyleHelper.PRIMARY_COLOR);
     
     UIStyleHelper.styleLabel(subtitleLabel, UIStyleHelper.LabelStyle.SECONDARY);
     
     UIStyleHelper.styleInputField(usernameField);
     UIStyleHelper.styleInputField(passwordField);
     UIStyleHelper.styleInputField(confirmPasswordField);
     UIStyleHelper.styleInputField(contactField);
     
     UIStyleHelper.styleSuccessButton(signupButton);
     UIStyleHelper.styleSecondaryButton(backButton);
     
     // Set placeholder text
     usernameField.setToolTipText("Choose a unique username");
     passwordField.setToolTipText("Create a strong password");
     confirmPasswordField.setToolTipText("Re-enter your password");
     contactField.setToolTipText("Enter your email or phone number");
 }
 
 private void handleSignup() {
     String username = usernameField.getText().trim();
     String password = new String(passwordField.getPassword());
     String confirmPassword = new String(confirmPasswordField.getPassword());
     String contact = contactField.getText().trim();
     
     // Validation
     if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || contact.isEmpty()) {
         showErrorDialog("Please fill in all fields.");
         return;
     }
     
     if (username.length() < 3) {
         showErrorDialog("Username must be at least 3 characters long.");
         usernameField.requestFocus();
         return;
     }
     
     if (password.length() < 3) {
         showErrorDialog("Password must be at least 3 characters long.");
         passwordField.requestFocus();
         return;
     }
     
     if (!password.equals(confirmPassword)) {
         showErrorDialog("Passwords do not match.");
         confirmPasswordField.selectAll();
         confirmPasswordField.requestFocus();
         return;
     }
     
     if (!isValidContact(contact)) {
         showErrorDialog("Please enter a valid email address or phone number.");
         contactField.requestFocus();
         return;
     }
     
     // Show loading state
     signupButton.setText("Creating Account...");
     signupButton.setEnabled(false);
     
     // Perform signup
     SwingUtilities.invokeLater(() -> {
         try {
             if (appController.signup(username, password, contact)) {
                 NotificationUtil.showNotification("Account created successfully!", mainFrame);
                 clearFields();
                 mainFrame.showLoginPanel();
             } else {
                 showErrorDialog("Username already exists. Please choose another username.");
                 usernameField.selectAll();
                 usernameField.requestFocus();
             }
         } finally {
             // Reset button state
             signupButton.setText("Create Account");
             signupButton.setEnabled(true);
         }
     });
 }
 
 private boolean isValidContact(String contact) {
     // Simple validation for email or phone
     return contact.contains("@") && contact.contains(".") || 
            contact.matches("\\d{10,}"); // At least 10 digits for phone
 }
 
 private void showErrorDialog(String message) {
     JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
 }
 
 private void clearFields() {
     usernameField.setText("");
     passwordField.setText("");
     confirmPasswordField.setText("");
     contactField.setText("");
 }
 
 public void resetFields() {
     clearFields();
     usernameField.requestFocus();
 }
}