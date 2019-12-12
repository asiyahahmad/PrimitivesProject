import java.awt.*;
import java.util.ArrayList;

public class PrimitivesClass
{
	//create a primitive type
	private  PrimitiveType type;
	
	//creates an integer array list
	private ArrayList<Integer> pointsCopy = new ArrayList<Integer>();

	//instance variable to use for changing the color of the primitives
	private Color color;
	
	// bounding box top left, width, and height
	private int xtop, ytop, width, height;           
	
	//instance variable for changing the fill state
	private boolean fillBoolean;

	
	
	//pass primitive type and array list of points into constructor 
	PrimitivesClass(PrimitiveType type, ArrayList<Integer> points, Color curColor, boolean fill ) {
		
		//assigning variable type to private instant variable type 
		this.type = type;
		
		//setting the instance variable to the paramater of the primitives
		//so that i can use it in this class
		fillBoolean = fill;
		
		//setting the instance variable to the paramater of the primitives
		//so that i can use it in this class
		color = curColor;
		
		//create a copy of points here
		for (int i = 0; i < points.size(); i++) {
			//put the points in the copy
			pointsCopy.add(points.get(i));
		}
	
		
		
	}
	
	//find the bounding box values
	public void findBox()
	{
		//i need the maxX, minX, maxY, and minY
		
		//if there are points in the pointscopy
		if (pointsCopy.size() != 0 ) {
			
			//find the maxX
			int maxX = pointsCopy.get(0);
		
			//start at 0 because 0 is x
			for (int i = 0; i < pointsCopy.size(); i = i + 2) {
				
				if (maxX < pointsCopy.get(i)) {
					maxX = pointsCopy.get(i);
				
				}
			}
			
			//find the minX
			int minX = pointsCopy.get(0);
			
			//start at 0 because 0 is x
			for (int i = 0; i < pointsCopy.size(); i = i + 2) {
				if (minX > pointsCopy.get(i)) {
					minX = pointsCopy.get(i);
				}	
			}
		
			//find the maxY
			int maxY = pointsCopy.get(1);
			//start at 1 because 1 is the first y value in the arraylist
			for (int i = 1; i < pointsCopy.size(); i = i + 2) {
				if (maxY < pointsCopy.get(i)) {
					maxY = pointsCopy.get(i);
				}
			}
		
			//find the minY
			int minY = pointsCopy.get(1);
			//start at 1 because 1 is the first y value in the arraylist
			
			for (int i = 1; i < pointsCopy.size(); i = i + 2) {
				if (minY > pointsCopy.get(i)) {
					minY = pointsCopy.get(i);
				}
			}
		
			//finding width, height, xtop, and ytop
			width = maxX - minX;
		
			height = maxY - minY;
		
			//i could have used xtop and ytop instead of minX and minY to reduce redundancy 
			//but i decided not to because I prefer to stick to what we did in class and 
			//doing an xbottom and ybottom would have confused me
			//this is simpler 
			
			xtop = minX;
		
			ytop = minY;
		
		}
	
	}
	//used to drawBoundingBox for selected primitive 
	public void drawBoundingBox(Graphics g) {
		//bounding box is red
		g.setColor(Color.RED);
		//drawn with xtop, ytop, width and height
		g.drawRect(xtop, ytop, width, height);
	}
	
	
	//bounding box test 
	boolean insideBox(int x, int y)
	{
		//if the click is inside the boundaries of the box
		if ((x > xtop) && (x < xtop + width) && (y > ytop) && (y < ytop + height)) {
			//return true for inside
			return true;
		}
		//return false if the conditions are not met
		return false;
	}
	
	//updates the value of color
	public void setColor(Color curColor) {
		color = curColor;
	}

	//updates the value of fill
	public void setFill(Boolean fill) {
		fillBoolean = fill;

	}
	
	//reads the updated value 
	public boolean getFill() {
		return fillBoolean;
		
	}
	

	//where all primitives are drawn!
	public void draw(Graphics g) {
		//i tried moving this to a different method or in the constructor and it did not work 
		//so I thought, since these arrays are only being used in this method, it's fine to keep the creation of 
		//these arrays here
		
		//need npoints to create my xarray and yarray
		int nPoints = pointsCopy.size()/2;
		
		//create arrays with a set length
		int xarray[] = new int[nPoints];
		int yarray[] = new int[nPoints];
		
		//loop through the xarray's length
		for (int k = 0; k < xarray.length; k++) {
				//fill x array with only even numbers
				xarray[k] = pointsCopy.get(k * 2);
			
				//from discrete, add one to all the evens 
				yarray[k] = pointsCopy.get((k * 2) + 1);
			
		}
		
		//reduce redundancy by combining variables seen in multiple shapes
		
		//find x for rect and oval
		int firstX = Math.min(pointsCopy.get(0), pointsCopy.get(2));
		
		//find y for rect and oval
		int firstY = Math.min(pointsCopy.get(1), pointsCopy.get(3));
		
		//find width for rect and oval
		int primitivesWidth = Math.abs(pointsCopy.get(2) - pointsCopy.get(0));
		
		//find height for rect and oval 
		int primitivesHeight = Math.abs(pointsCopy.get(3) - pointsCopy.get(1));
		
		//if primitive type equals line
		if (type.equals(PrimitiveType.LINE))  {
			//set color
			g.setColor(color);
			//draws the line using the xs and ys in the points copy array list 
			//there is no fill for a line
			g.drawLine(pointsCopy.get(0), pointsCopy.get(1), pointsCopy.get(2), pointsCopy.get(3));	
			
			
		//if primitive type equals rectangle
		} else if (type.equals(PrimitiveType.RECTANGLE)) {
			//set color
			g.setColor(color);
			
			//if fill is true
			if (fillBoolean == true) { 
				//fill the rectangle
				g.fillRect(firstX, firstY, primitivesWidth, primitivesHeight);
			//if not
			} else {
				 //draw rectangle 
				g.drawRect(firstX, firstY, primitivesWidth, primitivesHeight);
			}
			
			
		//if primitive type equals oval
		} else if (type.equals(PrimitiveType.OVAL)) {
			//set color
			g.setColor(color);
			

			//if fill is true
			if(fillBoolean == true) {
				//fill the oval
				g.fillOval(firstX, firstY, primitivesWidth, primitivesHeight);
			//if not
			} else {
			//draw oval
				g.drawOval(firstX, firstY, primitivesWidth, primitivesHeight);
		   }
			
		} else if(type.equals(PrimitiveType.POLYGON)) {
			//set color
			g.setColor(color);

			//if fill is true
			if (fillBoolean == true) { 
				//fill the polygon
				g.fillPolygon(xarray, yarray, nPoints);
			//if not
			} else {
				//draw polygon
				g.drawPolygon(xarray,yarray, nPoints);
			}
			
		} else if(type.equals(PrimitiveType.FREEHAND)) {
			//set color
			g.setColor(color);
			//draw the polyline
			//no fill bc you can't fill a freehand
			g.drawPolyline(xarray, yarray, nPoints);
		}
	}
	
	

}
