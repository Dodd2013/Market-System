/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerPkg;

import DataReadPkg.DataOnly;
import DataReadPkg.GetLanguageName;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Dodd-pc
 */
public class ServerMainFrame extends JFrame{
   public JButton startButton;
   public JButton sendButton;
   public JPanel upJPanel;
   public JSplitPane fiSplitPane;  //分割条
   public JPanel downJPanel;
   public JLabel staLabel;
   public JMenuBar jmb; //菜单栏
   public JTable table;
   public JTextField cmdField;
   public JTextArea jTextArea;
   public JScrollPane jsp;
   public JScrollPane tjsp;
   public DefaultTableModel tableModel;
   public TableModel model;
   public ServerStart sst;
   public RemetoControlListener rcl;
   public JComboBox comboBox;
   public ServerMainFrame(){
       super(GetLanguageName.getName("systemName"));
       final ServerMainFrame self=this;
       setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height-Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration()).bottom);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLocationRelativeTo(null);
       Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
               new SaveLog(self);
               for(SocketInfo s:DataOnly.socketInfoHashtable.values()){
                    PrintWriter out=s.out;
                    out.println("CMD:exit");
                    out.flush();
                    
                }
            }
        });
       //init upPanel
       upJPanel=new JPanel();
       upJPanel.setLayout(new BorderLayout(10,10));
       //init jmb
       jmb=new JMenuBar();
       JMenu mainMenu=new JMenu(GetLanguageName.getName("mainMenu"));
       jmb.add(mainMenu);
       JMenuItem exitItem=new JMenuItem(GetLanguageName.getName("exit"));
       mainMenu.add(exitItem);
       this.setJMenuBar(jmb);
       //init startbtn
       startButton=new JButton(GetLanguageName.getName("serverStart"));
       startButton.addActionListener(new ActionListener() {

           @Override
           public void actionPerformed(ActionEvent e) {
               if(startButton.getText().equals(GetLanguageName.getName("serverStart"))){
                  sst= new ServerStart(self);
                  rcl=new RemetoControlListener();
                  rcl.start();
                  sst.start();
                  staLabel.setText(GetLanguageName.getName("serverOn"));
                  jTextArea.append(DataOnly.getNowTime()+":"+GetLanguageName.getName("serverOn")+"！\n");
                  startButton.setText(GetLanguageName.getName("serverStop"));
               }
               else{
                   try {
                       sst.serverSocket.close();
                       sst.interrupt();
                       rcl.interrupt();
                       rcl.serverSocket.close();
                       staLabel.setText(GetLanguageName.getName("serverOff"));
                       jTextArea.append(DataOnly.getNowTime()+":"+GetLanguageName.getName("serverOff")+"！\n");
                       startButton.setText(GetLanguageName.getName("serverStart"));
                       for(SocketInfo s:DataOnly.socketInfoHashtable.values()){
                        PrintWriter out=s.out;
                        out.println("CMD:exit");
                        out.flush();
                    }
                   } catch (IOException ex) {
                       JOptionPane.showMessageDialog(self, GetLanguageName.getName("closePortFailure"));
                   }
               }
               
           }
       });
       upJPanel.add(startButton,BorderLayout.WEST);
       downJPanel=new JPanel();
       downJPanel.setLayout(new BorderLayout(10,10));
       //创建状态标签
       staLabel=new JLabel(GetLanguageName.getName("serverOff"));
       downJPanel.add(staLabel,BorderLayout.SOUTH);
       //init combox
       JPanel jp=new JPanel(new BorderLayout());
       String[] comboboxItems={"CMD:","MSG:","RemoteControl:"};
       comboBox=new JComboBox(comboboxItems);
       jp.add(comboBox,BorderLayout.WEST);
       //创建cmd
       cmdField=new JTextField();
       cmdField.setSize(80, 30);
       cmdField.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e) {
               if(e.getKeyChar()=='\n')sendButton.doClick();
           }
    });
       jp.add(cmdField,BorderLayout.CENTER);
       upJPanel.add(jp,BorderLayout.CENTER);
       //init sendbtn
       sendButton=new JButton(GetLanguageName.getName("send"));
       sendButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(table.getSelectedRowCount()==0){
                   JOptionPane.showMessageDialog(rootPane, GetLanguageName.getName("choseOneUser"));
               }else if(cmdField.getText().equals("")){
                   JOptionPane.showMessageDialog(rootPane, GetLanguageName.getName("enterTheText"));
               }else if((((String)comboBox.getSelectedItem()).equals("CMD:")&&IsCMD.isCmd(cmdField.getText()))||((String)comboBox.getSelectedItem()).equals("MSG:")){
                   
                   model=table.getModel();
               for(int rows:table.getSelectedRows()){
                   int socketId=Integer.valueOf((String)model.getValueAt(rows, 5));
                   System.out.println(socketId+"   "+cmdField.getText());
                   PrintWriter out=DataOnly.socketInfoHashtable.get(socketId).out;
                   out.println((String)comboBox.getSelectedItem()+cmdField.getText());
                   out.flush();
               }
               }else if(((String)comboBox.getSelectedItem()).equals("RemoteControl:")){
                   model=table.getModel();
                   int socketId=Integer.valueOf((String)model.getValueAt(table.getSelectedRow(), 5));
                   PrintWriter out=DataOnly.socketInfoHashtable.get(socketId).out;
                   DataOnly.out=out;
                   out.println((String)comboBox.getSelectedItem()+cmdField.getText());
                   out.flush();
               }else{
                   JOptionPane.showMessageDialog(rootPane, GetLanguageName.getName("enterTheRightCMD"));
               }
           }
       });
       upJPanel.add(sendButton,BorderLayout.EAST);
       //init jTextArea
       jTextArea =new JTextArea();
       jTextArea.setEditable(false);
       fiSplitPane=new JSplitPane();
       tjsp=new JScrollPane(jTextArea);
       fiSplitPane.setRightComponent(tjsp);
       //
       String[] columnNames={GetLanguageName.getName("userName"),GetLanguageName.getName("userId"),
           GetLanguageName.getName("networkAddress"),GetLanguageName.getName("LANAddress"),GetLanguageName.getName("loginTime"),"SockId"};
       tableModel= new DefaultTableModel(columnNames, 0);
       //init JTable
       table=new JTable(tableModel);
       //init jsp
       jsp=new JScrollPane();
       jsp.setViewportView(table);
       fiSplitPane.setLeftComponent(jsp);
       downJPanel.add(fiSplitPane,BorderLayout.CENTER);
       //可视
       add(upJPanel,BorderLayout.NORTH);
       add(downJPanel,BorderLayout.CENTER);
       setVisible(true);
   }
   
   
   public static void main(String[] args) {
        new LanguageChose();
    }
}
class LanguageChose extends JFrame{
        public LanguageChose(){
            this.setSize(300, 100);
            this.setLayout(null);
            final JButton okButton=new JButton("OK");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            String[] item=new String[]{
              "English","Chinese"  
            };
            final JComboBox<String> comboBox=new JComboBox<>(item);
            comboBox.setSize(100, 30);
            comboBox.setLocation(50, 20);
            okButton.setSize(80, 30);
            okButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(comboBox.getSelectedItem().equals("English"))
                    {
                        DataOnly.locale=new Locale("en", "US");
                    }else{
                        DataOnly.locale=new Locale("zh","CN");
                    }
                    new ServerMainFrame();
                    LanguageChose.this.dispose();
                }
            });
            this.setFocusable(true);
            this.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        okButton.doClick();
                    }
                }
            });
            comboBox.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if(e.getKeyChar()=='\n'){
                        okButton.doClick();
                    }
                }
            });
            okButton.setLocation(180, 20);
            this.add(okButton);
            this.add(comboBox);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
    }
