package com.cs.test;

import java.util.Calendar;
import java.util.TimeZone;

public class Test {
  
  public static void main(String[] args)
  {
    testTimeZones();    
  }

  private static void     testTimeZones()
  {
    Calendar c = Calendar.getInstance();
    TimeZone tz = c.getTimeZone();

    System.out.println( tz.getDisplayName() + " ---> " + System.currentTimeMillis() );    
  }
  
}
