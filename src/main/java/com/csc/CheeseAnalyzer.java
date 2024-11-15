package com.csc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

public class CheeseAnalyzer {
  // You can put source code here
    public static ArrayList<String> checkQuotes(String[] arr){
      ArrayList<String> result = new ArrayList<String>();
      boolean isQuote = false;
      boolean isQuote2 = false;
      String str;
      String temp ="";

      int len = arr.length;
      int count = 0;

      for(int i = 0; i < len; i++){
        str = arr[i];
        
        if(str.contains("\"") && isQuote == false){
          isQuote = true;
          temp = str;
          for(int j = 0; j < temp.length(); j++){
            if(temp.charAt(j) == '"') count++;
          }
          
          if(count % 2 == 0){
            
            isQuote = false;
            result.add(temp);
          }

        }
        else if(str.contains("\"") && isQuote == true){
          temp = temp + str;
          count = 0;
          for(int j = 0; j < temp.length(); j++){
            if(temp.charAt(j) == '"') count++;
          }
          if(count % 2 == 0){
            
            isQuote = false;
            result.add(temp);
          }

        }
        else if(isQuote == true){
          temp = temp + str;
        }
        else {
          result.add(str);
          // System.out.println(result.getLast());
        }
      }
      return result;
    }

    /*
     * a = cow      b = goat    c = ewe      d = buffalo
     */


    public static String checkPopularAnimal(int cowNum, int goatNum, int eweNum, int buffaloNum)
    {
      if ((cowNum >= goatNum) && (cowNum >= eweNum) && (cowNum >= buffaloNum)) { // a >= b,c,d,
        return "Cows";
      } else if ((goatNum >= eweNum) && (goatNum >= buffaloNum)) {      // b >= c,d,e
          return "Goats";
      } else if ((eweNum >= buffaloNum)) {                  // c >= d,e
          return "Ewes";
      }  else {                                            // e > d
          return "Buffalos" + buffaloNum;
      }
    }


    //Create and write to a file called output.txt
    public static void writeFile(String text, String fileName)
    {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(text);
        writer.close();
        
      } catch (IOException e) {
        // TODO: handle exception
        System.out.println("Could not write to the file caled output.txt");
      }
    }




    public static void main(String[] args) {
      String[] dataArr;

      try {
        BufferedReader reader = new BufferedReader(new FileReader("cheese_data.csv"));
        String str = "";
        int pasteurizedNum = 0;
        String pastuerizedString = "";

        int rawMilkNum = 0;
        String rawMilkString = "";

        int moistPercentNum = 0; //Counts for Moisture Percentage over 41%
        String moisPercentString = "";

        double moistPercentSum = 0;
        int moistPercentAmount = 0;
        double moistPercentAvg = 0;
        String moistPercAvgString = "";

        int cowNum = 0;
        int goatNum = 0;
        int eweNum = 0;
        int buffaloNum = 0;
        int lacticNum = 0;

        String popularAnimal = "";
        String popularAnimalString = "";
        String lacticString = "";

        String finalString;
        String withoutHeadersString;

        //Read the csv file line by line
        while ((str = reader.readLine()) != null)
        {
          dataArr = str.split(",");

          ArrayList<String> data = checkQuotes(dataArr);
          ArrayList<String> dataWithoutHeaders = new ArrayList<>();

          //Check and fill empty spaces with null for missing entries
          for(int i = 0; i < data.size()-1; i++)
          {

            if (data.get(i) == "") {
              data.set(i, null);
            }
            if (data.get(i) != null) {
              data.set(i, data.get(i).trim());
            }

            if(!data.get(0).equals("CheeseId"))
            {
              dataWithoutHeaders = data;
            }

          }

          

          //Calculate the num of Pasteurized and Raw Milk in cheeses
          if (data.get(9) != null && data.get(9).equals("Pasteurized")  ) {
            pasteurizedNum++;
          }
          else if (data.get(9) != null && data.get(9).contains("Raw Milk")) {
            rawMilkNum++;
          }

          

          //Calculate Organic cheese with Moisture Percentage over 41%
          try {   
            if (!data.get(3).equals("MoisturePercent") && data.get(6) != null) { 
              double moisturePercent = Double.parseDouble(data.get(3));
            
            
              if (data.get(6).equals("1") && moisturePercent > 41) {
                moistPercentNum++;
              }
            }

            //calculate average moist percentage
            if (data.get(3) != null) {
              moistPercentAmount++;
              double currentPercentage = (Double.parseDouble(data.get(3)))/100;
              moistPercentSum += currentPercentage;
            }

             //Also checks and calculates the MilkTypeEn animals
            if (data.get(8).contains("Cow")) {
              cowNum++;
            }
            if (data.get(8).contains("Ewe")) {
              eweNum++;
            }
            if (data.get(8).contains("Goat")) {
              goatNum++;
            }
            if (data.get(8).contains("Buffalo")) {
              buffaloNum++;
            }

            if (data.get(4).contains("lactic") || data.get(4).contains("Lactic")) {
              lacticNum++;
            }

         } catch (NumberFormatException | NullPointerException e) {
         }
        }
        
        DecimalFormat df = new DecimalFormat("#.0");
        moistPercentAvg = (moistPercentSum)/moistPercentAmount*100;
        
        pastuerizedString = "The amount of cheeses that use Pasteriuzed Milk equal to: " + pasteurizedNum;
        rawMilkString = "The amount of cheeses that use Raw Milk equal to: " + rawMilkNum;
        moisPercentString = "The amount of organic cheeses with a moisture percent over 41% equal to: " + moistPercentNum;
        popularAnimal = checkPopularAnimal(cowNum, goatNum, eweNum, buffaloNum);
        popularAnimalString = "Most of the cheeses in Canada come from: " + popularAnimal;
        lacticString = "The amount of cheeses that are described as 'lactic' equal to: " + lacticNum;
        moistPercAvgString = "The average moisture percentage of all cheeses is: " + df.format(moistPercentAvg) + "%";
        finalString = pastuerizedString + "\n" + rawMilkString + "\n" + moisPercentString + "\n" + popularAnimalString
        + "\n" + moistPercAvgString + "\n" + lacticString; 
        System.out.println(finalString);

        //Creates and writes to a file called output.txt
        writeFile(finalString, "output.txt");
        reader.close();

      } catch (IOException e) {
        // TODO: handle exception
        System.out.println("Could not read the file.");
      }
    }
}
