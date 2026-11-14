package swea.p1204;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int tc = sc.nextInt();
        for(int i = 0; i < tc; i++) {
            int testNum = sc.nextInt();
            HashMap<Integer, Integer> map = new HashMap<>();
            for(int s = 0; s < 1000; s++) {
                int score = sc.nextInt();
                map.put(score, map.getOrDefault(score, 0) + 1);
            }

            Set<Integer> set = map.keySet();
            int result = set.stream().sorted((k1, k2) -> {
                if(map.get(k1).equals(map.get(k2)))
                    return k2 - k1;

                return map.get(k2) - map.get(k1);
            }).findFirst().get();

            System.out.println("#" + testNum + " " + result);
        }
    }
}
