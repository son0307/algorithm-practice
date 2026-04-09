package programmers.lv2;

import java.util.Arrays;

public class p42842 {
    public int[] solution(int brown, int yellow) {
        for(int height = 3; height <= (brown-2)/2; height++) {
            for(int width = 3; width <= (brown-2)/2; width++) {
                int calBrown = (height + width - 2) * 2;
                int calYellow = height * width - calBrown;
                if(calBrown == brown && calYellow == yellow) {
                    return new int[]{width, height};
                }
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        p42842 p42842 = new p42842();
        int[] dict = p42842.solution(24, 24);
        System.out.println(Arrays.toString(dict));
    }
}
