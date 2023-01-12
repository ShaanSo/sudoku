package ru.katkova.sudoku;

import org.junit.Assert;
import org.junit.Test;

public class IndividualTest {

    @Test
    public void countMistakeTest() {
        Individual individual = new Individual();
        int[][] matrix = new int[][] {{3,1,2,4,5,6,8,9,7},{5,4,6,3,7,9,1,2,3},{7,8,9,1,8,2,4,6,5},{4,2,3,6,8,5,9,7,1},{1,9,7,2,4,3,6,5,8},{8,6,5,9,1,7,2,3,4},{2,7,1,3,6,4,5,8,9},{9,5,8,7,2,1,3,4,6},{6,3,4,5,9,8,7,1,2}};
        individual.setField(matrix);
        int mistakeCount = individual.countMistake();
        Assert.assertEquals(mistakeCount, 4);
    }

}
