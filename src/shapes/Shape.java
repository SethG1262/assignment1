package shapes;

import java.util.Comparator;

public abstract class Shape implements Comparable<Shape> {
    protected double height;

    public Shape(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    // Abstract methods for base area and volume
    public abstract double getBaseArea();
    public abstract double getVolume();

    // Compare shapes by height (for Comparable interface)
    @Override
    public int compareTo(Shape other) {
        return Double.compare(this.height, other.height);
    }

    // Comparator for base area
    public static Comparator<Shape> compareByBaseArea() {
        return (shape1, shape2) -> Double.compare(shape1.getBaseArea(), shape2.getBaseArea());
    }

    // Comparator for volume
    public static Comparator<Shape> compareByVolume() {
        return (shape1, shape2) -> Double.compare(shape1.getVolume(), shape2.getVolume());
    }
}
