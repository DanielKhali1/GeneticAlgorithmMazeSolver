import java.util.Random;

public class GeneticAlgorithm 
{
	private String[] myMoveSet;
	private final String[] vocabulary = {"u", "d", "l", "r"};
	private int sizeOfString;
	private int populationSize;
	private double mutationRate;
	private int generation;
	private int[][] Noise;
	Maze myMaze;
	Random rand;
	
	
	public GeneticAlgorithm(int popuSize, double mutationR, int sizeOString, Maze maze, int[][] noise)
	{
		Noise = noise;
		myMaze = maze;
		populationSize = popuSize;
		
		sizeOfString = sizeOString;
		
		myMoveSet = generateRandomMoveSet();
		mutationRate = mutationR;
		generation = 0;
		rand = new Random();
	}
	
	public double getFitness(String chromosome)
	{
		//TODO: do a fitness function for the maze
		
		int x = getPosition(chromosome)[0],y = getPosition(chromosome)[1];
		double distance = Math.sqrt( (double)Math.pow((x - myMaze.getGoal()[0]), 2) + (double)Math.pow(( y - myMaze.getGoal()[1]), 2 ));
		int dontTouchBoi = addifTouchesHash(chromosome);
		
		int earlyBoi = gotThereBeforeIntended(chromosome);
						
		return distance + dontTouchBoi;
		//return distance;
		//return dontTouchBoi;
		
	}
	
	public String selectParent() //Using tournament
	{
		String individual1 = myMoveSet[rand.nextInt(myMoveSet.length)];
		String individual2 = myMoveSet[rand.nextInt(myMoveSet.length)];
				
		return (getFitness(individual1) < getFitness(individual2))? individual1 : individual2;
	}
	
	public String CrossOver(String chromosome1, String chromosome2)
	{
		rand = new Random();
		
		int crossOverPoint = rand.nextInt(chromosome1.length());
				
		return chromosome1.substring(0, crossOverPoint) + chromosome2.substring(crossOverPoint);
	}
	
	public String Mutate(String chromosome, double mutRate)
	{	
		String offspring = chromosome;
		
		for(int i = 0; i < chromosome.length(); i++)
		{
			if(rand.nextDouble() <= mutRate)
			{
				int randomNumber = rand.nextInt(4)+1;
				
				switch(randomNumber) 
				{
					case 1: offspring = offspring.substring(0, i) + vocabulary[0] + offspring.substring(i+1);
							break;
					case 2: offspring = offspring.substring(0, i) + vocabulary[1] + offspring.substring(i+1);
							break;
					case 3: offspring = offspring.substring(0, i) + vocabulary[2] + offspring.substring(i+1);
							break;
					case 4: offspring = offspring.substring(0, i) + vocabulary[3] + offspring.substring(i+1);
							break;	
				}
			}
		}
		
		return offspring;
	}
	
	public void evolve()
	{
		String[] nextPopulation = new String[myMoveSet.length];
		
		for(int i = 0; i < nextPopulation.length; i++)
		{
			//Selection
			String parent1 = selectParent();
			String parent2 = selectParent();
			
			//Crossover
			String offSpring = CrossOver(parent1, parent2);
			
			//Mutation
			String mutatedOffspring = Mutate(offSpring, mutationRate);
			
			//add offspring to next population
			nextPopulation[i] = mutatedOffspring;
		}
		myMoveSet = nextPopulation;
		generation++;
	}
	
	//utility
	
	private String[] generateRandomMoveSet()
	{
		rand = new Random();
		
		String[] outputStringArray = new String[populationSize];
		
		for(int i = 0; i < populationSize; i++)
		{
			String chromosome = "";
			for(int j = 0; j < sizeOfString; j++)
			{
				int randomNumber = rand.nextInt(4)+1;
				
				switch(randomNumber) {
					case 1: chromosome += vocabulary[0];
							break;
					case 2: chromosome += vocabulary[1];
							break;
					case 3: chromosome += vocabulary[2];
							break;
					case 4: chromosome += vocabulary[3];
							break;					
				}
			}
			outputStringArray[i] = chromosome;
		}
		
		return outputStringArray;
	}

	public void displayBestIndividual()
	{

		
		System.out.println("Generation: " + generation + " Best: " + myMoveSet[BestIndividual()] + " Fittness: " + getFitness(myMoveSet[BestIndividual()]));
		
		System.out.println(displayIndividualsMove(myMoveSet[BestIndividual()]));
	}
	
	public int BestIndividual()
	{
		int mostFitIndex = 0;
		
		for(int i = 0; i < populationSize; i++)
			if(getFitness(myMoveSet[i]) < mostFitIndex)
				mostFitIndex = i;
		return mostFitIndex;
	}

	public String displayIndividualsMove(String chromosome)
	{		
		int x = 0, y = 0;
		
		x = myMaze.getStartingPosition()[0];
		y = myMaze.getStartingPosition()[1];
		
		Maze temp = new Maze(myMaze.getMazeMatrix().length, myMaze.getMazeMatrix()[0].length, myMaze.getGoal()[0], myMaze.getGoal()[1], myMaze.getStartingPosition()[0], myMaze.getStartingPosition()[1] );
		temp.addNoise(Noise);
		char[][] maze = temp.getMazeMatrix();
		
		
		
		for(int i = 0; i < chromosome.length(); i++)
		{			
			if(chromosome.charAt(i) == 'd' && x + 1 <= myMaze.getMazeMatrix()[0].length-1 && maze[x+1][y] != '#')
				x += 1;
			else if(chromosome.charAt(i) == 'u' && x - 1 >= 0 && maze[x-1][y] != '#')
				x -= 1;
			else if(chromosome.charAt(i) == 'r' && y + 1 <= myMaze.getMazeMatrix()[0].length-1 && maze[x][y+1] != '#')
				y += 1;
			else if(chromosome.charAt(i) == 'l' && y - 1 >= 0 && maze[x][y-1] != '#')
				y -= 1;
			
			maze[x][y] = 'o';
		}
		
		maze[x][y] = 'P';
		
		String outputString = "";
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length; j++)
			{
				outputString += maze[i][j] + " ";
			}
			outputString += "\n";
			
		}		
		return outputString;
	}
	
	
	public int[] getPosition(String chromosome)
	{
		int[] outputIntArray = {myMaze.getStartingPosition()[0], myMaze.getStartingPosition()[1]};
		
		for(int i = 0; i < chromosome.length(); i++)
		{
			if(chromosome.charAt(i) == 'd' && outputIntArray[0] + 1 <= myMaze.getMazeMatrix()[0].length-1 && myMaze.getMazeMatrix()[outputIntArray[0]+1][outputIntArray[1]] != '#')
				outputIntArray[0] += 1;
			else if(chromosome.charAt(i) == 'u' && outputIntArray[0] - 1 >= 0 && myMaze.getMazeMatrix()[outputIntArray[0]-1][outputIntArray[1]] != '#')
				outputIntArray[0] -= 1;
			else if(chromosome.charAt(i) == 'r' && outputIntArray[1] + 1 <= myMaze.getMazeMatrix()[0].length-1 && myMaze.getMazeMatrix()[outputIntArray[0]][outputIntArray[1]+1] != '#')
				outputIntArray[1] += 1;
			else if(chromosome.charAt(i) == 'l' && outputIntArray[1] - 1 >= 0 && myMaze.getMazeMatrix()[outputIntArray[0]][outputIntArray[1]-1] != '#')
				outputIntArray[1] -= 1;
		}
		
		return outputIntArray;
	}
	
	public int addifTouchesHash(String chromosome)
	{
		int[] outputIntArray = {myMaze.getStartingPosition()[0], myMaze.getStartingPosition()[1]};

		int sum = 0;
		
		int x = 0, y = 0;
		
		x = myMaze.getStartingPosition()[0];
		y = myMaze.getStartingPosition()[1];
		
		Maze temp = new Maze(myMaze.getMazeMatrix().length, myMaze.getMazeMatrix()[0].length, myMaze.getGoal()[0], myMaze.getGoal()[1], myMaze.getStartingPosition()[0], myMaze.getStartingPosition()[1] );
		temp.addNoise(Noise);
		char[][] maze = temp.getMazeMatrix();
		
		
		
		for(int i = 0; i < chromosome.length(); i++)
		{			
			if(chromosome.charAt(i) == 'd')
			{
				if(x + 1 <= myMaze.getMazeMatrix()[0].length-1 && maze[x+1][y] != '#' && maze[x+1][y] != 'o')
					x += 1;
				else
					sum+=20;
			}
			else if(chromosome.charAt(i) == 'u')
			{
				if(x - 1 >= 0 && maze[x-1][y] != '#' && maze[x-1][y] != 'o')
					x -= 1;
				else
					sum+=20;
			}
			else if(chromosome.charAt(i) == 'r')
			{
				if(y + 1 <= myMaze.getMazeMatrix()[0].length-1 && maze[x][y+1] != '#' && maze[x][y+1] != 'o')
					y += 1;
				else
					sum+=20;
			}
			else if(chromosome.charAt(i) == 'l')
			{
				if(y - 1 >= 0 && maze[x][y-1] != '#' && maze[x][y-1] != 'o')
					y -= 1;
				else
					sum+=20;
			}
			
			maze[x][y] = 'o';
			
		}
		
		return sum;
	}
	
	public int gotThereBeforeIntended(String chromosome)
	{
		
		
		return 0;
	}
	
	public String getBestIndividual()
	{
		return myMoveSet[BestIndividual()];
	}
	
	public String[] getMoveSet() { return myMoveSet;}
}
