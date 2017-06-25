package com.kozakteam.asteroids;

import android.graphics.PointF;

/**
 * Created by user on 25.06.2017.
 */

public class CollisionPackage {

    // model-space koordynacja w postaci vertexlist
    public float facingAngle;
    // kordynacja modelu
    public PointF[] vertexList;

    //okreslenie dlugosci vertexList
    public int vertexListLength;
    // sprawdzenie gdzie jest srodek obiektu
    public PointF worldLocation;


    /*
    sprawdzenie czy obiekt jest w obrebie kolizji uzywamy height / 2
   */
    public float radius;

    public PointF currentPoint = new PointF();
    public PointF currentPoint2 = new PointF();

    public CollisionPackage(PointF[] vertexList, PointF worldLocation, float radius, float facingAngle){
        vertexListLength = vertexList.length;
        this.vertexList = new PointF[vertexListLength];
        // Make a copy of the array
        for (int i = 0; i < vertexListLength; i++) {
            this.vertexList[i] = new PointF();
            this.vertexList[i].x = vertexList[i].x;
            this.vertexList[i].y = vertexList[i].y;
        }
        this.worldLocation = new PointF();
        this.worldLocation = worldLocation;
        this.radius = radius;
        this.facingAngle = facingAngle;
    }

}
