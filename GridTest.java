import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class GridTest extends Application {
   // Override the start method in the Application class
  
  boolean startSelected = false;
  boolean endSelected = false;
  
  @Override
  public void start(Stage primaryStage) {
    // Create a circle and set its properties

    // Create a pane to hold the circle
	BorderPane borderP = new BorderPane();
	Pane bottomPane = new Pane();
	bottomPane.setPrefSize(200, 400);
	bottomPane.setStyle("-fx-background-color: 'lightgrey'");
	
    Pane CenterPane = new Pane();
    CenterPane.setPrefSize(300, 300);
    GridPane map = new GridPane();
    map.setPrefSize(300, 300);
    map.setLayoutX(100);
    map.setLayoutY(100);
    
    Text textHeight = new Text("Height");
    textHeight.setLayoutX(10);
    textHeight.setLayoutY(50);
    
    TextField height  = new TextField();
    height.setLayoutX(10);
    height.setLayoutY(60);
    height.setPrefWidth(50);

    
    Text textWidth = new Text("Width");
    textWidth.setLayoutX(10);
    textWidth.setLayoutY(110);
    
    
    TextField width = new TextField();
    width.setLayoutX(10);
    width.setLayoutY(120);
    width.setPrefWidth(50);
    
    Button btGenerateMap = new Button("Generate");
    btGenerateMap.setLayoutX(10);
    btGenerateMap.setLayoutY(170);
    
    Button btSetStart = new Button("Set Start");
    btSetStart.setLayoutX(10);
    btSetStart.setLayoutY(200);
    
    
    Button btSetEnd = new Button("Set End");
    btSetEnd.setLayoutX(10);
    btSetEnd.setLayoutY(230);
    
    btGenerateMap.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
        	

			btSetEnd.setOnAction(new EventHandler<ActionEvent>() {
				boolean yy = endSelected;
				@Override
				public void handle(ActionEvent e)
				{
					yy = !yy;
					
					
					if(yy)
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
				boolean yy = startSelected;
				@Override
				public void handle(ActionEvent e)
				{
					yy = !yy;
					
					
					if(yy)
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
			
        	for(int i = 0; i < Integer.parseInt(height.getText()); i++)
        		for(int j = 0; j < Integer.parseInt(width.getText()); j++)
        		{	
        			boolean x = true;
        			Button temp = new Button("  ");
        			temp.setStyle("-fx-color: 'white'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
        			temp.setOnMouseClicked(new EventHandler<MouseEvent>() {
        				boolean xx = x;
						@Override
						public void handle(MouseEvent arg0) {
							// TODO Auto-generated method stub
							xx = !xx;
							
							if(endSelected)
							{
								temp.setStyle("-fx-color: 'black'; -fx-background-color: 'red'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
								btSetEnd.setStyle("-fx-color: 'white';");
								startSelected = false;
								changeEndSelection();
							}
							else if(startSelected)
							{
								temp.setStyle("-fx-color: 'black'; -fx-background-color: 'green'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
								btSetStart.setStyle("-fx-color: 'white';");
								endSelected = false;
								changeStartSelection();	
							}
							
							else if(!xx)
        	        			temp.setStyle("-fx-color: 'black'; -fx-background-color: 'black'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
        					else
        	        			temp.setStyle("-fx-color: 'white'; -fx-background-color: 'white'; -fx-border-style: solid; -fx-border-size: 1; -fx-border-color: 'black';");
     
							btSetEnd.setOnAction(new EventHandler<ActionEvent>() {
								boolean yy = endSelected;
								@Override
								public void handle(ActionEvent e)
								{
									yy = !yy;
									
									
									if(yy)
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
								boolean yy = startSelected;
								@Override
								public void handle(ActionEvent e)
								{
									yy = !yy;
									
									
									if(yy)
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

						}
        			});
        			map.add(temp, j, i);
            		}
        }
    });

    
    

    
    CenterPane.getChildren().add(map);
    bottomPane.getChildren().add(btSetEnd);
    bottomPane.getChildren().add(btSetStart);
    bottomPane.getChildren().add(textWidth);
    bottomPane.getChildren().add(textHeight);
    bottomPane.getChildren().add(height);
    bottomPane.getChildren().add(width);
    bottomPane.getChildren().add(btGenerateMap);
    borderP.setBottom(bottomPane);
    borderP.setTop(CenterPane);
    

    // Create a scene and place it in the stage
    Scene scene = new Scene(borderP, 800, 800);
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
  
}
