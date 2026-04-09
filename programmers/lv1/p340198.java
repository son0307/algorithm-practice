package programmers.lv1;

import java.util.Arrays;

public class p340198 {
    int getMaxMat(int[] mats, int x, int y, String[][] park) {
        int max = 0;

        for (int mat : mats) {
            for (int i = 0; i < mat; i++) {
                for (int j = 0; j < mat; j++) {
                    if (y + i >= park.length || x + j >= park[0].length || !park[y + i][x + j].equals("-1"))
                        return max;
                }
            }

            max = Math.max(max, mat);
        }

        return max;
    }

    public int solution(int[] mats, String[][] park) {
        int answer = 0;
        Arrays.sort(mats);

        for (int y = 0; y < park.length; y++) {
            for (int x = 0; x < park[0].length; x++) {
                if (park[y][x].equals("-1")) {
                    answer = Math.max(getMaxMat(mats, x, y, park), answer);
                }
            }
        }

        if (answer == 0)
            return -1;

        return answer;
    }

    public static void main(String[] args) {
        int[] mats = {5,3,2};
        String[][] park = {{"A", "A", "-1", "B", "B", "B", "B", "-1"}, {"A", "A", "-1", "B", "B", "B", "B", "-1"}, {"-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"}, {"D", "D", "-1", "-1", "-1", "-1", "E", "-1"}, {"D", "D", "-1", "-1", "-1", "-1", "-1", "F"}, {"D", "D", "-1", "-1", "-1", "-1", "E", "-1"}};

        System.out.println(new p340198().solution(mats, park));
    }
}
