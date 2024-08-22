package compeval;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class BucketSort {
	// Function to perform insertion sort on a bucket
    public static void insertionSort(float[] bucket, int size, int[] comparisons, int[] basic_operations) {
        for (int i = 1; i < size; ++i) {
            float key = bucket[i];
            int j = i - 1;
            basic_operations[0]++; // Increment for the initialization of key and j

            while (j >= 0 && bucket[j] > key) {
                comparisons[0]++; // Increment for the while loop comparison
                bucket[j + 1] = bucket[j];
                j--;
                basic_operations[0]++; // Increment for the assignment and decrement of j
            }
            bucket[j + 1] = key;
            basic_operations[0]++; // Increment for the final assignment of key
        }
    }

    // Function to sort an array using bucket sort
    public static void bucketSort(float[] arr, int n, int[] comparisons, int[] basic_operations, int[] memory_usage) {
        // Create n empty buckets (each bucket is an array)
        float[][] buckets = new float[n][n]; // Each bucket can hold up to n elements
        int[] bucketSizes = new int[n]; // Array to keep track of the number of elements in each bucket

        memory_usage[0] += (buckets.length * buckets[0].length * Float.BYTES) + (bucketSizes.length * Integer.BYTES); // Memory used by buckets and bucketSizes

        // Initialize bucket sizes to 0
        for (int i = 0; i < n; i++) {
            bucketSizes[i] = 0;
            basic_operations[0]++; // Increment for each initialization
        }

        // Put array elements into different buckets
        for (int i = 0; i < n; i++) {
            int bi = (int) (n * arr[i]);
            if (bi >= n) { // Ensure index is within bounds
                bi = n - 1;
            }
            buckets[bi][bucketSizes[bi]] = arr[i]; // Insert element into the bucket
            bucketSizes[bi]++; // Increment the size of the corresponding bucket
            basic_operations[0] += 3; // Increment for multiplication, assignment, and increment operations
        }

        // Sort individual buckets using insertion sort
        for (int i = 0; i < n; i++) {
            if (bucketSizes[i] > 0) {
                insertionSort(buckets[i], bucketSizes[i], comparisons, basic_operations);
            }
        }

        // Concatenate all buckets into the original array
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < bucketSizes[i]; j++) {
                arr[index++] = buckets[i][j];
                basic_operations[0]++; // Increment for each assignment
            }
        }
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("input1000.txt"); // Create a File object to read from a file.
            Scanner scanner = new Scanner(inputFile);

            int n = scanner.nextInt(); // Read the total number of elements from the file

            float[] arr = new float[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextFloat(); // Read each element from the file
            }
            scanner.close(); // Close the file

            int[] comparisons = {0};        // To count the number of comparisons
            int[] basic_operations = {0};    // To count the number of basic operations (excluding comparisons and swaps)
            int[] memory_usage = {arr.length * Float.BYTES}; // Memory used by the main array

            long startTime = System.nanoTime(); // Start time measurement
            bucketSort(arr, n, comparisons, basic_operations, memory_usage); // Sort the array
            long endTime = System.nanoTime(); // End time measurement
            long executionTime = endTime - startTime; // Calculate execution time

            for (int i = 0; i < n; i++) { // Print the sorted array
                System.out.print(arr[i] + " ");
            }
            System.out.println();

            // Print the number of comparisons, basic operations, memory usage, and execution time
            System.out.println("Number of Comparisons: " + comparisons[0]);
            System.out.println("Number of Swaps: N/A (No swaps in Bucket Sort)");
            System.out.println("Number of Basic Operations: " + basic_operations[0]);
            System.out.println("Memory Usage: " + memory_usage[0] + " bytes");
            System.out.println("Execution Time: " + executionTime + " ns");

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open the file.");
        }
    }
}
