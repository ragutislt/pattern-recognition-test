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
        plane.addPoint(new Point(1,2));
        assertTrue(plane.size() == 1);
    }

    @Test
    public void clearPoints() {
        Plane plane = new Plane();
        plane.addPoint(new Point(1,2));
        plane.clear();
        assertTrue(plane.size() == 0);
    }

    @Test
    public void lineSegments() {
        Plane plane = new Plane();
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,1);
        Point p3 = new Point(2,2);
        plane.addPoint(p1);
        plane.addPoint(p2);
        plane.addPoint(p3);

        Set<Line> lines = plane.getLinesWithNPoints(2);
        assertTrue(lines.contains(Line.of(p1,p2)));
        assertTrue(lines.contains(Line.of(p1,p3,p2)));
        assertTrue(lines.size() == 6);

        lines = plane.getLinesWithNPoints(3);
        assertTrue(lines.contains(Line.of(p1,p3,p2)));
        assertTrue(lines.size() == 3);

        lines = plane.getLinesWithNPoints(4);
        assertTrue(lines.size() == 0);

        lines = plane.getLinesWithNPoints(99);
        assertTrue(lines.size() == 0);
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
        Line line1 = Line.of(new Point(1,2), new Point(2,2));
        Line line2 = Line.of(new Point(1,2), new Point(2,2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1,2), new Point(2,1));
        line2 = Line.of(new Point(2,1), new Point(1,2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());

        line1 = Line.of(new Point(1,2), new Point(3,3), new Point(2,1));
        line2 = Line.of(new Point(2,1), new Point(3,3), new Point(1,2));
        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    public void linesAreNotEqual() {
        Line line1 = Line.of(new Point(1,2), new Point(2,2));
        Line line2 = Line.of(new Point(1,2), new Point(3,2));
        assertNotEquals(line1, line2);

        line1 = Line.of(new Point(1,2), new Point(3,3), new Point(2,1));
        line2 = Line.of(new Point(2,1), new Point(1,2), new Point(3,3));
        assertNotEquals(line1, line2);
    }

    @Test
    public void pointsAreEqual() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(1,2);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void pointsAreNotEqual() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(1,3);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());

        p1 = new Point(1,2);
        p2 = new Point(2,1);
        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}