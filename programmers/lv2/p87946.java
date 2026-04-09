package programmers.lv2;

public class p87946 {
    private int max_count = 0;

    public void search(int remain_hp, int count, boolean[] visited, int[][] dungeons) {
        for (int i = 0; i < dungeons.length; i++) {
            if (dungeons[i][0] <= remain_hp && !visited[i]) {
                visited[i] = true;
                search(remain_hp - dungeons[i][1], count + 1, visited, dungeons);
                visited[i] = false;
            }
        }

        max_count = Math.max(max_count, count);
    }

    public int solution(int k, int[][] dungeons) {
        boolean[] visited = new boolean[dungeons.length];
        search(k, 0, visited, dungeons);

        return max_count;
    }

    public static void main(String[] args) {
        int k = 80;
        int[][] dungeons = {{80, 20}, {50, 40}, {30, 10}};
        System.out.println(new p87946().solution(k, dungeons));
    }
}
