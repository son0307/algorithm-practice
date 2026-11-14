package swea.p5215;

import java.util.*;

public class Solution {
    static class Ingredient {
        int score;
        int calorie;

        public Ingredient(int score, int calorie) {
            this.score = score;
            this.calorie = calorie;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            int L = sc.nextInt();

            Ingredient[] ingredients = new Ingredient[N+1];
            for(int i = 1; i <= N; i++) {
                int score = sc.nextInt();
                int calorie = sc.nextInt();

                ingredients[i] = new Ingredient(score, calorie);
            }

            int[][] arr = new int[N+1][L+1];
            for(int i = 1; i < arr.length; i++) {
                for(int j = 1; j < arr[0].length; j++) {
                    arr[i][j] = arr[i-1][j];

                    if(j >= ingredients[i].calorie) {
                        arr[i][j] = Math.max(arr[i][j], ingredients[i].score + arr[i-1][j-ingredients[i].calorie]);
                    }
                }
            }

            System.out.println("#" + tc + " " + arr[N][L]);
        }
    }
}
