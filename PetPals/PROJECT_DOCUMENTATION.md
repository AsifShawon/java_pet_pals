# PetPals Project Documentation

## Project Overview
PetPals is a Java-based desktop application designed to facilitate pet adoption. It provides a platform for users to post about pets they want to donate or adopt, manage their posts and requests, and interact with other users. The application is built using Java Swing for the user interface and follows Object-Oriented Programming (OOP) principles to ensure modularity, reusability, and maintainability.

## Features
- **User Authentication**: Login and signup functionality.
- **Post Management**: Users can create, view, and delete posts about pets for adoption or request pets they are interested in.
- **Request Management**: Users can view and manage their adoption requests.
- **Notifications**: Users receive notifications about updates related to their posts and requests.
- **Profile Management**: Users can update their contact information and password.

## OOP Concepts Used

### 1. **Encapsulation**
Encapsulation is achieved by using private fields and providing public getter and setter methods to access and modify them. For example, the `User` class encapsulates user details:

```java
public class User {
    private String id;
    private String username;
    private String contact;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
```

### 2. **Inheritance**
Inheritance is used to extend the functionality of existing classes. For example, `UserDashboardPanel` extends `JPanel` to create a custom user interface panel:

```java
public class UserDashboardPanel extends JPanel {
    // Custom components and functionality
}
```

### 3. **Polymorphism**
Polymorphism is demonstrated through method overriding. For instance, the `DefaultTableModel` class is overridden to make table cells non-editable:

```java
postsTableModel = new DefaultTableModel(new String[]{"ID", "Author", "Type", "Title", "Description", "Date"}, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
```

### 4. **Abstraction**
Abstraction is achieved by hiding implementation details and exposing only the necessary functionality. For example, the `AppController` class provides high-level methods like `getAllPosts()` and `createPost()` without exposing the underlying data handling logic.

### 5. **Composition**
Composition is used to build complex objects from simpler ones. For example, the `UserDashboardPanel` class composes multiple components like `JTable`, `JButton`, and `JTabbedPane` to create a user-friendly interface.

### 6. **Interfaces**
Although not explicitly mentioned in the provided code, interfaces can be used to define contracts for certain functionalities, ensuring consistency across different implementations.

## Class Descriptions

### 1. **User**
Represents a user of the application. Encapsulates user details like ID, username, and contact information.

### 2. **Post**
Represents a post about a pet for adoption. Contains details like post ID, author ID, type (donate/need), title, description, and timestamp.

### 3. **Request**
Represents a request made by a user for a specific post. Contains details like request ID, post ID, status, and the user it is directed to.

### 4. **AppController**
Acts as the central controller for the application. Manages data retrieval, user authentication, and business logic.

### 5. **UserDashboardPanel**
The main user interface panel for logged-in users. Provides tabs for managing posts, requests, notifications, and profile settings.

### 6. **Notification**
Represents a notification sent to a user. Contains details like notification ID, type, title, message, time, and status.

## Design Patterns

### 1. **MVC (Model-View-Controller)**
The application follows the MVC pattern:
- **Model**: Classes like `User`, `Post`, and `Request` represent the data.
- **View**: Classes like `UserDashboardPanel` handle the user interface.
- **Controller**: The `AppController` class manages the interaction between the model and the view.

### 2. **Singleton**
The `AppController` class may use the Singleton pattern to ensure a single instance manages the application's logic (not explicitly shown in the provided code).

## How to Run the Project
1. **Prerequisites**:
   - Java Development Kit (JDK) installed.
   - An IDE like IntelliJ IDEA or Eclipse, or a text editor like VS Code.

2. **Steps**:
   - Clone the repository or download the source code.
   - Open the project in your IDE.
   - Compile the project.
   - Run the `PetAdoptionApp` class to start the application.

3. **Note**:
   - Ensure the `pet_adoption_data.dat` file is present in the correct directory for data persistence.

---

This documentation provides a comprehensive overview of the project, its features, and the OOP principles applied. It serves as a guide for developers and contributors to understand the application's structure and functionality.
