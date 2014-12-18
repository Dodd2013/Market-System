/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainPkg;

import DataReadpkg.DataOnly;
import Toolspkg.PkgClass;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Dodd-pc
 */
public class NagJTreeRoot extends DefaultMutableTreeNode{
    public NagJTreeRoot() {
        super(DataOnly.sysName,true);
        PkgClass p=null;
        DefaultMutableTreeNode parent=new DefaultMutableTreeNode();
        if(!DataOnly.jutisArrayList.isEmpty()){
           p =DataOnly.jutisArrayList.get(0);
            parent=new DefaultMutableTreeNode(p.moduleGoup);
            add(parent);
        }
        for (PkgClass t : DataOnly.jutisArrayList) {
            if(!p.moduleGoup.equals(t.moduleGoup)){
                p.moduleGoup=t.moduleGoup;
                parent=new DefaultMutableTreeNode(p.moduleGoup);
                add(parent);
            }
            DefaultMutableTreeNode treeNode=new DefaultMutableTreeNode(t.id+t.Name);
            parent.add(treeNode);
        }
        
    }
    
}
