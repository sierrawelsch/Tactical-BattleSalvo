package cs3500.pa04.controller;

import cs3500.pa04.model.Board;
import cs3500.pa04.model.CheckInput;
import cs3500.pa04.model.ComputerPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.OtherBoard;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.ThisBoard;
import cs3500.pa04.model.UserPlayer;
import cs3500.pa04.view.ManualDataEntry;
import cs3500.pa04.view.UserViewer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the Controller in terms of MVC, reads user input and
 * delegates to other classes to perform
 * tasks with that input to create the game of BattleSalvo
 */
public class BattleSalvoController implements Controller {
  private Appendable output;
  private Readable input;
  private UserPlayer user;
  private ComputerPlayer ai;
  private UserViewer view;
  private Scanner readInput;
  private int height;
  private int width;
  private ManualDataEntry takeShotsData;
  private CheckInput invalid;
  private Random randForAi;
  private Random randForUser;
  private Board myBoard;
  private Board opponentBoard;

  /**
   * initializes a Readable, Appendable, and two Random objects to read input, output, and randomly
   * generate shots and ships
   *
   * @param input reads user input
   *
   * @param output outputs messages to the console to be seen by the user
   *
   * @param randForUser random object for the UserPlayer class
   *
   * @param randForAi random object for the ComputerPlayer class
   */
  public BattleSalvoController(Readable input, Appendable output, Random randForUser,
                               Random randForAi) {
    this.input = input;
    this.readInput = new Scanner(this.input);
    this.output = output;
    view = new UserViewer(output);
    invalid = new CheckInput();
    this.takeShotsData = new ManualDataEntry(output, input, readInput);
    this.randForUser = randForUser;
    this.randForAi = randForAi;
    myBoard = new ThisBoard(new ArrayList<>());
    opponentBoard = new OtherBoard();
    this.user = new UserPlayer(randForUser, view, takeShotsData, myBoard, opponentBoard);
  }

  /**
   * displays messages to the user and takes in responding user input and processes it
   * by delegating to other model type classes to perform battle salvo functionality
   *
   * @throws IOException if an error occurs
   */
  public void run() throws IOException {
    view.display("Welcome to BattleSalvo: a similar version of Battleship!");
    view.display("Please enter a valid height and width each in the range (6, 15) below:");
    height = readInput.nextInt();
    width = readInput.nextInt();
    while (invalid.isInvalidDimensions(width, height)) {
      view.display("You've entered invalid dimensions. "
          + "The height and width of the game must be in the range (6, 15), inclusive. Try again!");
      height = readInput.nextInt();
      width = readInput.nextInt();
    }
    view.display(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    view.display("Remember, your fleet may not exceed size " + Math.min(width, height));
    initializeAmountOfFleets(Math.min(width, height));
    processShots();
  }

  /**
   * takes in user input for how many fleets they want to play BattleSalvo with
   * and makes sure their input meets the valid constraints
   *
   * @param maxSize the smallest dimension of the BattleSalvo board
   */
  private void initializeAmountOfFleets(int maxSize) {
    Map<ShipType, Integer> amountOfFleets = new LinkedHashMap<>();
    int carrierAmount = readInput.nextInt();
    int battleshipAmount = readInput.nextInt();
    int destroyerAmount = readInput.nextInt();
    int submarineAmount = readInput.nextInt();
    while (invalid.isInvalidAmountOfFleets(carrierAmount, battleshipAmount, destroyerAmount,
        submarineAmount, maxSize)) {
      view.display("You've entered invalid fleet sizes.");
      view.display(
          "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
      view.display("Remember, your fleet may not exceed size " + maxSize);
      carrierAmount = readInput.nextInt();
      battleshipAmount = readInput.nextInt();
      destroyerAmount = readInput.nextInt();
      submarineAmount = readInput.nextInt();
    }
    amountOfFleets.put(ShipType.Carrier, carrierAmount);
    amountOfFleets.put(ShipType.Battleship, battleshipAmount);
    amountOfFleets.put(ShipType.Destroyer, destroyerAmount);
    amountOfFleets.put(ShipType.Submarine, submarineAmount);
    user.setup(height, width, amountOfFleets);
    ai = new ComputerPlayer(randForAi, opponentBoard, myBoard);
    ai.setup(height, width, amountOfFleets);
  }

  /**
   * initiates the process of the user and ai taking shots
   * and determining which of their shots were successful or not
   * and keeping track of the state of the game
   *
   * @throws IOException if an error occurs
   */
  private void processShots() throws IOException {
    List<Coord> userShots;
    List<Coord> aiShots;
    List<Coord> damagesToUserShips;
    List<Coord> damagesToAiShips;
    aiShots = new ArrayList<>();
    while (true) {
      userShots = user.takeShots();
      if (userShots.size() != 0) {
        aiShots = ai.takeShots();
      }
      if (userShots.size() != 0 && aiShots.size() != 0) {
        damagesToUserShips = user.reportDamage(aiShots);
        damagesToAiShips = ai.reportDamage(userShots);
        user.successfulHits(damagesToAiShips);
        ai.successfulHits(damagesToUserShips);
      } else if (userShots.size() == 0 && aiShots.size() != 0) {
        view.display("Game Over! The ai sunk all of your ships :(");
        break;
      } else if (aiShots.size() == 0 && userShots.size() != 0) {
        view.display("Game Over! You sunk all of the ai ships :)");
        break;
      } else {
        view.display("Game Over! You tied :o");
        break;
      }
    }
  }
}
