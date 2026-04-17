package programmers.lv3.p49189;

import java.util.*;

public class p49189 {
    public int solution(int n, int[][] edge) {
        int maxDistance = 0;

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edge) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        int[] distance = new int[n+1];
        boolean[] visited = new boolean[n+1];

        Queue<Integer> q = new ArrayDeque<>();

        q.add(1);
        visited[1] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : graph.get(cur)) {
                if (!visited[next]) {
                    visited[next] = true;
                    distance[next] = distance[cur] + 1;
                    q.add(next);

                    maxDistance = Math.max(maxDistance, distance[next]);
                }
            }
        }

        int answer = 0;
        for (int d: distance)
            if (d == maxDistance)
                answer++;

        return answer;
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] edge = {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}};
        System.out.print(new p49189().solution(n, edge));
    }
}
