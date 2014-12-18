/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientServerPkg;

import DataReadpkg.ConfigRead;
import DataReadpkg.DataOnly;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Dodd-pc
 */
public class ClientServerClass {
    public  Socket socket;
    public BufferedReader in;
    public String cmd;
    public PrintWriter out;
    public GetMessageThread getMessageThread;
    public ClientServerClass() throws IOException{
        String serverIp=ConfigRead.readValue(DataOnly.config,"ServerIp");
        String serverPort=ConfigRead.readValue(DataOnly.config,"ServerPort");
           socket=new Socket(serverIp,Integer.valueOf(serverPort));
           in=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
           out=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
           out.println("LINK:"+ConfigRead.readValue(DataOnly.config, "LinkPassWord"));
           out.flush();
    }
    public void startGetMessage(){
        if(!DataOnly.msgGetOrNot){
           getMessageThread=new GetMessageThread();
           getMessageThread.start();
           DataOnly.msgGetOrNot=true;
        }
        
    }
    public void stopGetMessage(){
        if(DataOnly.msgGetOrNot){
           getMessageThread.interrupt(); 
           DataOnly.msgGetOrNot=false;
        }
        
    }
    class GetMessageThread extends Thread{
        @Override
        public void run(){
            while(true){
                try {
                    cmd=in.readLine();
                    if(cmd!=null)ServerCMD.excCMD(cmd);
                } catch (IOException ex) {
                    if(ex.getMessage().equals("Connection reset"))ServerCMD.excCMD("CMD:exit");
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
