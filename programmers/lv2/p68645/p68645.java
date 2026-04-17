package programmers.lv2.p68645;

import java.util.Arrays;

public class p68645 {
    public int[] solution(int n) {
        int[][] arr = new int[n][n];
        int num = 1;
        int indX = 0;
        int indY = 0;

        while(true) {
            // 아래로 내려가며 수를 채움
            while (true) {
                arr[indY][indX] = num++;
                if (indY + 1 >= n || arr[indY + 1][indX] != 0) break;
                indY++;
            }
            if (n == 1 || arr[indY][indX+1] != 0) break;
            indX++;

            // 오른쪽으로 이동하며 수를 채움
            while (true) {
                arr[indY][indX] = num++;
                if (indX + 1 >= n || arr[indY][indX + 1] != 0 ) break;
                indX++;
            }
            if (arr[indY-1][indX-1] != 0) break;
            indX--;
            indY--;

            // 왼쪽 위로 이동하며 수를 채움
            while (true) {
                arr[indY][indX] = num++;
                if (arr[indY - 1][indX - 1] != 0) break;
                indX--;
                indY--;
            }
            if (arr[indY+1][indX] != 0) break;
            indY++;
        }

        int[] answer = new int[num-1];
        int index = 0;
        for(int i=0; i<n; i++){
            System.out.println("arr[i] = " + Arrays.toString(arr[i]));
            for(int j=0; j<n; j++) {
                if(arr[i][j] == 0) continue;
                answer[index++] = arr[i][j];
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        p68645 solution = new p68645();
        int[] answer = solution.solution(6);
        System.out.println("answer = " + Arrays.toString(answer));
    }
}
