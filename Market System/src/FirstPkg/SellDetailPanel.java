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
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JButton;
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
public class SellDetailPanel extends JPanel {

    JPanel btnPanel, editPanel, upPanel;
    JScrollPane tablePanel;
    JButton newbtn, delbtn, updatabtn;
    JSeparator separator1, separator2;
    Vector<DisPlayPanel> disPalyPanelVector;
    String[] itemStrings;
    public JTable table;
    public DefaultTableModel tableModel;
    public TableModel model;
    public SellPanel sellPanel;
    int itemNum = 5;

    public SellDetailPanel(final SellPanel sellPanel) {
        this.sellPanel = sellPanel;
        disPalyPanelVector = new Vector<>();
        this.setLayout(new BorderLayout());
        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel = new JPanel();
        editPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editPanel.setPreferredSize(new Dimension(120, 120));
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
            GetLanguageName.getName("itemId"),
            GetLanguageName.getName("itemNameEN"),
            GetLanguageName.getName("itemNameCN"),
            GetLanguageName.getName("sellItemNum"),
            GetLanguageName.getName("moneySum"),};
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

        newbtn = new JButton(GetLanguageName.getName("newBtn"));
        newbtn.addActionListener(new ActionListener() {
            ListSelectionListener d = new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (tableModel.getRowCount() != 0) {
                        table.setRowSelectionInterval(table.convertRowIndexToView(tableModel.getRowCount() - 1), table.convertRowIndexToView(tableModel.getRowCount() - 1));
                    }
                }
            };

            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellPanel.newAndUpdata) {
                    final JPanel oldPanel;
                    upPanel.remove(btnPanel);
                    oldPanel = new JPanel();
                    oldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    JButton sbtn, exitbtn;
                    sbtn = new JButton(GetLanguageName.getName("ok"));
                    sbtn.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Vector<String> itemStrings = new Vector<>();
                            boolean flag = false;
                            for (int i = 1; i < disPalyPanelVector.size(); i++) {
                                DisPlayPanel disPlayPanel = disPalyPanelVector.get(i);
                                String s = disPlayPanel.textField.getText();
                                itemStrings.add(s);
                                if (s.equals("")) {
                                    flag = true;
                                }
                            }
                            if (flag) {
                                JOptionPane.showMessageDialog(DataOnly.mainFrame.maF, GetLanguageName.getName("empdiatel"));
                            } else {
                                try {
                                    PreparedStatement pstmt = DataOnly.conData.con.prepareStatement("insert into sellTB values(?,?,?)");
                                    for (int i = 0; i < itemStrings.size(); i++) {
                                        pstmt.setString(i + 1, itemStrings.get(i));
                                    }
                                    pstmt.executeUpdate();
                                    upPanel.remove(oldPanel);
                                    upPanel.add(btnPanel);
                                    upPanel.validate();
                                    upPanel.repaint();
                                    for (DisPlayPanel disPlayPanel : disPalyPanelVector) {
                                        disPlayPanel.textField.setEditable(false);
                                    }
                                    table.getSelectionModel().removeListSelectionListener(d);
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(btnPanel, ex.getMessage());
                                }
                            }
                        }
                    });
                    oldPanel.add(sbtn);
                    exitbtn = new JButton(GetLanguageName.getName("cancel"));
                    exitbtn.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tableModel.removeRow(tableModel.getRowCount() - 1);
                            upPanel.remove(oldPanel);
                            upPanel.add(btnPanel);
                            upPanel.validate();
                            upPanel.repaint();
                            for (DisPlayPanel disPlayPanel : disPalyPanelVector) {
                                disPlayPanel.textField.setEditable(false);
                            }
                            table.getSelectionModel().removeListSelectionListener(d);
                        }
                    });
                    oldPanel.add(exitbtn);
                    upPanel.add(oldPanel, BorderLayout.NORTH);
                    upPanel.validate();
                    upPanel.repaint();
                    tableModel.addRow(new Vector());
                    for (DisPlayPanel disPlayPanel : disPalyPanelVector) {
                        disPlayPanel.textField.setEditable(true);
                    }
                    disPalyPanelVector.get(0).textField.setEditable(false);
                    disPalyPanelVector.get(3).textField.setEditable(false);
                    table.setRowSelectionInterval(tableModel.getRowCount() - 1, tableModel.getRowCount() - 1);
                    table.getSelectionModel().addListSelectionListener(d);
                }

            }
        });
        btnPanel.add(newbtn);
        delbtn = new JButton(GetLanguageName.getName("deleteBtn"));
        delbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (sellPanel.newAndUpdata) {
                    if (table.getSelectedRow() != -1) {
                        if (JOptionPane.showConfirmDialog(DataOnly.mainFrame.maF, GetLanguageName.getName("delete"), "提示",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                            try {
                                PreparedStatement pstmt = DataOnly.conData.con.prepareStatement("Delete from sellDetailTB where sell_Id=? and Item_Id=?");
                                pstmt.setString(1, (String) tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 3));
                                pstmt.setString(2, (String) tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), 0));
                                pstmt.executeUpdate();
                                tableModel.removeRow(table.convertRowIndexToModel(table.getSelectedRow()));
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(btnPanel, ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(DataOnly.mainFrame.maF, GetLanguageName.getName("chose"));
                    }
                }
            }
        });
        btnPanel.add(delbtn);
        updatabtn = new JButton(GetLanguageName.getName("updataBtn"));
        btnPanel.add(updatabtn);
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
}
