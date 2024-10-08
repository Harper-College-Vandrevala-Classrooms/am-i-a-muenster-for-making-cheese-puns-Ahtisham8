package com.csc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CheeseAnalyzer {
  // You can put source code here
    public static void main(String[] args) {
      


      try {
        BufferedReader reader = new BufferedReader(new FileReader("cheese_data.csv"));
        System.out.println(reader.readLine());
        
      } catch (IOException e) {
        // TODO: handle exception
        System.out.println("Could not read the file.");
      }
      
    }
}
