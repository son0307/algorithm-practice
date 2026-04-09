package programmers.lv1;

public class p81301 {
    public int solution(String s) {
        String[] numDict = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] strDict = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        for(int i=0; i<10; i++) {
            s = s.replace(strDict[i], numDict[i]);
        }

        int answer = Integer.parseInt(s);
        return answer;
    }

    public static void main(String[] args) {
        p81301 p = new p81301();
        System.out.println(p.solution("123"));
    }
}
