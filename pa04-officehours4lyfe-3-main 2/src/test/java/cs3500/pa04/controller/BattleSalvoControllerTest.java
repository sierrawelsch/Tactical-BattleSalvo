package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa04.model.CheckInput;
import cs3500.pa04.view.ManualDataEntry;
import cs3500.pa04.view.UserViewer;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Represents a test for my BattleSalvoController class
 */
class BattleSalvoControllerTest {
  Random randForAi = new Random(5);
  Random randForUser = randForAi;

  Appendable output;
  Readable input;
  String userInput;
  UserViewer view;
  Scanner readInput;
  int height;
  int width;
  ManualDataEntry takeShotsData;
  CheckInput invalid;

  BattleSalvoController controller;


  /**
   * initializes all the fields to initialize a BattleSalvoController object
   * for a BattleSalvo game where the ai beats the user
   */
  void setupForAiWins() {
    output = new StringBuilder();
    userInput = "5 6 6 6 8 8 8 8 1 1 1 1 0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 "
        + "0 1 0 2 0 3 0 4 ";
    input = new StringReader(userInput);
    readInput = new Scanner(input);
    view = new UserViewer(output);
    width = 6;
    height = 6;
    takeShotsData = new ManualDataEntry(output, input, readInput);
    invalid = new CheckInput();
    controller = new BattleSalvoController(input, output, randForUser, randForAi);
  }


  /**
   * initializes all the fields to initialize a BattleSalvoController object
   * for a BattleSalvo game where the user beats the ai
   */
  void setupForUserWins() {
    output = new StringBuilder();
    userInput = "5 6 6 6 8 8 8 8 1 1 1 1 0 1 1 1 2 1 3 1 "
        + "4 1 5 1 5 2 5 3 "
        + "5 4 5 5 0 3 1 3 "
        + "2 3 3 3 5 0 2 5 "
        + "3 5 4 5 0 1 1 1 "
        + "3 5 4 5 0 1 1 1 ";
    input = new StringReader(userInput);
    readInput = new Scanner(input);
    view = new UserViewer(output);
    width = 6;
    height = 6;
    takeShotsData = new ManualDataEntry(output, input, readInput);
    invalid = new CheckInput();
    controller = new BattleSalvoController(input, output, randForUser, randForAi);
  }

  /**
   * tests playing a game of BattleSalvo
   * and that all the hits and misses are displayed correctly
   * and that the ai wins when they hit all the user's ships
   */
  @Test
  void runForWhenAiWins() {
    setupForAiWins();
    try {
      controller.run();
    } catch (IOException e) {
      fail();
    }
    assertEquals(output.toString(), "Welcome to BattleSalvo: a similar version of Battleship!\n"
        + "Please enter a valid height and width each in the range (6, 15) below:\n"
        + "You've entered invalid dimensions. "
        + "The height and width of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 6\n"
        + "You've entered invalid fleet sizes.\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 6\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + ". D . . C B \n"
        + ". D . . C B \n"
        + ". D . . C B \n"
        + "S D . . C B \n"
        + "S . . . C B \n"
        + "S . . . C . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + ". D . . + B \n"
        + ". D . . + B \n"
        + ". D . . C + \n"
        + "S D . . C B \n"
        + "S . . . C B \n"
        + "S . . . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . . C + \n"
        + "S D . . C B \n"
        + "S - . . C B \n"
        + "S . . . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . . + + \n"
        + "S D . . C + \n"
        + "+ - . - C B \n"
        + "S . . . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . - + + \n"
        + "S D . - C + \n"
        + "+ - . - C B \n"
        + "+ . - . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D - - + B \n"
        + ". + - - + B \n"
        + ". D . - + + \n"
        + "S D . - C + \n"
        + "+ - . - C B \n"
        + "+ . - - + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D - - + B \n"
        + ". + - - + + \n"
        + ". D . - + + \n"
        + "+ D - - C + \n"
        + "+ - . - C B \n"
        + "+ - - - + . \n"
        + "Please enter 3 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D - - + B \n"
        + ". + - - + + \n"
        + ". D - - + + \n"
        + "+ D - - C + \n"
        + "+ - - - + + \n"
        + "+ - - - + . \n"
        + "Please enter 3 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D - - + + \n"
        + ". + - - + + \n"
        + "- D - - + + \n"
        + "+ + - - + + \n"
        + "+ - - - + + \n"
        + "+ - - - + . \n"
        + "Please enter 1 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + "+ . . . . . \n"
        + "- . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- + - - + + \n"
        + "- + - - + + \n"
        + "- + - - + + \n"
        + "+ + - - + + \n"
        + "+ - - - + + \n"
        + "+ - - - + - \n"
        + "Game Over! The ai sunk all of your ships :(\n");
  }

  /**
   * tests playing a game of BattleSalvo
   * and that all the hits and misses are displayed correctly
   * and that the ai wins when they hit all the user's ships
   */
  @Test
  void runForWhenUserWins() {
    setupForUserWins();
    try {
      controller.run();
    } catch (IOException e) {
      fail();
    }
    assertEquals(output.toString(), "Welcome to BattleSalvo: a similar version of Battleship!\n"
        + "Please enter a valid height and width each in the range (6, 15) below:\n"
        + "You've entered invalid dimensions. "
        + "The height and width of the game must be in the range (6, 15), inclusive. Try again!\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 6\n"
        + "You've entered invalid fleet sizes.\n"
        + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 6\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + ". D . . C B \n"
        + ". D . . C B \n"
        + ". D . . C B \n"
        + "S D . . C B \n"
        + "S . . . C B \n"
        + "S . . . C . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ + + + . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + ". D . . + B \n"
        + ". D . . + B \n"
        + ". D . . C + \n"
        + "S D . . C B \n"
        + "S . . . C B \n"
        + "S . . . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ + + + + + \n"
        + ". . . . . + \n"
        + ". . . . . + \n"
        + ". . . . . . \n"
        + ". . . . . . \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . . C + \n"
        + "S D . . C B \n"
        + "S - . . C B \n"
        + "S . . . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . . \n"
        + "+ + + + + + \n"
        + ". . . . . + \n"
        + "+ + . . . + \n"
        + ". . . . . + \n"
        + ". . . . . + \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . . + + \n"
        + "S D . . C + \n"
        + "S - . - C B \n"
        + "S . . . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . + \n"
        + "+ + + + + + \n"
        + ". . . . . + \n"
        + "+ + + + . + \n"
        + ". . . . . + \n"
        + ". . + . . + \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . . + + \n"
        + "S D . - C + \n"
        + "+ - . - C B \n"
        + "S . - . + . \n"
        + "Please enter 4 shots:\n"
        + "Opponent Board Data: \n"
        + ". . . . . + \n"
        + "+ + + + + + \n"
        + ". . . . . + \n"
        + "+ + + + . + \n"
        + ". . . . . + \n"
        + ". . + + + + \n"
        + "Your Board:\n"
        + "- D . - + B \n"
        + ". D - . + B \n"
        + ". D . . + + \n"
        + "S D . - C + \n"
        + "+ - . - C B \n"
        + "+ . - . + . \n"
        + "Please enter 4 shots:\n"
        + "Game Over! You sunk all of the ai ships :)\n");
  }
}