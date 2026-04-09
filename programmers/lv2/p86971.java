package programmers.lv2;

import java.util.ArrayList;

public class p86971 {
    ArrayList<Integer>[] adjList;
    boolean[] visited;

    public int solution(int n, int[][] wires) {
        int minDiff = Integer.MAX_VALUE;

        adjList = new ArrayList[n+1];
        for (int i = 0; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] wire : wires) {
            adjList[wire[0]].add(wire[1]);
            adjList[wire[1]].add(wire[0]);
        }

        for (int[] wire : wires) {
            int v1 = wire[0];
            int v2 = wire[1];

            visited = new boolean[n+1];
            int subTree1Size = dfs(v1, v2);
            int subTree2Size = n - subTree1Size;

            minDiff = Math.min(minDiff, Math.abs(subTree1Size - subTree2Size));
        }

        return minDiff;
    }

    public int dfs(int curNode, int blockedNode) {
        visited[curNode] = true;
        int cur = 1;

        for (int adjNode : adjList[curNode]) {
            if (adjNode != blockedNode && !visited[adjNode]) {
                cur += dfs(adjNode, blockedNode);
            }
        }

        return cur;
    }

    public static void main(String[] args) {
        p86971 p = new p86971();
        int n1 = 9;
        int[][] wires1 = new int[][]{
                {1,3},{2,3},{3,4},{4,5},{4,6},{4,7},{7,8},{7,9}
        };

        int n2 = 4;
        int[][] wires2 = new int[][]{
                {1,2},{2,3},{3,4}
        };

        int n3 = 7;
        int[][] wires3 = new int[][]{
                {1,2},{2,7},{3,7},{3,4},{4,5},{6,7}
        };
        System.out.println("p = " + p.solution(n1, wires1));
        System.out.println("----------------------------------------");
        System.out.println("p = " + p.solution(n2, wires2));
        System.out.println("----------------------------------------");
        System.out.println("p = " + p.solution(n3, wires3));
    }
}
