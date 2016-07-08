///////////////////////////////////////////////////////////////
//
//  This is a class to read and store netflow data CSV files
//  within the last 5 minutes of the day and store in an
//  ArrayList<String[]>
//
//  Author:Sydney Lyon
//  Date Started: 7/1/2016
//  Last Worked: 7/5/2016
//
///////////////////////////////////////////////////////////////

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


public class Reader{
  
  private final String path;
  private String line = ""; 
  private BufferedReader br = null;
  private static final int COL1 = 11;  private static final int COL2 = 16;
  private static final int COL3 = 31;  private static final int COL4 = 36;
  // for CSV
  private static final String COMMA = ",";
  private static final String NEW_LINE = "\n";
  // test numbers
  int linesRead, linesSaved, linesWritten;
  
  
  public Reader(String in){
    linesRead = linesSaved = linesWritten = 0;
    path = in;
    
  }
  
  
  public String testToString(){
    String out= "----------------------------------------------\nReader Tests\n";
    out += "linesRead: " + linesRead + "\nlinesSaved: " + linesSaved + "\nlinesWritten: " + linesWritten;
    out += "\n----------------------------------------------";
    return out;
  }
  // Determines if the flow data needs to be saved
  // String should be whole flow entry from csv (one line)
  public boolean shouldSave(String in){
    
    //This is the line of the csv file we are currently checking
    String input = in;
    // start and stop variables for the loops
    int startMin = 5;
    int ceiling = 10;
    // creates an int array startMin long
    int[] index = new int[startMin];
    String time = "";
            
    // Determines if correct times are found and where, stores index values
    for(int i = startMin; i<ceiling; i++){
      // changes the minute from 5-9
      time = "23:5" + i;
      if(input.substring(COL1,COL2).equals(time) ||
         input.substring(COL3,COL4).equals(time))
        return true;
    } 
    
    return false;
  }
  
  
  
  
  public ArrayList<String[]> run(){
    ArrayList<String[]> store = new ArrayList<String[]>();
    try {
      // These are for storing our information
      File inFile = new File(path);
      File tempFile = new File(path + ".tmp");
      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
      if(!inFile.isFile()){
        System.out.println("No such File");
        return store;
      }
      // reads file and puts it into iterable BufferReader        
      br = new BufferedReader (new FileReader(path));
      while((line = br.readLine()) != null){
        // When correct times are found parse and store csv data in array
        linesRead++;
        if(shouldSave(line)){
          linesSaved++;
          // using comma as separator
          String[] flow = line.split(COMMA);
          store.add(flow);
        }else{
          // "line" is the current string in the bufferReader
          pw.println(line);
          pw.flush();
          linesWritten++;
        }
      }
      pw.close();
      br.close();
      
      // This function allows the temp file to be renamed, essentially deleting the
      // records of flows that need to be further analyzed and rewritten later ***************************************
      // inFile.delete();
      if(!tempFile.renameTo(inFile))
        System.out.println("Couldn't rename file");
      
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }catch(IOException e){
      e.printStackTrace();
    }finally{
      if(br != null){
        try {
          br.close();
        }catch (IOException e){
          e.printStackTrace();
        }
      }
    }
    return store;
  }    
    

  public static void main(String[] args){
    
    final long startTime = System.nanoTime();
    Reader test = new Reader("C:\\Users\\sylyon\\Documents\\Java\\Stitching\\01.csv");
    System.out.println("" + test.run().size());
    System.out.println(test.testToString());
    final long duration = System.nanoTime() - startTime;
    System.out.println(duration/1000000000 + " sec");
    
  }
  
}




/*
 * I need somthing in main file that will cycle through the different day files
 * and send them to the reader.  I'll need to account for the different number of
 * days in a month and for leap years. (if year is divisible by 4 but not 100)
 * 
 * 
 *    ///////////////// Tests: copy/paste into class //////////////////////////////////
 * 
    
  // tests are in bottom comments
  public String toString(){
    String out = "";
        
    // shouldSave() tests
    String test1 = "2015-11-30 23:59:14,2015-11-30 23:59:27,12.971,158.210.220.206,198.118.194.40,59381,62917,TCP,.A....,0,0,1800,100200,0,0,559,669,18127,1701";
    String test2 = "2015-12-01 23:59:11,2015-12-02 16:47:35,60504.134,128.252.155.29,163.221.156.231,33001,54679,UDP,......,0,0,827547850,1228001601800,0,0,669,559,25887,2500";
    String test3 = "2015-12-02 17:43:47,2015-12-02 20:54:58,11470.763,130.14.250.7,137.132.19.118,50062,33936,TCP,.A....,0,8,67345050,101017575000,0,0,669,559,70,7472";
    if(this.shouldSave(test1)){
      out += "true\n";
    }else{
      out += "false\n";
    }
    if(this.shouldSave(test2)){
      out += "true\n";
    }else{
      out += "false\n";
    }
    if(this.shouldSave(test3)){
      out += "true\n";
    }else{
      out += "false\n";
    }
    
    
    // test run()
    ArrayList<String[]> test = run();
    int testListSize = test.size();
    int testArraySize = 18;
    
    for(int i=0; i<testListSize; i++){
      for(int j=0; j<testArraySize; j++){
        if(j == 0){
          out += test.get(i)[j];
        }else{
          out += COMMA + test.get(i)[j];
        }
      }
      out += NEW_LINE;
    }
    
    
    return out;
  }
 * 
 */