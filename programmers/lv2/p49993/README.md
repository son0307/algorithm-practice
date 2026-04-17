## 문제 요약

- 선행 스킬 순서(skill)와 유저들이 만든 스킬트리 목록(skill_trees)이 주어졌을 때, 올바른 스킬트리의 개수를 반환
    - 선행 스킬은 주어진 순서대로 배워야 한다.
    - 선행 스킬 목록에 없으면 영향 x

## 핵심 포인트

이 문제의 핵심은 **스킬 트리에 등장한 스킬들이 선행 스킬 순서에 맞게 나왔는가?**를 확인하는 것이다. 이를 확인하기 위해 현재 배워야할 선행 스킬을 가리키는 커서를 만들고, 스킬 트리에 등장한 스킬들을 하나씩 살펴보며 선행 스킬 순서에 등장한 스킬이 나타나면 커서가 이를 가리키고 있는지를 검사하면 된다. 정리하자면 다음과 같다.

- **커서** : 선행 스킬 중 현재 배워야 할 스킬의 위치를 가리킨다. 초기값은 0으로, skill 배열의 가장 첫번째 스킬을 가리키도록 한다.
- **스킬트리 검증** : 유저의 스킬트리를 문자 단위로 순회하며 조건에 맞는지 검증한다.
    - 선행 스킬 목록에 없는 경우 : 순서와 무관하므로 건너띈다.
    - 선행 스킬 목록에 있는 경우
        - 커서가 가리키고 있는 현재 배워야할 선행 스킬과 일치하면 올바른 순서이므로, 커서를 다음 칸으로 옮기고 탐색을 계속한다.
        - 커서가 가리키고 있는 현재 배워야할 선행 스킬과 일치하지 않으면 잘못된 스킬 트리이므로 불가능하다고 판단한다.

### 전체 코드

```java
class Solution {
    private boolean inspect(String skill, String skill_tree) {
        int skillIndex = 0;

        for (char c : skill_tree.toCharArray()) {
            int index = skill.indexOf(c);

            if (index == -1) {
                continue;
            }

            if (index == skillIndex)
                skillIndex++;
            else
                return false;
        }

        return true;
    }

    public int solution(String skill, String[] skill_trees) {
        int answer = 0;

        for (String tree : skill_trees) {
            if (inspect(skill, tree))
                answer++;
        }

        return answer;
    }
}
```

이 코드의 시간 복잡도는 $O(N *M*K)$ 이다. (N: 스킬트리 개수, M: 스킬트리 길이, K: 선행 스킬 길이). 시간 복잡도가 복잡해보이지만 입력값들의 범위가 매우 작기 때문에 큰 어려움 없이 시간 내에 문제를 해결할 수 있다.