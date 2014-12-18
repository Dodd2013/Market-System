/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataReadPkg;

import ServerPkg.SocketInfo;
import ServerPkg.UserInfo;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

/**
 *
 * @author Administrator
 */
public class DataOnly {
    public static boolean RemoteControlOrNot=true;
    public static String config="serverconfig.properties";
    public static Hashtable<Integer, SocketInfo> socketInfoHashtable=new Hashtable<>();
    public static Hashtable<Integer, UserInfo> userInfoHashtable=new Hashtable<>();
    public static PrintWriter out;
    public static String getNowTime(){
       return new SimpleDateFormat("HH:mm:ss yyyy-MM-dd").format(new Date());
    }
    public static Locale locale=new Locale("en", "US");
}
