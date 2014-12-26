/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FirstPkg;

import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import Toolspkg.DisPlayPanel;
import Toolspkg.EncoderHandler;
import java.awt.BorderLayout;
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
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
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
public class UserManagementPanel extends JPanel{
    JPanel btnPanel,editPanel,upPanel;
    JScrollPane tablePanel;
    JButton newbtn,delbtn,searchbtn,updatabtn;
    JSeparator separator1,separator2;
    Vector<DisPlayPanel> disPalyPanelVector;
    String[] itemStrings;
    public JTable table;
    public DefaultTableModel tableModel;
    public TableModel model;
    int itemNum=3;
    public UserManagementPanel(){
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
            GetLanguageName.getName("userName"),
            GetLanguageName.getName("passWord"),
            GetLanguageName.getName("empId")
        };
        for(int i=0;i<itemNum;i++){
            disPalyPanelVector.add(new DisPlayPanel(itemStrings[i]+":", DisPlayPanel.isDis));
            editPanel.add(disPalyPanelVector.get(i));
        }
            
        
        searchbtn=new JButton(GetLanguageName.getName("searchBtn"));
        searchbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectDig();
            }
        });
        btnPanel.add(searchbtn); 
        newbtn=new JButton(GetLanguageName.getName("newBtn"));
        btnPanel.add(newbtn);
        delbtn=new JButton(GetLanguageName.getName("deleteBtn"));
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
                                PreparedStatement pstmt=DataOnly.conData.con.prepareStatement("update usersTB set userid=?,password=? where Emp_Id=?");
                                for(int i=0;i<itemStrings.size();i++){
                                    pstmt.setString(i+1, itemStrings.get(i));
                                }
                                pstmt.setString(2, EncoderHandler.encode("SHA1",itemStrings.get(1)));
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
                        }
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
                        }
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
                disPalyPanelVector.get(2).textField.setEditable(false);
                table.getSelectionModel().addListSelectionListener(d);
            }}
            }
);
        btnPanel.add(updatabtn);
        tableModel=new DefaultTableModel(itemStrings, 0);
        table=new JTable(tableModel);
        tablePanel.setViewportView(table);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);  
        table.setRowSorter(sorter); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
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
            GetLanguageName.getName("userName"),
            GetLanguageName.getName("empId")
        };
        for(int i=0;i<2;i++){
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
               for(int i=0;i<2;i++){
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
                            v.add(res.getString("userid"));
                            v.add("*********");
                            v.add(res.getString("Emp_Id"));
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
                pstmt=DataOnly.conData.con.prepareStatement("select * from usersTB where userid like ? and Emp_Id like ?");
                
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
