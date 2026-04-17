## 문제 요약

- 비내림차순으로 정렬된 수열에서 합이 K인 부분 수열을 찾아내는 문제
    - 합이 k인 부분 수열이 여러 개면 길이가 짧은 것을 반환
    - 길이까지 동일하다면 인덱스가 작은 부분 수열을 반환

## 핵심 포인트

이 문제는 주어진 수열 내에서 합이 k인 부분 수열이 여러 개 나올 수 있으므로 처음부터 끝까지 탐색하는 완전 탐색 방식을 이용해야 한다. 또한, 문제의 목표가 부분 수열을 찾아내는 것이므로 부분 수열의 시작과 끝을 지정하는 두 개의 포인터를 이용해야 한다. 각 포인터의 증가 조건은 다음과 같다.

- 현재 부분 수열의 합이 k보다 크면 시작 포인터를 움직여 부분 수열의 크기를 줄인다.
- 현재 부분 수열의 합이 k보다 작으면 끝 포인터를 움직여 부분 수열의 크기를 키운다.
- 현재 부분 수열의 합이 k와 같으면 현재 길이가 저장된 최소 길이보다 짧으면 정답을 갱신한다.

인덱스가 작은 곳부터 탐색을 진행하기에 길이가 동일하다면 인덱스가 작은 부분 수열이 더 우선적으로 탐색된다. 따라서, 이 조건은 자체적으로 만족된다.

### 전체 코드

```java
class Solution {
    public int[] solution(int[] sequence, int k) {
        int[] answer = new int[2];

        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < sequence.length; right++) {
            sum += sequence[right];

            while (sum > k && left <= right) {
                sum -= sequence[left++];
            }

            if (sum == k) {
                int currentLength = right - left;

                if (currentLength < minLength) {
                    minLength = currentLength;
                    answer[0] = left;
                    answer[1] = right;
                }
            }
        }

        return answer;
    }
}
```

이 코드의 시간복잡도는 $O(N)$이다. 주어진 sequence 배열을 처음부터 딱 1번씩만 탐색하기에 해당 배열의 길이만큼의 시간 복잡도를 가지게된다. 또한, 끝 포인터가 배열의 끝에 도달했는데도 그 합이 k보다 작으면 더 이상 조건을 만족할 수 없으므로 조기 종료시켜 최적화도 진행된다.