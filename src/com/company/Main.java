package com.company;

public class Main {

    public static void main(String[] args) {
        int j = 0;
        int[][] board = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 0, 3, 3, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 3, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 3, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 5, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 4, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

        for (int i = 0; i < board[j].length; i++) {
            if (i == 1 && j != 0) {
                System.out.print("█");
            }
            if (board[j][i] == 0) {
                System.out.print("-");
            }
            if (board[j][i] == 1) {
                System.out.print("█");
            }
            if (board[j][i] == 2) {
                System.out.print("★");
            }
            if (board[j][i] == 3) {
                System.out.print("~");
            }
            if (board[j][i] == 4) {
                System.out.print("◉");
            }
            if (board[j][i] == 5) {
                System.out.print("ᴥ");
            }
            if (i == 9 && j == 9) {
                System.exit(0);
            }
            if (i == 9) {
                j = j + 1;
                i = 0;
                System.out.println("");
            }
        }
    }
}
