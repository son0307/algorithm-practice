## 문제 요약

- 주어진 원형 수열에서 연속된 부분 수열의 합으로 만들 수 있는 수의 합의 개수를 반환
    - 원형 수열이므로 마지막 원소와 첫번째 원소는 연결된 상태
    - 길이가 1인 부분 수열부터 길이가 $N$인 부분 수열까지 모든 경우의 합을 구한다.

## 핵심 포인트

이 문제의 핵심은 **원형으로 연결된 구조를 어떻게 코드로 구현할 것인가?**이다. 이 구조를 해석하려면 모듈러 연산(%)을 이용하여 인덱스를 순환시키는 방법도 있지만, **수열을 복사하여 기존 배열 뒤에 붙이는 방법**을 이용하면 단순 슬라이싱을 통해 처리할 수 있다.

예를 들어, 4개의 원소를 가지는 원형 배열이 있다고 하자. 이 때, 길이가 3인 부분 수열은 다음과 같이 나타난다.

![image.png](attachment:0d50a4d6-822c-49bd-a854-ffa4dd2870da:image.png)

3, 4번째 경우는 모듈러 연산을 통해 0, 1번째에 위치한 원소를 선택한다. 그렇다면 수열을 복사하여 뒤에 붙인 경우는 어떨까?

![image.png](attachment:2d8a2e50-f61c-4c0a-94fb-f050c85d06fa:image.png)

이 경우는 별도의 연산 없이 뒤로 이동시키기만 하면 된다. 이를 통해 더욱 직관적이게 문제를 해결할 수 있다.

### 전체 코드

```java
import java.util.HashSet;

class Solution {
    public int solution(int[] elements) {
        HashSet<Integer> set = new HashSet<>();

        int[] newElements = new int[elements.length * 2];
        for (int i = 0; i < newElements.length; i++) {
            newElements[i] = elements[i % elements.length];
        }

        for (int i = 0; i < elements.length; i++) {
            int sum = 0;
            for (int j = 0; j < elements.length; j++) {
				        sum += newElements[i + j]; 
				        set.add(sum);
				    }
        }

        return set.size();
    }
}
```

이 코드의 시간 복잡도는 $O(N^2)$이다. 시작점을 정하는 루프(N)과 길이 루프(N) 두개가 겹친 이중 반복문으로 시간 복잡도가 결정된다.

이렇게 배열의 길이를 늘리는 방법은 직관적으로 문제를 해결할 수 있도록 해주지만 배열의 길이만큼 공간을 더 사용하는 것이기에 메모리 사용량에 대해 주의해야한다.