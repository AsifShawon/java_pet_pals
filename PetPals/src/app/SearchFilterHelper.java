package app;

//SearchFilterHelper.java
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SearchFilterHelper {
 
 public static <T> void addSearchFilter(JTextField searchField, List<T> allItems, 
                                       Predicate<T> filterPredicate, Runnable updateCallback) {
     searchField.getDocument().addDocumentListener(new DocumentListener() {
         @Override
         public void insertUpdate(DocumentEvent e) {
             updateCallback.run();
         }
         
         @Override
         public void removeUpdate(DocumentEvent e) {
             updateCallback.run();
         }
         
         @Override
         public void changedUpdate(DocumentEvent e) {
             updateCallback.run();
         }
     });
 }
 
 public static <T> List<T> filterItems(List<T> items, String searchText, Predicate<T> filterPredicate) {
     if (searchText == null || searchText.trim().isEmpty()) {
         return new ArrayList<>(items);
     }
     
     List<T> filtered = new ArrayList<>();
     for (T item : items) {
         if (filterPredicate.test(item)) {
             filtered.add(item);
         }
     }
     return filtered;
 }
}