package cs3500.pa04.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * utils class for serializing records
 */
public class JsonUtils {

  /**
   * method that serializes a passed in record
   *
   * @param record Record to serialize
   *
   * @return JsonNode the serialized record
   *
   * @throws IllegalArgumentException when record can't be serialized
   */
  public static JsonNode serializeRecord(Record record) throws IllegalArgumentException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("This record can not be serialized!");
    }
  }
}
