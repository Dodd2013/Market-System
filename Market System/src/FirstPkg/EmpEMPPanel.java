/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FirstPkg;

import DataReadpkg.GetLanguageName;
import Toolspkg.DisPlayPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Doddc
 */
public class EmpEMPPanel extends JPanel{
    JPanel btnPanel,editPanel,upPanel;
    JScrollPane tablePanel;
    JButton searchbtn;
    JSeparator separator1,separator2;
    Vector<DisPlayPanel> disPalyPanelVector;
    String[] itemStrings;
    public JTable table;
    public DefaultTableModel tableModel;
    public TableModel model;
    public EmpEMPPanel(){
        disPalyPanelVector=new Vector<>();
        this.setLayout(new BorderLayout());
        btnPanel=new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel=new JPanel();
        editPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel.setPreferredSize(new Dimension(150, 150));
        tablePanel=new JScrollPane();
        upPanel=new JPanel();
        upPanel.setLayout(new BorderLayout());
        this.add(upPanel,BorderLayout.NORTH);
        this.add(tablePanel);
        upPanel.add(btnPanel,BorderLayout.NORTH);
        separator1=new JSeparator(JSeparator.HORIZONTAL);
        upPanel.add(separator1);
        upPanel.add(editPanel,BorderLayout.SOUTH);
        itemStrings=new String[]{
            GetLanguageName.getName("empId"),
            GetLanguageName.getName("empNameCN"),
            GetLanguageName.getName("empNameEN"),
            GetLanguageName.getName("empGender"),
            GetLanguageName.getName("empDob"),
            GetLanguageName.getName("empMobile"),
            GetLanguageName.getName("empEmail"),
            GetLanguageName.getName("empAddress"),
            GetLanguageName.getName("empJoinDate")
        };
        for(int i=0;i<9;i++){
            disPalyPanelVector.add(new DisPlayPanel(itemStrings[i]+":", DisPlayPanel.isDis));
            editPanel.add(disPalyPanelVector.get(i));
        }
            
            
        searchbtn=new JButton(GetLanguageName.getName("searchBtn"));
        btnPanel.add(searchbtn); ;
        tableModel=new DefaultTableModel(itemStrings, 0);
        table=new JTable(tableModel);
        tablePanel.setViewportView(table);
        
    }
}
