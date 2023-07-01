package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
    
    private boolean play = false;
    private int score = 0;
    
    private int totalBricks = 21;
    
    private Timer timer;
    private int delay = 8;
    
    private int playerX = 310;
    
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;
    
    public GamePlay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    /**
     * paint() Method to display the ball , the pedal and implementation of their controls
     * @param g
     */
    public void paint(Graphics g) {
        g.setColor(Color.white);    //Background Color
        g.fillRect(1, 1, 692, 592);     //Frame filled with Color
        
        
        map.draw((Graphics2D) g);       // Painting Map 
        
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);   //Top Border
        g.fillRect(0, 0, 692, 3);   //Left Side Border
        g.fillRect(692, 0, 3, 592); //Bottom Border
        
        // Attributes of the Pedal
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);
        
        //Attributes of Ball
        g.setColor(Color.green);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        
        
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString(""+ score, 590, 30);
        
        if(totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.BLUE);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("You Won! Score: "+ score, 190, 300);
            
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart.", 230, 350);
        }
        
        // When Ball crosses the bottom level
        if(ballPosY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over! Score: "+ score, 190, 300);
            
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart.", 230, 350);
        }
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        
        if(play) {
            
            // Ball - Pedal Interaction
            if(new Rectangle(ballPosX, ballPosY,20,20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = - ballYdir;
            }
            
            
            for(int i = 0; i < map.map.length;i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
                        Rectangle brickRect = rect;
                        
                        if(ballRect.intersects(brickRect)) {
                            map.setBrickvalue(0, i, j);
                            totalBricks--;
                            score += 5;
                            
                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = - ballYdir;
                            }
                        }
                    }
                }
            }
            
            //Ball Movement : Position of ball incremented by the ball X-, Y- directions parameter
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            
            
            //Bouncing Ball off the walls
            if(ballPosX < 0) 
                ballXdir = -ballXdir;
            
            if(ballPosY < 0) {
                ballYdir = -ballYdir;
            }
            
            if(ballPosX > 670)
                ballXdir = -ballXdir;
        }
        
        repaint();
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // When Right is Pressed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if(playerX >= 600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }
        
        // When Left is pressed
        if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if(playerX < 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        } 
        
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }
    }
    
    /**
     * Right Movement of Pedal
     */
    private void moveRight() {
        play = true;
        
        // Pedal move to right by increment of 20px.
        playerX += 20;
    }
    
    
    /**
     * Left Movement of Pedal
     */
    private void moveLeft() {
        play = true;
        
        // Pedal move to left by decrement of 20px.
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
        
}
