package com.adainius.rest;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import com.adainius.recognition.Point;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "server.port=1234")
public class TestWebLayer {

    @Autowired
    private PlaneController controller;

    private int port = 1234;
    private String url = "http://localhost:" + port + "/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertTrue(controller != null);
    }

    @Before
    public void recreatePlane() {
        controller.initializePlane();
    }

    @Test
    public void addsPointsToSpace() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>("{ \"x\": 3, \"y\": 3 }", headers);

        this.restTemplate.postForObject(url + "point", requestEntity, Point.class);
        assertTrue(controller.plane.size() == 4);
    }

    @Test
    public void returnsAllPointsInSpace() {
        assertTrue(this.restTemplate.getForObject(url + "space", Set.class).size() == 3);
    }

    @Test
    public void returnsLinesOfMinLengthN() {
        assertTrue(this.restTemplate.getForObject(url + "lines/" + 2, Set.class).size() == 6);
    }

    @Test
    public void emptiesSpace() {
        this.restTemplate.delete(url + "space");
        assertTrue(controller.plane.size() == 0);
    }
}