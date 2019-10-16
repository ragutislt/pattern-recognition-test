package com.adainius.recognition;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

public class TestPlane {
    @Test
    public void addPointToPlane() {
        Plane plane = new Plane();
        plane.addPoint(new Point(1, 2));
        assertTrue(plane.size() == 1);
    }

    @Test
    public void clearPoints() {
        Plane plane = new Plane();
        plane.addPoint(new Point(1, 2));
        plane.clear();
        assertTrue(plane.size() == 0);
    }

    // number of lines should be: sum(n!/2 + ... + m!/2), where n - min nr. of
    // points in a line, m - total nr. of points in space
    @Test
    public void getsLineSegments() {
        Plane plane = new Plane();
        Point p1 = new Point(1, 2);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(-2, -2);

        plane.addPoint(p1);
        plane.addPoint(p2);
        plane.addPoint(p3);
        getFactorialOf(4, 4);
        getFactorialOf(4, 3);
        getFactorialOf(4, 2);
        getFactorialOf(4, 1);
        Set<Line> lines = plane.getLinesWithNPoints(2);
        assertTrue(lines.contains(Line.of(p1, p2)));
        assertTrue(lines.contains(Line.of(p1, p3, p2)));
        assertTrue(lines.size() == distinctLinesAvailable(2, 3));

        distinctLinesAvailable(4, 4);
        distinctLinesAvailable(3, 4);
        distinctLinesAvailable(2, 4);
        distinctLinesAvailable(1, 4);

        lines = plane.getLinesWithNPoints(3);
        assertTrue(lines.contains(Line.of(p1, p3, p2)));
        assertTrue(lines.size() == distinctLinesAvailable(3, 3));

        lines = plane.getLinesWithNPoints(4);
        assertTrue(lines.size() == 0);

        lines = plane.getLinesWithNPoints(99);
        assertTrue(lines.size() == 0);

        Point p4 = new Point(-2, 5);
        plane.addPoint(p4);
        lines = plane.getLinesWithNPoints(2);
        assertTrue(lines.size() == distinctLinesAvailable(2, 4));

        Point p5 = new Point(7, 3);
        plane.addPoint(p5);
        lines = plane.getLinesWithNPoints(2);
        assertTrue(lines.size() == distinctLinesAvailable(2, 5));
    }

    /**
     * Calculates the number of distinct lines possible in a plane with m points and
     * n as min nr. of points to consider it a valid line
     * 
     * @param minPointsForLine
     * @param totalPointsAvailable
     * @return
     */
    private int distinctLinesAvailable(int minPointsForLine, int totalPointsAvailable) {
        int total = 0;
        for (int i = totalPointsAvailable; i >= minPointsForLine; i--) {
            total += getFactorialOf(totalPointsAvailable, i);
        }
        return total / 2;
    }

    /**
     * Gets factorial but only taking the nr. of digits from the highest one.<br/>
     * Example: getFactorialOf(4,2) == 4*3 == 12 <br/>
     * getFactorialOf(4,3) == 4*3*2 == 24
     * 
     * @param n
     * @param digitsToCalculate
     * @return
     */
    private int getFactorialOf(int n, int digitsToCalculate) {
        int factorial = 1;
        for (int i = n, j = digitsToCalculate; j > 0; i--, j--) {
            factorial *= i;
        }
        return factorial;
    }

    @Test(expected = IllegalArgumentException.class)
    public void linesSegmentsDoesNotAllow1pointLines() {
        Plane plane = new Plane();
        plane.getLinesWithNPoints(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void linesSegmentsDoesNotAllowInvalidN() {
        Plane plane = new Plane();
        plane.getLinesWithNPoints(0);
    }

    @Test
    public void linesAreEqual() {
        Line line1 = Line.of(new Point(1, 2), new Point(2, 2));
        Line line2 = Line.of(new Point(1, 2), new Point(2, 2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1, 2), new Point(2, 1));
        line2 = Line.of(new Point(2, 1), new Point(1, 2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1, 2), new Point(3, 3), new Point(2, 1));
        line2 = Line.of(new Point(2, 1), new Point(3, 3), new Point(1, 2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1, 2), new Point(3, 3), new Point(2, 1), new Point(2, 5));
        line2 = Line.of(new Point(2, 5), new Point(2, 1), new Point(3, 3), new Point(1, 2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1, 2), new Point(3, 3), new Point(2, 1), new Point(2, 5), new Point(7, 1));
        line2 = Line.of(new Point(7, 1), new Point(2, 5), new Point(2, 1), new Point(3, 3), new Point(1, 2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    public void linesAreNotEqual() {
        Line line1 = Line.of(new Point(1, 2), new Point(2, 2));
        Line line2 = Line.of(new Point(1, 2), new Point(3, 2));
        assertNotEquals(line1, line2);

        line1 = Line.of(new Point(1, 2), new Point(3, 3), new Point(2, 1));
        line2 = Line.of(new Point(2, 1), new Point(1, 2), new Point(3, 3));
        assertNotEquals(line1, line2);

        line1 = Line.of(new Point(1, 2), new Point(-2, -2), new Point(-2, 5), new Point(0, 0));
        line2 = Line.of(new Point(1, 2), new Point(-2, 5), new Point(-2, -2), new Point(0, 0));
        assertNotEquals(line1, line2);
        assertNotEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1, 2), new Point(-2, 6), new Point(-2, 5), new Point(0, 0), new Point(7, 5));
        line2 = Line.of(new Point(1, 2), new Point(-2, 6), new Point(0, 0), new Point(-2, 5), new Point(7, 5));
        assertNotEquals(line1, line2);
        assertNotEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1, 2), new Point(-2, 6), new Point(-2, 5), new Point(0, 0), new Point(7, 5),
                new Point(6, 4));
        line2 = Line.of(new Point(1, 2), new Point(-2, 6), new Point(0, 0), new Point(-2, 5), new Point(7, 5),
                new Point(6, 4));
        assertNotEquals(line1, line2);
        assertNotEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    public void pointsAreEqual() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void pointsAreNotEqual() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());

        p1 = new Point(1, 2);
        p2 = new Point(2, 1);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}