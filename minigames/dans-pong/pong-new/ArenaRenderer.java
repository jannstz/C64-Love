import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.canvas.GraphicsContext;

public class ArenaRenderer implements Renderer {
	
	private Arena arena;

	public ArenaRenderer(Arena arena) {
		this.arena = arena;
	}

	// render the game objects
	@Override
	public void render(GraphicsContext gc) {

		// clear canvas
		gc.clearRect(0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);

		if (arena.isReady()) {
			// render instructions
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font ("Verdana", 30));
			gc.fillText("Press Space to Start!", 
				Constants.APP_WIDTH / 2 - Constants.INSTRUCTIONS_BUFFER, 
				Constants.APP_HEIGHT / 2);		
		}

		if (arena.isRunning()) {
			// arena layout
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(2.0);
			gc.strokeLine(Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH / 2, 
				Constants.APP_HEIGHT);
			
			// render the ball
			Ball ball = arena.getBall(); 
			gc.setFill(Color.WHITE);
			gc.fillOval(ball.getCenterX(), ball.getCenterY(), 
				ball.getRadius(), ball.getRadius());

			// render player 1 paddle
			Paddle paddleP1 = arena.getPaddleP1();
			gc.setFill(Color.WHITE);
			gc.fillRect(paddleP1.getX(), paddleP1.getY(), 
				paddleP1.getThickness(), paddleP1.getLength());

			// render player 2 paddle
			Paddle paddleP2 = arena.getPaddleP2();
			gc.setFill(Color.WHITE);
			gc.fillRect(paddleP2.getX(), paddleP2.getY(), 
				paddleP2.getThickness(), paddleP2.getLength());	

			// render each player's scores
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font ("Verdana", 30));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(String.format("%02d", paddleP1.getScore()), 
				Constants.APP_WIDTH / 2 - Constants.SCORE_BUFFER,
				Constants.SCORE_BUFFER);
			gc.fillText(String.format("%02d", paddleP2.getScore()), 
				Constants.APP_WIDTH / 2 + Constants.SCORE_BUFFER, 
				Constants.SCORE_BUFFER);
		}
	}
}