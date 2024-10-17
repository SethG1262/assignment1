package shapes;

import java.util.Comparator;

public abstract class Shape implements Comparable<Shape> {
    protected double height;

    public Shape(double height) {
        this.height = height;
    }

    // Abstract methods for base area and volume
    public abstract double getBaseArea();
    public abstract double getVolume();

    public double getHeight() {
        return height;
    }

    // Generate the output string dynamically based on the sorting type
    public String toString(String sortType) {
        switch (sortType) {
            case "v":
                return this.getClass().getSimpleName() + " [Volume=" + getVolume() + "]";
            case "h":
                return this.getClass().getSimpleName() + " [Height=" + getHeight() + "]";
            case "a":
                return this.getClass().getSimpleName() + " [Base Area=" + getBaseArea() + "]";
            default:
                return this.getClass().getSimpleName() + " [Unknown Sort Type]";
        }
    }

    // Comparator for base area
    public static Comparator<Shape> compareByBaseArea() {
        return (shape1, shape2) -> Double.compare(shape1.getBaseArea(), shape2.getBaseArea());
    }

    // Comparator for volume
    public static Comparator<Shape> compareByVolume() {
        return (shape1, shape2) -> Double.compare(shape1.getVolume(), shape2.getVolume());
    }

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.height, o.height);
    }
}
