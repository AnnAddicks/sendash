package com.addicks.sendash.admin.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamToFile {

  public static File convert(InputStream inputStream, String fileType) {

    OutputStream outputStream = null;

    try {
      // write the inputStream to a FileOutputStream
      outputStream = new FileOutputStream(new File("./temp" + fileType));

      int read = 0;
      byte[] bytes = new byte[1024];

      while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }

      System.out.println("Done!");

    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (outputStream != null) {
        try {
          // outputStream.flush();
          outputStream.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }

      }
    }
    return new File("./temp" + fileType);
  }
}
