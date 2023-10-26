package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents tests for my Ship class
 */
class ShipTest {
  Ship submarine;
  Ship battleship;
  Ship destroyer;
  Ship carrier;
  List<Coord> subCoords;
  List<Coord> battleCoords;
  List<Coord> destroyCoords;
  List<Coord> carrierCoords;

  /**
   * initializes fields to initialize different Ship objects
   */
  @BeforeEach
  void setup() {
    subCoords = new ArrayList<>(Arrays.asList(new Coord(0, 1), new Coord(0, 2), new Coord(0, 3)));
    submarine = new Ship(ShipType.Submarine, subCoords);
    battleCoords = new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(3, 0),
            new Coord(4, 0)));
    battleship = new Ship(ShipType.Battleship, battleCoords);
    destroyCoords = new ArrayList<>(
        Arrays.asList(new Coord(4, 0), new Coord(4, 1), new Coord(4, 2), new Coord(4, 3)));
    destroyer = new Ship(ShipType.Destroyer, destroyCoords);
    carrierCoords = new ArrayList<>(
        Arrays.asList(new Coord(3, 1), new Coord(3, 2), new Coord(3, 3), new Coord(3, 4),
            new Coord(3, 5), new Coord(3, 6)));
    carrier = new Ship(ShipType.Carrier, carrierCoords);
  }

  /**
   * tests that the correct type of the ship is returned
   */
  @Test
  void getType() {
    assertEquals(submarine.getType(), ShipType.Submarine);
    assertEquals(destroyer.getType(), ShipType.Destroyer);
    assertEquals(carrier.getType(), ShipType.Carrier);
    assertEquals(battleship.getType(), ShipType.Battleship);
  }

  /**
   * tests that given a coordinate and a given status, if that coordinate is in the Ship's list of
   * coordinates this method returns true and updates the status of that
   * coordinate to the given status
   */
  @Test
  void updateStatus() {
    for (Coord c : submarine.getCoords()) {
      if (c.getXsCoord() == 0 && c.getYsCoord() == 2) {
        assertEquals(c.getStatus(), Status.untouched);
      }
    }
    assertTrue(submarine.updateStatus(new Coord(0, 2), Status.miss));

    for (Coord c : submarine.getCoords()) {
      if (c.getXsCoord() == 0 && c.getYsCoord() == 2) {
        assertEquals(c.getStatus(), Status.miss);
      }
    }

    assertFalse(submarine.updateStatus(new Coord(5, 4), Status.miss));

    for (Coord c : destroyer.getCoords()) {
      if (c.getXsCoord() == 4 && c.getYsCoord() == 1) {
        assertEquals(c.getStatus(), Status.untouched);
      }
    }
    assertTrue(destroyer.updateStatus(new Coord(4, 1), Status.hit));
    for (Coord c : submarine.getCoords()) {
      if (c.getXsCoord() == 4 && c.getYsCoord() == 1) {
        assertEquals(c.getStatus(), Status.hit);
      }
    }

    assertFalse(destroyer.updateStatus(new Coord(0, 0), Status.hit));
  }

  /**
   * tests the isSunk method that determines if all the coordinates in a Ship's list of coordinates
   * have a hit status
   */
  @Test
  void isSunk() {
    assertFalse(battleship.isSunk());
    for (Coord c : battleCoords) {
      c.setStatus(Status.hit);
    }
    assertTrue(battleship.isSunk());
    assertFalse(carrier.isSunk());
    for (Coord c : carrierCoords) {
      c.setStatus(Status.miss);
    }
    assertFalse(carrier.isSunk());
  }

  /**
   * tests that a ship's list of coordinates has the correct values
   */
  @Test
  void getCoords() {
    for (int i = 0; i < battleship.getCoords().size(); i++) {
      assertEquals(battleship.getCoords().get(i), battleCoords.get(i));
    }

    for (int i = 0; i < carrier.getCoords().size(); i++) {
      assertEquals(carrier.getCoords().get(i), carrierCoords.get(i));
    }
  }

  /**
   * test for converting a ShipJson to a ship
   */
  @Test
  public void jsonToShipTest() {
    assertEquals(battleship.shipToJson().startingCoord().x(), battleCoords.get(0).getXsCoord());
    assertEquals(battleship.shipToJson().startingCoord().y(), battleCoords.get(0).getYsCoord());
    assertEquals(battleship.shipToJson().length(), 5);
    assertEquals(battleship.shipToJson().direction(), Orientation.HORIZONTAL);
  }
}