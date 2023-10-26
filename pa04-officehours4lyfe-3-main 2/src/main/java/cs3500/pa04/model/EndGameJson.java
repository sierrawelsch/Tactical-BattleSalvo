package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the arguments of an end-game message from the server
 *
 * @param result the result of the game
 *
 * @param reason the reason the result of the game occured
 */
public record EndGameJson(
    @JsonProperty("result") String result,
    @JsonProperty("reason") String reason) {

  /**
   * tests that it can convert the string win, lose, or tie to its associted GameResult enum
   *
   * @return an enum that represents if the player won, lost, or tied with their opponent
   */
  public GameResult stringToResult() {
    if (result.equals("WIN")) {
      return GameResult.WIN;
    } else if (result.equals("LOSE")) {
      return GameResult.LOSE;
    }
    return GameResult.DRAW;
  }
}
