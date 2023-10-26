package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for my UserViewer class
 */
class UserViewerTest {
  Appendable output;
  UserViewer viewer;
  char[][] startingOpponentBoard;

  /**
   * initializes all the fields in order to initialize a UserViewer object
   */
  @BeforeEach
  void setup() {
    output = new StringBuilder();
    viewer = new UserViewer(output);
    startingOpponentBoard = new char[6][6];
  }

  /**
   * tests that given a 2d array a board is converted to a
   * string properly and displayed as a BattleSalvo board
   */
  @Test
  void displayBoard() {
    for (int row = 0; row < 6; row++) {
      for (int col = 0; col < 6; col++) {
        startingOpponentBoard[row][col] = '.';
      }
    }
    viewer.displayBoard(startingOpponentBoard);
    assertEquals(output.toString(), ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n");
  }

  /**
   * tests that given a String it displays that
   * message to the user via the console in terms of my game
   */
  @Test
  void display() {
    viewer.display("Welcome to BattleSalvo!");
    assertEquals(output.toString(), "Welcome to BattleSalvo!\n");
  }
}