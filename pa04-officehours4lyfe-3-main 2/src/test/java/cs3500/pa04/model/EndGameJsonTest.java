package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for the EndGameJson record
 */
public class EndGameJsonTest {
  EndGameJson gameWin;
  EndGameJson gameLose;
  EndGameJson gameDraw;

  /**
   * setup before each method
   */
  @BeforeEach
  void setup() {
    gameWin = new EndGameJson("WIN", "You won!");
    gameLose = new EndGameJson("LOSE", "You lost!");
    gameDraw = new EndGameJson("DRAW", "You tied!");
  }

  /**
   * tests the stringToResult method
   */
  @Test
  void stringToResultTest() {
    assertEquals(gameWin.stringToResult(), GameResult.WIN);
    assertEquals(gameLose.stringToResult(), GameResult.LOSE);
    assertEquals(gameDraw.stringToResult(), GameResult.DRAW);
  }
}
