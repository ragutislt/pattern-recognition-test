package com.adainius.recognition;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonValue;

public class Line {

    @JsonValue // when serializing to json, it will output an array of points
    private List<Point> points = new ArrayList<>();

    private Line() {
    }

    public static Line of(Point... points) {
        return Line.fromIterable(Arrays.asList(points));
    }

    public static Line of(Collection<Point> points) {
        return Line.fromIterable(points);
    }

    private static Line fromIterable(Iterable<Point> points) {
        Line line = new Line();
        for (Point point : points) {
            if (!line.points.contains(point))
                line.points.add(point);
        }
        return line;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < points.size(); i++) {
            sb.append(points.get(i).toString() + (i == (points.size() - 1) ? "" : ", "));
        }
        sb.append(" ]");
        sb.append("-" + this.hashCode());
        return sb.toString();
    }

    /**
     * Checks if lines are equal.<br/>
     * 2 reversed lines are considered equal: Line(p1,p2).equals(Line(p2,p1)) ==
     * true.<br/>
     * Worst-case performance is n/2, where n is nr. of points in a line in case
     * both lines are equal
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Line line = (Line) o;

        if (this.points.size() != line.points.size())
            return false;

        for (int i = 0, j = points.size() - 1; i < points.size(); i++, j--) {
            if ((compareSamePosition(points, line.points, i) && compareSamePosition(points, line.points, j))
                    || (compareReversePosition(points, line.points, i)
                            && compareReversePosition(points, line.points, i - 1))) {
                if (i >= j) // if start index equal or bigger than end index - we've crossed the middle
                    break;
                else
                    continue;
            }

            return false;
        }
        return true;
    }

    private boolean compareSamePosition(List<Point> pointsA, List<Point> pointsB, int startIndex) {
        return pointsA.get(startIndex).equals(pointsB.get(startIndex));
    }

    private boolean compareReversePosition(List<Point> pointsA, List<Point> pointsB, int startIndex) {
        int actualStartIndex = Math.max(0, startIndex);
        int endIndex = Math.min(pointsA.size() - 1 - actualStartIndex, pointsA.size() - 1);
        return pointsA.get(actualStartIndex).equals(pointsB.get(endIndex));
    }

    /**
     * Calculates hashCode of this line.<br/>
     * Will return the same for 2 lines that are inverses of each other:
     * Line(p1,p2).hashCode == Line(p2,p1).hashCode.<br/>
     * Performance is linear, O(n) if n is nr. of points in the line
     */
    public int hashCode() {
        int prime = 37;
        int resultNormalOrder = 1;
        int resultReverseOrder = 1;
        for (int i = 0, j = points.size() - 1; i < points.size(); i++, j--) {
            resultNormalOrder = prime * resultNormalOrder + points.get(i).hashCode();
            resultReverseOrder = prime * resultReverseOrder + points.get(j).hashCode();
        }
        return resultNormalOrder ^ resultReverseOrder;
    }
}