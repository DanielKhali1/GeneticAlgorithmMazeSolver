import java.util.Arrays;

public class Maze 
{
	
	private char[][] myMazeMatrix;
	private int[] myStartingPosition = {0 , 0};
	private int[] myGoal = new int[2];
	
	//Constructors
	
	//Create the maze Matrix with rows and columns as inputs
	public Maze(int rows, int columns)
	{
		myMazeMatrix = new char[rows][columns];
		myGoal[0] = myMazeMatrix.length-1;
		myGoal[1] = myMazeMatrix[0].length-1;
		
		for(int i = 0; i < myMazeMatrix.length; i++)
			for(int j = 0; j < myMazeMatrix[0].length; j++)
				myMazeMatrix[i][j] = '_';
		
		myMazeMatrix[myStartingPosition[0]][myStartingPosition[1]] = 'O';
		myMazeMatrix[myGoal[0]][myGoal[1]] = 'X';
	}
	
	//Creates a default Matrix	
	public Maze()
	{
		myMazeMatrix = new char[10][10];
		myGoal[0] = myMazeMatrix.length-1;
		myGoal[1] = myMazeMatrix[0].length-1;
		
		for(int i = 0; i < myMazeMatrix.length; i++)
			for(int j = 0; j < myMazeMatrix[0].length; j++)
				myMazeMatrix[i][j] = '_';
		
		myMazeMatrix[myStartingPosition[0]][myStartingPosition[1]] = 'O';
		myMazeMatrix[myGoal[0]][myGoal[1]] = 'X';
	}
	
	//Creates a Matrix with a starting position and a goal
	public Maze(int rows, int columns, int goalX, int goalY, int startX, int startY)
	{
		myMazeMatrix = new char[rows][columns];
		myGoal[0] = goalX;
		myGoal[1] = goalY;
		myStartingPosition[0] = startX;
		myStartingPosition[1] = startY;
		
		for(int i = 0; i < myMazeMatrix.length; i++)
			for(int j = 0; j < myMazeMatrix[0].length; j++)
				myMazeMatrix[i][j] = '_';
		
		myMazeMatrix[myStartingPosition[0]][myStartingPosition[1]] = 'O';
		myMazeMatrix[myGoal[0]][myGoal[1]] = 'X';
	}
	
	//Methods
	
	public void addNoise(int[][] Noise)
	{
		//int[] of int[]		
		for(int i = 0; i < Noise.length; i++)
		{
			if(Noise[i][0] > myMazeMatrix.length || Noise[i][0] < 0)
				continue;
			if(Noise[i][1] > myMazeMatrix[0].length || Noise[i][1] < 0)
				continue;
			
			myMazeMatrix[Noise[i][0]][Noise[i][1]] = '#';
		}
	}
	
	
	public String toString()
	{
		String outputString = "MATRIX:\n\n";
		
		for(int i = 0; i < myMazeMatrix.length; i++)
			outputString += Arrays.toString(myMazeMatrix[i]) + "\n" ;
		
		return outputString;
	}
	
	//Setters (modifiers)
	public void setMazeMatrix(char[][] x){myMazeMatrix = x;}
	public void setStartingPoint(int[] x) {myStartingPosition = x;}
	public void setGoal(int[]x) {myGoal = x;}
	
	//Getters (Accessors)
	public char[][] getMazeMatrix(){return myMazeMatrix;}
	public int[] getStartingPosition() {return myStartingPosition;}
	public int[] getGoal() {return myGoal;}

}
