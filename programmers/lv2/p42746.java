package programmers.lv2;

import java.util.Arrays;

public class p42746 {
    public String solution(int[] numbers) {
        String[] str = Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted((s1, s2) -> Integer.compare(Integer.parseInt(s2+s1), Integer.parseInt(s1+s2)))
                .toArray(String[]::new);

        String answer = "";
        for (int i = 0; i < str.length; i++) {
            answer += str[i];
        }

        answer = answer.replaceAll("^0+", "0");
        return answer;
    }

    public static void main(String[] args) {
        p42746 p = new p42746();
        System.out.println(p.solution(new int[]{0,0,0,0,0}));
    }
}
