package programmers.lv1.p86491;

public class p86491 {
    public int solution(int[][] sizes) {
        int width = Integer.MIN_VALUE;
        int height = Integer.MIN_VALUE;

        for(int[] size : sizes) {
            width = Math.max(width, Math.max(size[0], size[1]));
            height = Math.max(height, Math.min(size[0], size[1]));
        }

        return width * height;
    }

    public static void main(String[] args) {
        p86491 p = new p86491();
        int[][] sizes1 = {{60,50}, {30,70}, {60,30}, {80,40}};
        int[][] sizes2 = {{10,7}, {12,3}, {8,15}, {14,7}, {5,15}};
        int[][] sizes3 = {{14,4}, {19,6}, {6,16}, {18,7}, {7,11}};

        System.out.println(p.solution(sizes3));
    }
}
