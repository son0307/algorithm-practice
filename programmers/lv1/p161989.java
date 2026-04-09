package programmers.lv1;

public class p161989 {
    public int solution(int n, int m, int[] section) {
        int roller = section[0];
        int cnt = 1;

        for (int i = 1; i < section.length; i++) {
            if (roller + m - 1 < section[i]) {
                cnt++;
                roller = section[i];
            }
        }

        return cnt;
    }

    public static void main(String[] args) {
        int n = 4;
        int m = 1;
        int[] section = new int[]{1,2,3,4};
        System.out.println(new p161989().solution(n, m, section));
    }
}
