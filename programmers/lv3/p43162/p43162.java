package programmers.lv3.p43162;

public class p43162 {
    public void dfs(int i, boolean[] visited, int[][] computers) {
        visited[i] = true;

        for (int j = 0; j < computers[i].length; j++) {
            if (i != j && !visited[j] && computers[i][j] == 1)
                dfs(j, visited, computers);
        }
    }

    public int solution(int n, int[][] computers) {
        int answer = 0;

        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                answer++;
                dfs(i, visited, computers);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        p43162 p = new p43162();
        int n1 = 3;
        int[][] computers1 = {{1,1,0}, {1,1,0}, {0,0,1}};
        int n2 = 3;
        int[][] computers2 = {{1,1,0}, {1,1,1}, {0,1,1}};
        System.out.println(p.solution(n2, computers2));
    }
}
