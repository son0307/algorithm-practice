package programmers.lv2.p42578;

import java.util.HashMap;

public class p42578 {
    public int solution(String[][] clothes) {
        int answer = 1;

        HashMap<String, Integer> hm = new HashMap<>();
        for(String[] c : clothes) {
            hm.put(c[1], hm.getOrDefault(c[1], 0) + 1);
        }

        for (Integer v : hm.values()) {
            answer *= v + 1;
        }

        return answer - 1;
    }

    public static void main(String[] args) {
        p42578 p = new p42578();

        String[][] clothes1 = {{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}};
        String[][] clothes2 = {{"crow_mask", "face"}, {"blue_sunglasses", "face"}, {"smoky_makeup", "face"}};
        System.out.println(p.solution(clothes1));
        System.out.println(p.solution(clothes2));
    }
}
