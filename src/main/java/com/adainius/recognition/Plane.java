package com.adainius.recognition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.adainius.constants.Errors;

public class Plane {

    // will contain unique points only
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

    /**
     * Recursive method to find permutations from a given point.<br/>
     * Backtracking approach, very slow, should be O(n!*n/2) in time complexity. n!
     * because of backtracking, n/2 because each time we add to linesFound, hashCode
     * of Line will be called, which will iterate n/2 times.
     * 
     * @param point      current point
     * @param linePoints points already present in the line
     * @param pointsLeft points that are left to iterate over
     * @param levelsMin  minimum nr. of points to have a valid line
     * @param linesFound all lines found
     */
    private void findLines(Point point, Set<Point> linePoints, Set<Point> pointsLeft, int levelsMin,
            Set<Line> linesFound) {
        linePoints.add(point);
        pointsLeft.remove(point);

        if (linePoints.size() < levelsMin && pointsLeft.isEmpty())
            return;

        // if we have at least a min nr. of points needed - make a line out of them
        if (linePoints.size() >= levelsMin) {
            Line newLine = Line.of(linePoints);
            linesFound.add(newLine);
        }

        // if no points left, that means we traversed full depth and can make a line
        if (pointsLeft.size() == 0) {
            Line newLine = Line.of(linePoints);
            linesFound.add(newLine);
        } else {
            // otherwise, go deeper
            Iterator<Point> it = pointsLeft.iterator();
            while (it.hasNext()) {
                findLines(it.next(), this.cloneOfSet(linePoints), this.cloneOfSet(pointsLeft), levelsMin, linesFound);
            }
        }
    }

    /**
     * Retrieves all lines with a minimum of n points.
     * 
     * @param n min number of points in a line: [2; +inf)
     * @return set of lines
     */
    public Set<Line> getLinesWithNPoints(int n) {
        if (n < 2) {
            // Can we consider a line with 1 point as a line?
            // If we're detecting features in an image, perhaps a very bright point could be
            // a feature in itself?
            throw new IllegalArgumentException(Errors.MIN_NR_OF_LINES);
        }

        Set<Line> linesFound = new HashSet<>();
        Iterator<Point> it1 = points.iterator();
        while (it1.hasNext()) {
            Point current1 = it1.next();
            Set<Point> linePoints = new LinkedHashSet<>();
            findLines(current1, linePoints, this.cloneOfSet(points), n, linesFound);
        }
        return linesFound;
    }

    private Set<Point> cloneOfSet(Set<Point> originalSet) {
        return originalSet.stream().collect(Collectors.toCollection(LinkedHashSet::new));
    }

}