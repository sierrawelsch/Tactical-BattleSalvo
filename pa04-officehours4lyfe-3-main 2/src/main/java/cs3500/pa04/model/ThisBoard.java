package cs3500.pa04.model;

import java.util.List;

/**
 * Represents a board that has ships on it and is
 * completely shown to the player
 */
public class ThisBoard extends Board {
  private List<Ship> fleet;

  /**
   * initializes a list of ships that will be
   * displayed on the board
   *
   * @param fleet a list of ships that are each on the board
   */
  public ThisBoard(List<Ship> fleet) {
    super();
    this.fleet = fleet;
  }

  /**
   * returns the board at the start of the game that has not been hit at (.)
   * along with its ships
   *
   * @param height the height of the board
   *
   * @param width the width of the board
   *
   * @return an empty board in terms of battleship
   */
  public char[][] createBoard(int height, int width) {
    board = new char[height][width];
    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        board[h][w] = '.';
      }
    }
    for (Ship s : fleet) {
      for (Coord coordinate : s.getCoords()) {
        if (s.getType().equals(ShipType.Battleship)) {
          board[coordinate.getYsCoord()][coordinate.getXsCoord()] = 'B';
        } else if (s.getType().equals(ShipType.Carrier)) {
          board[coordinate.getYsCoord()][coordinate.getXsCoord()] = 'C';
        } else if (s.getType().equals(ShipType.Destroyer)) {
          board[coordinate.getYsCoord()][coordinate.getXsCoord()] = 'D';
        } else if (s.getType().equals(ShipType.Submarine)) {
          board[coordinate.getYsCoord()][coordinate.getXsCoord()] = 'S';
        }
      }
    }
    return board;
  }

  /**
   * returns the battleship board (a getter)
   *
   * @return a 2d array that reprsents the board
   */
  public char[][] getBoard() {
    return board;
  }

  /**
   * sets the list of ships on this board to the passed in list of ships
   *
   * @param newFleet a list of ships created after the setup method is called
   */
  public void setFleet(List<Ship> newFleet) {
    this.fleet = newFleet;
  }
}
