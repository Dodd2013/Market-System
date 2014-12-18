/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainPkg;

import ClientServerPkg.ClientServerClass;
import DataBasePkg.ConnectedDatabase;
import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import Toolspkg.PkgClass;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;

/**
 *
 * @author Administrator
 */
public class InitAll {
    
    public InitAll(){
        //init JFrame
        JFrame initFrame=new JFrame("初始化窗口");
        initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initFrame.setLayout(null);
        initFrame.setSize(400, 350);
        initFrame.setLocationRelativeTo(null);
        initFrame.setVisible(true);
        //msg加入
        JLabel msgLabel=new JLabel("gfd");
        msgLabel.setBounds(30, 270, 300, 50);
        initFrame.add(msgLabel);
        /**
         * 初始化数据库连接器
         */
        if(!DataOnly.initCDB){
            msgLabel.setText("正在链接数据库.....");
            ConnectedtoDataBase();
        }
        //连接服务器
        if(!DataOnly.clientServerOrNot)
        ClientServer();
        //初始化系统
        msgLabel.setText("初始化系统");
        DataOnly.sysName="Dodd's ERP";
        //获取屏幕大小！
        msgLabel.setText("获取屏幕大小！");
        DataOnly.ScreenSize.width=Toolkit.getDefaultToolkit().getScreenSize().width;
        DataOnly.ScreenSize.height=Toolkit.getDefaultToolkit().getScreenSize().height;
        DataOnly.ScreenSize.ren=Toolkit.getDefaultToolkit().getScreenInsets(initFrame.getGraphicsConfiguration()).bottom;
        System.out.println("DataOnly.ScreenSize.width"+DataOnly.ScreenSize.width+ "    DataOnly.ScreenSize.height:"+DataOnly.ScreenSize.height);
        //退出钩子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run(){
                PrintWriter out=DataOnly.clientServer.out;
               out.println("LOGOUT");
               out.flush();
               out.println("EXIT");
               out.flush();
            }
        });
        //获取导航树
        msgLabel.setText("获取导航树");
        DataOnly.jutisArrayList=new ArrayList<>();
        GetJurisdiction();
        DataOnly.NaJTree=new JTree(new NagJTreeRoot());
        
        
        DataOnly.initOrnot=true;
        DataOnly.mainFrame=new MainFrame();
        //销毁窗口
        initFrame.dispose();
        
    }
    public static boolean ConnectedtoDataBase(){
        try {
            DataOnly.conData=new ConnectedDatabase();
            DataOnly.initCDB=true;
            return true;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,GetLanguageName.getName("jdbcConfail")+"\n");
            System.out.println(ex.getMessage());
            System.exit(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,GetLanguageName.getName("conDatabaseF")+"\n");
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return false;
    }
    public static boolean ClientServer(){
        try {
            DataOnly.clientServer=new ClientServerClass();
            DataOnly.clientServerOrNot=true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,GetLanguageName.getName("conServerF"));
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        return true;
    }
    private  void GetJurisdiction(){
        PreparedStatement pstmt;
        try {
            pstmt=DataOnly.conData.con.prepareStatement(" select * from PermissionTB a join ModelTB b on a.Per_Id=b.Model_id where Emp_Id= ? order by Model_id");
            pstmt.setInt(1, DataOnly.loginMan);
            ResultSet res=pstmt.executeQuery();
            while(res.next()){
                PkgClass t=new PkgClass();
                t.id=res.getInt("Model_id");
                t.Name=res.getString("Model_Name");
                t.moduleGoup=res.getString("Model_Group");
                DataOnly.jutisArrayList.add(t);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, GetLanguageName.getName("selectFailed")+ex.getMessage());
        }
    }
}
