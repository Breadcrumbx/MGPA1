package com.example.assignment;

// Created by TanSiewLan2021

public interface Collidable {
    String GetType();

    float GetPosX();
    float GetPosY();
    float GetRadius();

    float GetRight();
    float GetBottom();
    void OnHit(Collidable _other);
}

