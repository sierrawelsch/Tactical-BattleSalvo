package cs3500.pa04.view;

import cs3500.pa04.model.CheckInput;
import cs3500.pa04.model.Coord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a view in terms of MVC that outputs to the user and obtains the user input for them to
 * take shots
 */
public class ManualDataEntry {
  private Appendable output;
  private Readable input;
  private Scanner readInput;

  /**
   * initializes an Appendable object, a Readable object, and a Scanner object to display and obtain
   * input
   *
   * @param output to append Strings
   * @param input to read Strings
   * @param readInput to read user input
   */
  public ManualDataEntry(Appendable output, Readable input, Scanner readInput) {
    this.output = output;
    this.input = input;
    this.readInput = readInput;
  }

  /**
   * display a message to the user asking them to take a certain number of shots given
   *
   * @param shotsRemaining the amount of shots a player can take
   */
  public void requestShots(int shotsRemaining) {
    try {
      output.append("Please enter " + shotsRemaining + " shots:\n");
    } catch (IOException e) {
      System.out.println("We have encountered an unexpected error!");
    }
  }

  /**
   * returns a list of coordinates that represents all the user's inputted shots
   *
   * @param shotsRemaining the amount of shots a player can take
   *
   * @param width          the width of the board
   *
   * @param height         the height of the board
   *
   * @return a list of coordinates that represents all the user's inputted shots
   */
  public ArrayList<Coord> getUserShots(int shotsRemaining, int width, int height) {
    ArrayList<Coord> curShots = new ArrayList<>();
    CheckInput invalid = new CheckInput();
    int xcoord;
    int ycoord;
    for (int i = 0; i < shotsRemaining; i++) {
      xcoord = readInput.nextInt();
      ycoord = readInput.nextInt();
      if (invalid.isInvalidShots(xcoord, ycoord, height, width)) {
        requestShots(shotsRemaining);
        curShots = new ArrayList<>();
        i = -1;
      } else {
        Coord newShot = new Coord(xcoord, ycoord);
        curShots.add(newShot);
      }
    }
    return curShots;
  }
}
