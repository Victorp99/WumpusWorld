/**
 * @(#)Room.java
 *
 *
 * @author 
 * @version 1.00 2014/3/14
 */


public class Room {
	private boolean pit;
	private boolean breeze;
	private boolean wumpus;
	private boolean gold;
	private boolean stench;
	private boolean visited;
	
    public Room() {
    	pit =false;
    	wumpus = false;
    	gold = false;
    	breeze =false;
    	stench = false;
    	visited = false;
    }
    public void setPit()
    {
    	pit = true;
    }
    public boolean getPit()
    {
    	return pit;
    }
    public void setBreeze()
    {
    	breeze = true;
    }
    public boolean getBreeze()
    {
    	return breeze;
    }
    public void setWumpus(boolean w)
    {
    	wumpus = w;
    }
    public boolean getWumpus()
    {
    	return wumpus;
    }
    public void setStench()
    {
    	stench = true;
    }
    public boolean getStench()
    {
    	return stench;
    }
    public void setGold()
    {
    	gold = true;
    }
    public boolean getGold()
    {
    	return gold;
    }
    public void setVisited()
    {
    	visited = true;
    }
    public boolean getVisited()
    {
    	return visited;
    }
    
    
    
    
}