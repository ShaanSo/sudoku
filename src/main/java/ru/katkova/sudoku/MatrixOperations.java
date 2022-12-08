package ru.katkova.sudoku;

import java.util.Random;

public class MatrixOperations {

    public static void printArray(int[][] a) {
        for (int i = 0; i < 9; i ++) {
            System.out.println(a[i][0] + " " + a[i][1] + " " + a[i][2] + " | " + a[i][3] + " " + a[i][4] + " " + a[i][5] +
                    " | " + a[i][6] + " " + a[i][7] + " " + a[i][8]);
            if ((i + 1) %3==0 && i!=8) {
                System.out.println("---------------------");
            }
        }
    }

    public static int[][] transpose(int[][] matrix) {
        int[][] transMatrix = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = i; j < 9; j++) {
                transMatrix[i][j] = matrix[j][i];
                transMatrix[j][i] = matrix[i][j];
            }
        }
        return transMatrix;
    }

    public static int[][] swapRandomLinesV(int[][] a) {
        int min = 0;
        int max = 2;
        Random random = new Random();
        int randomRegion = random.nextInt(max - min + 1) + min;
        int randomLine = randomRegion * 3 + random.nextInt(max - min + 1) + min;
        int lineToswap = randomRegion * 3 + random.nextInt(max - min + 1) + min;
        for (int i = 0; i < 9; i++) {
            int temp = a[i][randomLine];
            a[i][randomLine] = a[i][lineToswap];
            a[i][lineToswap] = temp;
        }
        return a;
    }

    public static int[][] swapRandomLinesH(int[][] a) {
        int min = 0;
        int max = 2;
        Random random = new Random();
        int randomRegion = random.nextInt(max - min + 1) + min;
        int randomLine = randomRegion * 3 + random.nextInt(max - min + 1) + min;
        int lineToswap = randomRegion * 3 + random.nextInt(max - min + 1) + min;
        for (int i = 0; i < 9; i++) {
            int temp = a[randomLine][i];
            a[randomLine][i] = a[lineToswap][i];
            a[lineToswap][i] = temp;
        }
        return a;
    }


    public static int[][] swapRegionV(int[][] a) {
        int min = 0;
        int max = 2;
        Random random = new Random();
        int randomRegion = random.nextInt(max - min + 1) + min;
        int randomRegionToSwap = random.nextInt(max - min + 1) + min;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                int temp = a[i][randomRegion * 3 + j];
                a[i][randomRegion * 3 + j] = a[i][randomRegionToSwap * 3 + j];
                a[i][randomRegionToSwap * 3 + j] = temp;
            }
        }
        return a;
    }

    public static int[][] swapRegionH(int[][] a) {
        int min = 0;
        int max = 2;
        Random random = new Random();
        int randomRegion = random.nextInt(max - min + 1) + min;
        int randomRegionToSwap = random.nextInt(max - min + 1) + min;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                int temp = a[randomRegion * 3 + j][i];
                a[randomRegion * 3 + j][i] = a[randomRegionToSwap * 3 + j][i];
                a[randomRegionToSwap * 3 + j][i] = temp;
            }
        }
        return a;    }

    public static int[][] swapNumbers(int[][] a) {
        int min = 1;
        int max = 9;
        Random random = new Random();
        int numberOne = random.nextInt(max - min + 1) + min;
        int numberTwo = random.nextInt(max - min + 1) + min;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (a[i][j] == numberOne) {
                    a[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (a[i][j] == numberTwo) {
                    a[i][j] = numberOne;
                } else if (a[i][j] == 0) {
                    a[i][j] = numberTwo;
                }
            }
        }
        return a;
    }
}
