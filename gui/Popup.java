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


public class Popup extends JPopupMenu {

    public Popup(int x,int y,MouseEvent e) {
        JMenuItem item;

        add(item = new JMenuItem("Left", new ImageIcon("1.gif")));
        add(item = new JMenuItem("Center", new ImageIcon("2.gif")));
        add(item = new JMenuItem("Right", new ImageIcon("3.gif")));
        add(item = new JMenuItem("Full", new ImageIcon("4.gif")));
        addSeparator();
        add(item = new JMenuItem("---Statistics---"));
        
      	setLabel("Statistics");
      	setBorder(new BevelBorder(BevelBorder.RAISED));
        show(e.getComponent(),x,y);
        // setVisible(true);
    }

}



