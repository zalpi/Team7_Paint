package com.csci4020.team7_paint;


import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public class objects {

    private List[] colors;
    private List [] thick;
    private List<Path> paths;

    private List<String> past;
    private int current;
    private int currentThick;

    public List<String> getPast() {
        return past;
    }

    public void setPast(List<String> past) {
        this.past = past;
    }


    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public List<Integer>[] getColors() {
        return colors;
    }

    public void setColors(List[] colors) {
        this.colors = colors;
    }


    public List[] getThick() {
        return thick;
    }

    public void setThickness(List[] thickness) {
        this.thick = thickness;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrentThick() {
        return currentThick;
    }

    public void setCurrentThick(int currentThick) {
        this.currentThick = currentThick;
    }

    public objects() {
        paths = new ArrayList<>();
        colors = new List[3];
        thick = new List[4];
        past = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            colors[i] = new ArrayList();
            thick[i] = new ArrayList();
        }
        thick[3]=new ArrayList();
    }
}
