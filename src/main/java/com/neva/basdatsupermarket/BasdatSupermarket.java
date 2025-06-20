package com.neva.basdatsupermarket;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class BasdatSupermarket {

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        }
        catch(UnsupportedLookAndFeelException ex){
            System.out.println("Theme cannot be loaded");
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginPage().setVisible(true);
            }
        });
    }
}
