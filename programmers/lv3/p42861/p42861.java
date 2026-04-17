package programmers.lv3.p42861;

import java.util.Arrays;

public class p42861 {
    private int[] parent;

    private int find(int a) {
        if (parent[a] == a)
            return a;

        return parent[a] = find(parent[a]);
    }

    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB)
            parent[rootA] = rootB;
    }

    public int solution(int n, int[][] costs) {
        int answer = 0;

        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        Arrays.sort(costs, (c1, c2) -> c1[2] - c2[2]);
        int edgeCount = 0;

        for (int[] cost : costs) {
            if (find(cost[0]) != find(cost[1])){
                union(cost[0], cost[1]);
                answer += cost[2];
                edgeCount++;

                if (edgeCount == n - 1)
                    break;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] costs = {{0,1,1},{0,3,2},{0,4,3},{1,2,2},{1,3,2},{2,3,1},{3,4,3}};
        System.out.println(new p42861().solution(n, costs));
    }
}
