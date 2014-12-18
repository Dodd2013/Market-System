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
public class UserInfo {
    public String userId;
    public String userName;
    public SocketInfo socketInfo;
    public String internetadds;
    public String localadds;
    public String clientTime;

    public UserInfo(String userId, String userName, SocketInfo socketInfo, String internetadds, String localadds, String clientTime) {
        this.userId = userId;
        this.userName = userName;
        this.socketInfo = socketInfo;
        this.internetadds = internetadds;
        this.localadds = localadds;
        this.clientTime = clientTime;
    }
    
}
