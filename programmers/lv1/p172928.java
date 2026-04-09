package programmers.lv1;

import java.util.Arrays;

public class p172928 {
    public int[] solution(String[] park, String[] routes) {
        int h = park.length;
        int w = park[0].length();

        int y = 0, x = 0;

        for (int i = 0; i < h; i++) {
            if (park[i].contains("S")) {
                y = i;
                x = park[i].indexOf("S");
                break;
            }
        }

        for (String route : routes) {
            String[] parts = route.split(" ");
            String dir = parts[0];
            int dist = Integer.parseInt(parts[1]);

            int dy = 0, dx = 0;
            switch (dir) {
                case "N": dy = -1; break;
                case "S": dy = 1; break;
                case "W": dx = -1; break;
                case "E": dx = 1; break;
            }

            int ny = y;
            int nx = x;
            boolean isPossible = true;

            for (int i = 0; i < dist; i++) {
                ny += dy;
                nx += dx;

                if (ny < 0 || ny >= h || nx < 0 || nx >= w || park[ny].charAt(nx) == 'X') {
                    isPossible = false;
                    break;
                }
            }


            if (isPossible) {
                y = ny;
                x = nx;
            }
        }

        return new int[]{y, x};
    }

    public static void main(String[] args) {
        String[] park = {"SOO","OOO","OOO"};
        String[] routes = {"E 2","S 2","W 1"};
        System.out.println(Arrays.toString(new p172928().solution(park, routes)));
    }
}
