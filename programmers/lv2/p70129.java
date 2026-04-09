package programmers.lv2;

import java.util.Arrays;

public class p70129 {
    public int[] solution(String s) {
        int count = 0;
        int turn = 0;

        while(true) {
            if(s.length() == 1) break;

            StringBuilder sb = new StringBuilder();
            turn++;
            for(char c : s.toCharArray()) {
                if(c == '0') {
                    count++;
                } else {
                    sb.append(c);
                }
            }
            s = Integer.toString(sb.length(), 2);
        }

        int[] answer = {turn, count};
        return answer;
    }

    public static void main(String[] args) {
        p70129 p = new p70129();
        System.out.println(Arrays.toString(p.solution("110010101001")));
    }
}
