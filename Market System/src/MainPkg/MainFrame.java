/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainPkg;

import DataReadpkg.DataOnly;
import DataReadpkg.GetLanguageName;
import OtherClassPkg.AboutDialog;
import Toolspkg.PkgClass;
import Toolspkg.SelectAllWhileGetFocusJTF;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Dodd
 * @version 0.0.1
 */
public class MainFrame {
    public JFrame maF;  //主窗口
    JTextField navigationJTF;  //快速导航框
    JMenuBar jmb; //菜单栏
    JScrollPane treeNavigationJSP;//树状导航面板
    JTree tree;  //导航树
    JSplitPane fiSplitPane;  //分割条
    JPanel leftjPanel;//分割条左面板
    JPanel rightUpjPanel;//分割条右上面板
    JPanel rightDownjPanel;//分割条右下面板
  //  JSplitPane reSplitPane;  //分割条
    public JTabbedPane jTabbedPane;
    MainFrame self;
    public MainFrame(){
        self=this;
        //init JFrame
        maF=new JFrame(GetLanguageName.getName("systemName")+"  User:"+DataOnly.loginManName);
        maF.setSize(DataOnly.ScreenSize.width,DataOnly.ScreenSize.height-DataOnly.ScreenSize.ren);
        maF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maF.setResizable(false);
        maF.setLocation(0, 0);
        
        
        //init mainMenu
        JMenu mainMenu=new JMenu(GetLanguageName.getName("mainMenu"));
        JMenuItem exiItem=new JMenuItem(GetLanguageName.getName("exit"));
        exiItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mainMenu.add(exiItem);
        
        
        
        //init helpmemu
        JMenu helpMenu=new JMenu(GetLanguageName.getName("help"));
        JMenuItem aboutItem=new JMenuItem(GetLanguageName.getName("about"));
        aboutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              new AboutDialog();
            }
        });
        JMenuItem helpItem=new JMenuItem(GetLanguageName.getName("helpDoc"));
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        
        
        //init jmb
        jmb=new JMenuBar();
        jmb.add(mainMenu);
        jmb.add(helpMenu);
        maF.setJMenuBar(jmb);
        
        
        //init leftjpanel
        leftjPanel =new JPanel();
        leftjPanel.setLayout(new BorderLayout());
        
        
        //init RUpanel
        rightUpjPanel=new JPanel();
        rightUpjPanel.setLayout(new BorderLayout());
        
        //init RDpanel
        rightDownjPanel=new JPanel();
        
        
        //init reSplitPane
//        reSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
//        reSplitPane.setLeftComponent(rightUpjPanel);
//        reSplitPane.setRightComponent(rightDownjPanel);
//        reSplitPane.setOneTouchExpandable(true);
//        reSplitPane.setDividerLocation(DataOnly.ScreenSize.height*2/3);
        
        
        //init fiSplitPane
        fiSplitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        fiSplitPane.setRightComponent(rightUpjPanel);
        fiSplitPane.setOneTouchExpandable(true);
        fiSplitPane.setDividerLocation(DataOnly.ScreenSize.width/6);
        fiSplitPane.setLeftComponent(treeNavigationJSP);
        fiSplitPane.setLeftComponent(leftjPanel);
        maF.getContentPane().add(BorderLayout.CENTER,fiSplitPane);
        
        //init tree
        tree=DataOnly.NaJTree;
        
        tree.addMouseListener(new MouseAdapter() {
     @Override
     public void mousePressed(MouseEvent e) {
         int selRow = tree.getRowForLocation(e.getX(), e.getY());
         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
         if(selRow != -1) {
             if(e.getClickCount() == 2) {
                DefaultMutableTreeNode node= (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                if(node.isLeaf()&&!node.isRoot())
                 new NewTab(self,Integer.valueOf(node.toString().substring(0, 4)));
             }
         }
     }
 });
        
        //init Jscollpanl
         treeNavigationJSP=new JScrollPane();
         treeNavigationJSP.setViewportView(tree);
         leftjPanel.add(treeNavigationJSP,BorderLayout.CENTER);
         
         
        //init navigationJTF
        navigationJTF=new JTextField();
        navigationJTF.addFocusListener(new SelectAllWhileGetFocusJTF());
        navigationJTF.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char keyChar=e.getKeyChar();
                if(keyChar=='\n'){
                    int id=-1;
                    for(PkgClass t:DataOnly.jutisArrayList){
                        if(t.id==Integer.valueOf( navigationJTF.getText()))id=Integer.valueOf( navigationJTF.getText());
                    }
                    if(id!=-1){
                        new NewTab(self,id);
                    }else{
                        
                    }
                }
                if (keyChar>=KeyEvent.VK_0 && keyChar<=KeyEvent.VK_9) {
			
		} else {
			e.consume();  
		}
            }
});
        leftjPanel.add(navigationJTF,BorderLayout.NORTH);
        
        //init Jtabbed
        jTabbedPane=new JTabbedPane();
        jTabbedPane.addTab("Navigation", new NagPanel());
        jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        rightUpjPanel.add(jTabbedPane);
        //setvisible
        
        maF.setVisible(true);
        
    }
    public static void main(String[] args) {
        
        new LoginMain();
        
    }
}
