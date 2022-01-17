// Created by Brandon ;)

package com.example.assignment.Primitives;

import android.graphics.drawable.VectorDrawable;

import java.util.*;

public class Vector2{
    public float x;
    public float y;

    /**
     * Vector2 Default Constructor with default values of 0
     */
    public Vector2() {
        this.x = 0.f;
        this.y = 0.f;
    }

    /**
     * Vector2 Constructor that takes in x and y
     * @param x
     * @param y
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the length squared of the vector
     * @return x*x + y*y
     */
    public float LengthSquared() {
        return x*x + y*y;
    }

    /**
     * Returns the length of the vector
     * @return Math.sqrt(x*x + y*y)
     */
    public double Length() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Normalizes this vector
     */
    public void Normalize(){
        double length = Length();
        if(length != 0.0)
        {
            float s = 1.f / (float)length;
            x = x*s;
            y = y*s;
        }
    }

    /**
     * Calculates the distance between two vectors
     * @param a
     * @param b
     * @return Distance between the two vectors
     */
    public double Distance(Vector2 a, Vector2 b)
    {
        float v0 = b.x - a.x;
        float v1= b.y - a.y;
        return Math.sqrt(v0*v0 + v1*v1);
    }

    /**
     * Compares two vectors
     * @param other
     * @return true if they are the same else false
     */
    public boolean IsEqual(Vector2 other)
    {
        return(this.x == other.x && this.y == other.y);
    }

    /**
     * Adds vector with x & y
     * @param x
     * @param y
     * @return Vector with new added values
     */
    public Vector2 Add(float x, float y)
    {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Adds vector with another vector
     * @param other
     * @return Vector with new added values from another vector
     */
    public Vector2 Add(Vector2 other)
    {
        x += other.x;
        y += other.y;
        return this;
    }

    /**
     * Substracts vector with values x & y
     * @param x
     * @param y
     * @return Vector with new substracted values
     */
    public Vector2 Substract(float x, float y)
    {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * Substracts vector with another vector
     * @param other
     * @return Vector with new subtracted values from another vector
     */
    public Vector2 Substract(Vector2 other)
    {
        x -= other.x;
        y -= other.y;
        return this;
    }

}
