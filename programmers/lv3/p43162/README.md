### 문제 요약

- 컴퓨터의 개수와 2차원 연결 정보가 주어졌을 때, 네트워크의 개수를 반환하는 문제
    - 첫번째 예제는 2개로, {1, 2}, {3}} 으로 나뉘어진다.

  ![image.png](attachment:217e8497-e45d-489d-9b70-f284b0ed4a23:image.png)

    - 두번째 예제는 1개로, {1, 2, 3} 모두 하나의 네트워크로 구성된다.

  ![image.png](attachment:ef7927b5-10ab-4baa-81ca-ac2192f10dd4:image.png)


## 핵심 포인트

하나의 노드에서 탐색을 시작하여 방문한 노드들은 하나의 네트워크에 묶여있다고 볼 수 있다. 즉, dfs를 이용하여 주어진 그래프를 탐색하면 된다. 이를 모든 노드들에 대해 방문할 때까지 반복하고 dfs를 몇 번 시작했는지 카운트하면 네트워크의 개수를 알 수 있다.

다른 예제를 가지고 어떤 식으로 수행하면 되는지 살펴보도록 하자.

![image.png](attachment:c70dc6dc-75b0-4549-a0a5-41c2ce7913c9:ec5c6053-fe24-45e6-abc9-7322a0c6a0b4.png)

다음과 같이 네트워크가 연결되어 있다고 하자. 1번부터 시작하여 노드를 하나씩 살펴보며 dfs 탐색을 수행해보도록 하겠다.

- 1번 노드

  ![image.png](attachment:8536beca-6311-4855-b5a5-c27634746f60:b111716c-908a-4727-adb7-a7263c967ef9.png)

  1번 노드는 2, 3번 노드와 네트워크를 구성하고 있다. 그러므로 dfs를 통해 연결된 노드들을 탐색하고 노드들의 방문 여부를 모두 True로 설정해준다.


- 4번 노드

  ![image.png](attachment:66732b04-df8e-4137-a7c7-212a573bd872:032a64f3-8886-4b30-b4f8-a7ec73dd320f.png)

  2, 3번 노드가 모두 True로 체크되었으니 다음 False인 4번 노드에서 dfs가 시작된다. 4번 노드는 6, 7번 노드와 연결되어 있으므로 이들이 모두 True로 체크된다.

- 5번 노드

  ![image.png](attachment:5550f5d2-cdaf-4d93-ba83-836b54840fd3:78a929cd-1ed9-4c6d-bef8-df24e835b841.png)

  다음 dfs가 시작하는 노드는 5번 노드이다. 5번 노드는 어느 노드와도 연결되어 있지 않으므로 자기 자신만 True로 바꾸고 곧바로 종료된다. 이후 6, 7번은 이미 True로 체크되었으므로 반복문이 완전히 종료된다.


이렇게 총 3번 dfs가 시작되었고, 이는 3개의 네트워크로 구성되어 있다는 것을 보여준다.

위 그림과 같이 처리하도록 코드를 작성해보도록 하겠다. 우선, dfs를 먼저 구현하자. 컴퓨터간 연결 정보가 2차원으로 주어지므로 이를 유의해서 코드를 작성해야 한다.

```java
public void dfs(int i, boolean[] visited, int[][] computers) {
    visited[i] = true;

    for (int j = 0; j < computers[i].length; j++) {
        if (i != j && !visited[j] && computers[i][j] == 1)
            dfs(j, visited, computers);
    }
}
```

위 dfs() 함수를 노드들을 순회하며 한번도 방문하지 않은 노드를 시작점으로 삼아 탐색을 시작하도록 하면 된다.

```java
public int solution(int n, int[][] computers) {
    int answer = 0;

    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            answer++;
            dfs(i, visited, computers);
        }
    }

    return answer;
}
```

### 전체 코드

```java
class Solution {
    public void dfs(int i, boolean[] visited, int[][] computers) {
        visited[i] = true;

        for (int j = 0; j < computers[i].length; j++) {
            if (i != j && !visited[j] && computers[i][j] == 1)
                dfs(j, visited, computers);
        }
    }

    public int solution(int n, int[][] computers) {
        int answer = 0;

        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                answer++;
                dfs(i, visited, computers);
            }
        }

        return answer;
    }
}
```