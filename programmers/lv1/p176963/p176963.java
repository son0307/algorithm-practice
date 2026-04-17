package programmers.lv1.p176963;

import java.util.Arrays;
import java.util.HashMap;

public class p176963 {
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        int[] answer = new int[photo.length];

        HashMap<String, Integer> score = new HashMap<>();
        for (int i = 0; i < name.length; i++)
            score.put(name[i], yearning[i]);

        for (int i = 0; i < photo.length; i++) {
            String[] p = photo[i];
            int sum = 0;
            for (String s : p)
                sum += score.getOrDefault(s, 0);
            answer[i] = sum;
        }

        return answer;
    }

    public static void main(String[] args) {
        String[] name = new String[]{"may", "kein", "kain", "radi"};
        int[] yearning = new int[]{5, 10, 1, 3};
        String[][] photo = new String[][]{{"may", "kein", "kain", "radi"}, {"may", "kein", "brin", "deny"}, {"kon", "kain", "may", "coni"}};
        System.out.println(Arrays.toString(new p176963().solution(name, yearning, photo)));
    }
}
