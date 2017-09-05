
public class Arena {
	
	// Game objects
	private Paddle paddleP1;
	private Paddle paddleP2;
	private Ball ball;
	private GameState currentState;

	// Pong game states
    public enum GameState {
        READY, RUNNING, PAUSED, GAMEOVER
    }

	// Constructor - add paddles and ball to arena
	public Arena(Paddle paddleP1, Paddle paddleP2, Ball ball) {
		this.paddleP1 = paddleP1;
		this.paddleP2 = paddleP2;
		this.ball = ball;
	}

	public Paddle getPaddleP1() {
		return paddleP1;
	}

	public Paddle getPaddleP2() {
		return paddleP2;
	}

	public Ball getBall() {
		return ball;
	}

	public GameState getState() {
		return currentState;
	}

	private void readyUpdate() {
        // empty
    } // end readyUpdate

	private void pausedUpdate() {
        // empty
    } // end pausedUpdate()

    private void gameoverUpdate() {
        // empty
    } // end gameoverUpdate()

    public boolean isReady() {
        return currentState == GameState.READY;
    } // end isReady()

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    } // end isRunning()

    public void start() {
        currentState = GameState.RUNNING;
    } // end start()

    public void reset() {
        currentState = GameState.READY;
        // initialise game objects
    } // end restart()

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
	
}