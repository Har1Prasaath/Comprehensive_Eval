package compeval;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class QuickSort {
	// Function to partition the array
    public static int partition(int[] arr, int start, int end, Counter counter) {
        int piv = arr[end]; // Pivot element
        int slow = start - 1; // The slow pointer
        int fast = start; // The fast pointer
        counter.basicOperations += 3; // Counting the above three assignments as basic operations

        while (fast < end) {
            counter.comparisons++; // Increment for each comparison
            if (arr[fast] < piv) { // When an element smaller than the pivot is encountered
                slow++;
                swap(arr, fast, slow);
                counter.swaps++; // Increment the swap counter
            }
            fast++;
            counter.basicOperations++; // Increment for the fast++ operation
        }
        swap(arr, fast, slow + 1); // Place the pivot in its correct position
        counter.swaps++; // Increment the swap counter

        return slow + 1; // Return the position of the pivot
    }

    // Swap function
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Quick sort function
    public static void quicksort(int[] arr, int start, int end, Counter counter) {
        if (start < end) {
            int pivot = partition(arr, start, end, counter); // Partition the array
            quicksort(arr, start, pivot - 1, counter); // Recursively sort the left part
            quicksort(arr, pivot + 1, end, counter); // Recursively sort the right part
        }
    }

    // Class to encapsulate counting variables
    static class Counter {
        int comparisons = 0;
        int swaps = 0;
        int basicOperations = 0;
        int memoryUsage = 0;
    }

    public static void main(String[] args) {
        try {
            Scanner inputFile = new Scanner(new File("input1000.txt")); // Create a Scanner object to read from a file

            if (!inputFile.hasNext()) { // Check if the file is empty
                System.out.println("Error: File is empty.");
                return;
            }

            int n = inputFile.nextInt(); // Read the total number of elements from the file
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = inputFile.nextInt(); // Read each element from the file
            }
            inputFile.close(); // Close the file

            Counter counter = new Counter();
            counter.memoryUsage = Integer.BYTES * (n + 3); // Memory usage by array and counters

            long startTime = System.nanoTime(); // Start time measurement in nanoseconds
            quicksort(arr, 0, n - 1, counter); // Sort the array
            long endTime = System.nanoTime(); // End time measurement
            long executionTime = endTime - startTime; // Calculate the time in nanoseconds

            // Print the sorted array
            for (int i = 0; i < n; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();

            // Print the number of comparisons, swaps, basic operations, memory usage, and execution time
            System.out.println("Number of Comparisons: " + counter.comparisons);
            System.out.println("Number of Swaps: " + counter.swaps);
            System.out.println("Number of Basic Operations: " + counter.basicOperations);
            System.out.println("Memory Usage: " + counter.memoryUsage + " bytes");
            System.out.println("Execution Time: " + executionTime + " ns");

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open the file.");
        }
    }
}
