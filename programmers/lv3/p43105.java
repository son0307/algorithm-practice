package programmers.lv3;

public class p43105 {
    public int solution(int[][] triangle) {
        for (int i = triangle.length - 1; i >= 0; i--) {
            for (int j = 0; j < triangle[i].length - 1; j++) {
                triangle[i-1][j] += Math.max(triangle[i][j], triangle[i][j+1]);
            }
        }

        return triangle[0][0];
    }

    public static void main(String[] args) {
        p43105 p = new p43105();
        int[][] triangle = {{7}, {3,8}, {8,1,0}, {2,7,4,4}, {4,5,2,6,5}};
        System.out.println(p.solution(triangle));
    }
}
