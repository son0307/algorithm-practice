## 문제 요약

- **목표**: 크기가 $N$인 4개의 정수 수열 $A, B, C, D$에서 각각 원소를 하나씩 골라 더했을 때($a+b+c+d$), 그 합이 정확히 0이 되는 모든 경우의 수를 구한다.
- **조건**:
    1. 수열의 크기 $N$은 최대 5,000이다.
    2. 수열의 각 원소는 -1,000,000,000 이상 1,000,000,000 이하의 정수이다.
- **출력 항목**: 조건을 만족하는 4개의 원소 쌍의 총개수.

## 핵심 포인트

- **시간 복잡도 줄이기**: 4개의 배열에서 선택 가능한 모든 경우의 수를 검색하려면 $5,000^4 = 625 \times 10^{12}$번이 되어 제한 시간 내에 해결할 수 없다. 대신 수열을 $(A, B)$와 $(C,D)$로 나누면 $(A+B) = -(C+D)$라는 관계가 성립한다. 이 성질을 이용하면 $O(N^2)$의 연산으로 해결할 수 있다.
- **해시맵**: $(A+B)$의 합을 키로, 빈도를 값으로 저장해두면, $(C+D)$의 각 합에 대한 짝을 $O(1)$만에 찾아낼 수 있다.

### 동작 원리

1. **수열 분리 및 데이터 입력**: $A$ 수열을 배열에 저장한다.
2. **$(A+B)$ 그룹 처리 (`sumMap` 생성)**:
    - $B$ 수열의 원소를 하나씩 입력받을 때마다 $A$ 배열의 원소들과 더한다.
    - 계산된 $a+b$ 합계를 키(key)로, 해당 합계가 나온 횟수(빈도)를 값(value)으로 해시맵(`sumMap`)에 기록한다.
3. **$(C+D)$ 그룹 처리 및 동시 매칭**:
    - $C$ 수열을 배열에 저장한다.
    - $D$ 수열의 원소를 입력받을 때마다 $C$ 배열의 원소들과 더한다.
    - 이때 방금 계산한 $c+d$의 부호를 뒤집은 $-(c+d)$가 `sumMap`에 키로 존재하는지 조회한다. (즉, $a+b+c+d=0$이 되는 짝이 있는지 확인)
    - 짝이 존재한다면, 그 짝의 빈도수만큼 전체 경우의 수(`answer`)에 더한다.
4. **결과 출력**: 누적된 `answer`를 출력한다.

### 전체 코드

```java
package codetree.hashMap01.theSumOfTheElementsIs0;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arrA = new int[n];
        int[] arrC = new int[n];

        HashMap<Integer, Integer> sumMap = new HashMap<>();

        // A + B
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arrA[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int b = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                int sumAB = b +     arrA[j];
                sumMap.put(sumAB, sumMap.getOrDefault(sumAB, 0) + 1);
            }
        }

        // C + D
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arrC[i] = Integer.parseInt(st.nextToken());
        }

        long answer = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int d = Integer.parseInt(st.nextToken());
            for (int j = 0; j < n; j++) {
                int sumCD = d + arrC[j];
                answer += sumMap.getOrDefault(-sumCD, 0);
            }
        }

        System.out.println(answer);
    }
}

```

위 알고리즘의 시간 복잡도는 다음과 같다.

- $(A+B)$의 합을 구하는데 걸리는 시간 : $O(N^2)$
- $(C+D)$의 합을 구하는 데 걸리는 시간 : $O(N^2)$
- 해시맵에 값을 삽입 or 조회하는데 걸리는 시간 : $O(1)$

따라서, 위 알고리즘의 시간 복잡도는 $O(N^2)$이다.