import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		int n = 5;
		int[] money = {1,2,5}; // return 4
		
		System.out.println(new Solution().solution(n, money));
	}

}

class Solution {
	public int solution(int n, int[] money) {
		int[] dp = new int[n+1];
		Arrays.sort(money); // order by money asec;
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
	
}


class SolutionRecursive {
	int[] m;
	int count = 0;
	public int solution(int n, int[] money) {
		m = money;
		Arrays.sort(money); // order by money asec;
		
		countPaymentCase(n, money.length-1);
		return count;
	}
	
	private void countPaymentCase(int cost, int index) {

		if (cost == 0) {
			count = (count+1)%1000000007;
			return;
		}
		
		if (index == -1)
			return;
		
		if (cost >= m[index]) 
			countPaymentCase(cost-m[index], index);
		
		countPaymentCase(cost, index-1);
	}
	
	
}