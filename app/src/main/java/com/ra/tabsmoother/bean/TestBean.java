package com.ra.tabsmoother.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class TestBean implements Serializable {
    public String type;
    public String content;

    @Override
    public String toString() {
        return "TestBean{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
