import java.util.*;
import java.io.*;
public class wumpusWorld {
        

    public wumpusWorld() {
    }
    
 
    public static void main(String[] args) throws IOException {
       Scanner keyboard = new Scanner(System.in);
       PrintWriter outputFile = new PrintWriter("kb.dat");
       boolean dead =false, win = false, killed = false;
       int i= 0;// x coordinates of the map
       int j =0;// y coordinates of the map
       //int x = 1, y =1; //players intital position
       int coor[] = new int[2];
       coor[0] = 1; //x coordinate of player
       coor[1] = 1; //y coordinate of player
       //int actualCoor[] = new int[2];
       Coordinates actualCoors = new Coordinates();
       actualCoors.setStatus(true);
       actualCoors.setArrow(true);
       //actualCoors.setX(j -1);
       //actualCoors.setY(0);
       Coordinates wumpus = new Coordinates();
       wumpus.setStatus(true);
       Coordinates gold = new Coordinates();
       //actualCoor[0] = j-1; //rows
       //actualCoor[1] = 0; // column
       int size; // size of array
       String move;
       String direction = "East"; //the direction the player is facing
      String perceptMap[] = new String[200];// read whats in the map
      File file = new File("wumpusMap.txt");
      String[][] stringmap = parseFile(file);
       //find gold 
       System.out.println("Welcome to Wumpus World!!!");
       System.out.println("You will accompanied by the one and only cave expert, Bill!");
       System.out.println("Let us begin our journey!!!");
       Scanner fr = new Scanner(file);
       
       while(fr.hasNextLine())
       {
       	perceptMap[j] =fr.nextLine();
       	++j;
       }
       
      
      // System.out.println(j);
       String[] line1 = perceptMap[1].split(",");
       i = line1.length; //columns
     
       
       actualCoors.setX(j -1);
       actualCoors.setY(0);
       for(int q= 0; q<j; ++q)
       {
       	for(int z = 0; z<i; ++z)
       	{
       		if(stringmap[q][z].equals("G"))
       		{
       			gold.setX(q);
       			gold.setY(z);
       		}
       	}
       }
       for(int q= 0; q<j; ++q)
       {
       	for(int z = 0; z<i; ++z)
       	{
       		if(stringmap[q][z].equals("G"))
       		{
       			wumpus.setX(q);
       			wumpus.setY(z);
       		}
       	}
       }
 
		
		Room[][] map = new Room[j][i];
		//making map of boolean values
		for(int x = 0; x< j; ++x)
		{
			for(int y = 0; y< i; ++y)
			{
				Room p = new Room();
				if(stringmap[x][y].equals("P"))
				{
					p.setPit();
				}
				else if(stringmap[x][y].equals("G"))
				{
					p.setGold();
				}
				else if(stringmap[x][y].equals("W"))
				{
					p.setWumpus(true);
				}	
				map[x][y] = p;
			}
		}
		
		//setting breezes and stenches
		for(int x = 0; x<j; ++x)
		{
			for(int y= 0; y<i;++y)
			{
				if(map[x][y].getPit()== true)
				{
					// place breeze above
					if(x!= 0)
					{
						map[x-1][y].setBreeze();
					}
					//place breeze right
					if(y+1 <= i- 1)
					{
						map[x][y+1].setBreeze();
					}
					//place breeze down
					if(x+ 1 <= j -1)
					{
						map[x+1][y].setBreeze();
					}
					//place breeze left
					if(y!= 0)
					{
						map[x][y-1].setBreeze();
					}
					
				}
				if(map[x][y].getWumpus()== true)
				{
					// place stench above
					if(x!= 0)
					{
						map[x-1][y].setStench();
					}
					//place stench right
					if(y+1 <= i- 1)
					{
						map[x][y+1].setStench();
					}
					//place stench down
					if(x+ 1 <= j -1)
					{
						map[x+1][y].setStench();
					}
					//place stench left
					if(y!= 0)
					{
						map[x][y-1].setStench();
					}
					
				}
			}
		}
		
       int score = 0;
       //- = not , ^ = and, R: = rule, OR = or, Deduction: = a deduction made, > = implies
       outputFile.println("R: -P[1,1]");
       outputFile.println("R: (-W[x, y] ^ P[x,y]) ^ (-W[x,y] ^ G[x,y])");
       outputFile.println();
       map[actualCoors.getX()][actualCoors.getY()].setVisited();
       while(actualCoors.getStatus() == true && win != true)
       {
       	//if(direction ==1)
       		outputFile.println();
       		outputFile.println("---- in ["+coor[0]+"]["+coor[1]+"] ----");
       		System.out.println("You are in room ["+coor[0]+"]["+coor[1]+"] of the cave. Facing "+direction+".");
       		if(map[actualCoors.getX()][actualCoors.getY()].getBreeze()== true)
       		{
       			System.out.println("There is a BREEZE in here!");
       			outputFile.print("B["+coor[0]+", "+coor[1]+"] ");
       			
       		}
       		if(map[actualCoors.getX()][actualCoors.getY()].getStench()== true)
       		{
       			System.out.println("There is a STENCH in here!");
       			outputFile.print("S["+coor[0]+", "+coor[1]+"] ");
       		}
       		if(map[actualCoors.getX()][actualCoors.getY()].getWumpus()== true)
       		{
       			System.out.println("You have stepped into the room and the Wumpus killed you.");
       			score = score - 1000;
       			outputFile.print("W["+coor[0]+", "+coor[1]+"] ");
       			actualCoors.setStatus(false);
       			win = false;
       		}
       		if(map[actualCoors.getX()][actualCoors.getY()].getPit()== true)
       		{
       			System.out.println("You stepped into the room and you fell down a pit!!");
       			actualCoors.setStatus(false);
       			outputFile.print("P["+coor[0]+", "+coor[1]+"] ");
       			win = false;
       		}
       		if(map[actualCoors.getX()][actualCoors.getY()].getGold()== true)
       		{
       			System.out.println("You found the gold!!!!");
       			score = score +1000;
       			outputFile.print("G["+coor[0]+", "+coor[1]+"] ");
       			win = true;
       		}
       		outputFile.println();
       		//what is not in the current spot
       		if(map[actualCoors.getX()][actualCoors.getY()].getWumpus()== false)
       		{
       			outputFile.println("-W["+coor[0]+", "+coor[1]+"] ");
       		}
       		if(map[actualCoors.getX()][actualCoors.getY()].getPit()== false)
       		{
       			outputFile.println("-P["+coor[0]+", "+coor[1]+"] ");
       		}
       		if(map[actualCoors.getX()][actualCoors.getY()].getGold()== false)
       		{
       			outputFile.println("-G["+coor[0]+", "+coor[1]+"] ");
       		}
       		////////implications of pits///////////////////////////////////////
       		// use check values to put ors in, have to have another set of if statements
       		int check1 =0, check2 = 0, check3 = 0, check4 = 0, check = 0; //remember to set back to zero!!!!
       		if(map[actualCoors.getX()][actualCoors.getY()].getBreeze()== true)
       		{
       				outputFile.print("B["+coor[0]+","+coor[1]+"] -> ");
					if(actualCoors.getY()!= 0)
					{
						if(map[actualCoors.getX()][actualCoors.getY()-1].getVisited() == false)
						{
							check1= 1;
							++check;
							int temp = coor[1] -1;
							outputFile.print("P["+coor[0]+"],["+temp+"] ");
						}
					}
					if(actualCoors.getX()!= j-1)
					{
						if(map[actualCoors.getX()+1][actualCoors.getY()].getVisited() == false)
						{
							check2 =1;
							++check;
							int temp = coor[0] -1;
							outputFile.print("P["+temp+"],["+coor[1]+"] ");
						}
					}
					//array out of bounds index
					//maybe use actualcoors, actual array doesnt follow coor[]
					//convert? 
					if(actualCoors.getX()!= 0)
					{
						//check if if statement is right(properly converted)
						if(map[actualCoors.getX()-1][actualCoors.getY()].getVisited() == false)
						{
							check3 =1;
							++check;
							int temp = coor[0]+1;
							outputFile.print("P["+temp+"],["+coor[1]+"] ");
						}
					} 
					if(actualCoors.getY()!= i -1)
					{
						if(map[actualCoors.getX()][actualCoors.getY()+1].getVisited() == false)
						{
							check4 =1;
							++check;
							int temp = coor[1] +1;
							outputFile.print("P["+coor[0]+"],["+temp+"] ");
						}
					}
					//output to user here?
					//if(win != true && actualCoors.getStatus() == true)	
					if(check >1)
					{
						if(win != true && actualCoors.getStatus() == true)	
						{
						
						System.out.println("I think that there is probably a pit in these spots: ");
						if(check1 == 1)
						{
							int temp = coor[1] -1;
							System.out.print("P["+coor[0]+","+temp+"] ");
						}
						if(check2 ==1)
						{
							int temp = coor[0] -1;
							System.out.print("P["+temp+","+coor[1]+"] ");
						}
						if(check3 == 1)
						{
							int temp = coor[0]+1;
							System.out.print("P["+temp+","+coor[1]+"] ");
						}
						if(check4 == 1)
						{
							int temp = coor[1] +1;
							System.out.print("P["+coor[0]+","+temp+"] ");
						}
						System.out.println();
						check1 = 0;
						check2 =0;
						check3 = 0;
						check4 =0;
						check =0;
						}
					}
					//here is the pit
					else
					{
						if(check1 == 1)
						{
							int temp = coor[1] -1;
							System.out.println("Watch out!! There is a pit in ["+coor[0]+","+temp+"] ");
						}
						if(check2 == 1)
						{
							int temp = coor[0] -1;
							System.out.println("Watch out!! There is a pit in ["+temp+","+coor[1]+"] ");
						}
						if(check3 == 1)
						{
							int temp = coor[0]+1;
							System.out.println("Watch out!! There is a pit in ["+temp+","+coor[1]+"] ");
						}
						if(check4 == 1)
						{
							int temp = coor[1] +1;
							System.out.println("Watch out!! There is a pit in ["+coor[0]+","+temp+"] ");
						}
						System.out.println();
						check1 = 0;
						check2 =0;
						check3 = 0;
						check4 =0;
						check =0;
					}
       		}
       		////////implications of wumpus///////////////////////////////////////
       		if(map[actualCoors.getX()][actualCoors.getY()].getStench()== true)
       		{
       				outputFile.println();
       				outputFile.print("S["+coor[0]+","+coor[1]+"] -> ");
					if(actualCoors.getY()!= 0)
					{
						if(map[actualCoors.getX()][actualCoors.getY() - 1].getVisited()!= true)
						{
							check1 = 1;
							++check;
							int temp = coor[1] -1;
							outputFile.print("W["+coor[0]+","+temp+"] ");
						}
					}
					if(actualCoors.getX()!= j-1)
					{
						if(map[(actualCoors.getX())+1][actualCoors.getY()].getVisited() != true)
						{
							check2 =1;
							++check;
							int temp = coor[0] -1;
							outputFile.print("W["+temp+","+coor[1]+"] ");
						}
					}
					if(actualCoors.getX()!= 0)
					{
						if(map[actualCoors.getX() -1][actualCoors.getY()].getVisited() != true)
						{
							check3 =1;
							++check;
							int temp = coor[0]+1;
							outputFile.print("W["+temp+","+coor[1]+"] ");
						}
					} 
					if(actualCoors.getY()!= i -1)
					{
						if(map[actualCoors.getX()][actualCoors.getY()+1].getVisited() != true)
						{
							check4 =1;
							++check;
							int temp = coor[1] +1;
							outputFile.print("W["+coor[0]+","+temp+"] ");
						}
					}
					//output to user here?
					if(check >1)
					{
						if(win != true && actualCoors.getStatus() == true)	
						{
						
						System.out.println("I think that the Wumpus is probably in these spots: ");
						if(check1 == 1)
						{
							int temp = coor[1] -1;
							System.out.print("W["+coor[0]+","+temp+"] ");
						}
						if(check2 ==1)
						{
							int temp = coor[0] -1;
							System.out.print("W["+temp+","+coor[1]+"] ");
						}
						if(check3 == 1)
						{
							int temp = coor[0]+1;
							System.out.print("W["+temp+","+coor[1]+"] ");
						}
						if(check4 == 1)
						{
							int temp = coor[1] +1;
							System.out.print("W["+coor[0]+","+temp+"] ");
						}
						System.out.println();
						check1 = 0;
						check2 =0;
						check3 = 0;
						check4 =0;
						check =0;
						}
					}
					//here is the wumpus
					else
					{
						if(check1 == 1)
						{
							int temp = coor[1] -1;
							System.out.println("Watch out!! The Wumpus is in spot ["+coor[0]+","+temp+"] ");
						}
						if(check2 == 1)
						{
							int temp = coor[0] -1;
							System.out.println("Watch out!! The Wumpus is in spot ["+temp+","+coor[1]+"] ");
						}
						if(check3 == 1)
						{
							int temp = coor[0]+1;
							System.out.println("Watch out!! The Wumpus is in spot ["+temp+","+coor[1]+"] ");
						}
						if(check4 == 1)
						{
							int temp = coor[1] +1;
							System.out.println("Watch out!! The Wumpus is in spot ["+coor[0]+","+temp+"] ");
						}
						System.out.println();
						check1 = 0;
						check2 =0;
						check3 = 0;
						check4 =0;
						check =0;
					}
       		}
    
       	if(win != true && actualCoors.getStatus() == true)	
       	{
       	
        System.out.println("What would you like to do? Please enter command [R,L,F,S] :");
        System.out.print(">");
        move = keyboard.nextLine();
        
    
        //turn right moves
        if(move.equals("R") && direction.equals("East"))
        {
        	direction = "South";
        }
        else if(move.equals("R") && direction.equals("North"))
        {
        	direction = "East";
        }
       else if(move.equals("R") && direction.equals("West"))
        {
        	direction = "North";
        }
       else if(move.equals("R") && direction.equals("South"))
        {
        	direction = "West";
        }
       	//turn left moves
       else	if(move.equals("L") && direction.equals("East"))
        {
        	direction = "North";
        }
       else if(move.equals("L") && direction.equals("North"))
        {
        	direction = "West";
        }
       else if(move.equals("L") && direction.equals("West"))
        {
        	direction = "South";
        }
      else  if(move.equals("L") && direction.equals("South"))
        {
        	direction = "East";
        }
        //moving forward
        else if(move.equals("F") && direction.equals("East"))
        {
        	if(actualCoors.getY() == (i-1))
        		System.out.println("BUMP!!! You hit a wall!");
        	else
        	{
        		//++actualCoor[1];
        		score = score -1;
        		int temp = actualCoors.getY();
        		actualCoors.setY(++temp);
        		++coor[1];
        		map[actualCoors.getX()][actualCoors.getY()].setVisited();
        	}
        }
        else if(move.equals("F") && direction.equals("West"))
        {
        	if(actualCoors.getY() == 0)
        		System.out.println("BUMP!!! You hit a wall!");
        	else
        	{
        		//--actualCoor[1];
        		score = score -1;
        		int temp = actualCoors.getY();
        		actualCoors.setY(--temp);
        		--coor[1];
        		map[actualCoors.getX()][actualCoors.getY()].setVisited();
        	}
        }
        else if(move.equals("F") && direction.equals("North"))
        {
        	int sup = 0;
        	if(actualCoors.getX()== sup)
        		System.out.println("BUMP!!! You hit a wall!");
        	else if(actualCoors.getX() !=sup)
        	{
        	
        		//--actualCoor[0];
        		score = score -1;
        		int temp = actualCoors.getX();
        		actualCoors.setX(--temp);
        		++coor[0];
        		map[actualCoors.getX()][actualCoors.getY()].setVisited();
        	}
        }
        else if(move.equals("F") && direction.equals("South"))
        {
        	System.out.println(j);
        	int sup = j-1;
        	if(actualCoors.getX() == sup)
        		System.out.println("BUMP!!! You hit a wall!");
        	else if(actualCoors.getX() != sup)
        	{
        		//actualCoor[0];
        		score = score -1;
        		int temp = actualCoors.getX();
        		actualCoors.setX(++temp);
        		--coor[0];
        		map[actualCoors.getX()][actualCoors.getY()].setVisited();
        	}
        }
        else if(move.equals("S") && direction.equals("East"))
        {
        	if(actualCoors.getArrow() == true)
        	{
        		
        	for(int y =0; y< (i - actualCoors.getY());++y)
        	{
        		//if(map[actualCoors.getX()][y].getWumpus() == true)
        		if(y == wumpus.getY() && actualCoors.getX() == wumpus.getX())
        		{
        			score = score -10;
        			map[actualCoors.getX()][y].setWumpus(false);
        			wumpus.setStatus(false);
        			actualCoors.setArrow(false);
        			System.out.println("A scream from across the cave is heard.");
        			
        		}
        		
        	}
        	if(wumpus.getStatus() == true)
        	{
        		score = score -10;
        		actualCoors.setArrow(false);
        	}
        	}
        }
        else if(move.equals("S") && direction.equals("South"))
        {
        	if(actualCoors.getArrow() == true)
        	{
        	
        	for(int y =0; y< (j - actualCoors.getX());++y)
        	{
        		//if(map[actualCoors.getX()][y].getWumpus() == true)
        		if(y == wumpus.getX() && actualCoors.getY() == wumpus.getY())
        		{
        			score = score -10;
        			map[y][actualCoors.getY()].setWumpus(false);
        			wumpus.setStatus(false);
        			actualCoors.setArrow(false);
        			System.out.println("A scream from across the cave is heard.");
        			
        		}
        		
        	}
        	if(wumpus.getStatus() == true)
        	{
        		score = score -10;
        		actualCoors.setArrow(false);
        	}
        	}
        }
        
        else if(move.equals("S") && direction.equals("West"))
        {
        	if(actualCoors.getArrow() == true)
        	{
        	
        	for(int y =actualCoors.getY(); y>=0;--y)
        	{
        		//if(map[actualCoors.getX()][y].getWumpus() == true)
        		if(y == wumpus.getY() && actualCoors.getX() == wumpus.getX())
        		{
        			score = score -10;
        			map[actualCoors.getX()][y].setWumpus(false);
        			wumpus.setStatus(false);
        			actualCoors.setArrow(false);
        			System.out.println("A scream from across the cave is heard.");
        			
        		}
        		
        	}
        	if(wumpus.getStatus() == true)
        	{
        		score = score -10;
        		actualCoors.setArrow(false);
        		
        	}
        	}
        }
        else if(move.equals("S") && direction.equals("North"))
        {
        	boolean hit = false;
        	if(actualCoors.getArrow() == true)
        	{
        	for(int y = actualCoors.getX(); y>= 0;--y)
        	{
        		//if(map[actualCoors.getX()][y].getWumpus() == true)
        		if(y == wumpus.getY() && actualCoors.getX() == wumpus.getX())
        		{
        			/*
        			score = score -10;
        			map[y][actualCoors.getY()].setWumpus(false);
        			wumpus.setStatus(false);
        			actualCoors.setArrow(false);
        			System.out.println("A scream from across the cave is heard.");
        			*/
        			hit = true;
        		}
        		
        	}
        	if(hit == true)
        	{
        			score = score -10;
        			
        			map[wumpus.getX()][actualCoors.getY()].setWumpus(false);
        			wumpus.setStatus(false);
        			actualCoors.setArrow(false);
        			System.out.println("A scream from across the cave is heard.");
        	}
        	else
        	{
        	
        		score = score -10;
        		actualCoors.setArrow(false);
        		
        	
        	}
        }

       	}
       	}
       }
       
       if(actualCoors.getStatus() == false)
       {
       	System.out.println("You lost!");
       	System.out.println("You should have listened to my instructions.");
       	System.out.println("Your score is "+score);
       }
       if(win == true)
       {
       	System.out.println("You won!!!!");
       	System.out.println("It was a pleasure going on this adventure with you and now we are rich!!!");
       	System.out.println("Your score is "+score);
       }
       outputFile.close();
    }
    public static String[][] parseFile(File f) throws IOException {
            BufferedReader r = new BufferedReader(new FileReader(f));
            List<String> list = new ArrayList<String>();
            String s;

            while ((s = r.readLine()) != null) {
                  list.add(s);
            }
            r.close();
            String arr[][] = new String[list.size()][];
            for (int i = 0; i < arr.length; ++i) {
                  arr[i] = new String[arr.length];
                  Scanner scan = new Scanner(list.get(i));
                  
                  // if you are parsing on something other than whitespace, change the delimiter
                  scan.useDelimiter(",");
                  for (int k = 0; k < arr.length && scan.hasNext(); ++k) {
                  		
                        arr[i][k] = scan.next();
                        
                  }
            }

            return arr;
      }
    
}
