package application;

import java.util.Map;
import java.util.EnumMap;
import javafx.scene.paint.Color;

//
enum SETTING {

}
public enum SETTINGS{TOP,BOTTOM,LEFT,RIGHT;
	public static EnumMap<SETTINGS, Double> commonSpaces = new EnumMap<>(SETTINGS.class);
	
	private void set() {
		commonSpaces.put(SETTINGS.TOP, 50.0);
		commonSpaces.put(SETTINGS.BOTTOM, 50.0);
		commonSpaces.put(SETTINGS.LEFT, 100.0);
		commonSpaces.put(SETTINGS.RIGHT, 100.0);
	}
	
	SETTINGS(){
		set();
	}
	
	
	
	
	
	
	
}
