package cs3500.pa04.model;

import cs3500.pa04.view.ManualDataEntry;
import cs3500.pa04.view.UserViewer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a human player playing battleship
 */
public class UserPlayer extends AbstractPlayer {
  private String name;

  private ManualDataEntry shotsData;
  private UserViewer viewer;

  /**
   * initializes a random object, a UserViewer object, and a ManualDataEntry object
   * to create a human player
   *
   * @param rand      represents a Random object
   *
   * @param viewer    represents a UserViewer object that displays stuff to the user
   *
   * @param shotsData represents a viewer object that allows user to take shots
   *
   * @param myBoard represents the console user player's board
   *
   * @param otherBoard represents the other opponent player's board
   */
  public UserPlayer(Random rand, UserViewer viewer, ManualDataEntry shotsData,
                    Board myBoard, Board otherBoard) {
    super(rand, myBoard, otherBoard);
    this.viewer = viewer;
    this.name = "User";
    this.shotsData = shotsData;
  }

  /**
   * returns the name of the player
   *
   * @return the name of the player
   */
  public String name() {
    return name;
  }

  /**
   * returns a list of coordinates that represents where the user takes shots
   * in battleship
   *
   * @return a list of coordinates that represents where the user takes shots
   *
   */
  public List<Coord> takeShots() {
    viewer.display("Opponent Board Data: ");
    viewer.displayBoard(opponentBoard.getBoard());
    viewer.display("Your Board:");
    viewer.displayBoard(myBoard.getBoard());
    if (shotsRemaining == 0) {
      return new ArrayList<>();
    }
    shotsData.requestShots(shotsRemaining);
    currentShots = shotsData.getUserShots(shotsRemaining, myBoard.getBoard()[0].length,
        myBoard.getBoard().length);
    return currentShots;
  }

  /**
   * updates the user's board with the opponents shots that missed
   * the user's ships
   *
   * @param opponentsMissesOnBoard the opponents shots that missed user's shots
   */
  protected void processMisses(List<Coord> opponentsMissesOnBoard) {
    myBoard.updateBoardMisses(opponentMissesOnBoard);
  }

  /**
   * Reports to this player what shots returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    opponentBoard.updateBoardHits(shotsThatHitOpponentShips);
  }
}
