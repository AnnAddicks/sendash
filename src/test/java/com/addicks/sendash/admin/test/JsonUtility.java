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

  public static final String LOGIN_JSON = "json/login.json";

  public static final String ENDPOINT_JSON = "json/endpoint.json";

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

  public static byte[] readJsonFromFile(String fileName) throws Exception {
    ClassLoader classLoader = JsonUtility.class.getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());
    return toJson(file);
  }

  public static byte[] toJson(Object r) throws Exception {
    ObjectMapper map = new ObjectMapper();
    return map.writeValueAsString(r).getBytes();
  }
}
