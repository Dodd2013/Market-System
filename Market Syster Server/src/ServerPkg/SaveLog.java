/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerPkg;

import DataReadPkg.DataOnly;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author Administrator
 */
public class SaveLog {

    public SaveLog(ServerMainFrame mainFrame) {
        File logFile=new File("log");
        if(!logFile.exists())logFile.mkdir();
        File file=new File("log/serverLog"+DataOnly.getNowTime().trim().replaceAll(":","").replaceAll("-","")+".txt");
        try {
            PrintWriter out=new PrintWriter(file);
            out.println(mainFrame.jTextArea.getText().replaceAll("\n","\r\n"));
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
