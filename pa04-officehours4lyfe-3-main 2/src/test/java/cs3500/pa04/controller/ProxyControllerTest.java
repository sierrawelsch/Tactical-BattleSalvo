package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import cs3500.pa04.model.Board;
import cs3500.pa04.model.ComputerPlayer;
import cs3500.pa04.model.CoordJson;
import cs3500.pa04.model.CoordinatesJson;
import cs3500.pa04.model.EndGameJson;
import cs3500.pa04.model.JsonUtils;
import cs3500.pa04.model.MessageJson;
import cs3500.pa04.model.Mocket;
import cs3500.pa04.model.OtherBoard;
import cs3500.pa04.model.SetupJson;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.model.ThisBoard;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests for the ProxyController class
 */
class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController dealer;
  private Random rand;
  private Board myBoard;
  private Board opponentBoard;
  private Map<String, Integer> fleetSpecJson;
  private Map<ShipType, Integer> fleetSpec;
  private JsonNode emptyArgs;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    rand = new Random(5);
    myBoard = new ThisBoard(new ArrayList<>());
    opponentBoard = new OtherBoard();
    fleetSpecJson = new LinkedHashMap<>();
    fleetSpecJson.put("CARRIER", 1);
    fleetSpecJson.put("BATTLESHIP", 1);
    fleetSpecJson.put("DESTROYER", 1);
    fleetSpecJson.put("SUBMARINE", 1);
    fleetSpec = new LinkedHashMap<>();
    fleetSpec.put(ShipType.Carrier, 1);
    fleetSpec.put(ShipType.Battleship, 1);
    fleetSpec.put(ShipType.Destroyer, 1);
    fleetSpec.put(ShipType.Submarine, 1);
    emptyArgs = JsonNodeFactory.instance.objectNode();
  }

  /**
   * tests for handling the join request from the server
   */
  @Test
  void testForJoin() {
    // Prepare sample message
    MessageJson joinRequest = new MessageJson("join", emptyArgs);
    JsonNode sampleMessage = JsonUtils.serializeRecord(joinRequest);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    this.dealer = new ProxyController(socket, new ComputerPlayer(rand, myBoard, opponentBoard));

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"join\",\"arguments\":{\"name\":\"sierrawelsch\","
           + "\"game-type\":\"SINGLE\"}}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for the handling the setup request from the server
   */
  @Test
  void testForSetup() {
    // Prepare sample message
    SetupJson setupArgs = new SetupJson(6, 6, fleetSpecJson);
    JsonNode sampleMessage = createSampleMessage("setup", setupArgs);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    this.dealer = new ProxyController(socket, new ComputerPlayer(rand, myBoard, opponentBoard));

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":4,\"y\":0},"
            + "\"length\":6,\"direction\""
           + ":\"VERTICAL\"},{\"coord\":{\"x\":5,\"y\":0},\"length\":5,"
           + "\"direction\":\"VERTICAL\"},{\"coord\":"
            + "{\"x\":1,\"y\":0},\"length\":4,\"direction\":"
           + "\"VERTICAL\"},{\"coord\":{\"x\":0,\"y\":3},\"length\":3,"
           + "\"direction\":\"VERTICAL\"}]}}"
            + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for the handling the take-shots request from the server
   */
  @Test
  void testForTakeShots() {
    // Prepare sample message
    MessageJson takeShotsRequest = new MessageJson("take-shots", emptyArgs);
    JsonNode sampleMessage = JsonUtils.serializeRecord(takeShotsRequest);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    ComputerPlayer ai = new ComputerPlayer(rand, myBoard, opponentBoard);

    // Create a Dealer
    this.dealer = new ProxyController(socket, ai);
    ai.setup(6, 6, fleetSpec);
    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":2,\"y\":4},{\"x\""
            + ":4,\"y\":5},{\"x\":3,\"y\":1},{\"x\":5,\"y\":4}]}}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for handling the report-damage request from the server
   */
  @Test
  void testForReportDamage() {
    // Prepare sample message
    CoordJson[] opponentsShots = new CoordJson[4];
    opponentsShots[0] = new CoordJson(4, 3);
    opponentsShots[1] = new CoordJson(2, 1);
    opponentsShots[2] = new CoordJson(4, 5);
    opponentsShots[3] = new CoordJson(3, 3);
    CoordinatesJson reportDamageArgs = new CoordinatesJson(opponentsShots);
    JsonNode sampleMessage = createSampleMessage("report-damage", reportDamageArgs);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    ComputerPlayer ai = new ComputerPlayer(rand, myBoard, opponentBoard);
    this.dealer = new ProxyController(socket, ai);
    ai.setup(6, 6, fleetSpec);

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":"
           + "{\"coordinates\":[{\"x\":4,\"y\":3},{\"x\":4,\"y\":5}]}}"
         + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for handling the successful-hits request from the server
   */
  @Test
  void testForSuccessfulHits() {
    // Prepare sample message
    CoordJson[] yourSuccessfulShots = new CoordJson[2];
    yourSuccessfulShots[0] = new CoordJson(2, 1);
    yourSuccessfulShots[1] = new CoordJson(3, 3);
    CoordinatesJson successfulHitsArgs = new CoordinatesJson(yourSuccessfulShots);
    JsonNode sampleMessage = createSampleMessage("successful-hits", successfulHitsArgs);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    ComputerPlayer ai = new ComputerPlayer(rand, myBoard, opponentBoard);
    this.dealer = new ProxyController(socket, ai);
    ai.setup(6, 6, fleetSpec);

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"successful-hits\",\"arguments\":\"{}\"}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for handling the end-game request from
   * the server when the game ends in a win
   */
  @Test
  void testForEndGameWin() {
    // Prepare sample message
    EndGameJson endGameArgs = new EndGameJson("WIN", "You won!");
    JsonNode sampleMessage = createSampleMessage("end-game", endGameArgs);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    ComputerPlayer ai = new ComputerPlayer(rand, myBoard, opponentBoard);
    this.dealer = new ProxyController(socket, ai);

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"end-game\",\"arguments\":\"{}\"}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for handling the end-game request from
   * the server when the game ends in a loss
   */
  @Test
  void testForEndGameLost() {
    // Prepare sample message
    EndGameJson endGameArgs = new EndGameJson("LOST", "You lost!");
    JsonNode sampleMessage = createSampleMessage("end-game", endGameArgs);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    ComputerPlayer ai = new ComputerPlayer(rand, myBoard, opponentBoard);
    this.dealer = new ProxyController(socket, ai);
    // ai.setup(6, 6, fleetSpec);

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"end-game\",\"arguments\":\"{}\"}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * tests for handling the end-game request from
   * the server when the game ends in a draw
   */
  @Test
  void testForEndGameDraw() {
    // Prepare sample message
    EndGameJson endGameArgs = new EndGameJson("DRAW", "You tied!");
    JsonNode sampleMessage = createSampleMessage("end-game", endGameArgs);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    ComputerPlayer ai = new ComputerPlayer(rand, myBoard, opponentBoard);
    this.dealer = new ProxyController(socket, ai);
    // ai.setup(6, 6, fleetSpec);

    // run the dealer and verify the response
    this.dealer.run();

    String expected =
        "{\"method-name\":\"end-game\",\"arguments\":\"{}\"}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   *
   * @param messageObject object to embed in a message json
   *
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}