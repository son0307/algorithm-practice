package programmers.lv2.p42860;

public class p42860 {
    public int solution(String name) {
        int n = name.length();
        int minMoves = n - 1;
        int moves = 0;
        for (int i = 0; i < n; i++) {
            moves += calc(name.charAt(i));

            int j = i + 1;
            while (j < n && name.charAt(j) == 'A') {
                j++;
            }

            if (j - i > 1) {
                // 오른쪽으로 탐색하다가 방향 전환
                int distanceRight = i * 2 + (n - j);
                minMoves = Math.min(minMoves, distanceRight);

                // 왼쪽으로 탐색하다가 방향 전환
                int distanceLeft = (n - j) * 2 + i;
                minMoves = Math.min(minMoves, distanceLeft);
            }
        }

        return moves + minMoves;
    }

    public int calc(char c) {
        return Math.min(c - 'A', 'Z' - c + 1);
    }

    public static void main(String[] args) {
        p42860 p = new p42860();
        String name1 = "AAAAAAAAAAAAAABAA";
        String name2 = "JAN";

        System.out.println(p.solution(name1));
        System.out.println(p.solution(name2));
    }
}
