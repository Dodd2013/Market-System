/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataReadpkg;

import ClientServerPkg.ClientServerClass;
import DataBasePkg.ConnectedDatabase;
import MainPkg.MainFrame;
import RemoteControlPkg.RCServer;
import Toolspkg.PkgClass;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JTree;

/**
 *
 * @author Administrator
 */
public class DataOnly {
    /**
     *  配置文件所在位置
     */
    public static String config="config.properties";
    /**
     * 数据库链接器
     */
    public static ConnectedDatabase conData;//数据库连接类
    public static boolean initCDB=false;//数据库链接成功标志
    public static boolean loginOrnot=false;//登录成功标志
    public static boolean initOrnot=false;//初始化成功标志
    public static int loginMan;//登录者emp_id
    public static String loginManName;//登录者name_CN
    public static int loginPositionId;//登录者职位id
    public static JTree NaJTree;//导航树
    public static MainFrame mainFrame; //主窗口
    public static String sysName;//ERP名
    public static ArrayList<PkgClass> jutisArrayList;  
    public static ClientServerClass clientServer;  
    public static boolean  clientServerOrNot=false;
    public static boolean msgGetOrNot=false;
    public static int msgCount=0;
    public static RCServer rcs;
    public static volatile boolean remoteLinkOrNot=false;
    public static class ScreenSize{  //系统屏幕大小信息
        public static int width;//宽
        public static int height;//高
        public static int ren;//任务栏高度
    }
    public static Locale locale=new Locale("en", "US");
    
}
