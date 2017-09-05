import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameEngine {

    private static Scene scene;
	private Arena arena;
	private Paddle paddleP1;
	private Paddle paddleP2;
	private Ball ball;
	private Renderer renderer;
    static HashSet<String> currentlyActiveKeys;

	// Constructor
	public GameEngine(Scene scene) {
        this.scene = scene;
		paddleP1 = new Paddle(Constants.PADDLE_BUFFER, Constants.APP_HEIGHT / 2 - Constants.PADDLE_LENGTH / 2, 
			Constants.PADDLE_WIDTH, Constants.PADDLE_LENGTH);
		paddleP2 = new Paddle(Constants.APP_WIDTH - Constants.PADDLE_WIDTH - Constants.PADDLE_BUFFER, 
            Constants.APP_HEIGHT / 2 - Constants.PADDLE_LENGTH / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_LENGTH);
		ball = new Ball(Constants.APP_WIDTH / 2 - Constants.BALL_RADIUS / 2, 
            Constants.APP_HEIGHT / 2 - Constants.BALL_RADIUS / 2, 
            Constants.BALL_RADIUS);
		arena = new Arena(paddleP1, paddleP2, ball);

        // reset the arena ready to start a game
        arena.reset();

		// setup renderer (view)
		renderer = new ArenaRenderer(arena);

        // prepare action handles
        prepareActionHandlers();
	}
	
	public void handleEvent() {
        // check is game is started by pressing space bar
        if (currentlyActiveKeys.contains("SPACE"))
            arena.start();
        // check if player 1 moves paddle
        if (currentlyActiveKeys.contains("A")) {
            paddleP1.setDeltaY(-1);
        } else if (currentlyActiveKeys.contains("Z")) {
            paddleP1.setDeltaY(1);
        } else {
            paddleP1.setDeltaY(0);
        }
        // check if player 2 moves paddle
        if (currentlyActiveKeys.contains("K")) {
            paddleP2.setDeltaY(-1);
        } else if (currentlyActiveKeys.contains("M")) {
            paddleP2.setDeltaY(1);
        } else {
            paddleP2.setDeltaY(0);
        }
	}

    private static void prepareActionHandlers() {
        
        currentlyActiveKeys = new HashSet<String>();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

	// the GameEngine's update method
	public void update() {
        paddleP1.update();
        paddleP2.update();
        ball.update();
/*        switch (arena.getState()) {
            case READY:
                //readyUpdate();
                break;
            case RUNNING:
                //runningUpdate();
                break;
            case PAUSED:
                //pausedUpdate();
                break;
            case GAMEOVER:
                //gameoverUpdate();
                break;
        }*/
	} // end update()

/*    private void runningUpdate() {
        paddleP1.update();
        paddleP2.update();
        ball.update();
    } // end runningUpdate*/



	// render the whole game
	public void render(GraphicsContext gc) {
		renderer.render(gc);
	} // end render()
	
} // end GameEngine class