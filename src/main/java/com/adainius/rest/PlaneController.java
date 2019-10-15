package com.adainius.rest;

import java.util.Set;

import javax.annotation.PostConstruct;

import com.adainius.constants.Errors;
import com.adainius.recognition.Plane;
import com.adainius.recognition.Point;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaneController {

    Plane plane;

    @PostConstruct
    public void initializePlane() {
        this.plane = new Plane();
        plane.addPoint(new Point(1, 2));
        plane.addPoint(new Point(3, 1));
        plane.addPoint(new Point(2, 2));
    }

    @PostMapping(value = "/point", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Point addPoint(@RequestBody Point point) {
        this.plane.addPoint(point);
        return point;
    }

    @GetMapping("/space")
    public Set<Point> space() {
        return this.plane.getPoints();
    }

    @GetMapping("/lines/{n}")
    public ResponseEntity<Object> getLinesOfMinLengthN(@PathVariable int n) {
        if(n < 2) {
            return ResponseEntity.badRequest().body(new RestErrorResponse(Errors.MIN_NR_OF_LINES));
        }
        return ResponseEntity.ok(this.plane.getLinesWithNPoints(n));
    }

    @DeleteMapping("/space")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void emptySpace() {
        this.plane.clear();
    }
}
