package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for the driver class
 */
class DriverTest {
  Driver driver = new Driver();

  /**
   * tests the main method and that it doesn't throw an exception besides the expected
   * NoSuchElementException, and tests that an Illegal Argument
   * Exception is thrown if anything other than 2 or 0 arguments are given
   */
  @Test
  public void testMainExceptions() {
    try {
      driver.main(new String[] {});
    } catch (NoSuchElementException e) {
      assertThrows(NoSuchElementException.class, () -> Driver.main(new String[] {}));
    }
    String[] args = new String[3];
    args[0] = "0.0.0.0";
    args[1] = "35001";
    args[2] = "a";
    assertThrows(IllegalArgumentException.class, () -> Driver.main(args));
  }
}