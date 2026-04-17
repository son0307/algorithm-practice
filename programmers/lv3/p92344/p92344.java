package programmers.lv3.p92344;

import java.util.Arrays;

public class p92344 {
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;

        int[][] sumBoard = new int[board.length + 1][board[0].length + 1];
        for (int[] skillRow : skill) {
            int dmg;
            if (skillRow[0] == 1) dmg = skillRow[5];
            else dmg = -skillRow[5];

            sumBoard[skillRow[1]][skillRow[2]] += dmg;
            sumBoard[skillRow[3] + 1][skillRow[4] + 1] += dmg;
            sumBoard[skillRow[3] + 1][skillRow[2]] -= dmg;
            sumBoard[skillRow[1]][skillRow[4] + 1] -= dmg;
        }

        for (int row = 0; row < sumBoard.length; row++) {
            for (int col = 0; col < sumBoard[row].length - 1; col++) {
                sumBoard[row][col + 1] += sumBoard[row][col];
            }
        }

        for (int row = 0; row < sumBoard.length - 1; row++) {
            for (int col = 0; col < sumBoard[row].length; col++) {
                sumBoard[row + 1][col] += sumBoard[row][col];
            }
        }

        System.out.println(Arrays.deepToString(sumBoard));

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] - sumBoard[row][col] >= 1)
                    answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] board = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] skill = {{1,1,1,2,2,4},{1,0,0,1,1,2},{2,2,0,2,0,100}};
        System.out.println(new p92344().solution(board, skill));
    }
}
