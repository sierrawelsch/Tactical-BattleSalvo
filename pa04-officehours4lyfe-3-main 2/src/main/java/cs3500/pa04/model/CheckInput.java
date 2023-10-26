package cs3500.pa04.model;

/**
 * Represents checking if user input is valid and fits the appropriate rules of battleSalvo
 */
public class CheckInput {
  /**
   * initializes a CheckInput object
   */
  public CheckInput() {
  }

  /**
   * determines if the amount of fleets given by the user is positive and less than the
   * smallest board dimension
   *
   * @param carrierAmount the amount of carrier ships the user inputs
   *
   * @param battleshipAmount the amount of battleships the user inputs
   *
   * @param destroyerAmount the amount of destroyer ships the user inputs
   *
   * @param submarineAmount the amount of submarine ships the user inputs
   *
   * @param maxSize the smallest board dimension
   *
   * @return if the amount of fleets given is valid
   */
  public boolean isInvalidAmountOfFleets(int carrierAmount, int battleshipAmount,
                                         int destroyerAmount, int submarineAmount,
                                         int maxSize) {
    return carrierAmount <= 0 || battleshipAmount <= 0
        || destroyerAmount <= 0 || submarineAmount <= 0
        || carrierAmount + battleshipAmount + destroyerAmount + submarineAmount > maxSize;
  }

  /**
   * determines if the dimensions of a board given by the user are valid
   *
   * @param givenWidth the width of the board inputted by the user
   *
   * @param givenHeight the height of the board inputted by the user
   *
   * @return if the dimensions of a board given by the user are valid
   */
  public boolean isInvalidDimensions(int givenWidth, int givenHeight) {
    return givenHeight < 6 || givenHeight > 15 || givenWidth < 6 || givenWidth > 15;
  }

  /**
   * determines if the coordinates of the shots given by the user are valid
   * given the board dimensions/are positive
   *
   * @param xcoord the user inputted x coordinate
   *
   * @param ycoord the user inputted y coordinate
   *
   * @param height the height of the board
   *
   * @param width the width of the coordinate
   *
   * @return if the coordinates of the shots given by the user are valid
   */
  public boolean isInvalidShots(int xcoord, int ycoord, int height, int width) {
    return xcoord < 0 || xcoord >= width || ycoord < 0 || ycoord >= height;
  }
}
