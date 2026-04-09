package programmers.lv3;

public class p49191 {

    private int dfsForward(int a, boolean[][] graph, boolean[] visited) {
        int count = 1;

        for (int i = 0; i < graph.length; i++) {
            if (!visited[i] && graph[a][i]) {
                visited[i] = true;
                count += dfsForward(i, graph, visited);
            }
        }

        return count;
    }

    private int dfsBackward(int a, boolean[][] graph, boolean[] visited) {
        int count = 1;

        for (int i = 0; i < graph.length; i++) {
            if (!visited[i] && graph[i][a]) {
                visited[i] = true;
                count += dfsBackward(i, graph, visited);
            }
        }

        return count;
    }

    public int solution(int n, int[][] results) {
        int answer = 0;

        boolean[][] graph = new boolean[n][n];
        for (int[] result : results) {
            int x = result[0] - 1;
            int y = result[1] - 1;
            graph[x][y] = true;
        }

        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];
            int winCount = dfsForward(i, graph, visited) - 1;
            int loseCount = dfsBackward(i, graph, visited) - 1;
            if (winCount + loseCount == n - 1) answer++;
            System.out.println(i + ": " + winCount + "/" + loseCount);
        }

        return answer;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] results = {{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}};
        System.out.println(new p49191().solution(n, results));
    }
}
