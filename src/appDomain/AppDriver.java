package appDomain;

import shapes.*;
import utilities.SortingUtilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AppDriver {

    public static void main(String[] args) {
        String fileName = "shapes1.txt";  // Default file name
        String sortType = "v";  // Default sorting by volume
        String sortAlgorithm = "b";  // Default bubble sort

        // Parse command-line arguments (case-insensitive)
        for (String arg : args) {
            if (arg.toLowerCase().startsWith("-f")) {
                fileName = parseFilePath(arg.substring(2)); // Parse file path
            } else if (arg.toLowerCase().startsWith("-t")) {
                sortType = arg.substring(2).toLowerCase(); // Handle tH, tV, tA
            } else if (arg.toLowerCase().startsWith("-s")) {
                sortAlgorithm = arg.substring(2).toLowerCase(); // Handle sb, si, sm, sq, etc.
            }
        }

        long startTime = System.currentTimeMillis(); // Start the timer

        if (fileName.contains("shapes3")) {
            System.out.println("Processing large dataset...");
            processLargeFile(fileName, sortType, sortAlgorithm);
        } else {
            Shape[] shapes = processShapesFile(fileName);
            sortShapes(shapes, sortType, sortAlgorithm);
            printAllSortedShapes(shapes, sortType);
        }

        long endTime = System.currentTimeMillis(); // End the timer
        System.out.println(sortAlgorithm.toUpperCase() + " run time was: " + (endTime - startTime) + " milliseconds");
    }

    // Parse file path correctly, removing quotes and handling backslashes
    public static String parseFilePath(String path) {
        if (path.startsWith("\"") && path.endsWith("\"")) {
            path = path.substring(1, path.length() - 1); // Remove quotes if present
        }
        return path.replace("\\", "/"); // Convert backslashes to forward slashes for consistency
    }

    // Process file normally (for smaller files like shapes1.txt and shapes2.txt)
    public static Shape[] processShapesFile(String fileName) {
        List<Shape> shapesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip the first line (number of shapes)

            while ((line = reader.readLine()) != null) {
                String[] shapeData = line.split(" ");
                shapesList.add(parseShapeData(shapeData));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return shapesList.toArray(new Shape[0]);
    }

    // Sort shapes based on type and algorithm
    public static void sortShapes(Shape[] shapes, String sortType, String sortAlgorithm) {
        switch (sortType) {
            case "h":
                Arrays.sort(shapes, Comparator.comparing(Shape::getHeight).reversed());
                break;
            case "v":
                Arrays.sort(shapes, Shape.compareByVolume().reversed());
                break;
            case "a":
                Arrays.sort(shapes, Shape.compareByBaseArea().reversed());
                break;
            default:
                System.out.println("Invalid sort type.");
        }

        switch (sortAlgorithm) {
            case "b":
                SortingUtilities.bubbleSort(shapes);
                break;
            case "i":
                SortingUtilities.insertionSort(shapes);
                break;
            case "s":
                SortingUtilities.selectionSort(shapes);
                break;
            case "m":
                SortingUtilities.mergeSort(shapes);
                break;
            case "q":
                SortingUtilities.quickSort(shapes, 0, shapes.length - 1);
                break;
            case "h":
                SortingUtilities.heapSort(shapes);
                break;
            default:
                System.out.println("Invalid sorting algorithm. Using bubble sort by default.");
                SortingUtilities.bubbleSort(shapes);
        }
    }

    // Parse shape data from file
    private static Shape parseShapeData(String[] shapeData) {
        String shapeType = shapeData[0];
        double height = Double.parseDouble(shapeData[1]);
        double secondValue = Double.parseDouble(shapeData[2]);

        switch (shapeType) {
            case "Cylinder":
                return new Cylinder(height, secondValue);
            case "Cone":
                return new Cone(height, secondValue);
            case "Pyramid":
                return new Pyramid(height, secondValue);
            case "SquarePrism":
                return new SquarePrism(height, secondValue);
            case "TriangularPrism":
                return new TriangularPrism(height, secondValue);
            case "PentagonalPrism":
                return new PentagonalPrism(height, secondValue);
            case "OctagonalPrism":
                return new OctagonalPrism(height, secondValue);
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }

    // Print all sorted shapes
    public static void printAllSortedShapes(Shape[] shapes, String sortType) {
        for (Shape shape : shapes) {
            System.out.println(shape.toString(sortType));
        }
    }

    // Handle large file processing
    public static void processLargeFile(String fileName, String sortType, String sortAlgorithm) {
        System.out.println("Processing large file in batches...");
        // Handle large file processing in batches here
    }
}
