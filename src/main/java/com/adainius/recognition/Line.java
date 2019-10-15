package com.adainius.recognition;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonValue;

public class Line {
    /**
     * List for preserving insertion order. <br/>
     * We will guard for duplicates manually. <br/>
     * We could use a LinkedHashSet, which would avoid duplicates and preserve
     * insertion order, but we couldn't use a simple for loop in equals and hashCode
     * in that case
     */
    @JsonValue
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
        return sb.toString();
    }

    /**
     * Checks if lines are equal.<br/>
     * 2 reversed lines are considered equal: Line(p1,p2).equals(Line(p2,p1)) ==
     * true
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Line line = (Line) o;

        for (int i = 0, j = points.size() - 1; i < points.size(); i++, j--) {
            if (!(points.get(i).equals(line.points.get(i)) || points.get(i).equals(line.points.get(j))))
                return false;
            if (i >= j)
                break;
        }
        return true;
    }

    /**
     * Calculates hashCode of this line.<br/>
     * Will return the same for 2 lines that are inverses of each other:
     * Line(p1,p2).hashCode == Line(p2,p1).hashCode
     */
    public int hashCode() {
        int prime = 37;
        int result = 1;
        for (int i = 0, j = points.size() - 1; i < points.size(); i++, j--) {
            result = prime * result + points.get(i).hashCode() + points.get(j).hashCode();
            if (i >= j)
                break;
        }
        return result;
    }
}