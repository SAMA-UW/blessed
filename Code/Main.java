package Code;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static String appVersion = ".Version3";
    public static void main(String[] args) {
        EventQueue.invokeLater(()->
                {
                    var mainWindow = new GUIHandler();
                    mainWindow.setTitle("Information Organizer Application" + " " + appVersion);
                    mainWindow.setVisible(true);
         }
        );
    }
}
