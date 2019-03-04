/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;

import org.omg.CORBA.IRObject;

import static org.junit.Assert.assertEquals;

import java.time.Year;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for (int i = 0; i < 4; i++) {
        	turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (180.0 * (sides - 2) / sides);
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        int side;
        double sides;
        sides = 360 / (180 - angle);
        side = (int)sides;
        if (sides > side)
        	side++;
        return side;
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle;
        angle = (180 - calculateRegularPolygonAngle(sides));
        for (int i = 0; i < sides; i++) {
        	turtle.forward(sideLength);
            turtle.turn(angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	double a, b, c, sin, arcsin, angle;
    	a = targetX - currentX;
    	b = targetY - currentY;
    	c = Math.sqrt(a * a + b * b);
    	sin = b / c;
    	if (a >= 0 && b >= 0)
    		arcsin = 180 * Math.asin(sin) / Math.PI;
    	else if (a >= 0 && b <= 0)
    		arcsin = 360 + 180 * Math.asin(sin) / Math.PI;
    	else 
    		arcsin = 180 - 180 * Math.asin(sin) / Math.PI;
    	arcsin = 360 - arcsin + 90;
    	if (arcsin >= 360)
    		arcsin = arcsin - 360;
    	if (arcsin >= currentBearing)
    		angle = arcsin - currentBearing;
    	else 
			angle = 360 + arcsin - currentBearing;
    	return angle;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	List<Double> lists = new ArrayList<Double>();
    	double a, b, c, sin, angle = 0;
    	for (int i = 1; i < xCoords.size(); i++) {
    		lists.add(calculateBearingToPoint(angle, xCoords.get(i - 1), yCoords.get(i - 1), xCoords.get(i), yCoords.get(i)));
    		a = xCoords.get(i) - xCoords.get(i - 1);
        	b = yCoords.get(i) - yCoords.get(i - 1);
        	c = Math.sqrt(a * a + b * b);
        	sin = b / c;
        	if (a >= 0 && b >= 0)
        		angle = 180 * Math.asin(sin) / Math.PI;
        	else if (a >= 0 && b <= 0)
        		angle = 360 + 180 * Math.asin(sin) / Math.PI;
        	else 
        		angle = 180 - 180 * Math.asin(sin) / Math.PI;
        	angle = 360 - angle + 90;
        	if (angle >= 360)
        		angle = angle - 360;
    	}
    	return lists;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */

    public static Set<Point> convexHull(Set<Point> points) {
    	int i = 0;
    	Set<Point> point = new HashSet<Point>();
    	Set<Point> copypoints = new HashSet<Point>();
    	copypoints.addAll(points);
    	double x, y, angle = 360;
    	Point pointOnHull, endpoint, sign;
    	if (points.size() == 0) 
    		return point;
    	pointOnHull = copypoints.iterator().next();
    	for (Point p : copypoints) {
    		if (p.x() < pointOnHull.x())
    			pointOnHull = p;
    		if (p.y() > pointOnHull.y() && p.x() == pointOnHull.x())
    			pointOnHull = p;
    	}
    	point.add(pointOnHull);
    	if (points.size() == 1)
    		return point;
    	endpoint = pointOnHull;
    	sign = pointOnHull;
    	do {
    		for (Point p : copypoints) {
        		if (p != endpoint) {
        			if (calculateBearingToPoint(0, endpoint.x(), endpoint.y(), p.x(), p.y()) <= angle) {
        				
        				if (calculateBearingToPoint(0, endpoint.x(), endpoint.y(), p.x(), p.y()) == 0) {
        					if (pointOnHull.x() == p.x()) {
        						angle = calculateBearingToPoint(0, endpoint.x(), endpoint.y(), p.x(), p.y());
        						if (i == 0) 
        							sign = p;
        						else {
        							if (p.y() > sign.y())
            							sign = p;
								}
        						i++;
        					}	
        				}
        				else {
        					x = Math.pow(endpoint.x() - p.x(), 2) + Math.pow(endpoint.y() - p.y(), 2);
        					y = Math.pow(endpoint.x() - sign.x(), 2) + Math.pow(endpoint.y() - sign.y(), 2);
        					if (calculateBearingToPoint(0, endpoint.x(), endpoint.y(), p.x(), p.y()) == angle && x > y) 
        						sign = p;
        					else {
								angle = calculateBearingToPoint(0, endpoint.x(), endpoint.y(), p.x(), p.y());
								sign = p;
							}	
        				}
        			}	
        		}	
        	}
    		i = 0;
    		angle = 360;
    		copypoints.remove(sign);
    		endpoint = sign;
    		point.add(endpoint);
    	}while (pointOnHull != endpoint);
		return point;
    }
    
    private static double calculateBearingToPoint(int currentBearing, double currentX, double currentY, double targetX, double targetY) {
    	double a, b, c, sin, arcsin, angle;
    	a = targetX - currentX;
    	b = targetY - currentY;
    	c = Math.sqrt(a * a + b * b);
    	sin = b / c;
    	if (a >= 0 && b >= 0)
    		arcsin = 180 * Math.asin(sin) / Math.PI;
    	else if (a >= 0 && b <= 0)
    		arcsin = 360 + 180 * Math.asin(sin) / Math.PI;
    	else 
    		arcsin = 180 - 180 * Math.asin(sin) / Math.PI;
    	arcsin = 360 - arcsin + 90;
    	if (arcsin >= 360)
    		arcsin = arcsin - 360;
    	if (arcsin >= currentBearing)
    		angle = arcsin - currentBearing;
    	else 
			angle = 360 + arcsin - currentBearing;
    	return angle;
	}

	/**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        turtle.color(PenColor.GRAY);
        turtle.turn(225);
        turtle.forward(200);
        drawSquare(turtle, 40);
        drawRegularPolygon(turtle, 50, 5);
        

    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        Set<Point> points = new HashSet<Point>();
        Set<Point> point = new HashSet<Point>();
        
        drawPersonalArt(turtle);
        //draw the window
        //System.out.print(calculateBearingToPoint(0, 0, 0, 0, 1));
        turtle.draw();
    }

}
