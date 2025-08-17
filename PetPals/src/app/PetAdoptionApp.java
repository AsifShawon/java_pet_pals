package app;

//PetAdoptionApp.java
import javax.swing.SwingUtilities;

public class PetAdoptionApp {
 public static void main(String[] args) {
     // Setup look and feel
     UIStyleHelper.setupLookAndFeel();
     
     // Create and show the main frame
     SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
             new MainFrame();
         }
     });
 }
}
