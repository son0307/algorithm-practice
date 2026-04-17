## 문제 요약

- 체육복을 도난당한 학생, 여벌이 있는 학생 리스트가 주어졌을 때, 체육복을 빌림으로써 수업을 들을 수 있는 학생 수의 최댓값을 구하는 문제
    - 여벌 체육복이 있는 학생은 자기 번호의 바로 앞번호, 뒷번호 학생에게 빌려줄 수 있다.
    - 여벌 체육복이 있지만 도난 당한 학생은 다른 학생에게 빌려줄 수 없다.

## 핵심 포인트

이 문제는 빌려줄 수 있는 체육복을 가진 학생을 도출해낸 다음, 해당 학생을 기준으로 앞, 뒷번호 학생이 체육복을 도난당했는지 검사하는 방식으로 해결할 수 있다. 특정 원소를 포함하고 있는지 검사할 때 $O(1)$의 속도로 수행하는 HashSet 자료구조를 이용하면 효율적으로 검색을 수행할 수 있다.

또한, 빌려주는 학생을 검사하는 순서를 정하는 것이 필요하다. 자신보다 앞번호 학생에게 빌려줄 수 있는지를 검사하는 것이 더 우선되어야 하는데, 그 이유는 뒷번호의 학생은 다음 학생으로부터 빌릴 수 있는 가능성이 있기 때문이다. 따라서, 풀이 순서를 정의하면 다음과 같다.

1. 여분이 있는 학생 중 도난당한 학생이 있는지 검사한다. 이 학생은 여분이 있는 학생에서 제외시킨다.
2. 여분이 있는 학생의 앞, 뒷번호 학생이 도난당했는지 검사한다.
    1. 앞번호 학생을 먼저 검사하고, 뒷번호 학생을 검사한다.

### 전체 코드

```java
import java.util.HashSet;

class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        HashSet<Integer> lostSet = new HashSet<>();
        HashSet<Integer> reserveSet = new HashSet<>();
        
        for (int i : lost)
            lostSet.add(i);
        for (int j : reserve) {
            if (lostSet.contains(j))
                lostSet.remove(j);
            else
                reserveSet.add(j);
        }

        for (int j : reserveSet) {
            if (lostSet.contains(j - 1))
                lostSet.remove(j - 1);
            else if (lostSet.contains(j + 1))
                lostSet.remove(j + 1);
        }

        return n - lostSet.size();
    }
}
```

이 알고리즘의 시간 복잡도는 $O(N)$이다. (N은 전체 학생 수).

- lost 배열, reserve 배열을 순회하며 중복 제거 → $O(L + R)$
- 여벌 체육복이 있는 학생들을 순회하며 빌려주는 과정 → $O(R)$

$L$과 $R$은 전체 학생 수 $N$ 이하이므로, 시간 복잡도는 $O(N)$에 수렴한다.