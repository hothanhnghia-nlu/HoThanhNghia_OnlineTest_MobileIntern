package com.tyme.vietnam;

import java.util.Scanner;

public class Main {

    // Find and return the missing number
    private static int findMissingNumber(int[] array, int n) {
        int expectedSum = n * (n + 1) / 2;

        int actualSum = 0;
        for (int num : array) {
            actualSum += num;
        }
        return expectedSum - actualSum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Fill an array length
        System.out.print("Fill an array length: ");
        int n = scanner.nextInt();

        int[] arr = new int[n];

        // Fill elements and check distinct number
        System.out.println("Fill elements of array: ");
        for (int i = 0; i < n; i++) {
            boolean isUnique;
            do {
                isUnique = true;
                System.out.print("Element " + (i + 1) + ": ");
                int input = scanner.nextInt();

                for (int j = 0; j < i; j++) {
                    if (arr[j] == input) {
                        isUnique = false;
                        System.out.println("Element must be distinct from previous elements.");
                        break;
                    }
                }
                if (isUnique) {
                    arr[i] = input;
                }
            } while (!isUnique);
        }

        // Print array
        System.out.println("\nArray: ");
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                System.out.print(arr[i]);
            } else {
                System.out.print(arr[i] + ", ");
            }
        }
        System.out.println("]");

        // Find and return the missing number
        int missingNumber = Main.findMissingNumber(arr, n + 1);
        System.out.println("Missing number: " + missingNumber);
    }
}
