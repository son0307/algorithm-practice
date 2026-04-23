package codetree.backTracking03.chooseMOutOfNPoints;

import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int min = Integer.MAX_VALUE;
    static List<Point> points = new ArrayList<>();
    static List<Point> selected = new ArrayList<>();

    static class Point{
        int x, y;

        Point(int y, int x) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int py = Integer.parseInt(st.nextToken());
            int px = Integer.parseInt(st.nextToken());
            points.add(new Point(py, px));
        }

        selectM(0, 0, 0);

        System.out.println(min);
    }

    static void selectM(int cnt, int idx, int currentMax) {
        // 이미 현재 거리가 전역 최솟값 이상이라면 더 이상 탐색할 필요 없음
        if (currentMax >= min) return;

        // m개를 골랐으면 최솟값 확인 후 갱신
        if (cnt == m) {
            min = Math.min(min, currentMax);
            return;
        }

        for (int i = idx; i < n; i++) {
            Point p1 = points.get(i);
            int newMax = currentMax;

            // 새로 고른 점과 기존 점들과의 거리 계산
            for (Point p2 : selected) {
                int dx = p1.x - p2.x;
                int dy = p1.y - p2.y;
                int dist = dx * dx + dy * dy;
                newMax = Math.max(newMax, dist);
            }

            // 새로운 점을 선택하고 다음으로 이동
            selected.add(p1);
            selectM(cnt + 1, i + 1, newMax);
            selected.remove(selected.size() - 1);
        }
    }
}
