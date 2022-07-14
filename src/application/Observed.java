package application;

import java.io.File;
import java.util.Arrays;

import javafx.scene.shape.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.*;

public class Observed {
	private String []compareable;
	private OFile file;
	private ODirectory dir;
	private OUrl url;
	private Observed obs;
	
	public void renew() {};
	public String[] getCompareable() {
		return compareable;
	}
	
	public Observed() {
		//obs=new OFile("C:/Users/Student/Pictures/n.txt");		
	}
	
	public void setFile(String inc) {
		//obs=new OFile(inc);
		obs = new OUrl(inc);
	}
	
	
		class OFile extends Observed{
			 String fileName;
			 long fileSize; 
		        
		     public void renew() {
		    	 size();
		    	 buildComp();
		     }
			 
			 OFile(String fileName){
		    	  this.fileName = fileName;
		    	  size();
		      }
		      
		      public void buildComp() {
		    	  compareable = new String[] {""+fileSize,};
		      }
		      
		      private void size() {
		    	  java.nio.file.Path path = Paths.get(fileName);
		    	  
		          try {
		              fileSize = Files.size(path);
		              System.out.println(String.format("%,d bytes", fileSize));
		              System.out.println(String.format("%,d kilobytes", fileSize / 1024));
		          } catch (IOException e) {
		              e.printStackTrace();
		          }
		      }
			}
	
			class ODirectory extends Observed {
				File observedDir;
				public void renew() {
					
					compareable = observedDir.list();	
					Arrays.sort(compareable);
				}
			
				ODirectory(String dir){
					observedDir = new File(dir);
					compareable = observedDir.list();	
					Arrays.sort(compareable);	
				}
			}
	
			class OUrl extends Observed{
				private String Url;
				private String tempDir = "c:/tmp";
				
				
				OUrl(String obs_URL){
					File tmp = new File(tempDir);
					tmp.mkdir();
					try {
						URL url = new URL(obs_URL);
						File gg = tmp.createTempFile("Dr_", "Ecksack");
						int f = 'a';
						//System.out.println(url.openStream());
					} catch (IOException e) {
						System.out.println("connection failed");
						e.printStackTrace();
					}
					
				}
			
			}
}
