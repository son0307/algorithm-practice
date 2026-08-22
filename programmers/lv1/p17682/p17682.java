package programmers.lv1.p17682;

public class p17682 {
    public int solution(String dartResult) {
        int answer = 0;
        StringBuilder sb = new StringBuilder();
        int[] score = new int[3];
        int i = 0;

        char[] chars = dartResult.toCharArray();
        for(int j = 0; j < chars.length; j++) {
            char c = chars[j];
            if(c == 'S' || c == 'D' || c == 'T') {
                score[i] = Integer.parseInt(sb.toString());
                if (c == 'D')
                    score[i] = score[i] * score[i];
                else if (c == 'T')
                    score[i] = score[i] * score[i] * score[i];

                if(j + 1 < chars.length) {
                    if (chars[j + 1] == '*') {
                        score[i] *= 2;
                        if (i != 0)
                            score[i - 1] *= 2;
                        j++;
                    } else if (chars[j + 1] == '#') {
                        score[i] *= -1;
                        j++;
                    }
                }

                sb.setLength(0);
                i++;
                continue;
            }

            sb.append(c);
        }

        for(int s : score) {
            answer += s;
        }

        return answer;
    }

    public static void main(String[] args) {
        p17682 p = new p17682();
        String dartResult = "1D2S3T*";
        System.out.println(p.solution(dartResult));
    }
}
