
package minesweeperown;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static minesweeperown.MinesweeperOWN.*;


public class Popup extends JFrame {
        JLabel label = new JLabel();
        JButton reNewButton = new JButton();
        JButton exitButton = new JButton();
        int popupWindowLocationX,popupWindowLocationY;
        JLabel bestPrint;
        
    public Popup() {
         initComponents();
     }
    
    public void setsms(String s,int x, int y){
        label.setFont(new Font("Tahoma", 1, 24));
        label.setText(s);
        this.popupWindowLocationX=x+25;
        this.popupWindowLocationY=y+190;
        this.setLocation(popupWindowLocationX,popupWindowLocationY);
        
        this.setVisible(true);
        mainWindow.setEnabled(false);
        if(mainWindow.CheckNewBest==true){
            bestPrint.setText("New Best "+mainWindow.getTimeSpend()+" Sec!!");
        }
        else{
            bestPrint.setText("");
        }
        
        
        
    }
  
    
    
    private void initComponents(){
        
        this.setContentPane(new JLabel(new ImageIcon("popupWidowBacgroud.jpg")));
        this.setSize(490,140);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.add(label);
        this.add(reNewButton);
        this.add(exitButton);
        this.setLocation(popupWindowLocationX,popupWindowLocationY);
        
        label.setFont(new Font("Tahoma", 0, 24));
        label.setSize(470,40);
        label.setLocation(10,0);
        
        bestPrint=  new JLabel();
        this.add(bestPrint);
        bestPrint.setSize(200, 30);
        bestPrint.setLocation(150, 80);
        bestPrint.setFont(new Font("Tahoma", 1, 20));
        bestPrint.setForeground(Color.white);
        
        reNewButton.setBackground(new Color(102, 102, 255));
        reNewButton.setFont(new Font("Tahoma", 1, 24)); 
        reNewButton.setText("New Game");
        reNewButton.setSize(170, 40);
        reNewButton.setLocation(80, 40);
        reNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reNewButtonActionPerformed(evt);
            }
        });

        exitButton.setBackground(new Color(255, 51, 102));
        exitButton.setFont(new Font("Tahoma", 1, 24));
        exitButton.setText("Exit");
        exitButton.setSize(120, 40);
        exitButton.setLocation(290, 40);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
         
    }
     private void reNewButtonActionPerformed(ActionEvent evt) {                                         
            mainWindow.reSetAllBox();
            this.setVisible(false);
            mainWindow.setEnabled(true);
            mainWindow.setVisible(true);
            
    }                                        

    private void exitButtonActionPerformed(ActionEvent evt) {                                         
        System.exit(0);
    } 
}
