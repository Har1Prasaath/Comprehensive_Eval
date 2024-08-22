package compeval;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RadixSort {
    // Node structure for linked list
    static class Node {
        int data;
        Node next;

        Node(int value) {
            this.data = value;
            this.next = null;
        }
    }

    // Radix Sort implementation using linked list
    public static class RadixSortLinkedList {

        // Utility function to insert a new node at the end of the list
        public static void insertNode(Node[] headRef, int value) {
            Node newNode = new Node(value);
            if (headRef[0] == null) {
                headRef[0] = newNode;
            } else {
                Node temp = headRef[0];
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
        }

        // Utility function to print the linked list
        public static void printList(Node head) {
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            System.out.println();
        }

        // Utility function to get the maximum value in the list
        public static int getMax(Node head, int[] basic_operations) {
            int max = head.data;
            basic_operations[0]++; // For initial assignment
            Node temp = head;
            while (temp != null) {
                if (temp.data > max) {
                    max = temp.data;
                }
                temp = temp.next;
                basic_operations[0] += 3; // For comparison, assignment, and pointer traversal
            }
            return max;
        }

        // Utility function to count sort the linked list based on a specific digit
        public static void countSort(Node[] headRef, int exp, int[] comparisons, int[] swaps, int[] basic_operations, int[] memory_usage) {
            Node[] output = new Node[10]; // Output list array
            int[] count = new int[10]; // Count array
            basic_operations[0] += 20; // For initializing output array (10 assignments) and count array (10 assignments)
            memory_usage[0] += (output.length * 4) + (count.length * 4); // Track memory usage for arrays

            Node temp = headRef[0];
            while (temp != null) {
                int index = (temp.data / exp) % 10;
                Node[] newHead = new Node[1];
                insertNode(newHead, temp.data);
                output[index] = merge(output[index], newHead[0]);
                count[index]++;
                basic_operations[0] += 4; // For the arithmetic operations and index calculation
                temp = temp.next;
            }

            // Reconstruct the linked list
            headRef[0] = null;
            for (int i = 0; i < 10; i++) {
                while (output[i] != null) {
                    insertNode(headRef, output[i].data);
                    Node toDelete = output[i];
                    output[i] = output[i].next;
                    toDelete = null; // No explicit delete needed, Java handles garbage collection
                    swaps[0]++; // Increment for each node moved
                    basic_operations[0] += 3; // For node deletion and pointer updates
                }
            }
        }

        // Utility function to merge two linked lists
        public static Node merge(Node a, Node b) {
            if (a == null) return b;
            if (b == null) return a;
            Node temp = a;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = b;
            return a;
        }

        // Function to perform radix sort on the linked list
        public static void radixSort(Node[] headRef, int[] comparisons, int[] swaps, int[] basic_operations, int[] memory_usage) {
            int max = getMax(headRef[0], basic_operations);
            memory_usage[0] += 4; // Track memory usage for max variable

            for (int exp = 1; max / exp > 0; exp *= 10) {
                basic_operations[0]++; // For the exp initialization
                countSort(headRef, exp, comparisons, swaps, basic_operations, memory_usage);
                basic_operations[0]++; // For exp *= 10
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        try {
            File inputFile = new File("input1000.txt");
            Scanner scanner = new Scanner(inputFile);

            int n = scanner.nextInt();
            Node[] headRef = new Node[1]; // Using an array to pass by reference

            int memory_usage = (4 * 3); // Initial memory usage for n, value, and headRef

            for (int i = 0; i < n; i++) {
                int value = scanner.nextInt();
                RadixSortLinkedList.insertNode(headRef, value);
                memory_usage += (4 * 2); // Memory usage for each node (4 bytes for int data, 4 bytes for next pointer)
            }

            scanner.close();

            int[] comparisons = {0}; // Initialize comparison counter
            int[] swaps = {0}; // Initialize swap counter
            int[] basic_operations = {0}; // Initialize basic operations counter
            int[] memory_usageArr = {memory_usage}; // Array for memory usage to be updated

            long startTime = System.nanoTime(); // Start time measurement
            RadixSortLinkedList.radixSort(headRef, comparisons, swaps, basic_operations, memory_usageArr);
            long endTime = System.nanoTime(); // End time measurement
            long executionTime = endTime - startTime; // Calculate execution time

            RadixSortLinkedList.printList(headRef[0]);

            // Print the performance metrics
            System.out.println("Number of Comparisons: " + comparisons[0]);
            System.out.println("Number of Swaps: " + swaps[0]);
            System.out.println("Number of Basic Operations: " + basic_operations[0]);
            System.out.println("Memory Usage: " + memory_usageArr[0] + " bytes");
            System.out.println("Execution Time: " + executionTime + " ns");

            // Cleanup
            Node temp;
            while (headRef[0] != null) {
                temp = headRef[0];
                headRef[0] = headRef[0].next;
                temp = null; // Java handles garbage collection
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open the file.");
        }
    }
}
