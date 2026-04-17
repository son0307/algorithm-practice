package programmers.lv1.p12917;

public class p12917 {
    public String solution(String s) {
        Integer[] chars = s.chars().boxed().sorted((a, b) -> b - a).toArray(Integer[]::new);

        String answer = "";
        for (int i = 0; i < chars.length; i++) {
            answer += Character.toString(chars[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        p12917 p = new p12917();
        System.out.println(p.solution("ABCefgas"));
    }
}
