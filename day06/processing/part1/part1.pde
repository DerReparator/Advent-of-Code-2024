void setup() {
  size(600, 600);
  surface.setTitle("AoC 2024 - Day 06, Part 1");
  
  labelFont = createFont("Arial", 14, true);
  
  map = loadStrings("day06_part_1_test1.input");
}

PFont labelFont;

String[] map;
static final int size_in_pixel = 600;
final int COLOR_OBSTRUCTION = 0;
final int COLOR_FLOOR = 150;
final int COLOR_VISITED = 200;
final int COLOR_GUARD = #00EE00;

final char VISITED = 'X';
final char OBSTRUCTION = '#';

void draw() {
  background(51); // gray background
  
  int noOfVisited = 0;
  int pixelSize = size_in_pixel / (2 /* border */ + max(map.length, map[0].length()));
  
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
     if (map[lineIndex].charAt(charIndex) == OBSTRUCTION) {
       fill(COLOR_OBSTRUCTION);
     }
     else if (map[lineIndex].charAt(charIndex) == '^') {
       fill(COLOR_GUARD);
       triangle(charIndex * pixelSize, lineIndex * pixelSize + pixelSize,
       charIndex * pixelSize + pixelSize / 2.0, lineIndex * pixelSize,
       (charIndex + 1) * pixelSize, (lineIndex + 1) * pixelSize);
       continue;
     }
     else if (map[lineIndex].charAt(charIndex) == VISITED) {
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
* Perform a single step based on the current map.
*/
void doStep(){
  
}

void keyPressed() {
  if (key == ' '){
    doStep();
  }
}
