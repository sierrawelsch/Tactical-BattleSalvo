package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for the OtherBoard class
 */
class OtherBoardTest {
  OtherBoard other;
  char[][] expectedBoard;

  List<Coord> damages;
  List<Coord> misses;

  /**
   * initializes the fields in order to initialize a OtherBoard object
   */
  @BeforeEach
  void setup() {
    damages = new ArrayList<>();
    misses = new ArrayList<>();
    other = new OtherBoard();
    expectedBoard = new char[10][8];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 8; col++) {
        expectedBoard[row][col] = '.';
      }
    }
    damages.add(new Coord(0, 1));
    damages.add(new Coord(1, 1));
    damages.add(new Coord(5, 4));
    misses.add(new Coord(3, 2));
    misses.add(new Coord(2, 2));
    misses.add(new Coord(7, 4));
  }

  /**
   * tests that the board at the start of the game does not contain any ships, hits or misses
   */
  @Test
  void createBoard() {
    char[][] actualBoard = other.createBoard(10, 8);
    for (int row = 0; row < actualBoard.length; row++) {
      for (int col = 0; col < actualBoard[0].length; col++) {
        assertEquals(actualBoard[row][col], expectedBoard[row][col]);
      }
    }
  }

  /**
   * tests that the board returned matches the board of an OtherBoard object
   */
  @Test
  void getOtherBoard() {
    other.createBoard(10, 8);
    for (int row = 0; row < other.getBoard().length; row++) {
      for (int col = 0; col < other.getBoard()[0].length; col++) {
        assertEquals(other.getBoard()[row][col], expectedBoard[row][col]);
      }
    }
  }

  /**
   * tests that given a list of damages (hits) to the board those coordinates now
   * contain a + instead of a .
   */
  @Test
  void updateBoardHits() {
    expectedBoard[1][0] = '+';
    expectedBoard[1][1] = '+';
    expectedBoard[4][5] = '+';

    char[][] actualBoard = other.createBoard(10, 8);
    other.updateBoardHits(damages);
    for (int row = 0; row < actualBoard.length; row++) {
      for (int col = 0; col < actualBoard[0].length; col++) {
        assertEquals(actualBoard[row][col], expectedBoard[row][col]);
      }
    }
  }

  /**
   * tests that given a list of misses to the board the coordiantes now
   * contain a - instead of a .
   */
  @Test
  void updateBoardMisses() {
    expectedBoard[2][3] = '-';
    expectedBoard[2][2] = '-';
    expectedBoard[4][7] = '-';

    char[][] actualBoard = other.createBoard(10, 8);
    other.updateBoardMisses(misses);
    for (int row = 0; row < actualBoard.length; row++) {
      for (int col = 0; col < actualBoard[0].length; col++) {
        assertEquals(actualBoard[row][col], expectedBoard[row][col]);
      }
    }
  }
}