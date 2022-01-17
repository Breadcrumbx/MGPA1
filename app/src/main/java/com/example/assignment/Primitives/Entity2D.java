package com.example.assignment.Primitives;

import com.example.assignment.Collidable;
import com.example.assignment.EntityBase;

public abstract class Entity2D implements EntityBase, Collidable {
    public boolean IsDone;
    public Vector2 Pos;

    public Entity2D(){
        IsDone = false;
        Pos = new Vector2();
    }


    @Override
    public boolean IsDone()
    {
        return IsDone;
    }

    @Override
    public void SetIsDone(boolean _isDone)
    {
        IsDone = _isDone;
    }
    @Override
    public float GetPosX()
    {
        return Pos.x;
    }
    @Override
    public float GetPosY()
    {
        return Pos.y;
    }


}
