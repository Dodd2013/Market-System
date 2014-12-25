/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FirstPkg;

import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import Toolspkg.DisPlayPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Doddc
 */
public class EmpManagementPanel extends JPanel{
    JPanel btnPanel,editPanel,upPanel;
    JScrollPane tablePanel;
    JButton newbtn,delbtn,searchbtn,updatabtn;
    JSeparator separator1,separator2;
    Vector<DisPlayPanel> disPalyPanelVector;
    String[] itemStrings;
    public JTable table;
    public DefaultTableModel tableModel;
    public TableModel model;
    public EmpManagementPanel(){
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
            final DisPlayPanel disPlayPanel=disPalyPanelVector.get(i);
            final int j=i;
            editPanel.add(disPlayPanel);
            disPalyPanelVector.get(i).textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(disPlayPanel.textField.getText(), table.getSelectedRow(), j);
                    }
                });
        }
            
            
        searchbtn=new JButton(GetLanguageName.getName("searchBtn"));
        btnPanel.add(searchbtn);
        searchbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectDig();
            }
        });
        newbtn=new JButton(GetLanguageName.getName("newBtn"));
        newbtn.addActionListener(new ActionListener() {
            ListSelectionListener d=new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(tableModel.getRowCount()!=0)
                        table.setRowSelectionInterval(table.convertRowIndexToView(tableModel.getRowCount()-1), table.convertRowIndexToView(tableModel.getRowCount()-1));
                    }
                };
            @Override
            public void actionPerformed(ActionEvent e) {
                final JPanel oldPanel;
                upPanel.remove(btnPanel);
                oldPanel=new JPanel();
                oldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JButton sbtn,exitbtn;
                sbtn=new JButton(GetLanguageName.getName("ok"));
                sbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Vector<String> itemStrings=new Vector<>();
                        boolean flag=false;
                        for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            String s=disPlayPanel.textField.getText();
                            itemStrings.add(s);
                            if(s.equals(""))flag=true;
                        }
                        if(flag){
                            JOptionPane.showMessageDialog(DataOnly.mainFrame.maF, GetLanguageName.getName("empdiatel"));
                        }else{
                            try {
                                PreparedStatement pstmt= DataOnly.conData.con.prepareStatement("insert into EmployeeTB values(?,?,?,?,?,?,?,?,getdate())");
                                for(int i=0;i<itemStrings.size()-1;i++){
                                    pstmt.setString(i+1, itemStrings.get(i));
                                }
                                pstmt.executeUpdate();
//                                pstmt=DataOnly.conData.con.prepareStatement("select * from CompanyTB where Front_Tel='"+fronttelString+"'");
//                                ResultSet res=pstmt.executeQuery();
//                                String cmpid=null;
//                                while(res.next())
//                                    cmpid=res.getString("Com_id");
//                                table.setValueAt(cmpid, table.getSelectedRow(), 0);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(btnPanel, ex.getMessage());
                            }
                        upPanel.remove(oldPanel);
                        upPanel.add(btnPanel);
                        upPanel.validate();
                        upPanel.repaint();
                        for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            disPlayPanel.textField.setEditable(false);
                            //disPlayPanel.textField.setText("");
                        }
//                        nameCNPanel.label.setForeground(Color.BLACK);
//                        frontTelPanel.label.setForeground(Color.BLACK);
//                        addressCNPanel.label.setForeground(Color.BLACK);
//                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                        }
                    }
                });
                oldPanel.add(sbtn);
                exitbtn=new JButton(GetLanguageName.getName("cancel"));
                exitbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tableModel.removeRow(tableModel.getRowCount()-1);
                        upPanel.remove(oldPanel);
                        upPanel.add(btnPanel);
                        upPanel.validate();
                        upPanel.repaint();
                        for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            disPlayPanel.textField.setEditable(false);
                            //disPlayPanel.textField.setText("");
                        }
//                        nameCNPanel.label.setForeground(Color.BLACK);
//                        frontTelPanel.label.setForeground(Color.BLACK);
//                        addressCNPanel.label.setForeground(Color.BLACK);
//                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                    }
                });
                oldPanel.add(exitbtn);
                upPanel.add(oldPanel,BorderLayout.NORTH);
                upPanel.validate();
                upPanel.repaint();
                tableModel.addRow(new Vector());
                for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            disPlayPanel.textField.setEditable(true);
                        }
//                nameCNPanel.label.setForeground(Color.BLUE);
//                frontTelPanel.label.setForeground(Color.BLUE);
//                addressCNPanel.label.setForeground(Color.BLUE);
//                cityPanel.label.setForeground(Color.BLUE);
                table.setRowSelectionInterval(tableModel.getRowCount()-1, tableModel.getRowCount()-1);
                table.getSelectionModel().addListSelectionListener(d);
            }
            
        });
        btnPanel.add(newbtn);
        delbtn=new JButton(GetLanguageName.getName("deleteBtn"));
        delbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow()!=-1){
                if(JOptionPane.showConfirmDialog(DataOnly.mainFrame.maF, GetLanguageName.getName("delete"), "提示",
                                      JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==0){
                    try {
                        PreparedStatement pstmt=DataOnly.conData.con.prepareStatement("Delete from EmployeeTB where Emp_Id=?");
                        pstmt.setString(1, (String)tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 0));
                        pstmt.executeUpdate();
                        tableModel.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                    } catch (SQLException ex) {
                       JOptionPane.showMessageDialog(btnPanel, ex.getMessage());
                    }
                    
                    
                }}else{
                    JOptionPane.showMessageDialog(DataOnly.mainFrame.maF, GetLanguageName.getName("chose"));
                }
            }
        });
        btnPanel.add(delbtn);
        updatabtn=new JButton(GetLanguageName.getName("updataBtn"));
        updatabtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow()<0){
                            JOptionPane.showMessageDialog(updatabtn, GetLanguageName.getName("chose"));
                        }else{
                final ListSelectionListener d=new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(tableModel.getRowCount()!=0)
                        table.setRowSelectionInterval(table.convertRowIndexToView(tableModel.getRowCount()-1), table.convertRowIndexToView(tableModel.getRowCount()-1));
                    }
                };
                 final JPanel oldPanel;
                upPanel.remove(btnPanel);
                oldPanel=new JPanel();
                oldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JButton sbtn,exitbtn;
                sbtn=new JButton(GetLanguageName.getName("ok"));
                sbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Vector<String> itemStrings=new Vector<>();
                        boolean flag=false;
                        for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            String s=disPlayPanel.textField.getText();
                            itemStrings.add(s);
                            if(s.equals(""))flag=true;
                        }
                        if(flag){
                            JOptionPane.showMessageDialog(upPanel, GetLanguageName.getName("empdiatel"));
                        }else{
                            try {
                                PreparedStatement pstmt=DataOnly.conData.con.prepareStatement("update EmployeeTB set Name_Cn=?,Name_En=?,Gender=?,"
                                        + "Dob=?,Mobile=?,Email=?,Address=? where Emp_Id=?");
                                for(int i=1;i<itemStrings.size()-1;i++){
                                    pstmt.setString(i, itemStrings.get(i));
                                }
                                pstmt.setString(8, itemStrings.get(0));
                                pstmt.executeUpdate();
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(btnPanel, ex.getMessage());
                            }
                            }
                        upPanel.remove(oldPanel);
                        upPanel.add(btnPanel);
                        upPanel.validate();
                        upPanel.repaint();
                        for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            disPlayPanel.textField.setEditable(false);
                           // disPlayPanel.textField.setText("");
                        }
//                        nameCNPanel.label.setForeground(Color.BLACK);
//                        frontTelPanel.label.setForeground(Color.BLACK);
//                        addressCNPanel.label.setForeground(Color.BLACK);
//                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                        }
                
                    
                    
            });
                            oldPanel.add(sbtn);
                exitbtn=new JButton(GetLanguageName.getName("cancel"));
                exitbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        upPanel.remove(oldPanel);
                        upPanel.add(btnPanel);
                        upPanel.validate();
                        upPanel.repaint();
                        for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            disPlayPanel.textField.setEditable(false);
                            //disPlayPanel.textField.setText("");
                        }
//                        nameCNPanel.label.setForeground(Color.BLACK);
//                        frontTelPanel.label.setForeground(Color.BLACK);
//                        addressCNPanel.label.setForeground(Color.BLACK);
//                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                    }
                });
                oldPanel.add(exitbtn);
                upPanel.add(oldPanel,BorderLayout.NORTH);
                upPanel.validate();
                upPanel.repaint();
                for(DisPlayPanel disPlayPanel:disPalyPanelVector){
                            disPlayPanel.textField.setEditable(true);
                }
                disPalyPanelVector.get(0).textField.setEditable(false);
                disPalyPanelVector.get(8).textField.setEditable(false);
//                nameCNPanel.label.setForeground(Color.BLUE);
//                frontTelPanel.label.setForeground(Color.BLUE);
//                addressCNPanel.label.setForeground(Color.BLUE);
//                cityPanel.label.setForeground(Color.BLUE);
                table.getSelectionModel().addListSelectionListener(d);
            }}
            }
);
        btnPanel.add(updatabtn);
        tableModel=new DefaultTableModel(itemStrings, 0);
        table=new JTable(tableModel){
            public boolean  isCellEditable(int row,int column){
                return false;
            }
        };
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);  
        table.setRowSorter(sorter); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePanel.setViewportView(table);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int row=table.getSelectedRow();
                        if(row!=-1){
                        row=table.convertRowIndexToModel(row);
                        for(int i=0;i<disPalyPanelVector.size();i++){
                            DisPlayPanel disPlayPanel=disPalyPanelVector.get(i);
                            disPlayPanel.textField.setText((String)tableModel.getValueAt(row, i));
                        }
                        }
                        
                    }
                });
        
    }
     private class SelectDig extends JDialog{
        public PreparedStatement pstmt;
        private Vector<DisPlayPanel> disPlayPanels=new Vector<>();
        public JButton btn;
        private String[] itemStrings;
        public SelectDig(){
            super(DataOnly.mainFrame.maF, GetLanguageName.getName("searchBtn"));
            this.setSize(750,200);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setFocusable(true);
            itemStrings=new String[]{
            GetLanguageName.getName("empId"),
            GetLanguageName.getName("empNameCN"),
            GetLanguageName.getName("empNameEN"),
            GetLanguageName.getName("empMobile"),
        };
        for(int i=0;i<4;i++){
            disPlayPanels.add(new DisPlayPanel(itemStrings[i]+":", DisPlayPanel.isSelect));
            this.add(disPlayPanels.get(i));
            y(disPlayPanels.get(i));
        }
            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        btn.doClick();
                    }
                }
            });
            final SelectDig self=this;
            btn=new JButton(GetLanguageName.getName("searchBtn"));
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
               for(int i=0;i<4;i++){
                f(disPlayPanels.get(i),i+1);
              }
                    try {
                        Hashtable htable=new Hashtable<>();
                        ResultSet res=pstmt.executeQuery();
                        for(int i=0,j=tableModel.getRowCount();i<j;i++){
                           tableModel.removeRow(0); 
                        }
                        table.validate();
                        for(int i=0;res.next();i++){
                            Vector<String> v=new Vector<>();
                            v.add(res.getString("Emp_Id"));
                            v.add(res.getString("Name_Cn"));
                            v.add(res.getString("Name_En"));
                            v.add(res.getString("Gender"));
                            v.add(res.getString("Dob"));
                            v.add(res.getString("Mobile"));
                            v.add(res.getString("Email"));
                            v.add(res.getString("Address"));
                            v.add(res.getString("Join_Date"));
                            htable.put(i, v);
                            tableModel.addRow(v);
                        }
                        self.dispose();
                    } catch (SQLException ex) {
                        Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            this.add(btn);
            this.setVisible(true);
            try {
                pstmt=DataOnly.conData.con.prepareStatement("select * from EmployeeTB where Emp_Id like ? and Name_Cn like ? and Name_En like ? and Mobile like ?");
                
            } catch (SQLException ex) {
                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private  void y(DisPlayPanel disPanelSon){
            disPanelSon.textField.setEditable(true);
            disPanelSon.textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        btn.doClick();
                    }
                }
            });
        }
        private void f(DisPlayPanel disPanelSon,int i){
            try {
                if(!disPanelSon.textField.getText().equals("")){
                    if(((String)disPanelSon.comboBox.getSelectedItem()).equals(">")){
                        pstmt.setString(i,"%"+disPanelSon.textField.getText()+"%");
                    }else{
                        pstmt.setString(i, disPanelSon.textField.getText());
                    }
                    
                }else{
                   pstmt.setString(i, "%");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
    }
}
