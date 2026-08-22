package programmers.lv2.p132265;

import java.util.HashMap;

public class p132265 {
    public int solution(int[] topping) {
        int answer = 0;

        HashMap<Integer, Integer> type1 = new HashMap<>();
        HashMap<Integer, Integer> type2 = new HashMap<>();
        for(int t : topping)
            type1.put(t, type1.getOrDefault(t, 0) + 1);

        for(int t : topping) {
            type2.put(t, type2.getOrDefault(t, 0) + 1);
            if(type1.get(t) == 1) {
                type1.remove(t);
            } else {
                type1.put(t, type1.get(t) - 1);
            }

            if(type1.size() == type2.size()) answer++;
        }

        return answer;
    }

    public static void main(String[] args) {
        p132265 p = new p132265();
        int[] topping = {1,2,3,1,4};
        System.out.println(p.solution(topping));
    }
}
