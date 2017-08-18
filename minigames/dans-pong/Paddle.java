/** First stab at trying to get Pong up and
 * 	running using JavaFX.
 *  This is the Paddles class
 *
 *  User: D
 *  Date: 16th August, 2017
 */
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.lang.Math;

public class Paddle extends Rectangle 
{

	private int startingPosX;
	private int startingPosY;
	private double PADDLE_MOVE_SPEED = 1.5;
	
	public Paddle(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		startingPosX = x;
		startingPosY = y;
	}

	public void update(String dir)
	{
		if (dir == "UP")
			this.setY(this.getY() - PADDLE_MOVE_SPEED);
		if (dir == "DOWN")
			this.setY(this.getY() + PADDLE_MOVE_SPEED);
	}

	public void reset()
	{
		this.setX(startingPosX);
		this.setY(startingPosY);
	}

	public void draw(GraphicsContext gc)
	{
		gc.setFill(Color.WHITE);
		gc.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
}