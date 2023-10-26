package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * represents the arguments for a setup message from the server
 *
 * @param height the height of the battlesalvo board
 *
 * @param width the width of the battlesalvo board
 *
 * @param fleetSpec a hashmap of the types of ships to how many of each should be on the board
 */
public record SetupJson(
    @JsonProperty("height") int height,
    @JsonProperty("width") int width,
    @JsonProperty("fleet-spec") Map<String, Integer> fleetSpec) {

  /**
   * converts a hashmap in json format to a hashmap in our program format
   * (converts a string representing the type of the ship to an enumeration
   * representing the type of the ship)
   *
   * @return a LinkedHashmap in our program format
   */
  public Map<ShipType, Integer> fleetSpecToHashmap() {
    Map<ShipType, Integer> fleets = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : fleetSpec.entrySet()) {
      if (entry.getKey().equals("CARRIER")) {
        fleets.put(ShipType.Carrier, entry.getValue());
      } else if (entry.getKey().equals("BATTLESHIP")) {
        fleets.put(ShipType.Battleship, entry.getValue());
      } else if (entry.getKey().equals("DESTROYER")) {
        fleets.put(ShipType.Destroyer, entry.getValue());
      } else if (entry.getKey().equals("SUBMARINE")) {
        fleets.put(ShipType.Submarine, entry.getValue());
      }
    }
    return fleets;
  }

}
