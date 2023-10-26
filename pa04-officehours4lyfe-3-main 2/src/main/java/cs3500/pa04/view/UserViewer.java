package cs3500.pa04.view;

import java.io.IOException;

/**
 * Represents displaying things to the user in the console
 */
public class UserViewer implements Viewer {
  private Appendable output;

  /**
   * initializes an appendable object
   *
   * @param output an Appendable object that will process output
   */
  public UserViewer(Appendable output) {
    this.output = output;
  }

  /**
   * displays the 2d array/board to the user
   *
   * @param board a 2d array that player's play battle salvo on
   *
   */
  public void displayBoard(char[][] board) {
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        try {
          output.append(board[row][col] + " ");
        } catch (IOException e) {
          System.out.println("Could not display board!");
        }
      }
      display("");
    }
  }

  /**
   * displays a given String to the user
   *
   * @param phrase a given message
   */
  public void display(String phrase) {
    try {
      output.append(phrase + "\n");
    } catch (IOException e) {
      System.out.println("We encountered an unexpected error!");
    }
  }
}
