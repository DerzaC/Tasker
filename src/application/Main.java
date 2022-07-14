package application;
	
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Main extends Application {
	private static EHandler handler=new EHandler();
	public static Main main;
	private static double h=600;
	private static double w=1000;
	private static Group itemFrame;
		private class Space {
			double top =50;
			double bottom =50;
			double left =100;
			double right =100;	
		}
	private static Space space;
	private AnchorPane root;
	private Button[][] bttnz = new Button[2][];
	
	public Effect setShadow() {
		DropShadow shadow = new DropShadow(30, 10, 10, new Color(0.0, 0.0, 0.0, 0.5));
		return shadow;
	}
	
	public double getCenterHeight() {
		return h-(space.top+space.bottom);
	}
	
	public EHandler getEHandler() {
		return handler;
	}
	@Override
	public void start(Stage primaryStage) {
		Deamon test = new Deamon("");
		main=this;
		space=new Space();
		try {
			testFrame(15);//                  <--------------- testmethod
			addGfx();
			this.root = new AnchorPane();
			root.getChildren().addAll(itemFrame);
			bottomButtonBar();
			setItemActivity(true);
			Scene scene = new Scene(root,w,h);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);			
			primaryStage.show();
			if(Window.getWindows().size()>1) {
				Window.getWindows().get(0).hide();
			}	
		} catch(Exception e) {			
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setItemActivity(boolean setDisabled) {
		for (int i=0;i<bttnz[0].length;i++) {
			bttnz[0][i].setDisable(setDisabled);
			bttnz[0][i].setEffect(setDisabled?null:setShadow());
		}
	}
	
	public void bottomButtonBar() {
		double spaceBetween=50;//<make Scalable
		String[] customButtons = new String[]	 	{"Edit","Delete"}; //active when item in "tasks" is selected
		String[] buttons = new String[] 			{"Tasks","Log","Exit"};// always active/visible
		bttnz[0]=new Button[customButtons.length];
		bttnz[1]=new Button[buttons.length];
		bttnBarBuilder(customButtons,0,50);
		bttnBarBuilder(buttons,1,50);			
	}
	
	public void bttnBarBuilder(String buttons[],int row,double spaceBetween) {
		double ySpace = 40-row*40;
		double maxWidth=(w-(space.left+space.right))/buttons.length;
		for(int i=0;i < buttons.length;i++) {
			bttnz[row][i]=new Button(buttons[i]);
			bttnz[row][i].setId(buttons[i]+i);
			bttnz[row][i].setTranslateY(h-(space.bottom+40+ySpace));
			bttnz[row][i].setTranslateX(space.left+(i*maxWidth)+(spaceBetween/2));
			bttnz[row][i].setPrefWidth(maxWidth-spaceBetween);
			bttnz[row][i].addEventHandler(ActionEvent.ACTION, handler);
			bttnz[row][i].setEffect(setShadow());
		root.getChildren().add(bttnz[row][i]);
	}
	}
	
	public double[] calcCenterPos(int eleNr) {
		int xloc=eleNr;
		int yloc=0;
		double[] eleSize = Container.getSize();
		int col = (int) ((w-(space.left+space.right))/eleSize[0]);
		double remSpace=((w-(space.left+space.right))-(col*eleSize[0]))/(col-1);
		double yCusHeight  =eleSize[1]+10;
		while(xloc>=col) {
			yloc+=1;
			xloc-=col;
		}
		double x=(xloc*eleSize[0])+space.left;
		double y=yloc*yCusHeight+space.top;
		return new double[] {x+(remSpace*col),y};
	}
	
	private void addGfx() {
		Main.itemFrame = new Group();
		for(int i=0;i<elements.size();i++) {
			itemFrame.getChildren().add(elements.get(i).getGroup());
		}	
	}
	//------------------test methods------------------------
	//build some test tasks
	private boolean hasBeenBuilt=false;
	
	public void testFrame(int n) {
		if(!hasBeenBuilt) {
			
			hasBeenBuilt=true;
			for(int i=0;i<n;i++) {
				double[] pos=calcCenterPos(i);
				elements.add(new Container(pos[0],pos[1]));
			}
		elements.add(new Container(calcCenterPos(elements.size())));
		}
	}
	
	public void reBuildFrame() {
		//LinkedList<Container> TmpElements = elements;
		//elements.clear();
			for(int i=0;i<elements.size();i++) {
				//double[] pos=calcCenterPos(i);
				elements.get(i).setPos(calcCenterPos(i));
				//elements.add(TmpElements.get(i));
				//elements.add(new Container(pos[0],pos[1]));
			}
		}
	
	public void testAddSwitch(int id) {
		int index =getListIndexById(id);
		elements.get(index).consumeAdd();
		elements.get(index).reload();
		reloadContainer();
		start(new Stage());
	}
	
	
	
	
	//-------------------------------------------------------
	
	public void itemFrameIsVisible(boolean visible) {
		Main.itemFrame.setVisible(visible);
		for(int i=0;i<bttnz[0].length;i++) {
			bttnz[0][i].setVisible(visible);
		}
	}

	
	public int getListIndexById(int id) {
		for (int i=0; i<elements.size();i++) {			
			if(elements.get(i).getID()==id) {				
				return i;
			}
		}
		return -1;		
	}
	
	public int getSelectedListIndex() {
		for (int i=0; i<elements.size();i++) {			
			if(elements.get(i).getID()==Container.selectedID) {				
				return i;
			}
		}
		return -1;		
	}
	//laggy!!!11!
	public boolean confirmPane(String text) {
		String[]tmp=new String[] {"Yes","nope"};
		int confirmation =JOptionPane.showOptionDialog(null, text, "confirmation", 1, 0, null, tmp, tmp[1]);
		return confirmation == 0?true:false;
	}
	
	
	public void removeByIndex(int index) {	
		if(confirmPane("Remove selected Item?")) {
			int id = elements.get(index).getID();
			elements.get(index).selfDestruct();
			elements.remove(index);
			Container.setSelected(id);
			reloadContainer();
		}
	}
	
	public Container getContainerByListIndex(int index) {
		return elements.get(index);
	}
	
	public void reloadContainer() {
		if (Container.getAddConsumedStatus()) {addAdd();}
		for (int i=0;i<elements.size();i++) {	
			elements.get(i).reload();
			elements.get(i).setPos(calcCenterPos(i));
		}
		
	}
	
	public void addAdd() {
		//System.out.println("exec");
		elements.add(new Container(calcCenterPos(elements.size())));
	}
	
	//private static ObservableList<Container> elements=new ObservableList();
	private static ArrayList<Container> elements =new ArrayList<Container>();
	//private static ObservableList
	
		
		class Container{
			private static double width=90;
			private static double height=115;
			private static double border=5;
			private static int idCount=0;
			private int id;
			private Group content=new Group();
			private Text tf=new Text();
			private boolean active=false;
			private Button bttn;
			private Rectangle bg=new Rectangle(width,height);
			private double[] pos;
			private String[] text=new String[] {"null","null"};
			private static int selectedID=-1;
			private boolean addFrame=false;
			private static boolean addConsumed=false;
		
			public static boolean getAddConsumedStatus() {
				return addConsumed;
			}
			
			public void consumeAdd() {
				this.addFrame=false;
				addConsumed = true;
				bttn.setVisible(true);
			}
			
			public static int setSelected(int id) {
				int tmp = selectedID==-1?1:selectedID;
				selectedID= selectedID==id?-1:id;
				main.setItemActivity(selectedID!=-1?false:true);		
			return tmp;
			}
		
			public static void clearSelected() {
				selectedID=-1;
			}
		
			public void invertActivation() {
				active = !active;
				reload();
			}
		
			public int getID() {
				return id;	
			}
			
			public void setPos(double[] pos) {
				this.pos = pos;
				reload();
			}
			
			public Group getGroup() {
				return content;
			}
		
			public void infoText() {
				int space=4;
				tf.setFill(active?new Color(0,0,0,1):new Color (0,0,0,0.3));
				tf.setText("ID:"+id+"\nTarget:\n"+text[0]+"\nTimer:\n"+text[1]);
				tf.prefWidth(width-space);
				tf.prefHeight(height-space);
				tf.setWrappingWidth(width-space);
				tf.setX(pos[0]+border+space/2);
				tf.setY(pos[1]+20+space/2);
				tf.setId("select"+id);
			}
			
			public void selfDestruct() {
				this.content.setVisible(false);
				this.content=null;
			}
		
		
			public static double[] getSize() {		
				return new double[] {width+2*border,height+2*border};
			}
		
			public void setSize(double widthNew,double heightNew) {
				width=widthNew;
				height=heightNew;	
			}
		
			private void background() {
				bg.setFill(!active?new Color(0.8,0.8,0.8,0.1):new Color (0.95,0.95,0.95,1));
				bg.setStroke(active?new Color(0,0.57,1,1):new Color (0,0,0,0.3));
				bg.setStrokeWidth(selectedID==id?3:1);
				bg.setX(pos[0]+border);
				bg.setY(pos[1]+border);
				bg.setEffect(active?main.setShadow():null);
				bg.setArcWidth(10);
				bg.setArcHeight(10);
				bg.setId("select"+id);
			}
		
			Container(double[] pos){
				this.addFrame=true;
				addConsumed=false;
				this.pos=pos;
				this.id=idCount;	
				bttn=new Button(active?"Deactivate":"Activate");				
				bttn.addEventHandler(ActionEvent.ACTION, handler);
				tf.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
				bg.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
				buildFrame();
				idCount++;
				reload();
			}
			
			public void add() {
			//System.out.print("exec");
			//bttn.addEventHandler(ActionEvent.ACTION, handler);
			//tf.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
			//bg.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
			//buildFrame();
			//idCount++;
			bttn.setVisible(false);
			bg.setId("Add"+id);
			tf.setId("Add"+id);
			tf.setText("ADD");
			}
			
			
			Container(double x,double y){			
				this.pos = new double[] {x,y};
				this.id=idCount;	
				bttn=new Button(active?"Deactivate":"Activate");
				reload();
				bttn.addEventHandler(ActionEvent.ACTION, handler);
				tf.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
				bg.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
				buildFrame();
				idCount++;
			}
		
			private void buildButtons() {
				bttn.setText(active?"Deactivate":"Activate");
				bttn.setId("deamonBttn"+id);
				bttn.setTranslateX(pos[0]+border+3);
				bttn.setTranslateY(pos[1]+height-20-3);
				bttn.setPrefWidth(width-6);	
				bttn.setEffect(!active?new Reflection():null);
			}
		
			public void reload() {
				buildButtons();	
				infoText();
				background();
				if (addFrame) add();
			}
		
			private void buildFrame() {
				content.getChildren().addAll(bg,tf,bttn);
			}
			
		
			
			
	}
}
