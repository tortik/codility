package com.project.euler;


import java.util.*;

public class SolutionMatrix {

    public int solution(int[][] A) {
        int counter = 0;
        Set<Position> visitedCoordinates = new HashSet<>();
        for (int row = 0; row < A.length; row++) {
            for (int col = 0; col < A[row].length; col++) {
                if (!visitedCoordinates.contains(new Position(row, col))) {
                    counter++;
                    rec(A, row, col, A[row][col], visitedCoordinates);
                }
            }
        }
        return counter;
    }


    private void rec(int[][] A, int row, int column, int expected, Set<Position> positions) {
        if (row < 0 || row >= A.length || column < 0 || column >= A[row].length) {
            return;
        }
        Position p = new Position(row, column);
        if (expected == A[row][column] && !positions.contains(p)) {
            positions.add(p);
        } else {
            return;
        }
        rec(A, row - 1, column, A[row][column], positions);
        rec(A, row + 1, column, A[row][column], positions);
        rec(A, row, column - 1, A[row][column], positions);
        rec(A, row, column + 1, A[row][column], positions);
    }

    class Position {
        int row;
        int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(row, position.row) &&
                    Objects.equals(column, position.column);
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) {
        SolutionMatrix s = new SolutionMatrix();
        int[][] A = new int[7][3];
        A[0][0] = 5;
        A[1][0] = 4;
        A[2][0] = 3;
        A[3][0] = 2;
        A[4][0] = 3;
        A[5][0] = 1;
        A[6][0] = 4;
        A[1][1] = 3;
        A[0][1] = 4;
        A[2][1] = 2;
        A[3][1] = 2;
        A[4][1] = 3;
        A[5][1] = 4;
        A[6][1] = 1;
        A[1][2] = 4;
        A[0][2] = 4;
        A[2][2] = 4;
        A[3][2] = 2;
        A[4][2] = 4;
        A[5][2] = 4;
        A[6][2] = 1;
        System.out.println(s.solution(A));
    }
}
