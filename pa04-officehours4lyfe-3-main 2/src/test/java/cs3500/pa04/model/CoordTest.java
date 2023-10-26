package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * represents test for my Coord class
 */
class CoordTest {
  Coord coord1;
  Coord coord2;

  /**
   * initializes 2 different Coord objects to test
   */
  @BeforeEach
  void setup() {
    coord1 = new Coord(3, 4);
    coord2 = new Coord(4, 2);
  }

  /**
   * tests that given a new Status the coord's status will change
   * from its original value to the given one
   */
  @Test
  void setStatus() {
    assertEquals(coord1.getStatus(), Status.untouched);
    coord1.setStatus(Status.miss);
    assertEquals(coord1.getStatus(), Status.miss);
    assertEquals(coord2.getStatus(), Status.untouched);
    coord2.setStatus(Status.hit);
    assertEquals(coord2.getStatus(), Status.hit);
    coord2.setStatus(Status.untouched);
    assertEquals(coord2.getStatus(), Status.untouched);
  }

  /**
   * tests that this method gives the correct X value of the Coord
   */
  @Test
  void getXsCoord() {
    assertEquals(coord1.getXsCoord(), 3);
    assertEquals(coord2.getXsCoord(), 4);
  }

  /**
   * tests that this method gives the correct Y value of the Coord
   */
  @Test
  void getYsCoord() {
    assertEquals(coord1.getYsCoord(), 4);
    assertEquals(coord2.getYsCoord(), 2);
  }

  /**
   * tests that the method gives the correct status value of the Coord
   */
  @Test
  void getStatus() {
    assertEquals(coord1.getStatus(), Status.untouched);
    assertEquals(coord2.getStatus(), Status.untouched);
  }

  @Test
  public void coordToJsonTest() {
    assertEquals(3, coord1.coordToJson().x());
    assertEquals(4, coord1.coordToJson().y());
  }
}