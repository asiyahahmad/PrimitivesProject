import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JColorChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PrimitivesMain extends Frame
{
	private static final long serialVersionUID = 1L;


    // menu and all of the menu's items and check boxes
	private MenuBar menuBar;
	private Menu mainMenu;
	private Menu editMenu;
	private MenuItem exitItem;
	private MenuItem colorItem;
	private CheckboxMenuItem Line; 
	private CheckboxMenuItem Rectangle;
	private CheckboxMenuItem Oval;
	private CheckboxMenuItem Polygon;
	private CheckboxMenuItem Freehand;
	private CheckboxMenuItem select;
	private MenuItem fill;
	private MenuItem delete;
	
	// the current drawing color because all of the primitives are initially drawn in black
	public Color curColor = Color.black;
	
	//the current xs and ys 
	private int pointX = 0;
	private int pointY = 0;
	
	//the new xys and ys
	private int newPointX = 0;
	private int newPointY = 0;
	
	//need to have an instance of the primitivesclass 
	private PrimitivesClass SelectedPrim;
	
	//creating an array list of points 
	private ArrayList<Integer> points = new ArrayList <Integer>();
	
	//create an array list of primitives 
	private ArrayList<PrimitivesClass> NumOfPrimitives = new ArrayList<PrimitivesClass>();
	
	//Constructor
	PrimitivesMain()
	{
			//Enables the closing of the window.
			addWindowListener(new MyFinishWindow());
			createMenu();
			
			//add two points to primitive 
			// capture the mouse down event with an event handler
			addMouseListener(
					new MouseAdapter()
					{
						public void mousePressed(MouseEvent evt)
						{
							
							 //picks up pointX
							pointX = evt.getX();
							 
							 //picks up pointY
							pointY = evt.getY();
		 
							 //If line is clicked 
							if (Line.getState() == true) {
								
								//store the points
								points.add(pointX);
								 
								points.add(pointY);
								 
								 
								if (points.size() == 4) {
									//prints to help guide users 
									System.out.println("You've selected line.");
									   
									//send line and the points to the primitives class, send the color and whether you fill it as well
									PrimitivesClass p = new PrimitivesClass(PrimitiveType.LINE, points, curColor, false);
									//add this primitive to the number of primitives 
									NumOfPrimitives.add(p);
										
									//clear the points here use .clear()
									points.clear();
								}
								 
							 } else if (Oval.getState() == true) {
								
								 
								//store the points
								 points.add(pointX);
								 
								 points.add(pointY);
								 
								 //i still need 4 points (x1, y1, x2, y2) 
								 if (points.size() == 4) {
									//prints to help guide users 
									 System.out.println("You've selected oval.");
							
									 //send oval and the points to the primitives class, send the color and whether you fill it as well
									 PrimitivesClass p = new PrimitivesClass(PrimitiveType.OVAL, points, curColor, false);
										
									 //add this primitive to the number of primitives 
									 NumOfPrimitives.add(p);
										
									 //clear the points here use .clear()
									 points.clear();
								 }
								 
								 
							 } else if (Rectangle.getState() == true) {
								 
								
								 
								 //store the points
								 points.add(pointX);
								 
								 points.add(pointY);
								
								 //i still need 4 points (x1, y1, x2, y2) 
								 if (points.size() == 4) {
									//prints to help guide users 
									System.out.println("You've selected rectangle.");
									 	
									//send rectangle and the points to the primitives class, send the color and whether you fill it as well
								    PrimitivesClass p = new PrimitivesClass(PrimitiveType.RECTANGLE, points, curColor, false);
									//add the primitive to the numOfPrimitives array
									NumOfPrimitives.add(p);
							
								   //clear the points here use .clear()
								   points.clear();
								}
								 
						      } else if(Polygon.getState() == true) {
						    	  
						    	  //store the points
						    	  points.add(pointX);
								 
						    	  points.add(pointY);
						    	  
						    	  //prints to help guide users 
						    	  System.out.println("Double click when you want to close your polygon");

						    	  //click twice, to finish drawing the polygon 
						    	  if (evt.getClickCount() == 2) {
						    		 //prints to help guide users 
						    		  System.out.println("You've selected polygon.");
						    		  
									  //send polygon and the points to the primitives class, send the color and whether you fill it as well
						    		  PrimitivesClass p = new PrimitivesClass(PrimitiveType.POLYGON, points, curColor, false);
									  //add the primitive to the numOfPrimitives array
						    		  NumOfPrimitives.add(p);
						    		  points.clear();
						    	  }
    		
						      } else if(select.getState() == true) {
							
							 		//selected prim to null until you found something 
							 		SelectedPrim = null;
							 		//prints to help guide users 
							 		System.out.println("You're in select mode.");
							 		
							 		//search through entire numofprimitives size
									for (int i = 0; i < NumOfPrimitives.size(); i++) {
										
										NumOfPrimitives.get(i).findBox();
									
										//if they clicked a spot that's inside
										if(NumOfPrimitives.get(i).insideBox(pointX, pointY) == true) {
											
											//stop searching once you found it 
											SelectedPrim = NumOfPrimitives.get(i);
											break;
											
										}
									}
									
						      } 
											
							  repaint();
						}
					
					//used for the freehand primitive
					public void mouseReleased(MouseEvent evt)
					{	
						if (Freehand.getState() == true) {
							//prints to help guide users 
							System.out.println("You've selected free hand.");
							//send freehand and the points to the primitives class, send the color and whether you fill it as well
							PrimitivesClass p = new PrimitivesClass(PrimitiveType.FREEHAND, points, curColor, false);
							//add the primitive to the numOfPrimitives array
							NumOfPrimitives.add(p);
							points.clear();
						}
					}
				}
				
			);
				addMouseMotionListener(
						new MouseAdapter()
						   {
								public void mouseDragged (MouseEvent evt) {
									
									Graphics2D g = (Graphics2D) getGraphics();
									
									if(Freehand.getState() == true) {
										
										//for mouse dragged 
										newPointX = evt.getX();
										newPointY = evt.getY();
										
										//adding the points to the points array list
									    points.add(pointX);
									    points.add(pointY);
									    
									    //draws a line connecting the old points to the new points
									    g.drawLine(pointX, pointY, newPointX, newPointY);
									    
									    //updating the ponts with the new points
									    pointX = newPointX;
										pointY = newPointY;									
									}									
										
								}
					
							}
					);
	}


	private void createMenu()
	{
		// build a menu
		menuBar = new MenuBar();
		// main menu bar
		setMenuBar(menuBar);
		
		// a menu
		mainMenu = new Menu("Primitives");
		menuBar.add(mainMenu);
		
		//created a checkboxmenu item for line
		Line = new CheckboxMenuItem("Line");
		//adding line to the main menu
		mainMenu.add(Line);
		
		Line.addItemListener(
				new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						// we changed the fill state and want to see the effect
						repaint();
					}
				});
		
		//created a checkboxmenu item for rectangle
		Rectangle = new CheckboxMenuItem("Rectangle");
		//adding rectangle to the main menu
		mainMenu.add(Rectangle);
		
		Rectangle.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent evt)
				{
					// we changed the fill state and want to see the effect
					repaint();
				}
			});
		
		//created a checkboxmenu item for oval
		Oval = new CheckboxMenuItem("Oval");
		//adding oval to the main menu
		mainMenu.add(Oval);
		
		//add an itemlistener
		Oval.addItemListener(
			new ItemListener()
			{
				public void itemStateChanged(ItemEvent evt)
				{
					// we changed the fill state and want to see the effect
					repaint();
				}
			});
		
		//created a checkboxmenu item for polygon
		Polygon = new CheckboxMenuItem("Polygon");
		//adding polygon to the main menu
		mainMenu.add(Polygon);
		
		//add an itemlistener
		Polygon.addItemListener(
				new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						// we changed the fill state and want to see the effect
						repaint();
					}
				});
		
		//created a checkboxmenu item for freehand
		Freehand = new CheckboxMenuItem("Freehand");
		//adding freehand to the main menu
		mainMenu.add(Freehand);
		
		//add an itemlistener
		Freehand.addItemListener(
				new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						// we changed the fill state and want to see the effect
						repaint();
					}
				});
		
		// exit
		exitItem = new MenuItem("Exit");
		mainMenu.add(exitItem);
		// exit handler
		exitItem.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						// the parameter of exit() can indicate an error code
						// zero means the program exited without error
						System.exit(0);
					}
				}
				);
		
		//edit menu for selection, fill, color change, and deletion
		editMenu = new Menu("Edit");
		//add editmenu to the menuBar
		menuBar.add(editMenu);
		
		//create a checkboxmenuitem for select
		select = new CheckboxMenuItem("Select");
		//adding select into the editmenu
		editMenu.add(select);
		
		//create a menuitem for fill
		fill = new MenuItem("Fill Primitives");
		//adding fill into the editmenu
		editMenu.add(fill);
		
		//needs an actionlistener because things must be done when a user clicks delete
		fill.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						//if select is checked and there IS a selectedPrim
						if (select.getState() == true && SelectedPrim != null) {
							//if the selectedprim is full
							if (SelectedPrim.getFill() == true) {
								//set the fill to false
								SelectedPrim.setFill(false);
							 } else {
								//set the fill to true
								SelectedPrim.setFill(true);
							 }
						 }
						 // we changed the fill and want to see the effect so we need to repaint
						 repaint();
					}
				}
		);
		//create a menu item for delete and add delete option to edit menu
		delete = new MenuItem("Delete");
		editMenu.add(delete);
		
		//needs an actionlistener because things must be done when a user clicks delete
		delete.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						//if select is true 
						if (select.getState() == true) {
							//remove the primitive from the primitives array
							NumOfPrimitives.remove(SelectedPrim);
							//set the selectedprim to null
							SelectedPrim = null;
						
						}
					}
				}
				);
		
		// a menu item through which we allow changing the color
		colorItem = new MenuItem("Color");
		//add that menu item to editmenu
		editMenu.add(colorItem);
		
		// color dialog handler
		colorItem.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						// bring up a color dialog with the current color selected
						Color c = JColorChooser.showDialog(null, "Select drawing color", curColor);
						
						if (SelectedPrim != null && select.getState() == true) {
							//changed this to c to correctly change the color 
							SelectedPrim.setColor(c);
							
						} else {
							//else just send black color
							curColor = c;
							
						}
						
						// we changed the color and want to see the effect
						repaint();
					}
				}
					
		);
	}
	
	
	public void paint(Graphics g)
	{
		//loop through the points 
		//i need this for loop because my initial points won't print without it
		//i also decided to use system.out.prints to guide my users instead of printing out the rest
		//of the points for the users
		
		for (int i = 0; i < points.size(); i = i + 2) {
		
				
				//the first point is the first number in points 
				int point = points.get(0);
			
				//the second point is the second number in points
				int point2 = points.get(1);
			
				//for the first x and y, I decided to draw an oval instead of the + because it was easier for me 
				// to visualize 
				g.setColor(Color.RED);
				g.drawOval(point, point2, 2, 4);
			
		}
		
		
		//loop through the number of primitives array
		for (int j = 0; j < NumOfPrimitives.size(); j++) {
			//create an instance of PrimitivesClass for every primitive
			PrimitivesClass primitive = NumOfPrimitives.get(j);
			//draw the primitives
			primitive.draw(g);
			
		}
		
		for (int i = 0; i < NumOfPrimitives.size(); i++) {
			//if select is clicked and counter is equal to 1
			//do not check points in here
			if (SelectedPrim != null && select.getState() == true) {
				SelectedPrim.drawBoundingBox(g);
			}
		
		}
		
	}
	

	public static void main(String[] argv)
	{
		//Generate the window.
		PrimitivesMain f = new PrimitivesMain();

		//Define a title for the window.
		f.setTitle("Paint Program by Asiyah");      
		//Definition of the window size in pixels
		f.setSize(800, 600);
		//Show the window on the screen.
		f.setVisible(true);
	}
	
}