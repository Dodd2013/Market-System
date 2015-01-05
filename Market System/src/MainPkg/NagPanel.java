/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPkg;

import DataReadpkg.DataOnly;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author QDU-01
 */
public class NagPanel extends JPanel {

    ArrayList<JButton> btnList;
    String groupName ="";

    public  NagPanel() {
        btnList=new ArrayList<>();
        for (int i = 0; i < DataOnly.jutisArrayList.size(); i++) {
            String groupNameTemp=DataOnly.jutisArrayList.get(i).moduleGoup;
            if (!groupName.equals(groupNameTemp)) {
                groupName=groupNameTemp;
                btnList.add(new MyButton(groupNameTemp));
            }
            System.out.println(i+":"+DataOnly.jutisArrayList.get(i).Name);
        }
        this.setLayout(new FlowLayout(FlowLayout.CENTER,35,35));
        for(int i=0;i<btnList.size();i++){
            this.add(btnList.get(i));
        }
        
    }
    class MyButton extends JButton{
        public MyButton(String title){
            super(title);
            this.setPreferredSize(new Dimension(200, 200));
        }
    }
}
