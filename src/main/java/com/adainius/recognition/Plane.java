package com.adainius.recognition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Plane {

    private Set<Point> points = new HashSet<>();

    public void addPoint(Point point) {
        points.add(point);
    }

    public Set<Point> getPoints() {
        return Collections.unmodifiableSet(this.points);
    }

    public void clear() {
        this.points.clear();
    }

    public int size() {
        return this.points.size();
    }

    private boolean findLines(Point point, Set<Point> linePoints, Set<Point> pointsLeft, int levelsMin, Set<Line> linesFound) {
        linePoints.add(point);
        pointsLeft.remove(point);
        if(linePoints.size() >= levelsMin) {
            Line newLine = Line.of(linePoints);
            linesFound.add(newLine);
        }
        if (pointsLeft.size() == 0) {
            Line newLine = Line.of(linePoints);
            linesFound.add(newLine);
            return true;
        } else {
            Iterator<Point> it = pointsLeft.iterator();
            while (it.hasNext()) {
                findLines(it.next(), linePoints.stream().collect(Collectors.toCollection(LinkedHashSet::new)),
                        pointsLeft.stream().collect(Collectors.toCollection(LinkedHashSet::new)), levelsMin, linesFound);
            }
        }
        return true;
    }

    public Set<Line> getLinesWithNPoints(int n) {
        Set<Line> linesFound = new HashSet<>();
        Iterator<Point> it1 = points.iterator();
        while (it1.hasNext()) {
            Point current1 = it1.next();
            Set<Point> linePoints = new LinkedHashSet<>();
            findLines(current1, linePoints, points.stream().collect(Collectors.toCollection(LinkedHashSet::new))
                    ,n ,linesFound);
        }
        return linesFound;
    }

}