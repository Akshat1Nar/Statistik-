package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class Popup extends JPanel {

    public JPopupMenu popup;
  
    public Popup() {
        popup = new JPopupMenu();
        JMenuItem item;

        popup.add(item = new JMenuItem("Left", new ImageIcon("1.gif")));
        popup.add(item = new JMenuItem("Center", new ImageIcon("2.gif")));
        popup.add(item = new JMenuItem("Right", new ImageIcon("3.gif")));
        popup.add(item = new JMenuItem("Full", new ImageIcon("4.gif")));
        popup.addSeparator();
        popup.add(item = new JMenuItem("Settings . . ."));
        
      	popup.setLabel("Statistics");
      	popup.setBorder(new BevelBorder(BevelBorder.RAISED));
        
    }
  
    // An inner class to check whether mouse events are the popup trigger
    // class MousePopupListener extends MouseAdapter {
    //     public void mousePressed(MouseEvent e) {
    //       checkPopup(e);
    //     }
    
    //       public void mouseClicked(MouseEvent e) {
    //         checkPopup(e);
    //       }
    
    //       public void mouseReleased(MouseEvent e) {
    //         checkPopup(e);
    //       }
    
    //       private void checkPopup(MouseEvent e) {
    //         if (e.isPopupTrigger()) {
    //           popup.show(Popup.this, e.getX(), e.getY());
    //         }
    //     }
    // }
  
    // // An inner class to show when popup events occur
    // class PopupPrintListener implements PopupMenuListener {
    //     public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    //       System.out.println("Popup menu will be visible!");
    //     }
    
    //       public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    //         System.out.println("Popup menu will be invisible!");
    //       }
    
    //       public void popupMenuCanceled(PopupMenuEvent e) {
    //         System.out.println("Popup menu is hidden!");
    //     }
    // }
  
    // public static void main(String s[]) {
    //     JFrame frame = new JFrame("Popup Menu Example");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setContentPane(new Popup());
    //     frame.setSize(300, 300);
    //     frame.setVisible(true);
    // }
}



