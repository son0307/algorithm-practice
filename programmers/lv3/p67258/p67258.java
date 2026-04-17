package programmers.lv3.p67258;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class p67258 {
    public int[] solution(String[] gems) {
        HashMap<String, Integer> gemsMap = new HashMap<>();
        HashSet<String> gemTypes = new HashSet<>(Arrays.asList(gems));

        int length = Integer.MAX_VALUE;
        int start = 0;

        int pt1 = 0;
        int pt2 = 0;

        while (true) {
            if (gemsMap.size() == gemTypes.size()) {
                if (pt2 - pt1 < length) {
                    length = pt2 - pt1;
                    start = pt1;
                }

                gemsMap.put(gems[pt1], gemsMap.get(gems[pt1]) - 1);
                if (gemsMap.get(gems[pt1]) == 0)
                    gemsMap.remove(gems[pt1]);

                pt1++;
            }
            else if (pt2 == gems.length) {
                break;
            }
            else {
                gemsMap.put(gems[pt2], gemsMap.getOrDefault(gems[pt2], 0) + 1);
                pt2++;
            }
        }

        return new int[]{start + 1, start + length};
    }

    public static void main(String[] args) {
        String[] gems1 = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};
        String[] gems2 = {"AA", "AB", "AC", "AA", "AC"};
        String[] gems3 = {"XYZ", "XYZ", "XYZ"};
        String[] gems4 = {"ZZZ", "YYY", "NNNN", "YYY", "BBB"};

        System.out.println(Arrays.toString(new p67258().solution(gems1)));
        System.out.println(Arrays.toString(new p67258().solution(gems2)));
        System.out.println(Arrays.toString(new p67258().solution(gems3)));
        System.out.println(Arrays.toString(new p67258().solution(gems4)));
    }
}
