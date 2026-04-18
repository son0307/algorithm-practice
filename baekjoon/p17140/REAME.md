## 문제 요약

- **목표**: 1초마다 주어지는 조건(R 연산, C 연산)에 따라 2차원 배열을 정렬 및 확장하며, 주어진 시간(최대 100초) 내에 `A[r][c]`의 값이 `k`가 되기 위한 최소 시간을 구한다.
- **조건**:
    1. **R 연산**: 행의 개수 $\ge$ 열의 개수일 때, 모든 행에 대해 정렬.
    2. **C 연산**: 행의 개수 $<$ 열의 개수일 때, 모든 열에 대해 정렬.
    3. **정렬 기준**: 1순위 - 수의 등장 횟수 오름차순, 2순위 - 수의 크기 오름차순. (0은 정렬에서 무시)
    4. **배열 확장**: 정렬 결과를 '수, 등장 횟수' 순으로 다시 넣으며, 길이가 가장 긴 행/열에 맞춰 크기가 늘어나고 빈 곳은 0으로 채운다. (최대 100개까지만 유지)
- **출력 항목**: `A[r][c] == k`가 되는 최소 시간. 100초가 넘어가면 -1 출력.

## 핵심 포인트

- **커스텀 정렬 (Custom Sort)**: 숫자와 등장 횟수를 한 쌍으로 묶어 문제의 조건에 맞게 정렬할 수 있는 `Comparable` 객체 설계가 필수적이다.
- **배열의 찌꺼기 초기화 (Zero Padding)**: 배열 크기가 줄어들거나 변경될 때, 이전에 저장되어 있던 데이터가 남아 오작동을 일으키지 않도록 남은 자리를 확실하게 0으로 덮어씌워야 한다.
- **행과 열의 유연한 탐색**: 2차원 배열에서 가로 탐색(R 연산)과 세로 탐색(C 연산)을 분기하여 정확하게 수행해야 한다.

### 동작 원리

1. **변수 초기화**: $101 \times 101$ 크기의 `arr` 배열을 생성하여 1-based 인덱싱을 안전하게 처리하고 초기 $3 \times 3$ 데이터를 입력받는다.
2. **조건 검사 (탈출)**: 루프 시작 시 `arr[r][c]`가 `k`인지 확인하고 맞다면 현재 `time`을 출력하고 종료한다.
3. **R 연산 / C 연산 판별**: 현재 `rowLength`와 `colLength`를 비교하여 분기한다.
4. **등장 횟수 카운팅**: 각 행(또는 열)을 순회하며 0을 제외한 숫자들을 `HashMap`에 담아 등장 횟수를 기록한다.
5. **정렬 및 배열 갱신**:
    - `HashMap`의 데이터를 `Count` 객체로 변환하여 리스트에 담고 조건에 맞게 정렬한다.
    - 정렬된 결과를 다시 `arr` 배열에 '숫자 → 횟수' 순으로 채워 넣는다.
    - 최대 크기가 100을 넘어가면 중단하고, 채우고 남은 뒷부분의 인덱스는 모두 0으로 지워준다.
6. **크기 갱신 및 시간 증가**: 가장 길었던 행/열의 길이로 전체 `colLength` / `rowLength`를 갱신하고 시간을 1 증가시킨다. 100초를 초과하면 -1을 출력한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

class Count implements Comparable<Count>{
    int n;
    int cnt;

    public Count(int n, int cnt) {
        this.n = n;
        this.cnt = cnt;
    }

    @Override
    public int compareTo(Count c1) {
        if (this.cnt == c1.cnt) {
            return this.n - c1.n;
        }
        return this.cnt - c1.cnt;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] arr = new int[101][101];
        int rowLength = 3;
        int colLength = 3;
        for (int i = 1; i <= rowLength; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= colLength; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int time = 0;
        while (time >= 0) {
            if (arr[r][c] == k)
                break;

            // R 연산
            if (rowLength >= colLength) {
                int maxColLength = 0;

                for (int i = 1; i <= rowLength; i++) {
                    Map<Integer, Integer> map = new HashMap<>();

                    // 1. 카운트 (0은 무시)
                    for (int j = 1; j <= colLength; j++) {
                        if (arr[i][j] == 0) continue;
                        map.put(arr[i][j], map.getOrDefault(arr[i][j], 0) + 1);
                    }

                    // 2. ArrayList에 넣고 정렬
                    List<Count> list = new ArrayList<>();
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        list.add(new Count(entry.getKey(), entry.getValue()));
                    }
                    Collections.sort(list);

                    // 3. 배열에 다시 채우기
                    int idx = 1;
                    for (Count count : list) {
                        arr[i][idx++] = count.n;
                        arr[i][idx++] = count.cnt;
                        if (idx > 100) break;
                    }

                    maxColLength = Math.max(maxColLength, idx - 1);

                    // 4. 남은 자리는 0으로 채우기
                    while (idx <= 100)
                        arr[i][idx++] = 0;
                }
                colLength = maxColLength;
            }
            // C 연산
            else {
                int maxRowLength = 0;

                for (int i = 1; i <= colLength; i++) {
                    Map<Integer, Integer> map = new HashMap<>();

                    // 1. 카운트 (0은 무시)
                    for (int j = 1; j <= rowLength; j++) {
                        if (arr[j][i] == 0) continue;
                        map.put(arr[j][i], map.getOrDefault(arr[j][i], 0) + 1);
                    }

                    // 2. ArrayList에 넣고 정렬
                    List<Count> list = new ArrayList<>();
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        list.add(new Count(entry.getKey(), entry.getValue()));
                    }
                    Collections.sort(list);

                    // 3. 배열에 다시 채우기
                    int idx = 1;
                    for (Count count : list) {
                        arr[idx++][i] = count.n;
                        arr[idx++][i] = count.cnt;
                        if (idx > 100) break;
                    }

                    maxRowLength = Math.max(maxRowLength, idx - 1);

                    // 4. 남은 자리는 0으로 채우기
                    while (idx <= 100)
                        arr[idx++][i] = 0;
                }
                rowLength = maxRowLength;
            }

            time++;

            if (time > 100)
                time = -1;
        }

        System.out.println(time);
    }
}
```