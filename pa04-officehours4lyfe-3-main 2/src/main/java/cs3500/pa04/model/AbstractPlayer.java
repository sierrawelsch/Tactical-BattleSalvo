package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a player playing battleship
 */
public abstract class AbstractPlayer implements Player {
  protected Board myBoard;
  protected List<Ship> allShips;
  protected List<Coord> shipCoordinates;
  protected Board opponentBoard;
  protected List<Coord> currentShots;
  protected int shotsRemaining;
  protected Random rand;
  protected List<Coord> opponentMissesOnBoard;

  protected int width;
  protected int height;

  /**
   * initializes a Random object and list of coordinates that
   * will keep track of important ship and shots data
   *
   * @param rand a Random object used to generate random number/booleans
   */
  AbstractPlayer(Random rand, Board myBoard, Board opponentBoard) {
    this.rand = rand;
    this.myBoard = myBoard;
    this.opponentBoard = opponentBoard;
    allShips = new ArrayList<>();
    shipCoordinates = new ArrayList<>();
    opponentMissesOnBoard = new ArrayList<>();
  }

  /**
   * returns the name of a player
   *
   * @return the name of a player
   */
  public abstract String name();


  /**
   * randomly initializes placements of each player's ships on the battleship board
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   *
   * @param width          the width of the board, range: [6, 15] inclusive
   *
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   *
   * @return a list of ships that are on the player's board
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.width = width;
    this.height = height;
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int ships = 0; ships < entry.getValue(); ships++) {
        generatePlacements(entry.getKey(), width, height);
      }
    }
    myBoard.setFleet(allShips);
    myBoard.createBoard(height, width);
    opponentBoard.createBoard(height, width);
    shotsRemaining = allShips.size();
    return allShips;
  }

  /**
   * a helper for setup, randomly generates appropiate placements (coordinates) for
   * the ships on the player's board and adds them to a list of ships
   *
   * @param type the type of the ship
   *
   * @param width the width of the board
   *
   * @param height the height of the board
   */
  private void generatePlacements(ShipType type, int width, int height) {
    int randXsCoord;
    int randYsCoord;
    int shipSize = type.getSize();
    boolean horizontal;
    ArrayList<Coord> newShipCoords;
    while (true) {
      randXsCoord = rand.nextInt(width);
      randYsCoord = rand.nextInt(height);
      horizontal = rand.nextBoolean();
      newShipCoords = new ArrayList<>();
      if (horizontal) {
        if (randXsCoord + shipSize <= width
            && doesNotOverlap(randXsCoord, randYsCoord, shipSize, true)) {
          for (int i = 0; i < shipSize; i++) {
            Coord coordInShip = new Coord(randXsCoord + i, randYsCoord);
            newShipCoords.add(coordInShip);
            shipCoordinates.add(coordInShip);
          }
          break;
        }
      } else {
        if (randYsCoord + shipSize <= height
            && doesNotOverlap(randXsCoord, randYsCoord, shipSize, false)) {
          for (int i = 0; i < shipSize; i++) {
            Coord coordInShip = new Coord(randXsCoord, randYsCoord + i);
            newShipCoords.add(coordInShip);
            shipCoordinates.add(coordInShip);
          }
          break;
        }
      }
    }
    allShips.add(new Ship(type, newShipCoords));
  }


  /**
   * determines if the desired ship coordinates would overlap with existing ships placed
   * on the board
   *
   * @param xcoord the x value of the potential ship coordinate
   *
   * @param ycoord the y value of the potential ship coordinate
   *
   * @param shipSize the length of the ship
   *
   * @param horizontal a boolean that represents if the ship is horizontal or not
   *
   * @return if the desired ship coordinates would overlap with existing ships
   */
  private boolean doesNotOverlap(int xcoord, int ycoord, int shipSize, boolean horizontal) {
    Coord newCoord;
    int j = 0;
    while (j < shipSize) {
      if (horizontal) {
        newCoord = new Coord(xcoord + j, ycoord);
      } else {
        newCoord = new Coord(xcoord, ycoord + j);
      }
      if (contains(shipCoordinates, newCoord)) {
        return false;
      }
      j++;
    }
    return true;
  }


  /**
   * determines if the given coordinate is in a list of coordinates
   *
   * @param coordinates a list of coordinates that store the ship coordinates
   *
   * @param newCoord the given coordinate
   *
   * @return if the given coordinate is in a given list of coordinates
   */
  protected boolean contains(List<Coord> coordinates, Coord newCoord) {
    for (int i = 0; i < coordinates.size(); i++) {
      if ((coordinates.get(i).getXsCoord() == newCoord.getXsCoord())
          && (coordinates.get(i).getYsCoord() == newCoord.getYsCoord())) {
        return true;
      }
    }
    return false;
  }

  /**
   * returns a list of coordinates of the shots taken by the player
   *
   * @return  a list of coordinates of the shots taken by the player
   *
   */
  public abstract List<Coord> takeShots();


  /**
   * returns a list of the opponents hits on this player's board
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   *
   * @return a list of the opponents hits on this player's board
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hitsOnPlayerBoard = new ArrayList<>();
    opponentMissesOnBoard = new ArrayList<>();
    for (Coord coordinate : opponentShotsOnBoard) {
      if (contains(shipCoordinates, coordinate)) {
        updateShipStatus(coordinate, Status.hit);
        hitsOnPlayerBoard.add(coordinate);
      } else {
        updateShipStatus(coordinate, Status.miss);
        opponentMissesOnBoard.add(coordinate);
      }
    }
    processMisses(opponentMissesOnBoard);
    return hitsOnPlayerBoard;
  }


  /**
   * updates the baord to reflect the misses on this player's board
   *
   * @param opponentMissesOnBoard the opponents shots that did not hit this player's ships
   */
  protected abstract void processMisses(List<Coord> opponentMissesOnBoard);

  /**
   * updates the status of a coordinate with a ship on it if it has been hit or missed
   * by the opponent
   *
   * @param shipCoord the coordinate the opponent shot at
   *
   * @param newStatus the status of this coordinate depending on if there was a ship
   *                  there or not
   */
  private void updateShipStatus(Coord shipCoord, Status newStatus) {
    for (Ship s : allShips) {
      if (s.updateStatus(shipCoord, newStatus)) {
        if (s.isSunk()) {
          shotsRemaining--;
        }
      }
    }
  }


  /**
   * updates the opponent's board to reflect the user hits
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  /**
   * is called when the game ends and prints the outcome
   *
   * @param result if the player has won, lost, or forced a draw
   *
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
  }
}
