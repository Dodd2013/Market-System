/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FirstPkg;

import DataReadpkg.DataOnly;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
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
public class CompanyPanel extends JPanel{
   public JTable table;
   public DefaultTableModel tableModel;
   public TableModel model;
   public JPanel upPanel,mainPanel,disPanel;
   DisPanelSon cmpidPanel,nameCNPanel,nameENPanel,cityPanel,addressCNPanel,addressENPanel,frontTelPanel,remarkPanel;
   public JButton newBtn,selectBtn,upDataBtn,delBtn;
   public JScrollPane tablePane;
   public ListSelectionListener selectAndDisPlay;
   public Hashtable<Integer,Vector<String>> htable=new Hashtable<>();
    public CompanyPanel(){
        selectAndDisPlay=new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int row=table.getSelectedRow();
                        if(row!=-1){
                        row=table.convertRowIndexToModel(row);
                        cmpidPanel.textField.setText((String)tableModel.getValueAt(row, 0));
                        nameCNPanel.textField.setText((String)tableModel.getValueAt(row, 1));
                        nameENPanel.textField.setText((String)tableModel.getValueAt(row, 2));
                        cityPanel.textField.setText((String)tableModel.getValueAt(row, 3));
                        addressCNPanel.textField.setText((String)tableModel.getValueAt(row, 4));
                        addressENPanel.textField.setText((String)tableModel.getValueAt(row, 5));
                        frontTelPanel.textField.setText((String)tableModel.getValueAt(row, 6));
                        remarkPanel.textField.setText((String)tableModel.getValueAt(row, 7));
                        }
                        
                    }
                };
        String[] columString={
            "公司编号",
            "分公司中文名称",
            "分公司英文名称",
            "城市",
            "中文地址",
            "英文地址",
            "前台电话",
            "备注"
        };
        this.setLayout(new BorderLayout());
        tableModel=new DefaultTableModel();
        for(String i:columString)
        tableModel.addColumn(i);
        table=new JTable(tableModel){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
          
        };
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        table.getColumn("分公司中文名称").setPreferredWidth(150);
        table.getColumn("分公司英文名称").setPreferredWidth(150);
        table.getColumn("中文地址").setPreferredWidth(200);
        table.getColumn("前台电话").setPreferredWidth(150);
        table.getColumn("备注").setPreferredWidth(200);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePane=new JScrollPane();
        tablePane.setHorizontalScrollBarPolicy( 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        tablePane.setVerticalScrollBarPolicy( 
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        tablePane.setViewportView(table);
        this.add(tablePane,BorderLayout.CENTER);
        table.getSelectionModel().addListSelectionListener(selectAndDisPlay);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);  
        table.setRowSorter(sorter); 
        mainPanel=new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.add(mainPanel,BorderLayout.NORTH);
        
        JSeparator js=new JSeparator();
        mainPanel.add(js);
        
        disPanel=new JPanel();
        disPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        disPanel.setPreferredSize(new Dimension(200, 200));
        mainPanel.add(disPanel,BorderLayout.SOUTH);
        
        
        
        disPanel.add(cmpidPanel=new DisPanelSon("公司编号:",DisPanelSon.isDis));
        disPanel.add(nameCNPanel=new DisPanelSon( "分公司中文名称:",DisPanelSon.isDis));
        disPanel.add(cityPanel=new DisPanelSon("城市:",DisPanelSon.isDis));
        disPanel.add(addressCNPanel=new DisPanelSon("中文地址:",DisPanelSon.isDis));
        disPanel.add(nameENPanel=new DisPanelSon("分公司英文名称:",DisPanelSon.isDis));
        disPanel.add(addressENPanel=new DisPanelSon( "英文地址:",DisPanelSon.isDis));
        disPanel.add(frontTelPanel=new DisPanelSon("前台电话:",DisPanelSon.isDis));
        disPanel.add(remarkPanel=new DisPanelSon("备注:",DisPanelSon.isDis));
        remarkPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(remarkPanel.textField.getText(), table.getSelectedRow(), 7);
                    }
                });
                nameCNPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(nameCNPanel.textField.getText(), table.getSelectedRow(), 1);
                    }
                });
                nameENPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(nameENPanel.textField.getText(), table.getSelectedRow(), 2);
                    }
                });
                cityPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(cityPanel.textField.getText(), table.getSelectedRow(), 3);
                    }
                });
                addressCNPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(addressCNPanel.textField.getText(), table.getSelectedRow(), 4);
                    }
                });
                frontTelPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(frontTelPanel.textField.getText(), table.getSelectedRow(), 6);
                    }
                });
                addressENPanel.textField.addKeyListener(new KeyAdapter() {
                    
                    public void keyReleased(KeyEvent e) {
                        table.setValueAt(addressENPanel.textField.getText(), table.getSelectedRow(), 5);
                    }
                });
        upPanel=new JPanel();
        upPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(upPanel,BorderLayout.NORTH);
        
        selectBtn=new JButton("查询");
        selectBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectDig(DataOnly.mainFrame.maF);
                
            }
        });
        upPanel.add(selectBtn);
        
        newBtn=new JButton("新增");
        newBtn.addActionListener(new ActionListener() {
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
                mainPanel.remove(upPanel);
                oldPanel=new JPanel();
                oldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JButton sbtn,exitbtn;
                sbtn=new JButton("确定");
                sbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nameENString=nameENPanel.textField.getText(),
                                nameCNString=nameCNPanel.textField.getText(),
                                addressCString=addressCNPanel.textField.getText(),
                                addressEString=addressENPanel.textField.getText(),
                                citysString=cityPanel.textField.getText(),
                                remarkString=remarkPanel.textField.getText(),
                                fronttelString=frontTelPanel.textField.getText();
                        if(nameCNString.equals("")||fronttelString.equals("")||addressCString.equals("")||citysString.equals("")){
                            JOptionPane.showMessageDialog(nameCNPanel, "请填写必要信息！！！");
                        }else{
                            try {
                                PreparedStatement pstmt= DataOnly.conData.con.prepareStatement("insert into CompanyTB values(?,?,?,?,?,?,?)");
                                pstmt.setString(1, nameCNString);
                                pstmt.setString(2, nameENString);
                                pstmt.setString(3, citysString);
                                pstmt.setString(4, addressCString);
                                pstmt.setString(5, addressEString);
                                pstmt.setString(6, fronttelString);
                                pstmt.setString(7, remarkString);
                                pstmt.executeUpdate();
                                pstmt=DataOnly.conData.con.prepareStatement("select * from CompanyTB where Front_Tel='"+fronttelString+"'");
                                ResultSet res=pstmt.executeQuery();
                                String cmpid=null;
                                while(res.next())
                                    cmpid=res.getString("Com_id");
                                table.setValueAt(cmpid, table.getSelectedRow(), 0);
                            } catch (SQLException ex) {
                                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        mainPanel.remove(oldPanel);
                        mainPanel.add(upPanel);
                        mainPanel.validate();
                        mainPanel.repaint();
                        nameCNPanel.textField.setEditable(false);
                        nameENPanel.textField.setEditable(false);
                        cityPanel.textField.setEditable(false);
                        addressCNPanel.textField.setEditable(false);
                        frontTelPanel.textField.setEditable(false);
                        addressENPanel.textField.setEditable(false);
                        remarkPanel.textField.setEditable(false);
                        nameCNPanel.textField.setText("");
                        nameENPanel.textField.setText("");
                        cityPanel.textField.setText("");
                        addressCNPanel.textField.setText("");
                        frontTelPanel.textField.setText("");
                        addressENPanel.textField.setText("");
                        remarkPanel.textField.setText("");
                        nameCNPanel.label.setForeground(Color.BLACK);
                        frontTelPanel.label.setForeground(Color.BLACK);
                        addressCNPanel.label.setForeground(Color.BLACK);
                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                        }
                    }
                });
                oldPanel.add(sbtn);
                exitbtn=new JButton("取消");
                exitbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tableModel.removeRow(tableModel.getRowCount()-1);
                        mainPanel.remove(oldPanel);
                        mainPanel.add(upPanel);
                        mainPanel.validate();
                        mainPanel.repaint();
                        nameCNPanel.textField.setEditable(false);
                        nameENPanel.textField.setEditable(false);
                        cityPanel.textField.setEditable(false);
                        addressCNPanel.textField.setEditable(false);
                        frontTelPanel.textField.setEditable(false);
                        addressENPanel.textField.setEditable(false);
                        remarkPanel.textField.setEditable(false);
                        nameCNPanel.textField.setText("");
                        nameENPanel.textField.setText("");
                        cityPanel.textField.setText("");
                        addressCNPanel.textField.setText("");
                        frontTelPanel.textField.setText("");
                        addressENPanel.textField.setText("");
                        remarkPanel.textField.setText("");
                        nameCNPanel.label.setForeground(Color.BLACK);
                        frontTelPanel.label.setForeground(Color.BLACK);
                        addressCNPanel.label.setForeground(Color.BLACK);
                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                    }
                });
                oldPanel.add(exitbtn);
                mainPanel.add(oldPanel,BorderLayout.NORTH);
                mainPanel.validate();
                mainPanel.repaint();
                tableModel.addRow(new Vector());
                nameCNPanel.textField.setEditable(true);
                nameENPanel.textField.setEditable(true);
                cityPanel.textField.setEditable(true);
                addressCNPanel.textField.setEditable(true);
                frontTelPanel.textField.setEditable(true);
                addressENPanel.textField.setEditable(true);
                remarkPanel.textField.setEditable(true);
                nameCNPanel.label.setForeground(Color.BLUE);
                frontTelPanel.label.setForeground(Color.BLUE);
                addressCNPanel.label.setForeground(Color.BLUE);
                cityPanel.label.setForeground(Color.BLUE);
                table.setRowSelectionInterval(tableModel.getRowCount()-1, tableModel.getRowCount()-1);
                table.getSelectionModel().addListSelectionListener(d);
            }
            
        });
        upPanel.add(newBtn);
        
        upDataBtn=new JButton("修改");
        upDataBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow()<0){
                            JOptionPane.showMessageDialog(nameCNPanel, "请选中一条记录！！");
                        }else{
                final ListSelectionListener d=new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(tableModel.getRowCount()!=0)
                        table.setRowSelectionInterval(table.convertRowIndexToView(tableModel.getRowCount()-1), table.convertRowIndexToView(tableModel.getRowCount()-1));
                    }
                };
                 final JPanel oldPanel;
                mainPanel.remove(upPanel);
                oldPanel=new JPanel();
                oldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                JButton sbtn,exitbtn;
                sbtn=new JButton("确定");
                sbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String nameENString=nameENPanel.textField.getText(),
                                nameCNString=nameCNPanel.textField.getText(),
                                addressCString=addressCNPanel.textField.getText(),
                                addressEString=addressENPanel.textField.getText(),
                                citysString=cityPanel.textField.getText(),
                                remarkString=remarkPanel.textField.getText(),
                                fronttelString=frontTelPanel.textField.getText(),
                                cmpIdString=cmpidPanel.textField.getText();
                        if(nameCNString.equals("")||fronttelString.equals("")||addressCString.equals("")||citysString.equals("")){
                            JOptionPane.showMessageDialog(nameCNPanel, "请填写必要信息！！！");
                        }else{
                            try {
                                PreparedStatement pstmt=DataOnly.conData.con.prepareStatement("update CompanyTB set Name_Cn=?,Name_En=?,city=?,"
                                        + "Address_Cn=?,Front_Tel=?,Address_En=?,Remarks=? where Com_id=?");
                                pstmt.setString(1, nameCNString);
                                pstmt.setString(2, nameENString);
                                pstmt.setString(3, citysString);
                                pstmt.setString(4, addressCString);
                                pstmt.setString(5, fronttelString);
                                pstmt.setString(6, addressEString);
                                pstmt.setString(7, remarkString);
                                pstmt.setString(8, cmpIdString);
                                pstmt.executeUpdate();
                            } catch (SQLException ex) {
                                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            }
                        mainPanel.remove(oldPanel);
                        mainPanel.add(upPanel);
                        mainPanel.validate();
                        mainPanel.repaint();
                        nameCNPanel.textField.setEditable(false);
                        nameENPanel.textField.setEditable(false);
                        cityPanel.textField.setEditable(false);
                        addressCNPanel.textField.setEditable(false);
                        frontTelPanel.textField.setEditable(false);
                        addressENPanel.textField.setEditable(false);
                        remarkPanel.textField.setEditable(false);
                        nameCNPanel.textField.setText("");
                        nameENPanel.textField.setText("");
                        cityPanel.textField.setText("");
                        addressCNPanel.textField.setText("");
                        frontTelPanel.textField.setText("");
                        addressENPanel.textField.setText("");
                        remarkPanel.textField.setText("");
                        nameCNPanel.label.setForeground(Color.BLACK);
                        frontTelPanel.label.setForeground(Color.BLACK);
                        addressCNPanel.label.setForeground(Color.BLACK);
                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                        }
                
                    
                    
            });
                            oldPanel.add(sbtn);
                exitbtn=new JButton("取消");
                exitbtn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainPanel.remove(oldPanel);
                        mainPanel.add(upPanel);
                        mainPanel.validate();
                        mainPanel.repaint();
                        nameCNPanel.textField.setEditable(false);
                        nameENPanel.textField.setEditable(false);
                        cityPanel.textField.setEditable(false);
                        addressCNPanel.textField.setEditable(false);
                        frontTelPanel.textField.setEditable(false);
                        addressENPanel.textField.setEditable(false);
                        remarkPanel.textField.setEditable(false);
                        nameCNPanel.textField.setText("");
                        nameENPanel.textField.setText("");
                        cityPanel.textField.setText("");
                        addressCNPanel.textField.setText("");
                        frontTelPanel.textField.setText("");
                        addressENPanel.textField.setText("");
                        remarkPanel.textField.setText("");
                        nameCNPanel.label.setForeground(Color.BLACK);
                        frontTelPanel.label.setForeground(Color.BLACK);
                        addressCNPanel.label.setForeground(Color.BLACK);
                        cityPanel.label.setForeground(Color.BLACK);
                        table.getSelectionModel().removeListSelectionListener(d);
                    }
                });
                oldPanel.add(exitbtn);
                mainPanel.add(oldPanel,BorderLayout.NORTH);
                mainPanel.validate();
                mainPanel.repaint();
                nameCNPanel.textField.setEditable(true);
                nameENPanel.textField.setEditable(true);
                cityPanel.textField.setEditable(true);
                addressCNPanel.textField.setEditable(true);
                frontTelPanel.textField.setEditable(true);
                addressENPanel.textField.setEditable(true);
                remarkPanel.textField.setEditable(true);
                nameCNPanel.label.setForeground(Color.BLUE);
                frontTelPanel.label.setForeground(Color.BLUE);
                addressCNPanel.label.setForeground(Color.BLUE);
                cityPanel.label.setForeground(Color.BLUE);
                table.getSelectionModel().addListSelectionListener(d);
            }}
            }
);
        upPanel.add(upDataBtn);
        
        delBtn=new JButton("删除");
        delBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow()!=-1){
                if(JOptionPane.showConfirmDialog(DataOnly.mainFrame.maF, "是否要删除该条记录", "提示",
                                      JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==0){
                    try {
                        PreparedStatement pstmt=DataOnly.conData.con.prepareStatement("Delete from CompanyTB where Com_id=?");
                        pstmt.setString(1, (String)tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 0));
                        pstmt.executeUpdate();
                        tableModel.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                    } catch (SQLException ex) {
                        Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }}else{
                    JOptionPane.showMessageDialog(DataOnly.mainFrame.maF, "请选中一条要删除的记录！！");
                }
            }
        });
        upPanel.add(delBtn);
        
    }
    private class DisPanelSon extends JPanel{
        public JLabel label;
        public JTextField textField;
        public String title;
        public final static int isSelect=1,isDis=0;
        public JComboBox comboBox;
        public DisPanelSon(String title,int OP) {
            this.label = new JLabel(title);
            this.textField = new JTextField();
            this.title = title;
            textField.setPreferredSize(new Dimension(150, 30));
            textField.setEditable(false);
            textField.addFocusListener(new Toolspkg.SelectAllWhileGetFocusJTF());
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            
            this.add(label);
            if(OP==DisPanelSon.isSelect){
                this.setPreferredSize(new Dimension(320, 45));
                String[] items={
                    "包含",
                    "等于"
                };
                comboBox=new JComboBox(items);
                this.add(comboBox);
            }else{
                this.setPreferredSize(new Dimension(300, 45));
            }
            this.add(textField);
        }
    }
    
    private class SelectDig extends JDialog{
        public PreparedStatement pstmt;
        public DisPanelSon cmpidPanel,nameCNPanel,nameENPanel,cityPanel,addressCNPanel,addressENPanel,frontTelPanel;
        public JButton btn;
        public SelectDig(JFrame j){
            super(j, "查询");
            this.setSize(750,200);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setFocusable(true);          
            this.add(cmpidPanel=new DisPanelSon("公司编号:",DisPanelSon.isSelect));
            this.add(nameCNPanel=new DisPanelSon( "分公司中文名称:",DisPanelSon.isSelect));
            this.add(frontTelPanel=new DisPanelSon("前台电话:",DisPanelSon.isSelect));
            this.add(cityPanel=new DisPanelSon("城市:",DisPanelSon.isSelect));
            this.add(addressCNPanel=new DisPanelSon("中文地址:",DisPanelSon.isSelect));
            y(cityPanel);
            y(cmpidPanel);
            y(nameCNPanel);
            y(addressCNPanel);
            y(frontTelPanel);
            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        btn.doClick();
                    }
                }
            });
            final SelectDig self=this;
            btn=new JButton("查询");
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    f(cmpidPanel,1);
                    f(nameCNPanel,2);
                    f(cityPanel,3);
                    f(addressCNPanel,4);
                    f(frontTelPanel,5);
                    try {
                        htable=new Hashtable<>();
                        ResultSet res=pstmt.executeQuery();
                        for(int i=0,j=tableModel.getRowCount();i<j;i++){
                           tableModel.removeRow(0); 
                        }
                        table.validate();
                        for(int i=0;res.next();i++){
                            Vector<String> v=new Vector<>();
                            v.add(res.getString("Com_id"));
                            v.add(res.getString("Name_Cn"));
                            v.add(res.getString("Name_En"));
                            v.add(res.getString("City"));
                            v.add(res.getString("Address_Cn"));
                            v.add(res.getString("Address_En"));
                            v.add(res.getString("Front_Tel"));
                            v.add(res.getString("Remarks"));
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
            cmpidPanel.textField.setEditable(true);
            nameCNPanel.textField.setEditable(true);
            cityPanel.textField.setEditable(true);
            addressCNPanel.textField.setEditable(true);
            frontTelPanel.textField.setEditable(true);
            this.setVisible(true);
            try {
                pstmt=DataOnly.conData.con.prepareStatement("select * from CompanyTB where Com_id like ? and Name_Cn like ? "
                        + "and city like ? and Address_Cn like ? and Front_Tel like ?");
                
            } catch (SQLException ex) {
                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private  void y(DisPanelSon disPanelSon){
            disPanelSon.textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        btn.doClick();
                    }
                }
            });
        }
        private void f(DisPanelSon disPanelSon,int i){
            try {
                if(!disPanelSon.textField.getText().equals("")){
                    if(((String)disPanelSon.comboBox.getSelectedItem()).equals("包含")){
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
