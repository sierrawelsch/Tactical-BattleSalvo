package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of coordinates in Json format
 *
 * @param coordinates a list of coordinates in Json format
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") CoordJson[] coordinates) {

  /**
   * converts a list of coordinates in json format to a list of coordinates in our program's format
   *
   * @return a list of coordinates in our program format
   */
  public List<Coord> jsonToCoordList() {
    List<Coord> coords = new ArrayList<>();
    for (CoordJson c : coordinates) {
      coords.add(c.jsonToCoord());
    }
    return coords;
  }
}
