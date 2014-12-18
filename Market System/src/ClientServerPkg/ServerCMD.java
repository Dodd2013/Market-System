/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientServerPkg;

import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import RemoteControlPkg.RCServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dodd-pc
 */
public class ServerCMD {
    public static void excCMD(String cmdString){
        System.out.println(cmdString);
        if(cmdString.equals("CMD:exit")){
            JOptionPane.showMessageDialog(null, GetLanguageName.getName("serverConBreakOff"));
            System.exit(0);
        }
        if(cmdString.startsWith("MSG:")){
            JOptionPane.showMessageDialog(null, cmdString.substring(4));
        }
        if(cmdString.startsWith("UserMsg:")){
            DataOnly.msgCount++;
        }
        if(cmdString.equals("RemoteControl:START")){
            DataOnly.rcs=new RCServer();
            try {
                DataOnly.rcs.startServer(18080);
                DataOnly.remoteLinkOrNot=true;
            } catch (Exception ex) {
                Logger.getLogger(ServerCMD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(cmdString.equals("RemoteControl:EXIT")){
                DataOnly.rcs.stopServer();
                DataOnly.remoteLinkOrNot=false;
        }
    }
}
