/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MainPkg;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Administrator
 */
public class NewTab {
    public NewTab(MainFrame mainFrame,String title){
        JTabbedPane j=mainFrame.jTabbedPane;
        j.addTab(title,GetPanel.getPanel(title));
        j.setSelectedIndex(j.getTabCount()-1);
        j.setTabComponentAt(j.getTabCount()-1, new TabPanel(title, j));
    }
    public NewTab(MainFrame mainFrame,int panelid){
        JTabbedPane j=mainFrame.jTabbedPane;
        j.addTab(GetPanel.getPanelName(panelid),GetPanel.getPanel(panelid));
        j.setSelectedIndex(j.getTabCount()-1);
        j.setTabComponentAt(j.getTabCount()-1, new TabPanel(GetPanel.getPanelName(panelid), j));
    }
}
 
class TabPanel extends JPanel{
    private JLabel title;
    private CloseButton closebutton;
    private final JTabbedPane pane;

    public TabPanel(String s,JTabbedPane pane){
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        title=new JLabel(s);
        this.pane=pane;
        closebutton=new CloseButton();
        add(title);
        add(closebutton);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setOpaque(false);
    }

    private class CloseButton extends JButton {
        private ImageIcon icon;
        public CloseButton(){
            
            URL resource = getClass().getResource("/Image/Button/close.png");
            icon=new ImageIcon(resource);
            setSize(icon.getImage().getWidth(null),icon.getImage().getHeight(null));
            setIcon(icon);
            setBorder(null);
            setBorderPainted(false);
            setFocusPainted(false);
            setPressedIcon(new ImageIcon(getClass().getResource("/Image/Button/close_pressed.png")));
            setRolloverIcon(new ImageIcon(getClass().getResource("/Image/Button/close_rollover.png")));
            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    pane.remove(pane.indexOfTabComponent(TabPanel.this));
                    if (pane.getTabCount()==0) {
                       // pane.addTab("空", null);
                    }
                }
            });
        }
    }

}
