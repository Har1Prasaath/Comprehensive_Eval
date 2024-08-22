package compeval;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MergeSort {
	// Function to merge the two halves of the array
    private void merge(int[] arr, int start, int mid, int end, Counter counter) {
        int n1 = mid - start + 1; // Number of elements from start to middle
        int n2 = end - mid;       // Number of elements from mid to end

        // Create temporary arrays
        int[] left = new int[n1];
        int[] right = new int[n2];

        counter.basicOperations += n1 + n2 + 2; // Counting array creation and loop operations

        // Calculate memory usage for temporary arrays
        counter.memoryUsage += Integer.BYTES * (n1 + n2); // Memory used by the temporary arrays

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            left[i] = arr[start + i];
            counter.basicOperations++; // For each element assignment
        }
        for (int j = 0; j < n2; j++) {
            right[j] = arr[mid + 1 + j];
            counter.basicOperations++; // For each element assignment
        }

        // Merge the temporary arrays back into arr[start..end]
        int i = 0, j = 0, k = start;

        // Merge the temp arrays in order
        while (i < n1 && j < n2) {
            counter.comparisons++; // Increment the comparison count
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
            counter.basicOperations++; // For each assignment
        }

        // Copy remaining elements of left[], if any
        while (i < n1) {
            arr[k++] = left[i++];
            counter.basicOperations++; // For each assignment
        }

        // Copy remaining elements of right[], if any
        while (j < n2) {
            arr[k++] = right[j++];
            counter.basicOperations++; // For each assignment
        }
    }

    // Function to perform merge sort
    private void mergeSort(int[] arr, int start, int end, Counter counter) {
        if (start < end) {
            int mid = (start + end) / 2;
            counter.basicOperations++; // Increment basic operation for division and finding mid

            // Recursively sort the two halves
            mergeSort(arr, start, mid, counter);
            mergeSort(arr, mid + 1, end, counter);

            // Merge the sorted halves
            merge(arr, start, mid, end, counter);
        }
    }

    // Class to encapsulate counting variables
    static class Counter {
        int comparisons = 0;
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
            counter.memoryUsage += Integer.BYTES * n; // Memory usage by the main array

            MergeSort sorter = new MergeSort();

            long startTime = System.nanoTime(); // Start time measurement in nanoseconds
            sorter.mergeSort(arr, 0, n - 1, counter); // Sort the array
            long endTime = System.nanoTime(); // End time measurement
            long executionTime = endTime - startTime; // Calculate the time in nanoseconds

            // Print the sorted array
            for (int i = 0; i < n; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();

            // Print the number of comparisons, basic operations, memory usage, and execution time
            System.out.println("Number of Comparisons: " + counter.comparisons);
            System.out.println("Number of Swaps: N/A (No swaps in 3-Way Merge Sort)");
            System.out.println("Number of Basic Operations: " + counter.basicOperations);
            System.out.println("Memory Usage: " + counter.memoryUsage + " bytes");
            System.out.println("Execution Time: " + executionTime + " ns");

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open the file.");
        }
    }
}
