package ru.katkova.sudoku;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        int[][] matrix = initBaseArray();
//        базовая матрица:
//        1 2 3 | 4 5 6 | 7 8 9
//        4 5 6 | 7 8 9 | 1 2 3
//        7 8 9 | 1 2 3 | 4 5 6
//        ---------------------
//        2 3 4 | 5 6 7 | 8 9 1
//        5 6 7 | 8 9 1 | 2 3 4
//        8 9 1 | 2 3 4 | 5 6 7
//        ---------------------
//        3 4 5 | 6 7 8 | 9 1 2
//        6 7 8 | 9 1 2 | 3 4 5
//        9 1 2 | 3 4 5 | 6 7 8
        Random random = new Random();
        int randomIter = random.nextInt(1000); // произвольное количество перемешиваний базовой матрицы

        // получаем произвольную заполненную матрицу:
        for (int i = 0; i < randomIter; i++) {
            MatrixOperations.transpose(matrix);
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

        //маска = массив 9 х 9 из "0" и "1", где 0 означает пустую клетку матрицы судоку (задачи), а 1 - заполненную
        int[][] mask = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mask[i][j] = 1;
            }
        }
        int maxEmptySells = 50;
        int emptySells = 1;
        while (emptySells <= maxEmptySells) {
            //удаляем произвольную еще не нулевую клетку из маски
            int content = 0;
            while (content == 0) {
                int index1 = random.nextInt(9);
                int index2 = random.nextInt(9);
                content = mask[index1][index2];
                mask[index1][index2] = 0;
            }

            //ищем единственное решение
            int solutions = solve(matrix, mask);
            System.out.println("empty sells = " + emptySells + "; solutions = " + solutions);
            MatrixOperations.printArray(createProblem(matrix, mask));
            System.out.println("--------------------------------------------------------------");
            emptySells++;
        }
    }
    private static int[][] initBaseArray() {
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

        int solutionCount = 1;

        while (restart <= maxRestarts) {
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
            Optional<Individual> opt = geneticAlgorithm.calculate(matrix, mask);
            if (opt.isPresent()) {
                solutionCount = 1;
                Individual solution = opt.get();
                int[][] solutionField = solution.getField();
                for (int i = 0; i < 9; i++) {
                    if (Arrays.mismatch(solutionField[i], matrix[i]) != -1) {
                        System.out.println("--------------other solution-----------------------------------");
                        MatrixOperations.printArray(solutionField);
                        System.out.println("-------------------------------------------------");
                        //если решение отличается от решенной матрицы, значит решений больше 1
                        solutionCount++;
                        return solutionCount;
                    }
                }
            } else solutionCount = 0;
            restart++;
        }
        return solutionCount;
    }

    private static int[][] createProblem(int[][] matrix, int[][] mask) {
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
