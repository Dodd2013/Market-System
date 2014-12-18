/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Toolspkg;

/**
 *
 * @author Dodd-pc
 */
public class CheckString {
    public static Boolean checkStringIsPassWord(String passWordString){
        int size=passWordString.length();
        if(size>=6&&size<=16){
            return true;
        }
        return false;
    }
    
}
