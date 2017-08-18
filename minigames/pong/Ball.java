import javafx.scene.shape.Circle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

class Ball extends Circle {
    private Paint color = Color.WHITE;
    private double xVelocity;
    private double yVelocity;

    // Override Constructor
    public Ball(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        super.setFill(color);
    } // end Ball constructor

    public void setXVelocity(double value) {
        this.xVelocity = value;
    } // end setXVelocity()

    public double getXVelocity() {
        return this.xVelocity;
    } // end getXVelocity()

    public void setYVelocity(double value) {
        this.yVelocity = value;
    } // end setYVelocity()

    public double getYVelocity() {
        return this.yVelocity;
    } // end getYVelocity()

    public void update() {
        this.setCenterX(this.getCenterX() + xVelocity);
        this.setCenterY(this.getCenterY() + yVelocity);
    } // end update()

} // end Ball class