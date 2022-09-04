package com.toss.bean;

import java.awt.Point;
import java.util.List;

public class VerifyContent {
    private String mobile;
    private List<Point> points;

    public VerifyContent() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
