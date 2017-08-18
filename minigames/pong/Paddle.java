import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

class Paddle extends Rectangle {
    private Paint color = Color.WHITE;
    private int deltaY;

    // Override Constructor
    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
        super.setFill(color);
    } // end Paddle constructor

    public void setDeltaY(int value) {
        this.deltaY = value;
    } // end setDeltaY()

    public void update() {
        this.setY(this.getY() + this.deltaY * 4);
    } // end update()

} // end Paddle class