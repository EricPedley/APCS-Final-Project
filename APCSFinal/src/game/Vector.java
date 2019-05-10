package game;

import processing.core.PApplet;

public class Vector {
	public float x,y;
	public Vector(float x, float y) {
		this.x=x;
		this.y=y;
	}
	
	public float length() {
		return (float)(Math.sqrt(x*x+y*y));
	}
	
	public Vector getParallelComponentTo(Vector other) {
		Vector v = new Vector(other.x,other.y);
		v.multiply(this.dot(other)/length()/other.length());
		return v;
	}
	
	public Vector getOrthogonalComponentTo(Vector other) {
		Vector v = new Vector(other.x,other.y);
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
	 * Subtracts other from this
	 * @param other
	 */
	public void subtract(Vector other) {
		x-=other.x;
		y-=other.y;
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
	
	public void draw(PApplet drawer,float x, float y) {
		drawer.line(x, y,x+this.x, y+this.y);
	}
	
	public String toString() {
		return "["+x+","+y+"]";
	}
}
