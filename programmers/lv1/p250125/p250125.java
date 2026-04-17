package programmers.lv1.p250125;

public class p250125 {
    public int solution(String[][] board, int h, int w) {
        int answer = 0;
        int n = board.length;
        String color = board[h][w];

        int[] dh = {0, 0, 1, -1};
        int[] dw = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int h_check = h + dh[i];
            int w_check = w + dw[i];
            if (h_check >= 0 && h_check < n && w_check >= 0 && w_check < n) {
                if (board[h_check][w_check].equals(color)) {
                    answer++;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        p250125 p = new p250125();
        String[][] board = {
                {"blue", "red", "orange", "red"},
                {"red", "red", "blue", "orange"},
                {"blue", "orange", "red", "red"},
                {"orange", "orange", "red", "blue"}};
        int h = 1;
        int w = 1;
        System.out.println(p.solution(board, h, w));
    }
}
