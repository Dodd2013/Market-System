/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FirstPkg;

import DataReadpkg.GetLanguageName;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author Doddc
 */
public class EmpManagementPanel extends JPanel{
    JPanel btnPanel,editPanel,tablePanel,upPanel;
    JButton newbtn,delbtn,searchbtn,updatabtn;
    JSeparator separator1;
    public EmpManagementPanel(){
        this.setLayout(new BorderLayout());
        btnPanel=new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel=new JPanel();
        tablePanel=new JPanel();
        upPanel=new JPanel();
        upPanel.setLayout(new BorderLayout());
        this.add(upPanel,BorderLayout.NORTH);
        this.add(tablePanel,BorderLayout.CENTER);
        upPanel.add(btnPanel,BorderLayout.NORTH);
        upPanel.add(editPanel,BorderLayout.CENTER);
        
        
        
        newbtn=new JButton(GetLanguageName.getName("newBtn"));
        btnPanel.add(newbtn);
        delbtn=new JButton(GetLanguageName.getName("deleteBtn"));
        btnPanel.add(delbtn);
        updatabtn=new JButton(GetLanguageName.getName("updataBtn"));
        btnPanel.add(updatabtn);
        searchbtn=new JButton(GetLanguageName.getName("searchBtn"));
        btnPanel.add(searchbtn);
        separator1=new JSeparator(JSeparator.HORIZONTAL);
    }
    public static void main(String[] args) {
        JFrame frame=new JFrame();
        frame.add(new  EmpManagementPanel(),BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
