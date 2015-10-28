package com.addicks.sendash.admin.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonUtility {

  public static final String USER_JSON = "json/user.json";

  public static final String PENDING_ENPOINT_JSON = "json/pendingEndpoint.json";

  public static final String CLIENT_JSON = "json/client.json";

  public static <T> T loadObjectFromJson(String fileName, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
    ClassLoader classLoader = JsonUtility.class.getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    String path = file.getPath();

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(new File(path), clazz);
  }

  public static <T> T loadObjectFromString(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, clazz);
  }

  public static <T> List<T> loadObjectListFromString(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json,
        TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
  }
}
