package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.view.ManualDataEntry;
import cs3500.pa04.view.UserViewer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the UserPlayer class
 */
class UserPlayerTest {
  UserPlayer user;
  Appendable output;
  Readable input;
  String userShots;
  Random rand;
  ManualDataEntry shotsData;
  Scanner readInput;
  UserViewer viewer;
  List<Coord> expectedShots;
  Map<ShipType, Integer> amountOfFleets;
  List<Coord> damagesToOtherBoard;
  List<Coord> missesToOtherBoard;
  List<Coord> damagesToThisBoard;
  List<Coord> expectedHits;
  Board opponentsBoard;
  Board myBoard;
  char[][] expectedBoard;

  /**
   * initializes all the fields in order to initialize a UserPlayer object
   */
  @BeforeEach
  void setup() {
    amountOfFleets = new LinkedHashMap<>();
    expectedShots = new ArrayList<>();
    missesToOtherBoard = new ArrayList<>();
    damagesToOtherBoard = new ArrayList<>();
    damagesToThisBoard = new ArrayList<>();
    expectedHits = new ArrayList<>();
    opponentsBoard = new OtherBoard();
    myBoard = new ThisBoard(new ArrayList<>());
    rand = new Random(5);
    output = new StringBuilder();
    userShots = "0 1 0 2 0 3 0 4";
    input = new StringReader(userShots);
    readInput = new Scanner(input);
    shotsData = new ManualDataEntry(output, input, readInput);
    viewer = new UserViewer(output);
    user = new UserPlayer(rand, viewer, shotsData, myBoard, opponentsBoard);
    expectedBoard = new char[10][8];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 8; col++) {
        expectedBoard[row][col] = '.';
      }
    }
    expectedShots.add(new Coord(0, 1));
    expectedShots.add(new Coord(0, 2));
    expectedShots.add(new Coord(0, 3));
    expectedShots.add(new Coord(0, 4));
    amountOfFleets.put(ShipType.Carrier, 1);
    amountOfFleets.put(ShipType.Battleship, 1);
    amountOfFleets.put(ShipType.Destroyer, 1);
    amountOfFleets.put(ShipType.Submarine, 1);
    damagesToOtherBoard.add(new Coord(0, 1));
    missesToOtherBoard.add(new Coord(0, 2));
    missesToOtherBoard.add(new Coord(0, 3));
    missesToOtherBoard.add(new Coord(0, 4));
    damagesToThisBoard.add(new Coord(5, 1));
    damagesToThisBoard.add(new Coord(3, 6));
    damagesToThisBoard.add(new Coord(1, 5));
    damagesToThisBoard.add(new Coord(6, 3));
    expectedHits.add(new Coord(5, 1));
    expectedHits.add(new Coord(3, 6));
    user.setup(10, 8, amountOfFleets);
  }

  /**
   * tests that the name method returns the name of the UserPlayer
   */
  @Test
  void name() {
    assertEquals(user.name(), "User");
  }

  /**
   * tests the takeShots method and that using a seeded random the user takes
   * the correct amount of shots and that they are all unique
   */
  @Test
  void takeShots() {
    List<Coord> actualShots;
    actualShots = user.takeShots();
    for (int i = 0; i < actualShots.size(); i++) {
      assertEquals(actualShots.get(i).getXsCoord(), expectedShots.get(i).getXsCoord());
      assertEquals(actualShots.get(i).getYsCoord(), expectedShots.get(i).getYsCoord());
    }
  }

  /**
   * tests the reportDamage method and that given a list of opponents shots
   * a list of shots that actually hit this player's ships is returned
   */
  @Test
  void reportDamage() {
    List<Coord> actualHits = user.reportDamage(damagesToThisBoard);
    for (int i = 0; i < actualHits.size(); i++) {
      //System.out.println(actualHits.get(i).getXsCoord() + " "  + actualHits.get(i).getYsCoord());
      assertEquals(actualHits.get(i).getXsCoord(), expectedHits.get(i).getXsCoord());
      assertEquals(actualHits.get(i).getYsCoord(), expectedHits.get(i).getYsCoord());
    }
  }

  /**
   * tests the successfulHits method and that it updates the board properly with
   * displaying hits from the user onto the ai's (opponent) board
   */
  @Test
  void successfulHits() {
    user.successfulHits(damagesToOtherBoard);
    expectedBoard[1][0] = '+';
    for (int row = 0; row < opponentsBoard.getBoard().length; row++) {
      for (int col = 0; col < opponentsBoard.getBoard()[0].length; col++) {
        assertEquals(opponentsBoard.getBoard()[row][col], expectedBoard[row][col]);
      }
    }
  }
}