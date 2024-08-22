package compeval;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InPlaceHeapSort {

    // Function to restore the heap property
    private void heapify(int arr[], int n, int i, Counter counter) {
        int largest = i; // Assume the root is the largest
        int left = 2 * i + 1; // Get the left child position
        int right = 2 * i + 2; // Get the right child position
        counter.basicOperations += 3; // Counting the above three assignments as basic operations

        // If left child is larger than root
        if (left < n) {
            counter.comparisons++; // Increment for the comparison
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        // If right child is larger than the largest so far
        if (right < n) {
            counter.comparisons++; // Increment for the comparison
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        // If largest is not root, swap it with the largest child
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            counter.swaps++; // Increment the swap counter
            heapify(arr, n, largest, counter); // Recursively heapify the affected subtree
        }
    }

    // Function to perform heap sort
    public void heapsort(int arr[], int n, Counter counter) {
        counter.memoryUsage += Integer.BYTES * n; // Memory used by the array

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, counter);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            counter.swaps++; // Increment the swap counter
            heapify(arr, i, 0, counter);
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
            counter.memoryUsage += Integer.BYTES * 4; // Memory usage by variables

            InPlaceHeapSort sorter = new InPlaceHeapSort();

            long startTime = System.nanoTime(); // Start time measurement in nanoseconds
            sorter.heapsort(arr, n, counter); // Sort the array
            long endTime = System.nanoTime(); // End time measurement
            long executionTime = endTime - startTime; // Calculate the time in nanoseconds

            for (int i = 0; i < n; i++) { // Print the sorted array
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
