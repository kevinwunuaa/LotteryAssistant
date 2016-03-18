package pers.kw.lotteryassistant.test;

import java.util.ArrayList;
import java.util.List;

import pers.kw.lotteryassistant.util.DataUtil;

public class TestData {
	public static void main(String[] args) {
		Integer a[] = {2,6,7,8,23,25,33};
		List<String> result = DataUtil.mInN(a, 7, 6,0);
		for(String r : result){
			System.out.println(r);
		}
		
//		Integer s[] = {1,2,17,22,26,27},t[] = {1,3,5,20,21,31};
//		List<Integer> els = new ArrayList<Integer>();
//		System.out.println("相同元素个数" + DataUtil.countSharedEl(s, t, null));
		//System.out.println(els);
	}
}
