package programmers.lv2.p12978;

import java.util.*;

class Node implements Comparable<Node> {
    int num;
    int distance;

    Node(int num, int distance) {
        this.num = num;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        return this.distance - o.distance;
    }
}

public class p12978_2 {
    static LinkedList<Node>[] graph;
    static int[] distance;

    static void dijkstra(int N) {
        boolean[] visited = new boolean[N+1];
        int start = 1;

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0));

        while(!queue.isEmpty()) {
            Node node = queue.poll();
            if(!visited[node.num]) {
                visited[node.num] = true;

                for(Node n : graph[node.num]) {
                    if(!visited[n.num] && distance[node.num] + n.distance < distance[n.num]) {
                        distance[n.num] = distance[node.num] + n.distance;
                        queue.add(new Node(n.num, distance[n.num]));
                    }
                }
            }
        }
    }

    public int solution(int N, int[][] roads, int K){
        graph = new LinkedList[N+1];
        distance = new int[N+1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new LinkedList<Node>();
        }

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int w = road[2];
            graph[u].add(new Node(v, w));
            graph[v].add(new Node(u, w));
        }

        dijkstra(N);

        int answer = 0;
        for (int i = 1; i <= N; i++) {
            if (distance[i] <= K) {
                System.out.println("i = " + i);
                answer++;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        int N = 5;
        int K = 3;
        int[][] road = {
                {1,2,1},
                {2,3,3},
                {5,2,2},
                {1,4,2},
                {5,3,1},
                {5,4,2}
        };

        p12978_2 p = new p12978_2();
        System.out.println("p = " + p.solution(N, road, K));
    }
}
