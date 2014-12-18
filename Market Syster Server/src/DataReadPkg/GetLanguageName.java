/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataReadPkg;

import java.util.ResourceBundle;

/**
 *
 * @author Doddc
 */
public class GetLanguageName {
    public static ResourceBundle res;
    public static String getName(String keyString){
        res=ResourceBundle.getBundle("Language",DataOnly.locale);
        return res.getString(keyString);
    }
}
