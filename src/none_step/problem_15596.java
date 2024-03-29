/*
 * @since 2021-10-17
 * User https://www.acmicpc.net/user/nect2r
 * Blog https://nect2r.tistory.com/
 * Github https://github.com/nect2r/BAEKJOON
 * Change the class name to Main
 * Delete the package
 * Run
 */
package none_step;

public class problem_15596 {
	public static void main(String[] args) {
		boolean[] check = new boolean[10001];	// 1부터 10000이므로
		
		for(int i=1; i<10001; i++) {
			int n = d(i);
			
			if(n < 10001) {
				check[n] = true;
			}
		}
		
		StringBuilder sb = new StringBuilder();
        
		for (int i = 1; i < 10001; i++) {
			if (!check[i]) {	// false 인 인덱스만 출력
				sb.append(i).append('\n');
			}
		}
		System.out.println(sb);
	}

	private static int d(int number) {
		int sum = number;
		
		while(number != 0){
			sum = sum + (number % 10); // 첫 째 자리수
			number = number/10;	// 10을 나누어 첫 째 자리를 없앤다
		}
		
		return sum;
	}
}
