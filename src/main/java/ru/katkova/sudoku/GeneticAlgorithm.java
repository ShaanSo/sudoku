package ru.katkova.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {

    public boolean calculate(int[][] matrix, int[][] mask) {

        //создание новой популяции
        int populationSize = 3000;
        ArrayList<Individual> population = initPopulation(mask, matrix, populationSize);

        int iterationCount = 1000;
        int iteration = 1;
        int parentPoolSize = Math.floorDiv(populationSize, 100);

        while (iteration <= iterationCount) {

            ArrayList<Individual> parentPool = parentSelection(populationSize, parentPoolSize, population);
            ArrayList<Individual> childrenPool = crossover(populationSize, parentPoolSize, parentPool, mask);
            ArrayList<Individual> finalChildrenPool = mutation(populationSize, childrenPool, mask);

            Collections.sort(finalChildrenPool);
            if (finalChildrenPool.get(0).getScore() == 0) {
                System.out.println(iteration);
                return true;
            }
            iteration++;
        }
        System.out.println("no solution found");
        return false;
    }

    public static ArrayList<Individual> initPopulation(int[][] mask, int[][] solvedMatrix, int populationSize) {
        ArrayList<Individual> population = new ArrayList<>();
        int k = 1;
        while (k <= populationSize) {
            Individual ind = new Individual();
            Random random = new Random();
            int[][] field = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mask[i][j] == 0) field[i][j] = random.nextInt(9) + 1;
                    else field[i][j] = solvedMatrix[i][j];
                }
            }
            ind.setField(field);
            ind.setScore(ind.countMistake());
            population.add(ind);
            k++;
        }
        return population;
    }

    public static ArrayList<Individual> parentSelection(int populationSize, int parentPoolSize, ArrayList<Individual> population) {
        ArrayList<Individual> parentPool = new ArrayList<>();
        int k = 1;
        while (k <= populationSize) {
            Random random = new Random();
            int index1 = random.nextInt(populationSize);
            int index2 = random.nextInt(populationSize);
            if (population.get(index1).getScore() > population.get(index2).getScore()) {
                parentPool.add(population.get(index2));
            } else parentPool.add(population.get(index1));
            k++;
        }
        return parentPool;
    }

    private static ArrayList<Individual> crossover(int populationSize, int parentPoolSize, ArrayList<Individual> parentPool, int[][] mask) {
        ArrayList<Individual> childPopulation = new ArrayList<>();
        int k = 1;
        while (k <= populationSize) {
            Random random = new Random();
            int index1 = random.nextInt(parentPoolSize);
            int index2 = random.nextInt(parentPoolSize);

            Individual parent1 = parentPool.get(index1);
            Individual parent2 = parentPool.get(index2);

            Individual child = new Individual();

            int[][] field1 = parent1.getField();
            int[][] field2 = parent2.getField();
            int[][] field = parent1.getField();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mask[i][j] == 0) {
                        if (random.nextInt(2) == 0) field[i][j] = field1[i][j];
                        else field[i][j] = field2[i][j];
                    }
                }
            }
            child.setField(field);
            child.setScore(child.countMistake());
            childPopulation.add(child);
            k++;
        }
        return childPopulation;
    }

    public static ArrayList<Individual> mutation(int populationSize, ArrayList<Individual> childrenPool, int[][] mask) {
        int mutationProbability = 100; // p = 1/mutationProbability;
        int k = 0;
        while (k < populationSize) {
            Random random = new Random();
            int[][] field = childrenPool.get(k).getField();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mask[i][j] == 0) {
                        if (random.nextInt(mutationProbability) == 0) field[i][j] = random.nextInt(9);
                    }
                }
            }
            childrenPool.get(k).setField(field);
            childrenPool.get(k).setScore(childrenPool.get(k).countMistake());
            k++;
        }
        return childrenPool;
    }
}
