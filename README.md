# Pattern recognition test challenge

An application that computes all possible lines of size N from a plane with M points.

I've tried not to have too many comments in the code (trying to have clean code instead), but rather compile a summary here.

## Glossary:
* Point - an object with 'x' (int) and 'y' (int) properties
*	Line - a set of minimum 2 points
*	Plane/space - a collection of points in the space
	
## Data structures used:
* Point
* Line - Points are stored in an ArrayList to preserve insertion order and to be able to retrieve by index.  
    Duplicate insertion detection is done manually.  
		2 lines that contain points in reverse order of each other are considered equal: Line(p1,p2).equals(Line(p2,p1)) == true  
		When serialized to json, it becomes an array of points.  
		To create a line, we use a static factory method: Line.of(points...)  
		Line.equals and Line.hashCode has n/2 performance (n - nr. of points in a line)
* Plane - contains all the points in the space, stores them in a HashSet to not have duplicates.  
		Points can be added.  
		Plane can be emptied (all points removed).  
		Computes all possible lines with at least n points. Min value of n is 2, otherwise IllegalArgumentException is thrown.  
		Uses backtracking to compute all such lines. Performance is O(n!*m/2), where n = nr. of points in the space, m = nr. of points in a given line (min line length is the parameter we choose). Valid lines are added to a HashSet, where each .add call will compute hashCode for the line, which has m/2 performance.
		
## REST API:
PlaneController contains all the api methods. Collected all the endpoints here for simplicity's sake.

Contains 4 endpoints:
* POST /point with body {"x": ..., "y": ...}  
			Accepts only integers as coordinates
* GET /space - returns all points in space
* DELETE /space - removes all endpoints from the space
* GET /lines/n - gets all lines with at least n points.  
			Validates n to be at least 2. A line must have at least 2 points to be valid.
			
## Tests
Both the business logic and the web layer have tests. I was doing TDD while developing this project.  
	Point, Line, Plane are covered by JUnit tests (TestPlane.java, it might also make sense to create a test class for each object).  
	Web layer is covered by spring boot integration tests, where a web server is imitated.
	
## Questions:
1. Is a line of 1 point a valid line? In pattern recognition from images - can a particularly bright point be considered a feature in itself?  
		This assumes that the size of this bright point should be the size of our selected point measure.
2. Can points be floating-point numbers?  
		If so, precision should be decided since we can always make a float more precise.
