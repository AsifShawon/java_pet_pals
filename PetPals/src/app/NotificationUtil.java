package app;

//NotificationUtil.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationUtil {
 
 public static void showNotification(String message, JFrame parentFrame) {
     JWindow notificationWindow = new JWindow(parentFrame);
     JLabel messageLabel = new JLabel("  " + message + "  ");
     
     messageLabel.setOpaque(true);
     messageLabel.setBackground(new Color(50, 50, 50, 200));
     messageLabel.setForeground(Color.WHITE);
     messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
     
     notificationWindow.add(messageLabel);
     notificationWindow.pack();
     
     // Position at top-right of parent frame
     Point location = parentFrame.getLocation();
     Dimension frameSize = parentFrame.getSize();
     Dimension windowSize = notificationWindow.getSize();
     
     int x = location.x + frameSize.width - windowSize.width - 20;
     int y = location.y + 20;
     
     notificationWindow.setLocation(x, y);
     notificationWindow.setVisible(true);
     
     // Auto-hide after 3 seconds
     Timer timer = new Timer(3000, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             notificationWindow.dispose();
         }
     });
     timer.setRepeats(false);
     timer.start();
 }
}