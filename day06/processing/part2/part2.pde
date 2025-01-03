import java.util.Optional;

void setup() {
  size(600, 600);
  frameRate(1000);
  surface.setTitle("AoC 2024 - Day 06, Part 2");
  
  labelFont = createFont("Arial", 14, true);
  
  map = loadStrings("day06_part_2_test1.input");
  pixelSize = size_in_pixel / (2 /* border */ + max(map.length, map[0].length()));
}

public enum Movement {

  UP(-1, 0, GUARD_UP),
  RIGHT(0,1, GUARD_RIGHT),
  DOWN(1,0, GUARD_DOWN),
  LEFT(0, -1, GUARD_LEFT);

  public final int vertical;
  public final int horizontal;
  public final char guardChar;

  Movement(int vertical, int horizontal, char guardChar) {
    this.vertical = vertical;
    this.horizontal = horizontal;
    this.guardChar = guardChar;
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

/*  if true, the UI is updated after every step (Done with SPACE bar).
    if false, the simulation is run automatically and the UI is not updated. */
boolean isUIUpdateActive = true;

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
  
  if (isUIUpdateActive) {
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
  }
  else {
   doStep();
  }
  
  /* draw label */
  fill(255);
  textAlign(CENTER);
  textFont(labelFont);
  text(isUIUpdateActive
    ? "Visited: " + noOfVisited
    : "Simulating... Press Ö to stop."
    , width / 2, height - pixelSize);
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
     Optional<Movement> currMovement = Movement.tryParseMovement(currChar);
       
     if (currMovement.isEmpty()) {
      continue; 
     }
       
     if (isOutOfBounds(x + currMovement.get().horizontal, y + currMovement.get().vertical)) {
       isUIUpdateActive = true; // enable drawing UI.
       return false;
     }
     
     if (map[y + currMovement.get().vertical].charAt(x + currMovement.get().horizontal) == OBSTRUCTION) {
       System.out.println("Encountering wall...");
       currMovement = Optional.of(currMovement.get().turnRight());
     }
      // do step
     map[y] = map[y].substring(0, x) + VISITED + map[y].substring(x + 1);
     int nextX = x + currMovement.get().horizontal;
     int nextY = y + currMovement.get().vertical;
     map[nextY] = map[nextY].substring(0, nextX) + currMovement.get().guardChar + map[nextY].substring(nextX + 1);
     
     return true;
   }
  }
  return false; // no guard was found
}

boolean isOutOfBounds(int x, int y) {
  return !(x >= 0 && x < map[0].length() && y >= 0 && y < map.length);
}

void keyPressed() {
  if (key == ' '){
    if (isUIUpdateActive) {
      System.out.println("Did step: " + doStep());
    }
  }
  
  if (key == 'ö') {
    isUIUpdateActive = !isUIUpdateActive;
    if (!isUIUpdateActive) {
      System.out.println("Simulating without UI!");
    }
    else {
      System.out.println("Press or hold SPACE to do step(s).");
    } 
  }
}
