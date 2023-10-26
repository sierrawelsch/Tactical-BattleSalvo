package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a ship in battlesalvo in json format
 *
 * @param startingCoord represents the starting coordinate where the ship resides
 *
 * @param length represents the length of the ship
 *
 * @param direction represents the direction of the ship on the board (horizontal or vertical)
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson startingCoord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Orientation direction) {
}
