package programmers.lv2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class p340211 {
    public int solution(int[][] points, int[][] routes) {
        int answer = 0;

        Map<Integer, int[][]> time_map = new HashMap<>();
        for (int[] route : routes) {
            int time = 0;
            int[] current = Arrays.copyOf(points[route[0] - 1], 2);
            if (!time_map.containsKey(time)) time_map.put(time, new int[101][101]);
            time_map.get(time)[current[0]][current[1]]++;

            for (int i = 1; i < route.length; i++) {
                int[] start = Arrays.copyOf(points[route[i-1] - 1], 2);
                int[] end = Arrays.copyOf(points[route[i] - 1], 2);
                System.out.println("start : " + Arrays.toString(start) + ", end : " + Arrays.toString(end));

                while (start[0] != end[0]) {
                    time++;
                    if (start[0] > end[0])
                        start[0]--;
                    else
                        start[0]++;

                    System.out.println(time + " " + start[0] + " " + start[1]);
                    if (!time_map.containsKey(time))
                        time_map.put(time, new int[101][101]);
                    time_map.get(time)[start[0]][start[1]]++;
                }

                while (start[1] != end[1]) {
                    time++;
                    if (start[1] > end[1])
                        start[1]--;
                    else
                        start[1]++;

                    System.out.println(time + " " + start[0] + " " + start[1]);
                    if (!time_map.containsKey(time))
                        time_map.put(time, new int[101][101]);
                    time_map.get(time)[start[0]][start[1]]++;
                }
            }
        }

        for (int key : time_map.keySet()) {
            int[][] map = time_map.get(key);

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {
                    if (map[i][j] > 1) {
                        answer++;
                        System.out.println("time : " + key + " i : " + i + ", j : " + j);
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] points = {{2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}};
        int[][] routes = {{2,3,4,5},{1,3,4,5}};
        System.out.println(new p340211().solution(points, routes));
    }
}