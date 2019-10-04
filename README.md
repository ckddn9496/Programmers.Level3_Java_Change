# Programmers.Level3_Java_Change

## 프로그래머스 > 연습문제 > 거스름돈

### 1. 문제설명

문제: https://programmers.co.kr/learn/courses/30/lessons/12907

input으로 거슬러 주려는 돈 `n` 과 거스름돈의 화폐의 단위 종류를 담은 정수형 배열 `money`가 들어온다. 돈 `n`원을 거슬러 줄 방법의 수를 return하는 문제.

* 제한사항
> 정답이 커질 수 있으니 1,000,000,007로 나눈 나머지를 return 해주세요.

### 2. 풀이

#### 1. 재귀적 방법 - 정확도 100%, 효율성 1/6

money를 액수에 따라 정렬한 후, 큰 화폐단위의 `index`와 거슬러 주어야 할 액수 `cost`를 재귀함수에 넘겨준다.
`cost==0`이면 액수에 맞게 거슬러 준 것이므로 `count++`을 하고 재귀를 마친다. `index == -1`에 대한 제어문은 나중에 설명하겠다. `cost >= money[index]`일 경우 현재의 화폐단위 보다 거스름돈의 액수가 크기 때문에 `cost-money[index]`로 다시 재귀함수를 돌린다. 또한 해당 화폐단위를 사용하지 않고 다음으로 낮은 화폐단위를 이용하여 계산할 수도 있다. `cost`는 그대로 둔 상태로 `index - 1`을 넘겨주어 재귀함수를 돌린다. 위과정으로 나중에 설명한다고 한 `index == -1`의 경우는 이렇게 화폐단위를 낮추다가 더 이상 낮은 화폐단위가 없을 때 상황을 의미하며 재귀를 멈춘다. 

```java
private void countPaymentCase(int cost, int index) {

  if (cost == 0) {
    count = (count+1)%1000000007;
    return;
  }

  if (index == -1)
    return;

  if (cost >= money[index]) 
    countPaymentCase(cost-money[index], index);

  countPaymentCase(cost, index-1);
}
```

### 2. DP - 정확도, 효율성 100%

역시 먼저 money를 정렬한다. `int[] dp = new int[n+1]`를 만든다. 이때 `dp[k]`는 거스름돈이 `k`원일때 거슬러 줄 수 있는 방법에 대한 경우의 수 이다. 이 `dp`배열은 `money.length`만큼 반복하며 만들어 진다. 매 반복마다 `1`부터 `i` 개의 money를 이용한 거스름돈의 경우를 저장하며 `dp[k]`에 대해`i+1`번째 거스름돈은 `k원을 i번까지의 money를 이용하여 거슬러 줄 경우의 수` + `k-money[i+1]를 i+1번까지의 money를 이용하여 거슬러 줄 경우의 수`이다. 이해가 잘 가지 않는다면 직접 표를 그려 채우면서 값을 보면 이해가 갈 것이다.

```java
public int solution(int n, int[] money) {
  int[] dp = new int[n+1];
  Arrays.sort(money);
  dp[0] = 1;
  for (int i = money[0]; i < dp.length; i+=money[0]) {
    dp[i] = 1;
  }

  for (int mnIdx = 1; mnIdx < money.length; mnIdx++) {
    for (int dpIdx = 0; dpIdx < dp.length; dpIdx++) {
      if (money[mnIdx] <= dpIdx) {
        dp[dpIdx] = (dp[dpIdx] + dp[dpIdx-money[mnIdx]]) % 1000000007;
      }
    }
  }

  return dp[n];
}
```

### 3. 어려웠던 점

dp배열를 초기화 할 때, 거스름돈이 0원이면 거슬러줄 방법은 1개이며 1원부터 n원까지 거슬러 줄 방법은 한 가지 화폐만을 (`money[0]`)을 이용하여 거슬러 줄 것이기 때문에 모두 1로 초기화 해주었다. 즉, 첫 시작시 모든 배열을 1로 초기화 해주었다. 하지만, 예를들어 거스름돈이 `3`원이 남았고 가장 작은 화폐단위가 `2`원 이라면 거슬러 줄 수 있는 방법은 0개이다. 테스트 케이스에서 화폐의 단위 중 최소가 `1`이었기 문제점을 빠르게 알지 못하였다. 최소 화폐단위로 거스름돈이 0원이 되도록 만들어 줄 수 있을때만 1로 초기화 해주어 문제를 해결하였.

```java
for (int i = money[0]; i < dp.length; i+=money[0]) {
  dp[i] = 1;
}
```
