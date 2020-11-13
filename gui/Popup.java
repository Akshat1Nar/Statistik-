package gui;

import base.Query;

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

    public Popup(int x,int y,MouseEvent e,float lat,float lng) {
        JMenuItem item;
        Query.GetQuery(lat,lng);

        for(int i=0;i<Query.ALL.length;i++){
            add(item = new JMenuItem((Query.ALL[i]+" "+Query.ALLVALUES[i]), Query.ALLICONS[i]));
        }
        addSeparator();
        if(Query.CITY==null){
            add(item = new JMenuItem("Statistics/"));
        }
        else{
            add(item = new JMenuItem("Statistics/"+Query.CITY));
            Query.CITY = null;
        }
        
      	setLabel("Statistics");
      	setBorder(new BevelBorder(BevelBorder.RAISED));
        show(e.getComponent(),x,y);
        // setVisible(true);
    }

}



