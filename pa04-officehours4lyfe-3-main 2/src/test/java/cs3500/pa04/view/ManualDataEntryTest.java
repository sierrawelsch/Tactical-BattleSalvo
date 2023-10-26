package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents test for my ManualDataEntry class
 */
class ManualDataEntryTest {
  Appendable output;
  Readable input;
  Scanner readInput;
  String inputShots;
  ManualDataEntry shotsData;
  List<Coord> expectedShots;

  /**
   * Initializes all the fields to initialize a ManualDataEntry object
   */
  @BeforeEach
  void setup() {
    output = new StringBuilder();
    expectedShots = new ArrayList<>();
    inputShots = "1 1 2 2 3 3 4 4";
    input = new StringReader(inputShots);
    readInput = new Scanner(input);
    shotsData = new ManualDataEntry(output, input, readInput);
    expectedShots.add(new Coord(1, 1));
    expectedShots.add(new Coord(2, 2));
    expectedShots.add(new Coord(3, 3));
    expectedShots.add(new Coord(4, 4));
  }


  /**
   * tests that given the shots remaining the correct message is displayed
   * to request shots from the user
   */
  @Test
  void requestShots() {
    shotsData.requestShots(3);
    assertEquals(output.toString(), "Please enter 3 shots:\n");
    setup();
    shotsData.requestShots(5);
    assertEquals(output.toString(), "Please enter 5 shots:\n");
  }

  /**
   * tests that the shots the user inputs are properly inputted and converted
   * to a list of coordinates
   */
  @Test
  void getUserShots() {
    List<Coord> actualShots = shotsData.getUserShots(4, 6, 6);
    for (int i = 0; i < actualShots.size(); i++) {
      assertEquals(actualShots.get(i).getXsCoord(), expectedShots.get(i).getXsCoord());
      assertEquals(actualShots.get(i).getYsCoord(), expectedShots.get(i).getYsCoord());
    }
  }

  /**
   * tests that if the user inputted an invalid coordinate to shoot at it
   * does not process that shot and restarts collecting shots from the user
   */
  @Test
  void getInvalidUserShots() {
    inputShots = "1 1 2 2 10 3 1 1 2 2 3 3 4 4";
    input = new StringReader(inputShots);
    readInput = new Scanner(input);
    shotsData = new ManualDataEntry(output, input, readInput);
    List<Coord> actualShots = shotsData.getUserShots(4, 6, 6);
    for (int i = 0; i < actualShots.size(); i++) {
      assertEquals(actualShots.get(i).getXsCoord(), expectedShots.get(i).getXsCoord());
      assertEquals(actualShots.get(i).getYsCoord(), expectedShots.get(i).getYsCoord());
    }
  }
}