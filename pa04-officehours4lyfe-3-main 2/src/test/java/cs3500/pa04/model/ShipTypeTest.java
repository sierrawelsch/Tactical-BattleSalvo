package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * represents test for my ShipType class
 */
class ShipTypeTest {

  /**
   * tests that the correct size matches each ShipType
   */
  @Test
  void getSize() {
    assertEquals(ShipType.Carrier.getSize(), 6);
    assertEquals(ShipType.Battleship.getSize(), 5);
    assertEquals(ShipType.Destroyer.getSize(), 4);
    assertEquals(ShipType.Submarine.getSize(), 3);
  }
}