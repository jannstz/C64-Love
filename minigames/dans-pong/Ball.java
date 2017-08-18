/** First stab at trying to get Pong up and
 * 	running using JavaFX.
 *  This is the Ball class
 *
 *  User: D
 *  Date: 16th August, 2017
 */
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.lang.Math;

public class Ball extends Rectangle
{	
	static double SPEED = 5;
	private int startingPosX;
	private int startingPosY;

	public Ball(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		startingPosX = x;
		startingPosY = y;
	}

	public void update(double dir)
	{
		this.setX(this.getX() + Math.cos(dir) * SPEED);
		this.setY(this.getY() - Math.sin(dir) * SPEED);
	}

	public void draw(GraphicsContext gc)
	{
		gc.setFill(Color.WHITE);
    	gc.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public void reset()
	{
		this.setX(startingPosX);
		this.setY(startingPosY);
	}
}