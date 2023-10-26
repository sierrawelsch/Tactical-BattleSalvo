package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the arguments of a join message from the server
 *
 * @param name the name of the player
 *
 * @param type the type of game the user wants to play (single or multi)
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String type) {
}
