package kpan.not_enough_oxygen.world.biome;

import java.util.List;
import java.util.Objects;

public final class MVec2f implements Cloneable {
    public float x;
    public float y;

    public MVec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MVec2f(float... array) {
        this(array[0], array[1]);
    }

    public MVec2f(List<Float> list) {
        this(list.get(0), list.get(1));
    }

    @Override
    public MVec2f clone() {
        try {
            return (MVec2f) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public MVec2f add(float x, float y) {
        x += x;
        y += y;
        return this;
    }

    public MVec2f add(MVec2f v, float scale) {
        return add(v.x * scale, v.y * scale);
    }

    public MVec2f add(MVec2f v) {
        return add(v, 1);
    }

    public MVec2f sub(MVec2f v) {
        return add(v, -1);
    }

    public MVec2f scale(float sx, float sy) {
        x *= sx;
        y *= sy;
        return this;
    }

    public MVec2f scale(float s) {
        return scale(s, s);
    }

    public MVec2f scale(MVec2f s) {
        return scale(s.x, s.y);
    }

    public MVec2f divBy(MVec2f s) {
        return scale(1 / s.x, 1 / s.y);
    }

    public float distanceSq(MVec2f other) {
        return distanceSq(x - other.x, y - other.y);
    }

    public float distance1(MVec2f other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public float lengthSq() {
        return distanceSq(x, y);
    }

    public float length1() {
        return Math.abs(x) + Math.abs(y);
    }

    public float getAngle(MVec2f v) {
        return getAngle(this, v);
    }

    public float getAngleFromOrigin() {
        return getAngle(x, y);
    }

    public MVec2f normalize() {
        if (x == 0 && y == 0) {
            x = 1;
        } else {
            float invLen = 1 / lengthSq();
            x *= invLen;
            y *= invLen;
        }
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MVec2f other))
            return false;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    // utils

    public static float distanceSq(float dx, float dy) {
        return dx * dx + dy * dy;
    }

    public static float getAngle(MVec2f p1, MVec2f p2) {
        float dx = p2.x - p1.x;
        float dy = p2.y - p1.y;
        return getAngle(dx, dy);
    }

    public static float getAngle(float dx, float dy) {
        return (float) Math.atan2(dy, dx);
    }


    public static MVec2f min(MVec2f v1, MVec2f v2) {
        return new MVec2f(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y));
    }

    public static MVec2f max(MVec2f v1, MVec2f v2) {
        return new MVec2f(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y));
    }
}
