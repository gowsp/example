package com.toss.bean;

import java.awt.*;

/**
 * 生成的验证数据
 */
public class VerifyData {
    private String strData;
    private Point point;

    public VerifyData() {
    }

    public VerifyData(String strData, Point point) {
        super();
        this.strData = strData;
        this.point = point;
    }

    public String getStrData() {
        return strData;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "VerifyData{" +
                "strData='" + strData + '\'' +
                ", point=" + point +
                '}';
    }
}
