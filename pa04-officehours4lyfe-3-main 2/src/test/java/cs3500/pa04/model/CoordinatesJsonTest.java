package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for the CoordinatesJson class
 */
public class CoordinatesJsonTest {

  CoordinatesJson jsonCoords;
  CoordJson[] listOfCoordJson;
  CoordJson c1;
  CoordJson c2;
  CoordJson c3;

  /**
   * setup before each test is called
   */
  @BeforeEach
  void setup() {
    listOfCoordJson = new CoordJson[3];
    c1 = new CoordJson(3, 3);
    c2 = new CoordJson(3, 6);
    c3 = new CoordJson(1, 0);
    listOfCoordJson[0] = c1;
    listOfCoordJson[1] = c2;
    listOfCoordJson[2] = c3;
    jsonCoords = new CoordinatesJson(listOfCoordJson);
  }

  /**
   * test for converting a Json to a list of Coords
   */
  @Test
  void jsonToCoordList() {
    List<Coord> convertedList = jsonCoords.jsonToCoordList();

    assertEquals(convertedList.get(0).getXsCoord(), 3);
    assertEquals(convertedList.get(0).getYsCoord(), 3);

    assertEquals(convertedList.get(1).getXsCoord(), 3);
    assertEquals(convertedList.get(1).getYsCoord(), 6);

    assertEquals(convertedList.get(2).getXsCoord(), 1);
    assertEquals(convertedList.get(2).getYsCoord(), 0);
  }
}
