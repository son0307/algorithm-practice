package programmers.lv1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class p150370 {
    public int toInt(String s) {
        String[] arr = s.split("\\.");
        int res = 0;
        res += Integer.parseInt(arr[0]) * 12 * 28;
        res += Integer.parseInt(arr[1]) * 28;
        res += Integer.parseInt(arr[2]);

        return res;
    }

    public int[] solution(String today, String[] terms, String[] privacies) {
        List<Integer> res = new LinkedList<>();

        int today_int = toInt(today);

        HashMap<String, Integer> termMap = new HashMap<>();
        for (String term : terms) {
            String[] termArr = term.split(" ");
            termMap.put(termArr[0], Integer.parseInt(termArr[1]) * 28);
        }

        int[] privacies_int = new int[privacies.length];
        for (int i = 0; i < privacies.length; i++) {
            String[] privaciesArr = privacies[i].split(" ");
            privacies_int[i] = toInt(privaciesArr[0]) + termMap.get(privaciesArr[1]);
        }

        for (int i = 0; i < privacies.length; i++) {
            if (privacies_int[i] <= today_int)
                res.add(i+1);
        }

        return res.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        p150370 a = new p150370();
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};

        System.out.println(Arrays.toString(a.solution(today, terms, privacies)));
    }
}
