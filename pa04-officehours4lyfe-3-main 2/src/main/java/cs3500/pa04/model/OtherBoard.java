package cs3500.pa04.model;

import java.util.List;

/**
 * Represents the opponents board that only shows hits or misses
 */
public class OtherBoard extends Board {

  /**
   * initializes an OtherBoard object (opponents board)
   */
  public OtherBoard() {
    super();
  }

  /**
   * returns the initial board that is first shown to the user with all (.)
   *
   * @param height the height of the board
   *
   * @param width the width of the board
   *
   * @return a 2d array that represents the board untouched, initially
   */
  public char[][] createBoard(int height, int width) {
    board = new char[height][width];
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        board[h][w] = '.';
      }
    }
    return board;
  }

  /**
   * gets the opponents board
   *
   * @return the opponents board
   */
  public char[][] getBoard() {
    return board;
  }

  /**
   * sets the list of ships of this board to the passed in list of ships
   * (only happens in ThisBoard class, this method is in this class
   * for dynamic dispatching purposes)
   *
   * @param newFleet a list of ships created after the setup method is called
   */
  public void setFleet(List<Ship> newFleet) {
  }
}
