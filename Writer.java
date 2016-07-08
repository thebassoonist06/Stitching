///////////////////////////////////////////////////////////////
//
//  This is a class writes stitched results from array to a file
//
//  Author:Sydney Lyon
//  Date Started: 7/7/2016
//  Last Worked: 7/7/2016
//
///////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Writer{
  private String path;
  private ArrayList<String[]> list;
  private static final String NEW_LINE = "\n";
  private static final String COMMA = ",";
    
  public Writer(String path, ArrayList<String[]> list){
    this.path = path;
    this.list = list;
  }
  
  public void run(){
    
  }
  
  public String arrayToString(String[] input){
    String[] in = input;
    String out = "";
    int size = in.length;
    for(int i=0; i<size; i++){
      if(i==0){
        out += in[i];
      }else{
        out += COMMA + in[i];
      }
    }
    out += NEW_LINE;
  
    return out;
  }
  
  public String listToString(){
    String out= "";
    // ToDo: make a long string with the entire arrayList
    int len = list.size();
    for(int i=0; i<len; i++){
      out += arrayToString(list.get(i));
    }
    return out;
  }
  
  public void write(){
    FileWriter fw = null;
    BufferedWriter bw = null;
    PrintWriter out = null;
    String writeString = listToString();
    try {
        fw = new FileWriter(path + ".tmp", true);
        bw = new BufferedWriter(fw);
        out = new PrintWriter(bw);
        out.println(writeString);
        out.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    finally {

        if(out != null)
            out.close();
        
        try {
            if(bw != null)
                bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(fw != null)
                fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  }
  
  public static void main(String[] args){
    Reader test1 = new Reader("C:\\Users\\sylyon\\Documents\\Java\\Stitching\\01.csv");
    ArrayList<String[]> testList = test1.run();
    Writer test2 = new Writer("temp", testList);
    System.out.println(test2.listToString());
  }
}