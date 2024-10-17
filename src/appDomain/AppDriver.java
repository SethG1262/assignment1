package appDomain;

import shapes.*;
import utilities.SortingUtilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppDriver {

    public static void main(String[] args) {
        String fileName = "res/shapes3.txt";  // Default example file
        String sortAlgorithm = "h";  // Default: heap sort
        String sortType = "tv";  // Default: sort by volume

        // Command-line arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-f")) {
                fileName = args[i].substring(2);
            } else if (args[i].startsWith("-t")) {
                sortType = args[i].substring(2);
            } else if (args[i].startsWith("-s")) {
                sortAlgorithm = args[i].substring(2);
            }
        }

        long startTime = System.currentTimeMillis(); // Start the timer

        if (fileName.contains("shapes3.txt")) {
            // Process shapes3.txt in batches and accumulate all shapes
            List<Shape> allShapes = processShapesFileInBatches(fileName, sortType, sortAlgorithm);

            // Sort all accumulated shapes
            Shape[] allShapesArray = allShapes.toArray(new Shape[0]);
            sortShapes(allShapesArray, sortType, sortAlgorithm);

            // **Move this print statement to the start of the process**
            // Print first sorted element
            System.out.println("First sorted element: " + allShapesArray[0]);

            // Print second-last and last sorted elements
            printSecondLastAndLastElements(allShapesArray);

            long endTime = System.currentTimeMillis(); // End the timer
            System.out.println(sortAlgorithm + " run time was: " + (endTime - startTime) + " milliseconds");
        } else {
            // Process smaller files like shapes1.txt and shapes2.txt normally
            Shape[] shapesArray = processShapesFile(fileName);
            sortShapes(shapesArray, sortType, sortAlgorithm);
            
            // **Move this print statement to the start of the process**
            // Print first sorted element for smaller files
            System.out.println("First sorted element: " + shapesArray[0]);

            // Print all sorted shapes for smaller files
            printAllSortedShapes(shapesArray);

            // Print second-last and last elements
            printFirstAndLastElements(shapesArray);

            long endTime = System.currentTimeMillis(); // End the timer
            System.out.println(sortAlgorithm + " run time was: " + (endTime - startTime) + " milliseconds");
        }
    }

    // Process file normally (for shapes1.txt and shapes2.txt)
    public static Shape[] processShapesFile(String fileName) {
        List<Shape> shapesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Skip the first line (number of shapes)
            reader.readLine();

            // Read each shape line by line
            while ((line = reader.readLine()) != null) {
                String[] shapeData = line.split(" ");
                Shape shape = parseShapeData(shapeData);
                shapesList.add(shape);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return shapesList.toArray(new Shape[0]);  // Return the array of shapes
    }

    // Process shapes3.txt in batches of 1000 shapes and accumulate all shapes
    public static List<Shape> processShapesFileInBatches(String fileName, String sortType, String sortAlgorithm) {
        List<Shape> allShapes = new ArrayList<>();  // Accumulate all shapes
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<Shape> shapesBatch = new ArrayList<>();
            int lineNumber = 0;

            // Skip the first line (number of shapes)
            reader.readLine();

            // Read shapes in batches
            while ((line = reader.readLine()) != null) {
                String[] shapeData = line.split(" ");
                Shape shape = parseShapeData(shapeData);
                shapesBatch.add(shape);
                allShapes.add(shape);  // Add shape to the final list
                lineNumber++;

                // Once we reach 1000 shapes, sort and process the batch
                if (lineNumber % 1000 == 0) {
                    processBatch(shapesBatch, sortType, sortAlgorithm, lineNumber);
                    shapesBatch.clear();  // Clear the batch for the next set of shapes
                }
            }

            // Process the last batch if it's smaller than 1000
            if (!shapesBatch.isEmpty()) {
                processBatch(shapesBatch, sortType, sortAlgorithm, lineNumber);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return allShapes;  // Return all accumulated shapes
    }

    // Process and sort each batch of shapes
    public static void processBatch(List<Shape> shapesBatch, String sortType, String sortAlgorithm, int lineNumber) {
        // Convert the list to an array for sorting
        Shape[] shapesArray = shapesBatch.toArray(new Shape[0]);

        // Sort the batch of shapes
        sortShapes(shapesArray, sortType, sortAlgorithm);

        // Print every 1000th element
        System.out.println(lineNumber + "-th element is: " + shapesArray[0]);
    }

    // Sort shapes based on chosen algorithm and type
    public static void sortShapes(Shape[] shapes, String sortType, String sortAlgorithm) {
        // Sorting based on volume or height
        if (sortType.equalsIgnoreCase("tv")) {
            Arrays.sort(shapes, Shape.compareByVolume().reversed());  // Sort by volume descending
        } else {
            Arrays.sort(shapes);  // Default: sort by height
        }

        // Sorting algorithms
        switch (sortAlgorithm.toLowerCase()) {
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
                System.out.println("Invalid sorting algorithm. Defaulting to Bubble Sort.");
                SortingUtilities.bubbleSort(shapes);
                break;
        }
    }

    // Print all sorted elements for smaller files (shapes1.txt and shapes2.txt)
    public static void printAllSortedShapes(Shape[] shapes) {
        for (Shape shape : shapes) {
            System.out.println(shape);
        }
    }

    // Print first, second-to-last, and last element after the intervals
    public static void printSecondLastAndLastElements(Shape[] shapes) {
        if (shapes.length > 1) {
            System.out.println("Second Last sorted element: " + shapes[shapes.length - 2]);
        }
        System.out.println("Last sorted element: " + shapes[shapes.length - 1]);
    }

    // Print first and last elements for smaller files (no interval)
    public static void printFirstAndLastElements(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("No shapes found.");
            return;
        }
        // Print first element
        System.out.println("First sorted element: " + shapes[0]);

        // Print second-to-last and last elements
        if (shapes.length > 1) {
            System.out.println("Second Last sorted element: " + shapes[shapes.length - 2]);
        }
        System.out.println("Last sorted element: " + shapes[shapes.length - 1]);
    }

    // Parsing the shape data to create Shape objects
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
}
