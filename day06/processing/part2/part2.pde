import java.util.Optional;

void setup() {
  size(600, 600);
  frameRate(1000);
  surface.setTitle("AoC 2024 - Day 06, Part 2");
  
  labelFont = createFont("Arial", 14, true);
  
  map = loadStrings("day06_part_2_test1.input");
  originalSimulatedMap = new String[map.length];
  mapBeforeSimulation = new String[map.length];
  
  simulatePart1();
  map = loadStrings("day06_part_2_test1.input"); // reset map
  
  pixelSize = size_in_pixel / (2 /* border */ + max(map.length, map[0].length()));
}

void simulatePart1(){
  System.out.println("Simulating Part1...");
 while (doStep());
 System.out.println("Simulated Part 1. Result is stored in 'originalSimulatedMap'.");
 
 transferMapInPlace(map, originalSimulatedMap);
}

class Point {
  final int X;
  final int Y;
  
  Point(int x, int y) {
    this.X = x;
    this.Y = y;
  }
  
  public String toString() {
    return "[" + X + ";" + Y + "]";
  }
}

public enum Movement {

  UP(-1, 0, GUARD_UP, VISITED_UP),
  RIGHT(0,1, GUARD_RIGHT, VISITED_RIGHT),
  DOWN(1,0, GUARD_DOWN, VISITED_DOWN),
  LEFT(0, -1, GUARD_LEFT, VISITED_LEFT);

  public final int vertical;
  public final int horizontal;
  public final char guardChar;
  public final char visitedChar;

  Movement(int vertical, int horizontal, char guardChar, char visitedChar) {
    this.vertical = vertical;
    this.horizontal = horizontal;
    this.guardChar = guardChar;
    this.visitedChar = visitedChar;
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
String[] originalSimulatedMap;
String[] mapBeforeSimulation;
/** This flag is true while an alternate world with a new obstruction is tested out. */
boolean isSimulated = false;

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

final static char VISITED_UP = 'A';
final static char VISITED_RIGHT = ']';
final static char VISITED_DOWN = 'V';
final static char VISITED_LEFT = '[';
final String IS_VISITED = "" + VISITED_UP + VISITED_RIGHT + VISITED_DOWN + VISITED_LEFT;

final static char OBSTRUCTION = '#';
final static char GUARD_UP = '^';
final static char GUARD_RIGHT = '>';
final static char GUARD_DOWN = 'v';
final static char GUARD_LEFT = '<';

final String IS_GUARD = "" + GUARD_UP + GUARD_RIGHT + GUARD_DOWN + GUARD_LEFT;

int noOfValidSimulations = 0;

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
       else if (IS_VISITED.contains("" + currChar)) {
         fill(COLOR_VISITED);
         drawGuardAt(charIndex, lineIndex, currChar);
         ++noOfVisited;
         continue;
       }
       else {
         fill(COLOR_FLOOR);
       }
       
       rect(charIndex * pixelSize, lineIndex * pixelSize, pixelSize, pixelSize);
     }
    }
  }
  else {
   simulateOrDoStep();
  }
  
  /* draw label */
  fill(255);
  textAlign(CENTER);
  textFont(labelFont);
  text(isUIUpdateActive
    ? "Valid simulations: " + noOfValidSimulations
    : "Simulating... Press Ö to stop. Valid simulations: " + noOfValidSimulations
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
   case VISITED_UP:
     triangle(x * pixelSize, y * pixelSize + pixelSize,
         x * pixelSize + pixelSize / 2.0, y * pixelSize,
         (x + 1) * pixelSize, (y + 1) * pixelSize);
     break;
   case GUARD_RIGHT:
   case VISITED_RIGHT:
     triangle(x * pixelSize, y * pixelSize,
         (x + 1) * pixelSize, (y + 0.5) * pixelSize,
         x * pixelSize, (y + 1) * pixelSize);
     break;
   case GUARD_DOWN:
   case VISITED_DOWN:
     triangle(x * pixelSize, y * pixelSize,
     (x+ 1) * pixelSize, y * pixelSize,
     (x + 0.5) * pixelSize, (y + 1) * pixelSize);
     break;
   case GUARD_LEFT:
   case VISITED_LEFT:
     triangle((x + 1) * pixelSize, y * pixelSize,
     (x + 1) * pixelSize, (y + 1) * pixelSize,
     x * pixelSize, (y + 0.5) * pixelSize);
     break;
  }
}

void simulateOrDoStep() {
  if (!isSimulated) {
    // if necessary, enter a simulation
    Optional<Point> newObstruction = isSimulationStart(map);
    if (!newObstruction.isEmpty()) {
      System.out.println("Entering simulation at " + newObstruction.get());
      isSimulated = true;
      transferMapInPlace(map, mapBeforeSimulation);
      map[newObstruction.get().Y] = map[newObstruction.get().Y].substring(0, newObstruction.get().X) + OBSTRUCTION + map[newObstruction.get().Y].substring(newObstruction.get().X + 1);
    }
    else
    {
      doStep();
    }
  }
  else {
    /* within a simulation:
    1. do a step
    2. check if the guard is exactly matching the original path
    3. if he does: rewind the simulation
    4. if he doesn't: check if the simulation must end because of another exit condition
    */
      doStep();
    
    Point guardPos = findGuardInMap(map);
    if (isCurrentGuardPositionExactlyInOriginalMap(guardPos, originalSimulatedMap)) {
      addToValidSimulation(guardPos);
      transferMapInPlace(mapBeforeSimulation, map);
      doStep();
      System.out.println("Simulation ends successful.");
      isSimulated = false;
    }
    else {
      Movement guardMovement = Movement.tryParseMovement(map[guardPos.Y].charAt(guardPos.X)).get();
      if (isOutOfBounds(guardPos.X + guardMovement.horizontal, guardPos.Y + guardMovement.vertical)) {
         transferMapInPlace(mapBeforeSimulation, map);
        doStep();
        System.out.println("Simulation ends UNsuccessful.");
        isSimulated = false;
      }
    }
  }
}

boolean isCurrentGuardPositionExactlyInOriginalMap(Point currentGuard, String[] originalSimulatedMap) {
  char symbolInOriginalSimulation = originalSimulatedMap[currentGuard.Y].charAt(currentGuard.X);
  if (IS_VISITED.contains("" + symbolInOriginalSimulation)
  && Movement.tryParseMovement(map[currentGuard.Y].charAt(currentGuard.X)).get().visitedChar == symbolInOriginalSimulation) {
    return true; //<>//
  }
  
  return false;
}

Point findGuardInMap(String[] map) throws IllegalStateException {
  for (int x = 0; x < map[0].length(); ++x) {
   for (int y = 0; y < map.length; ++y) {
     char currChar = map[y].charAt(x);
     Optional<Movement> currMovement = Movement.tryParseMovement(currChar);
       
     if (!currMovement.isEmpty()) {
      return new Point(x, y);
     }
   }
  }
  
  throw new IllegalStateException("Given map did not contain guard");
}

void addToValidSimulation(Point currPosition) {
  System.out.println("Encountered original path at " + currPosition);
  noOfValidSimulations++;
}

/**
* Returns a Point iff the guard position (and its direction) in the given map is a valid
start point for a simulation with a new obstruction.

@returns The point of the new obstruction.
*/
Optional<Point> isSimulationStart(String[] map) {
  for (int x = 0; x < map[0].length(); ++x) {
   for (int y = 0; y < map.length; ++y) {
     char currChar = map[y].charAt(x);
     Optional<Movement> currMovement = Movement.tryParseMovement(currChar);
       
     if (currMovement.isEmpty()) {
      continue; 
     }
     
     int nextX = x + currMovement.get().horizontal;
     int nextY = y + currMovement.get().vertical;
     
     if (isOutOfBounds(nextX, nextY) || map[y].charAt(x) == OBSTRUCTION) {
       return Optional.empty();
     }
     
     return Optional.of(new Point(nextX, nextY));
   }
  }
  
  return Optional.empty(); // no guard found
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
     
      map[y] = map[y].substring(0, x) + currMovement.get().visitedChar + map[y].substring(x + 1);     
     
     if (map[y + currMovement.get().vertical].charAt(x + currMovement.get().horizontal) == OBSTRUCTION) {
       System.out.println("Encountering wall...");
       currMovement = Optional.of(currMovement.get().turnRight());
     }
      // do step
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

void transferMapInPlace(String[] from, String[] to) throws IllegalArgumentException {
 if (from.length != to.length) throw new IllegalArgumentException("Transfer maps must be of same length"); 
 
 for (int y = 0; y < from.length; ++y) {
  to[y] = "" + from[y]; 
 }
}

void keyPressed() {
  if (key == ' '){
    if (isUIUpdateActive) {
      simulateOrDoStep();
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
