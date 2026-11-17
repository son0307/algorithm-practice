### 1. 문제 요약
   무게가 각각 다른 과자 봉지 N개 중 정확히 두 봉지를 선택한다. 두 봉지의 무게 합은 M을 초과할 수 없으며, 이 조건을 만족하는 무게 합의 최댓값을 구한다. 선택할 수 있는 두 봉지가 없다면 -1을 출력한다.
### 2. 핵심 포인트
   제출한 두 풀이는 모두 가능한 답을 찾는다. 차이는 두 봉지를 탐색하는 방법에 있다.

| 풀이 | 탐색 방법 | 시간 복잡도 |
|---|---|---:|
| DFS | 두 봉지를 고르는 모든 조합 확인 | `O(N²)` |
| 정렬 + 투 포인터 | 정렬한 배열의 양 끝에서 범위를 좁힘 | `O(N log N)` |


시간 복잡도상 더 빠른 방법은 투 포인터 방식이다. N이 커질수록 DFS보다 유리하다.
### 3. 동작 원리
**DFS 방식**
   
첫 번째 과자를 고른 뒤, 그보다 뒤에 있는 과자 중 두 번째 과자를 고른다. 두 봉지를 선택할 때마다 무게 합이 M 이하인지 확인하고, 조건을 만족하면 최댓값을 갱신한다. 뒤쪽 봉지만 선택하므로 같은 조합을 중복해서 검사하지 않는다.
   
**정렬 + 투 포인터 방식**
   
과자 무게를 오름차순으로 정렬하고, 가장 가벼운 과자를 가리키는 left와 가장 무거운 과자를 가리키는 right를 둔다.
- 두 봉지의 합이 M보다 크면 더 가벼운 조합을 만들기 위해 right를 왼쪽으로 옮긴다.
- 합이 M 이하이면 최댓값을 갱신하고, 더 큰 합을 찾기 위해 left를 오른쪽으로 옮긴다.
  두 포인터가 만나면 서로 다른 두 봉지를 선택할 수 없으므로 탐색을 끝낸다.
### 4. 전체 코드

- DFS 방식


    import java.util.*;
    
    public class Solution {
        static int maxWeight;
    
        static void dfs(int[] weights, int idx, int selected, int limit, int sum) {
            if (selected == 2) {
                if (sum <= limit) {
                    maxWeight = Math.max(maxWeight, sum);
                }
                return;
            }
    
            for (int i = idx; i < weights.length; i++) {
                dfs(weights, i + 1, selected + 1, limit, sum + weights[i]);
            }
        }
    
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
    
            for (int tc = 1; tc <= T; tc++) {
                maxWeight = -1;
    
                int N = sc.nextInt();
                int M = sc.nextInt();
                int[] weights = new int[N];
    
                for (int i = 0; i < N; i++) {
                    weights[i] = sc.nextInt();
                }
    
                dfs(weights, 0, 0, M, 0);
                System.out.println("#" + tc + " " + maxWeight);
            }
        }
    }

- 정렬 + 투 포인터 방식


    import java.util.*;
    
    public class Solution {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
    
            for (int tc = 1; tc <= T; tc++) {
                int maxWeight = -1;
    
                int N = sc.nextInt();
                int M = sc.nextInt();
                int[] weights = new int[N];
    
                for (int i = 0; i < N; i++) {
                    weights[i] = sc.nextInt();
                }
    
                Arrays.sort(weights);
    
                int left = 0;
                int right = N - 1;
    
                while (left < right) {
                    int sum = weights[left] + weights[right];
    
                    if (sum > M) {
                        right--;
                    } else {
                        maxWeight = Math.max(maxWeight, sum);
                        left++;
                    }
                }
    
                System.out.println("#" + tc + " " + maxWeight);
            }
        }
    }

### 5. 시간 복잡도
   DFS는 두 봉지를 고르는 모든 조합을 확인하므로 O(N²)이다. 재귀 깊이는 최대 2다.

투 포인터 방식은 정렬에 O(N log N), 포인터 이동에 O(N)이 걸린다. 따라서 전체 시간 복잡도는 **`O(N log N)`**
이다. 이 문제의 제한인 N ≤ 1000에서는 두 방식 모두 사용할 수 있지만, 효율 면에서는 투 포인터 방식이 더 좋다.