import java.util.Arrays;
import java.util.Scanner;

public class Manager 
{
	public static void main(String[] args)
	{
		//int rows, int columns, int goalX, int goalY, int startX, int startY
		Maze maze = new Maze(7, 6, 0, 5, 6, 5);
		int[][]Noise = {{5,1},{5,2},{5,3}, {5,4},{5,5},{3,0},{3,1},{3,2},{3,3},{3,4},{1,1},{1,2},{1,3},{1,4},{1,5}};
		
		//Maze maze = new Maze(7, 7, 6, 0, 6, 6);
		//int[][]Noise = {{6,1},{5,1},{4,1},{3,1},{2,1},{1,1},{5,3},{4,3},{3,3},{2,3},{1,3},{0,3},{6,5},{5,5},{4,5},{3,5},{2,5},{1,5}};
				

		//Maze maze = new Maze(10, 8, 9, 0, 9, 4);
		//int[][]Noise = {{1,1},{2,1},{3,1},{4,1},{5,1},{6,1},{7,1},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{7,6},{6,6},{5,6},{4,6},{3,6},{2,6},{1,6},{1,5},{1,4},{1,3},{1,2},{9,3}};

		//Maze maze = new Maze(20, 20, 0, 0, 19, 19);
		//int[][] Noise = {};
		
		maze.addNoise(Noise);
		
		GeneticAlgorithm x = new GeneticAlgorithm(50, .002, 29, maze, Noise);
		
		System.out.println(maze);
		
		Scanner input = new Scanner(System.in);
		
		int set = input.nextInt();
		
		if(set == 1)
		{
		

			x.displayBestIndividual();

			//if start position is not on your end position
			while(x.getPosition(x.getBestIndividual())[0] != maze.getGoal()[0] || x.getPosition(x.getBestIndividual())[1] != maze.getGoal()[1])
			{
				x.evolve();
				x.displayBestIndividual();

				System.out.println(Arrays.toString(x.getPosition(x.getBestIndividual())));
				System.out.println(Arrays.toString(maze.getGoal()));
				System.out.println(x.getFitness(x.getBestIndividual()));
			}
		
		}
		else if(set == 2)
		{
			x.displayBestIndividual();

			//if start position is not on your end position
			while(x.getFitness(x.getBestIndividual()) > 0.0)
			{
				x.evolve();
				x.displayBestIndividual();
				
			}			
		}
		
	}

}
