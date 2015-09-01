import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;

public class PedestrianLight {
  int x,y,_state,_size,_num;
  
  /* _state:
   * 1 = walk
   * 2 = don't walk flashing
   * 3 = don't walk
   */
  
  public PedestrianLight(int state, int num, int size){
    _state = state;
    _num = num;
    _size = size;
  }
  
  public PedestrianLight(int size){
    _state = 1;
    _num = 0;
    _size = size;
  }
  
  public void draw(Graphics2D g2, int x, int y){
    g2.setColor(Color.black);
    g2.fillRect(x,y,_size*3/2,_size);
    
    g2.setColor(Color.yellow);
    g2.setStroke(new BasicStroke(4));
    g2.drawRect(x+3,y+3,_size*3/2-6,_size-6);
    
    if(_state == 1){
      g2.setColor(Color.orange);
      g2.setFont(new Font("Arial",Font.BOLD,_size-14));
      g2.drawString("20",x+_size/3*2-4,y+_size-14);
    }
  }
}