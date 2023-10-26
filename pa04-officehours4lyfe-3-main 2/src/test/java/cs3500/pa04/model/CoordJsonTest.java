package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the CoordJson class
 */
public class CoordJsonTest {

  CoordJson jsonCoord;
  Coord coord;

  /**
   * initializes a CoordJson object and a Coord object for testing jsonToCoord()
   */
  @BeforeEach
  void setup() {
    jsonCoord = new CoordJson(5, 4);
    coord = new Coord(5, 4);
  }

  /**
   * tests that the method converts a coordinate in json format to a
   * coordinate in our programs format
   */
  @Test
  void jsonToCoord() {
    Coord convertedToCoord = jsonCoord.jsonToCoord();
    assertEquals(convertedToCoord.getXsCoord(), coord.getXsCoord());
    assertEquals(convertedToCoord.getYsCoord(), coord.getYsCoord());
  }
}
