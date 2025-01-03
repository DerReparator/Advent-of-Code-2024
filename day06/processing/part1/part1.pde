import java.util.Optional;

void setup() {
  size(600, 600);
  surface.setTitle("AoC 2024 - Day 06, Part 1");
  
  labelFont = createFont("Arial", 14, true);
  
  map = loadStrings("day06_part_1_test1.input");
  pixelSize = size_in_pixel / (2 /* border */ + max(map.length, map[0].length()));
}

public enum Movement {

  UP(-1, 0),
  RIGHT(0,1),
  DOWN(1,0),
  LEFT(0, -1);

  public final int vertical;
  public final int horizontal;

  Movement(int vertical, int horizontal) {
    this.vertical = vertical;
    this.horizontal = horizontal;
  }

  public Movement turnRight() {
    return Movement.values()[(this.ordinal() + 1) % Movement.values().length];
  }

  public Movement turnLeft(){
    int ordinalToTheLeft = this.ordinal() - 1;
    if (ordinalToTheLeft < 0){
      ordinalToTheLeft = LEFT.ordinal();
    }
    return Movement.values()[ordinalToTheLeft];
  }
  
  public static Optional<Movement> tryParseMovement(char guardChar) {
    switch (guardChar) {
     case GUARD_UP:
       return Optional.of(Movement.UP);
     case GUARD_RIGHT:
       return Optional.of(Movement.RIGHT);
     case GUARD_DOWN:
       return Optional.of(Movement.DOWN);
     case GUARD_LEFT:
       return Optional.of(Movement.LEFT);
     default:
       return Optional.empty();
    }
  }
}

PFont labelFont;

String[] map;
/** must be set in setup() */
int pixelSize;

static final int size_in_pixel = 600;
final int COLOR_OBSTRUCTION = 0;
final int COLOR_FLOOR = 150;
final int COLOR_VISITED = 200;
final int COLOR_GUARD = #00EE00;

final static char VISITED = 'X';
final static char OBSTRUCTION = '#';
final static char GUARD_UP = '^';
final static char GUARD_RIGHT = '>';
final static char GUARD_DOWN = 'v';
final static char GUARD_LEFT = '<';

final String IS_GUARD = "" + GUARD_UP + GUARD_RIGHT + GUARD_DOWN + GUARD_LEFT;

void draw() {
  background(51); // gray background
  
  int noOfVisited = 0;
  
  /* Draw the border */
  fill(#FC0FC0);
  rect(0, 0, width - pixelSize, pixelSize, pixelSize, 0, 0, 0);
  rect(width - pixelSize, 0, pixelSize, height-pixelSize, 0, pixelSize, 0, 0);
  rect(pixelSize, height-pixelSize, width-pixelSize, pixelSize, 0, 0, pixelSize, 0);
  rect(0, pixelSize, pixelSize, height - pixelSize, 0, 0, 0, pixelSize);
  
  /* Draw the map */
  translate(pixelSize, pixelSize);
  for (int lineIndex = 0; lineIndex < map.length; ++lineIndex) {
   for (int charIndex = 0; charIndex < map[lineIndex].length(); ++charIndex){
     char currChar = map[lineIndex].charAt(charIndex);
     
     if (currChar == OBSTRUCTION) {
       fill(COLOR_OBSTRUCTION);
     }
     else if (IS_GUARD.contains("" + currChar)) {
       fill(COLOR_GUARD);
       drawGuardAt(charIndex, lineIndex, currChar);
       
       continue;
     }
     else if (currChar == VISITED) {
       fill(COLOR_VISITED);
       ++noOfVisited;
     }
     else {
       fill(COLOR_FLOOR);
     }
     
     rect(charIndex * pixelSize, lineIndex * pixelSize, pixelSize, pixelSize);
   }
  }
  
  /* draw label */
  fill(255);
  textAlign(CENTER);
  textFont(labelFont);
  text("Visited: " + noOfVisited, width / 2, height - pixelSize);
}

/**
* Draws the shape of a guard at the specified position and in the specified
* direction.
*
* The color settings must be done outside of this method.
*/
void drawGuardAt(int x, int y, char guardChar){
  switch (guardChar){
   case GUARD_UP:
     triangle(x * pixelSize, y * pixelSize + pixelSize,
         x * pixelSize + pixelSize / 2.0, y * pixelSize,
         (x + 1) * pixelSize, (y + 1) * pixelSize);
     break;
   case GUARD_RIGHT:
     triangle(x * pixelSize, y * pixelSize,
         (x + 1) * pixelSize, (y + 0.5) * pixelSize,
         x * pixelSize, (y + 1) * pixelSize);
     break;
   case GUARD_DOWN:
     triangle(x * pixelSize, y * pixelSize,
     (x+ 1) * pixelSize, y * pixelSize,
     (x + 0.5) * pixelSize, (y + 1) * pixelSize);
     break;
   case GUARD_LEFT:
     triangle((x + 1) * pixelSize, y * pixelSize,
     (x + 1) * pixelSize, (y + 1) * pixelSize,
     x * pixelSize, (y + 0.5) * pixelSize);
     break;
  }
  
  
}

/**
* Perform a single step based on the current map.
*/
boolean doStep(){
  for (int x = 0; x < map[0].length(); ++x){
   for (int y = 0; y < map.length; ++y){
     char currChar = map[y].charAt(x);
     int movementX = 0, movementY = 0;
     if (IS_GUARD.contains("" + currChar)) {
       switch (currChar) {
         case GUARD_UP:
           movementX = 0; movementY = -1;
           break;
         case GUARD_RIGHT:
           movementX = 1; movementY = 0;
           break;
         case GUARD_DOWN:
           movementX = 0; movementY = 1;
           break;
         case GUARD_LEFT:
           movementX = -1; movementY = 0;
           break;
       }
       
       int nextX = x + movementX;
       int nextY = y + movementY;
       
       if (isOutOfBounds(nextX, nextY)) {
         return false;
       }
       
       if (map[nextY].charAt(nextX) == OBSTRUCTION) {
         
       }
     }
   }
  }
  return true;
}

boolean isOutOfBounds(int x, int y) {
  return x >= 0 && x < map[0].length() && y >= 0 && y < map.length;
}

void keyPressed() {
  if (key == ' '){
    doStep();
  }
}
