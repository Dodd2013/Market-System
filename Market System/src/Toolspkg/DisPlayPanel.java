/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Toolspkg;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Doddc
 */
public class DisPlayPanel extends JPanel{
        public JLabel label;
        public JTextField textField;
        public String title;
        public final static int isSelect=1,isDis=0;
        public JComboBox comboBox;
        public DisPlayPanel(String title,int OP) {
            this.label = new JLabel(title);
            this.textField = new JTextField();
            this.title = title;
            textField.setPreferredSize(new Dimension(130, 30));
            textField.setEditable(false);
            textField.addFocusListener(new Toolspkg.SelectAllWhileGetFocusJTF());
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            
            this.add(label);
            if(OP==DisPlayPanel.isSelect){
                this.setPreferredSize(new Dimension(320, 45));
                String[] items={
                    ">",
                    "="
                };
                comboBox=new JComboBox(items);
                this.add(comboBox);
            }else{
                this.setPreferredSize(new Dimension(300, 45));
            }
            this.add(textField);
        }
    }