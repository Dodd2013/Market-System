/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataBasePkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import DataReadpkg.ConfigRead;
import DataReadpkg.DataOnly;
import java.sql.Statement;
/**
 *
 * @author QDU-01
 */
public class ConnectedDatabase {
    public  Connection con;
    public  Statement stmt;
    public  ConnectedDatabase() throws ClassNotFoundException, SQLException{
        
        String databasekind=ConfigRead.readValue(DataOnly.config,"databaseName");
        if(databasekind.equals("SQL"))
        {
            String ClassName=ConfigRead.readValue(DataOnly.config, "SQLClassName");
            String url=ConfigRead.readValue(DataOnly.config, "SQLUrl");
            Class.forName(ClassName);
            con=DriverManager.getConnection(url);
        }
        if(databasekind.equals("MYSQL")){
            String ClassName=ConfigRead.readValue(DataOnly.config, "MYSQLClassName");
            String url=ConfigRead.readValue(DataOnly.config, "MYSQLUrl");
            String MYSQLid=ConfigRead.readValue(DataOnly.config,"MYSQLid");
            String MYSQLpsd=ConfigRead.readValue(DataOnly.config,"MYSQLpsd");
            Class.forName(ClassName);
            con=DriverManager.getConnection(url,MYSQLid,MYSQLpsd);
        }
        
       // System.out.println("gdsafdas");
        stmt=con.createStatement();
    }
}
