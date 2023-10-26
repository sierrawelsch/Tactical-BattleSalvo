package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a list of ships in Json format
 *
 * @param fleet a list of ships in Json format
 */
public record FleetJson(
    @JsonProperty("fleet") ShipJson[] fleet) {
}
