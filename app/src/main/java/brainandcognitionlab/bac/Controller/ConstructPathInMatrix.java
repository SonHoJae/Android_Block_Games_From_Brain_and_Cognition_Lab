package brainandcognitionlab.bac.Controller;

import java.util.Random;


public class ConstructPathInMatrix {

	private int numOfTurns;//numOfTurns = scanner.nextInt();
	private Line lines[];
	
	public ConstructPathInMatrix(int numOfTurns){
		this.numOfTurns = numOfTurns;
	}
	public Line[] getPath(){
		// TODO Auto-generated method stub 
		  
		 System.out.println("Insert numOfTurns"); 
		 
		 Position position[] = new Position[numOfTurns+2];
		 lines = new Line[numOfTurns+1];
		 int turningPoint[] = new int[numOfTurns+1];  
		 
		 for(int i=0; i<numOfTurns+2; i++)
		 {
			 position[i] = new Position();
		 } 

		 
		 generatePath(numOfTurns,turningPoint,position,lines);
		 int temp = 0;
		 for(int i=0; i<numOfTurns; i++)
			 for(int j=i+1; j<numOfTurns+1; j++)
			 {
				 while(isCrossed(lines[i],lines[j]) || isDuplicatedPosition(position)){
					  
					 generatePath(numOfTurns,turningPoint,position,lines);
					 i=0; 
					 temp++;
					 if(temp%1000000 == 0)
						 System.out.println("ee");
				 }
			 }
		 for(int i=0; i<numOfTurns+1; i++)
		 {
			 System.out.println(i+" P1 -> X = "+lines[i].getP1().getX()+" Y = "+lines[i].getP1().getY()
					 +"  P2 -> X = "+lines[i].getP2().getX()+" Y = "+lines[i].getP2().getY());
		 }

		 return lines;
	}
	
	private static boolean isDuplicatedPosition(Position[] position) {
		// TODO Auto-generated method stub
		for(int i=0; i< position.length-1; i++)
			for(int j=i+1; j < position.length; j++)
			{
				if(position[i].getX() == position[j].getX() && position[i].getY() == position[j].getY())
					return true;
			}
		return false;
	}

	static boolean isCrossed(Line a, Line b)  {

		Line l1 = new Line();
        l1.setP1(a.getP1());
        l1.setP2(a.getP2());
		Line l2 = new Line();
        l2.setP1(b.getP1());
        l2.setP2(b.getP2());
        /*Line l1 = a;
        Line l2 = b;*/
		//this is for checking whether it is crossed or not
		if(l1.getP1().getX() + l1.getP1().getY() > l1.getP2().getX() + l1.getP2().getY()) {
			Position tempPosition = l1.getP1();
			l1.setP1(l1.getP2());
			l1.setP2(tempPosition);
		}

		if(l2.getP1().getX() + l2.getP1().getY() > l2.getP2().getX() + l2.getP2().getY()) {
			Position tempPosition = l2.getP1();
			l2.setP1(l2.getP2());
			l2.setP2(tempPosition);
		}

		Line horizontalLine;
		Line verticalLine;
		if(l1.getP1().getX() == l1.getP2().getX() && l2.getP1().getY() == l2.getP2().getY())
		{
			horizontalLine = l2;
			verticalLine = l1;
		}
		else if(l1.getP1().getX() == l1.getP2().getX() && l2.getP1().getX() == l2.getP2().getX())
		{
			return l2.getP1().getY() < l1.getP1().getY() && l1.getP1().getY() < l2.getP2().getY()
					|| l2.getP1().getY() < l1.getP2().getY() && l1.getP2().getY() < l2.getP2().getY()
					|| l2.getP1().getY() < l1.getP1().getY() && l1.getP2().getY() < l2.getP2().getY()
					|| l1.getP1().getY() < l2.getP1().getY() && l2.getP2().getY() < l1.getP2().getY();
		}
		else if(l1.getP1().getY() == l1.getP2().getY() && l2.getP1().getY() == l2.getP2().getY())
		{
			return l2.getP1().getX() < l1.getP1().getX() && l1.getP1().getX() < l2.getP2().getX()
					|| l2.getP1().getX() < l1.getP2().getX() && l1.getP2().getX() < l2.getP2().getX()
					|| l2.getP1().getX() < l1.getP1().getX() && l1.getP2().getX() < l2.getP2().getX()
					|| l1.getP1().getX() < l2.getP1().getX() && l2.getP2().getX() < l1.getP2().getX();
		}
		else
		{
			horizontalLine = l1;
			verticalLine = l2; 
		}


		return (horizontalLine.getP1().getX() < verticalLine.getP1().getX() && verticalLine.getP1().getX() < horizontalLine.getP2().getX())
				&& (verticalLine.getP1().getY() < horizontalLine.getP1().getY() && horizontalLine.getP1().getY() < verticalLine.getP2().getY());
	}
	
	static void generatePath(int numOfTurns,int turningPoint[],Position position[],Line lines[])
	{
		
		for(int i=0; i<numOfTurns+1; i++)
		 {
			 turningPoint[i] = new Random().nextInt(10);
		 }
		
		 position[1].setX(0);
		 position[1].setY(turningPoint[0]);
		 
		 for(int i=2; i<numOfTurns+2; i++){
			 if(i%2 == 1)
			 {
				 position[i].setX(position[i-1].getX());
				 position[i].setY(turningPoint[i-1]);
			 }
			 else
			 {
				 position[i].setX(turningPoint[i-1]);
				 position[i].setY(position[i-1].getY());
			 }
		 } 
		 if(numOfTurns%2 == 1)
			 position[numOfTurns].setY(9);
		 else
			 position[numOfTurns].setX(9);
		 
		 position[numOfTurns+1].setX(9);
		 position[numOfTurns+1].setY(9);
		 for(int i=0; i<numOfTurns+1; i++){
			 lines[i] = new Line();
			 lines[i].setP1(position[i]);
			 lines[i].setP2(position[i+1]);
		 }
	}
}
