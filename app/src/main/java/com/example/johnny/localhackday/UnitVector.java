package com.example.johnny.localhackday;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by Kathy on 2016-12-03.
 */


public class UnitVector {
    private float x, y;
    private final float ERROR_BOUND = 0.25f;

    //pass 2 points and this constructor will calculate the components
    public UnitVector(PointF a, PointF b){
        this.update(a,b);
    }

    //pass the values of the components to this constructor
    public UnitVector(float a, float b){
        this.update(a,b);
    }

    //default is (0,1)
    public UnitVector(){
        this.x = 0f;
        this.y = 1f;
    }

    public boolean isEqual(UnitVector other){
        boolean a = ((this.x - other.x) <= this.ERROR_BOUND);
        boolean b = ((this.y - other.y) <= this.ERROR_BOUND);
        return (a && b);
    }

    public void update(PointF a, PointF b){
        float len = (float) Math.sqrt((this.x*this.x)+(this.y*this.y));
        this.x = (b.x - a.x)/len;
        this.y = (b.y - a.y)/len;
    }

    public void update(float a, float b){
        float len = (float) Math.sqrt((a*a)+(b*b));
        this.x = a/len;
        this.y = b/len;
    }

    public float getX(){
        return this.x;
    }

    public  float getY(){
        return this.y;
    }
}
