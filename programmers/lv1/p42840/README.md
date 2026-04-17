## 핵심 포인트

각 수포자들이 찍는 방식이 반복해서 나타나는 것을 파악하는 것이 중요하다. 반복되는 찍는 방식을 추출하면 다음과 같이 나타난다.

- 1번 수포자 : 1, 2, 3, 4, 5
- 2번 수포자 : 2, 1, 2, 3, 2, 4, 2, 5
- 3번 수포자 : 3, 3, 1, 1, 2, 2, 4, 4, 5, 5

이들을 이용하여 주어진 정답 배열과 값을 비교해야 한다. 1번 수포자가 찍은 방식을 바탕으로 예시를 살펴보자.

- 주어진 answers : [1, 3, 5, 3, 2, 4, 2]
    - answers[0] = 1번 수포자[0] 정답
    - answers[1] ≠ 1번 수포자[1] 오답
    - answers[2] ≠ 1번 수포자[2] 오답
    - answers[3] ≠ 1번 수포자[3] 오답
    - answers[4] ≠ 1번 수포자[4] 오답
    - answers[5] ≠ 1번 수포자[0] 오답
    - answers[6] = 1번 수포자[1] 정답

1번 수포자가 맞춘 답은 총 2개가 된다. 이 예시에서 볼 수 있듯이 5번째 정답, 즉, 수포자가 찍은 답을 전부 살펴보았을 때 아직 비교해야하는 정답이 남아있다면 다시 처음으로 되돌아가서 살피기 시작해야한다. 이를 고려하여 코드를 작성해야 한다.

### 전체 코드

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    private static int[][] no = {
        {1, 2, 3, 4, 5}, 
        {2, 1, 2, 3, 2, 4, 2, 5}, 
        {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
    };

    public Object[] solution(int[] answers) {
        int[] answer = new int[3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < answers.length; j++) {
                if(answers[j] == no[i][j % no[i].length]) answer[i]++;
            }
        }
        
        int max = Math.max(Math.max(answer[0], answer[1]), answer[2]);
        List<Integer> ans = new ArrayList<>();
        for(int i=0; i<3; i++) {
            if(answer[i] == max) ans.add(i+1);
        }
        return ans.toArray();
    }
}
```