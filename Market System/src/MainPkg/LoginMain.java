/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainPkg;
import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import Toolspkg.CheckString;
import Toolspkg.EncoderHandler;
import Toolspkg.SelectAllWhileGetFocusJTF;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author Dodd
 */
public class LoginMain {
    /**
     * 登录窗体
     */
    JFrame loginFrame;
    JLabel idLabel,passwordLabel;
    JTextField idField;
    JPasswordField pasField;
    JButton exitbtn,loginbtn;
    JComboBox<String> languageComboBox;
    public JLabel msg;
    public LoginMain(){
         //init JFrame
        loginFrame=new JFrame(GetLanguageName.getName("systemName"));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        loginFrame.setLayout(null);
        loginFrame.setSize(400,300);
        loginFrame.setLocationRelativeTo(null);
        //init languageComboBox
        String[] languageItem=new String[]{
            "English","Chinese"
        };
        languageComboBox=new JComboBox<>(languageItem);
        languageComboBox.setBounds(280, 10, 100, 30);
        if(DataOnly.locale.equals(new Locale("zh", "CN"))){
            languageComboBox.setSelectedItem("Chinese");
        }
        languageComboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    if(e.getItem().equals("English")){
                    DataOnly.locale=new Locale("en", "US");
                }else if(e.getItem().equals("Chinese")){
                    DataOnly.locale=new Locale("zh", "CN");
                }
                loginFrame.dispose();
                new LoginMain();
                }
            }
        });
        loginFrame.add(languageComboBox);
        //init JLabel
        idLabel=new JLabel(GetLanguageName.getName("userName"));
        idLabel.setBounds(80, 35, 120, 50);
        loginFrame.add(idLabel);
        passwordLabel=new JLabel(GetLanguageName.getName("passWord"));
        passwordLabel.setBounds(80, 90, 120, 50);
        loginFrame.add(passwordLabel);
        
        //init JTF
        idField=new JTextField();
        idField.setBounds(145, 45, 120, 30);
        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    pasField.requestFocus();
                }
            }
});
        
        idField.addFocusListener(new SelectAllWhileGetFocusJTF());
        loginFrame.add(idField);
        pasField=new JPasswordField();
        pasField.setBounds(145, 100, 120, 30);
        pasField.addKeyListener(new KeyAdapter() {
             @Override
             public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    loginbtn.doClick();
                }
            }
});
        pasField.addFocusListener(new SelectAllWhileGetFocusJTF());
        loginFrame.add(pasField);
        
        //init btn
        exitbtn=new JButton(GetLanguageName.getName("exit"));
        exitbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              System.exit(0);
            }
        });
        exitbtn.setBounds(200, 180, 80, 30);
        loginFrame.add(exitbtn);
        loginbtn=new JButton(GetLanguageName.getName("login"));
        loginbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               if(!DataOnly.initCDB){
                   JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("conDatabase"));
               }else if(!DataOnly.clientServerOrNot){
                   JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("conServer"));
               }else{
                    if(idField.getText().equals("")||pasField.getText().equals("")){
                        JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("userNameAndPassWordEmpty"));
                    }else{
                        if(CheckString.checkStringIsPassWord(pasField.getText())){
                            if(loginIn(idField.getText(), pasField.getText())){
                                DataOnly.loginOrnot=true;
                                DataOnly.loginManName=idField.getText();
                                DataOnly.clientServer.startGetMessage();
                                new InitAll();
                                loginFrame.dispose();
                                //System.out.println("fdsag");
                          }
                        }else{
                            JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("passWordNotRight"));
                        }
                        
                    }
               }
               
            }
        });
        loginbtn.setBounds(100, 180, 80, 30);
        loginFrame.add(loginbtn);
        
       
        //设置显示
        loginFrame.setVisible(true); 
        //连接数据库
        this.fristConDBAndServer();
        
    }
    
    protected boolean loginIn(String id,String password){
        String p=password;
        try {
            password=EncoderHandler.encode("sha1", password);
            PreparedStatement pstmt=DataOnly.conData.con.prepareStatement("select * from usersTB a join employeeTB b on a.emp_id=b.emp_id  where userid= ?");
            ResultSet res;
            pstmt.setString(1, id);
            res =pstmt.executeQuery();
            if(res.next()){
                if(password.equals(res.getString("password")))
                {
                    String msg;
                    DataOnly.clientServer.out.println("LOGIN:id:"+id+";password:"+p+";name:"+res.getString("Name_CN"));
                    DataOnly.clientServer.out.flush();
                    this.msg.setText(GetLanguageName.getName("serverRespond"));
                    msg=DataOnly.clientServer.in.readLine();
                    System.out.println(msg);
                    if(msg.equals("CMD:LoginOK")){
                        DataOnly.loginMan=res.getString("emp_id");
                        DataOnly.loginManName=res.getString("Name_CN");
                        return true;
                    }else if(msg.equals("CMD:UserIsOnLine")){
                        JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("userOnline"));
                        this.msg.setText(GetLanguageName.getName("userOnline"));
                        return false;
                    }else{
                        return false;
                    }
                }else JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("passWordNotRight"));
                    
            }else
                JOptionPane.showMessageDialog(loginFrame, GetLanguageName.getName("userNotExist"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(loginFrame, ex.getMessage());
        } catch (IOException ex) {
            if(ex.getMessage().equals("Connection reset")){
                JOptionPane.showMessageDialog(loginFrame,GetLanguageName.getName("serverConBreakOff"));
            }
            System.out.println(ex.getMessage());
        }
        return false;
        
    }
    private void fristConDBAndServer(){
        msg=new JLabel(GetLanguageName.getName("conDatabase"));
        msg.setBounds(60, 225, 200, 30);
        loginFrame.add(msg);
        if(InitAll.ConnectedtoDataBase()){
            msg.setText(GetLanguageName.getName("conSuccess"));
        }
        msg.setText(GetLanguageName.getName("conServer"));
        if(InitAll.ClientServer()){
            msg.setText(GetLanguageName.getName("conSuccess"));
        }
    }
    
}
