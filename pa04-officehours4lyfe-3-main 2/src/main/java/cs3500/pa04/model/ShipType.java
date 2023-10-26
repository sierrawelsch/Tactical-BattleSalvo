package cs3500.pa04.model;

/**
 * Represents the types of ship that can be on a battleship board
 */
public enum ShipType {
  Carrier(6), Battleship(5), Destroyer(4), Submarine(3);
  private final int size;

  /**
   * initializes the type of ship and its size associated with it
   *
   * @param size an int that represents the length of a type of ship
   */
  private ShipType(int size) {
    this.size = size;
  }

  /**
   * gets the size of a ship type
   *
   * @return the size of a type of ship
   */
  public int getSize() {
    return size;
  }

}
