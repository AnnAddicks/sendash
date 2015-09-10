package com.addicks.sendash.admin.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipCompare {

  public static boolean filesEqual(ZipFile file1, ZipFile file2) {

    Set<String> set1 = new LinkedHashSet<>();
    for (Enumeration e = file1.entries(); e.hasMoreElements();)
      set1.add(((ZipEntry) e.nextElement()).getName());

    Set<String> set2 = new LinkedHashSet<>();
    for (Enumeration e = file2.entries(); e.hasMoreElements();)
      set2.add(((ZipEntry) e.nextElement()).getName());

    int errcount = 0;

    int filecount = 0;
    for (Iterator<String> i = set1.iterator(); i.hasNext();) {
      String name = i.next();
      if (!set2.contains(name)) {
        System.out.println(name + " not found in " + file2);
        errcount += 1;
        continue;
      }
      try {
        set2.remove(name);
        if (!streamsEqual(file1.getInputStream(file1.getEntry(name)),
            file2.getInputStream(file2.getEntry(name)))) {
          System.out.println(name + " does not match");
          errcount += 1;
          continue;
        }
      }
      catch (Exception e) {
        System.out.println(name + ": IO Error " + e);
        e.printStackTrace();
        errcount += 1;
        continue;
      }
      filecount += 1;
    }
    for (Iterator i = set2.iterator(); i.hasNext();) {
      String name = (String) i.next();
      System.out.println(name + " not found in " + file1);
      errcount += 1;
    }
    System.out.println(filecount + " entries matched");
    if (errcount > 0) {
      return false;
    }
    return true;

  }

  public static boolean streamsEqual(InputStream stream1, InputStream stream2) throws IOException {
    byte[] buf1 = new byte[4096];
    byte[] buf2 = new byte[4096];
    boolean done1 = false;
    boolean done2 = false;

    try {
      while (!done1) {
        int off1 = 0;
        int off2 = 0;

        while (off1 < buf1.length) {
          int count = stream1.read(buf1, off1, buf1.length - off1);
          if (count < 0) {
            done1 = true;
            break;
          }
          off1 += count;
        }
        while (off2 < buf2.length) {
          int count = stream2.read(buf2, off2, buf2.length - off2);
          if (count < 0) {
            done2 = true;
            break;
          }
          off2 += count;
        }
        if (off1 != off2 || done1 != done2)
          return false;
        for (int i = 0; i < off1; i++) {
          if (buf1[i] != buf2[i])
            return false;
        }
      }
      return true;
    }
    finally {
      stream1.close();
      stream2.close();
    }
  }

}
