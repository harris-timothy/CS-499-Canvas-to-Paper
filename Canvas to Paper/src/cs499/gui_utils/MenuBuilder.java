package cs499.gui_utils;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuBuilder {
    
    public JMenu buildMenu(String title, int mnemonic){
        JMenu menu = new JMenu(title);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    public JMenuItem buildMenuItem(String title, int mnemonic, JMenu supermenu){
        JMenuItem menu_item = new JMenuItem(title, mnemonic);
        supermenu.add(menu_item);
        return menu_item;
    }
}
