package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents test for the ComputerPlayer class
 */
class ComputerPlayerTest {
  ComputerPlayer ai;
  Random rand;
  Map<ShipType, Integer> amountOfFleets;
  List<Coord> expectedShots;
  List<Coord> missesToOtherBoard;
  List<Coord> damagesToOtherBoard;
  char[][] expectedBoard;
  List<Ship> fleets;
  Ship submarine;
  Ship battleship;
  Ship destroyer;
  Ship carrier;
  List<Coord> subCoords;
  List<Coord> battleCoords;
  List<Coord> destroyCoords;
  List<Coord> carrierCoords;
  Board myBoard;
  Board opponentsBoard;

  /**
   * initializes all the fields in order to initialize a ComputerPlayer object
   */
  @BeforeEach
  void setup() {
    fleets = new ArrayList<>();
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
    myBoard = new ThisBoard(new ArrayList<>());
    opponentsBoard = new OtherBoard();
    amountOfFleets = new LinkedHashMap<>();
    expectedShots = new ArrayList<>();
    missesToOtherBoard = new ArrayList<>();
    damagesToOtherBoard = new ArrayList<>();
    rand = new Random(5);
    ai = new ComputerPlayer(rand, opponentsBoard, myBoard);
    ai.setup(10, 8, amountOfFleets);
    amountOfFleets.put(ShipType.Carrier, 1);
    amountOfFleets.put(ShipType.Battleship, 1);
    amountOfFleets.put(ShipType.Destroyer, 1);
    amountOfFleets.put(ShipType.Submarine, 1);
    expectedShots.add(new Coord(1, 0));
    expectedShots.add(new Coord(1, 2));
    expectedShots.add(new Coord(3, 4));
    expectedShots.add(new Coord(5, 0));
    damagesToOtherBoard.add(new Coord(1, 0));
    damagesToOtherBoard.add(new Coord(3, 4));
    missesToOtherBoard.add(new Coord(1, 2));
    missesToOtherBoard.add(new Coord(5, 0));
    expectedBoard = myBoard.createBoard(10, 8);
  }

  /**
   * tests the setup() method and that using a seeded random the ships do not overlap and are placed
   * across the correct amount of coordinates based on their size
   */
  @Test
  void setupTest() {
    fleets = new ArrayList<>();
    subCoords = new ArrayList<>(Arrays.asList(new Coord(7, 2), new Coord(7, 3), new Coord(7, 4)));
    submarine = new Ship(ShipType.Submarine, subCoords);
    battleCoords = new ArrayList<>(
        Arrays.asList(new Coord(3, 1), new Coord(3, 2), new Coord(3, 3),
            new Coord(3, 4), new Coord(3, 5)));
    battleship = new Ship(ShipType.Battleship, battleCoords);
    destroyCoords = new ArrayList<>(
        Arrays.asList(new Coord(2, 0), new Coord(3, 0), new Coord(4, 0), new Coord(5, 0)));
    destroyer = new Ship(ShipType.Destroyer, destroyCoords);
    carrierCoords = new ArrayList<>(
        Arrays.asList(new Coord(5, 2), new Coord(5, 3), new Coord(5, 4),
            new Coord(5, 5), new Coord(5, 6), new Coord(5, 7)));
    carrier = new Ship(ShipType.Carrier, carrierCoords);
    fleets.add(carrier);
    fleets.add(battleship);
    fleets.add(destroyer);
    fleets.add(submarine);
    List<Ship> actualFleets = ai.setup(10, 8, amountOfFleets);
    for (int i = 0; i < actualFleets.size(); i++) {
      for (int j = 0; j < actualFleets.get(i).getCoords().size(); j++) {
        assertEquals(actualFleets.get(i).getCoords().get(j).getXsCoord(),
            fleets.get(i).getCoords().get(j).getXsCoord());
        assertEquals(actualFleets.get(i).getCoords().get(j).getYsCoord(),
            fleets.get(i).getCoords().get(j).getYsCoord());
      }
    }
  }

  /**
   * tests the name method and that the correct name of the player is returned
   */
  @Test
  void name() {
    assertEquals(ai.name(), "sierrawelsch");
  }

  /**
   * tests the takeShots method and that using a seeded random the ai takes
   * the correct amount of shots and that they are all unique
   */
  @Test
  void takeShots() {
    List<Coord> actualShots = ai.takeShots();
    for (int i = 0; i < actualShots.size(); i++) {
      assertEquals(actualShots.get(i).getXsCoord(), expectedShots.get(i).getXsCoord());
      assertEquals(actualShots.get(i).getYsCoord(), expectedShots.get(i).getYsCoord());
    }
  }

  /**
   * tests the successfulHits method and that it updates the board properly with
   * displaying hits from the ai onto the user's board
   */
  @Test
  void successfulHits() {
    ai.setup(10, 8, amountOfFleets);
    ai.successfulHits(damagesToOtherBoard);
    expectedBoard[0][1] = '+';
    expectedBoard[4][3] = '+';
    for (int row = 0; row < myBoard.getBoard().length; row++) {
      for (int col = 0; col < myBoard.getBoard()[0].length; col++) {
        assertEquals(myBoard.getBoard()[row][col], expectedBoard[row][col]);
      }
    }
  }
}