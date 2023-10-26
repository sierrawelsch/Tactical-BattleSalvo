package cs3500.pa04.model;

import java.util.List;

/**
 * Represents a board in battleship the user's play on
 */
public abstract class Board {
  protected char[][] board;

  /**
   * initializes a board the players play on
   */
  Board() {
    board = new char[][] {};
  }

  /**
   * creates the board the player starts with at the
   * beginning of the game
   *
   * @param height the height of the board
   *
   * @param width the width of the board
   *
   * @return a 2d array which represents the board the player starts with
   */
  public abstract char[][] createBoard(int height, int width);


  /**
   * updates the opponent's board to reflect the shots of a player that hit a ship (+)
   *
   *  @param damages list of shots that hit opponent ships
   */
  public void updateBoardHits(List<Coord> damages) {
    for (Coord c : damages) {
      board[c.getYsCoord()][c.getXsCoord()] = '+';
    }
  }

  /**
   * updates the opponents board to reflect the shots of a player that missed a ship (-)
   *
   * @param misses list of shots that missed opponent ships
   */
  public void updateBoardMisses(List<Coord> misses) {
    for (Coord c : misses) {
      board[c.getYsCoord()][c.getXsCoord()] = '-';
    }
  }

  public abstract char[][] getBoard();

  public abstract void setFleet(List<Ship> newFleets);
}
