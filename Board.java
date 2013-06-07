import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.image.BufferedImage;

public class Board extends JPanel implements ActionListener {
  
  
  private boolean _pause;
  
  private int _gameMode = 1;
  /* _gameMode:
   * 1 = main menu
   * 2 = in-game
   * 3 = escape
   * 4 = custom select CAR
   * 5 = custom select OPTIONS
   * 6 = free drive OPTIONS
   * 7 = CONTROLS main menu
   * 8 = CONTROLS in-game
   */
  private int _gameType = 1;
  /* _gameType:
   * 1 = race
   * 2 = auto simulation
   * 3 = free drive
   * 4 = race finish
   */
  private int _controlSelectType = 1;
  /* _controlSelectType:
   * 1 = normal (nothing
   * 2 = choose new key
   * 3 = key in use - choose again
   */
  
  
  // CONTROLS
  /*
  private int 
    _controlsAccelerate = KeyEvent.VK_UP,
    _controlsBrake = KeyEvent.VK_DOWN,
    _controlsRight = KeyEvent.VK_RIGHT,
    _controlsLeft = KeyEvent.VK_LEFT,
    _controlsAutoDrive = KeyEvent.VK_A,
    _controlsDebug = KeyEvent.VK_D,
    _controlsReset = KeyEvent.VK_R;*/
  private int[] _controls = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_R, KeyEvent.VK_A, KeyEvent.VK_D};
  private String[] _controlsText = {"Accelerate","Brake","Turn Right","Turn Left","Reset","Toggle Auto-Drive","Toggle Debug"};
  /* _controls:
   * 0 = Accelerate
   * 1 = Brake
   * 2 = Turn Right
   * 3 = Turn Left
   * 4 = Reset
   * 5 = Toggle Auto-Drive
   * 6 = Toggle Debug
   */
  
  private String _navigationDirections = "Use UP/DOWN to move - ENTER to select", _navigationDirections2 = "Use UP/DOWN to move - RIGHT/LEFT to change - ENTER to select";
  
  private ArrayList<Integer> _finishTimes = new ArrayList<Integer>();
  private ArrayList<String> _finishCars = new ArrayList<String>();
  
  private int _gameWidth = 900, _gameHeight = 750;
  
  private Timer _timer;
  private int _time = 15;
  private int _w = 50; // lane width4
  private int _carWidth = 28;
  
  private int _lanesL, _lanesR, _medianL, _medianR, _shoulderL, _shoulderR;
  private int _dashed = 0;
  
  private Car _me;
  private int _maxSpeed = 100;
  
  private boolean _startPos, _startPosFree;
  private double _raceDistance = 0;
  
  private double _y;
  
  private int _m = 300;
  
  private Color _mark = new Color(222,222,222);
  
  private int _place = 1, _opponentCars = 0, _raceFinish;
  
  private int _min = -1500;
  private int _max = 2500;
  
  private int _crossLanesU, _crossLanesD;
  
  
  private int 
    _mainMenuOption = 1, 
    _mainMenuOptionMax = 5, 
    _pauseMenuOption = 1, 
    _pauseMenuOptionMax = 3, 
    _customMenuOption = 1, 
    _customMenuOptionMax = 6, 
    _customCarOption = 1, 
    _customCarOptionMax = 2, 
    _freeMenuOption = 1, 
    _freeMenuOptionMax = 4, 
    _controlMenuOption = 1, 
    _controlMenuOptionMax = _controls.length+1;
  
  
  private Font _optionSelected = new Font("Arial",Font.BOLD,20), _option = new Font("Arial",Font.PLAIN,20);
  private int _customCar = 4, _customDistance = 4, _customDistanceMax = 100, _customRacers = 4, _customRacersMax = 15, _customRoadSize = 2, _customLaneWidth = 2, _customTraffic = 2;
  
  private String[] _customCarTypeA = {"Mercedes Benz E Class","Chevy Volt"},
    _customRoadSizeA = {"Small","Medium","Large"},
      _customLaneWidthA = {"Extremely Narrow","Narrow","Normal","Wide","Extra Wide"},
        _customTrafficA = {"None","Very Light","Light","Normal","Heavy","Very Heavy"},
          
          _customCarA = {"BMW 3-Series","Chevrolet Volt", "Dodge Challenger", "Dodge Viper", "Lexus HS", "Mercedes Benz E-Class"};
          
          
          private int _t;
          
          private int _lastKey;
          
          private ArrayList<Car> _traffic, _cars;
          
          private boolean[] _keys; // which keys are pressed
          
          private BufferedImage _img;
          
          private Image _leftLaneEnds, _rightLaneEnds, _trafficLightAhead, _keepRight, _stayInLane, _pedWalk, _pedDontWalk, _pedBlank, _pedWalkHalf, _pedDontWalkHalf, _pedBlankHalf, _pedestrianCrossing, _noTurns;
          
          //private int _score;
          private int _scoreIncrease = 0;
          
          private double _distance; // distance in miles
          
          
          private String _message = "Merge left carefully";
          
          private BasicStroke _dd = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{(int)(_w*1.5),(int)(_w*2.5)}, 0);
          
          private int _sMaxLanes = 7;
          private int _sMinLanes = 2;
          private int _sMaxMedian = 2;
          private int _sMaxShoulder = 1;
          
          private double _mult = 10/9.0;
          
          private int _medianType;
          // 1 = double yellow
          // 2 = divider
          
          private int _laneType = 1;
          // 0 = no lines at all
          // 1 = dashed
          // 2 = single solid
          
          private int _newMedian = 0;
          private double _top = 0;
          private int _change = 1; // 1 = change soon, 2 = changing
          private int _changeType = 5;
          // 1 = right lane ends
          // 2 = right lane +
          // 3 = left lane ends
          // 4 = left lane +
          
          // 5 = right shoulder -
          // 6 = right shoulder +
          // 7 = stay in lane
          // 8 = remove stay in lane
          
          // 9 = intersection
          // 10 = ^
          // 11 = ^
          // 12 = ^
          // 13 = ^
          // 14 = ^
          // 15 = ^
          
          private int _interType = 1;
          /*
           * 1 = crosswalk
           * 2 = intersection
           * 3 = start line
           */
          
          public int _interOffset = 0;
          private int _lightPhase = 1;
          /*
           * 1 = me straight GREEN
           * 2 = me pedestrian flash
           * 3 = me straight YELLOW
           * 4 = me straight RED - waiting to switch
           * 5 = cross straight GREEN
           * 6 = cross pedestrian flash
           * 7 = cross straight YELLOW
           * 8 = cross straight RED - waiting to switch
           */
          
          
          private int _lightCount = 0;
          
          
          private boolean _police = false; // police mode: cars move out of your lane
          
          
          // SOUNDS
          /*
          private Sound _sHorn = new Sound("horn3.wav");
          private boolean _horn;
          
          private Sound _sSiren = new Sound("siren2.wav");
          private boolean _siren = false;
          */
          
          
          
          // COLORS
          private Color _lightGreen = new Color(11, 97, 11);
          private Color _lightYellow = new Color(94, 97, 11);
          private Color _lightRed = new Color(138, 8, 8);
          private Color _blackOpac = new Color(0,0,0,100);
          
          
          public Board(){
            
            addKeyListener(new Key());
            
            setFocusable(true);
            setDoubleBuffered(true);
            
            _keys = new boolean[250];
            
            //setBackground(Color.gray);
            
            
            _t = 0;
            
            if(_police){
              _medianType = 1;
              _medianR = 0;
            } else {
              _medianType = (_medianL+_medianR > 1)?(int)(Math.random()*2)+1:1;
              _medianR = (int)(Math.random()*_sMaxMedian);
            }
            
            
            _medianL = 0;//(int)(Math.random()*_sMaxMedian);
            _lanesL = 2;//(int)(Math.random()*(_sMaxLanes-_sMinLanes))+_sMinLanes;
            _lanesR = _sMaxLanes;//(int)(Math.random()*(_sMaxLanes-_sMinLanes))+_sMinLanes;
            _shoulderL = (int)(Math.random()*_sMaxShoulder);
            _shoulderR = Math.max(1,(int)(Math.random()*_sMaxShoulder));
            
            _top = 0;
            
            _y = 0;
            
            _img = new BufferedImage(_gameWidth,_gameHeight,BufferedImage.TYPE_INT_ARGB);
            
            
            
            
            _cars = new ArrayList<Car>();
            _traffic = new ArrayList<Car>();
            
            
            //_me = new Car(_m+3+(_medianR+_lanesR+_shoulderR-1)*_w+(_w-_carWidth)/2,550,60,_carWidth,Color.black,_w,_siren);
            int xm = _m+6+(_medianR)*_w+(_lanesR/2)*_w;
            _me = new ChevyVolt(xm+_w,550,0,0,100,xm,_w);
            _me.setLights(true);
            _me.toggleAutoDrive(_m+6+_medianR*_w,_m+6+(_medianR+_lanesR)*_w);
            _me.setSpeed(40);
            _cars.add(_me);
            _traffic.add(_me);
            
            createCarsLeft(4);
            createCarsRight(5);
            
            /*
             for(int x=0;x<0;x++){
             Car testcar = new MercedesBenzEClass(xm-_w*x,700,0,0,100,xm-_w*x,_w);
             _cars.add(testcar);
             _traffic.add(testcar);
             _traffic.get(x).setAutoDrive(true);
             }
             
             
             _traffic.add(_me);
             
             
             for(int x=0;x<10;x++){
             int xx = _m+6+(_medianR)*_w+(int)(Math.random()*_lanesR)*_w;
             Car k = randomCar(xx,(int)(Math.random()*_gameHeight),0,0,35+(int)(Math.random()*15),xx,_w);
             //Car k = randomCar(xx,(int)(Math.random()*_gameHeight),0,0,5+(int)(Math.random()*15),xx,_w);
             //if((int)(Math.random()*3)==1) k.setAutoDrive(true);
             _traffic.add(k);
             _cars.add(k);
             }
             
             for(int x=0;x<4;x++){
             int xx = _m-3-(_medianL+1)*_w-(int)(Math.random()*_lanesL)*_w;
             Car k = randomCar(xx,-(int)(Math.random()*300),180,0,30+(int)(Math.random()*20),xx,_w);
             _traffic.add(k);
             _cars.add(k);
             }
             */
            
            for(int x=0;x<_traffic.size();x++){
              //_traffic.get(x).setAutoDrive(true);
              for(int y=x+1;y<_traffic.size();y++){
                if(_traffic.get(x).getBounds().intersects(_traffic.get(y).getBounds())){
                  if(_traffic.get(y) == _me) _traffic.get(x).setY(_max);
                  else _traffic.get(y).setY(_max);
                }
              }
            }
            
            
            //if(_siren) _sSiren.loop();
            
            
            
            
            // IMAGES
            _leftLaneEnds = image("leftLaneEnds.png");
            _rightLaneEnds = image("rightLaneEnds.png");
            _trafficLightAhead = image("trafficLightAhead.png");
            _keepRight = image("keepRight2.png");
            _stayInLane = image("stayInLane.png");
            _pedWalk = image("pedWalk.gif");
            _pedDontWalk = image("pedDontWalk.gif");
            _pedBlank = image("pedBlank.gif");
            _pedWalkHalf = image("pedWalkHalf.gif");
            _pedDontWalkHalf = image("pedDontWalkHalf.gif");
            _pedBlankHalf = image("pedBlankHalf.gif");
            _pedestrianCrossing = image("pedestrianCrossing.gif");
            _noTurns = image("noTurns.gif");
            
            
            _timer = new Timer(_time,this);
            _timer.start();
            
          }
          
          
          public Image image(String n){
            return (new ImageIcon(this.getClass().getResource(n))).getImage();
          }
          
          public Car randomCar(int x, int y, double angle, int speed, int desiredSpeed, int lane, int laneWidth){
            
            Car k;
            
            
            int ran = (int)(Math.random()*8);
            if(ran >= _customCar) ran++;
            
            
            if(ran == 0) k = new BMW3(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 1) k = new ChevyVolt(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 2) k = new DodgeChallenger(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 3) k = new DodgeViper(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 4) k = new LexusHS(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 5) k = new MercedesBenzEClass(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 6) k = new Sierra(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else k = new HondaOdyssey(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            
            return k;
          }
          
          public Car randomRacer(int x, int y, double angle, int speed, int desiredSpeed, int lane, int laneWidth){
            Car k;
            
            
            int ran = (int)(Math.random()*6);
            if(ran >= _customCar) ran++;
            
            
            if(ran == 0) k = new BMW3(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 1) k = new ChevyVolt(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 2) k = new DodgeChallenger(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 3) k = new DodgeViper(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else if(ran == 4) k = new LexusHS(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            else k = new MercedesBenzEClass(x, y, angle, speed, desiredSpeed, lane, laneWidth);
            
            return k;
          }
          
          /*
           public Car randomCar(int s){
           (int x, int y, double angle, int speed, int desiredSpeed, int lane, int laneWidth){
           Car k;
           int ran = (int)(Math.random()*4);
           if(ran == 0) k = new Sierra(0,(int)(Math.random()*750),0,0,s,0,_w);
           else if(ran == 1) k = new HondaOdyssey(0,(int)(Math.random()*750),0,0,s,0,_w);
           else if(ran == 2) k = new MercedesBenzEClass(0,(int)(Math.random()*750),0,0,s,0,_w);
           else k = new Lambo(0,(int)(Math.random()*750),0,0,s,0,_w);
           
           int xx = _m+6+(_medianR)*_w+(_w-k.WIDTH)/2+(int)(Math.random()*_lanesR)*_w;
           k.setX(xx);
           k.setLane(xx);
           
           return k;
           }
           */
          
          
          
          public void actionPerformed(ActionEvent e){
            
            repaint();
            
            if(!_pause){
              
              _t++;
              
              /*
              // increase score
              if(_gameMode == 2) _score += _scoreIncrease;
              if(_score < 0) _score = 0;
              
              if(_police){
                if(_me.getSpeed() > 15) _scoreIncrease = (int)_me.getSpeed();
                else _scoreIncrease = 0;
              } else {
                if(_me.getX() > _m+(_medianR+_lanesR)*_w-10){
                  if(_me.getSpeed() > 15) _scoreIncrease = -((int)_me.getSpeed()-15);
                  else _scoreIncrease = 0;
                } else if(_me.getX() > _m+_medianR*_w){
                  if(_me.getSpeed() > 15) _scoreIncrease = (int)_me.getSpeed();
                  else _scoreIncrease = 0;
                } else _scoreIncrease = -((int)_me.getSpeed()+5);
              }
              */
              
              
              
              
              if(_t%10 == 0){
                _me.signalToggle();
                for(int x=0;x<_traffic.size();x++){
                  if(_traffic.get(x) != _me) _traffic.get(x).signalToggle();
                }
              }
              
              
              
              
              if(_t%5 == 0){
                _me.sirenToggle();
              }
              
              _distance += _me.getSpeed()*_time/(1000.0*3600)*_mult;
              
              if(_t%30 == 0 && _changeType != 9){
                Car k = _traffic.get((int)(Math.random()*_traffic.size()));
                if(!k.getAutoDrive() && k.getAngle() == 0){
                  if((k.getX() < _m || !(_changeType == 7 || _laneType == 2)) && k.getX() == k.getLane()){
                    
                    Rectangle al = k.getDangerAreaLeft();
                    Rectangle ar = k.getDangerAreaRight();
                    
                    boolean left = false;
                    boolean right = false;
                    
                    for(int x=-1;x<_traffic.size();x++){
                      Car c = (x==-1)?_me:_traffic.get(x);
                      if(k != c){
                        if(!left && al.intersects(c.getBounds())) left = true;
                        else if(!right && ar.intersects(c.getBounds())) right = true;
                        if(left && right) break;
                      }
                    }
                    
                    
                    if(!(left && right)){
                      int m = (int)(Math.random()*2)*2-1;
                      
                      if(left) m = 1;
                      else if(right) m = -1;
                      
                      
                      if(k.getAngle() == 0) k.changeLane(m*_w,_m+3+(_medianR+((_change!=0&&_changeType==3)?1:0))*_w,_m+3+(_medianR+_lanesR+((_change!=0&&_changeType==1)?-1:0))*_w);
                      else if(k.getAngle() == 180) k.changeLane(m*_w,_m-3-(_medianL+_lanesL)*_w,_m-3-_medianL*_w);
                    }
                  }
                }
              }
              
              
              // TRAFFIC LIGHT
              if(_t%100 == 0){
                if(_interType == 1 || _interType == 2 || _interType == 3){
                  if((_lightPhase == 4 || _lightPhase == 8) ||
                     (_t%400 == 0 && (_lightPhase == 2 || _lightPhase == 6)) ||
                     (_t%200 == 0 && (_lightPhase == 3 || _lightPhase == 7)) ||
                     (_t%1200 == 0 && (_lightPhase == 1 || _lightPhase == 5))) _lightPhase++;
                  
                  if(_lightPhase == 9) _lightPhase = 1;
                  
                  if(_startPos && _interType == 3 && _lightPhase == 1){
                    _startPos = false;
                    _t = 0;
                  }
                }
              }
              
              
              
              // overlapping traffic cars
              /*
               if(_t%10 == 0){
               for(int x=0;x<_cars.size();x++){
               if(_cars.get(x).getY() < 0){
               for(int y=x+1;y<_traffic.size();y++){
               if(_cars.get(x).getBounds().intersects(_traffic.get(y).getBounds())){
               _traffic.get(y).setY(_max);
               }
               }
               }
               
               if(_cars.get(x).getAngle() < 90 && _cars.get(x).getLane() < _m+3+(_medianR+(_changeType==3?1:0))*_w) _cars.get(x).changeLane(_w);
               }
               }*/
              
              
              
              if(_change == 0){
                _change = 1;
                
                if(_gameType == 1 || _gameType == 2){
                  if(_me.getX() > _m+6+(_medianR+_lanesR)*_w && _shoulderR > 0) _changeType = 5;
                  else _changeType = (int)(Math.random()*6)+1;
                } else _changeType = (int)(Math.random()*11)+1;
                
                if(_police && _changeType == 6){
                  if(_shoulderR > 0) _changeType = 5;
                  else _changeType = 9;
                }
                
                if(_changeType > 9 && _changeType <= 15) _changeType = 9;
                
                if(_changeType == 9){
                  _top = _dashed-_w*72;//108;
                  
                  _interType = (int)(Math.random()*2)+1;
                  
                  if(_interType == 1)_interOffset = _w*4;
                  else if(_interType == 2){
                    _crossLanesU = _crossLanesD = 2;
                    _interOffset = _w*(4+_crossLanesU+_crossLanesD);
                  }
                  
                } else _top = _dashed-_w*36;
                
                if(_laneType == 2 && _changeType != 7) _changeType = 8;
                
                if(_medianR == 0 && _changeType == 4) _changeType = 3;
                
                if(_lanesR == _sMinLanes && (_changeType == 1 || _changeType == 3)) _changeType = 2;
                if(_lanesR == _sMaxLanes){
                  if(_changeType == 2) _changeType = 1;
                  else if(_changeType == 4) _changeType = 3;
                }
                
                if(_medianR == _sMaxMedian && _changeType == 3){
                  if(_lanesR < _sMaxLanes) _changeType = 2;
                  else _changeType = 1;
                }
                
                if(_shoulderR == 0 && _changeType == 5) _changeType = 6;
                else if(_shoulderR == _sMaxShoulder && _changeType == 6) _changeType = 5;
                
                if(_changeType == 1){
                  for(int x=0;x<_traffic.size();x++){
                    if(_traffic.get(x).getLane() > _m+3+(_lanesR+_medianR-1)*_w){
                      if(_traffic.get(x).getY() > -150) _traffic.get(x).forceChangeLane(-_w);
                      else _traffic.get(x).forceX(-_w);
                    }
                  }
                } else if(_changeType == 3){
                  for(int x=0;x<_traffic.size();x++){
                    if(_traffic.get(x).getX() > _m && _traffic.get(x).getLane() < _m+3+(_medianR+1)*_w){
                      if(_traffic.get(x).getY() > -150) _traffic.get(x).forceChangeLane(_w);
                      else _traffic.get(x).forceX(_w);
                    }
                  }
                }
                
                
                if(_medianR+_medianL-(_changeType==4?1:0) > 0){
                  if(_me.getX() < _m+_medianR*_w) _newMedian = 2;
                  else _newMedian = (int)(Math.random()*2)+1;
                  //if(_medianR+(_changeType==4?-1:0)+(_changeType==3?1:0)+_medianL == 0) _newMedian = 1;
                  
                } else _newMedian = 1;
                
                
                _message = 
                  _changeType == 1 ? "Right lane ends - Merge left" :
                  _changeType == 3 ? "Left lane ends - Merge right" :
                  _changeType == 7 || (_changeType == 8 && _laneType == 2) ? "        Stay in lane" :
                  _changeType == 9 ? (_interType == 1 ? "Pedestrian crossing ahead" : "") :
                  "";
              }
              
              if(_change == 1){
                _top += (_me.getSpeed()/5.0)*Math.cos(Math.toRadians(_me.getAngle()));
                if(_top >= _gameHeight+_w*12){
                  
                  _change = 0;
                  _top = 0;
                  
                  if(_changeType == 1) _lanesR--;
                  else if(_changeType == 2) _lanesR++;
                  else if(_changeType == 3){
                    _lanesR--;
                    _medianR++;
                  } else if(_changeType == 4){
                    _lanesR++;
                    _medianR--;
                  } else if(_changeType == 5) _shoulderR--;
                  else if(_changeType == 6) _shoulderR++;
                  
                  _medianType = _newMedian;
                  _laneType = _changeType==7?2:1;
                  
                }
                
              } 
              
              _dashed = (int)(_y%(_w*4));
              
              _y += ((_me.getSpeed()/5.0)*Math.cos(Math.toRadians(_me.getAngle())));
              
              if(!_me.getJustChanged()) _me.setX(_me.getX()+(_me.getSpeed()/5.0)*Math.sin(Math.toRadians(_me.getAngle())));
              
              
              if(_controlSelectType == 1 && control(Controls.DEBUG) && _lastKey != _controls[Controls.DEBUG]){
                _me.toggleDebug();
              } 
            }
            
            if(_gameMode == 4){ // CUSTOM CAR SELECT
              if(_keys[KeyEvent.VK_UP] && _lastKey != KeyEvent.VK_UP){
                if(_customCarOption > 1) _customCarOption--;
              } else if(_keys[KeyEvent.VK_DOWN] && _lastKey != KeyEvent.VK_DOWN){
                if(_customCarOption < _customCarOptionMax) _customCarOption++;
              } else if(_keys[KeyEvent.VK_RIGHT] && _lastKey != KeyEvent.VK_RIGHT){
                if(_customCarOption == 1){
                  if(_customCar<_customCarA.length-1) _customCar++;
                }
              } else if(_keys[KeyEvent.VK_LEFT] && _lastKey != KeyEvent.VK_LEFT){
                if(_customCarOption == 1){
                  if(_customCar>0) _customCar--;
                }
              } else if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER){
                if(_customCarOption != 2) _customCarOption = 2;
                else if(_gameType == 3) _gameMode = 6;
                else {
                  _gameMode = 5;
                }
              }
            }
            
            else if(_gameMode == 5){ // CUSTOM SELECT
              if(_keys[KeyEvent.VK_UP] && _lastKey != KeyEvent.VK_UP){
                if(_customMenuOption > 1) _customMenuOption--;
              } else if(_keys[KeyEvent.VK_DOWN] && _lastKey != KeyEvent.VK_DOWN){
                if(_customMenuOption < _customMenuOptionMax) _customMenuOption++;
              } else if(_keys[KeyEvent.VK_RIGHT] && _lastKey != KeyEvent.VK_RIGHT){
                if(_customMenuOption == 1){
                  if(_customDistance<_customDistanceMax) _customDistance++;
                } else if(_customMenuOption == 2){
                  if(_customRacers<_customRacersMax) _customRacers++;
                } else if(_customMenuOption == 3){
                  if(_customTraffic<_customTrafficA.length-1) _customTraffic++;
                } else if(_customMenuOption == 4){
                  if(_customRoadSize<_customRoadSizeA.length-1) _customRoadSize++;
                } else if(_customMenuOption == 5){
                  if(_customLaneWidth<_customLaneWidthA.length-1) _customLaneWidth++;
                }
              } else if(_keys[KeyEvent.VK_LEFT] && _lastKey != KeyEvent.VK_LEFT){
                if(_customMenuOption == 1){
                  if(_customDistance>1) _customDistance--;  
                } else if(_customMenuOption == 2){
                  if(_customRacers>0) _customRacers--;
                } else if(_customMenuOption == 3){
                  if(_customTraffic>0) _customTraffic--;
                } else if(_customMenuOption == 4){
                  if(_customRoadSize>0) _customRoadSize--;
                } else if(_customMenuOption == 5){
                  if(_customLaneWidth>0) _customLaneWidth--;
                }
              } else if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER){
                if(_customMenuOption != 6) _customMenuOption = 6;
                else {
                  // START CUSTOM RACE
                  
                  setLaneWidth(46+_customLaneWidth*5);
                  setMaxLanes((int)(350/_w)-(_customRoadSizeA.length-1)*2+_customRoadSize*2);
                  System.out.println(_sMaxLanes);
                  
                  raceSetup(true);
                  setRaceDistance(_customDistance/2.0);
                  clearCars();
                  createPlayerCar(true);
                  createCarsLeft(4);
                  createCarsRight((int)Math.pow(_customTraffic+1,2));
                  createOpponentCars(_customRacers);
                  
                  if(_gameType == 2) _me.setAutoDrive(true);
                  
                  for(int x=0;x<_traffic.size();x++){
                    for(int y=x+1;y<_traffic.size();y++){
                      if(_traffic.get(x).getBounds().intersects(_traffic.get(y).getBounds())){
                        if(_traffic.get(y) == _me) _traffic.get(x).setY(_max);
                        else _traffic.get(y).setY(_max);
                      }
                    }
                  }
                  
                  _gameMode = 2;
                }
              }
            }
            
            else if(_gameMode == 6){ // FREE DRIVE OPTIONS
              if(_keys[KeyEvent.VK_UP] && _lastKey != KeyEvent.VK_UP){
                if(_freeMenuOption > 1) _freeMenuOption--;
              } else if(_keys[KeyEvent.VK_DOWN] && _lastKey != KeyEvent.VK_DOWN){
                if(_freeMenuOption < _freeMenuOptionMax) _freeMenuOption++;
              } else if(_keys[KeyEvent.VK_RIGHT] && _lastKey != KeyEvent.VK_RIGHT){
                if(_freeMenuOption == 1){
                  if(_customTraffic<_customTrafficA.length-1) _customTraffic++;
                } else if(_freeMenuOption == 2){
                  if(_customRoadSize<_customRoadSizeA.length-1) _customRoadSize++;
                } else if(_freeMenuOption == 3){
                  if(_customLaneWidth<_customLaneWidthA.length-1) _customLaneWidth++;
                }
              } else if(_keys[KeyEvent.VK_LEFT] && _lastKey != KeyEvent.VK_LEFT){
                if(_freeMenuOption == 1){
                  if(_customTraffic>0) _customTraffic--;
                } else if(_freeMenuOption == 2){
                  if(_customRoadSize>0) _customRoadSize--;
                } else if(_freeMenuOption == 3){
                  if(_customLaneWidth>0) _customLaneWidth--;
                }
              } else if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER){
                if(_freeMenuOption != 4) _freeMenuOption =4;
                else {
                  
                  setLaneWidth(46+_customLaneWidth*5);
                  setMaxLanes((int)(350/_w)-(_customRoadSizeA.length-1)*2+_customRoadSize*2);
                  
                  raceSetup(false);
                  clearCars();
                  createPlayerCar(false);
                  createCarsLeft(4);
                  createCarsRight((int)Math.pow(_customTraffic+1,2));
                  
                  for(int x=0;x<_traffic.size();x++){
                    for(int y=x+1;y<_traffic.size();y++){
                      if(_traffic.get(x).getBounds().intersects(_traffic.get(y).getBounds())){
                        if(_traffic.get(y) == _me) _traffic.get(x).setY(_max);
                        else _traffic.get(y).setY(_max);
                      }
                    }
                  }
                  
                  _gameMode = 2;
                  
                }
              }
            }
            
            else if(_gameMode == 7 || _gameMode == 8){ // CONTROLS
              if((_controlSelectType == 2 || _controlSelectType == 3)){
                if(_lastKey != KeyEvent.VK_ENTER){
                  if(_lastKey < _keys.length && _lastKey > 4){
                    if(_lastKey == _controls[_controlMenuOption-1]) _controlSelectType = 1;
                    else {
                      boolean good = true;
                      
                      for(int x=0;x<_controls.length;x++){
                        if(_lastKey == _controls[x]){
                          good = false;
                          break;
                        }
                      }
                      
                      if(good){ // success
                        _controls[_controlMenuOption-1] = _lastKey;
                        _controlSelectType = 1;
                      } else { // fail
                        _controlSelectType = 3;
                      }
                    }
                  }
                }
              } else {
                
                if(_keys[KeyEvent.VK_UP] && _lastKey != KeyEvent.VK_UP){
                  if(_controlMenuOption > 1) _controlMenuOption--;
                } else if(_keys[KeyEvent.VK_DOWN] && _lastKey != KeyEvent.VK_DOWN){
                  if(_controlMenuOption < _controlMenuOptionMax) _controlMenuOption++;
                } else if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER){
                  if(_controlMenuOption == _controls.length+1){
                    _gameMode = _gameMode==7?1:3;
                  } else {
                    if(_controlSelectType == 1){
                      _controlSelectType = 2;
                    }
                  }
                }
              }
              
              if(_keys[KeyEvent.VK_ESCAPE] && _lastKey != KeyEvent.VK_ESCAPE){
                _controlSelectType = 1;
              }
              
            }
            
            else if(_gameMode == 3){ // PAUSE MENU KEYS
              
              if(_keys[KeyEvent.VK_UP] && _lastKey != KeyEvent.VK_UP){
                if(_pauseMenuOption > 1) _pauseMenuOption--;
              } else if(_keys[KeyEvent.VK_DOWN] && _lastKey != KeyEvent.VK_DOWN){
                if(_pauseMenuOption < _pauseMenuOptionMax) _pauseMenuOption++;
              } else if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER){
                if(_pauseMenuOption == 1){ // resume
                  _gameMode = 2;
                  _pause = false;
                } else if(_pauseMenuOption == 2){
                  _gameMode = 8;
                } else if(_pauseMenuOption == 3){ // exit to main menu
                  _gameMode = 1;
                  _me.setAutoDrive(true,_m+6+_medianR*_w,_m+6+(_medianR+_lanesR)*_w);
                  _lightPhase = 1;
                  _pause = false;
                }
              }
              
            } else if(_gameMode == 1){ // MAIN MENU KEYS
              
              if(_keys[KeyEvent.VK_UP] && _lastKey != KeyEvent.VK_UP){
                if(_mainMenuOption > 1) _mainMenuOption--;
              } else if(_keys[KeyEvent.VK_DOWN] && _lastKey != KeyEvent.VK_DOWN){
                if(_mainMenuOption < _mainMenuOptionMax) _mainMenuOption++;
              } else if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER){
                
                if(_mainMenuOption == 1){ // QUICK RACE
                  //_score = 0;
                  
                  setLaneWidth(55);
                  setMaxLanes(6);
                  raceSetup(true);
                  clearCars();
                  createPlayerCar(true);
                  createCarsLeft(4);
                  createCarsRight(10);
                  createOpponentCars(5);
                  setRaceDistance(1);
                  
                  
                  for(int x=0;x<_traffic.size();x++){
                    for(int y=x+1;y<_traffic.size();y++){
                      if(_traffic.get(x).getBounds().intersects(_traffic.get(y).getBounds())){
                        if(_traffic.get(y) == _me) _traffic.get(x).setY(_max);
                        else _traffic.get(y).setY(_max);
                      }
                    }
                  }
                  
                  _gameMode = 2;
                }
                
                else if(_mainMenuOption == 2){ // CUSTOM RACE
                  _gameType = 1;
                  _gameMode = 4; // CHANGE TO 4 LATER
                }
                
                else if(_mainMenuOption == 3){ // AUTO DRIVE MODE
                  _gameType = 2;
                  _gameMode = 4;
                }
                
                else if(_mainMenuOption == 4){ // FREE DRIVE
                  _gameType = 3;
                  _gameMode = 4;
                }
                
                else if(_mainMenuOption == 5){ // CONTROLS
                  _gameMode = 7;
                }
              }
              
            } if(_gameMode == 2){ // GAME MODE KEYS
              
              if(control(Controls.AUTODRIVE) && _lastKey != _controls[Controls.AUTODRIVE] && (_gameType == 2 || _gameType == 3)){
                _startPosFree = false;
                _me.toggleAutoDrive(_m+6+_medianR*_w,_m+6+(_medianR+_lanesR)*_w);
              }
              
              if(_keys[KeyEvent.VK_ENTER] && _lastKey != KeyEvent.VK_ENTER && _gameType == 4){
                _gameMode = 1;
                _gameType = 2;
              }
              
              // BRAKE
              if(!_me.getAutoDrive()){
                if(control(Controls.BRAKE)){
                  brake();
                  _me.brake(true);
                } else {
                  _me.brake(false);
                  
                  // ACCELERATE
                  if(control(Controls.ACCELERATE) && !_startPos){
                    accelerate((_maxSpeed-_me.getSpeed())/350.0);
                    _startPosFree = false;
                  } else accelerate(-.125); // slow down
                }
                
                // STEERING
                if(control(Controls.RIGHT)){
                  if(_me.getAngle() < 0) steer(2);
                  else steer(.5);
                } else if(control(Controls.LEFT)){
                  if(_me.getAngle() > 0) steer(-2);
                  else steer(-.5);
                }
                else {
                  double k = _me.getAngle();
                  if(k < .5 && k > -.5) _me.setAngle(0);
                  else if(k < 0) steer(.75);
                  else if(k > 0) steer(-.75);
                  
                }
                
                
                
                // SIGNAL LIGHTS
                
                /*
                if(_keys[KeyEvent.VK_1] && _lastKey != KeyEvent.VK_1){
                  boolean k = _me.getSignalLeft();
                  _me.signalLeft(!k);
                  _me.signalRight(false);
                  //_t = 0;
                  _me.signalOn();
                } else if(_keys[KeyEvent.VK_2] && _lastKey != KeyEvent.VK_2){
                  boolean k = _me.getSignalRight();
                  _me.signalLeft(false);
                  _me.signalRight(!k);
                  //_t = 0;
                  _me.signalOn();
                }*/
              } else {
                if(control(Controls.BRAKE)){
                  brake();
                  _me.brake(true);
                }
              }
              /*
               else {
               if(_keys[KeyEvent.VK_SPACE]){
               _me.brake();
               _me.setAutoDrive(false);
               }
               }*/
              /*
              if(_keys[KeyEvent.VK_S] && _lastKey != KeyEvent.VK_S){
                _police = !_police;
                _me.setPolice(_police);
                if(_police) _sSiren.loop();
                else _sSiren.stop();
              }*/
              
              if(control(Controls.RESET) && _lastKey != _controls[Controls.RESET] && _me.getSpeed() == 0){
                _me.setX((int)(_m-3+(_w+_carWidth)/2+(_medianR+_lanesR+_shoulderR-1.5)*_w));
                _me.setAngle(0);
                
                /*for(int x=0;x<_traffic.size();x++){
                  _traffic.get(x).setY(-400);
                }*/
              }
              
            }
            if(_lastKey > 1000) _lastKey /= 1000;
            
            
            if(!_pause){
              
              
              
              // CHECK FOR COLLISION
              // outer walls
              if(_me.getFurthestPointLeft() < _m-3 - (_lanesL+_medianL+_shoulderL)*_w || _me.getFurthestPointRight() > _m+3 + (_lanesR+_medianR+_shoulderR)*_w){
                //reset();
                _me.setSpeed((int)(_me.getSpeed()/2));
                _me.setAngle(0);
                _scoreIncrease = -10000;
              }
              
              // median
              if(_medianType == 2 && (_newMedian != 1 || _top+(_changeType==9?_interOffset:0) < _me.getY())){
                if(_me.getFurthestPointLeft() < _m+_medianR*_w && _me.getFurthestPointRight() > _m-_medianL*_w){
                  //reset();
                  _me.setSpeed((int)(_me.getSpeed()/2));
                  _me.setAngle(0);
                  _scoreIncrease = -10000;
                }
              }
              
            }
            
          }
          
          
          private void brake(){
            _me.changeSpeed(-1.25);
            if(_me.getSpeed() < 0) _me.setSpeed(0);
          }
          
          private void accelerate(double n){
            if(_me.getSpeed()+n < _maxSpeed && _me.getSpeed()+n > 0) _me.changeSpeed(n);
          }
          
          private void steer(double n){
            if(_me.getSpeed() >= 1){
              double k = _me.getAngle();
              if(k/(k+n) == -1) n /= 2;
              k += n;
              if(k > -85 && k < 85) 
                _me.setAngle(k);
            }
          }
          
          private boolean control(int n){
            return _keys[_controls[n]];
          }
          
          public void paint(Graphics g){
            super.paint(g);
            
            
            //Graphics2D g2 = (Graphics2D)g;
            Graphics2D g2 = _img.createGraphics();
            
            g2.setBackground(new Color(169, 245, 188));
            g2.clearRect(0,0,_gameWidth,_gameHeight);
            
            //g2.setColor(Color.green);
            //g2.fillRect(0,0,900,750);
            
            int nlr = _lanesR+(_changeType==1||_changeType==3?-1:_changeType==2||_changeType==4?1:0);
            int nmr = _medianR+(_changeType==3?1:_changeType==4?-1:0);
            int nsr = _shoulderR+(_changeType==5?-1:_changeType==6?1:0);
            
            if(_top+((_change != 0 && _changeType == 9)?_interOffset:0) <= 0) road(g2, _lanesL, _medianL, _lanesR, _medianR, _shoulderL, _shoulderR, 0, 800, _medianType, _laneType);
            else {
              
              if(_changeType == 9){ // INTERSECTION
                road(g2, _lanesL, _medianL, nlr, nmr, _shoulderL, nsr, 0, (int)_top, _newMedian, (_changeType==7?2:1)); // top
                road(g2, _lanesL, _medianL, _lanesR, _medianR, _shoulderL, _shoulderR, (int)_top+_interOffset, _gameHeight, _medianType, _laneType); // bottom
                
                // connecting segment
                if(_interType == 1) road(g2, _lanesL, _medianL, _lanesR, _medianR, _shoulderL, _shoulderR, (int)_top, (int)_top+_interOffset, _medianType, 0);
                else if(_interType == 2){
                  intersection(g2, _lanesL+_medianL+_shoulderL, _lanesR+_medianR+_shoulderR, (int)_top, (int)_top+_interOffset, _medianType);
                  roadSide(g2, _crossLanesU, _crossLanesD, (int)_top+_w*2, 0, (_m-3-(_medianL+_lanesL+_shoulderL+2)*_w),1);
                  roadSide(g2, _crossLanesU, _crossLanesD, (int)_top+_w*2, (_m+3+(_medianR+_lanesR+_shoulderR+2)*_w), _gameWidth,2);
                }
                
                if(_interType == 1){ // CROSSWALK
                  g2.setStroke(new BasicStroke(8));
                  g2.setColor(Color.white);
                  
                  g2.drawLine(_m-(_medianL+_lanesL+_shoulderL)*_w+1,(int)_top+_w/3*2,_m+(_medianR+_lanesR+_shoulderR)*_w-3,(int)_top+_w/3*2);
                  g2.drawLine(_m-(_medianL+_lanesL+_shoulderL)*_w+1,(int)_top+_interOffset-_w/3*2,_m+(_medianR+_lanesR+_shoulderR)*_w-3,(int)_top+_interOffset-_w/3*2);
                  
                  for(int x=(_m-(_medianL+_lanesL+_shoulderL)*_w+_w/2);x<(_m+(_medianR+_lanesR+_shoulderR)*_w-3);x+=_w){
                    g2.drawLine(x,(int)_top+_w/3*2,x,(int)_top+_interOffset-_w/3*2);
                  }
                }
              } else {
                road(g2, _lanesL, _medianL, nlr, nmr, _shoulderL, nsr, 0, (int)_top-_w*12, _newMedian, (_changeType==7?2:1));
                road(g2, _lanesL, _medianL, _lanesR, _medianR, _shoulderL, _shoulderR, (int)_top-_w*12, (int)_top, _medianType, (_changeType==7?2:1));
                road(g2, _lanesL, _medianL, _lanesR, _medianR, _shoulderL, _shoulderR, (int)_top, _gameHeight, _medianType, _laneType);
              }
            }
            
            // FINISH LINE
            if((_gameMode == 2 || _gameMode == 3) && (_gameType == 1 || _gameType == 2 || _gameType == 4)){
              int dd = (int)(_y-_raceDistance*42500);
              if(dd > -100){
                for(int x=0;x<_lanesR;x++){
                  g2.setColor(Color.black);
                  g2.fillRect(_m+1+(_medianR+x)*_w,dd,_w/2,_w/2);
                  g2.fillRect(_m+1+(_medianR+x)*_w+_w/2,dd+_w/2,_w/2,_w/2);
                  
                  g2.setColor(Color.white);
                  g2.fillRect(_m+1+(_medianR+x)*_w+_w/2,dd,_w/2,_w/2);
                  g2.fillRect(_m+1+(_medianR+x)*_w,dd+_w/2,_w/2,_w/2);
                }
              }
              
              if((_gameType == 1 || _gameType == 2) && _me.getY() < dd){
                _me.setAutoDrive(true);
                _raceFinish = _place;
                _gameType = 4;
              }
              
              for(int x=0;x<_cars.size();x++){
                if(_cars.get(x).getAutoDrive()  && !_cars.get(x).getFinish() && _cars.get(x).getY() < dd){
                  _cars.get(x).finish();
                  _finishTimes.add(_t);
                  _finishCars.add(_cars.get(x).getCarType());
                }
              }
              
            }
            
            // DRAW CARS
            
            _me.draw(g2);
            
            //Polygon meBounds = _me.getBoundsMe();
            
            for(int x=0;x<_traffic.size();x++){
              if(!_pause){
                
                if(_traffic.get(x) == _me && !_me.getAutoDrive()) continue;
                
                
                boolean trafBrake = false;
                
                // stop for red lights
                if(_change != 0 && _changeType == 9){
                  if(_interType == 1 || _interType == 2 || (_interType == 3 && _traffic.get(x).getAngle() < 90)){
                    if(_traffic.get(x).getSpeed() > 10 || !(_traffic.get(x).getPoliceInfluence()/* && Math.abs(_traffic.get(x).getX()-_me.getX()) < _w/2*/)){
                      if(_traffic.get(x).getAngle() < 90){
                        if(_lightPhase > 2 && (_lightPhase != 3 || (_traffic.get(x).getSpeed() < 20 && _traffic.get(x).getY() > _top+_interOffset)) && _traffic.get(x).getBounds().intersects(new Rectangle(_m+3+_medianR*_w,(int)_top+_interOffset,_lanesR*_w,50))){
                          _traffic.get(x).brake();
                          trafBrake = true;
                        } else if(_lightPhase > 2 && _traffic.get(x).getSpeed() > 15 && _traffic.get(x).getBounds().intersects(new Rectangle(_m+3+_medianR*_w,(int)_top+_interOffset+50+(_lightPhase==3?100:0),_lanesR*_w,(int)Math.pow(_traffic.get(x).getSpeed()/2,2)-(_lightPhase==3?100:0)))){
                          _traffic.get(x).brake();
                          trafBrake = true;
                        }
                      } else {
                        if(_lightPhase > 2 && (_lightPhase != 3 || (_traffic.get(x).getSpeed() < 25 && _traffic.get(x).getY() < _top)) && _traffic.get(x).getBounds().intersects(new Rectangle(_m-3-(_lanesL+_medianL)*_w,(int)_top-50,_lanesL*_w,50))){
                          _traffic.get(x).brake();
                          trafBrake = true;
                        } else if(_lightPhase > 2 && _traffic.get(x).getSpeed() > 15 && _traffic.get(x).getBounds().intersects(new Rectangle(_m-3-(_lanesL+_medianL)*_w,(int)_top-450-(_lightPhase==3?100:0),_lanesL*_w,400-(_lightPhase==3?100:0)))){
                          _traffic.get(x).brake();
                          trafBrake = true;
                        }
                      }
                    }
                  }
                }
                
                /*
                 g2.setColor(Color.red);
                 g2.drawRect(_m+3+_medianR*_w,(int)_top+_interOffset,_lanesR*_w,50);
                 g2.drawRect(_m-3-(_lanesL+_medianL)*_w,(int)_top-50,_lanesL*_w,50);
                 
                 g2.setColor(Color.yellow);
                 g2.drawRect(_m-3-(_lanesL+_medianL)*_w,(int)_top-450-(_lightPhase==2?100:0),_lanesL*_w,400-(_lightPhase==2?100:0));
                 g2.drawRect(_m+3+_medianR*_w,(int)_top+_interOffset+50+(_lightPhase==2?100:0),_lanesR*_w,400-(_lightPhase==2?100:0));
                 */
                
                boolean sig = _traffic.get(x).getSignalLeft() || _traffic.get(x).getSignalRight();
                int trafx = (int)_traffic.get(x).getX();
                
                // police mode
                _traffic.get(x).setPoliceInfluence(false);
                if(_police){
                  if(trafx > _m){
                    if(_traffic.get(x).getY() > _me.getY() && _traffic.get(x).getY() < _me.getY()+250){
                      _traffic.get(x).brake();
                      trafBrake = true;
                    } else {
                      if(/*_me.getY()-_traffic.get(x).getY() < 800*/ _traffic.get(x).getY() < _me.getY()/* && _traffic.get(x).getY() > _me.getY()-400*/ && Math.abs(_traffic.get(x).getLane()-_me.getX()) < _w*3/2){
                        if(trafx - _me.getX() <= 0){
                          if(trafx > _m+3+(Math.max(_medianR,nmr)+1)*_w){
                            if(_changeType != 9 || _traffic.get(x).getY()-(_top+_interOffset) > _w*36){
                              _traffic.get(x).changeLane(-_w);
                              _traffic.get(x).changeX(-1);
                            }
                          } else if(_traffic.get(x).getLane() > _m+(Math.max(_medianR,nmr)+1)*_w){
                            if(_changeType != 9 || _traffic.get(x).getY()-(_top+_interOffset) > _w*36){
                              _traffic.get(x).changeLane(_w);
                              _traffic.get(x).changeX(1);
                            }
                          }
                        } else if(_traffic.get(x).getLane() < _m+(Math.min(_medianR+_lanesR,nmr+nlr)-1)*_w){
                          if(_changeType != 9 || _traffic.get(x).getY()-(_top+_interOffset) > _w*20){
                            _traffic.get(x).changeLane(_w);
                            _traffic.get(x).changeX(1);
                          }
                        } else if(trafx-_me.getX() < _w/3 && trafx > _m+3+(Math.max(_medianR,nmr)+1)*_w){
                          if(_changeType != 9 || _traffic.get(x).getY()-(_top+_interOffset) > _w*20){
                            _traffic.get(x).changeLane(-_w);
                            _traffic.get(x).changeX(-1);
                          }
                        }
                      }
                    }
                    
                    if(Math.abs(_me.getX()-_traffic.get(x).getX()) < _w/2) _traffic.get(x).setPoliceInfluence(true);
                  } else {
                    if(_me.getX() < _m-_medianL*_w){
                      if(trafx > _m-(_medianL+_lanesL-1)*_w && _traffic.get(x).getLane() == trafx){
                        _traffic.get(x).changeX(-1);
                        _traffic.get(x).changeLane(-_w);
                      }// else if(trafx == _traffic.get(x).getLane() && _traffic.get(x).getY() > 0) _traffic.get(x).brake();
                    }
                  }
                  /*
                   if(trafx == _traffic.get(x).getLane() && (meBounds.intersects(_traffic.get(x).getDangerAreaLeft()) || meBounds.intersects(_traffic.get(x).getDangerAreaRight()))){
                   _traffic.get(x).brake();
                   trafBrake = true;
                   }*/
                  
                }
                
                
                
                // COLLISION
                _traffic.get(x).setCrash(false);
                for(int y=0;y<_cars.size();y++){
                  boolean cra = false;
                  if(_traffic.get(x) != _cars.get(y) && (
                                                         (_cars.get(y) == _me && _cars.get(y).getBoundsMe().intersects(_traffic.get(x).getBounds())) ||
                                                         (_cars.get(y) != _me && _traffic.get(x).getBounds().intersects(_cars.get(y).getBounds())))){
                    
                    Car front, back;
                    if(_traffic.get(x).getY() > _cars.get(y).getY()){
                      back = _traffic.get(x);
                      front = _cars.get(y);
                    } else {
                      back = _cars.get(y);
                      front = _traffic.get(x);
                    }
                    
                    
                    double ns = Math.max(0,(int)(_cars.get(y).getSpeed()*Math.cos(Math.toRadians(_cars.get(y).getAngle()))+_traffic.get(x).getSpeed()*Math.cos(Math.toRadians(_traffic.get(x).getAngle())))/2-.25);
                    _traffic.get(x).setSpeed(ns);
                    _cars.get(y).setSpeed(ns);
                    
                    double na = (back.getSpeed()/5.0)*Math.sin(Math.toRadians(back.getAngle()))/2;
                    
                    front.changeX(na);
                    back.changeX(-na);
                    
                    
                    if(ns < 1){
                      if(front == _me) _me.setSpeed(5);
                      else front.changeY(-2);
                      _traffic.get(x).setCrash(false);
                      _cars.get(y).setCrash(false);
                    } else {
                      _traffic.get(x).setCrash(true);
                      _cars.get(y).setCrash(true);
                    }
                  }
                  //if(!cra) _cars.get(y).setCrash(false);
                }
                
                
                // collision detection
                /*
                 if(_traffic.get(x) != _me && meBounds.intersects(_traffic.get(x).getBounds())){
                 _traffic.get(x).setCrash(true);
                 
                 int ns = Math.max(0,(int)(_me.getSpeed()+_traffic.get(x).getSpeed())/2);
                 if(_traffic.get(x).getAngle() > 90) ns = (int)(_me.getSpeed()-_traffic.get(x).getSpeed())/2;
                 
                 double kn = (_me.getSpeed()/5.0)*Math.sin(Math.toRadians(_me.getAngle()))/2;
                 _traffic.get(x).changeX(kn);
                 _me.changeX(-kn);
                 reset();
                 _scoreIncrease = -500;
                 _traffic.get(x).setSpeed(ns-1);
                 _me.setSpeed(ns);
                 
                 
                 
                 if(_me.getSpeed() == 0) _traffic.get(x).changeY(-1);
                 } else _traffic.get(x).setCrash(false);
                 */
                
                
                int nsp = 0;
                
                // braking
                /*
                 if(_me.getAutoDrive() == false && _traffic.get(x) != _me && ((meBounds.intersects(_traffic.get(x).getDangerAreaFront())) || (sig && ((meBounds.intersects(_traffic.get(x).getDangerAreaSideRight())) || (meBounds.intersects(_traffic.get(x).getDangerAreaSideLeft())))))){
                 _traffic.get(x).brake();
                 trafBrake = true;
                 } else {
                 */
                if(_traffic.get(x).getSignal()) _traffic.get(x).setTempDSpeed(-1);
                for(int y=0;y<_cars.size();y++){
                  if(_traffic.get(x) != _cars.get(y)){
                    
                    if(_traffic.get(x).getAutoDrive() &&
                       _traffic.get(x).getSignal()){
                      
                      if((_traffic.get(x).getSignalLeft() && _traffic.get(x).getDangerAreaSideLeft().intersects(_cars.get(y).getBounds())) ||
                         (_traffic.get(x).getSignalRight() && _traffic.get(x).getDangerAreaSideRight().intersects(_cars.get(y).getBounds())) ||
                         (_traffic.get(x).getCutAcrossBackTo().intersects(_cars.get(y).getBounds()) && _cars.get(y).getSpeed() > _traffic.get(x).getSpeed())){
                        if(_traffic.get(x).getForceChangeLane()) _traffic.get(x).brake();
                        else _traffic.get(x).autoSetLane();
                        
                      }
                      
                      if(((_traffic.get(x).getX()-_traffic.get(x).getLane() > 0 && _cars.get(y).getX() < _traffic.get(x).getX()) ||
                          _traffic.get(x).getX()-_traffic.get(x).getLane() < 0 && _cars.get(y).getX() > _traffic.get(x).getX()) &&
                         _traffic.get(x).getCutAcrossFrontTo().intersects(_cars.get(y).getBounds()) && 
                         _cars.get(y).getSpeed() < _me.getSpeed()){
                        if((_traffic.get(x).getTempDSpeed () < 10 || _cars.get(y).getSpeed() < _traffic.get(x).getTempDSpeed())) _traffic.get(x).setTempDSpeed((int)_cars.get(y).getSpeed());
                      }
                    }
                    if((_traffic.get(x).getDangerAreaFront()).intersects(_cars.get(y).getBounds()) || 
                       (_traffic.get(x).getX() != _traffic.get(x).getLane() && (
                                                                                (_traffic.get(x).getSignalRight() && _traffic.get(x).getDangerAreaSideRight().intersects(_cars.get(y).getBounds()) && !(_cars.get(y).getSignalLeft() && _cars.get(y).getDangerAreaSideLeft().intersects(_traffic.get(x).getBounds()))) || 
                                                                                (_traffic.get(x).getSignalLeft() && _traffic.get(x).getDangerAreaSideLeft().intersects(_cars.get(y).getBounds()))))){
                      _traffic.get(x).brake();
                      trafBrake = true;
                      
                      if(_traffic.get(x).getAutoDrive()){
                        nsp = (int)_cars.get(y).getSpeed();
                        
                        if(_traffic.get(x).getX() == _traffic.get(x).getLane() && _laneType != 2){// && (_changeType != 9 || _traffic.get(x).getSpeed() > 10)){ // AUTO DRIVE CHANGE LANES
                          Stack<Integer> laneCheck = new Stack<Integer>();
                          int laLeft = (_traffic.get(x).getLane()-(_m+6+(_medianR)*_w))/_w, laRight = (_traffic.get(x).getLane()-(_m+6+(_medianR+_lanesR)*_w))/_w;
                          while(laLeft > 1 || laRight < -1){
                            if(laRight < -1) laneCheck.push(laRight++);
                            if(laLeft > 1) laneCheck.push(laLeft--);
                          }
                          if(laRight < 0) laneCheck.push(-1);
                          if(laLeft > 0) laneCheck.push(1);
                          
                          boolean stopcheck = false;
                          int hitbrakes = -1;
                          while(!laneCheck.empty()){
                            int tm = laneCheck.pop();
                            boolean tgo = true;
                            for(int zi=0;zi<_cars.size();zi++){
                              if(_traffic.get(x).getOpeningLane(tm).intersects(_cars.get(zi).getBounds())){
                                tgo = false;
                                break;
                              }
                              
                              if(_traffic.get(x).getCutAcross(tm).intersects(_cars.get(zi).getBounds()) || (_traffic.get(x).getCutAcrossBack(tm).intersects(_cars.get(zi).getBounds()) && _cars.get(zi).getSpeed() > _traffic.get(x).getSpeed())){
                                stopcheck = false;
                                break;
                              }
                              
                              if(_traffic.get(x).getCutAcrossFront(tm).intersects(_cars.get(zi).getBounds()) && _cars.get(zi).getSpeed() < _traffic.get(x).getSpeed()){
                                if(hitbrakes == -1) hitbrakes = (int)_cars.get(zi).getSpeed();
                                else hitbrakes = (int)Math.min(hitbrakes,_cars.get(zi).getSpeed());
                              }
                            }
                            if(stopcheck) break;
                            
                            if(tgo){
                              boolean ok = true;
                              for(int zi=0;zi<_cars.size();zi++){
                                if(_traffic.get(x).getCutAcross(tm).intersects(_cars.get(zi).getBounds())){
                                  ok = false;
                                  break;
                                }
                              }
                              if(ok){
                                _traffic.get(x).changeLane(-_w*tm, _m+3+(_medianR+((_change!=0&&_changeType==3)?1:0))*_w,_m+3+(_medianR+_lanesR+((_change!=0&&_changeType==1)?-1:0))*_w);
                                _traffic.get(x).setTempDSpeed(hitbrakes);
                                //System.out.println("\n\nSwitching lanes: "+tm+"\nCurrent x: "+_traffic.get(x).getX()+"\nNew x: "+_traffic.get(x).getLane());
                              }
                              
                              break;
                            }
                          }
                          
                          _traffic.get(x).setSpeedCarAhead((int)_cars.get(y).getSpeed());
                          _traffic.get(x).setYCarAhead((int)_cars.get(y).getY()+_cars.get(y).getLength());
                          
                        }
                      }
                      break;
                    } else if(!trafBrake && !(_interType == 3 && _startPos)) {
                      _traffic.get(x).noBrake();
                    }
                  }
                }
                
                if(_interType == 3 && _startPos && _traffic.get(x).getAngle() < 90 && _traffic.get(x).getY() > _me.getY()) _traffic.get(x).brake();
                
                if(_traffic.get(x).getDebug()){
                  
                  if(_laneType != 2){
                    Stack<Integer> laneCheck = new Stack<Integer>();
                    int laLeft = (_traffic.get(x).getLane()-(_m+6+(_medianR)*_w))/_w, laRight = (_traffic.get(x).getLane()-(_m+6+(_medianR+_lanesR)*_w))/_w;
                    while(laLeft > 1 || laRight < -1){
                      if(laRight < -1) laneCheck.push(laRight++);
                      if(laLeft > 1) laneCheck.push(laLeft--);
                    }
                    if(laRight < 0) laneCheck.push(-1);
                    if(laLeft > 0) laneCheck.push(1);
                    
                    int curVal = 1;
                    while(!laneCheck.empty()){
                      _traffic.get(x).fillOpeningLane(g2,laneCheck.pop(),curVal++);
                    }
                  }
                }
                
                //if(_traffic.get(x) == _me) g2.fill(_me.getOpeningLane((_me.getLane()-(_m+6+(_medianR)*_w))/_w));
                //if(_traffic.get(x) != _me) 
                if(_traffic.get(x).getY() > -150 || _t%4==0) _traffic.get(x).move(_me.getSpeed()*Math.cos(Math.toRadians(_me.getAngle())),nsp);
                
                //if(_traffic.get(x).getFurthestPointRight() > _me.getFurthestPointLeft() || _traffic.get(x).getFurthestPointLeft() < _me.getFurthestPointRight()){
                // _timer.stop();
                //}
                
                if(_traffic.get(x).getY() < _min && !_traffic.get(x).getAutoDrive()){
                  _traffic.get(x).setY(_max);
                  //_traffic.get(x).setColor(randomColor());
                  
                  if(_traffic.get(x).getX() > _m){
                    
                    int medr = _medianR;
                    int lanr = _lanesR;
                    if(_change != 0){
                      if(_changeType == 3) medr++;
                      
                      if(_changeType == 1 || _changeType == 3) lanr--;
                    }
                    
                    _traffic.get(x).reset(_m+3+((int)(Math.random()*lanr)*_w)+medr*_w+(_w-_carWidth)/2,0);
                  }
                  
                } else if(_traffic.get(x).getY() < _gameHeight && _traffic.get(x) != _me) _traffic.get(x).draw(g2);
                else if(_traffic.get(x).getY() >= _max && !_traffic.get(x).getAutoDrive()){
                  _traffic.get(x).setY(_min);
                  //_traffic.get(x).setColor(randomColor());
                  
                  if(_traffic.get(x).getX() > _m){
                    
                    int medr = _medianR;
                    int lanr = _lanesR;
                    if(_change != 0){
                      if(_changeType == 3) medr++;
                      
                      if(_changeType == 1 || _changeType == 3) lanr--;
                    }
                    
                    _traffic.get(x).reset(_m+3+((int)(Math.random()*lanr)*_w)+medr*_w+(_w-_carWidth)/2,0);
                  }
                  
                  else {
                    int medl = _medianL;
                    int lanl = _lanesL;
                    
                    _traffic.get(x).reset(_m-3-((int)(Math.random()*lanl)*_w)-_w-medl*_w+(_w-_carWidth)/2,180);
                  }
                  
                  
                } 
              } else if(_traffic.get(x) != _me) _traffic.get(x).draw(g2);
            }
            
            
            
            // TRAFFIC LIGHT
            if(_change != 0 && _changeType == 9 && _top+_interOffset > -150){
              
              
              TrafficLight k = new TrafficLight(Math.max(Math.min(_lightPhase-1,3),1), 30);
              k.draw(g2,_m+3+(_medianR+_lanesR+_shoulderR)*_w+5,(int)_top+_interOffset);
              if(_interType == 1 || _interType == 2) k.draw(g2,_m-3-(_medianL+_lanesL+_shoulderL+1)*_w+5,(int)_top-k.getHeight());
              if(_medianType == 2) k.draw(g2,_m+3+(_medianR-1)*_w+10,(int)_top+_interOffset);
              if(_newMedian == 2) k.draw(g2,_m+3-_medianL*_w+5,(int)_top-k.getHeight());
              
              if(_interType == 1){
                Image p;
                if(_lightPhase == 5) p = _pedWalk;
                else if(_lightPhase == 6){
                  if((_t%50)/25 == 0) p = _pedDontWalk;
                  else p = _pedBlank;
                } else p = _pedDontWalk;
                
                g2.drawImage(p, _m+3+(_medianR+_lanesR+_shoulderR)*_w+5, (int)_top+(_interOffset-50)/2, this);
                g2.drawImage(p, _m-3-(_medianL+_lanesL+_shoulderL+1)*_w-30, (int)_top+(_interOffset-50)/2, this);
                
              } else if(_interType == 2){
                TrafficLight w = new TrafficLight((_lightPhase==5||_lightPhase==6)?1:_lightPhase==7?2:3, 30);
                w.draw(g2, _m-3-(_medianL+_lanesL+_shoulderL+2)*_w,(int)_top+_interOffset-_w*2+10,90);
                w.draw(g2, _m+3+(_medianR+_lanesR+_shoulderR+2)*_w, (int)_top+_w*2-10, -90);
                
                Image pMe, pCross;
                if(_lightPhase == 1) pMe = _pedWalkHalf;
                else if(_lightPhase == 2){
                  if((_t%50)/25 == 0) pMe = _pedDontWalkHalf;
                  else pMe = _pedBlankHalf;
                } else pMe = _pedDontWalkHalf;
                
                if(_lightPhase == 5) pCross = _pedWalkHalf;
                else if(_lightPhase == 6){
                  if((_t%50)/25 == 0) pCross = _pedDontWalkHalf;
                  else pCross = _pedBlankHalf;
                } else pCross = _pedDontWalkHalf;
                
                int rr = _medianR+_lanesR+_shoulderR;
                int ll = _medianL+_lanesL+_shoulderL;
                
                g2.drawImage(pCross, _m+3+rr*_w+5+_w/4, (int)_top+_w/3, this);
                g2.drawImage(pCross, _m-3-(ll+1)*_w, (int)_top+_w/3, this);
                g2.drawImage(pCross, _m+3+rr*_w+5+_w/4, (int)_top+(_crossLanesU+_crossLanesD+3)*_w+_w/4, this);
                g2.drawImage(pCross, _m-3-(ll+1)*_w, (int)_top+(_crossLanesU+_crossLanesD+3)*_w+_w/4, this);
                
                g2.drawImage(pMe, _m+3+rr*_w+_w, (int)_top+_w, this);
                g2.drawImage(pMe, _m-3-(ll+1)*_w-_w*3/4, (int)_top+_w, this);
                g2.drawImage(pMe, _m+3+rr*_w+_w, (int)_top+(_crossLanesU+_crossLanesD+3)*_w-_w/2, this);
                g2.drawImage(pMe, _m-3-(ll+1)*_w-_w*3/4, (int)_top+(_crossLanesU+_crossLanesD+3)*_w-_w/2, this);
              }
              
              
            }
            
            
            
            
            // ROAD SIGNS
            if(_gameMode == 2 && _change != 0 && _top < _me.getY()){
              if(_changeType == 1) g2.drawImage(_rightLaneEnds,_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,480,this);
              else if(_changeType == 3) g2.drawImage(_leftLaneEnds,_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,480,this);
              
              else if(_changeType == 9 && _me.getY()-_top-_interOffset > 200){ 
                g2.drawImage(_trafficLightAhead,_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,480,this);
                if(_interType == 1) g2.drawImage(_pedestrianCrossing,_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,360,this);
                else if(_interType == 2){
                  g2.drawImage(_noTurns,_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2+4,360,this);
                }
                
                int ft = ((int)(-(_top-_interOffset-_me.getY())/10*_mult))/50*50;
                
                // AHEAD TRAFFIC LIGHT
                g2.setColor((_lightPhase==1||_lightPhase==2)?_lightGreen:_lightPhase==3?_lightYellow:_lightRed);
                g2.setFont(new Font("Arial",Font.BOLD,32));
                g2.drawString(ft+" ft",_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,610);
                //if(_lightPhase == 2) g2.drawString("YELLOW AHEAD",_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,620);
                //if(_lightPhase != 1 && _lightPhase != 2) g2.drawString("RED AHEAD",_m+(_lanesR+_medianR+_shoulderR)*_w+_w/2,620);
                
                g2.setFont(new Font("Arial",Font.BOLD,30));
                
                if(_top+_interOffset < 0){
                  if(_lightPhase < 3){
                    //g2.setColor(Color.white);
                    //g2.drawRect(_m+_medianR*_w+_w,99,314,40);
                    
                    g2.setColor(_blackOpac);
                    g2.fillRect(_m+_medianR*_w+_w,99,314,40);
                    
                    g2.setColor(Color.green);
                    g2.drawString("GREEN LIGHT "+ft+" FT",_m+3+_medianR*_w+_w,130);
                  } else if(_lightPhase == 3){
                    //g2.setColor(Color.white);
                    //g2.drawRect(_m+_medianR*_w+_w,99,335,40);
                    
                    g2.setColor(_blackOpac);
                    g2.fillRect(_m+_medianR*_w+_w,99,335,40);
                    
                    g2.setColor(Color.yellow);
                    g2.drawString("YELLOW LIGHT "+ft+" FT",_m+3+_medianR*_w+_w,130);
                  } else if(_lightPhase > 3){
                    //g2.setColor(Color.white);
                    //g2.drawRect(_m+3+_medianR*_w+_w,99,274,40);
                    
                    g2.setColor(_blackOpac);
                    g2.fillRect(_m+3+_medianR*_w+_w,99,274,40);
                    
                    g2.setColor(Color.red);
                    g2.drawString("RED LIGHT "+ft+" FT",_m+8+_medianR*_w+_w,130);
                  }
                }
              }
              
              if(_medianType != 2 && _newMedian == 2 && (_t%50)/25 == 0 && _top < 0) g2.drawImage(_keepRight,_m+((_medianR-_medianL)*_w)/2-(66+_w)/4,100,this);
              
              if((_laneType == 2 && _top < _me.getY()) || (_changeType == 7 && (_top > _me.getY() || (_t%50)/25 == 0))) g2.drawImage(_stayInLane,_m+(_lanesR+_medianR+_shoulderR+2)*_w-48,400,this);
              
            }
            
            
            
            
            
            
            
            // SCORE
            
            int tp = 0;//632;
            if((_gameMode == 2 || _gameMode == 3) && _gameType != 3){
              
              
              // RACE DISTANCE
              
              g2.setColor(Color.white);
              g2.fillRect(50,150,_sMaxLanes*3+12,_gameHeight-300+12);
              g2.setColor(Color.black);
              g2.setStroke(new BasicStroke(1));
              g2.drawRect(50,150,_sMaxLanes*3+12,_gameHeight-300+12);
              
              /*
               int count = 0;
               for(double x=0;x<=_raceDistance;x+=(_raceDistance/5.0)){
               g2.drawString(((int)(x*10))/10.0+" mi",5,(int)(_gameHeight-150-count++*((_gameHeight-300)/5.0))+5);
               }*/
              
              g2.setFont(new Font("Arial",Font.PLAIN,12));
              int xx;
              for(xx=0;xx<=_raceDistance;xx+=(int)(_raceDistance/10)+1){
                g2.drawString(((int)(xx*10))/10.0+" mi",5,(int)(_gameHeight-150-(xx/_raceDistance)*(_gameHeight-300))+10);
              }
              if(xx != _raceDistance) g2.drawString(_raceDistance+" mi",5,160);
              
              g2.setColor(Color.gray);
              
              
              int place = 1;
              
              for(int x=0;x<_cars.size();x++){
                if(_cars.get(x).getAutoDrive() && _cars.get(x) != _me){
                  
                  g2.fillOval(51+(int)((_cars.get(x).getX()-_m)/_w)*3, Math.max((int)(   ((-_cars.get(x).getY()+_y-510+550)/42500.0)  / _raceDistance *  -(_gameHeight-300)+_gameHeight-150),150), 12, 12);
                  
                  if(_cars.get(x).getY() < _me.getY()) place++;
                }
              }
              _place = place;
              
              g2.setColor(Color.red);
              g2.fillRect(51+(int)((_me.getX()-_m)/_w)*3, Math.max((int)(   ((_y-510)/42500.0)  / _raceDistance *  -(_gameHeight-300)+_gameHeight-150), 150), 12, 12);
              g2.setColor(new Color(155,0,0));
              g2.setStroke(new BasicStroke(2));
              g2.drawRect(51+(int)((_me.getX()-_m)/_w)*3, Math.max((int)(   ((_y-510)/42500.0)  / _raceDistance *  -(_gameHeight-300)+_gameHeight-150), 150), 12, 12);
              
              
            }
            
            if(_gameMode == 2 || _gameMode == 3){
              g2.setColor(_blackOpac);
              g2.fillRect(0,tp,_gameWidth,90);
              
              g2.setFont(new Font("Arial",Font.BOLD,44));
              
              g2.setColor(Color.white);
              
              g2.drawString(Math.round(_me.getSpeed())+"mph",720,tp+40);
              
              //g2.drawString(_score+"",20,tp+60);
              
              //if(_me.getDebug()){
              g2.setFont(new Font("Arial",Font.BOLD,20));
              //g2.drawString(Math.round(_distance*100)/100.0+" miles driven",720,tp+80);
              
              
              
              if(_gameType == 3) g2.drawString((int)((_me.getY()+_y-550)/425)/100.0+" miles driven",720,tp+80);
              else g2.drawString(_place+ (_place==1?"st" : _place==2? "nd" : _place==3?"rd" : "th")+" place", 720, tp+80);
              
              //}
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Arial",Font.BOLD,36));
              
              if((_gameType == 1 || _gameType == 2) && !_startPos) g2.drawString((_t*100/67)/100.0+"",20,tp+60);
              
              g2.drawString(_message,140,tp+60);
              
            }
            
            if(_startPos){ // START OF RACE
              int tim = (367-_t)/67;
              if(tim > 0){
                g2.setFont(new Font("Verdana",Font.BOLD,100));
                g2.setColor(Color.ORANGE);
                g2.drawString(tim+"",_gameWidth/2-25,_gameHeight/2-25);
                
                if(_gameType == 2){
                  g2.setColor(_blackOpac);
                  g2.fillRect(_m-115,110,700,160);
                  
                  g2.setColor(Color.green);
                  g2.setFont(new Font("Arial",Font.BOLD,30));
                  g2.drawString("Auto-drive enabled",_m-100,140);
                  
                  g2.setColor(Color.white);
                  g2.setFont(new Font("Arial",Font.PLAIN,20));
                  g2.drawString("Press >"+KeyEvent.getKeyText(_controls[Controls.AUTODRIVE])+"< to toggle between auto-drive and player control",_m-100,180);
                  g2.drawString("Press >"+KeyEvent.getKeyText(_controls[Controls.DEBUG])+"< to toggle debug mode",_m-100,210);
                  g2.drawString("Press >"+KeyEvent.getKeyText(_controls[Controls.BRAKE])+"< to apply brakes at any time",_m-100,240);
                }
              }
              
              if((_t/34)%2 == 0){
                g2.setColor(new Color(255,0,0,50));
                g2.fillRect((int)_me.getX()-5,(int)_me.getY()-5,_me.getWidth()+10,_me.getLength()+10);
              }
            }
            
            if(_gameType == 3 && _gameMode == 2 && _startPosFree){
              g2.setColor(_blackOpac);
              g2.fillRect(_m-115,110,700,160);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Arial",Font.BOLD,30));
              g2.drawString("Free Drive",_m-100,140);
              
              g2.setFont(new Font("Arial",Font.PLAIN,20));
              g2.drawString("Press >"+KeyEvent.getKeyText(_controls[Controls.AUTODRIVE])+"< to toggle between auto-drive and player control",_m-100,180);
              g2.drawString("Press >"+KeyEvent.getKeyText(_controls[Controls.DEBUG])+"< to toggle debug mode",_m-100,210);
              g2.drawString("Press >"+KeyEvent.getKeyText(_controls[Controls.BRAKE])+"< to apply brakes at any time",_m-100,240);
            }
            
            
            // RACE FINISH - SHOW STATS
            if(_gameType == 4){
              g2.setColor(_blackOpac);
              
              g2.fillRect(100,110,_gameWidth-200,_gameHeight-150-(15-_opponentCars)*30);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Verdana",Font.BOLD,40));
              g2.drawString(_raceFinish + (_raceFinish==1?"st" : _raceFinish==2?"nd" : _raceFinish==3?"rd" : "th") +" Place", 300, 150);
              
              for(int x=0;x<_opponentCars+1;x++){
                g2.setColor(x+1 == _raceFinish ? Color.orange : Color.white);
                g2.setFont(new Font("Arial",x+1 == _raceFinish ? Font.BOLD : Font.PLAIN, 20));
                
                g2.drawString((x+1)+"",110,200+x*30);
                
                if(_finishCars.size() > x){
                  g2.drawString(_finishCars.get(x),180,200+x*30);
                  
                  int seconds = ((int)(_finishTimes.get(x)*100/67));
                  int minutes = seconds/6000;
                  seconds %= 6000;
                  
                  g2.drawString((minutes > 0 ? minutes+" min ":"")+ (seconds/100.0)+" sec",500,200+x*30);
                }
              }
              
              g2.setFont(new Font("Arial",Font.BOLD,20));
              g2.setColor(Color.white);
              g2.drawString("Press ENTER to return to main menu",120,210+(_opponentCars+1)*30);
            }
            
            
            
            
            
            if(_gameMode == 3){ // PAUSE MENU
              g2.setColor(_blackOpac);
              g2.fillRect(0,0,_gameWidth,_gameHeight);
              
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Arial",Font.BOLD,36));
              g2.drawString("Pause",400,300);
              
              drawOption(g2, "Resume", 380, 340, _pauseMenuOption == 1);
              drawOption(g2, "Controls", 380, 370, _pauseMenuOption == 2);
              drawOption(g2, "Exit To Main Menu", 380, 400, _pauseMenuOption == 3);
              
              drawNav(g2,380,460);
            }
            
            
            
            
            else if(_gameMode == 1){
              g2.setColor(_blackOpac);
              g2.fillRect(0,0,_gameWidth,_gameHeight);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Verdana",Font.BOLD,60));
              g2.drawString("Street Racer",100,100);
              
              g2.setFont(new Font("Arial",Font.PLAIN,20));
              g2.drawString("Created by Andrew Zarenberg",50,_gameHeight-100);
              
              drawOption(g2, "Quick Race", 80, 240, _mainMenuOption == 1);
              drawOption(g2, "Custom Race", 80, 270, _mainMenuOption == 2);
              drawOption(g2, "Auto-Drive Simulation Race", 80, 300, _mainMenuOption == 3);
              drawOption(g2, "Free Drive", 80, 330, _mainMenuOption == 4);
              drawOption(g2, "Controls", 80, 360, _mainMenuOption == 5);
              //drawOption(g2, "Settings", 80, 330, _mainMenuOption == 4);
              drawNav(g2,80,420);
            }
            
            else if(_gameMode == 4){ // CUSTOM SELECT CAR
              g2.setColor(_blackOpac);
              g2.fillRect(0,0,_gameWidth,_gameHeight);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Verdana",Font.BOLD,40));
              g2.drawString("Select Car",200,100);
              
              //_customCarA = {"BMW 3-Series","Chevrolet Volt", "Dodge Challenger", "Dodge Viper", "Lexus HS", "Mercedes Benz E-Class"};
              
              g2.setColor(Color.gray);
              g2.fillRect(410,180,80,130);
              
              g2.setStroke(new BasicStroke(5));
              g2.setColor(Color.black);
              g2.drawRect(410,180,80,130);
              
              Car k;
              
              int x = 450, y = 200, angle = 0;
              
              if(_customCar == 0) k = new BMW3(x, y, angle, 0,0,0,0);
              else if(_customCar == 1) k = new ChevyVolt(x, y, angle, 0,0,0,0);
              else if(_customCar == 2) k = new DodgeChallenger(x, y, angle, 0,0,0,0);
              else if(_customCar == 3) k = new DodgeViper(x, y, angle, 0,0,0,0);
              else if(_customCar == 4) k = new LexusHS(x, y, angle, 0,0,0,0);
              else k = new MercedesBenzEClass(x, y, angle, 0,0,0,0);
              
              k.draw(g2);
              
              drawSelect(g2, "Car",200,400,360, _customCarOption == 1, _customCarA[_customCar], _customCar>0,_customCar<_customCarA.length-1);
              drawOption(g2, "Select Car", 200, 420, _customCarOption == 2);
                
              drawNav2(g2,200,480);
            }
            
            else if(_gameMode == 5){ // CUSTOM SELECT OPTIONS
              g2.setColor(_blackOpac);
              g2.fillRect(0,0,_gameWidth,_gameHeight);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Verdana",Font.BOLD,40));
              g2.drawString("Custom Race",200,100);
              
              drawSelect(g2, "Race Distance", 200, 400, 240, _customMenuOption == 1, (_customDistance/2.0)+" miles", _customDistance>1, _customDistance<_customDistanceMax);
              drawSelect(g2, "Opponents", 200, 400, 270, _customMenuOption == 2, _customRacers+"", _customRacers>0, _customRacers<_customRacersMax);
              drawSelect(g2, "Traffic", 200, 400, 300, _customMenuOption == 3, _customTrafficA[_customTraffic], _customTraffic>0, _customTraffic<_customTrafficA.length-1); 
              drawSelect(g2, "Road Width", 200, 400, 330, _customMenuOption == 4, _customRoadSizeA[_customRoadSize], _customRoadSize>0, _customRoadSize<_customRoadSizeA.length-1);
              drawSelect(g2, "Lane Width", 200, 400, 360, _customMenuOption == 5, _customLaneWidthA[_customLaneWidth], _customLaneWidth>0, _customLaneWidth<_customLaneWidthA.length-1);
              
              drawOption(g2, "Start Race", 200, 420, _customMenuOption == 6);
              
              drawNav2(g2,200,480);
            }
            
            else if(_gameMode == 6){ // FREE DRIVE OPTIONS
              g2.setColor(_blackOpac);
              g2.fillRect(0,0,_gameWidth,_gameHeight);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Verdana",Font.BOLD,40));
              g2.drawString("Free Drive",200,100);
              
              drawSelect(g2, "Traffic", 200, 400, 240, _freeMenuOption == 1, _customTrafficA[_customTraffic], _customTraffic>0, _customTraffic<_customTrafficA.length-1); 
              drawSelect(g2, "Road Width", 200, 400, 270, _freeMenuOption == 2, _customRoadSizeA[_customRoadSize], _customRoadSize>0, _customRoadSize<_customRoadSizeA.length-1);
              drawSelect(g2, "Lane Width", 200, 400, 300, _freeMenuOption == 3, _customLaneWidthA[_customLaneWidth], _customLaneWidth>0, _customLaneWidth<_customLaneWidthA.length-1);
              
              drawOption(g2, "Start Drive", 200, 360, _freeMenuOption == 4);
              
              drawNav2(g2,200,420);
            }
            
            else if(_gameMode == 7 || _gameMode == 8){ // CONTROLS
              g2.setColor(_blackOpac);
              g2.fillRect(0,0,_gameWidth,_gameHeight);
              
              g2.setColor(Color.white);
              g2.setFont(new Font("Verdana",Font.BOLD,40));
              g2.drawString("Controls",200,100);
              
              for(int x=0;x<_controls.length;x++){
                drawOption2(g2, _controlsText[x], KeyEvent.getKeyText(_controls[x]),200,400,200+30*x, _controlMenuOption == x+1);
              }
              
              drawOption(g2, "Back to "+ ( _gameMode==7?"Main Menu":"Game"), 200, 200+(_controls.length+2)*30, _controlMenuOption == _controls.length+1);
              drawNav(g2,200,200+(_controls.length+2)*30+60);
              /*
              drawOption2(g2, "Accelerate", KeyEvent.getKeyText(_controls[0]), 200, 400, 200, _controlMenuOption == 1);
              drawOption2(g2, "Brake", KeyEvent.getKeyText(_controlsBrake), 200, 400, 230, _controlMenuOption == 2);
              drawOption2(g2, "Turn Right", KeyEvent.getKeyText(_controlsRight), 200, 400, 260, _controlMenuOption == 3);
              drawOption2(g2, "Turn Left", KeyEvent.getKeyText(_controlsLeft), 200, 400, 290, _controlMenuOption == 4);
              */
              if(_controlSelectType == 2 || _controlSelectType == 3){ // CHOOSE NEW KEY
                g2.setColor(Color.white);
                g2.fillRect(250,250,400,150);
                g2.setColor(Color.black);
                g2.setStroke(new BasicStroke(4));
                g2.drawRect(250,250,400,150);
                
                g2.setFont(new Font("Arial",Font.BOLD,20));
                g2.drawString("Press new key now",310,310);
                
                if(_controlSelectType == 3){
                  g2.setColor(Color.red);
                  g2.drawString("Key Already Assigned", 310, 340);
                }
                g2.setColor(Color.black);
                g2.setFont(new Font("Arial",Font.PLAIN,15));
                g2.drawString("Press ESCAPE to cancel",310,380);
                
              }
            }
            
            
            if(_me.getDebug()){
              
              
              g2.setColor(_blackOpac);
              g2.fillRect(0,90,200,_gameHeight-90);
              
              g2.setFont(new Font("Arial",Font.PLAIN,13));
              
              g2.setColor(Color.white);
              g2.drawString("_angle: "+_me.getAngle(),20,tp+120);
              g2.drawString("_lane: "+_me.getLane(),20,tp+135);
              g2.drawString("_speedCarAhead: "+_me.getSpeedCarAhead(),20,tp+150);
              g2.drawString("_tempDSpeed: "+_me.getTempDSpeed(),20,tp+165);
              g2.drawString("_x: "+_me.getX(),20,tp+180);
              g2.drawString("_yCarAhead: "+_me.getYCarAhead(),20,tp+195);
              g2.drawString("_roadMin: "+_me.getRoadMin(),20,tp+210);
              g2.drawString("_roadMax: "+_me.getRoadMax(),20,tp+225);
              
              
              g2.setFont(new Font("Arial",Font.BOLD,15));
              g2.drawString("Boolean variables:",20,tp+250);
              //g2.setFont(new Font("Arial",Font.PLAIN,15));
              debugBool(g2, _me.getAutoDrive(), "_autoDrive", 20, tp+270);
              debugBool(g2, _me.getCrash(), "_crash", 20, tp+290);
              debugBool(g2, _me.getJustChanged(), "_justChanged", 20, tp+310);
              debugBool(g2, _me.getAngleNoChange(), "_angleNoChange", 20, tp+330);
              debugBool(g2, _me.getForceChangeLane(), "_forceChangeLane", 20, tp+350);
            }
            
            g.drawImage(_img,0,0,this);
          }
          
          
          private void drawNav(Graphics2D g2, int x, int y){
            g2.setFont(new Font("Arial",Font.PLAIN, 15));
            g2.setColor(Color.white);
            g2.drawString(_navigationDirections, x, y);
          }
          private void drawNav2(Graphics2D g2, int x, int y){
            g2.setFont(new Font("Arial",Font.PLAIN, 15));
            g2.setColor(Color.white);
            g2.drawString(_navigationDirections2, x, y);
          }
          
          private void drawOption(Graphics2D g2, String text, int x, int y, boolean sel){
            g2.setColor(sel ? Color.ORANGE : Color.WHITE);
            g2.setFont(sel ? _optionSelected : _option);
            g2.drawString(text, x, y);
            
            if(sel) g2.fillRect(x-18,y-12,10,10);
          }
          
          private void drawOption2(Graphics2D g2, String text, String opt, int x, int x2, int y, boolean sel){
            g2.setColor(sel ? Color.ORANGE : Color.WHITE);
            g2.setFont(sel ? _optionSelected : _option);
            g2.drawString(text, x, y);
            g2.drawString(opt, x2, y);            
            
            if(sel) g2.fillRect(x-18,y-12,10,10);
          }
          
          private void drawSelect(Graphics2D g2, String text, int x, int x2, int y, boolean sel, String opt, boolean min, boolean max){
            g2.setColor(sel ? Color.ORANGE : Color.WHITE);
            g2.setFont(sel ? _optionSelected : _option);
            g2.drawString(text, x, y);
            
            g2.drawString((sel&&min?"< ":"")+opt+(sel&&max?" >":""), x2,y);
            
            if(sel) g2.fillRect(x-18,y-12,10,10);
          }
          
          
          private void debugBool(Graphics2D g2, boolean n, String b, int x, int y){
            g2.setColor(n?Color.green:Color.red);
            g2.drawString(b,x,y);
          }
          
          
          public void road(Graphics2D g2, int laneL, int medL, int laneR, int medR, int shoL, int shoR, int top, int bottom, int median, int laneType){
            
            
            int da = top==0?_dashed-4*_w:top;
            
            int m = _m;
            
            int nlr = laneR;
            int nmr = medR;
            int nsr = shoR;
            if(_change != 0 && (int)(bottom-_top) == 0){
              if(_changeType == 1) nlr--;
              else if(_changeType == 2) nlr++;
              else if(_changeType == 3){
                nmr++;
                nlr--;
              } else if(_changeType == 4){
                nmr--;
                nlr++;
              } else if(_changeType == 5) nsr--;
              else if(_changeType == 6) nsr++;
            }
            
            
            // ROAD BACKGROUND
            g2.setColor(Color.gray);
            g2.fillPolygon(new int[]{m-3-(laneL+medL+shoL)*_w,m-3-(laneL+medL+shoL)*_w,m+3+(laneR+medR+shoR)*_w,m+3+(nlr+nmr+nsr)*_w},
                           new int[]{top,bottom,bottom,top},
                           4);
            
            if(laneType != 0){
              
              // YELLOW LINES
              g2.setColor(Color.yellow);
              g2.setStroke(new BasicStroke(2));
              
              g2.drawLine(m-3-medL*_w,top,m-3-medL*_w,bottom);
              g2.drawLine(m-3+nmr*_w,top,m-3+medR*_w,bottom);
              
              g2.drawLine(m+3-medL*_w,top,m+3-medL*_w,bottom);
              g2.drawLine(m+3+nmr*_w,top,m+3+medR*_w,bottom);
              
              
              // MEDIAN
              if(median == 1){
                
                if(medL + nmr > 0){
                  
                  g2.setStroke(new BasicStroke(4));
                  
                  if(medR-nmr == 0){
                    
                    for(int x=0;x<bottom/_w+_w;x+=4){
                      g2.fillPolygon(
                                     new int[]{m+3-medL*_w,m+3-medL*_w,m-3+medR*_w,m-3+medR*_w},
                                     new int[]{(medL+medR)*40+da+_w*x+5,(medL+medR)*40+da+_w*x-5,da+_w*x-5,da+_w*x+5},
                                     4);
                    }
                  }
                }
              } else if(median == 2){
                g2.setColor(new Color(247, 242, 224));
                g2.fillPolygon(new int[]{m+2-medL*_w,m+2-medL*_w,m-1+medR*_w,m-1+nmr*_w},
                               new int[]{top,bottom,bottom,top},
                               4);
              }
              
              //g2.setStroke(new BasicStroke(2));
              
              
              
              
              
              g2.setColor(_mark);
              g2.setStroke(_dd);
              
              // LEFT LANES
              int ll = m-3-medL*_w;
              for(int x=0;x<laneL-1;x++){
                ll -= _w;
                g2.drawLine(ll,da,ll,bottom);
              }
              
              // RIGHT LANES
              if(laneType == 2) g2.setStroke(new BasicStroke(2));
              int lr = m+3+medR*_w;
              for(int x=Math.max(medR,nmr)+1;x<Math.min(laneR,nlr)+Math.max(medR,nmr);x++){
                g2.drawLine(m+3+x*_w,da,m+3+x*_w,bottom);
              }
              
              // SHOULDER
              g2.setStroke(new BasicStroke(4));
              if(shoR > 0) g2.drawLine(m+4+(nlr+nmr)*_w+1,da,m+4+(laneR+medR)*_w+1,bottom);
              if(shoL > 0) g2.drawLine(ll-_w-1,da,ll-_w-1,bottom);
              
            }
            // BORDER LINES
            /*
             g2.setColor(Color.black);
             g2.setStroke(new BasicStroke(4));
             g2.drawLine(ll-_w-1,0,ll-_w-1,800);
             g2.drawLine(lr+_w+1,0,lr+_w+1,800);
             */
            
            // SIDEWALKS
            
            g2.setColor(Color.lightGray);
            g2.fillPolygon(new int[]{m-3-(laneL+medL+shoL+2)*_w,m-3-(laneL+medL+shoL+2)*_w,m-3-(laneL+medL+shoL)*_w,m-3-(laneL+medL+shoL)*_w},
                           new int[]{top,bottom,bottom,top},
                           4);
            g2.fillPolygon(new int[]{m+3+(nlr+nmr+nsr)*_w,m+3+(laneR+medR+shoR)*_w,m+3+(laneR+medR+shoR+2)*_w,m+3+(nlr+nmr+nsr+2)*_w},
                           new int[]{top,bottom,bottom,top},
                           4);
            
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(m-4-(laneL+medL+shoL)*_w,top,m-4-(laneL+medL+shoL)*_w,bottom);
            g2.drawLine(m+2+(nlr+nmr+nsr)*_w,top,m+2+(laneR+medR+shoR)*_w,bottom);
            
            
            // ROAD MARKINGS - ARROWS ON ROADS & MERGE LEFT/RIGHT
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(8));
            if(_change == 1){
              if(_changeType == 1 || _changeType == 3){
                for(int x=0;x<3;x++){
                  int v = (int)_top+_w*12*x;
                  int h = _changeType == 1 ? m+6+(_lanesR+_medianR-1)*_w : m+(_medianR+1)*_w;
                  int r = _changeType == 1 ? -30 : 30;
                  
                  g2.rotate(Math.toRadians(r),h,v);
                  
                  g2.fillPolygon(new int[]{h,h-_w/3,h,h+_w/3},
                                 new int[]{v,v+_w*2/3,v+_w/2,v+_w*2/3},
                                 4);
                  g2.drawLine(h,v+_w/2,h,v+_w*3/2);
                  /*
                   g2.fillPolygon(new int[]{h,h-_w/3,h+_w/3},
                   new int[]{v,v+_w*2/3,v+_w*2/3},
                   3);
                   g2.drawLine(h,v+_w/2,h,v+_w*3/2);*/
                  g2.rotate(-Math.toRadians(r),h,v);
                }
              } else if(_changeType == 9){
                if(_interType == 1 || _interType == 2){ // crosswalk
                  for(int x=0;x<_lanesR;x++){
                    //for(int y=0;y<3;y++){
                    int v = (int)_top+_w+_interOffset;
                    int h = m+3+(_medianR+x)*_w+_w/2;
                    
                    g2.fillPolygon(new int[]{h,h-_w/3,h,h+_w/3},
                                   new int[]{v,v+_w*2/3,v+_w/2,v+_w*2/3},
                                   4);
                    g2.drawLine(h,v+_w/2,h,v+_w*3/2);
                    //}
                  }
                  
                  for(int x=0;x<_lanesL;x++){
                    int v = (int)_top-_w;
                    int h = m-3-(_medianL+x)*_w-_w/2;
                    
                    g2.fillPolygon(new int[]{h,h-_w/3,h,h+_w/3},
                                   new int[]{v,v-_w*2/3,v-_w/2,v-_w*2/3},
                                   4);
                    g2.drawLine(h,v-_w/2,h,v-_w*3/2);
                  }
                  
                }
                // STOP LINE
                if(_top+_interOffset > 0){
                  g2.setStroke(new BasicStroke(14));
                  g2.drawLine(m+_medianR*_w+11,(int)_top+_interOffset+6,m+(_medianR+_lanesR)*_w-6,(int)_top+_interOffset+6);
                  if(_interType == 1 || _interType == 2) g2.drawLine(m-(_medianL+_lanesL+_shoulderL)*_w+5,(int)_top-6,m-_medianL*_w-9,(int)_top-6);
                }
                
              }
            }
            
            
            // end road
          }  
          
          
          public void roadSide(Graphics2D g2, int lanesU, int lanesD, int top, int left, int right, int stopLine){
            // stop line: 1 = right, 2 = left
            g2.setColor(Color.gray);
            g2.fillRect(left, top, right-left, 6+(lanesU+lanesD)*_w);
            
            // YELLOW LINES
            g2.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            
            g2.drawLine(left, top+lanesU*_w-3, right, top+lanesU*_w-3);
            g2.drawLine(left, top+lanesU*_w+3, right, top+lanesU*_w+3);
            
            
            g2.setColor(_mark);
            g2.setStroke(_dd);
            
            
            // UP LANES
            int ll = top;
            for(int x=0;x<lanesU-1;x++){
              ll += _w;
              g2.drawLine(left, ll, right-4, ll);
            }
            
            // DOWN LANES
            int lr = top+3+lanesU*_w;
            for(int x=0;x<lanesD-1;x++){
              lr += _w;
              g2.drawLine(left, lr, right-4, lr);
            }
            
            
            // STOP LINE
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(14));
            if(stopLine == 1) g2.drawLine(right-7,top+lanesU*_w+11,right-7,top+(lanesU+lanesD)*_w);
            else if(stopLine == 2) g2.drawLine(left+7,top,left+7,top+lanesU*_w-11);
            
            
            // SIDEWALKS
            
            g2.setColor(Color.lightGray);
            g2.fillRect(left, top-_w*2, right-left, _w*2);
            g2.fillRect(left, top+3+(lanesU+lanesD)*_w, right-left, _w*2);
            
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.black);
            g2.drawLine(left, top, right, top);
            g2.drawLine(left, top+3+(lanesU+lanesD)*_w, right, top+3+(lanesU+lanesD)*_w);
            
          }
          
          
          public void intersection(Graphics2D g2, int left, int right, int top, int bottom, int median){
            // ROAD BACKGROUND
            g2.setColor(Color.gray);
            g2.fillRect(_m-3-(left+2)*_w, top, (left+right+4)*_w+6, bottom-top);
            
            // CROSSWALK
            g2.setStroke(new BasicStroke(8));
            g2.setColor(Color.white);
            
            int ko = 10;
            
            // bottom
            g2.drawLine(_m-left*_w-ko,bottom-_w*2+ko,_m+right*_w+ko,bottom-_w*2+ko);
            g2.drawLine(_m-left*_w,bottom-ko,_m+right*_w,bottom-ko);
            // top
            g2.drawLine(_m-left*_w-ko,top+_w*2-ko,_m+right*_w+ko,top+_w*2-ko);
            g2.drawLine(_m-left*_w,top+ko,_m+right*_w,top+ko);
            // right
            g2.drawLine(_m+right*_w+ko,top+_w*2-ko,_m+right*_w+ko,bottom-_w*2+ko);
            g2.drawLine(_m+(right+2)*_w-ko,top+_w*2,_m+(right+2)*_w-ko,bottom-_w*2);
            // left
            g2.drawLine(_m-left*_w-ko,top+_w*2-ko,_m-left*_w-ko,bottom-_w*2+ko);
            g2.drawLine(_m-(left+2)*_w+ko,top+_w*2,_m-(left+2)*_w+ko,bottom-_w*2);
            
            
            // SIDEWALK CURVE
            g2.setColor(Color.lightGray);
            g2.fillArc(_m-5-(left+4)*_w, bottom-_w*2+2,_w*4,_w*4,0,90);
            g2.fillArc(_m+3+(right)*_w, bottom-_w*2+2,_w*4,_w*4,90,90);
            g2.fillArc(_m+3+(right)*_w, top-_w*2,_w*4,_w*4,180,90);
            g2.fillArc(_m-5-(left+4)*_w, top-_w*2,_w*4,_w*4,270,90);
            
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(2));
            g2.drawArc(_m-5-(left+4)*_w, bottom-_w*2+2,_w*4,_w*4,0,90);
            g2.drawArc(_m+3+(right)*_w, bottom-_w*2+2,_w*4,_w*4,90,90);
            g2.drawArc(_m+3+(right)*_w, top-_w*2,_w*4,_w*4,180,90);
            g2.drawArc(_m-5-(left+4)*_w, top-_w*2,_w*4,_w*4,270,90);
            
          }
          
          
          
          public Color randomColor(){
            Color[] cols = 
            {
              Color.blue, 
              Color.green, 
              Color.orange, 
              Color.pink,
              new Color(245, 169, 169),
              new Color(247, 129, 190),
              new Color(11, 97, 11),
              new Color(208, 245, 169),
              new Color(236, 206, 245),
              new Color(206, 216, 246),
              new Color(136, 106, 8)
            };
            
            return cols[(int)(Math.random()*cols.length)];
          }
          
          
          
          private class Key extends KeyAdapter {
            public void keyPressed(KeyEvent e){      
              int k = e.getKeyCode();
              if(k < 150) _keys[k] = true;
              
              _lastKey = k*1000;
              
              if((_gameMode == 2 || _gameMode == 3) && k == KeyEvent.VK_ESCAPE && _lastKey != KeyEvent.VK_ESCAPE){
                _gameMode = (_gameMode == 3 ? 2 : 3);
                _pause = !_pause;
              }
              
              /*if(k == KeyEvent.VK_H && !_horn){
                _horn = true;
                _sHorn.play();
              }*/
              
            }
            
            public void keyReleased(KeyEvent e){
              int k = e.getKeyCode();
              if(k < 150) _keys[k] = false;
              
              if(_lastKey == k) _lastKey = 0;
              
              /*if(k == KeyEvent.VK_H && _horn){
                _horn = false;
                _sHorn.stop();
              }*/
            }
          }
          
          
          private void reset(){
            //_me.setAngle(0);
            //_me.setX(_m);
            _me.setSpeed(0);
            //_change = 0;
          }
          
          
          /*
           //_me = new Car(_m+3+(_medianR+_lanesR+_shoulderR-1)*_w+(_w-_carWidth)/2,550,60,_carWidth,Color.black,_w,_siren);
           int xm = _m+6+(_medianR)*_w+(_lanesR/2)*_w;
           _me = new ChevyVolt(xm+_w,550,0,0,100,xm,_w);
           _me.toggleAutoDrive(_m+6+_medianR*_w,_m+6+(_medianR+_lanesR)*_w);
           _cars.add(_me);
           
           for(int x=0;x<0;x++){
           Car testcar = new MercedesBenzEClass(xm-_w*x,700,0,0,100,xm-_w*x,_w);
           _cars.add(testcar);
           _traffic.add(testcar);
           _traffic.get(x).setAutoDrive(true);
           }
           
           
           _traffic.add(_me);
           
           
           for(int x=0;x<10;x++){
           int xx = _m+6+(_medianR)*_w+(int)(Math.random()*_lanesR)*_w;
           Car k = randomCar(xx,(int)(Math.random()*_gameHeight),0,0,35+(int)(Math.random()*15),xx,_w);
           //Car k = randomCar(xx,(int)(Math.random()*_gameHeight),0,0,5+(int)(Math.random()*15),xx,_w);
           //if((int)(Math.random()*3)==1) k.setAutoDrive(true);
           _traffic.add(k);
           _cars.add(k);
           }
           
           for(int x=0;x<4;x++){
           int xx = _m-3-(_medianL+1)*_w-(int)(Math.random()*_lanesL)*_w;
           Car k = randomCar(xx,-(int)(Math.random()*300),180,0,30+(int)(Math.random()*20),xx,_w);
           _traffic.add(k);
           _cars.add(k);
           }
           
           
           for(int x=0;x<_traffic.size();x++){
           //_traffic.get(x).setAutoDrive(true);
           for(int y=x+1;y<_traffic.size();y++){
           if(_traffic.get(x).getBounds().intersects(_traffic.get(y).getBounds())){
           if(_traffic.get(y) == _me) _traffic.get(x).setY(_max);
           else _traffic.get(y).setY(_max);
           }
           }
           }
           */
          
          
          
          
          
          
          private void clearCars(){
            _traffic.clear();
            _cars.clear();
          }
          
          private void createPlayerCar(boolean race){
            int xm;
            if(race) xm = _m+6+(_medianR)*_w+(_lanesR/2-1)*_w;
            else xm = _m+6+(_medianR+_lanesR-1)*_w;
            
            //_customCarA = {"BMW 3-Series","Chevrolet Volt", "Dodge Challenger", "Dodge Viper", "Lexus HS", "Mercedes Benz E-Class"};
            
            if(_customCar == 0) _me = new BMW3(xm+_w,550,0,0,100,xm,_w);
            else if(_customCar == 1) _me = new ChevyVolt(xm+_w,550,0,0,100,xm,_w);
            else if(_customCar == 2) _me = new DodgeChallenger(xm+_w,550,0,0,100,xm,_w);
            else if(_customCar == 3) _me = new DodgeViper(xm+_w,550,0,0,100,xm,_w);
            else if(_customCar == 4) _me = new LexusHS(xm+_w,550,0,0,100,xm,_w);
            else if(_customCar == 5) _me = new MercedesBenzEClass(xm+_w,550,0,0,100,xm,_w);
            
            
            _me.setLights(true);
            _cars.add(_me);
            _traffic.add(_me);
          }
          
          private void createCarsLeft(int n){ 
            for(int x=0;x<n;x++){
              int xx = _m-3-(_medianL+1)*_w-(int)(Math.random()*_lanesL)*_w;
              Car k = randomCar(xx,-(int)(Math.random()*300),180,0,30+(int)(Math.random()*20),xx,_w);
              _traffic.add(k);
              _cars.add(k);
            }
          } 
          
          
          private void createCarsRight(int n){ 
            for(int x=0;x<n;x++){
              int xx = _m+6+(_medianR)*_w+(int)(Math.random()*_lanesR)*_w;
              Car k = randomCar(xx,1000,0,0,35+(int)(Math.random()*15),xx,_w);
              _traffic.add(k);
              _cars.add(k);
            }
          }
          private void createOpponentCars(int n){ 
            
            _opponentCars = n;
            
            int r = 550, c = 0;
            for(int x=0;x<n;x++){
              int xx = _m+6+(_medianR)*_w+c*_w;
              Car k = randomRacer(xx,r,0,0,100,xx,_w);
              k.setAutoDrive(true);
              k.setLights(true);
              _traffic.add(k);
              _cars.add(k);
              
              c++;
              if(c == _lanesR/2 && r == 550) c++;
              
              if(c == _lanesR){
                c = 0;
                r += 100;
              }
            }
            
            _finishTimes.clear();
            _finishCars.clear();
          }
          
          private void raceSetup(boolean race){
            _t = 0;
            _medianType = 1;
            _newMedian = 0;
            _change = 1;
            
            if(race){
              _changeType = 9;
              _interOffset = 0;
              _interType = 3;
              _lightPhase = 7;
              _top = _y = 510;
              _startPos = true;
              _shoulderR = 0;
            } else {
              _changeType = 5;
              _top = _y = 0;
              _shoulderR = 1;
              _startPosFree = true;
            }
            
            _medianL = _medianR = 0;
            _lanesL = 2;
            _lanesR = _sMaxLanes;
            _shoulderL = 0;
            
            _message = "";
          }
          
          private void setLaneWidth(int n){
            _w = n;
            _dd = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{(int)(n*1.5),(int)(n*2.5)}, 0);
          }
          
          private void setMaxLanes(int n){
            _sMaxLanes = n;
          }
          
          private void setRaceDistance(double n){
            _raceDistance = n;
          }
}
