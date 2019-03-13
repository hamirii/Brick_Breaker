
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
void settings(){

  size(borderWidth, borderHeight);

}

void setup() {
  
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

void draw() {
  
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
  
  if (((ball.y + ball.R >= box[i].y) && (ball.y - ball.R <= box[i].y + 0.5*box[i].h) 
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
