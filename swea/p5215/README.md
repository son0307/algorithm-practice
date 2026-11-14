### 1. 문제 요약
   각 재료에는 맛 점수와 칼로리가 있다. 재료를 각각 최대 한 번씩 사용하여, 총칼로리가 L 이하인 조합 중 맛 점수의 합이 가장 큰 값을 구한다.
### 2. 핵심 포인트
   이 문제는 0/1 배낭 문제다. 재료마다 다음 두 경우를 비교한다.
- 선택하지 않기: arr[i - 1][j]
- 선택하기: ingredients[i].score + arr[i - 1][j - ingredients[i].calorie]
  선택하는 경우에도 이전 행인 i - 1을 참조하므로 같은 재료를 두 번 사용하지 않는다.
### 3. 동작 원리
   arr[i][j]는 앞에서부터 i개의 재료를 고려하고, 칼로리 제한이 j일 때의 최대 맛 점수다.

먼저 현재 재료를 선택하지 않았을 때의 값을 넣는다. 현재 재료의 칼로리가 j 이하라면, 선택했을 때의 점수와 비교해 더 큰 값을 저장한다. 모든 재료를 처리한 뒤 정답은 arr[N][L]이다.
### 4. 전체 코드
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
            for (int tc = 1; tc <= T; tc++) {
                int N = sc.nextInt();
                int L = sc.nextInt();
    
                Ingredient[] ingredients = new Ingredient[N + 1];
                for (int i = 1; i <= N; i++) {
                    int score = sc.nextInt();
                    int calorie = sc.nextInt();
    
                    ingredients[i] = new Ingredient(score, calorie);
                }
    
                int[][] arr = new int[N + 1][L + 1];
                for (int i = 1; i <= N; i++) {
                    for (int j = 1; j <= L; j++) {
                        arr[i][j] = arr[i - 1][j];
    
                        if (j >= ingredients[i].calorie) {
                            arr[i][j] = Math.max(
                                arr[i][j],
                                ingredients[i].score
                                    + arr[i - 1][j - ingredients[i].calorie]
                            );
                        }
                    }
                }
    
                System.out.println("#" + tc + " " + arr[N][L]);
            }
        }
    }

### 5. 시간 복잡도
재료 N개에 대해 칼로리 1부터 L까지 확인하므로 **시간 복잡도는 \(O(NL)\)** 이다. DP 배열의 **공간 복잡도도 \(O(NL)\)** 이다.