package programmers.lv1.p68935;

public class p68935 {
    public int solution(int n) {
        StringBuilder sb = new StringBuilder(Integer.toString(n, 3)).reverse();
        int answer = Integer.parseInt(sb.toString(), 3);


        return answer;
    }

    public static void main(String[] args) {
        p68935 p = new p68935();
        System.out.println(p.solution(125));
    }
}
