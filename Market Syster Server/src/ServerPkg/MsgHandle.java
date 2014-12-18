/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerPkg;

import DataReadPkg.DataOnly;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author QDU-01
 */
public class MsgHandle {
    public static void dealWithMsg(String msg,ServerMainFrame mainFrame,int socketId,Thread thread){
        if(msg.equals("EXIT")){
            mainFrame.jTextArea.append("第"+socketId+"个链接退出!");
            exit(socketId,thread,mainFrame);
        }
        if(msg.startsWith("LOGIN:")){
            boolean flag=false;
            String id=msg.substring(msg.lastIndexOf("id:")+3, msg.indexOf(";password:"));
            String userName=msg.substring(msg.lastIndexOf(";name:")+6);
            for(int i=0;i<mainFrame.tableModel.getRowCount();i++){
              if(mainFrame.tableModel.getValueAt(i, 5).equals(id)) {
                  flag=true;
              }
            }
            if(!flag){
                SocketInfo socketInfo=DataOnly.socketInfoHashtable.get(socketId);
                UserInfo userInfo=
                    new UserInfo(id, userName,socketInfo ,socketInfo.socket.getInetAddress().getHostAddress(), socketInfo.socket.getLocalAddress().getHostAddress(),DataOnly.getNowTime());
                 DataOnly.userInfoHashtable.put(socketId, userInfo);
                 Vector<String> v=new Vector<>();
                v.add(userName);
                v.add(id);
                v.add(userInfo.internetadds);
                v.add(userInfo.localadds);
                v.add(userInfo.clientTime);
                v.add(Integer.toString(socketId));
                //"账户名称","账户ID","外网地址","内网地址","加入时间","Sockid"
                mainFrame.tableModel.addRow(v);
                mainFrame.jTextArea.append(DataOnly.getNowTime()+":用户"+userName+"登录！\n");
                PrintWriter out= DataOnly.socketInfoHashtable.get(socketId).out;
                out.println("CMD:LoginOK");
                out.flush();
            }else{
                PrintWriter out= DataOnly.socketInfoHashtable.get(socketId).out;
                out.println("CMD:UserIsOnLine");
                out.flush();
            }
            
        }
        if(msg.startsWith("LOGOUT")){
            String userName=DataOnly.userInfoHashtable.get(socketId).userName;
            mainFrame.jTextArea.append(DataOnly.getNowTime()+":用户"+userName+"退出!\n");
            exit(socketId,thread,mainFrame);
        }
    }
    public static void exit(int socketId,Thread thread,ServerMainFrame mainFrame){
        try {
                DataOnly.socketInfoHashtable.get(socketId).in.close();
                DataOnly.socketInfoHashtable.get(socketId).socket.close();
                DataOnly.socketInfoHashtable.get(socketId).out.close();
            } catch (IOException ex) {
                Logger.getLogger(MsgHandle.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int rowsid=0;rowsid<mainFrame.tableModel.getRowCount();rowsid++){
                if(socketId==Integer.valueOf((String) mainFrame.tableModel.getValueAt(rowsid, 5))){
                    mainFrame.tableModel.removeRow(rowsid);
                }
            }
            thread.interrupt();
            DataOnly.socketInfoHashtable.remove(socketId);
    }
}
