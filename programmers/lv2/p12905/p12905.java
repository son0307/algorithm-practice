package programmers.lv2.p12905;

public class p12905 {
    public int solution(int[][] board)
    {
        int max = 0;
        for(int x = 0; x < board[0].length; x++)
            if (board[0][x] == 1) {
                max = 1;
                break;
            }
        for (int[] ints : board)
            if (ints[0] == 1) {
                max = 1;
                break;
            }

        for(int y = 1; y < board.length; y++) {
            for(int x = 1; x < board[0].length; x++) {
                if (board[y][x] == 1) {
                    board[y][x] = Math.min(board[y][x - 1], Math.min(board[y - 1][x], board[y - 1][x - 1])) + 1;
                    max = Math.max(max, board[y][x]);
                }
            }
        }

        return max * max;
    }
}
