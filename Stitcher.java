///////////////////////////////////////////////////////////////
//
//  This is a class determine if two flows should be stitched
//  together
//
//  Author:Sydney Lyon
//  Date Started: 7/5/2016
//  Last Worked: 7/6/2016
//
///////////////////////////////////////////////////////////////
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Stitcher{
  
  private ArrayList<String[]> firstList;
  private ArrayList<String[]> secondList;
  private Reader readerOne, readerTwo;
  // for CSV
  private static final String COMMA = ",";
  private static final String NEW_LINE = "\n";
  
  
  
  public Stitcher(String pathOne, String pathTwo){
    
    readerOne = new Reader(pathOne);
    readerTwo = new Reader(pathTwo);
    firstList = readerOne.run();
    secondList = readerTwo.run();
    
  }
  
  // compare contains two nested loops, based on our two arrayLists.  It iterates 
  // through and compares feilds from the coresponding String[] position with if 
  // statements to see if they match if statements are ordered based on how many flows
  // they rule out first int order to save computation in later if's
  public void compare(){
    String out = "";
    for(int i=0; i<firstList.size(); i++){
      for(int j=0; j<secondList.size(); j++){
        if(firstList.get(i)[3].equals(secondList.get(j)[3])){ //sIP
          if(firstList.get(i)[5].equals(secondList.get(j)[5])){ //sPort
            if(firstList.get(i)[4].equals(secondList.get(j)[4])){ //dIP
              if(firstList.get(i)[6].equals(secondList.get(j)[6])){ //dPort
                if(firstList.get(i)[1].substring(0,9).
                     equals(secondList.get(j)[0].substring(0,9))){ // date
                  secondList.get(j)[0] = firstList.get(i)[0];
                  // changes the byte total, adding both days in second's stats
                  secondList.get(j)[12] = addBytes(firstList.get(i),secondList.get(j));
                  
                  // takes out the now duplicate flow from first day
                  firstList.remove(i);
                  // moves index back so we don't miss any
                  if(i != 0)i--;
                }
              }
            }
          }
        }
      }
    }
  }
  
  
  // adds together the bytes column of our flow arrays
  public String addBytes(String[] a1, String[] a2){
    String out = "";
    double total = Double.parseDouble(a1[12]) + Double.parseDouble(a2[12]);
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(0);
    out += df.format(total);
    return out;
  }
  
  public String arrayToString(String[] input){
    String[] in = input;
    String out = "";
    int size = in.length;
    for(int i=0; i<=(size-1); i++){
      if(i==0){
        out += in[i];
      }else{
        out += COMMA + in[i];
      }
    }
    out += NEW_LINE;
    
    return out;
  }
  
  
  public static void main(String[] args){
    
    final long startTime = System.nanoTime();
    Stitcher test = new Stitcher
      ("C:\\Users\\sylyon\\Documents\\Java\\Stitching\\01.csv",
       "C:\\Users\\sylyon\\Documents\\Java\\Stitching\\02.csv");
    
    test.compare();
    //System.out.println(test.toString());    
    final long duration = System.nanoTime() - startTime;
    System.out.println("" + duration/1000000000 + " sec");
    
  }
  
  
  
}


  
  
  // ToDo: make a function that opens a file and adds flow
  // Should this be a seperate class?
  
  