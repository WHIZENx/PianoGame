package com.cmu.project.pianogame.Game.Utils;

public class ItemHitBox {

    public static boolean HitBox(float scalex, float scaley, float width, float height, float x, float y) {
        return scalex <= x && x <= (scalex + width) && scaley <= y && y <= (scaley + height); }
}
