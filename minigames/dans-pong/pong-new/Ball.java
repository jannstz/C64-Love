class Ball {
	private double centerX;
	private double centerY;
	private double radius;
	private double xVelocity;
	private double yVelocity;

	// constructor
	public Ball(double x, double y, double r) {
		this.centerX = x;
		this.centerY = y;
		this.radius = r;
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

    public double getCenterX() {
    	return this.centerX;
    } // end getRadius

    public double getCenterY() {
    	return this.centerY;
    } // end getRadius

    public double getRadius() {
    	return this.radius;
    } // end getRadius

    public void update() {
        this.centerX= this.centerX + xVelocity;
        this.centerY = this.centerY + yVelocity;
    } // end update()

} // end Ball class