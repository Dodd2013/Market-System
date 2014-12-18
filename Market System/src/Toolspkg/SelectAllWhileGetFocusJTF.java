/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Toolspkg;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class SelectAllWhileGetFocusJTF implements FocusListener{

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getComponent() instanceof JTextField){
           JTextField f=(JTextField)e.getComponent();
           f.selectAll();
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
    
}
