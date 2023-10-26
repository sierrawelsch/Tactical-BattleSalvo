package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for my CheckInput class
 */
class CheckInputTest {

  CheckInput check;

  /**
   * initializes a CheckInput object
   */
  @BeforeEach
  void setup() {
    check = new CheckInput();
  }

  /**
   * tests the isInvalidAmountOfFleets method that determines if the inputted amount of ships
   * are not positive or the sum of them is greater than the smallest dimension of the board
   */
  @Test
  void isInvalidAmountOfFleets() {
    assertTrue(check.isInvalidAmountOfFleets(0, 3,
        4, 5, 11));
    assertTrue(check.isInvalidAmountOfFleets(3, -1,
        4, 5, 11));
    assertTrue(check.isInvalidAmountOfFleets(3, 3,
        -2, 5, 11));
    assertTrue(check.isInvalidAmountOfFleets(4, 3,
        4, -3, 11));
    assertTrue(check.isInvalidAmountOfFleets(1, 2, 3, 4, 6));
    assertFalse(check.isInvalidAmountOfFleets(1, 1, 1, 1, 6));
  }

  /**
   * tests the isInvalidDimensions method that determines if the given dimensions are not in
   * the range from 6 to 15 inclusive
   */
  @Test
  void isInvalidDimensions() {
    assertTrue(check.isInvalidDimensions(5, 14));
    assertTrue(check.isInvalidDimensions(5, 2));
    assertTrue(check.isInvalidDimensions(7, 20));
    assertFalse(check.isInvalidDimensions(12, 10));
  }

  /**
   * tests the isInvalidShots method that determines if the given shot (coordinate) is positive
   * and not greater than the dimensions of the board
   */
  @Test
  void isInvalidShots() {
    assertFalse(check.isInvalidShots(0, 5, 6, 6));
    assertTrue(check.isInvalidShots(7, 0, 6, 6));
    assertTrue(check.isInvalidShots(-1, 0, 6, 6));
    assertTrue(check.isInvalidShots(3, 6, 6, 6));
    assertTrue(check.isInvalidShots(7, -3, 6, 6));
  }
}