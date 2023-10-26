package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.CoordJson;
import cs3500.pa04.model.CoordinatesJson;
import cs3500.pa04.model.EndGameJson;
import cs3500.pa04.model.FleetJson;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.GameType;
import cs3500.pa04.model.JoinJson;
import cs3500.pa04.model.JsonUtils;
import cs3500.pa04.model.MessageJson;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.SetupJson;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController {

  private final Socket server;
  private InputStream in;
  private PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("{}");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   */
  public ProxyController(Socket server, Player player) {
    this.server = server;
    try {
      this.in = server.getInputStream();
    } catch (IOException e) {
      System.out.println("Unexpected error");
    }
    try {
      this.out = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      System.out.println("Unexpected error");
    }
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
      server.close();
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else {
      handleEndGame(arguments);
    }
  }

  /**
   * handles the join request from the server by parsing in
   * JsonNode arguments and setting the game mode and sending a
   * serialized response to the server
   */
  private void handleJoin() {
    String name = this.player.name();
    String gameType = GameType.SINGLE.toString();
    JoinJson response = new JoinJson(name, gameType);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson actual = new MessageJson("join", jsonResponse);
    JsonNode response1 = JsonUtils.serializeRecord(actual);
    this.out.println(response1);
  }

  /**
   * handles the setup request from the server by parsing in arguments
   * and converting it to the fields used by the program to perform AI setup
   * and send a serialized response back to the server
   *
   * @param arguments JsonNode the Json representation of a MessageJson
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    List<Ship> ships =
        this.player.setup(setupArgs.height(), setupArgs.width(), setupArgs.fleetSpecToHashmap());
    ShipJson[] shipsToJson = new ShipJson[ships.size()];
    for (int index = 0; index < ships.size(); index++) {
      shipsToJson[index] = ships.get(index).shipToJson();
    }

    FleetJson response = new FleetJson(shipsToJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson actual = new MessageJson("setup", jsonResponse);
    JsonNode response1 = JsonUtils.serializeRecord(actual);
    this.out.println(response1);
  }

  /**
   * handles the take-shots request from the server by parsing in arguments
   * and converting it to the fields used by the program to take shots
   * and send a serialized list of coordinates back to the server
   *
   */
  private void handleTakeShots() {
    List<Coord> listOfShotsCoords = this.player.takeShots();
    CoordJson[] shotsToJson = listOfCoordsToJson(listOfShotsCoords);
    CoordinatesJson response = new CoordinatesJson(shotsToJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson actual = new MessageJson("take-shots", jsonResponse);
    JsonNode response1 = JsonUtils.serializeRecord(actual);
    this.out.println(response1);
  }

  /**
   * handles the report-damage request from the server by parsing in arguments
   * and converting it to the fields used by the program to report damages
   * caused by the opponent and send a serialized list of coordinates back
   * to the server
   *
   * @param arguments JsonNode the Json representation of a MessageJson
   */
  private void handleReportDamage(JsonNode arguments) {
    CoordinatesJson reportDamageArgs = this.mapper.convertValue(arguments, CoordinatesJson.class);
    List<Coord> opponentsShots = reportDamageArgs.jsonToCoordList();
    List<Coord> damages;
    damages = this.player.reportDamage(opponentsShots);
    CoordJson[] damagesToJson = listOfCoordsToJson(damages);

    CoordinatesJson response = new CoordinatesJson(damagesToJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson actual = new MessageJson("report-damage", jsonResponse);
    JsonNode response1 = JsonUtils.serializeRecord(actual);
    this.out.println(response1);
  }

  /**
   * converts a list of coordinates into a CoordJson array
   *
   * @param coordList list of coord to convert to an array of CoordJson
   *
   * @return CoordJson[] array used as an argument passed into the server
   */
  private CoordJson[] listOfCoordsToJson(List<Coord> coordList) {
    CoordJson[] coordinatesAsJson = new CoordJson[coordList.size()];
    for (int i = 0; i < coordList.size(); i++) {
      coordinatesAsJson[i] = coordList.get(i).coordToJson();
    }
    return coordinatesAsJson;
  }

  /**
   * handles the successful-hits request from the serverby parsing in arguments
   * and converting it to the fields used by the program to handle successful hits
   * and send back a void response in a MessageJson to the server
   *
   * @param arguments JsonNode the Json representation of a MessageJson
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    CoordinatesJson successfulHitsArgs = this.mapper.convertValue(arguments, CoordinatesJson.class);
    this.player.successfulHits(successfulHitsArgs.jsonToCoordList());
    MessageJson actual = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode response1 = JsonUtils.serializeRecord(actual);
    this.out.println(response1);
  }

  /**
   * handles the end-game request from the server and converting it
   *
   * @param arguments JsonNode the Json representation of a MessageJson
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endgameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    GameResult result = endgameArgs.stringToResult();
    String reason = endgameArgs.reason();
    this.player.endGame(result, reason);
    MessageJson actual = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode response1 = JsonUtils.serializeRecord(actual);
    this.out.println(response1);
  }
}
