/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainPkg;

import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import FirstPkg.EmpManagementPanel;
import FirstPkg.ItemDetailManagementPanel;
import FirstPkg.UserManagementPanel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Dodd-pc
 */
public class GetPanel{
    public static JPanel getPanel(int panlId){
        return getPanel(getPanelName(panlId));
    }
    public static JPanel getPanel(String panlName){
        switch(panlName){
            case "EMP Management":return new EmpManagementPanel();
            case "User Management":return new UserManagementPanel();
            case "ItemDetail Management":return new ItemDetailManagementPanel();
        }
       return null;
    }
    public static String getPanelName(int panelId){     //模块id获取模块名称
        String panelName=null;
        try {
            PreparedStatement pstmt= DataOnly.conData.con.prepareStatement("select * from ModelTB where Model_id=?");
            pstmt.setString(1,Integer.toString(panelId));
            ResultSet res=pstmt.executeQuery();
            res.next();
            panelName=res.getString("Model_Name");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, GetLanguageName.getName("getNameOfModule")+"\n"+ex.getMessage());
        }
        return panelName;
    }
    public static int getPanelId(String panelName){     //模块id获取模块名称
        int panelId=0;
        try {
            PreparedStatement pstmt= DataOnly.conData.con.prepareStatement("select * from ModelTB where Model_Name=?");
            pstmt.setString(1,panelName);
            ResultSet res=pstmt.executeQuery();
            res.next();
            panelId=res.getInt("Model_id");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, GetLanguageName.getName("getIDOfModule")+"\n"+ex.getMessage());
        }
        return panelId;
    }
    
}
