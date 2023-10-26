package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for my ThisBoard class
 */
class ThisBoardTest {

  ThisBoard myBoard;
  List<Ship> fleets;
  List<Coord> damages;
  List<Coord> misses;
  Ship submarine;
  Ship battleship;
  Ship destroyer;
  Ship carrier;
  List<Coord> subCoords;
  List<Coord> battleCoords;
  List<Coord> destroyCoords;
  List<Coord> carrierCoords;
  char[][] expectedBoard;

  /**
   * initializes all the fields in order to initialize a ThisBoard object
   */
  @BeforeEach
  void setup() {
    fleets = new ArrayList<>();
    damages = new ArrayList<>();
    misses = new ArrayList<>();
    subCoords = new ArrayList<>(Arrays.asList(new Coord(0, 1), new Coord(0, 2), new Coord(0, 3)));
    submarine = new Ship(ShipType.Submarine, subCoords);
    fleets.add(submarine);
    battleCoords = new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(3, 0),
            new Coord(4, 0)));
    battleship = new Ship(ShipType.Battleship, battleCoords);
    fleets.add(battleship);
    destroyCoords = new ArrayList<>(
        Arrays.asList(new Coord(4, 0), new Coord(4, 1), new Coord(4, 2), new Coord(4, 3)));
    destroyer = new Ship(ShipType.Destroyer, destroyCoords);
    fleets.add(destroyer);
    carrierCoords = new ArrayList<>(
        Arrays.asList(new Coord(3, 1), new Coord(3, 2), new Coord(3, 3), new Coord(3, 4),
            new Coord(3, 5), new Coord(3, 6)));
    carrier = new Ship(ShipType.Carrier, carrierCoords);
    fleets.add(carrier);
    myBoard = new ThisBoard(fleets);
    damages.add(new Coord(0, 1));
    damages.add(new Coord(4, 0));
    damages.add(new Coord(3, 1));
    damages.add(new Coord(0, 0));
    misses.add(new Coord(1, 0));
    misses.add(new Coord(4, 1));
    misses.add(new Coord(0, 2));
  }


  /**
   * tests the createBoard method that given a width and a height
   * makes sure all the ships represented by a character
   * are displayed in the correct coordinates and there are no hits or misses
   * at the beginning of the BattleSalvo game
   */
  @Test
  void createBoard() {
    expectedBoard = new char[7][7];
    expectedBoard[1][0] = 'S';
    expectedBoard[2][0] = 'S';
    expectedBoard[3][0] = 'S';

    expectedBoard[0][0] = 'B';
    expectedBoard[0][1] = 'B';
    expectedBoard[0][2] = 'B';
    expectedBoard[0][3] = 'B';
    expectedBoard[0][4] = 'B';

    expectedBoard[0][4] = 'D';
    expectedBoard[1][4] = 'D';
    expectedBoard[2][4] = 'D';
    expectedBoard[3][4] = 'D';

    expectedBoard[1][3] = 'C';
    expectedBoard[2][3] = 'C';
    expectedBoard[3][3] = 'C';
    expectedBoard[4][3] = 'C';
    expectedBoard[5][3] = 'C';
    expectedBoard[6][3] = 'C';

    for (int row = 0; row < 7; row++) {
      for (int col = 0; col < 7; col++) {
        if (expectedBoard[row][col] == '\u0000') {
          expectedBoard[row][col] = '.';
        }
      }
    }
    char[][] actualBoard = myBoard.createBoard(7, 7);
    for (int row = 0; row < actualBoard.length; row++) {
      for (int col = 0; col < actualBoard[0].length; col++) {
        assertEquals(actualBoard[row][col], expectedBoard[row][col]);
      }
    }
  }

  /**
   * tests that the board inherent to this class is properly returned in a 2d array
   */
  @Test
  void getThisBoard() {
    expectedBoard = new char[8][7];
    expectedBoard[1][0] = 'S';
    expectedBoard[2][0] = 'S';
    expectedBoard[3][0] = 'S';

    expectedBoard[0][0] = 'B';
    expectedBoard[0][1] = 'B';
    expectedBoard[0][2] = 'B';
    expectedBoard[0][3] = 'B';
    expectedBoard[0][4] = 'B';

    expectedBoard[0][4] = 'D';
    expectedBoard[1][4] = 'D';
    expectedBoard[2][4] = 'D';
    expectedBoard[3][4] = 'D';

    expectedBoard[1][3] = 'C';
    expectedBoard[2][3] = 'C';
    expectedBoard[3][3] = 'C';
    expectedBoard[4][3] = 'C';
    expectedBoard[5][3] = 'C';
    expectedBoard[6][3] = 'C';

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 7; col++) {
        if (expectedBoard[row][col] == '\u0000') {
          expectedBoard[row][col] = '.';
        }
      }
    }
    myBoard.createBoard(8, 7);
    for (int row = 0; row < myBoard.getBoard().length; row++) {
      for (int col = 0; col < myBoard.getBoard()[0].length; col++) {
        assertEquals(myBoard.getBoard()[row][col], expectedBoard[row][col]);
      }
    }
  }

  /**
   * tests that given a list of hits, hits are properly represents
   * with a + in the correct coordinates along with the remaining ships
   */
  @Test
  void updateBoardHits() {
    expectedBoard = new char[8][7];
    expectedBoard[1][0] = '+';
    expectedBoard[2][0] = 'S';
    expectedBoard[3][0] = 'S';

    expectedBoard[0][0] = '+';
    expectedBoard[0][1] = 'B';
    expectedBoard[0][2] = 'B';
    expectedBoard[0][3] = 'B';
    expectedBoard[0][4] = 'B';

    expectedBoard[0][4] = '+';
    expectedBoard[1][4] = 'D';
    expectedBoard[2][4] = 'D';
    expectedBoard[3][4] = 'D';

    expectedBoard[1][3] = '+';
    expectedBoard[2][3] = 'C';
    expectedBoard[3][3] = 'C';
    expectedBoard[4][3] = 'C';
    expectedBoard[5][3] = 'C';
    expectedBoard[6][3] = 'C';

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 7; col++) {
        if (expectedBoard[row][col] == '\u0000') {
          expectedBoard[row][col] = '.';
        }
      }
    }
    char[][] actualBoard = myBoard.createBoard(8, 7);
    myBoard.updateBoardHits(damages);
    for (int row = 0; row < actualBoard.length; row++) {
      for (int col = 0; col < actualBoard[0].length; col++) {
        assertEquals(actualBoard[row][col], expectedBoard[row][col]);
      }
    }
  }

  /**
   * tests that given a list of misses, misses are properly represents with
   * a - in the correct coordinates along with the remaining ships
   */
  @Test
  void updateBoardMisses() {
    expectedBoard = new char[8][7];
    expectedBoard[1][0] = 'S';
    expectedBoard[2][0] = '-';
    expectedBoard[3][0] = 'S';

    expectedBoard[0][0] = 'B';
    expectedBoard[0][1] = '-';
    expectedBoard[0][2] = 'B';
    expectedBoard[0][3] = 'B';
    expectedBoard[0][4] = 'B';

    expectedBoard[0][4] = 'D';
    expectedBoard[1][4] = '-';
    expectedBoard[2][4] = 'D';
    expectedBoard[3][4] = 'D';

    expectedBoard[1][3] = 'C';
    expectedBoard[2][3] = 'C';
    expectedBoard[3][3] = 'C';
    expectedBoard[4][3] = 'C';
    expectedBoard[5][3] = 'C';
    expectedBoard[6][3] = 'C';

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 7; col++) {
        if (expectedBoard[row][col] == '\u0000') {
          expectedBoard[row][col] = '.';
        }
      }
    }
    char[][] actualBoard = myBoard.createBoard(8, 7);
    myBoard.updateBoardMisses(misses);
    for (int row = 0; row < actualBoard.length; row++) {
      for (int col = 0; col < actualBoard[0].length; col++) {
        assertEquals(actualBoard[row][col], expectedBoard[row][col]);
      }
    }
  }
}