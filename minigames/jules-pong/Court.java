import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;


class Court implements Runnable{
    // Borrowed constants
    private int FIELD_WIDTH = JulesPong.WIDTH; 
    private int FIELD_HEIGHT = JulesPong.HEIGHT;

    // Class constants
    private int BUFFER = 5;     // distance from screen edge to paddle
    private int SCOREBUFF = 55; // distance from screen edge to score
    private int PADW = 10;      // paddle width
    private int PADH = 60;      // paddle height
    private int BALLR = 10;     // ball radius
    private int FONTSIZE = 36;  // score font size
    private int WIN_SCORE = 3;  // score needed for win
    
    // Constants for ball start location
    private int P1_BALLX = BUFFER + PADW + BALLR;
    private int P2_BALLX = FIELD_WIDTH - BUFFER - PADW - BALLR;
    private int P_BALLY = FIELD_HEIGHT / 2;

    // Game objects
    Paddle paddleP1;
    Paddle paddleP2;
    Ball ball;
    Net net;
    Score scoreP1;
    Score scoreP2;

    // Utility fields
    private boolean isWon = false;
    private boolean launchable = true;
    private int pointsP1 = 0;
    private int pointsP2 = 0;
    private Paddle focus = paddleP1;
    int testCount = 0;

    // Constructor
    public Court() {
        // Create Paddles
        paddleP1 = new Paddle(BUFFER, FIELD_HEIGHT / 2 - PADH / 2, 
                                PADW, PADH);
        paddleP2 = new Paddle(FIELD_WIDTH - BUFFER - PADW, 
                                FIELD_HEIGHT / 2 - PADH / 2, 
                                PADW, PADH);

        // Create ball located at P1 paddle
        ball = new Ball(P1_BALLX, P_BALLY, BALLR);

        // Create net
        net = new Net(FIELD_WIDTH / 2, 0, FIELD_WIDTH / 2, FIELD_HEIGHT);

        // Create scores
        scoreP1 = new Score(FIELD_WIDTH / 2 - SCOREBUFF - FONTSIZE / 2, 
                            SCOREBUFF, new Integer(pointsP1).toString(),
                            TextAlignment.RIGHT);
        scoreP2 = new Score(FIELD_WIDTH / 2 + SCOREBUFF, SCOREBUFF,
                            new Integer(pointsP2).toString(),
                            TextAlignment.LEFT);
    } // end Court constructor

    private void setFocus(Paddle paddle) {
        this.focus = paddle;
    } // end setFocus

    private Paddle getFocus() {
        return this.focus;
    } // end getFocus()

    public boolean hasWon() {
        return isWon;
    } // end hasWon()

    public class Score extends Text {
        public Score(double x, double y, String text, TextAlignment align) {
            super(x, y, text);
            this.setTextAlignment(align);
            Font scoreFont = new Font("Arial", FONTSIZE);
            this.setFont(scoreFont);
            this.setFill(Color.WHITE);
        } // end Score constructor

        public void update(int points) {
            this.setText(new Integer(points).toString());
        } // end update()

    } // end Score class

    public class Net extends Line {
        public Net(double x1, double y1, double x2, double y2) {
            super(x1, y1, x2, y2);
            this.setStroke(Color.WHITE);
        } // end Net constructor

    } // end Net class

    public void checkCollisions() {
        if (!isWon) {
            // Poll relevant game objects for current position
            double ballX = ball.getCenterX();
            double ballY = ball.getCenterY();
            double padP1x = paddleP1.getX();
            double padP1yNorth = paddleP1.getY();
            double padP2x = paddleP2.getX();
            double padP2yNorth = paddleP2.getY();

            double ballYNorth = ballY - BALLR; // Northern bound
            double ballYSouth = ballY + BALLR; // Southern bound

            double padP1ySouth = padP1yNorth + PADH; // Southern bound
            double padP2ySouth = padP2yNorth + PADH; // Southern bound

            boolean atLeftEdge = ballX - BALLR <= padP1x + PADW;
            boolean atRightEdge = ballX + BALLR >= padP2x;
            
            boolean inP1YRange = (
                (ballYNorth < padP1ySouth && ballYNorth > padP1yNorth) ||
                (ballYSouth < padP1yNorth && ballYNorth > padP1ySouth));
            boolean inP2YRange = (
                (ballYNorth < padP2ySouth && ballYNorth > padP2yNorth) ||
                (ballYSouth < padP2yNorth && ballYNorth > padP2ySouth));

            // Check for collision conditions
            if (atLeftEdge) {
                // Check for collisions between ball and paddleP1
                if (inP1YRange) {
                    // Switch focus to paddleP1
                    setFocus(paddleP1);
                    // Reverse ball's horizontal velocity
                    ball.setXVelocity(ball.getXVelocity() * -1);
                    // Move ball away from edge
                    ball.setCenterX(P1_BALLX + 1);
                    // Update ballX for remaining checks
                    ballX = ball.getCenterX();
                }
            }
            if (atRightEdge) {
                // Check for collision between ball and paddleP2
                if (inP2YRange) {
                    // Switch focus to paddleP2
                    setFocus(paddleP2);
                    // Reverse ball's horizontal velocity
                    ball.setXVelocity(ball.getXVelocity() * -1);
                    // Move ball away from edge
                    ball.setCenterX(P2_BALLX - 1);
                    // Update ballX for remaining checks
                    ballX = ball.getCenterX();
                }
            }
            
            // Check for ball out of bounds on left/right sides
            if (ballX <= 0 || ballX >= FIELD_WIDTH) {
                // Stop ball
                ball.setXVelocity(0);
                ball.setYVelocity(0);

                // Increment point to focus player, reset ball's position
                if (getFocus() == paddleP1) {
                    pointsP1++;
                    ball.setCenterX(P1_BALLX);
                } 
                else {
                    pointsP2++;
                    ball.setCenterX(P2_BALLX);
                }
                ball.setCenterY(P_BALLY);
                launchable = true;
                
                // Check for win
                if (pointsP1 == 3 || pointsP2 == 3) {
                    isWon = true;
                }
            }
            // Check for ball out of bounds on top/bottom sides
            if (ballY <= BALLR || ballY >= FIELD_HEIGHT - BALLR) {
                // Reverse ball's vertical velocity
                ball.setYVelocity(ball.getYVelocity() * -1);
            }
        }
        else {
            // Transition to win state
            System.out.println("Game is won");
            ball.setXVelocity(0);
            ball.setYVelocity(0);

        }
    } // end checkCollisions()

    @Override
    public void run() {
        // Update game object positions
        paddleP1.update();
        paddleP2.update();
        ball.update();
        scoreP1.update(pointsP1);
        scoreP2.update(pointsP2);
        // Check for collisions
        checkCollisions();
    } // end run()
    
    public void launchBall() {
        // Verify ball isn't already in motion
        if (launchable) {
            // Get random magnitude
            double mag = Math.random() * 2 + 3;

            // Determine which paddle has focus
            if (focus == paddleP1) {
                ball.setXVelocity(mag);
            }
            else ball.setXVelocity(-mag);

            // Randomize up or down trajectory and value
            int[] directionPool = {1, -1};
            int direction = directionPool[(int)(Math.random() * 2)];
            double magnitude = Math.random() * 2 + 3;
            double velocity = magnitude * direction;
            ball.setYVelocity(velocity);

            // Toggle launchable
            launchable = false;
        }
    } // end launchBall()

} // end Court class