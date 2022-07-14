package application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;

public class EHandler implements ChangeListener, ListChangeListener, EventHandler<Event> {
	
	

	@Override
	public void handle(Event arg0) {
		doAction(arg0);		
	}
	@Override
	public void onChanged(Change arg0) {
		System.out.println("Change: "+arg0);	
	}
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		System.out.println("ObservableValue: "+arg0+" : "+ arg1+" : "+arg2);
	}
	
	public void doAction(Event ev) {
		String inc = ev.toString();
		int start = inc.indexOf("id=");
		int end= inc.indexOf(",");
		int index=end;
		do{
			index--;
		}while (Character.isDigit(inc.charAt(index)));		
		int id = Integer.parseInt((String) inc.subSequence(index+1, end));
		inc= inc.substring(start+3,index+1);
		switch (inc) {
			case "deamonBttn":		
				//System.out.println(Main.main.getListIndexById(id));
				Main.main.getContainerByListIndex(Main.main.getListIndexById(id)).invertActivation();
				break;
			case"Exit":
				Platform.exit();
				break;
			case"Tasks":
				Main.main.itemFrameIsVisible(true);
				break;
			case"Edit":
				System.out.println("TODO-Edit");
				break;
			case"Log":
				System.out.println("TODO");
				Main.main.itemFrameIsVisible(false);
				break;
			case "select":
				int tmp = Main.Container.setSelected(id);
				Main.main.reloadContainer();
				break;
			case "Delete":
				Main.main.removeByIndex(Main.main.getSelectedListIndex());
				Main.main.reloadContainer();				
				break;
			case "Add":
				Main.main.testAddSwitch(id);
				break;				
			default:
				System.out.println("Unregistered event occured\n"+ev);
				System.out.println("ID_STRING: "+inc+"; ID_INT: "+id);	
		}
	}
}
