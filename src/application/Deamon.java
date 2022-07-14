package application;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



public class Deamon {
	private String[] lastScan;
	private Observed obs;
	private File observedDir; //make observed object. Should return arrayList<String>
	private Instant lastExecTime= Instant.now();
	private ArrayList<String> addedElements = new ArrayList<String>();
	private ArrayList<String> removedElements = new ArrayList<String>();
	private ArrayList<Condition> conditions = new ArrayList<Condition>();
	private String id;
	
	public void addCondition(Condition con) {
		conditions.add(con);
	}
	
	public void setID(String id) {
		this.id=id;
	}
		
	public String getAdded(int index) {
		return addedElements.toArray(new String[addedElements.size()])[index];
	}
	
	public String[] getAdded() {
		String[] add = new String[addedElements.size()];
		addedElements.toArray(add);
		return 	add;
	}
	
	public String getRemoved(int index) {			
		return 	removedElements.toArray(new String[removedElements.size()])[index];	
	}
	
	public String[] getRemoved() {			
		return 	removedElements.toArray(new String[removedElements.size()]);	
	}
	
	public void clearAdd() {
		addedElements.clear();
	}
	public void clearRemoved() {
		removedElements.clear();
	}
	
	
	public boolean reFresh() {
		clearAdd();
		clearRemoved();
		String[] newScan = observedDir.list();
		Arrays.sort(newScan);
		if (equal(newScan)) {
			return true;			
		}else {
			listChanges(newScan);
			lastScan=newScan;
		}	
		return false;	
	}
	
	//compare elements of given StringArry compared to last scan, return true if equal
	private boolean equal(String[] temp) {
		if(temp.length!=lastScan.length) {
			return false;
		}
		for (int i=0;i<temp.length;i++) {
			if(!(temp[i].equals(lastScan[i]))) {			
				return false;
			}
		}		
		return true;		
	}
	

	
	
	//returns new/removed elements of a given StringArry compared to last scan saved to local field 
	//results are written to added/removed local Arraylists
	public void listChanges(String[] temp) {	
		for(int n=0;n<2;n++) {
			String[]arr1 = n==0? temp:lastScan;
			String[]arr2 = n==0? lastScan: temp;						
			for (int i =0; i<arr1.length;i++) {
				boolean changed = true;
				for(int j= 0;j<arr2.length;j++) {
					if (arr1[i].equals(arr2[j])) {
						j=arr2.length;
						changed=false;
					}
				}
				if(changed) {
					if(n==0) {
						addedElements.add(arr1[i]);
					}else {
						removedElements.add(arr1[i]);
					}
				}
			}
		}
	}
		// todo dir => object
	public Deamon(String dir) {	
		obs = new Observed();
		//obs.setFile("C:/Users/Student/Pictures/n.txt");	
		obs.setFile("http://baeldung.com");
		
		
		
//		observedDir = new File(dir);
//		lastScan = observedDir.list();	
//		Arrays.sort(lastScan);	
	}
	
	public void print() {
		for(int i=0;i<addedElements.size();i++) {
			System.out.println("added: "+addedElements.get(i));
		}
		for(int i=0;i<removedElements.size();i++) {
			System.out.println("removed: "+removedElements.get(i));
		}
	}
	
	
	
}