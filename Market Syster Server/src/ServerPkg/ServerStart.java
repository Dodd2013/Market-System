/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerPkg;

import DataReadPkg.ConfigRead;
import DataReadPkg.DataOnly;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Dodd-pc
 */
public class ServerStart extends Thread{
    public ServerSocket serverSocket;
    ServerMainFrame mainFrame;
    public ServerStart(ServerMainFrame m) {
        mainFrame=m;
    }
    @Override
    public void run(){
        int count=1;
        Socket s;
        PrintWriter out;
        BufferedReader in;
        SocketInfo socketInfo;
        DataInputStream dataIn;
        ObjectOutputStream objOut;
        ByteArrayInputStream baIn;
        try {
            serverSocket=new ServerSocket(45678);
            while(true){
                mainFrame.jTextArea.append(DataOnly.getNowTime()+":等待连接！\n");
                s=serverSocket.accept();
                out=new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"),true);
                in=new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
                socketInfo=new SocketInfo(s,out,in);
                DataOnly.socketInfoHashtable.put(count,socketInfo);
                new GetMessageThread(socketInfo,mainFrame,count).start();
                mainFrame.jTextArea.append(DataOnly.getNowTime()+":第"+count+"个连接已建立！\n");
                count++;
            }
        } catch (IOException ex) {
            if(!ex.getMessage().equals("socket closed"))
            JOptionPane.showMessageDialog(null, "无法在该端口创建服务！"+ex.getMessage());
            else{
                DataOnly.socketInfoHashtable.remove(count);
            }
        }
        
    }
    
    class GetMessageThread extends Thread{
        SocketInfo socketInfo;
        String msg;
        int socketId;
        ServerMainFrame mainFrame;
        public GetMessageThread(SocketInfo socketInfo,ServerMainFrame m,int socketId) {
            this.socketInfo=socketInfo;
            this.socketId=socketId;
            this.mainFrame=m;
        }
        public void run(){
            
            try {
                handleSocket();
            } catch (IOException ex) {
                //Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        public void handleSocket() throws IOException{
            boolean isLink=true;
            boolean isFirst=true;
            while(isLink){
                if(isFirst)
                    if(isFirst){
                        socketInfo.socket.setSoTimeout(10000);
                        try {
                            msg=socketInfo.in.readLine();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                            if (e.getMessage().equals("Read timed out")) {
                                mainFrame.jTextArea.append(DataOnly.getNowTime()+":第"+socketId+"个链接验证超时！不是ERP系统！\n");
                            }
                        }
                        if(msg!=null||msg.equals("LINK:"+ConfigRead.readValue(DataOnly.config, "LinkPassWord"))){
                            mainFrame.jTextArea.append(DataOnly.getNowTime()+":第"+socketId+"个链接验证成功,是ERP系统！\n");
                            socketInfo.socket.setSoTimeout(0);
                            isFirst=false;
                        }else if(msg!=null||msg.equals("RemoteControl:"+ConfigRead.readValue(DataOnly.config, "LinkPassWord"))){
                            mainFrame.jTextArea.append(DataOnly.getNowTime()+":第"+socketId+"个链接验证成功,是远程系统！\n");
                        }else{
                            
                            if(msg!=null&&msg.startsWith("LINK:"))
                            mainFrame.jTextArea.append(DataOnly.getNowTime()+":第"+socketId+"个链接验证密钥错误！是ERP系统！\n");
                            else{
                                 mainFrame.jTextArea.append(DataOnly.getNowTime()+":第"+socketId+"个链接验证错误！不是ERP系统！\n");
                            }
                            isLink=false;
                            socketInfo.in.close();
                            socketInfo.out.close();
                            socketInfo.socket.close();
                            DataOnly.socketInfoHashtable.remove(socketId);
                            break;
                        }
                        
                    }
                
                msg=socketInfo.in.readLine();
              //System.out.println(msg);
                MsgHandle.dealWithMsg(msg, mainFrame,socketId,this);
                }
        }
    }
}
