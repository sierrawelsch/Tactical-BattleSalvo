package cs3500.pa04.model;

/**
 * Represents a coordinate on a battleship board
 */
public class Coord {
  private int xcoord;
  private int ycoord;
  private Status status;

  /**
   * initializes an x coordinate and
   * a y coordinate to create a Coord
   *
   * @param xcoord an int that represents the horizontal coordinate
   *
   * @param ycoord an int that represents the vertical coordinate
   */
  public Coord(int xcoord, int ycoord) {
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.status = Status.untouched;
  }

  /**
   * sets the status of a coordinate to a given new status
   *
   * @param newStatus a new Status
   */
  public void setStatus(Status newStatus) {
    this.status = newStatus;
  }

  /**
   * gets the x coordinate of a Coord
   *
   * @return the x coordinate of a Coord
   */
  public int getXsCoord() {
    return xcoord;
  }

  /**
   * gets the y coordinate of a Coord
   *
   * @return the y coordinate of a Coord
   */
  public int getYsCoord() {
    return ycoord;
  }

  /**
   * gets the status of a Coord
   *
   * @return the status of a Coord
   */
  public Status getStatus() {
    return status;
  }

  /**
   * converts a coordinate in our program format to json format to be passed to the server
   *
   * @return a coordinate in json format
   */
  public CoordJson coordToJson() {
    return new CoordJson(this.getXsCoord(), this.getYsCoord());
  }
}
