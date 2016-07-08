///////////////////////////////////////////////////////////////
//
//  This is a class runs through the files in a directory and
//  stitches them together
//
//  Author:Sydney Lyon
//  Date Started: 7/7/2016
//  Last Worked: 7/7/2016
//
///////////////////////////////////////////////////////////////

import java.io.File;
  
public class FileWalker{
  private String path;
  private int days;
  public FileWalker(String path){
    this.path = path;
    //this.days = days;
  }
//  public String changeDay(){
//    
//  }
//  public String changePath(){
//    
//  }
  
  public void run(){
    
    File dir = new File(path);
    File[] directoryListing = dir.listFiles();
    File previous = null;
    
    if (directoryListing != null) {
      for (File child : directoryListing) {
        if(child == null){
          
        }else{
          //System.out.println(child);
        }
      }
    } else {
      System.out.println("failed");
      // Handle the case where dir is not really a directory.
      // Checking dir.isDirectory() above would not be sufficient
      // to avoid race conditions with another process that deletes
      // directories.
    }
    
    
  }
  
  public static void main(String[] args){
    FileWalker test = new FileWalker("C:\\Users\\sylyon\\Documents\\Java\\Stitching");
    test.run();
  }

}