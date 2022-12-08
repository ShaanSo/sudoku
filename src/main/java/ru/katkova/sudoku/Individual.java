package ru.katkova.sudoku;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Individual implements Comparable<Individual>{
    private int[][] field;
    private int score;

    @Override
    public int compareTo(Individual o) {
        return Integer.compare(this.score, o.score);
    }

    public int countMistake() {
        MatrixOperations matrixOperations = new MatrixOperations();
        int mistakeCount = 0;
        int[][] transA = MatrixOperations.transpose(this.field);
        for (int i = 0; i < 9; i++) {
            int lineA[] = this.field[i];
            int lineTransA[] = transA[i];
            for (int j = 1; j <= 9; j++) {
                int finalJ = j;
                if (Arrays.stream(lineA).filter(l -> l == finalJ).count() == 0) {
                    mistakeCount++;
                }
                if (Arrays.stream(lineTransA).filter(l -> l == finalJ).count() == 0) {
                    mistakeCount++;
                }
            }
        }
        return mistakeCount;
    }
}
