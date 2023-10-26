package cs3500.pa04.view;

import java.io.IOException;

/**
 * Represents a view in terms of MVC
 */
public interface Viewer {
  /**
   * displays the 2d array/board to the user
   *
   * @param board a 2d array that player's play battle salvo on
   *
   * @throws IOException if an error occurs
   */
  public void displayBoard(char[][] board) throws IOException;


  /**
   * displays a given String to the user
   *
   * @param phrase a given message
   *
   * @throws IOException if an error occurs
   */
  public void display(String phrase) throws IOException;
}
