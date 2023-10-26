package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a coordinate in Json format
 *
 * @param x represents a coordinate on the x-axis
 *
 * @param y represents a coordinates on the y-axis
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

  /**
   * converts a coordinate in Json format to a coordinate in our program's format
   *
   * @return a coordinate in our program's format
   */
  public Coord jsonToCoord() {
    return new Coord(x, y);
  }
}
