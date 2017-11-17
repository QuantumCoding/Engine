package com.Engine.Util.Vectors;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;

public class Vector2f {
	public float x, y;

	public Vector2f() {
		this(0, 0);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(double x, double y) {
		this((float) x, (float) y);
	}
	
	public Vector2f(float[] values) {
		this(values[0], values[1]);
	}
	
	public Vector2f(double[] values) {
		this(values[0], values[1]);
	}
	
	public Vector2f(float value) {
		this(value, value);
	}
	
	public Vector2f(double value) {
		this(value, value);
	}
	
	public Vector2f(Vector2f clone) {
		this(clone.x, clone.y);
	}
	
	public Vector2f(org.lwjgl.util.vector.Vector2f clone) {
		this(clone.x, clone.y);
	}

	public org.lwjgl.util.vector.Vector2f toLWJGL() {
		return new org.lwjgl.util.vector.Vector2f(x, y);
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Vector2f)) return false;
		
		Vector2f other = (Vector2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) return false;
		
		return true;
	}
	
	public boolean equals(float x, float y) {
		if (Float.floatToIntBits(this.x) != Float.floatToIntBits(x)) return false;
		if (Float.floatToIntBits(this.y) != Float.floatToIntBits(y)) return false;
		
		return true;
	}

//----------------------------------------------------- Basic Arithmetic -------------------------------------------------------\\
	public Vector2f add(float add) { return add(add, add); }
	public Vector2f add(double add) { return add(add, add); }
	public Vector2f add(Vector2f add) { return add(add.x, add.y); }
	public Vector2f add(double x, double y) { return add((float) x, (float) y); }
	
	public Vector2f add(float x, float y) {
		return new Vector2f(
				this.x + x,
				this.y + y
			);
	}
	
	public Vector2f subtract(float subtract) { return subtract(subtract, subtract); }
	public Vector2f subtract(double subtract) { return subtract(subtract, subtract); }
	public Vector2f subtract(Vector2f subtract) { return subtract(subtract.x, subtract.y); } 
	public Vector2f subtract(double x, double y) { return subtract((float) x, (float) y); }
	
	public Vector2f subtract(float x, float y) {
		return new Vector2f(
				this.x - x,
				this.y - y
			);
	}

	public Vector2f multiply(float multiply) { return multiply(multiply, multiply); }
	public Vector2f multiply(double multiply) { return multiply(multiply, multiply); }
	public Vector2f multiply(Vector2f multiply) { return multiply(multiply.x, multiply.y); }
	public Vector2f multiply(double x, double y) { return multiply((float) x, (float) y); }
	
	public Vector2f multiply(float x, float y) {
		return new Vector2f(
				this.x * x,
				this.y * y
			);
	}
	
	public Vector2f divide(float divide) { return divide(divide, divide); }
	public Vector2f divide(double divide) { return divide(divide, divide); }
	public Vector2f divide(Vector2f divide) { return divide(divide.x, divide.y); }
	public Vector2f divide(double x, double y) { return divide((float) x, (float) y); }
	
	public Vector2f divide(float x, float y) {
		return new Vector2f(
				this.x / x,
				this.y / y
			);
	}

	public Vector2f mod(float mod) { return mod(mod, mod); }
	public Vector2f mod(double mod) { return mod(mod, mod); }
	public Vector2f mod(Vector2f mod) { return mod(mod.x, mod.y); }
	public Vector2f mod(double x, double y) { return mod((float) x, (float) y); }
	
	public Vector2f mod(float x, float y) {
		return new Vector2f(
				this.x % x,
				this.y % y
			);
	}
	
	public Vector2f pow(float pow) { return pow(pow, pow); }
	public Vector2f pow(double pow) { return pow(pow, pow); }
	public Vector2f pow(Vector2f pow) { return pow(pow.x, pow.y); }
	public Vector2f pow(double x, double y) { return pow((float) x, (float) y); }
	
	public Vector2f pow(float x, float y) {
		return new Vector2f(
				(float) Math.pow(this.x, x),
				(float) Math.pow(this.y, y)
			);
	}

	public Vector2f randomize(float max) { return randomize(max, max); }
	public Vector2f randomize(double max) { return randomize(max, max); }
	public Vector2f randomize(Vector2f max) { return randomize(max.x, max.y); }
	public Vector2f randomize(double x, double y) { return randomize((float) x, (float) y); }
	
	public Vector2f randomize(float x, float y) {
		return new Vector2f(
				Math.random() * x, 
				Math.random() * y
			);
	}
	
	public static Vector2f random(float max) {
		return new Vector2f().randomize(max);
	}
	
//--------------------------------------------------------- Cap Values ---------------------------------------------------------\\
	
	public Vector2f capMin(float capMin) { return capMin(capMin, capMin); }
	public Vector2f capMin(double capMin) { return capMin(capMin, capMin); }
	public Vector2f capMin(Vector2f capMin) { return capMin(capMin.x, capMin.y); }
	public Vector2f capMin(double x, double y) { return capMin((float) x, (float) y); }

	public Vector2f capMin(float x, float y) {
		return new Vector2f(
				this.x < x ? x : this.x, 
				this.y < y ? y : this.y
			);
	}
	
	public Vector2f capMax(float capMax) { return capMax(capMax, capMax); }
	public Vector2f capMax(double capMax) { return capMax(capMax, capMax); }
	public Vector2f capMax(Vector2f capMax) { return capMax(capMax.x, capMax.y); }
	public Vector2f capMax(double x, double y) { return capMax((float) x, (float) y); }

	public Vector2f capMax(float x, float y) {
		return new Vector2f(
				this.x > x ? x : this.x, 
				this.y > y ? y : this.y
			);
	}
	
	public float max() {
		float max = 0;
		
		if(x > max) max = x;
		if(y > max) max = y;
		
		return max;
	}
	
	public float min() {
		float min = 0;
		
		if(x < min) min = x;
		if(y < min) min = y;
		
		return min;
	}

//------------------------------------------------- Vector Mathematical Methods ------------------------------------------------\\
	
	public float length() {
		return (float) Math.sqrt( dot(this) );
	}
	
	public float lengthSquared() {
		return dot(this);
	}
	
	public float dot(Vector2f other) {
		return (x * other.x) +
			   (y * other.y) ;
	}
	
	public Vector2f difference(Vector2f other) {
		return subtract(other).abs();
	}

	public float distance(Vector2f distance) { return distance(distance.x, distance.y); }
	public float distance(double x, double y) { return distance((float) x, (float) y); }
	
	public float distance(float x, float y) {
		return (float) Math.sqrt(distanceSquared(x, y));
	}
	
	public float distanceSquared(Vector2f distance) { return distanceSquared(distance.x, distance.y); }
	public float distanceSquared(double x, double y) { return distanceSquared((float) x, (float) y); }
	
	public float distanceSquared(float x, float y) {
		return (float) (
				Math.pow(this.x - x, 2) +
				Math.pow(this.y - y, 2) 
			);
	}
	
//------------------------------------------------- Other Mathematical Methods -------------------------------------------------\\
	
	public Vector2f transform(Matrix3f matrix) {
		Vector3f vecOut = new Vector3f();
		Matrix3f.transform(matrix, new Vector3f(x, y, 1), vecOut);
		return new Vector2f(vecOut.x, vecOut.y);
	}
	
	public Vector2f invert() {
		return new Vector2f(1.0f / x, 1.0f / y);
	}
	
	public Vector2f direction() {
		return new Vector2f(
				x > 0 ? 1 : x < 0 ? -1 : 0, 
				y > 0 ? 1 : y < 0 ? -1 : 0
			);
	}
		
	public float sum() {
		return x + y;
	}
	
	public Vector2f truncate() {
		return new Vector2f((int) x, (int) y);
	}
	
	public Vector2f round() {
		return new Vector2f(
				Math.round(x),
				Math.round(y)
			);
	}
	
	public Vector2f abs() {
		return new Vector2f(
				Math.abs(x),
				Math.abs(y)
			);
	}
	
	public Vector2f normalize() {
		return divide(length());
	}
	
	public Vector2f reflect(Vector2f normal) {
		return subtract(normal.multiply(dot(normal) * 2));
	}
	
	public Vector2f rotate(float angle) {
		angle = (float) Math.toRadians(angle);
		
		return new Vector2f(
				x *  cos(angle) + y * sin(angle),
				x * -sin(angle) + y * cos(angle)
			);
	}	
	
	public Vector2f invertRotate(float angle) {
		angle = (float) Math.toRadians(angle);
		
		return new Vector2f(
				x * cos(angle) + y * -sin(angle),
				x * sin(angle) + y *  cos(angle)
			);
	}
	
	public Vector2f toDegrees() {
		return new Vector2f(
				(float) Math.toDegrees(x),
				(float) Math.toDegrees(y)
			);
	}
	
	public Vector2f toRadians() {
		return new Vector2f(
				(float) Math.toRadians(x),
				(float) Math.toRadians(y)
			);
	}
	
//------------------------------------------------ Logical Mathematical Operators ----------------------------------------------\\

	public boolean greaterThen(float then) { return greaterThen(then, then); }
	public boolean greaterThen(double then) { return greaterThen(then, then); }
	public boolean greaterThen(Vector2f then) { return greaterThen(then.x, then.y); }
	public boolean greaterThen(double x, double y) { return greaterThen((float) x, (float) y); }
	
	public boolean greaterThen(float x, float y) {
		return  this.x > x &&
				this.y > y ;
	}
	
	public boolean anyGreaterThen(float then) { return anyGreaterThen(then, then); }
	public boolean anyGreaterThen(double then) { return anyGreaterThen(then, then); }
	public boolean anyGreaterThen(Vector2f then) { return anyGreaterThen(then.x, then.y); }
	public boolean anyGreaterThen(double x, double y) { return anyGreaterThen((float) x, (float) y); }
	
	public boolean anyGreaterThen(float x, float y) {
		return  this.x > x ||
				this.y > y ;
	}
	
	public boolean greaterThenOrEqual(float then) { return greaterThenOrEqual(then, then); }
	public boolean greaterThenOrEqual(double then) { return greaterThenOrEqual(then, then); }
	public boolean greaterThenOrEqual(Vector2f then) { return greaterThenOrEqual(then.x, then.y); }
	public boolean greaterThenOrEqual(double x, double y) { return greaterThenOrEqual((float) x, (float) y);}
	
	public boolean greaterThenOrEqual(float x, float y) {
		return  this.x >= x &&
				this.y >= y ;
	}

	public boolean anyGreaterThenOrEqual(float then) { return anyGreaterThenOrEqual(then, then); }
	public boolean anyGreaterThenOrEqual(double then) { return anyGreaterThenOrEqual(then, then); }
	public boolean anyGreaterThenOrEqual(Vector2f then) { return anyGreaterThenOrEqual(then.x, then.y); }
	public boolean anyGreaterThenOrEqual(double x, double y) { 
		return anyGreaterThenOrEqual((float) x, (float) y); }
	
	public boolean anyGreaterThenOrEqual(float x, float y) {
		return  this.x >= x ||
				this.y >= y ;
	}
	

	public boolean lessThen(float then) { return lessThen(then, then); }
	public boolean lessThen(double then) { return lessThen(then, then); }
	public boolean lessThen(Vector2f then) { return lessThen(then.x, then.y); }
	public boolean lessThen(double x, double y) { return lessThen((float) x, (float) y); }
	
	public boolean lessThen(float x, float y) {
		return  this.x < x &&
				this.y < y ;
	}
	
	public boolean anyLessThen(float then) { return anyLessThen(then, then); }
	public boolean anyLessThen(double then) { return anyLessThen(then, then); }
	public boolean anyLessThen(Vector2f then) { return anyLessThen(then.x, then.y); }
	public boolean anyLessThen(double x, double y) { return anyLessThen((float) x, (float) y); }
	
	public boolean anyLessThen(float x, float y) {
		return  this.x < x ||
				this.y < y ;
	}
	
	public boolean lessThenOrEqual(float then) { return lessThenOrEqual(then, then); }
	public boolean lessThenOrEqual(double then) { return lessThenOrEqual(then, then); }
	public boolean lessThenOrEqual(Vector2f then) { return lessThenOrEqual(then.x, then.y); }
	public boolean lessThenOrEqual(double x, double y) { return lessThenOrEqual((float) x, (float) y); }
	
	public boolean lessThenOrEqual(float x, float y) {
		return  this.x <= x &&
				this.y <= y ;
	}
	
	public boolean anyLessThenOrEqual(float then) { return anyLessThenOrEqual(then, then); }
	public boolean anyLessThenOrEqual(double then) { return anyLessThenOrEqual(then, then); }
	public boolean anyLessThenOrEqual(Vector2f then) { return anyLessThenOrEqual(then.x, then.y); }
	public boolean anyLessThenOrEqual(double x, double y) { 
		return anyLessThenOrEqual((float) x, (float) y); }
	
	public boolean anyLessThenOrEqual(float x, float y) {
		return  this.x <= x ||
				this.y <= y ;
	}
	
	public boolean equalTo(float then) { return equalTo(then, then); }
	public boolean equalTo(double then) { return equalTo(then, then); }
	public boolean equalTo(Vector2f then) { return equalTo(then.x, then.y); }
	public boolean equalTo(double x, double y) { return equalTo((float) x, (float) y); }
	
	public boolean equalTo(float x, float y) {
		return equals(x, y);
	}
	
	public boolean anyEqualTo(float then) { return anyEqualTo(then, then); }
	public boolean anyEqualTo(double then) { return anyEqualTo(then, then); }
	public boolean anyEqualTo(Vector2f then) { return anyEqualTo(then.x, then.y); }
	public boolean anyEqualTo(double x, double y) { return anyEqualTo((float) x, (float) y); }
	
	public boolean anyEqualTo(float x, float y) {
		return  Math.abs(this.x - x) < 0.000001 ||
				Math.abs(this.y - y) < 0.000001 ;
	}
	
//---------------------------------------------------- Accessors / Modifiers ----------------------------------------------------\\
	
	public void set(Vector2f set) {
		this.x = set.x;
		this.y = set.y;
	}
	
	public Vector2f clone() {
		return new Vector2f(x, y);
	}
	
	public String toString() {
		return printValues(x, y);
	}
	
	public static String printValues(float x, float y) {
		return "<" + x + ", " + y + ">";
	}

	public float getX() { return x; }
	public float getY() { return y; }

	public Vector2f addX(float x) { this.x += x; return this; }
	public Vector2f addY(float y) { this.y += y; return this; }

	public Vector2f setX(float x) { this.x = x; return this; }
	public Vector2f setY(float y) { this.y = y; return this; }
	
	public Vector2f reset() {
		return this.setX(0).setY(0);
	}
}
