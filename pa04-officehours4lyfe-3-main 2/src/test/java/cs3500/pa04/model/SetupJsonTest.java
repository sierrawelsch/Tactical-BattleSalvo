package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * class to test the SetupJson record
 */
public class SetupJsonTest {

  SetupJson serverArgs;
  Map<String, Integer> fleetSpecJson;

  /**
   * setup before each test method
   */
  @BeforeEach
  void setup() {
    fleetSpecJson = new LinkedHashMap<>();
    fleetSpecJson.put("CARRIER", 1);
    fleetSpecJson.put("BATTLESHIP", 1);
    fleetSpecJson.put("DESTROYER", 1);
    fleetSpecJson.put("SUBMARINE", 1);
    serverArgs = new SetupJson(12, 8, fleetSpecJson);
  }

  /**
   * test for converting a fleet-spec to a linked HashMap
   */
  @Test
  void fleetSpecToHashmap() {
    Map<ShipType, Integer> actualFleetSpec = serverArgs.fleetSpecToHashmap();
    assertTrue(actualFleetSpec.containsKey(ShipType.Carrier));
    assertTrue(actualFleetSpec.containsKey(ShipType.Battleship));
    assertTrue(actualFleetSpec.containsKey(ShipType.Destroyer));
    assertTrue(actualFleetSpec.containsKey(ShipType.Submarine));

    assertEquals(actualFleetSpec.get(ShipType.Carrier), 1);
    assertEquals(actualFleetSpec.get(ShipType.Battleship), 1);
    assertEquals(actualFleetSpec.get(ShipType.Destroyer), 1);
    assertEquals(actualFleetSpec.get(ShipType.Submarine), 1);
  }
}
