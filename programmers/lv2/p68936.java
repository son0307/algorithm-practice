package programmers.lv2;

import java.util.Arrays;

public class p68936 {
    private void quad(int[][] arr, int[] answer, int xStart, int xEnd, int yStart, int yEnd) {
        if(xStart == xEnd && yStart == yEnd) {
            answer[arr[yStart][xStart]]++;
            return;
        }

        int ind = arr[yStart][xStart];
        for(int i=yStart; i<=yEnd; i++) {
            for (int j=xStart; j<=xEnd; j++) {
                if(arr[i][j] != ind) {
                    int size = (xEnd - xStart + 1) / 2;
                    quad(arr, answer, xStart, xStart+size-1, yStart, yStart+size-1);
                    quad(arr, answer, xStart+size, xEnd, yStart, yStart+size-1);
                    quad(arr, answer, xStart, xStart+size-1, yStart+size, yEnd);
                    quad(arr, answer, xStart+size, xEnd, yStart+size, yEnd);
                    return;
                }
            }
        }
        answer[ind]++;
    }

    public int[] solution(int[][] arr) {
        int[] answer = new int[2];
        quad(arr, answer, 0, arr.length-1, 0, arr.length-1);
        return answer;
    }

    public static void main(String[] args) {
        p68936 p = new p68936();
        // int[][] arr = {{1,1,0,0}, {1,0,0,0}, {1,0,0,1}, {1,1,1,1}};
        int[][] arr = { {1, 1, 1, 1, 1, 1, 1, 1},
                        {0, 1, 1, 1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 1, 1, 1, 1},
                        {0, 1, 0, 0, 1, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 1, 0, 0, 1},
                        {0, 0, 0, 0, 1, 1, 1, 1}};
        int[] answer = p.solution(arr);
        System.out.println(Arrays.toString(answer));
    }
}
