### 1. 문제 요약
   홀수 크기인 \(N \times N\) 농장에서 중앙을 기준으로 형성되는 마름모 영역의 농작물 가치를 합산한다.
### 2. 핵심 포인트
   마름모에 포함되는 열의 범위는 행의 위치에 따라 달라진다. 첫 행에서는 중앙 한 칸만 포함하고, 중앙 행까지 범위가 넓어진 뒤 다시 좁아진다.

각 행의 양쪽에서 제외할 칸 수를 blank의 절댓값으로 구하면 이 범위를 간단하게 표현할 수 있다.
### 3. 동작 원리
   blank를 -(N / 2)로 초기화하고 행을 처리할 때마다 1씩 증가시킨다. \(N=5\)라면 각 행에서 제외할 칸 수는 2 → 1 → 0 → 1 → 2가 된다.

각 행에서 Math.abs(blank)번 열부터 N - Math.abs(blank) - 1번 열까지의 값을 더한다. 이 과정을 모든 행에 적용하면 마름모 영역의 합을 구할 수 있다.
### 4. 전체 코드
    import java.util.*;
    
    public class Solution {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
    
            int T = sc.nextInt();
            for (int tc = 1; tc <= T; tc++) {
                int n = sc.nextInt();
                int[][] arr = new int[n][n];
    
                for (int i = 0; i < n; i++) {
                    String[] nums = sc.next().split("");
                    for (int j = 0; j < n; j++) {
                        arr[i][j] = Integer.parseInt(nums[j]);
                    }
                }
    
                int blank = -(n / 2);
                int sum = 0;
    
                for (int i = 0; i < n; i++) {
                    int absBlank = Math.abs(blank);
    
                    for (int j = absBlank; j < n - absBlank; j++) {
                        sum += arr[i][j];
                    }
    
                    blank++;
                }
    
                System.out.println("#" + tc + " " + sum);
            }
        }
    }

### 5. 시간 복잡도
   농장 데이터를 입력받는 데 \(O(N^2)\), 마름모 영역을 순회하는 데 최대 \(O(N^2)\)이 소요된다. 따라서 전체 시간 복잡도는 **\(O(N^2)\)** 이며, 배열을 저장하는 공간 복잡도는 **\(O(N^2)\)** 이다.