package OtherClassPkg;

import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import javax.swing.JDialog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class AboutDialog extends JDialog{

    public AboutDialog() {
        super(DataOnly.mainFrame.maF, GetLanguageName.getName("about"),true);
        setLocation(DataOnly.ScreenSize.width/3, DataOnly.ScreenSize.height/5);
        setSize(500, 400);
        this.setVisible(true);
    }
    
}
