/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataReadPkg;

import ServerPkg.SocketInfo;
import ServerPkg.UserInfo;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

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
       return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
