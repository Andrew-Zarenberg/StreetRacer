import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class TrafficLight {
  int _col,_size;
  
  public TrafficLight(int col, int size){
    _col = col;
    _size = size;
  }
  
  public void draw(Graphics2D g2, int x, int y){
    draw(g2, x, y, 0);
  }
  
  public void draw(Graphics2D g2, int x, int y, int rotate){
    
    g2.rotate(Math.toRadians(rotate),x,y);
    
    g2.setColor(new Color(94, 97, 11));
    g2.fillRect(x,y,_size,_size*5/2);
    
    int k = _size-14;
    int d = (_size*5/2-10)/3-k;
    
    // red light
    g2.setColor(_col==3?Color.red:Color.gray);
    g2.fillOval(x+7,y+5,k,k);
    
    // yellow light
    g2.setColor(_col==2?Color.yellow:Color.gray);
    g2.fillOval(x+7,y+5+d+k,k,k);
    
    // green light
    g2.setColor(_col==1?Color.green:Color.gray);
    g2.fillOval(x+7,y+5+d*2+k*2,k,k);
    
    
    g2.setStroke(new BasicStroke(4));
    g2.setColor(Color.black);
    g2.drawRect(x,y,_size,_size*5/2);
    
    g2.setStroke(new BasicStroke(1));
    g2.drawOval(x+7,y+5,k,k);
    g2.drawOval(x+7,y+5+d+k,k,k);
    g2.drawOval(x+7,y+5+d*2+k*2,k,k);
    
    g2.rotate(-Math.toRadians(rotate),x,y);
  }
  
  public int getHeight(){ return _size*5/2; }
  public int getWidth(){ return _size; }
}
