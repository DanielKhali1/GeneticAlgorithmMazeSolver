import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class GridTest extends Application {
   // Override the start method in the Application class
  
  boolean startSelected = false;
  boolean endSelected = false;
  GridPane map;
  int noiseNumber = 0;
  Maze myMaze;
  TextField fieldMutSize;
  TextField fieldMove;
  TextField fieldPopSize;
  Button btEnd;
  Button btReset;
  Text textGen;
  Text textChrom;
  Text textFit;
  Button[][] ViewMaze;
  int first = 0;
  
  
  @Override
  public void start(Stage primaryStage) {
    // Create a circle and set its properties

    // Create a pane to hold the circle
	BorderPane borderP = new BorderPane();
	Pane bottomPane = new Pane();
	bottomPane.setPrefSize(200, 300);
	bottomPane.setStyle("-fx-background-color: 'lightgrey'");
	
    Pane CenterPane = new Pane();
    CenterPane.setPrefSize(300, 300);
    map = new GridPane();
    map.setPrefSize(300, 300);
    map.setLayoutX(100);
    map.setLayoutY(100);
    //map.setStyle("-fx-background-color: 'black'");
    
    Pane agentPane = new Pane();
    agentPane.setStyle("-fx-background-color: 'white'");
    agentPane.setPrefSize(450, 190);
    agentPane.setLayoutX(300);
    agentPane.setLayoutY(10);
    
    Text textPopSize = new Text("Population Size");
    textPopSize.setLayoutX(10);
    textPopSize.setLayoutY(20);
    
    fieldPopSize = new TextField();
    fieldPopSize.setLayoutX(10);
    fieldPopSize.setLayoutY(30);
    fieldPopSize.setPrefWidth(50);
    fieldPopSize.setText("50");
    
    Text textMove = new Text("Move");
    textMove.setLayoutX(10);
    textMove.setLayoutY(80);
    
    fieldMove = new TextField();
    fieldMove.setLayoutX(10);
    fieldMove.setLayoutY(85);
    fieldMove.setPrefWidth(50);
    
    Text textMutSize = new Text("Mutation Size");
    textMutSize.setLayoutX(10);
    textMutSize.setLayoutY(135);
    
    fieldMutSize = new TextField();
    fieldMutSize.setLayoutX(10);
    fieldMutSize.setLayoutY(140);
    fieldMutSize.setPrefWidth(50);
    fieldMutSize.setText("0.02");
    
    Text textHeight = new Text("Height");
    textHeight.setLayoutX(10);
    textHeight.setLayoutY(50);
    
    Button btSolve = new Button("Solve");
    btSolve.setLayoutX(380);
    btSolve.setLayoutY(150); 
    
    btReset = new Button("Reset");
    btReset.setLayoutX(380);
    btReset.setLayoutY(100);
    
    TextField height  = new TextField();
    height.setLayoutX(10);
    height.setLayoutY(60);
    height.setPrefWidth(50);
    height.setText("5");
    
    Text textWidth = new Text("Width");
    textWidth.setLayoutX(10);
    textWidth.setLayoutY(110);
    
    
    TextField width = new TextField();
    width.setLayoutX(10);
    width.setLayoutY(120);
    width.setPrefWidth(50);
    width.setText("5");
    
    Button btGenerateMap = new Button("Generate");
    btGenerateMap.setLayoutX(10);
    btGenerateMap.setLayoutY(170);
    
    Button btSetStart = new Button("Start");
    btSetStart.setLayoutX(90);
    btSetStart.setLayoutY(170);

    Button btSetEnd = new Button("End");
    btSetEnd.setLayoutX(150);
    btSetEnd.setLayoutY(170);
    
    Button btLoad = new Button("Load");
    btLoad.setLayoutX(200);
    btLoad.setLayoutY(170);
    
    Button btSave = new Button("Save");
    btSave.setLayoutX(150);
    btSave.setLayoutY(170);
    
    Text heightWarning  = new Text("Invalid Height");
    heightWarning.setLayoutX(70);
    heightWarning.setLayoutY(80);
    heightWarning.setVisible(false);
    heightWarning.setFill(Color.RED);
    
    Text widthWarning = new Text("Invalid Width");
    widthWarning.setLayoutX(70);
    widthWarning.setLayoutY(140);
    widthWarning.setVisible(false);
    widthWarning.setFill(Color.RED);
    
    btEnd = new Button("End");
    btEnd.setLayoutX(320);
    btEnd.setLayoutY(150); 
    
    textGen = new Text("Generation: ");
    textGen.setLayoutX(150);
    textGen.setLayoutY(20);
    textChrom = new Text("Moves: ");
    textChrom.setLayoutX(150);
    textChrom.setLayoutY(40);
    textFit = new Text("Fitness:");
    textFit.setLayoutX(150);
    textFit.setLayoutY(60);
    

    //MAX HEIGHT 20 MAX WIDTH 28
    btGenerateMap.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
        	
        	boolean parseInput;
        	
        	try {
        	parseInput = Integer.parseInt(height.getText()) > 20 || Integer.parseInt(height.getText()) < 0;
        	}
        	catch(NumberFormatException x)
        	{
        		heightWarning.setVisible(true);
            	try {
            		parseInput = Integer.parseInt(width.getText()) > 28 || Integer.parseInt(width.getText()) < 0;
            	}
            	catch(NumberFormatException f)
            	{
            		widthWarning.setVisible(true);
            		return;
            	}
        		return;
        	}
        	
        	try {
        		parseInput = Integer.parseInt(width.getText()) > 28 || Integer.parseInt(width.getText()) < 0;
        	}
        	catch(NumberFormatException x)
        	{
        		widthWarning.setVisible(true);
        		return;
        	}
        	parseInput = Integer.parseInt(height.getText()) > 20 || Integer.parseInt(width.getText()) > 28 || Integer.parseInt(height.getText()) < 0 || Integer.parseInt(width.getText()) < 0;
        	
			if(parseInput)
			{
				if(Integer.parseInt(height.getText()) > 20 || Integer.parseInt(height.getText()) < 0)
					heightWarning.setVisible(true);
				if(Integer.parseInt(width.getText()) > 28 || Integer.parseInt(width.getText()) < 0)
					widthWarning.setVisible(true);
			}
			
			else
			{
				
				map.getChildren().clear();
				widthWarning.setVisible(false);
				heightWarning.setVisible(false);
				
				btSetStart.setDisable(false);
				btSetEnd.setDisable(false);
				myMaze = new Maze(Integer.parseInt(height.getText()), Integer.parseInt(width.getText()));
				ViewMaze = new Button[Integer.parseInt(height.getText())][Integer.parseInt(width.getText())];
				fieldMove.setText("" + myMaze.whiteSpace());
				System.out.println(myMaze);
	
				for(int i = 0; i < Integer.parseInt(height.getText()); i++)
	        		for(int j = 0; j < Integer.parseInt(width.getText()); j++)
	        		{	
	        			boolean x = true;
	        			ViewMaze[i][j] = new Button("  ");
	        			ViewMaze[i][j].setStyle("-fx-color: 'white'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	        			final int ii = i;
	        			final int jj = j;
	        			
	        			btSetEnd.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e)
							{								
								if(!endSelected)
								{
									btSetEnd.setStyle("-fx-color: 'red';");
									changeEndSelection();
									btSetStart.setStyle("-fx-color: 'white'");
									startSelected = false;
									
								}
								else
								{
									btSetEnd.setStyle("-fx-color: 'white';");
									changeEndSelection();	
								}
							}
						});
						btSetStart.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e)
							{
								if(!startSelected)
								{
									btSetStart.setStyle("-fx-color: 'green';");
									changeStartSelection();
									btSetEnd.setStyle("-fx-color: 'white'");
									endSelected = false;
								}
								else
								{
									btSetStart.setStyle("-fx-color: 'white';");
									changeStartSelection();
								}
						}
					});
	        			
						ViewMaze[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
	        				boolean xx = x;
							@Override
							public void handle(MouseEvent arg0) {
								// TODO Auto-generated method stub
								xx = !xx;
								
								if(endSelected)
								{
									int[] goal = {ii, jj};
									myMaze.setGoal(goal);
									System.out.println(myMaze);

									
									ViewMaze[ii][jj].setStyle("-fx-color: 'black'; -fx-background-color: 'red'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
									btSetEnd.setStyle("-fx-color: 'white';");
									startSelected = false;
									changeEndSelection();
									btSetEnd.setDisable(true);
	        						fieldMove.setText("" + (myMaze.whiteSpace()+1));

								}
								else if(startSelected)
								{

									int[] start = {ii, jj};
									myMaze.setStartingPoint(start);
									System.out.println(myMaze);
									
									ViewMaze[ii][jj].setStyle("-fx-color: 'black'; -fx-background-color: 'green'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
									btSetStart.setStyle("-fx-color: 'white';");
									endSelected = false;
									changeStartSelection();
									btSetStart.setDisable(true);
	        						fieldMove.setText("" +  (myMaze.whiteSpace()+1));


								}
								
								else if(!xx)
								{
									int[][] noise = {{ii, jj}};
									myMaze.addNoise(noise);
									System.out.println(myMaze);
									ViewMaze[ii][jj].setStyle("-fx-color: 'black'; -fx-background-color: 'black'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
									noiseNumber++;
	        						fieldMove.setText("" +  (myMaze.whiteSpace()+1));
								}
	        					else
	        					{
	        						if(myMaze.getMazeMatrix()[ii][jj] == 'O')
	        							btSetStart.setDisable(false);
	        						else if(myMaze.getMazeMatrix()[ii][jj] == 'X')
	        							btSetEnd.setDisable(false);
	        						else if(myMaze.getMazeMatrix()[ii][jj] == '#')
	        							noiseNumber--;
	        						
	        						
	        						int[][] noise = {{ii, jj}};
	        						myMaze.removeNoise(noise);
	        						System.out.println(myMaze);
	        						ViewMaze[ii][jj].setStyle("-fx-color: 'white'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	        						fieldMove.setText("" + (myMaze.whiteSpace()+1));

	        					}
						}
        			});
        			map.add(ViewMaze[i][j], j, i);
            		}
			}
        }
    });
    
    btSolve.setOnAction(new EventHandler<ActionEvent>() {
    	 @Override
         public void handle(ActionEvent e) 
    	 {
    		 //	public GeneticAlgorithm(int popuSize, double mutationR, int sizeOString, Maze maze)
    		 displayAndEvolve();
    		 
 		}	
    });
    
    
    //CenterPane.getChildren().add(map);
    
    agentPane.getChildren().add(btReset);
    agentPane.getChildren().add(textFit);
    agentPane.getChildren().add(textGen);
    agentPane.getChildren().add(textChrom);
    agentPane.getChildren().add(btEnd);
    agentPane.getChildren().add(btSolve);
    agentPane.getChildren().add(fieldMutSize);
    agentPane.getChildren().add(textMutSize);
    agentPane.getChildren().add(textPopSize);
    agentPane.getChildren().add(fieldPopSize);
    agentPane.getChildren().add(textMove);
    agentPane.getChildren().add(fieldMove);
    bottomPane.getChildren().add(agentPane);
    bottomPane.getChildren().add(widthWarning);
    bottomPane.getChildren().add(heightWarning);
    bottomPane.getChildren().add(btSetEnd);
    bottomPane.getChildren().add(btSetStart);
    bottomPane.getChildren().add(textWidth);
    bottomPane.getChildren().add(textHeight);
    bottomPane.getChildren().add(height);
    bottomPane.getChildren().add(width);
    bottomPane.getChildren().add(btGenerateMap);
    borderP.setBottom(bottomPane);
    borderP.setTop(map);
    

    // Create a scene and place it in the stage
    Scene scene = new Scene(borderP, 784, 870);
    primaryStage.setTitle("Map Maker"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }
  
  public static void main(String[] args) {
    launch(args);
  }
  
  public void changeStartSelection()
  {
	  startSelected = !startSelected;
  }
  public void changeEndSelection()
  {
	  endSelected = !endSelected;
  }

  public void displayAndEvolve()
  {
	  
	  
		int[][] noise = new int[noiseNumber][2];
		
		int f = 0;
		for(int i = 0; i < myMaze.getMazeMatrix().length; i++)
		{
			for(int j = 0; j < myMaze.getMazeMatrix()[i].length; j++)
			{
				if(myMaze.getMazeMatrix()[i][j] == '#')
				{
					noise[f][0] = i;
					noise[f][1] = j;
					f++;
				}
			}
		}
		//GeneticAlgorithm x = new GeneticAlgorithm(Integer.parseInt(fieldPopSize.getText()), Double.parseDouble(fieldMutSize.getText()), Integer.parseInt(fieldMove.getText()), myMaze, noise);
		GeneticAlgorithm x = new GeneticAlgorithm(Integer.parseInt(fieldPopSize.getText()), Double.parseDouble(fieldMutSize.getText()), Integer.parseInt(fieldMove.getText()), myMaze, noise);
		Timeline timeline = new Timeline();
		//double previousFitness;
		//int prevCount;
		timeline.setCycleCount(Timeline.INDEFINITE);
//		int[][] move = new int[Integer.parseInt(fieldMove.getText())][2];
		int[][] move = new int[x.getMoveSet().length][2];
		
		for(int i = 0; i < move.length; i++)
		{
			move[i][0] = -1;
			move[i][1] = -1;
		}

		KeyFrame keyframe = new KeyFrame(Duration.millis(myMaze.getMazeMatrix().length * myMaze.getMazeMatrix()[0].length), action -> {
						
	    		for(int i = 0; i < move.length; i++)
	    		{
	    			if(!(move[i][0] == -1 || move[i][1] == -1))
	    			{
	    				if(myMaze.getMazeMatrix()[move[i][0]][move[i][1]] == 'O')
		    				ViewMaze[move[i][0]][move[i][1]].setStyle("-fx-color: 'black'; -fx-background-color: 'green'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	    				else if(myMaze.getMazeMatrix()[move[i][0]][move[i][1]] == 'X')
		    				ViewMaze[move[i][0]][move[i][1]].setStyle("-fx-color: 'black'; -fx-background-color: 'red'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	    				else
	    					ViewMaze[move[i][0]][move[i][1]].setStyle("-fx-color: 'black'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	    			}
	    		}   	

 				x.evolve();
 				
 	    		int moveNumber = 0;
 	    		for(int i = 0; i < myMaze.getMazeMatrix().length; i++)
 	    		{
 	    			for(int j = 0; j < myMaze.getMazeMatrix()[i].length; j++)
 	    			{
 	    				if(x.individualMovedLikeThis(x.getBestIndividual())[i][j] == 'o')
 	    				{
 	    					move[moveNumber][0] = i;
 	    					move[moveNumber][1] = j;
 	    					moveNumber++;
    	        			ViewMaze[i][j].setStyle("-fx-color: 'black'; -fx-background-color: 'blue'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
 	    				}
 	    				
 	    			}
 	    		}
  				//textChrom.setText("Chrom: " + x.getBestIndividual());
 	    		fieldMove.setText(x.getSizeOfString()+"");
 	    		textChrom.setText("Chrom  " + x.getBestIndividual());
 				textFit.setText("Fitness: " + x.getFitness(x.getBestIndividual()));
 				textGen.setText("Generation: " + x.getGeneration());
 	 			
 	 			/*if(!(x.getPosition(x.getBestIndividual())[0] != myMaze.getGoal()[0] || x.getPosition(x.getBestIndividual())[1] != myMaze.getGoal()[1]))
 	 			{
 	 				timeline.stop();
 	 			}*/
 				if(x.percentOfPopAtZero(.8))
 				{
 					timeline.stop();
 				}
 	 			
 	 			btEnd.setOnAction(new EventHandler<ActionEvent>() {
 	 		    	 @Override
 	 		         public void handle(ActionEvent e) 
 	 		    	 {
 	 		    		 timeline.stop();
 	 		    	 }
 	 		    	 
 	 		    	 });
 	 			
 	 			
 	 			btReset.setOnAction(new EventHandler<ActionEvent>() {
	 		    	 @Override
	 		         public void handle(ActionEvent e) 
	 		    	 {
	 		    		timeline.stop();
	 		    		for(int i = 0; i < move.length; i++)
	 		    		{
	 	    				//Button temp = new Button("  ");
	 		    			if(first != 0)
	 		    				ViewMaze[move[i][0]][move[i][1]].setStyle("-fx-color: 'black'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	 		    			
	 		    			for(int j = 0; j < myMaze.getMazeMatrix().length; j++)
	 		    			{
	 		    				for(int k = 0; k < myMaze.getMazeMatrix()[j].length; k++)
	 		    				{
	 		    					if(x.individualMovedLikeThis(x.getBestIndividual())[j][k] == 'o')
	 		    						ViewMaze[j][k].setStyle("-fx-color: 'black'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	 		    					if(myMaze.getMazeMatrix()[j][k] == 'O')
	 		    						ViewMaze[j][k].setStyle("-fx-color: 'black'; -fx-background-color: 'green'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	 		    					if(myMaze.getMazeMatrix()[j][k] == 'X')
	 		    						ViewMaze[j][k].setStyle("-fx-color: 'black'; -fx-background-color: 'red'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
	 		    				}
	 		    			}
	 		    		}
	 		    		
	 	 	 			/*if(x.getFitness(x.getBestIndividual()) == previousFitness)
	 	 	 			{
	 	 	 				prevCount += 1;
	 	 	 			}
	 	 	 			
	 	 	 			else
	 	 	 			{
	 	 	 				prevCount = 0;
	 	 	 				
	 	 	 			}
	 	 	 			
	 		    		previousFitness = x.getFitness(x.getBestIndividual());

	 		    		if(prevCount == 5)
	 		    		{
	 		    			x.setMutationRate(Double.parseDouble(fieldMutSize.getText()*));
	 		    		}
	 		    		 
	 		    	 */
	 		    	 }
	 		    	 });
 	 			
			
		});
		
		timeline.getKeyFrames().add(keyframe);

		// start the timeline
		timeline.play();
  }
  
  
}
