//package cs3500.pa04.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Class that represents the shots the ComputerPlayer object will
// * take in a game of BattleSalvo based on an algorithm
// * this is for the tournament, it is a work in progress
// * so it is not associated with PA04 submission!
// */
//public class ComputerAlgorithm {
//  //things we want to keep track of:
//  // - list of every other shot (checkerboard algorithm)
//  // - list of shots we already took
//
//  int numShots;
//  List<Coord> alreadyShotList;
//  List<Coord> coordinatesAvailable;
//  Coord[][] board;
//  List<Coord> coordHitPerimeter;
//  int height;
//  int width;
//  boolean checkerBoardState;
//  List<Coord> hitQueue;
//  List<Coord> remainingPerimeterShots;
//  Coord currentCoord;
//  List<Boolean> directions;
//  List<Coord> currentShots;
//  boolean even;
//  int hitCoordIndex;
//  boolean processed;
//
//  //constructor
//  public ComputerAlgorithm(int numShots, int height, int width) {
//    this.numShots = numShots;
//    this.alreadyShotList = new ArrayList<>();
//    this.coordinatesAvailable = new ArrayList<>();
//    this.checkerBoardState = true;
//    this.currentCoord = new Coord(0, 0);
//    this.remainingPerimeterShots = new ArrayList<>();
//    generateCoordinates();
//    this.even = true;
//    this.hitCoordIndex = -1;
//    this.processed = true;
//  }
//
//  //only thing to return, only public method
//  //dispatching to different states
//  public List<Coord> shotsToTake(){
//    currentShots = new ArrayList<>();
//    if(!hitQueue.isEmpty()) {
//      checkerBoardState = false;
//    }
//    if(checkerBoardState) {
//      this.checkerBoardAlgo(currentShots);
//    } else {
//      if (!remainingPerimeterShots.isEmpty()) {
//        for(int i = 0; i < numShots; i++) {
//          currentShots.add(remainingPerimeterShots.get(i));
//          remainingPerimeterShots.remove(i);
//        }
//        processed = false;
//      }
//      if (currentShots.size() < numShots) {
//        if(processed) {
//          hitCoordIndex++;
//          perimeterAlgorithm(currentShots, hitQueue.get(hitCoordIndex));
//        } else {
//          shootStraight(hitQueue.get(hitCoordIndex), hitQueue);
//        }
//      }
//
//      //TODO figure out how to differentiate between perimeter and shooting straight
//      //and how to get back into perimeter after all of the stuff above eeeeeeeeeeee
//    }
//    alreadyShotList.addAll(currentShots);
//    return currentShots;
//  }
//
//  //only called when checkerBoardState is true
//  private void checkerBoardAlgo(List<Coord> currentShots) {
//    int row = currentCoord.getYsCoord();
//    int col = currentCoord.getXsCoord();
//
//    while (currentShots.size() < numShots) {
//      if(isValidCoordinate(currentCoord)) {
//        currentShots.add(currentCoord);
//      }
//      if (this.even) {
//        if (col == width - 1) {
//          currentCoord = new Coord(1, row++);
//          this.even = false;
//        } else {
//          col += 2;
//          currentCoord = new Coord(col, row);
//        }
//      } else {
//        if (col == width - 2) {
//          currentCoord = new Coord(0, row++);
//          this.even = true;
//        } else {
//          col += 2;
//          currentCoord = new Coord(col, row);
//        }
//      }
//    }
//  }
//
//  private void perimeterAlgorithm(List<Coord> currentShots, Coord coordHit) {
//    //while perimiterShots.size() > 0
//    //shot at perimeter, add to takeShots, add to already seen, remove coord from perimeterShots
//    coordHitPerimeter = getPerimeterCoords(coordHit);
//    if (numShots < 4) {
//      //when size is less than 4
//      for (int i = 0; i < numShots; i++) {
//        Coord shot = coordHitPerimeter.get(i);
//        if(isValidCoordinate(shot)) {
//          currentShots.add(shot);
//        }
//        coordHitPerimeter.remove(shot);
//      }
//
//      for(Coord c: coordHitPerimeter) {
//        if(isValidCoordinate(c)) {
//          remainingPerimeterShots.add(c);
//        }
//      }
//    }
//    else { //for when numShots >= 4
//      currentShots.addAll(coordHitPerimeter);
//      if(currentShots.size() < numShots) {
//        backAndForth(currentShots);
//      }
//    }
//  }
//
//  private List<Coord> getPerimeterCoords(Coord coordHit) {
//    List<Coord> perimeterShots = new ArrayList<>();
//    Coord top = new Coord(coordHit.getXsCoord(), coordHit.getYsCoord() - 1);
//    Coord bottom = new Coord(coordHit.getXsCoord(), coordHit.getYsCoord() + 1);
//    Coord left = new Coord(coordHit.getXsCoord() - 1, coordHit.getYsCoord());
//    Coord right = new Coord(coordHit.getXsCoord() + 1, coordHit.getYsCoord());
//    List<Coord> tempList = new ArrayList<>();
//
//    tempList.add(top);
//    tempList.add(right);
//    tempList.add(bottom);
//    tempList.add(left);
//
//    for(Coord currCoord: tempList) {
//      if(isValidCoordinate(currCoord)) {
//        perimeterShots.add(currCoord);
//      }
//    }
//    return perimeterShots;
//  }
//
//  private void backAndForth(List<Coord> currentShots) {
//
//  }
//
//  private void shootStraight(Coord hitCoord, List<Coord> hitQueue) {
//    //set processed to true
//    Coord rightCoord = new Coord(hitCoord.getXsCoord() + 1, hitCoord.getYsCoord());
//    Coord bottomCoord = new Coord(hitCoord.getXsCoord(), hitCoord.getYsCoord() + 1);
//    int index;
//    Coord current;
//    if(contains(hitQueue, rightCoord) && contains(hitQueue, bottomCoord)) {
//
//    } else if (contains(hitQueue, rightCoord)) {
//      //shoot right
//      index = rightCoord.getXsCoord();
//      while (currentShots.size() < numShots) {
//        if(index < width) {
//          current = new Coord(rightCoord.getXsCoord() + 1, rightCoord.getYsCoord());
//          if(isValidCoordinate(current)) {
//            currentShots.add(current);
//          }
//          index++;
//        } else {
//          currentCoord = new Coord(0, rightCoord.getYsCoord() + 1);
//          if(rightCoord.getYsCoord() + 1 % 2 == 0) {
//            this.even = true;
//          } else {
//            this.even = false;
//          }
//          checkerBoardAlgo(currentShots);
//        }
//      }
//
//    } else if (contains(hitQueue, bottomCoord)) {
//      //shoot vertical down
//      index = bottomCoord.getYsCoord();
//      while (currentShots.size() < numShots) {
//        if(index < height) {
//          current = new Coord(bottomCoord.getXsCoord(), bottomCoord.getYsCoord() + 1);
//          if(isValidCoordinate(current)) {
//            currentShots.add(current);
//          }
//          index++;
//        } else {
//          currentCoord = new Coord(0, hitCoord.getYsCoord() + 1);
//          if(hitCoord.getYsCoord() + 1 % 2 == 0) {
//            this.even = true;
//          } else {
//            this.even = false;
//          }
//          checkerBoardAlgo(currentShots);
//        }
//      }
//    }
//  }
//
//
//  private void generateCoordinates() {
//    for (int y = 0; y < height; y++) {
//      for (int x = 0; x < width; x++) {
//        coordinatesAvailable.add(new Coord(x, y));
//      }
//    }
//  }
//
//  public void populateHitQueue(List<Coord> successfulHits) {
//    hitQueue.addAll(successfulHits);
//  }
//
//  //check if shots is valid
//  private boolean isValidCoordinate(Coord coord) {
//    return coord.getXsCoord() < width && coord.getYsCoord() < height
//        && coord.getXsCoord() > 0 && coord.getYsCoord() > 0 && !contains(alreadyShotList, coord);
//  }
//
//  private boolean contains(List<Coord> coordinates, Coord newCoord) {
//    for (int i = 0; i < coordinates.size(); i++) {
//      if ((coordinates.get(i).getXsCoord() == newCoord.getXsCoord())
//          && (coordinates.get(i).getYsCoord() == newCoord.getYsCoord())) {
//        return true;
//      }
//    }
//    return false;
//  }
//}
