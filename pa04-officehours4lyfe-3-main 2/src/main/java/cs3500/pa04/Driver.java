package cs3500.pa04;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.ComputerPlayer;
import cs3500.pa04.model.OtherBoard;
import cs3500.pa04.model.ThisBoard;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {


  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    Random rand = new Random();
    Board myBoard = new ThisBoard(new ArrayList<>());
    Board opponentBoard = new OtherBoard();
    ProxyController proxyDealer =
        new ProxyController(server, new ComputerPlayer(rand, myBoard, opponentBoard));
    proxyDealer.run();
  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    //ProxyController delegation
    if (args.length == 2) { //exception when server host and port are not what we want
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      try {
        Driver.runClient(host, port);
      } catch (IOException e) {
        System.out.println("Cannot connect to the server \n:( \n Please try again!");
      }
    } else if (args.length == 0) { //BattleSalvo Controller delegation
      Readable input = new InputStreamReader(System.in);
      Appendable output = new PrintStream(System.out);
      Random randForUser = new Random();
      Random randForAi = new Random();
      BattleSalvoController controller =
          new BattleSalvoController(input, output, randForUser, randForAi);
      try {
        controller.run();
      } catch (IOException e) {
        System.out.println("Ran into an unexpected error!");
      }
    } else {
      throw new IllegalArgumentException("You have entered an invalid number of arguments");
    }
  }
}