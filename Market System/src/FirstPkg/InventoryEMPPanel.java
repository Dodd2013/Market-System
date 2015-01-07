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
public class InventoryEMPPanel extends JPanel {

    JPanel btnPanel, editPanel, upPanel;
    JScrollPane tablePanel;
    JButton searchbtn;
    JSeparator separator1, separator2;
    Vector<DisPlayPanel> disPalyPanelVector;
    String[] itemStrings;
    public JTable table;
    public DefaultTableModel tableModel;
    public TableModel model;
    int itemNum = 7;

    public InventoryEMPPanel() {
        disPalyPanelVector = new Vector<>();
        this.setLayout(new BorderLayout());
        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel = new JPanel();
        editPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel.setPreferredSize(new Dimension(150, 150));
        tablePanel = new JScrollPane();
        upPanel = new JPanel();
        upPanel.setLayout(new BorderLayout());
        this.add(upPanel, BorderLayout.NORTH);
        this.add(tablePanel);
        upPanel.add(btnPanel, BorderLayout.NORTH);
        separator1 = new JSeparator(JSeparator.HORIZONTAL);
        upPanel.add(separator1);
        upPanel.add(editPanel, BorderLayout.SOUTH);
        itemStrings = new String[]{
            GetLanguageName.getName("inventoryId"),
            GetLanguageName.getName("itemId"),
            GetLanguageName.getName("itemNameCN"),
            GetLanguageName.getName("itemNameEN"),
            GetLanguageName.getName("address"),
            GetLanguageName.getName("itemNum"),
            GetLanguageName.getName("sellStatus")
        };
        for (int i = 0; i < itemNum; i++) {
            disPalyPanelVector.add(new DisPlayPanel(itemStrings[i] + ":", DisPlayPanel.isDis));
            final DisPlayPanel disPlayPanel = disPalyPanelVector.get(i);
            final int j = i;
            editPanel.add(disPlayPanel);
            disPalyPanelVector.get(i).textField.addKeyListener(new KeyAdapter() {

                public void keyReleased(KeyEvent e) {
                    table.setValueAt(disPlayPanel.textField.getText(), table.getSelectedRow(), j);
                }
            });
        }

        searchbtn = new JButton(GetLanguageName.getName("searchBtn"));
        searchbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectDig();
            }
        });
        btnPanel.add(searchbtn);
        tableModel = new DefaultTableModel(itemStrings, 0);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
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
                int row = table.getSelectedRow();
                if (row != -1) {
                    row = table.convertRowIndexToModel(row);
                    for (int i = 0; i < disPalyPanelVector.size(); i++) {
                        DisPlayPanel disPlayPanel = disPalyPanelVector.get(i);
                        disPlayPanel.textField.setText((String) tableModel.getValueAt(row, i));
                    }
                }

            }
        });

    }

    private class SelectDig extends JDialog {

        public PreparedStatement pstmt;
        private Vector<DisPlayPanel> disPlayPanels = new Vector<>();
        public JButton btn;
        private String[] itemStrings;

        public SelectDig() {
            super(DataOnly.mainFrame.maF, GetLanguageName.getName("searchBtn"));
            this.setSize(750, 200);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setFocusable(true);
            itemStrings = new String[]{
                GetLanguageName.getName("inventoryId"),
                GetLanguageName.getName("itemId"),
                GetLanguageName.getName("address"),
                GetLanguageName.getName("itemNum"),
                GetLanguageName.getName("sellStatus")
            };
            for (int i = 0; i < 5; i++) {
                disPlayPanels.add(new DisPlayPanel(itemStrings[i] + ":", DisPlayPanel.isSelect));
                this.add(disPlayPanels.get(i));
                y(disPlayPanels.get(i));
            }
            this.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        btn.doClick();
                    }
                }
            });
            final SelectDig self = this;
            btn = new JButton(GetLanguageName.getName("searchBtn"));
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 5; i++) {
                        f(disPlayPanels.get(i), i + 1);
                    }
                    try {
                        Hashtable htable = new Hashtable<>();
                        ResultSet res = pstmt.executeQuery();
                        for (int i = 0, j = tableModel.getRowCount(); i < j; i++) {
                            tableModel.removeRow(0);
                        }
                        table.validate();
                        for (int i = 0; res.next(); i++) {
                            Vector<String> v = new Vector<>();
                            v.add(res.getString("Inventory_Id"));
                            v.add(res.getString("Item_Id"));
                            v.add(res.getString("Item_NameCN"));
                            v.add(res.getString("Item_NameEN"));
                            v.add(res.getString("Address"));
                            v.add(res.getString("Item_Num"));
                            v.add(res.getString("Sell_Status"));
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
                pstmt = DataOnly.conData.con.prepareStatement(" select * from inventoryTB a inner join ItemDetailTB b on a.Item_Id=b.Item_Id where a.Inventory_Id like ? and a.Item_Id like ? and a.Address like ? and a.Item_Num like ? and a.Sell_Status like ?");

            } catch (SQLException ex) {
                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void y(DisPlayPanel disPanelSon) {
            disPanelSon.textField.setEditable(true);
            disPanelSon.textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        btn.doClick();
                    }
                }
            });
        }

        private void f(DisPlayPanel disPanelSon, int i) {
            try {
                if (!disPanelSon.textField.getText().equals("")) {
                    if (((String) disPanelSon.comboBox.getSelectedItem()).equals(">")) {
                        pstmt.setString(i, "%" + disPanelSon.textField.getText() + "%");
                    } else {
                        pstmt.setString(i, disPanelSon.textField.getText());
                    }

                } else {
                    pstmt.setString(i, "%");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CompanyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
