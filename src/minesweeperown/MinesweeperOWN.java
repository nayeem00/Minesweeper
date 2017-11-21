package minesweeperown;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

class Box extends JButton{
    String boxStatus;
    
    Box(){
        this.boxStatus="Unchecked";
    }
    
    public void setBoxStatus(String boxStatus){this.boxStatus=boxStatus;}
    public String getBoxStatus(){return this.boxStatus;}
}

class TimeCount{
    String timeCount="00:00:00";
    int hour,minute,second;
    String sHour,sMinute,sSecond;
    public void intialAgain(){
        timeCount="00:00:00";
        hour=0;
        minute=0;
        second=0;
    }
    public void increaseOneSecound(){
        second++;
        if(second==60){
            minute++;
            second=0;
        }
        if(minute==60)
        {
            hour++;
            minute=0;
        }
        
        if(second<10){
            sSecond="0";
        }
        else{
            sSecond="";
        }
        if(minute<10){
            sMinute="0";
        }
        else{
            sMinute="";
        }
        if(hour<10){
            sHour="0";
        }
        else{
            sHour="";
        }
        
        
        this.settimeCount(sHour+hour+":"+sMinute+minute+":"+sSecond+second);
    }
    public int  getTotalSecond(){
        int temp = (hour*3600)+(minute*60)+second;
        return temp;
    }
    public void settimeCount(String timeCount){this.timeCount=timeCount;}
    public String gettimeCount(){return this.timeCount;}
}

class bestScoreCompare{
    File bestScore;
    bestScoreCompare(){
        bestScore =  new File("bestScore.txt");
        
    }
    
    public int readPreviousScore() {
        try{
            if (!bestScore.exists()){
              bestScore.createNewFile();
          }
             
          }
          catch(IOException f){
              f.printStackTrace();
          }
        
          Scanner s = null;
          try{
             s =  new Scanner(bestScore); 
             
          }
          catch(FileNotFoundException f){
              f.printStackTrace();
          }
          
          
          
          
        if (s.hasNext()){
            String temp  =  s.next();
            int temp1 =  Integer.parseInt(temp);
            return temp1;
            
          }
          else{
              return 9999;
          }  
          
    }
    
    public void writeNewScore(int score) {
        String temp = String.valueOf(score);
        try{
            BufferedWriter b =  new BufferedWriter(new FileWriter(bestScore));
            b.write(temp);
            b.close();
        }
        catch(IOException e){
            
        }
        
    }
    
    public boolean check(int score) {
            if(score<this.readPreviousScore())
            {
                this.writeNewScore(score);
                return true;
            }
            else{
                return false;
            }
    }
    public void reSetBestScore(){
        try{
            BufferedWriter b =  new BufferedWriter(new FileWriter(bestScore));
            b.write("999999999");
            b.close(); 
        }
        catch(IOException e){
            
        }
        
    }
}

 class mainWindowFrame extends JFrame {
    Box B[][] = new Box[9][9];
    int mainWindowLocationX,mainWindowLocationY;
    Popup popupWindow = new Popup();
    JLayeredPane boxLayer = new JLayeredPane();
    JLayeredPane otherLayer = new JLayeredPane();
    JTextField bombMarkShow=  new JTextField();
    JTextField timeShow=  new JTextField();
    int bombMarkCounter;
    boolean firstClick = false;
    boolean CheckNewBest = false;
    TimeCount newTC = new TimeCount();
    Timer T;
    JLabel timerIcon = new JLabel(new ImageIcon("timer.jpg"));
    JLabel bombMarkIcon = new JLabel(new ImageIcon("MarkBomb.png"));
    bestScoreCompare BSC =  new bestScoreCompare();
    
    
    
    public mainWindowFrame(){
        manageComponents();
    }
    

    
    public void setCurrentLocation(){
        Point p = this.getLocation(null);
        
        Double x = p.getX();
        Double y=p.getY();
        this.mainWindowLocationX=x.intValue();
        this.mainWindowLocationY=y.intValue();
        
    }
    
    public void reSetAllBox(){
       
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                B[i][j].setBoxStatus("Unchecked");
                B[i][j].setBackground(new Color(0,51,204));
                B[i][j].setText("");
                B[i][j].setIcon(null);
            }
            
        }
        
      this.firstClick=false;
      this.CheckNewBest =  false;
      newTC.intialAgain();
      timeShow.setText(newTC.gettimeCount());
      bombMarkCounter=10;
      bombMarkShow.setText("   "+bombMarkCounter);
      T.start();
    }
    

    private void manageComponents(){
        this.mainWindowLocationX=380;
        this.mainWindowLocationY=230;
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Minesweeper By Nayeem");
        this.setBackground(Color.white);
        this.setResizable(false);
        this.setSize(546,646);
        this.setLocation(this.mainWindowLocationX, this.mainWindowLocationY);
        this.setLayout(null);
        
        
        
        
      
        boxLayer.setBackground(Color.white);
        boxLayer. setSize(540,540);
        boxLayer.setLocation(0, 0);
        this.add(boxLayer);
        boxLayer.setLayout(new GridLayout(9,0));
        boxLayer.setVisible(true);
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                int temp =  (i*10)+j;
                B[i][j] =  new Box();
                B[i][j].setBackground(new Color(0, 51, 204));
                B[i][j].setSize(60,60);
                
                B[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                      AnyButtonActionPerformed(evt,temp);
                     
                    }
                });
                
                B[i][j].addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent evt){
                        MouseRightButtonClicked(evt,temp);
                    }
                });
                boxLayer.add(B[i][j]);
                
            }
        }
        
        
        this.add(otherLayer);
        otherLayer.setVisible(true);
        otherLayer.setLocation(0, 541);
        otherLayer.setSize(540,124);
        otherLayer.setBackground(Color.white);
        otherLayer.setLayout(null);
        otherLayer.add(bombMarkShow);
        bombMarkShow.setSize(120, 80);
        bombMarkShow.setLocation(420, 0);
        bombMarkShow.setEditable(false);
        bombMarkShow.setBackground(Color.white);
        bombMarkShow.setFont(new Font("Tahoma", 1, 40));
        otherLayer.add(timeShow);
        timeShow.setSize(190, 80);
        timeShow.setEditable(false);
        timeShow.setBackground(Color.white);
        timeShow.setFont(new Font("Tahoma", 1, 40));
        timerIcon.setLocation(185, 0);
        timerIcon.setSize(80,80);
        otherLayer.add(timerIcon);
        
        bombMarkIcon.setLocation(330, 0);
        bombMarkIcon.setSize(85,85);
        otherLayer.add(bombMarkIcon);
        
        T =  new Timer(1000,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
                 {
                   timerDo();
                 }
        });
        this.reSetAllBox();
    }
    
    
    public void timerDo(){
        
        newTC.increaseOneSecound();
        timeShow.setText(newTC.gettimeCount());
        
        
    }
    public int getTimeSpend(){
        return newTC.getTotalSecond();
    }
    public boolean getCheckWin(){
        return CheckNewBest;
    }
    
    public void setRandomBomb(int i){
        Random r = new Random();
          int counter=0,Bomb=0;
          int alreadyInputedValueCheck[]= new int[50];
          alreadyInputedValueCheck[counter++]=i;
          alreadyInputedValueCheck[counter++]=i+1;
          alreadyInputedValueCheck[counter++]=i-1;
          alreadyInputedValueCheck[counter++]=i+9;
          alreadyInputedValueCheck[counter++]=i-9;
          alreadyInputedValueCheck[counter++]=i+10;
          alreadyInputedValueCheck[counter++]=i-10;
          alreadyInputedValueCheck[counter++]=i+11;
          alreadyInputedValueCheck[counter++]=i-11;
          Outer:
          for (int j=0;true;j++){
              
            int randomRow = r.nextInt(9);
            int randomColumn = r.nextInt(9);
            
            int temp = (randomRow*10)+randomColumn;
            for(int z=0;z<counter;z++)
            {
                if(alreadyInputedValueCheck[z]==temp)
                {
                    continue Outer;
                    
                }
            }
            
                alreadyInputedValueCheck[counter++]=temp;
                
                B[randomRow][randomColumn].setBoxStatus("Bomb");
                Bomb++;
                if(Bomb==10){
                    break;
                }
            
            
            
        }
          
    }
    
    
    private void MouseRightButtonClicked(MouseEvent evt,int i){
        int boxRow = i / 10 ;
        int boxColum = i % 10 ;
        
        if (evt.getButton() == 3) {
            if(B[boxRow][boxColum].getBoxStatus().equals("Unchecked") || B[boxRow][boxColum].getBoxStatus().equals("Bomb")){
                if(B[boxRow][boxColum].getText().equals("B"))
                {
                    B[boxRow][boxColum].setText("?");
                    B[boxRow][boxColum].setFont(new Font("Tahoma", 1, 40));
                    B[boxRow][boxColum].setForeground(Color.white);
                    bombMarkCounter++;
                    bombMarkShow.setText("   "+bombMarkCounter);
                }
                else if(B[boxRow][boxColum].getText().equals("?"))
                {
                    B[boxRow][boxColum].setText("");
                }
                       
                else 
                {
                    B[boxRow][boxColum].setText("B");
                    B[boxRow][boxColum].setFont(new Font("Tahoma", 1, 32));
                    B[boxRow][boxColum].setForeground(Color.red);
                    bombMarkCounter--;
                    bombMarkShow.setText("   "+bombMarkCounter);
                    
                }
            }
            
            
        }
    }
    
    
   private void AnyButtonActionPerformed(ActionEvent evt,int i){                                   
       forFirstClick(i);
       checkAction(i);
      
    } 
   
   public void forFirstClick(int i){
       if(this.firstClick==false)
       {
           this.setRandomBomb(i);
       }
       this.firstClick=true;
   }
   
   public void doAction(String actionString){
       String BombCounter = actionString.substring(1, 2);
       String boxNo=actionString.substring(2);
       int currentBoxNo= Integer.parseInt(boxNo);
       int boxRow = currentBoxNo / 10 ;
       int boxColum = currentBoxNo % 10 ;
       String choose = actionString.substring(0, 1);
       
       switch(choose){
           case "A":bombClic(currentBoxNo);
                    this.setCurrentLocation();
                    popupWindow.setsms("Sorry u Lose , You just Clicked a Bomb",mainWindowLocationX,mainWindowLocationY);
                    break; //Bomb
               
           case "B":B[boxRow][boxColum].setBackground(Color.white);
                    winCheck();break;//No surrounging bomb
           case "C":
                    B[boxRow][boxColum].setFont(new Font("Tahoma", 1, 36));
                    B[boxRow][boxColum].setText(BombCounter);
                    switch(BombCounter){
                        case "1":B[boxRow][boxColum].setForeground(Color.black);break;
                        case "2":B[boxRow][boxColum].setForeground(Color.green);break;
                        case "3":B[boxRow][boxColum].setForeground(Color.blue);break;
                        case "4":B[boxRow][boxColum].setForeground(Color.yellow);break;
                        case "5":B[boxRow][boxColum].setForeground(Color.red);break;
                    }
               
                    B[boxRow][boxColum].setBackground(Color.white);
                    winCheck();
                    break;// surrounding bomb
           case "D":break;//Do nothing 
           
       }
    }
   
   
    public void checkAction(int enteredBoxNumber){
                
                
                int bombCounter =0;
                int boxRow = enteredBoxNumber / 10 ;
                int boxColum = enteredBoxNumber % 10 ;
                if(B[boxRow][boxColum].getText().equals("B"))
                {
                   doAction("D"+bombCounter+enteredBoxNumber);
                }
                
                else if(B[boxRow][boxColum].getText().equals("?"))
                {
                   doAction("D"+bombCounter+enteredBoxNumber);
                }
                
                else if (B[boxRow][boxColum].getBoxStatus().equals("Checked"))
                {
                    doAction("D"+bombCounter+enteredBoxNumber);
                    
                }

                else if (B[boxRow][boxColum].getBoxStatus().equals("Bomb"))
                {
                    doAction("A"+"0"+enteredBoxNumber);
                    
                }

                else if (B[boxRow][boxColum].getBoxStatus().equals("Unchecked"))
                {

                    for(int i=0;i<9;i++)
                    {
                        for(int j=0;j<9;j++)
                        {
                            if((i== boxRow || i== boxRow+1 || i== boxRow-1) && (j==boxColum || j==boxColum+1 || j==boxColum-1)  )
                            {
                                if(B[i][j].getBoxStatus().equals("Bomb"))
                                {
                                    bombCounter++;
                                }
                                

                            }
                        }
                    }
                    if (bombCounter==0)
                    {
                        
                        B[boxRow][boxColum].setBoxStatus("Checked");
                        doAction("B"+"0"+enteredBoxNumber);
                        
                        for(int x=0;x<9;x++)
                        {
                            for(int y=0;y<9;y++)
                            {
                                if((x== boxRow || x== boxRow+1 || x== boxRow-1) && (y==boxColum || y==boxColum+1 || y==boxColum-1)  )
                                {
                                    int temp = (x*10)+y;
                                    checkAction(temp);
                                   
                                }
                            }
                        }
                        
                    }
                    if (bombCounter>0)
                    {
                        B[boxRow][boxColum].setBoxStatus("Checked");
                        doAction("C"+bombCounter+enteredBoxNumber);
                        
                    }
                    

                }

                
        
    }
    
    
    public void bombClic(int blastedBombLocation){
            T.stop();
            for(int x=0;x<9;x++)
            {
              for(int y=0;y<9;y++)
              {
                    
                   if(B[x][y].getBoxStatus().equals("Bomb"))
                   {
                       int temp = (x*10)+y;
                       if (blastedBombLocation==temp)
                       {
                           B[x][y].setBackground(Color.ORANGE);
                           B[x][y].setIcon(new ImageIcon("blastedBomb.png"));
                       }
                       else
                       {
                           B[x][y].setBackground(Color.red);   
                           B[x][y].setIcon(new ImageIcon("bomb.png"));
                       }
                     
                   }
                   if(B[x][y].getBoxStatus().equals("Unchecked") && B[x][y].getText().equals("B"))
                   {
                       B[x][y].setIcon(new ImageIcon("wrongmark.png"));
                   }
                   if(B[x][y].getText().equals("B")||B[x][y].getText().equals("?"))
                    {
                        B[x][y].setText("");
                    }
                   
                }
            }
        }
    
    
    public void winCheck() {
        int Counter=0;
        for(int x=0;x<9;x++)
            {
                for(int y=0;y<9;y++)
                {
                   if(B[x][y].getBoxStatus().equals("Checked") || B[x][y].getBoxStatus().equals("Bomb"))
                   {
                       
                      Counter++;
                       
                   }
                }
            }
        if(Counter==81)
        {
            T.stop();
           this.setCurrentLocation();
           if(BSC.check(this.getTimeSpend())){
           CheckNewBest =  true;
           }
           
           popupWindow.setsms("            Congratulation , You win               ",mainWindowLocationX,mainWindowLocationY);
           
        }
    }
 }


public class MinesweeperOWN extends JFrame {
    
    static mainWindowFrame mainWindow = new mainWindowFrame();
    
   
    public static void main(String[] args) {
        
        mainWindow.setVisible(true);
        
    }

    
}
