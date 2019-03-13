
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
    this.yspeed = 2.5;
    this.R = 15;
  }
  
  
  void update(){
   
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
  void moveLeft(){
  
    this.xspeed = -1*abs(this.xspeed);
  
  }
  
  // Make ball move right
  void moveRight(){
  
    //this.xspeed = 1;
    this.xspeed = abs(this.xspeed*1);
  
  }
  
  // Flip y direction of the ball (up or down)
  void flipY(){
    
    this.yspeed = -1*this.yspeed;
  }
  
  
  void hit() {
    
    // If it hits the left side of the paddle, bounce left and flip Y
    
    if (((this.x < mouseX + 50) && (this.x > mouseX)) 
    && ((this.y < 670) && (this.y > 640))) {
    
      flipY();
      moveLeft();
      this.xspeed -= .7; // Ball moves faster when it hits the paddle
    } 
    
    // If it hits the right side of the paddle, bounce right and flip Y
    
    if (((this.x <= mouseX + 100) && (this.x >= mouseX + 50)) 
    && ((this.y <= 670) && (this.y >= 640))) {
    
      flipY();
      moveRight();
      this.xspeed += .7; // Ball moves faster when it hits the paddle
    }
    
  }
  // Edge cases, if ball hits edges
  
  void edges() {
  
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
  
  void gameOver(){
  
  if (this.x == borderHeight){
    
    stopGame = true;
    score = 0;
  }
  
  
}
  
  
  
  
}
