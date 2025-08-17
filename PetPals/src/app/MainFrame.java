package app;

//MainFrame.java
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
 private AppController appController;
 private CardLayout cardLayout;
 private JPanel mainPanel;
 
 private LoginPanel loginPanel;
 private SignupPanel signupPanel;
 private AdminDashboardPanel adminDashboardPanel;
 private UserDashboardPanel userDashboardPanel;
 
 public MainFrame() {
     this.appController = new AppController();
     initializeComponents();
     setupLayout();
     setupEventHandlers();
     
     // Start with login panel
     showLoginPanel();
     
     // Setup window properties
     setupWindow();
 }
 
 private void initializeComponents() {
     cardLayout = new CardLayout();
     mainPanel = new JPanel(cardLayout);
     UIStyleHelper.stylePanel(mainPanel);
     
     loginPanel = new LoginPanel(this, appController);
     signupPanel = new SignupPanel(this, appController);
     // Other panels will be initialized when needed
 }
 
 private void setupLayout() {
     setLayout(new BorderLayout());
     getContentPane().setBackground(UIStyleHelper.BACKGROUND_COLOR);
     
     add(mainPanel, BorderLayout.CENTER);
     
     mainPanel.add(loginPanel, "LOGIN");
     mainPanel.add(signupPanel, "SIGNUP");
 }
 
 private void setupEventHandlers() {
     // Event handlers are implemented in individual panels
 }
 
 private void setupWindow() {
     setTitle("PetPals - Pet Adoption & Donation System");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setSize(1000, 700);
     setLocationRelativeTo(null); // Center on screen
     setMinimumSize(new Dimension(800, 800));
     
     // Set application icon (if available)
     try {
         setIconImage(Toolkit.getDefaultToolkit().getImage("image/pet_bg.jpg"));
     } catch (Exception e) {
         // Icon not found, continue without it
     }
     
     setVisible(true);
 }
 
 public void showLoginPanel() {
     loginPanel.resetFields();
     cardLayout.show(mainPanel, "LOGIN");
     setTitle("PetPals - Login");
 }
 
 public void showSignupPanel() {
     signupPanel.resetFields();
     cardLayout.show(mainPanel, "SIGNUP");
     setTitle("PetPals - Create Account");
 }
 
 public void showAdminDashboard() {
     if (adminDashboardPanel == null) {
         adminDashboardPanel = new AdminDashboardPanel(this, appController);
         mainPanel.add(adminDashboardPanel, "ADMIN_DASHBOARD");
     } else {
         // Refresh data when returning to dashboard
         adminDashboardPanel.refreshData();
     }
     cardLayout.show(mainPanel, "ADMIN_DASHBOARD");
     setTitle("PetPals - Admin Dashboard");
 }
 
 public void showUserDashboard() {
     if (userDashboardPanel == null) {
         userDashboardPanel = new UserDashboardPanel(this, appController);
         mainPanel.add(userDashboardPanel, "USER_DASHBOARD");
     } else {
         // Refresh data when returning to dashboard
         userDashboardPanel.refreshData();
     }
     cardLayout.show(mainPanel, "USER_DASHBOARD");
     User currentUser = appController.getCurrentUser();
     setTitle("PetPals - Dashboard (" + (currentUser != null ? currentUser.getUsername() : "User") + ")");
 }
 
 public AppController getAppController() {
     return appController;
 }
 
 // Method to refresh dashboard panels when needed
 public void refreshDashboards() {
     if (adminDashboardPanel != null) {
         adminDashboardPanel.refreshData();
     }
     if (userDashboardPanel != null) {
         userDashboardPanel.refreshData();
     }
 }
}