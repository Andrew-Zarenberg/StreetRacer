import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.*;

public class DodgeChallenger extends Car {
  public static final int LENGTH = 86;
  public static final int WIDTH = 40;
  
  private Image _image = (new ImageIcon(this.getClass().getResource("challenger.gif"))).getImage();
  
  
  public DodgeChallenger(int x, int y, int laneWidth){
    super(x, y, LENGTH, WIDTH, laneWidth);
    _carType = "Dodge Challenger";
  }
  
  
  public DodgeChallenger(int x, int y, double angle, int speed, int desiredSpeed, int lane, int laneWidth){
    super(x, y, LENGTH, WIDTH, angle, speed, desiredSpeed, lane, laneWidth);
    _carType = "Dodge Challenger";
  }
  
  
  public void draw(Graphics2D g2){
    g2.rotate(Math.toRadians(_angle),_x+_width/2,_y+_length-_length/10);
    
    int x = (int)_x;
    int y = (int)_y;
    
    // car background
    g2.drawImage(_image, x-2, y, null);
    
    // brake lights
    
    
    if(_brakeLight || _lights){
      if(_brakeLight) g2.setColor(Color.red);
      else if(_lights) g2.setColor(new Color(247, 159, 159));
      g2.fillRect(x+6,y+_length-5,4,5);
      g2.fillRect(x+_width-13,y+_length-5,4,5);
    }
    
    
    if(_signalOn){
      // LEFT signal light
      if(_signalLeft){
        g2.setColor(Color.yellow);
        g2.fillRect(x+2,y+_length-5,4,5);
        g2.fillPolygon(new int[]{x+4,x+1,x+4,x+8},
                       new int[]{y+1,y+9,y+9,y+1},
                       4);
      }
      // RIGHT signal light
      if(_signalRight){
        g2.setColor(Color.yellow);
        g2.fillRect(x+_width-9,y+_length-5,4,5);
        g2.fillPolygon(new int[]{x+_width-4,x+_width-1 ,x+_width-4 ,x+_width-8},
                       new int[]{y+1,y+9,y+9,y+1},
                       4);
      }
    }
    
    
    // HEADLIGHTS
    if(_lights || (_police && _sirenPhase%2 == 0)){
      g2.setColor(Color.white);
      g2.fillPolygon(new int[]{x+4,x+1,x+4,x+8},
                     new int[]{y+1,y+9,y+9,y+1},
                     4);
      if(_lights){
        g2.setColor(new Color(255,255,255,40));
        g2.fillPolygon(new int[]{x+1,x-20,x+30,x+8},
                       new int[]{y+9,y-60,y-60,y+1},
                       4);
      }
    }
    
    if(_lights || (_police && _sirenPhase%2 == 1)){
      g2.setColor(Color.white);
      g2.fillPolygon(new int[]{x+_width-9,x+_width-6 ,x+_width-9 ,x+_width-13},
                     new int[]{y+1,y+9,y+9,y+1},
                     4);
        
      if(_lights){
        g2.setColor(new Color(255,255,255,40));
        g2.fillPolygon(new int[]{x+_width-1,x+_width+20,x+_width-30,x+_width-8},
                       new int[]{y+9,y-60,y-60,y+1},
                       4);
      }
    }
    
    
    
    // SIREN
    if(_police){
      if((_sirenPhase/2)%2 == 0) g2.setColor(Color.blue);
      g2.fillRect(x+3,y+32,12,6);
      
      if((_sirenPhase/2)%2 == 1) g2.setColor(Color.red);
      else g2.setColor(Color.lightGray);
      g2.fillRect(x+15,y+32,12,6);
      
      
      if(_sirenPhase == 1 || _sirenPhase == 3){
        g2.setColor(Color.red);
        g2.fillRect(x+6,y+_length-5,4,5);
        g2.fillRect(x+_width-10,y+_length-5,4,5);
        
      } else if(_sirenPhase == 5 || _sirenPhase == 7){
        g2.setColor(Color.white);
        g2.fillRect(x+2,y+_length-5,4,5);
        g2.fillRect(x+_width-6,y+_length-5,4,5);
      }
      
      if(_sirenPhase%2 == 0){
        g2.setColor(Color.blue);
        g2.fillRect(x+8,y+1,7,3);
      } else {
        g2.setColor(Color.red);
        g2.fillRect(x+15,y+1,7,3);
      }
      
    }
    
    g2.rotate(-Math.toRadians(_angle),_x+_width/2,_y+_length-_length/10);
    if(debug) drawDebug(g2);
    
  }
}
