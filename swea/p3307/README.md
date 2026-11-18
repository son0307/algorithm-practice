## 1. 문제 요약

길이가 `N`인 수열에서 원소의 순서를 유지하며, 값이 **엄격하게 증가**하는 부분 수열의 최대 길이를 구한다. SWEA 3307번의 출력 형식은 테스트케이스별 `#테스트케이스번호 정답`이다.

두 코드는 같은 문제를 서로 다른 방법으로 해결한다.

| 코드 | 풀이 방법 | 시간 복잡도 |
|---|---|---|
| `Solution` | 동적 계획법(DP) | O(N²) |
| `Solution2` | 이분탐색 | O(N log N) |

## 2. 핵심 포인트

### `Solution`: 각 위치에서 끝나는 LIS 길이 구하기

`lens[i]`는 **`nums[i]`를 마지막 원소로 사용하는 증가 부분 수열의 최대 길이**다. 따라서 앞에 있는 `nums[j]`가 `nums[i]`보다 작다면, `j`에서 끝나는 수열 뒤에 `nums[i]`를 붙일 수 있다.

```java
lens[i] = Math.max(lens[i], lens[j] + 1);
```

모든 `lens[i]` 중 최댓값이 정답이다.

### `Solution2`: 길이별로 가장 작은 마지막 값 유지하기

이 코드의 `lens`는 첫 번째 코드의 `lens`와 **의미가 다르다**. `lens.get(k)`는 길이가 `k + 1`인 증가 부분 수열이 가질 수 있는 **가장 작은 마지막 값**이다.

새 숫자 `num`을 처리할 때는 다음과 같이 갱신한다.

- `num`이 현재 마지막 값보다 크면 `lens` 끝에 추가한다.
- 그렇지 않으면 이분탐색으로 `num` **이상인 첫 번째 값**을 찾아 `num`으로 교체한다.

교체는 수열의 길이를 늘리지는 않지만, 마지막 값을 작게 만들어 이후 숫자를 연결할 가능성을 높인다. 값이 같은 원소는 증가한 것으로 보지 않으므로, 탐색 조건이 `<=`가 아니라 `<`인 점도 중요하다.

## 3. 동작 원리

수열이 `1, 3, 2, 4`라고 가정해 보자.

| 입력한 값 | DP에서 해당 위치의 길이 | 이분탐색 풀이의 `lens` |
|---:|---:|---|
| 1 | 1 | `[1]` |
| 3 | 2 | `[1, 3]` |
| 2 | 2 | `[1, 2]` |
| 4 | 3 | `[1, 2, 4]` |

DP 풀이에서는 `2` 앞의 `1`을 확인하여 길이 `2`를 구한다. 이분탐색 풀이에서는 `[1, 3]`의 `3`을 `2`로 교체한다. 두 방법 모두 최종 답은 `3`이다.

이때 이분탐색 풀이의 `lens`는 **실제 LIS 자체를 저장하기 위한 배열이 아니다.** LIS의 길이를 효율적으로 계산하기 위해 길이별 최적의 마지막 값을 기록한다.

## 4. 전체 코드

### 방법 1: 동적 계획법 — `Solution`

```java
package swea.p3307;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            int[] nums = new int[N + 1];
            int[] lens = new int[N + 1];

            for (int i = 1; i <= N; i++) {
                nums[i] = sc.nextInt();
                lens[i] = 1;
            }

            int max = 1;
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        lens[i] = Math.max(lens[i], lens[j] + 1);
                    }
                }
                max = Math.max(max, lens[i]);
            }

            System.out.println("#" + tc + " " + max);
        }

        sc.close();
    }
}
```

### 방법 2: 이분탐색 — `Solution2`

```java
package swea.p3307;

import java.util.ArrayList;
import java.util.Scanner;

public class Solution2 {
    static int getIndex(ArrayList<Integer> lens, int num) {
        int left = 0;
        int right = lens.size() - 1;

        while (left < right) {
            int mid = (left + right) / 2;

            if (lens.get(mid) < num) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            ArrayList<Integer> lens = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                int num = sc.nextInt();

                if (lens.isEmpty() || lens.get(lens.size() - 1) < num) {
                    lens.add(num);
                } else {
                    int idx = getIndex(lens, num);
                    lens.set(idx, num);
                }
            }

            System.out.println("#" + tc + " " + lens.size());
        }

        sc.close();
    }
}
```

## 5. 시간 복잡도

| 풀이 | 시간 복잡도 | 공간 복잡도 | 이유 |
|---|---:|---:|---|
| 동적 계획법 | O(N²) | O(N) | 각 원소에서 그보다 앞선 원소를 모두 확인 |
| 이분탐색 | O(N log N) | O(N) | 각 원소를 처리할 때 최대 한 번 이분탐색 |

입력 크기가 커질수록 이분탐색 풀이가 유리하다. DP 풀이는 점화식이 직관적이고, 이분탐색 풀이는 `lens`가 **길이별 최소 마지막 값**이라는 의미를 이해하는 것이 핵심이다.