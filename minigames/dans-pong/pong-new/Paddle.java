class Paddle {
	private int deltaY;
	private double xPos;
	private double yPos;
	private int length;
	private int thickness;
	private int score;

	// Override constructor
	public Paddle(double x, double y, int thickness, int length) {
		this.xPos = x;
		this.yPos = y;
		this.length = length;
		this.thickness = thickness;
		this.score = 0;
	} // end Paddle constructor

	public void scored() {
		this.score += 1;
	}

	public int getScore() {
		return this.score;
	}

	public double getX() {
    	return this.xPos;
    } // end getX

    public double getY() {
    	return this.yPos;
    } // end getY	

    public double getLength() {
    	return this.length;
    } // end getX

    public double getThickness() {
    	return this.thickness;
    } // end getY

	public void setDeltaY(int value) {
		this.deltaY = value;
	} // end setDelta()

	public void update() {
		this.yPos = this.yPos + this.deltaY * Constants.PADDLE_SPEED;
	} // end update()

} // end Paddle class