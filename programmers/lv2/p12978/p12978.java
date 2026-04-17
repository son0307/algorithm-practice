package programmers.lv2.p12978;

import java.util.*;

public class p12978 {
    HashSet<Integer> set = new HashSet<>();

    static class Node {
        int num;
        int distance;

        Node(int num, int distance) {
            this.num = num;
            this.distance = distance;
        }
    }

    public int solution(int N, int[][] roads, int K) {
        boolean[] visited = new boolean[N+1];
        List<List<Node>> map = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) {
            map.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            map.get(road[0]).add(new Node(road[1], road[2]));
            map.get(road[1]).add(new Node(road[0], road[2]));
        }

        dfs(map, visited, 1, 0, K);

        System.out.println("set = " + set);
        return set.size();
    }

    public void dfs(List<List<Node>> map, boolean[] visited, int curNum, int curDistance, int maxDistance) {
        if (visited[curNum]) return;

        visited[curNum] = true;
        set.add(curNum);
        List<Node> adjacentNodes = map.get(curNum);
        for (Node node : adjacentNodes) {
            if (!visited[node.num] && curDistance + node.distance <= maxDistance) {
                dfs(map, visited, node.num, curDistance + node.distance, maxDistance);
            }
        }
        visited[curNum] = false;
    }

    public static void main(String[] args) {
        int N = 6;
        int K = 4;
        int[][] road = {
                {1,2,1},
                {1,3,2},
                {2,3,2},
                {3,4,3},
                {3,5,2},
                {3,5,3},
                {5,6,1}
        };

        p12978 p = new p12978();
        System.out.println("p = " + p.solution(N, road, K));
    }
}
