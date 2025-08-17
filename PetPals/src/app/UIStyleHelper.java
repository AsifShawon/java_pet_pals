package app;

//UIStyleHelper.java
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class UIStyleHelper {
 
 // Modern color scheme
 public static final Color PRIMARY_COLOR = new Color(52, 152, 219);        // Bright Blue
 public static final Color PRIMARY_DARK = new Color(41, 128, 185);         // Darker Blue
 public static final Color SECONDARY_COLOR = new Color(155, 89, 182);      // Purple
 public static final Color ACCENT_COLOR = new Color(230, 126, 34);         // Orange
 public static final Color SUCCESS_COLOR = new Color(39, 174, 96);         // Green
 public static final Color DANGER_COLOR = new Color(231, 76, 60);          // Red
 public static final Color WARNING_COLOR = new Color(241, 196, 15);        // Yellow
 public static final Color BACKGROUND_COLOR = new Color(248, 249, 250);    // Light Gray
 public static final Color CARD_COLOR = Color.WHITE;                       // White
 public static final Color TEXT_PRIMARY = new Color(33, 37, 41);           // Dark Gray
 public static final Color TEXT_SECONDARY = new Color(108, 117, 125);      // Medium Gray
 public static final Color BORDER_COLOR = new Color(222, 226, 230);        // Light Border
 
 public static void setupLookAndFeel() {
     try {
         // Set system look and feel for better integration
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         
         // Customize global UI properties
         UIManager.put("Button.font", getStandardFont());
         UIManager.put("Label.font", getStandardFont());
         UIManager.put("TextField.font", getStandardFont());
         UIManager.put("Table.font", getStandardFont());
         UIManager.put("TabbedPane.font", getStandardFont());
         UIManager.put("TabbedPane.selectedBackground", PRIMARY_COLOR);
         UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
         
         // Table styling
         UIManager.put("Table.selectionBackground", PRIMARY_COLOR);
         UIManager.put("Table.selectionForeground", Color.WHITE);
         UIManager.put("Table.gridColor", BORDER_COLOR);
         UIManager.put("Table.background", CARD_COLOR);
         
     } catch (Exception e) {
         try {
             UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
         } catch (Exception ex) {
             System.err.println("Failed to set look and feel: " + ex.getMessage());
         }
     }
 }
 
 public static void styleButton(JButton button) {
     button.setFocusPainted(false);
     button.setBorderPainted(false);
     button.setContentAreaFilled(true);
     button.setBackground(PRIMARY_COLOR);
     button.setForeground(Color.WHITE);
     button.setFont(getStandardFont().deriveFont(Font.BOLD));
     button.setCursor(new Cursor(Cursor.HAND_CURSOR));
     button.setBorder(new EmptyBorder(12, 24, 12, 24));
     button.setPreferredSize(new Dimension(button.getPreferredSize().width, 40));
     
     // Add rounded corners and hover effect
     button.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
             button.setBackground(PRIMARY_DARK);
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
             button.setBackground(PRIMARY_COLOR);
         }
     });
 }
 
 public static void stylePrimaryButton(JButton button) {
     styleButton(button);
     button.setBackground(PRIMARY_COLOR);
     button.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
             button.setBackground(PRIMARY_DARK);
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
             button.setBackground(PRIMARY_COLOR);
         }
     });
 }
 
 public static void styleSecondaryButton(JButton button) {
     styleButton(button);
     button.setBackground(SECONDARY_COLOR);
     button.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
             button.setBackground(SECONDARY_COLOR.darker());
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
             button.setBackground(SECONDARY_COLOR);
         }
     });
 }
 
 public static void styleDangerButton(JButton button) {
     styleButton(button);
     button.setBackground(DANGER_COLOR);
     button.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
             button.setBackground(DANGER_COLOR.darker());
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
             button.setBackground(DANGER_COLOR);
         }
     });
 }
 
 public static void styleSuccessButton(JButton button) {
     styleButton(button);
     button.setBackground(SUCCESS_COLOR);
     button.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
             button.setBackground(SUCCESS_COLOR.darker());
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
             button.setBackground(SUCCESS_COLOR);
         }
     });
 }
 
 public static void styleWarningButton(JButton button) {
     styleButton(button);
     button.setBackground(WARNING_COLOR);
     button.setForeground(TEXT_PRIMARY);
     button.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseEntered(java.awt.event.MouseEvent evt) {
             button.setBackground(WARNING_COLOR.darker());
         }
         public void mouseExited(java.awt.event.MouseEvent evt) {
             button.setBackground(WARNING_COLOR);
         }
     });
 }
 
 public static void styleInputField(JComponent component) {
     component.setBorder(new CompoundBorder(
         new LineBorder(BORDER_COLOR, 1, true),
         new EmptyBorder(12, 16, 12, 16)
     ));
     component.setFont(getStandardFont());
     component.setBackground(CARD_COLOR);
     
     if (component instanceof JTextField || component instanceof JPasswordField) {
         component.setPreferredSize(new Dimension(250, 45));
         
         // Add focus effect
         component.addFocusListener(new java.awt.event.FocusAdapter() {
             public void focusGained(java.awt.event.FocusEvent evt) {
                 component.setBorder(new CompoundBorder(
                     new LineBorder(PRIMARY_COLOR, 2, true),
                     new EmptyBorder(11, 15, 11, 15)
                 ));
             }
             public void focusLost(java.awt.event.FocusEvent evt) {
                 component.setBorder(new CompoundBorder(
                     new LineBorder(BORDER_COLOR, 1, true),
                     new EmptyBorder(12, 16, 12, 16)
                 ));
             }
         });
     }
 }
 
 public static void styleTable(JTable table) {
     table.setFont(getStandardFont());
     table.setRowHeight(35);
     table.setSelectionBackground(PRIMARY_COLOR);
     table.setSelectionForeground(Color.WHITE);
     table.setGridColor(BORDER_COLOR);
     table.setBackground(CARD_COLOR);
     table.setShowGrid(true);
     table.setIntercellSpacing(new Dimension(1, 1));
     
     // Style table header
     table.getTableHeader().setFont(getStandardFont().deriveFont(Font.BOLD));
     table.getTableHeader().setBackground(BACKGROUND_COLOR);
     table.getTableHeader().setForeground(TEXT_PRIMARY);
     table.getTableHeader().setBorder(new LineBorder(BORDER_COLOR));
     table.getTableHeader().setPreferredSize(new Dimension(0, 40));
     
     // Alternate row colors
     table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
         @Override
         public Component getTableCellRendererComponent(JTable table, Object value,
                 boolean isSelected, boolean hasFocus, int row, int column) {
             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             if (!isSelected) {
                 c.setBackground(row % 2 == 0 ? CARD_COLOR : BACKGROUND_COLOR);
             }
             return c;
         }
     });
 }
 
 public static void stylePanel(JPanel panel) {
     panel.setBackground(BACKGROUND_COLOR);
 }
 
 public static void styleCardPanel(JPanel panel) {
     panel.setBackground(CARD_COLOR);
     panel.setBorder(new CompoundBorder(
         new LineBorder(BORDER_COLOR, 1, true),
         new EmptyBorder(20, 20, 20, 20)
     ));
 }
 
 public static void styleTabbedPane(JTabbedPane tabbedPane) {
     tabbedPane.setFont(getStandardFont().deriveFont(Font.BOLD));
     tabbedPane.setBackground(BACKGROUND_COLOR);
     tabbedPane.setForeground(TEXT_PRIMARY);
 }
 
 public static void styleLabel(JLabel label, LabelStyle style) {
     label.setForeground(TEXT_PRIMARY);
     switch (style) {
         case HEADER:
             label.setFont(getHeaderFont());
             break;
         case SUBHEADER:
             label.setFont(getSubheaderFont());
             break;
         case TITLE:
             label.setFont(getTitleFont());
             break;
         case SECONDARY:
             label.setForeground(TEXT_SECONDARY);
             label.setFont(getStandardFont());
             break;
         default:
             label.setFont(getStandardFont());
             break;
     }
 }
 
 public static Font getTitleFont() {
     return new Font("Segoe UI", Font.BOLD, 24);
 }
 
 public static Font getHeaderFont() {
     return new Font("Segoe UI", Font.BOLD, 20);
 }
 
 public static Font getSubheaderFont() {
     return new Font("Segoe UI", Font.BOLD, 16);
 }
 
 public static Font getStandardFont() {
     return new Font("Segoe UI", Font.PLAIN, 14);
 }
 
 public static Font getLargeFont() {
     return new Font("Segoe UI", Font.PLAIN, 16);
 }
 
 public static Font getSmallFont() {
     return new Font("Segoe UI", Font.PLAIN, 12);
 }
 
 public enum LabelStyle {
     TITLE, HEADER, SUBHEADER, STANDARD, SECONDARY
 }
 
 // Custom rounded border
 public static class RoundedBorder extends javax.swing.border.AbstractBorder {
     private Color color;
     private int radius;
     
     public RoundedBorder(Color color, int radius) {
         this.color = color;
         this.radius = radius;
     }
     
     @Override
     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
         Graphics2D g2 = (Graphics2D) g.create();
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.setColor(color);
         g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
         g2.dispose();
     }
     
     @Override
     public Insets getBorderInsets(Component c) {
         return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
     }
 }
}