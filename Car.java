import java.awt.*;

public class Car {
  
  // Automatically drive car to weave in and out and go fast without crashing
  private boolean _autoDrive, _justChanged, _finish = false; 
  private int _speedCarAhead = 0, _nspCount = 0, _yCarAhead = 0, _roadMin = -1, _roadMax = -1;
  
  public double _x, _y, _speed, _angle;
  public int _length, _width, _lane, _time, _laneWidth, _brakeNum, _tempDSpeed = -1;
  public Color _col;
  public int _desiredSpeed; // how fast the traffic car wants to go
  public boolean _brake, _brakeLight, _signalLeft, _signalRight, _police, _crash, _policeInfluence, _angleNoChange, _forceChangeLane;
  public boolean _signalOn; // if the signal is displayed or not
  
  public int _rand;
  
  public boolean _lights = false;
  
  public static final int LENGTH = 77;
  public static final int WIDTH = 40;
  public String _carType = "Car";
  
  public int _sirenPhase; // 4 phases of siren
  
  
  public boolean debug = false;
  
  public Car(int x, int y, int length, int width, Color col, int laneWidth, boolean police){
    _x = x+(laneWidth-WIDTH)/2;
    _y = y;
    _length = length;
    _width = width;
    _col = col;
    _angle = 0;
    _lane = 0;
    _laneWidth = laneWidth;
    
    _time = 0;
    _brakeNum = 0;
    _police = police;
    
    _rand = (int)(Math.random()*30)+40;
  }
  
  public Car(int x, int y, int length, int width, int laneWidth){
    _x = x+(laneWidth-WIDTH)/2;
    _y = y;
    _length = length;
    _width = width;
    _angle = 0;
    _lane = 0;
    _laneWidth = laneWidth;
    
    _time = 0;
    _brakeNum = 0;
    _police = false;
    
    _rand = (int)(Math.random()*50)+10;
  }
  
  
  public Car(int x, int y, int length, int width, double angle, int speed, int desiredSpeed, int lane, int laneWidth){
    _x = x+(laneWidth-WIDTH)/2;
    _y = y;
    _length = length;
    _width = width;
    _angle = angle;
    _speed = speed;
    _desiredSpeed = desiredSpeed;
    _lane = lane+(laneWidth-WIDTH)/2;
    _laneWidth = laneWidth;
    
    _time = 0;
    _brakeNum = 0;
    _police = false;
    
    _rand = (int)(Math.random()*50)+10;
  }
  
  public Car(int x, int y, int length, int width, Color col, double angle, int speed, int desiredSpeed, int lane, int laneWidth){
    _x = x+(laneWidth-WIDTH)/2;
    _y = y;
    _length = length;
    _width = width;
    _col = col;
    _angle = angle;
    _speed = speed;
    _desiredSpeed = desiredSpeed;
    _lane = lane+(laneWidth-WIDTH)/2;
    _laneWidth = laneWidth;
    
    _time = 0;
    _brakeNum = 0;
    _police = false;
    
    _rand = (int)(Math.random()*50)+10;
  }
  
  public Polygon getBoundsMe(){
    Polygon r;
    
    double n = Math.sin(Math.toRadians(_angle));
    double s = Math.cos(Math.toRadians(_angle));
    
    int x = (int)(_x-n*10);
    int y = (int)(_y-n*10);
    
    
    if(_angle < 0) r = new Polygon(new int[]{(int)(x+n*(_length-10)),x,(int)x+_width,(int)(x+s*_width+n*_length)},
                                   new int[]{(int)(_y+Math.abs(n*_width*3/2)),(int)y+_length,(int)y+_length,(int)(_y+Math.abs(n*_width/2)+n*20)},
                                   4);
    else r = new Polygon(new int[]{(int)(x+n*(_length-10)+n*20),(int)(x+n*10),(int)x+_width,(int)(x+s*_width+n*_length)},
                         new int[]{(int)(_y+Math.abs(n*_width)-n*20),(int)(y+_length-n*10),(int)(y+_length+n*20),(int)(_y+Math.abs(n*_width*3/2))},
                         4);
    
    return r;
  }
  
  public Rectangle getBounds(){
    Rectangle r;
    
    double sn = Math.sin(Math.toRadians(_angle));
    if(_angle < 0) r = new Rectangle((int)(_x+(_length*7/9)*sn),(int)(_y-(_length*sn/3)),(int)(_width-(_length*7/9)*sn),(int)(_length+(_length*sn/3)));
    else if(_angle < 90) r = new Rectangle((int)_x,(int)(_y+(_length*sn/3)),(int)(_width+(_length*7/9)*sn),(int)(_length-(_length*sn/3)));
    else r = new Rectangle((int)_x,(int)_y+_length-10,_width,_length);
    
    return r;
  }
  
  public Rectangle getDangerAreaFront(){ // set a space cushion
    Rectangle r;
    
    double k = 1.4, sp = _autoDrive ? Math.max(_speed-_speedCarAhead,0)+(int)(_speed/5)*3 : _speed;
    int p = _autoDrive ? 0 : 2, ra = _autoDrive ? 25 : _rand;
    
    if(_angle < 90) r = new Rectangle((int)(_x-p+Math.sin(Math.toRadians(_angle))*WIDTH),(int)(_y-ra-Math.pow(sp,k)),_width+p*2,(int)(ra+Math.pow(sp,k)));//((int)_x,(int)(_y-50-_speed*4),_width,(int)(50+_speed*4));
    else r = new Rectangle((int)_x-2,(int)(_y+_length+_rand),_width+4,(int)(_rand+50+Math.pow(_speed,k)));
    
    return r;
  }
  
  public Rectangle getDangerAreaLeft(){
    Rectangle r;
    
    if(_angle < 90) r = new Rectangle((int)_x-_laneWidth-5,(int)(_y-_length),_width+10,(int)(_length*2+Math.pow(_speed,1.3)));
    else r = new Rectangle((int)_x-_laneWidth-5,(int)(_y-Math.pow(_speed,1.3)+_length),_width+10,(int)(_length*2+Math.pow(_speed,1.3)));
    
    return r;
  }
  
  public Rectangle getDangerAreaRight(){
    Rectangle r;
    
    if(_angle < 90) r = new Rectangle((int)_x+_laneWidth-5,(int)(_y-_length),_width+10,(int)(_length*2+Math.pow(_speed,1.3)));
    else r = new Rectangle((int)_x+_laneWidth-5,(int)(_y-Math.pow(_speed,1.3)+_length),_width+10,(int)(_length*2+Math.pow(_speed,1.3)));
    
    return r;
  }
  
  public Rectangle getDangerAreaSideRight(){
    Rectangle r;
    
    if(_angle < 90) r = new Rectangle((int)(_x+_width),(int)(_y-15),(int)Math.abs(_lane-_x)+10,_length+10);
    else r = new Rectangle((int)(_x+_width),(int)(_y+_length*3/2+10),15,30);
    
    return r;
  }
  
  public Rectangle getDangerAreaSideLeft(){
    Rectangle r;
    
    if(_angle < 90) r = new Rectangle((int)(_x-Math.abs(_lane-_x)-10),(int)(_y-15),(int)Math.abs(_lane-_x)+10,_length+10);
    else r = new Rectangle((int)(_x-15),(int)(_y+_length*3/2+10),15,30);
    
    return r;
  }
  
  
  
  
  // ========
  // AUTO DRIVE AREAS
  // ========
  public Rectangle getOpeningAhead(){
    double k = 1.4, sp = _autoDrive ? Math.max(_speed-_speedCarAhead,0)+(int)(_speed/5)*3 : _speed;
    return new Rectangle((int)_x,(int)(_y-_rand-Math.pow(sp,k)-25),_width,(int)(_rand+Math.pow(sp,k)+25));
  }  
  
  /*
   public Rectangle getOpeningRight(){
   double k = 1.4, sp = _autoDrive ? Math.max(_speed-_speedCarAhead,0)+(int)(_speed/5)*3 : _speed;
   return new Rectangle((int)_x+_laneWidth-2,(int)(_y-_rand-Math.pow(sp,k)-25),_width+4,(int)(_rand+Math.pow(sp,k)+100));
   }
   public Rectangle getOpeningBackRight(){
   return new Rectangle((int)_x+_laneWidth-2,(int)_y+_length,_width+4,150);
   }
   
   
   public Rectangle getOpeningLeft(){
   double k = 1.4, sp = _autoDrive ? Math.max(_speed-_speedCarAhead,0)+(int)(_speed/5)*3 : _speed;
   return new Rectangle((int)_x-_laneWidth-2,(int)(_y-_rand-Math.pow(sp,k)-25),_width+4,(int)(_rand+Math.pow(sp,k)+100));
   }
   public Rectangle getOpeningBackLeft(){
   return new Rectangle((int)_x-_laneWidth-2,(int)_y+_length,_width+4,150);
   }*/
  
  
  public void fillOpeningLane(Graphics2D g2, int n, int ord){
    g2.setColor(new Color(0,0,255,100));
    g2.fill(getOpeningLane(n));
    g2.setColor(Color.white);
    g2.drawString(ord+"", (int)_lane-_laneWidth*n-2+_laneWidth/3,(int)_y);
  }
  public Rectangle getOpeningLane(int n){
    double k = 1.4, sp = _autoDrive ? Math.max(_speed-_speedCarAhead,0)+(int)(_speed/5)*3 : _speed;
    return new Rectangle((int)_lane-_laneWidth*n-2,(int)(_y-_rand-Math.pow(sp,k)-25),_width+4,(int)(_rand+Math.pow(sp,k)+150));
  }
  public Rectangle getOpeningBackLane(int n){
    return new Rectangle((int)_lane-_laneWidth*n-2,(int)_y+_length+50,_width+4,100);
  }
  
  public Rectangle getCutAcross(int n){
    return new Rectangle((int)_x-_laneWidth*n-2,(int)_y-200,_laneWidth*(n-1)+_width+4,200+_length+50);
  }  
  public Rectangle getCutAcrossBack(int n){
    return new Rectangle((int)_x-_laneWidth*n-2,(int)_y+_length+50,_laneWidth*(n-1)+_width+4,150);
  }  
  public Rectangle getCutAcrossBackTo(){
    return new Rectangle((int)Math.min(_x,_lane),(int)_y+_length+50,(int)Math.abs(_x-_lane)+_laneWidth/2,150);
  }  
  public Rectangle getCutAcrossFront(int n){
    return new Rectangle((int)_x-_laneWidth*n-2,(int)_y-450,_laneWidth*(n-1)+_width+4,250);
  }  
  public Rectangle getCutAcrossFrontTo(){
    return new Rectangle((int)Math.min(_x,_lane)+_width,(int)_y-350,(int)Math.abs(_x-_lane),350);
  }
  
  
  public void setSpeedCarAhead(int n){ _speedCarAhead = n; }
  public int getSpeedCarAhead(){ return _speedCarAhead; }
  
  public void setYCarAhead(int n){ 
    _yCarAhead = n;
    //System.out.println(_y-n);
  }
  public int getYCarAhead(){ return (int)(_yCarAhead-_y); }
  
  
  
  public void reset(int n, int angle){
    _angle = angle;
    _signalLeft = false;
    _signalRight = false;
    _x = n;
    _lane = n;
  }
  
  
  public boolean getDebug(){ return debug; }
  public void changeX(double n){ _x += n; }
  public void changeY(double n){ _y += n; }
  public void changeSpeed(double n){ _speed += n; }
  public void setLane(int n){ _lane = n; }
  public void setX(double n){ _x = n; }
  public void setY(double n){ _y = n; }
  public void setAngle(double n){ _angle = n; }
  public void setColor(Color col){ _col = col; }
  public void setCrash(boolean n){ _crash = n; }
  public void setLaneWidth(int n){ _laneWidth = n; }
  public void setPolice(boolean n){ _police = n; }
  public void setSpeed(double n){ _speed = n; }
  public void setDesiredSpeed(int n){ _desiredSpeed = n; }
  public void setTempDSpeed(int n){ _tempDSpeed = n; }
  public void setPoliceInfluence(boolean n){ _policeInfluence = n; }
  public void incSpeed(int n){ _speed += n; }
  public void finish(){ _finish = true; }
  
  
  public void setLights(boolean n){ _lights = n; }
  
  public int getRoadMin(){ return _roadMin; }
  public int getRoadMax(){ return _roadMax; }
  
  
  public boolean toggleAutoDrive(){ 
    setAutoDrive(!_autoDrive);
    return _autoDrive;
  }
  public boolean toggleAutoDrive(int min, int max){ 
    _roadMin = min;
    _roadMax = max;
    setAutoDrive(!_autoDrive,min,max);
    autoSetLane();
    return _autoDrive;
  }
  public boolean getAutoDrive(){ return _autoDrive; }
  public void setAutoDrive(boolean n){ setAutoDrive(n, -1, -1); }
  public void setAutoDrive(boolean n, int min, int max){ 
    _autoDrive = n;//debug = n;
    _tempDSpeed = -1;
    _desiredSpeed = 100;
    //_lights = n;
    
    _crash = _angleNoChange = _signalLeft = _signalRight = false;
    _forceChangeLane = true;
    
    _justChanged = n;
  }
  
  public void autoSetLane(){
    _signalLeft = false;
    _signalRight = false;
    
    if(_lane > _x){
      while(_lane > _x){
        _lane -= _laneWidth;
      }
    } else if(_lane < _x){
      while(_lane < _x){
        _lane += _laneWidth;
      }
    } else if(Math.abs(_lane-_x) < 1) _x = _lane;
    
    
    if(_roadMin != -1 && _roadMax != -1){
      while(_lane < _roadMin) _lane += _laneWidth;
      while(_lane > _roadMax) _lane -= _laneWidth;
    }
    
  }
  
  
  public void setDebug(boolean n){ debug = n; }
  public void toggleDebug(){ debug = !debug; }
  
  public boolean getCrash(){ return _crash; }
  public int getTempDSpeed(){ return _tempDSpeed; }
  public int getLength(){ return _length; }
  public int getWidth(){ return _width; }
  public double getX(){ return _x; }
  public double getY(){ return _y; }
  public double getAngle(){ return _angle; }
  public boolean getSignal(){ return _signalRight || _signalLeft; }
  public boolean getSignalLeft(){ return _signalLeft; }
  public boolean getSignalRight(){ return _signalRight; }
  public int getLane(){ return _lane; }
  public double getSpeed(){ return _speed; }
  public boolean getJustChanged(){ return _justChanged; }
  public boolean getAngleNoChange(){ return _angleNoChange; }
  public boolean getPoliceInfluence(){ return _policeInfluence; }
  public boolean getForceChangeLane(){ return _forceChangeLane; }
  public boolean getFinish(){ return _finish; }
  public String getCarType(){ return _carType; }
  
  public boolean signalOn(){ return _signalOn; }
  
  public void brake(boolean n){ _brake = _brakeLight = n; }
  public void signalLeft(boolean n){ _signalLeft = n; }
  public void signalRight(boolean n){ _signalRight = n; }
  
  public void signalToggle(){ _signalOn = !_signalOn; }
  
  public void sirenToggle(){
    _sirenPhase++;
    if(_sirenPhase == 8) _sirenPhase = 0;
  }
  
  public void draw(Graphics2D g2){
    
    g2.rotate(Math.toRadians(_angle),_x+_width/2,_y+_length-_length/10);
    
    // car background
    g2.setColor(_col);
    g2.fillRect((int)_x,(int)_y,_width,_length);
    
    int k1 = 4; // light width
    int k2 = 10; // light length
    
    // brake lights
    if(_brakeLight) g2.setColor(Color.red);
    else if(_lights) g2.setColor(new Color(247, 159, 159));
    else g2.setColor(Color.lightGray);
    g2.fillRect((int)_x+k1,(int)_y+_length-k2,k1,k2);
    g2.fillRect((int)_x+_width-k1*2,(int)_y+_length-k2,k1,k2);
    
    // LEFT signal light
    g2.setColor(_signalLeft&&_signalOn?Color.yellow:Color.lightGray);
    g2.fillRect((int)_x,(int)_y+_length-k2,k1,k2);
    g2.fillRect((int)_x,(int)_y,k1,k2);
    // RIGHT signal light
    g2.setColor(_signalRight&&_signalOn?Color.yellow:Color.lightGray);
    g2.fillRect((int)_x+_width-k1,(int)_y+_length-k2,k1,k2);
    g2.fillRect((int)_x+_width-k1,(int)_y,k1,k2);
    
    // HEADLIGHTS
    if(_police && _sirenPhase%2 == 0) g2.setColor(Color.white);
    else if(_lights) g2.setColor(new Color(215, 215, 215));
    else g2.setColor(Color.lightGray);
    g2.fillRect((int)_x+k1,(int)_y,k1,k2);
    
    if(_police && _sirenPhase%2 == 1) g2.setColor(Color.white);
    else if(_lights) g2.setColor(new Color(215, 215, 215));
    else g2.setColor(Color.lightGray);
    g2.fillRect((int)_x+_width-k1*2,(int)_y,k1,k2);
    
    
    // SIREN
    if(_police){
      if((_sirenPhase/2)%2 == 0) g2.setColor(Color.blue);
      else g2.setColor(Color.lightGray);
      g2.fillRect((int)_x,(int)(_y+(_length/2)-3),_width/2,6);
      
      if((_sirenPhase/2)%2 == 1) g2.setColor(Color.red);
      else g2.setColor(Color.lightGray);
      g2.fillRect((int)(_x+_width/2),(int)(_y+(_length/2)-3),_width/2,6);
      
      if(_sirenPhase == 1 || _sirenPhase == 3){
        g2.setColor(Color.red);
        g2.fillRect((int)_x+k1,(int)_y+_length-5,k1,5);
        g2.fillRect((int)_x+_width-k1*2,(int)_y+_length-5,k1,5);
        
        g2.setColor(Color.white);
        g2.fillRect((int)_x,(int)_y,k1,5);
        g2.fillRect((int)_x+_width-k1,(int)_y,k1,5);
      } else if(_sirenPhase == 5 || _sirenPhase == 7){
        g2.setColor(Color.white);
        g2.fillRect((int)_x,(int)_y+_length-5,k1,5);
        g2.fillRect((int)_x+_width-k1,(int)_y+_length-5,k1,5);
      }
      
      if(_sirenPhase%2 == 0){
        g2.setColor(Color.blue);
        g2.fillRect((int)_x+k1*2,(int)_y,(_width/2-k1*2),5);
      } else {
        g2.setColor(Color.red);
        g2.fillRect((int)_x+(_width/2),(int)_y,(_width/2-k1*2),5);
      }
    }
    
    g2.rotate(-Math.toRadians(_angle),_x+_width/2,_y+_length-_length/10);
    
    
    if(debug){
      drawDebug(g2);
    }
  }
  
  
  public void drawDebug(Graphics2D g2){
    //g2.setStroke(new BasicStroke(2));
    //g2.setColor(Color.green);
    //g2.draw(getBoundsMe());
    //g2.fillOval((int)_x-5,(int)_y-5,10,10);
    
    
    
    //g2.setStroke(new BasicStroke(1));
    
    g2.setColor(new Color(0,0,255,100));
    //g2.fill(getCutAcrossFront(3));
    
    //g2.fill(getOpeningAhead());
    //g2.fill(getOpeningRight());
    //g2.fill(getOpeningLeft());
    //g2.fill(getOpeningLane(3));
    //g2.draw(getDangerAreaLeft());
    //g2.draw(getDangerAreaRight());
    
    g2.setColor(new Color(255,255,0,100));
    g2.fill(getCutAcrossFrontTo());
    g2.fill(getCutAcrossBackTo());
    //g2.fill(getCutAcross(2));
    
    
    //g2.setStroke(new BasicStroke(2));
    g2.setColor(new Color(255,0,0,100));
    g2.fill(getDangerAreaFront());
    if(_signalRight) g2.fill(getDangerAreaSideRight());
    if(_signalLeft) g2.fill(getDangerAreaSideLeft());
    
    //g2.setColor(new Color(255,0,255,100));
    //g2.fill(getOpeningBackRight());
    //g2.fill(getOpeningBackLeft());
    //g2.fill(getCutAcrossBack(3));
    /*
     g2.setColor(Color.white);
     g2.setFont(new Font("Arial",Font.BOLD,20));
     g2.drawString((int)_speed+"",(int)_x,(int)_y);
     
     g2.setFont(new Font("Arial",Font.BOLD,12));
     g2.drawString((int)_desiredSpeed+"",(int)_x,(int)_y+22);
     
     
     
     //g2.drawString("("+(int)_x+","+_lane+")",(int)_x,(int)_y);
     /*if(_lane-_x != 0){
     g2.setColor(Color.pink);
     g2.drawString(_lane+" - "+Math.round(_angle*10)/10.0,(int)_x,(int)_y+15);
     }
     
     g2.setColor(Color.white);*/
    
    //g2.fillRect((int)_x,(int)(_y-10-(_width/2+10)*Math.sin(Math.toRadians(_angle))+10*Math.cos(Math.toRadians(_angle))),_width,2); // top point
    
    //g2.fillRect((int)_x,(int)(_y+(_length/2)-(_length/2)*Math.cos((int)((_angle+90)/180)*180)),_width,2); // top point
    //g2.fillRect((int)_x,(int)(_y+(_length*3/2)-(_length/2)*Math.cos((int)((_angle+90)/180)*180)),_width,2); // bottom point
    
    /*
     // RIGHT POINT
     if(_angle >= 0 && _angle < 90) g2.fillRect((int)(_x+_width*Math.cos(Math.toRadians(_angle))+_length*Math.sin(Math.toRadians(_angle))),(int)_y,2,_length);
     else if(_angle < 0 || _angle >= 180) g2.fillRect((int)(_x+_width),(int)_y,2,_length);
     else g2.fillRect((int)(_x+_width*Math.cos(Math.toRadians(180-_angle))+_length*Math.sin(Math.toRadians(180-_angle))),(int)_y,2,_length);
     
     // LEFT POINT
     if(_angle >= 0 && _angle < 90) g2.fillRect((int)_x,(int)_y,2,_length);
     else if(_angle < 0 || _angle >= 180) g2.fillRect((int)(_x+_width*Math.cos(Math.toRadians(_angle))+_length*Math.sin(Math.toRadians(_angle))),(int)_y,2,_length);
     */
    
    /*
     // RIGHT POINT
     if(_angle > 0 && _angle < 90 || _angle > 180) g2.fillRect((int)(_x+_length*Math.sin(Math.toRadians(_angle))+_width*Math.cos(Math.toRadians(_angle))),(int)_y,2,_length);
     else g2.fillRect((int)(_x+_width),(int)_y,2,_length);
     
     // LEFT POINT
     if(_angle > 90 && _angle < 180 || _angle < 0) g2.fillRect((int)(_x+_length*Math.sin(Math.toRadians(_angle))-(_width/2)*Math.sin(Math.toRadians(_angle))),(int)_y,2,_length);
     else g2.fillRect((int)_x,(int)_y,2,_length);
     
     // TOP POINT
     g2.fillRect((int)_x,(int)(_y+(_length/2)-(_length/2)*Math.cos((int)((_angle+90)/180)*180)+_width*Math.sin(Math.toRadians(Math.abs(_angle)))),_width,2); // top point
     
     // BOTTOM POINT
     g2.fillRect((int)_x,(int)(_y+(_length*3/2)-(_length/2)*Math.cos((int)((_angle+90)/180)*180)),_width,2); // bottom point
     */
  }
  
  
  
  public int getFurthestPointRight(){
    int r = 0;
    if(_angle > 0 && _angle < 90 || _angle > 180) r = (int)(_x+_length*Math.sin(Math.toRadians(_angle))+_width*Math.cos(Math.toRadians(_angle)));
    else r = (int)(_x+_width);
    return r;
  }
  
  public int getFurthestPointLeft(){
    int r = 0;
    if(_angle > 90 && _angle < 180 || _angle < 0) r = (int)(_x+_length*Math.sin(Math.toRadians(_angle))-(_width/2)*Math.sin(Math.toRadians(_angle)));
    else r = (int)_x;
    return r;
  }
  
  public int getFurthestPointUp(){
    return (int)(_y+(_length/2)-(_length/2)*Math.cos((int)((_angle+90)/180)*180)+_width*Math.sin(Math.toRadians(Math.abs(_angle))));
  }
  
  public int getFurthestPointDown(){
    return (int)(_y+(_length*3/2)-(_length/2)*Math.cos((int)((_angle+90)/180)*180));
  }
  
  
  // TRAFFIC CARS
  public void move(double sp, int nsp){
    
    _time++;
    
    if(nsp != 0){
      _speedCarAhead = nsp;
      _nspCount = 0;
    } else {
      if(_nspCount > 50) _speedCarAhead = 0;
      else _nspCount++;
    }
    
    _x += (_speed/4.0)*Math.sin(Math.toRadians(_angle));
    _y += (sp-_speed*Math.cos(Math.toRadians(_angle)))/5;
    
    
    if(!_brake && _speed < _desiredSpeed && (_lane == _x || _tempDSpeed < 10 || _tempDSpeed > _speed)){
      if(_autoDrive) _speed += (_desiredSpeed-_speed)/350.0;
      else _speed += _desiredSpeed/270.0;
    } else if(_tempDSpeed < _speed && _tempDSpeed >= 10) brake();
    
    // CHANGING LANES
    
    double ch = 120.0;//_autoDrive ? 60.0 : 240.0;
    
    if(_justChanged){
      if(Math.abs(_angle) < 1){
        _angle = 0;
        _justChanged = false;
        autoSetLane();
      } else {
        if(_angle > 0) _angle -= _speed/ch*3;
        else _angle += _speed/ch*3;
      }
    } else {
      
      
      if((_x != _lane || _angle%180 != 0) && !_crash){
        //_x += (_speed/4.0)*Math.sin(Math.toRadians(_angle));
        if(Math.abs(_angle-180) < 1 && Math.abs(_x-_lane) < 3){ // center in new lane
          _angle = 180.0;
          _x = _lane;
          _signalLeft = _signalRight = _forceChangeLane = false;
        } else if(Math.abs(_angle) < 1 && Math.abs(_x-_lane) < 2){ // center in new lane
          _tempDSpeed = -1;
          _angleNoChange = _forceChangeLane = _signalLeft = _signalRight = false;
          _angle = 0;
          _x = _lane;
        } else { // increase angle to go to new lane
          if(!_angleNoChange && Math.abs(_x-_lane) >= 2 && (Math.abs(_angle-180) < Math.max(1,Math.abs(_x-_lane)/_laneWidth*8) || Math.abs(_angle) < Math.max(1,Math.abs(_x-_lane)/_laneWidth*8))){
            if((_x-_lane < 0 && _angle > 90) || (_x-_lane > 0 && _angle < 90)){
              _angle -= _speed/ch;
              _signalLeft = true;
            } else {
              _angle += _speed/ch;
              _signalRight = true;
            }
          } else {
            if(_angle < -1 && ((_x-_lane < 0 && _angle > 90) || (_x-_lane > 0 && _angle < 90))) _angle += _speed/(ch*.6);
            else if(_angle > 1) _angle -= _speed/(ch*.6);
            
            if(Math.abs(_angle) < 1 && ((_lane > _x && _angle > 0) || (_lane < _x && _angle < 0))) _angleNoChange = true;
            else _angleNoChange = false;
          }
        }
      }
    }
    
  }
  
  public void changeLaneAuto(int n, boolean cLeft, boolean cRight, int min, int max){
    _justChanged = false;
    if(_x-_lane == 0 && Math.abs(min-max) > Math.abs(n)){
      int k = _lane+n;
      
      if(cLeft && k >= min && k <= max) _lane = k;
      else {
        k = _lane-n;
        if(cRight && k >= min && k <= max) _lane = k;
      }
      //else _lane -= (k-_lane);
    }
  }
  
  
  public void changeLane(int n, int min, int max){
    if(_x-_lane == 0 && Math.abs(min-max) > Math.abs(n)){
      int z = (int)(Math.random()*2);
      int k = _lane+n;
      
      if(k >= min && k <= max) _lane = k;
      //else _lane -= (k-_lane);
    }
  }
  
  public void changeLane(int n){
    _signalLeft = false;
    _signalRight = false;
    _lane = _lane+n;
    //if(_angle < 0 || (_angle > 90 && _angle < 180)) _angle++;
    //else _angle--;
  }
  
  public void forceChangeLane(int n){
    _forceChangeLane = true;
    _lane += n;
  }
  
  public void forceX(int n){
    _x += n;
    _lane += n;
  }
  
  public void brake(){
    
    if(_brakeNum > 100){
      _brakeLight = true;
      _brake = true;
      _brakeNum = 3;
      if(_speed > 70) _speed -= 4;
      else if(_speed > 50) _speed -= 3;
      else if(_speed > 30) _speed -= 2;
      else _speed -= 1.5;
    } else {
      _speed -= .75;
      _brakeNum++;
    }
    
    
    if(_speed < 0.0) _speed = 0.0;
  }
  
  public void noBrake(){ 
    if(_brakeNum > 500){
      _brake = _brakeLight = false;
      _brakeNum = 0;
    } else _brakeNum++;
  }
}