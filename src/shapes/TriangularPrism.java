package shapes;

public class TriangularPrism extends Shape {
    private double edgeLength;

    public TriangularPrism(double height, double edgeLength) {
        super(height);
        this.edgeLength = edgeLength;
    }

	@Override
    public double getBaseArea() {
        return (Math.pow(edgeLength, 2) * Math.sqrt(3)) / 4;
    }

    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
    public String toString(String sortType) {
        return super.toString(sortType);
    }
}
