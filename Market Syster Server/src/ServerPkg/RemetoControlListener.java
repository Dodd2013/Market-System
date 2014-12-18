/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerPkg;

import RemoteControl.RCClient;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dodd-pc
 */
public class RemetoControlListener extends Thread{

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(18080);
        } catch (IOException ex) {
            Logger.getLogger(RemetoControlListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {
                Socket s=serverSocket.accept();
                new RCClient(s).start();
                System.out.println("fasgdsafewwe");
            } catch (IOException ex) {
                Logger.getLogger(RemetoControlListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
