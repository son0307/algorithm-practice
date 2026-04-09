package programmers.lv2;

import java.util.ArrayList;
import java.util.List;

public class p60057 {
    public int solution(String s) {
        // 최대 길이는 넘어온 문자열의 길이
        int answer = s.length();

        // 문자 1개부터 길이/2개까지 나눠가며 압축
        for(int i=1; i<=s.length()/2; i++) {
            List<String> strings = new ArrayList<>();
            StringBuilder sb = new StringBuilder();

            // 문자 나누기
            for(int j=0; j<s.length(); j+=i) {
                // 마지막 글자들 추가
                if(j+i-1 >= s.length()) {
                    String lastStr = s.substring(j);
                    strings.add(lastStr);
                } else {
                    // 글자 단위로 잘라서 리스트에 추가
                    String sub = s.substring(j, j + i);
                    strings.add(sub);
                }
            }

            // 압축한 문자열의 길이 계산
            String last = strings.get(0);
            strings.remove(0);
            int count = 1;

            // 중복되서 나타난 것은 앞에 중복횟수를 붙여 문자열에 추가
            for(String str : strings) {
                if(str.equals(last)) {
                    count++;
                } else {
                    if(count > 1) sb.append(count);
                    sb.append(last);
                    last = str;
                    count = 1;
                }
            }

            // 가장 마지막 항목은 for문 안에서 추가하는 부분이 없어 수동으로 추가
            if(count > 1) sb.append(count);
            sb.append(last);

            answer = Math.min(answer, sb.length());
        }
        return answer;
    }

    public static void main(String[] args) {
        p60057 p = new p60057();
        System.out.println(p.solution("xababcdcdababcdcd"));
    }
}
