class Brick
{
  
  float w; //brick width
  float h; //brich height
  
  
  float x; //brick x position
  float y; //brick y position
 
  float red; //brick red val
  float green; //grick green val
  float blue; //brick blue val

  boolean struck; //brick struck 'state'


    Brick(float x0, float y0)
  {
    
    
    x = x0;
    y = y0;

    // assign random colors to bricks
    red = random(255);
    green = random(255);
    blue = random(255);
      
    
    
    w = 75; //brick width
    h = 25; //brick height

    struck = false; //brick starts off 'not struck'
  }

  //Draws the brick
  void update()
  {
    noStroke();
    fill(red, green, blue);
    rect(x, y, w, h);
  }

  //What happens to the brick once it gets hit
  void gotStruck()
  {
    struck = true; //brick recognizes that it has been hit

    red = 0;
    green = 0;
    blue = 0;
    rect(x, y, w, h);
  }
}
