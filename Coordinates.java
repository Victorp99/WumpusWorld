/**
 * @(#)coordinates.java
 *
 *
 * @author 
 * @version 1.00 2014/3/13
 */


public class Coordinates {

    public Coordinates() {
    	
    }
    
    private int x;
    private int y;
    private boolean alive;
    private boolean arrow;
    public void setX(int xCoor)
    {
    	x =xCoor;
    }
    public void setY(int yCoor)
    {
    	y = yCoor;
    }
    public boolean getArrow()
    {
    	return arrow;
    }
    public void setArrow(boolean a)
    {
    	arrow = a;
    }
    public int getX()
    {
    	return x;
    }
    public int getY()
    {
    	return y;
    }
    public boolean getStatus()
    {
    	return alive;
    }
    public void setStatus(boolean life)
    {
    	alive = life;
    }
}