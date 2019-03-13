import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Brick_Breaker extends PApplet {


PImage space2; // Background image
int scl = 20; // the current scale of the game 
int borderWidth = 800; // X dimension of game screen
int borderHeight = 800; // Y dimension of game screen
int rows = 5; // Number of Rows of Bricks
int columns = 5; // Number of Columns of Bricks
int gridStack = rows*columns; // Total number of bricks
Brick[] box = new Brick[gridStack]; // declare new stack of bricks
Ball ball; // declare new Ball
int score; // declare Score
boolean stopGame = false; // Determines whether game is over


/*
* sets inital size of screen
*/
public void settings(){

  size(borderWidth, borderHeight);

}

public void setup() {
  
  // Set up the bricks
  
  for (int i = 0; i < rows; i++)
  {
    for (int j = 0; j< columns; j++)
    {
      //places all the bricks into the array, properly labelled.
      
      box[i*rows + j] = new Brick((i+1) *width/(rows + 2), (j+1) * 50); 
    }
  }
  
  
  
  ball = new Ball(); // creating a "ball" of type Ball
  
  smooth();
  //frameRate(30);
  //bg = loadImage("space3.png"); // Load the background image
  score = 0; // initial score = 0;
}

public void draw() {
  
  // Conditions for Game Over and Game Start
  if (stopGame) {
    text("GAME OVER", 200, 550);
  }
  
  else {
  background(0); // set Background color
  
  //image(bg, 0, 0);  // Draw/Load Image onto screen
  
  // Create the "paddle" and fix it to a y location, moving with mouse along X axis
  fill(255);
  rect(mouseX, 650, 100, 10); // paddle of length 100 and height 10
 
  // draw the bricks
  for (int i = 0; i<gridStack; i++)
  {
    box[i].update();
  }
  
  // draw the ball
  ball.update();


  ball.hit();
  ball.edges();
  ball.gameOver();
  
  for (int i = 0; i<gridStack; i++)
  {
   
    // if Ball hits bottom of box
    
    if (((ball.y - ball.R <= box[i].y + box[i].h) && ball.y - ball.R >= box[i].y) 
  && (ball.x >= box[i].x) && (ball.x <= box[i].x + box[i].w) && (box[i].struck == false)) {
  
  
    ball.flipY(); // Flip the Y direction of the ball
    box[i].gotStruck(); // Make brick 'disappear'/change state
    score++; // accumulate score
    
    
  }
  
  // if Ball hits top of box
  
  if (((ball.y + ball.R >= box[i].y) && (ball.y - ball.R <= box[i].y + 0.5f*box[i].h) 
  && (ball.x >= box[i].x) && (ball.x <= box[i].x + box[i].w) && (box[i].struck == false))) {
  
  
    ball.flipY();
    box[i].gotStruck();
    
    score++;
  }
    
  
  }
  
  // Print out the score at coordinates (100,500)
  text(score, 100, 500);



}

}

class Ball {
  
  
  float x; // x position of the ball
  float y; // y position of the ball
  float xspeed; // xspeed of the ball
  float yspeed; // yspeed of the ball
  float R; // radius of the ball
  
  
  
  // Pong Ball constructor 
  
  Ball(){
    noStroke();
    this.x = 300;
    this.y = 300;
    this.xspeed = 0;
    this.yspeed = 2.5f;
    this.R = 15;
  }
  
  
  public void update(){
   
    noStroke();
    
    // draw ball
    fill(255);
    ellipse(this.x, this.y, R, R);
    
    
    this.x = this.x + this.xspeed;
    this.y = this.y + this.yspeed;
    
    // constrain ball within boundaries of screen
    this.x = constrain(this.x, 0, borderWidth-scl);
    this.y = constrain(this.y, 0, borderHeight-scl);
    
    
    
  }
  
  

  
  // Make ball move left
  public void moveLeft(){
  
    this.xspeed = -1*abs(this.xspeed);
  
  }
  
  // Make ball move right
  public void moveRight(){
  
    //this.xspeed = 1;
    this.xspeed = abs(this.xspeed*1);
  
  }
  
  // Flip y direction of the ball (up or down)
  public void flipY(){
    
    this.yspeed = -1*this.yspeed;
  }
  
  
  public void hit() {
    
    // If it hits the left side of the paddle, bounce left and flip Y
    
    if (((this.x < mouseX + 50) && (this.x > mouseX)) 
    && ((this.y < 670) && (this.y > 640))) {
    
      flipY();
      moveLeft();
      this.xspeed -= .7f; // Ball moves faster when it hits the paddle
    } 
    
    // If it hits the right side of the paddle, bounce right and flip Y
    
    if (((this.x <= mouseX + 100) && (this.x >= mouseX + 50)) 
    && ((this.y <= 670) && (this.y >= 640))) {
    
      flipY();
      moveRight();
      this.xspeed += .7f; // Ball moves faster when it hits the paddle
    }
    
  }
  // Edge cases, if ball hits edges
  
  public void edges() {
  
  if (this.x == borderWidth-scl){
   
    moveLeft(); // if Ball hits right edge, make it move left
    
  }
  
  if (this.x == 0) {
  
    moveRight(); // if Ball hits right edge, make it move left
  }
  
  
  if ((this.y == 0) ){ 
    
    flipY(); // if Ball hits top, make it bounce down
    
  }
    
    // if Ball hits the bottom edge, below the paddle, game is over. Print out score 
    // and reset score to 0.
    
  if (this.y == borderHeight-scl) {
  
    stopGame = true;
    text("Your score is:", 400, 500);
    text(score, 500, 500);
    score = 0;
  
  }
  
  if (score == (rows*columns)){
  
    stopGame = true;
    text("WELL DONE", 600, 600);
  }
  
}
  

 // Game Over Method  
  
  public void gameOver(){
  
  if (this.x == borderHeight){
    
    stopGame = true;
    score = 0;
  }
  
  
}
  
  
  
  
}
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
  public void update()
  {
    noStroke();
    fill(red, green, blue);
    rect(x, y, w, h);
  }

  //What happens to the brick once it gets hit
  public void gotStruck()
  {
    struck = true; //brick recognizes that it has been hit

    red = 0;
    green = 0;
    blue = 0;
    rect(x, y, w, h);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Brick_Breaker" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
