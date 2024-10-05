package kpan.not_enough_oxygen.world.biome;

import java.util.Objects;

public final class MVec3f implements Cloneable {
    public float x;
    public float y;
    public float z;

    public MVec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public MVec3f clone() {
        try {
            return (MVec3f) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(MVec3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public MVec3f scale(float s) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    public float distSq(MVec3f other) {
        return sq(x - other.x) + sq(y - other.y) + sq(z - other.z);
    }

    public float length1() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MVec3f other = (MVec3f) obj;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    private static float sq(float value) {
        return value * value;
    }
}
