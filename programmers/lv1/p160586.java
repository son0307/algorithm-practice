package programmers.lv1;

import java.util.Arrays;
import java.util.HashMap;

public class p160586 {
    public int[] solution(String[] keymap, String[] targets) {
        int[] answer = new int[targets.length];

        HashMap<Character, Integer> map = new HashMap<>();
        for (String key : keymap) {
            char[] chars = key.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!map.containsKey(chars[i])) {
                    map.put(chars[i], i + 1);
                } else {
                    if (map.get(chars[i]) > i + 1)
                        map.put(chars[i], i + 1);
                }
            }
        }

        for (int i = 0; i < targets.length; i++) {
            for (char c : targets[i].toCharArray()) {
                if (!map.containsKey(c)) {
                    answer[i] = -1;
                    break;
                }
                answer[i] += map.get(c);
            };
        }

        return answer;
    }

    public static void main(String[] args) {
        String[] keymap = {"ABCDE", "FGHIJ"};
        String[] targets = {"ZABCDE"};
        System.out.println(Arrays.toString(new p160586().solution(keymap, targets)));
    }
}
