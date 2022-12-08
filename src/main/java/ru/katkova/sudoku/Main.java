package ru.katkova.sudoku;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[][] matrix = initBaseArray();
        int min = 0;
        int max = 1000;
        Random random = new Random();
        int randomIter = random.nextInt(max - min + 1) + min;

        for (int i = 0; i < randomIter; i++) {
            matrix = MatrixOperations.transpose(matrix);
            MatrixOperations.swapNumbers(matrix);
            MatrixOperations.swapRegionH(matrix);
            MatrixOperations.swapRegionV(matrix);
            MatrixOperations.swapRandomLinesH(matrix);
            MatrixOperations.swapRandomLinesV(matrix);
        }
        System.out.println(randomIter);
        System.out.println("-------------------------------------------------");
        MatrixOperations.printArray(matrix);
        System.out.println("-------------------------------------------------");

        int[][] mask = new int[9][9];
        int maxEmptySells = 30;
        int emptySells = 1;
        //заполняем маску
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mask[i][j] = 1;
            }
        }

        while (emptySells <= maxEmptySells) {
            //удаляем произвольную еще не нулевую клетку из маски
            int content = 0;
            while (content == 0) {
                int index1 = random.nextInt(9);
                int index2 = random.nextInt(9);
                content = mask[index1][index2];
                mask[index1][index2] = 0;
            }

            //ищем решение
            int solutions = solve(matrix, mask);
            System.out.println("empty sells = " + emptySells + "; solutions = " + solutions);
            MatrixOperations.printArray(createProblem(matrix, mask));
            System.out.println("--------------------------------------------------------------");
            emptySells++;
        }
    }



    public static int[][] initBaseArray() {
        int[][] array = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int koeff1 = (i % 3) * 3;
                int koeff2 = i / 3;
                array[i][j] = j + 1 + koeff1 + koeff2;
                if (array[i][j] > 9) array[i][j] = array[i][j] - 9;
            }
        }
        return array;
    }

    public static int solve(int[][] matrix, int[][] mask) {

        int maxRestarts = 20; //рестарты для поиска нового решения
        int restart = 1;

        int solutionCount = 0;

        while (restart <= maxRestarts) {
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
            if (geneticAlgorithm.calculate(matrix, mask)) {
                //сравниваем решение с предыдущим
                solutionCount++;
            };
            restart++;
        }

//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//
//            }
//        }
        return solutionCount;
    }

    public static int[][] createProblem(int[][] matrix, int[][] mask) {
        int[][] problem = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(mask[i][j] == 0) {
                    problem[i][j] = 0;
                } else problem[i][j] = matrix[i][j];
            }
        }
        return problem;
    }
}
