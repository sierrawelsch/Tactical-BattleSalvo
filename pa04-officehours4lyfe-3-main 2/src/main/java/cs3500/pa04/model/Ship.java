package cs3500.pa04.model;

import java.util.List;

/**
 * Represents a Ship that is the game Battleship
 */
public class Ship {
  private ShipType type;
  private List<Coord> coords;

  /**
   * initializes a ShipType object, and a List of Coordinates
   * to create a Ship
   *
   * @param type   the type of the ship
   *
   * @param coords the coordinates that have this ship on it on the board
   */
  public Ship(ShipType type, List<Coord> coords) {
    this.type = type;
    this.coords = coords;
  }

  /**
   * gets the type of the ship
   *
   * @return returns the type of the ship
   */
  public ShipType getType() {
    return type;
  }

  /**
   * updates the status of the coordinate with a ship on it with
   * the new status passed in (whether it was hit or missed) and whether this ship is on
   * the coordinate given
   *
   * @param shipCoord the coordinate the opponent shot at that they either
   *     hit or missed a ship
   *
   * @param newStatus if the opponent hit or missed a ship
   *
   * @return true if this ship is on the given coordinate
   */
  public boolean updateStatus(Coord shipCoord, Status newStatus) {
    for (Coord c : coords) {
      if (c.getXsCoord() == shipCoord.getXsCoord() && c.getYsCoord() == shipCoord.getYsCoord()) {
        if (c.getStatus() != newStatus) {
          c.setStatus(newStatus);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * returns if the ship has sunk meaning the opponent
   * hit all the coordinates in the ship
   *
   * @return a boolean that returns if the ship has sunk
   */
  public boolean isSunk() {
    for (Coord coordinate : coords) {
      if (coordinate.getStatus().equals(Status.untouched)
          || coordinate.getStatus().equals(Status.miss)) {
        return false;
      }
    }
    return true;
  }

  /**
   * returns the list of coordinates this ship is on, on the board
   *
   * @return the list of coordinates this ship is on
   */
  public List<Coord> getCoords() {
    return coords;
  }

  /**
   * converts a ship to a ShipJson
   *
   * @return ShipJson created from ship's parameters
   */
  public ShipJson shipToJson() {
    Coord startingCoord = this.getCoords().get(0);
    Coord nextCoord = this.getCoords().get(1);
    if (startingCoord.getXsCoord() == nextCoord.getXsCoord()) {
      return new ShipJson(startingCoord.coordToJson(), this.getCoords().size(),
          Orientation.VERTICAL);
    } else {
      return new ShipJson(startingCoord.coordToJson(), this.getCoords().size(),
          Orientation.HORIZONTAL);
    }
  }
}
