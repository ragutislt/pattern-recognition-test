package com.adainius.recognition;

public class Point {

    private int x;
    private int y;

    private Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "{ x: " + x + ", y: " + y + " }";
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Point point = (Point) o;
        return this.x == point.x && this.y == point.y;
    }

    public int hashCode() {
        int result = 1;
        result = 37 * result + x;
        result = 19 * result + y;
        return result;
    }
}
