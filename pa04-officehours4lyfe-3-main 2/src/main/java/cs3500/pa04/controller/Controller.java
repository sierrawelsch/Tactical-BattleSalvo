package cs3500.pa04.controller;

import java.io.IOException;

/**
 * Represents the controller in terms of MVC
 */
public interface Controller {
  /**
   * runs the entire program and essentially allows the functionality of Battle Salvo
   *
   * @throws IOException if an error occurs
   */
  void run() throws IOException;
}
