package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a player of battleship that is the computer
 */
public class ComputerPlayer extends AbstractPlayer {
  private String name;
  private ArrayList<Coord> coordinatesSeen;

  /**
   * initializes a Random object, a width (int), a height (int),
   * and a UserPlayer object to create a computer player
   *
   * @param rand represents a Random object
   *
   * @param myBoard represents the user's Board object
   *
   * @param opponentBoard represents the opponent's Board object
   *
   */
  public ComputerPlayer(Random rand, Board myBoard, Board opponentBoard) {
    super(rand, myBoard, opponentBoard);
    this.name = "sierrawelsch";
    this.coordinatesSeen = new ArrayList<>();
  }

  /**
   * returns the name of the computer player
   *
   * @return the name of the computer player
   */
  public String name() {
    return name;
  }

  /**
   * returns the list of shots that the computer shoots
   * at the user
   *
   * @return the list of shots that the computer takes
   */
  public List<Coord> takeShots() {
    currentShots = new ArrayList<>();
    int shots = shotsRemaining;
    int xcoord;
    int ycoord;
    Coord newCoord;
    while (shots > 0) {
      xcoord = rand.nextInt(width);
      ycoord = rand.nextInt(height);
      newCoord = new Coord(xcoord, ycoord);
      if (!contains(coordinatesSeen, newCoord)) {
        currentShots.add(newCoord);
        coordinatesSeen.add(newCoord);
        shots--;
      }
      if (coordinatesSeen.size() == width * height) {
        break;
      }
    }
    return currentShots;
  }

  /**
   * updates the user board to reflect the opponents misses
   * on the user's board
   *
   * @param opponentMissesOnBoard the opponents shots that missed on the user's board
   */
  @Override
  protected void processMisses(List<Coord> opponentMissesOnBoard) {
    myBoard.updateBoardMisses(opponentMissesOnBoard);
  }


  /**
   * reports to this player what shots returned from takeShots()
   * successfully hit the user's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the user's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    opponentBoard.updateBoardHits(shotsThatHitOpponentShips);
    //algorithm.populateHitQueue(shotsThatHitOpponentShips);
    // TODO implement this once you make method
  }

}
