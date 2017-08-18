/** First stab at trying to get Pong up and
 * 	running using JavaFX.
 *  This is the main 'driver' class
 *
 *  User: D
 *  Date: 17th August, 2017
 */
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import java.text.*;

import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

public class Pong extends Application
{	
	public static void main(String[] args)
	{
		launch(args);
	}

    static GraphicsContext gc;
    public static int WIDTH = 512;
    public static int HEIGHT = 256;
    static Paddle paddleLeft;
    static Paddle paddleRight;
    static Ball ball;
    private static int scoreRight = 0;
    private static int scoreLeft = 0;
    private static double ballDir;
	static HashSet<String> currentlyActiveKeys;

	@Override
	public void start(Stage theStage) throws Exception
	{
		theStage.setTitle("Pong");
		Group root = new Group();
		Scene theScene = new Scene(root);

		// --- Create a canvas node ---
		Canvas canvas = new Canvas(WIDTH, HEIGHT);

		prepareActionHandlers(theScene);

		// --- obtain the GraphicsContext (drawing surface) ---
		gc = canvas.getGraphicsContext2D();

		// --- create two paddles ---
		// -- Paddles are 50 x 10 and are located 30px in from the side walls ---
		paddleLeft = new Paddle(30, HEIGHT / 2 - 25, 10, 50);
		paddleRight = new Paddle(WIDTH - 40, HEIGHT / 2 - 25, 10, 50);

		// create the ball
		ball = new Ball(WIDTH / 2 - 5, HEIGHT / 2, 10, 10);
		setBallDir();

		// --- create an animate (update & render loop) ---
		new AnimationTimer()
    	{
	        public void handle(long now)
	        {
	        	drawGameArea();
	        	updateAndRender();
	        	displayScore();
	        }
    	}.start();

		// --- add the single node to the scene graph ---
		root.getChildren().add(canvas);
		theStage.setScene(theScene);
		theStage.show();

	}

	private static void setBallDir()
	{
		ArrayList<Double>  lista = new ArrayList<Double>();
		lista.add(Math.PI / 4);
		lista.add(3 * Math.PI / 4);
		lista.add(5 * Math.PI / 4);
		lista.add(7 * Math.PI / 4);
		Random r = new Random();
		ballDir = lista.get(r.nextInt(lista.size()));
	}

    private static void prepareActionHandlers(Scene theScene)
    {
        // --- use a set so duplicates are not possible ---
        currentlyActiveKeys = new HashSet<String>();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

	private static void updateAndRender() 
	{
		// --- check if a player has won (ie when a score = 12) ---
		if (scoreLeft == 12) {
			System.out.println("Left-side player wins!");
			System.exit(0);
		}
		else if (scoreRight == 12)
		{
			System.out.println("Right-hand player wins");
			System.exit(0);
		}
		// --- check if players have moved the paddles and if so update and draw paddle ---
		if (currentlyActiveKeys.contains("A"))
			if (paddleLeft.getY() > 5)
				paddleLeft.update("UP");
		if (currentlyActiveKeys.contains("Z"))
			if (paddleLeft.getY() + paddleLeft.getHeight() < HEIGHT - 5)
				paddleLeft.update("DOWN");

		paddleLeft.draw(gc);

		if (currentlyActiveKeys.contains("K"))
			if (paddleRight.getY() > 5)
				paddleRight.update("UP");
		if (currentlyActiveKeys.contains("M"))
			if (paddleRight.getY() + paddleRight.getHeight() < HEIGHT - 5)
				paddleRight.update("DOWN");

		paddleRight.draw(gc);

		// --- check for a collision against wall ---
		if (ball.getY() > HEIGHT - 15 || ball.getY() < 5)
			ballDir *= -1; // reverse vert. component of dir

		// --- check for a collision with leftside paddle ---
		if (Math.cos(ballDir) < 0) // Sticky balls! First check direction
			if ( 
				(ball.getX() < paddleLeft.getX() + 10 && ball.getX() + 10 > paddleLeft.getX() &&
				 ball.getY() < paddleLeft.getY() + 50 && ball.getY() + 10 > paddleLeft.getY())
				) {
				ballDir = Math.PI - ballDir; // reverse horiz. component of dir
				}	

		// --- check for a collision with righttside paddle ---
		if (Math.cos(ballDir) > 0) // Sticky balls! First check direction
			if ( 
				(ball.getX() < paddleRight.getX() + 10 && ball.getX() + 10 > paddleRight.getX() &&
				 ball.getY() < paddleRight.getY() + 50 && ball.getY() + 10 > paddleRight.getY())
				) {
				ballDir = Math.PI - ballDir; // reverse horiz. component of dir
		}

		// --- check if a player wins
		if (ball.getX() <= 0) {
			scoreRight += 1;
			resetGame();
		}
		else if (ball.getX() > WIDTH - 10) {
			scoreLeft += 1;
			resetGame();
		}

		ball.update(ballDir);
		ball.draw(gc);
	}

	private static void resetGame()
	{
		// reset scores here !!
		paddleRight.reset();
		paddleLeft.reset();	
		setBallDir();
		ball.reset();

		// --- wait two seconds ---
		try {

			// --- sleep 1 second to allow gloating ---
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void drawGameArea()
	{
		// --- set background color as black ---	
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		// --- top and bottom gamearea white line ---
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(10.0);
		gc.setLineDashes();
		gc.strokeLine(0, 0, WIDTH, 0);
		gc.strokeLine(0, HEIGHT, WIDTH, HEIGHT);

		// --- the halfway seperation line between the two players ---
		gc.setLineWidth(3.0);
		gc.setLineDashes(5d);
		gc.setLineDashOffset(5d);
		gc.strokeLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	}

	private static void displayScore()
	{
		// --- the players' scores ---
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font ("Verdana", 30));
		gc.fillText(String.format("%02d", scoreLeft), (WIDTH / 2) - 50, 50);
		gc.fillText(String.format("%02d", scoreRight), (WIDTH / 2) + 12, 50);
	}


}