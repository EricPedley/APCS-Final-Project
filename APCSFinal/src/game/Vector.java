package game;

import processing.core.PApplet;

public class Vector {
	public float x,y;
	
	public Vector() {
		this(0,0);
	}
	
	public Vector(float x, float y) {
		this.x=x;
		this.y=y;
	}
	
	public float length() {
		return (float)(Math.sqrt(x*x+y*y));
	}
	
	public Vector getParallelComponentTo(Vector other) {
		Vector v = new Vector(other.x,other.y);
		v.multiply(this.dot(other)/other.length()/other.length());//math magic
		return v;
	}
	
	public Vector getOrthogonalComponentTo(Vector other) {
		Vector v = new Vector(x,y);
		v.subtract(getParallelComponentTo(other));
		return v;
	}
	
	
	/**
	 * 
	 * @param other
	 * @return the dot product of this vector and other
	 */
	public float dot(Vector other) {
		return x*other.x+y*other.y;
	}
	
	public void multiply(float scalar) {
		x*=scalar;
		y*=scalar;
	}
	
	/**
	 * multiplies this vector by a scalar and stores the result in a new vector, leaving this unchanged
	 * @param scalar
	 * @return
	 */
	public Vector multiplyN(float scalar) {
		return new Vector(x*scalar,y*scalar);
	}
	
	/**
	 * Adds other to this
	 * @param other
	 */
	public void add(Vector other) {
		x+=other.x;
		y+=other.y;
	}
	
	/**
	 * Adds other to this and stores the result in a new vector instead of changing this
	 * @param other
	 */
	public Vector addN(Vector other) {
		return new Vector(x+other.x, y+other.y);
	}
	
	/**
	 * Subtracts other from this
	 * @param other
	 */
	public void subtract(Vector other) {
		x-=other.x;
		y-=other.y;
	}
	
	/**
	 * Subtracts other from this and stores the result in a new vector instead of changing this
	 * @param other
	 */
	public Vector subtractN(Vector other) {
		return new Vector(x-other.x, y-other.y);
	}
	/**
	 * Rotates this vector counter-clockwise(clockwise for usual upside-down coord system) by angle, in radians
	 * @param angle
	 */
	public void rotate(float angle) {
		float a  = PApplet.atan(y/x)+ ((x<0)? PApplet.PI:0);
		float l = length();
		x = l*PApplet.cos(a+angle);
		y = l*PApplet.sin(a+angle);
	}
	
	/**
	 * Does the same thing as rotate but stores the change in a new vector and doesn't change this vector
	 * @param angle
	 * @return
	 */
	public Vector rotateN(float angle) {
		float a  = PApplet.atan(y/x)+ ((x<0)? PApplet.PI:0);
		float l = length();
		return new Vector(l*PApplet.cos(a+angle),l*PApplet.sin(a+angle));
	}
	
	/**
	 * draws this vector as a line starting at (x,y)
	 * @param drawer
	 * @param x
	 * @param y
	 */
	public void draw(PApplet drawer,float x, float y) {
		drawer.line(x, y,x+this.x, y+this.y);
	}
	/**
	 * Draws this vector as a point
	 * @param drawer
	 */
	public void draw(PApplet drawer) {
		drawer.point(x, y);
	}
	
	public String toString() {
		return "["+x+","+y+"]";
	}

	/**
	 * Returns the angle of this vector in radians
	 * @return
	 */
	public float getAngle() {
		return PApplet.atan(y/x)+ ((x<0)? PApplet.PI:0);
	}
	
	public void scaleMagnitudeTo(float magnitude) {
		float l = length();
		multiply(magnitude/l);
	}
	
	public boolean asPointIntersectsCircle(Vector center, float radius) {
		return (x-center.x)*(x-center.x)+(y-center.y)*(y-center.y)<radius*radius;
	}
}
