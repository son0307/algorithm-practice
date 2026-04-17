## 문제 요약

- **목표**: 짝수인 $N$명의 사람을 정확히 절반($N/2$)씩 스타트 팀과 링크 팀으로 나누었을 때, 두 팀의 능력치 차이의 최솟값을 구한다.
- **조건**:
    1. 사람 i와 j가 같은 팀에 속하면 팀의 능력치에 $S_{ij} + S_{ji}$가 더해진다.
    2. 능력치 표의 대각선($S_{ii}$)은 0이며, $S_{ij}$와 $S_{ji}$는 다를 수 있다.
- **출력 항목**: 두 팀의 능력치 차이의 최솟값

## 핵심 포인트

- **조합 (Combinations)과 백트래킹**: N명 중에서 N/2명을 뽑는 모든 경우의 수를 재귀 함수를 이용해 탐색한다.
- **상태 배열을 통한 논리적 분리**: `visited` 배열을 사용하여 `true`인 사람은 스타트 팀, `false`인 사람은 링크 팀으로 간주하여 한 번의 탐색으로 두 팀을 동시에 분류한다.
- **대칭성 제거 (Symmetry Breaking)**: 팀을 A팀, B팀으로 나누는 것은 이름표만 다를 뿐 사실상 대칭적인 구조를 가진다. 이를 파악하고 절반의 경우의 수만 탐색하는 것이 시간 단축의 핵심이다.
    - 4명을 {1, 2}와 {3, 4}로 나누는 것과, {3, 4}, {1, 2}로 나누는 것은 이름표만 다를 뿐 능력치의 차이는 동일하다. 그러므로, 1번 사람을 한쪽 팀에 못 박아두면 이런 중복 탐색이 절반으로 줄어들어 연산 시간을 획기적으로 아낄 수 있다.
    - 이를 수학적으로 접근하면 확실하게 확인할 수 있다.
      N명 중에서 N/2명을 뽑는 $_{N}C_{N/2}$이다.
      1번 사람을 한 팀에 고정하고 나머지 N-1명 중에서 남은 자리 N/2 -1 개를 채우는 경우의 수는 $_{N-1}C_{N/2-1}$이다.

      수식을 정리하면 다음과 같다.

      ${N \choose N/2} = \frac{N!}{(N/2)! (N/2)!}$

      ${N-1 \choose N/2-1} = \frac{(N-1)!}{(N/2-1)! (N/2)!} = \frac{1}{2} \times \frac{N!}{(N/2)! (N/2)!} = \frac{1}{2} \times {N \choose N/2}$

      이를 통해 1명을 고정시킴으로써 전체 경우의 수가 1/2이 된다는 것을 알 수 있다.


### 동작 원리

1. **배열 및 변수 초기화**: $N \times N$ 크기의 능력치 2차원 배열 `S`와 팀 분류를 위한 `visited` 배열을 생성한다. 최솟값 갱신을 위한 `minDiff`는 정수 최댓값으로 둔다.
2. **시작점 고정 및 DFS 탐색**: 중복 탐색을 막기 위해 1번 사람(`visited[1]`)을 방문 처리(`true`)하고, 2번 사람부터 탐색 깊이 1 상태로 재귀(`dfs`)를 시작한다.
3. **팀원 선발 (Recursive Step)**: 아직 선택되지 않은 사람들을 순차적으로 방문 처리(`visited[i] = true`)하며 N/2명이 찰 때까지 재귀 호출을 깊게 파고든다. 한 경로의 탐색이 끝나면 백트래킹을 통해 상태를 복구(`false`)한다.
4. **능력치 계산 (Base Case)**: N/2명이 모두 뽑혔다면(`count == n/2`), `visited`가 `true`인 팀(스타트 팀)과 `false`인 팀(링크 팀)의 시너지 합(`stat1`, `stat2`)을 각각 계산한다.
5. **최솟값 갱신 및 가지치기**: 두 팀의 능력치 차이를 절대값으로 구하여 `minDiff`를 갱신한다. 만약 이 값이 0이라면 최적해를 이미 찾은 것이므로 호출 스택을 연쇄적으로 종료시킨다.

### 전체 코드

```java
import java.io.*;
import java.util.StringTokenizer;

public class Main {
    int minDiff = Integer.MAX_VALUE;

    public void dfs(int n, int[][] S, boolean[] visited, int idx, int count) {
        if (count == n/2) {
            int stat1 = 0;
            int stat2 = 0;

            for (int i = 1; i <= n; i++) {
                for (int j = i; j <= n; j++) {
                    if (visited[i] && visited[j]) stat1 += S[i][j] + S[j][i];
                    else if (!visited[i] && !visited[j]) stat2 += S[i][j] + S[j][i];
                }
            }

            minDiff = Math.min(minDiff, Math.abs(stat1 - stat2));

            return;
        }

        for (int i = idx; i <= n; i++) {
            visited[i] = true;
            dfs(n, S, visited, i + 1, count + 1);
            visited[i] = false;
            if (minDiff == 0) return;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int[][] S = new int[n+1][n+1];
        boolean[] visited = new boolean[n+1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Main m = new Main();
        visited[1] = true;
        m.dfs(n, S, visited, 2, 1);
        System.out.println(m.minDiff);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- N명 중 1명을 고정시켜두고 N/2을 뽑는 경우의 수 : $_{N-1}C_{N/2-1}$
- 각 조합이 완성될 때마다 능력치의 차 계산 : $N^2$

최종적으로 위 알고리즘의 시간복잡도는 $O(N^2 \cdot _{N-1}C_{N/2-1})$이 된다.