package app;

//IconHelper.java
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class IconHelper {
    
    private static final int ICON_SIZE = 16;
    private static final Color ICON_COLOR = UIStyleHelper.PRIMARY_COLOR;
    private static final Color ICON_ACCENT = UIStyleHelper.ACCENT_COLOR;
    
    /**
     * Creates a statistics/chart icon
     */
    public static ImageIcon createStatisticsIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        g2d.setStroke(new BasicStroke(2));
        
        // Draw chart bars
        g2d.fillRect(2, 10, 3, 4);
        g2d.fillRect(6, 8, 3, 6);
        g2d.fillRect(10, 6, 3, 8);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a users/people icon
     */
    public static ImageIcon createUsersIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw first person (head + body)
        g2d.fillOval(2, 2, 5, 5);
        g2d.fillRoundRect(1, 7, 7, 6, 2, 2);
        
        // Draw second person (slightly offset)
        g2d.setColor(ICON_ACCENT);
        g2d.fillOval(8, 2, 5, 5);
        g2d.fillRoundRect(7, 7, 7, 6, 2, 2);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a posts/document icon
     */
    public static ImageIcon createPostsIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw document outline
        g2d.fillRoundRect(3, 2, 10, 12, 2, 2);
        
        // Draw lines to represent text
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(5, 5, 11, 5);
        g2d.drawLine(5, 7, 11, 7);
        g2d.drawLine(5, 9, 9, 9);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a requests/clipboard icon
     */
    public static ImageIcon createRequestsIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw clipboard outline
        g2d.fillRoundRect(2, 3, 12, 11, 2, 2);
        
        // Draw clip at top
        g2d.setColor(ICON_ACCENT);
        g2d.fillRoundRect(6, 1, 4, 3, 1, 1);
        
        // Draw checkmarks/lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(4, 7, 6, 9);
        g2d.drawLine(6, 9, 10, 5);
        g2d.drawLine(4, 11, 12, 11);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a home/house icon
     */
    public static ImageIcon createHomeIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw house outline
        int[] xPoints = {8, 3, 13};
        int[] yPoints = {2, 8, 8};
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.fillRect(5, 8, 6, 6);
        
        // Draw door
        g2d.setColor(Color.WHITE);
        g2d.fillRect(7, 10, 2, 4);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a notification/bell icon
     */
    public static ImageIcon createNotificationIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw bell shape
        g2d.fillOval(5, 3, 6, 8);
        g2d.fillRect(4, 10, 8, 2);
        
        // Draw bell top
        g2d.fillRect(7, 2, 2, 2);
        
        // Draw notification dot
        g2d.setColor(UIStyleHelper.DANGER_COLOR);
        g2d.fillOval(11, 3, 4, 4);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a profile/user icon
     */
    public static ImageIcon createProfileIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw head
        g2d.fillOval(5, 2, 6, 6);
        
        // Draw body
        g2d.fillRoundRect(3, 8, 10, 6, 3, 3);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a logout/exit icon
     */
    public static ImageIcon createLogoutIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(UIStyleHelper.DANGER_COLOR);
        g2d.setStroke(new BasicStroke(2));
        
        // Draw arrow pointing right (exit)
        g2d.drawLine(2, 8, 10, 8);
        g2d.drawLine(8, 6, 10, 8);
        g2d.drawLine(8, 10, 10, 8);
        
        // Draw door frame
        g2d.drawLine(12, 3, 12, 13);
        g2d.drawLine(11, 3, 14, 3);
        g2d.drawLine(11, 13, 14, 13);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a refresh icon
     */
    public static ImageIcon createRefreshIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(UIStyleHelper.SUCCESS_COLOR);
        g2d.setStroke(new BasicStroke(2));
        
        // Draw circular arrow
        g2d.drawArc(3, 3, 10, 10, 45, 270);
        
        // Draw arrow head
        g2d.drawLine(11, 3, 13, 3);
        g2d.drawLine(11, 3, 11, 5);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a delete/trash icon
     */
    public static ImageIcon createDeleteIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(UIStyleHelper.DANGER_COLOR);
        
        // Draw trash can
        g2d.fillRoundRect(4, 6, 8, 8, 2, 2);
        
        // Draw lid
        g2d.fillRect(3, 5, 10, 2);
        g2d.fillRect(6, 3, 4, 2);
        
        // Draw lines on can
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(6, 8, 6, 12);
        g2d.drawLine(8, 8, 8, 12);
        g2d.drawLine(10, 8, 10, 12);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a create/plus icon
     */
    public static ImageIcon createCreateIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(UIStyleHelper.SUCCESS_COLOR);
        g2d.setStroke(new BasicStroke(2));
        
        // Draw plus sign
        g2d.drawLine(8, 4, 8, 12);
        g2d.drawLine(4, 8, 12, 8);
        
        // Draw circle around it
        g2d.drawOval(2, 2, 12, 12);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a view/eye icon
     */
    public static ImageIcon createViewIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw eye shape
        g2d.fillOval(2, 6, 12, 4);
        
        // Draw pupil
        g2d.setColor(Color.WHITE);
        g2d.fillOval(6, 7, 4, 2);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(7, 7, 2, 2);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates an update/edit icon
     */
    public static ImageIcon createUpdateIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(ICON_COLOR);
        
        // Draw pencil shape
        g2d.fillPolygon(new int[]{10, 12, 14, 12}, new int[]{2, 2, 4, 4}, 4);
        g2d.fillPolygon(new int[]{9, 11, 4, 2}, new int[]{3, 5, 12, 10}, 4);
        
        // Draw edit lines
        g2d.setColor(UIStyleHelper.ACCENT_COLOR);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(3, 11, 1, 13);
        g2d.drawLine(1, 13, 3, 13);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    /**
     * Creates a send/arrow icon
     */
    public static ImageIcon createSendIcon() {
        BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(UIStyleHelper.PRIMARY_COLOR);
        g2d.setStroke(new BasicStroke(2));
        
        // Draw arrow
        g2d.drawLine(2, 8, 12, 8);
        g2d.drawLine(9, 5, 12, 8);
        g2d.drawLine(9, 11, 12, 8);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
}
