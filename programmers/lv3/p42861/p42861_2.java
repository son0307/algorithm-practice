package programmers.lv3.p42861;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class p42861_2 {
    static class Edge {
        int node;
        int cost;

        public Edge(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }

    public int solution(int n, int[][] costs) {
        int answer = 0;
        int currentNodes = 0;

        List<List<Edge>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int[] cost : costs) {
            edges.get(cost[0]).add(new Edge(cost[1], cost[2]));
            edges.get(cost[1]).add(new Edge(cost[0], cost[2]));
        }

        boolean[] visited = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));

        pq.offer(new Edge(0, 0));

        while(!pq.isEmpty()) {
            Edge current = pq.poll();

            if (!visited[current.node]) {
                visited[current.node] = true;
                answer += current.cost;
                currentNodes++;

                if (currentNodes == n) {
                    break;
                }

                for (Edge next : edges.get(current.node)) {
                    if (!visited[next.node]) {
                        pq.offer(next);
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] costs = {{0,1,1},{0,3,2},{0,4,3},{1,2,2},{1,3,2},{2,3,1},{3,4,3}};
        System.out.println(new p42861_2().solution(n, costs));
    }
}
