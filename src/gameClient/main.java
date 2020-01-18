package gameClient;

import dataStructure.EdgeData;
import gameUtils.Fruit;

public class main {
	public static void main(String[] args) {
		MyGameGUI main = new MyGameGUI();
	}



	public static String decode(String s, int k) {
		String ans = "";
		for (int i = 0; i < s.length(); i++) {	//loop that passes all char in the String
			int temp = s.charAt(i);
			temp = temp + k;
			while (temp > 'z') {	//check if the temp bigger then z 
				temp = temp - 26;
			}
			while (temp < 'a') {	//check if the temp smaller then a
				temp = temp + 26;
			}
			ans = ans + (char)(temp);
		}		
		return ans;
	}
}