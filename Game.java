import javax.swing.JFrame;

public class Game extends JFrame {
  public Game(){
    add(new Board());
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900,750);
    setResizable(false);
    setVisible(true);
    
    
    setTitle("The Streets");
  }
  
  public static void main(String[] args){
    new Game();
  }
}


/* Images from:
 * 
 * ROAD SIGNS:
 * Keep Right - http://www.safetysign.com/images/catlog/product/medium/X4543.png
 * Left Lane Ends  - http://www.safetysign.com/images/catlog/product/small/Y2371.png
 * No Turns - http://www.signsandsafetydevices.com/media/k2/items/cache/c82cc4e14a1d2c8c8ffff9840d24b558_XS.jpg
 * Pedestrian Crossing - http://freevectorfinder.com/images/thums/us_crosswalk_sign_clip_art_25661.jpg
 * Right Lane Ends - http://www.safetysign.com/images/catlog/product/small/Y2369.png
 * Stay in Lane - http://www.roadtrafficsigns.com/img/lg/X/Stay-in-Lane-Sign-X-R4-9.gif
 * Traffic Light Ahead - http://images.roadtrafficsigns.com/img/sm/X/Traffic-Light-Ahead-Sign-X-W3-3.gif
 * 
 * Pedestrian Crossing Light - http://www.fhwa.dot.gov/publications/research/safety/04091/images/fig037.gif
 * 
 * CARS:
 * BMW 3 series - http://buyersguide.caranddriver.com/media/eVox/stills_0640/6773/6773_st0640_117.jpg
 * Chevy Volt - http://www.carsendse.com/wp-content/uploads/2012/03/2012-chevy-volt-Chevrolet-electric-car-top-view-525x330.jpg
 * Dodge Challenger - http://buyersguide.caranddriver.com/media/eVox/stills_0320/7922/7922_st0320_117.jpg
 * Dodge Viper - http://buyersguide.caranddriver.com/media/eVox/stills_0320/5884/5884_st0320_117.jpg
 * Mercedes Benz E Class - http://buyersguide.caranddriver.com/media/eVox/stills_0320/6864/6864_st0320_117.jpg
 * Lambo (2008 Lamborghini Gallardo) - http://buyersguide.caranddriver.com/media/eVox/stills_0320/4446/4446_st0320_117.jpg
 * Sierra (2007 GMC Sierra) - http://buyersguide.caranddriver.com/media/eVox/stills_0320/4213/4213_st0320_117.jpg
 * Van - http://thumbs.imagekind.com/member/320388fb-42fa-43a9-85fc-f569f661a252/uploadedartwork/650X650/878e1cc8-2d69-42e3-8c61-ed4651f617e3.jpg
 * 
 * Sounds from:
 * Horn - http://www.freesfx.co.uk/soundeffects/
 * Siren - http://www.sounddogs.com/results.asp?CategoryID=1044&SubcategoryID=10&Type=1
 */