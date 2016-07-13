///////////////////////////////////////////////////////////////
//
//  This is a class to read and store netflow data CSV files
//  within the last 5 minutes of the day and store in an
//  ArrayList<String[]>
//
//  Author:Sydney Lyon
//  Date Started: 7/1/2016
//  Last Worked: 7/12/2016
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
  
  public String newPath(){
    String out = "";
    String seperator = "\\";
    out += "C:\\Users\\sylyon\\Documents\\STITCHED";
    String date = path.substring(path.length() - 15);
    String year = date.substring(1,5);
    String month = date.substring(6,8);
    String day = date.substring(date.length() - 6);
    // changes to the appropriate path
    out += seperator + year + seperator + month + seperator;
    // changes to the appropriate file name
    out += year + "-" + month + "-" + day;
    
    return out;
  }
  
  
  public ArrayList<String[]> run(){
    ArrayList<String[]> store = new ArrayList<String[]>();
    try {
      // These are for storing our information
      File inFile = new File(path);
      File outFile = new File(newPath());
      outFile.getParentFile().mkdirs();
      PrintWriter pw = new PrintWriter(new FileWriter(outFile));
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
      br.close();
                  
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
    //System.out.println(this.testToString());
    return store;
  }    
    

  public static void main(String[] args){
    
    final long startTime = System.nanoTime();
    Reader test = new Reader("C:\\Users\\sylyon\\Documents\\Java\\Stitching\\2016\\01\\01.csv");
    System.out.println("" + test.run().size());
    System.out.println(test.testToString());
    System.out.println(test.newPath());
    final long duration = System.nanoTime() - startTime;
    System.out.println(duration/1000000000 + " sec");
    
  }
  
}
