package programmers.lv3;

import java.util.Arrays;

public class p42884 {
    public int solution(int[][] routes) {
        Arrays.sort(routes, (r1, r2) -> r1[0] - r2[0]);
        int camera = 1;
        int[] current = routes[0];

        for (int i = 1; i < routes.length; i++) {
            if (routes[i][0] <= current[1]) {
                if (routes[i][1] < current[1]) {
                    current[1] = routes[i][1];
                }
            } else {
                camera++;
                current = routes[i];
            }
        }

        return camera;
    }

    public static void main(String[] args) {
        int[][] routes = {{1, 5}, {2, 4}, {2, 6}, {5, 8}, {7, 10}, {11, 15}, {14, 17}};
        System.out.println(new p42884().solution(routes));
    }
}
