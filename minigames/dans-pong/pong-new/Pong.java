/*
* Dan's 'new' Pong
* randles.dan@gmail.com
 */

import javafx.application.Application;
import javafx.animation.AnimationTimer;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pong extends Application {

	private GameEngine engine;

	public static void main(String[] args) {
	    launch(args);
	} // end main()

	public void start(Stage stage) {
	    stage.setTitle("Dan's <<new>> Pong");

	    // Create group to hold game elements and attach to scene
	    Group root = new Group();
	    Scene scene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);
	    scene.setFill(Color.BLACK);

	    // Create a canvas node
		Canvas canvas = new Canvas(Constants.APP_WIDTH, Constants.APP_HEIGHT);
		// obtain the GraphicsContext (drawing surface)
		GraphicsContext gc = canvas.getGraphicsContext2D();

		engine = new GameEngine(scene);

	    // Start the game loop
		new AnimationTimer()
    	{
    		public void handle(long now) {
    			// handle events
    			engine.handleEvent();
    			// update the game state
    			engine.update();
    			// render eveything
    			engine.render(gc);
    		}
    	}.start();

    	// Set the stage and make visible
    	root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
    } // end start()

    public void play() {

    } // end play()

} // end Pong class