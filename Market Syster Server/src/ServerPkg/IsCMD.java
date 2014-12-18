/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ServerPkg;

/**
 *
 * @author Administrator
 */
public class IsCMD {
    public static boolean isCmd(String cmd){
        switch(cmd){
            case "exit":return true;
                case "START":return true;
        }
        return false;
    }
    
}
