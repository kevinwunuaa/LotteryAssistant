package pers.kw.lotteryassistant.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
	/**
	 * 计算n个数字中m个数字的组合形成的和值及其出现次数
	 * @param arr ： 待选数字
	 * @param n ：待选数字个数
	 * @param m : 选出的个数
	 * @return Map<Integer,Integer> : key->和值,value->出现次数
	 */
	public static Map<Integer,Integer> sumOfmInN(Integer arr[],int n,int m){
		Map<Integer,Integer> sumMap = new HashMap<Integer,Integer>();
		List<String> result = mInN(arr,n,m,0);
		for(String r : result){
			String []values = r.split(",");
			Integer sum = 0;
			for(int i = 0; i < values.length;i ++){
				sum += Integer.parseInt(values[i]);
			}
			Integer count = sumMap.get(sum);
			if(count != null){
				count ++;
			}
			else {
				count = 1;
			}
			sumMap.put(sum, count);
		}
		return sumMap;
	}
	
	/**
	 * 计算n个数字中选出m个的所有组合
	 * @param arr
	 * @param n
	 * @param m
	 * @param pos
	 * @return
	 */
	public static List<String> mInN(Integer arr[],int n,int m,int pos){
		if(m == 1){
			List<String> result = new ArrayList<String>();
			for(int i = pos; i < n; i ++){
				result.add(arr[i] + "");
			}
			return result;
		}
		
		List<String> all = new ArrayList<String>();
		
		for(int j = pos; j < n - m + 1; j ++){
			Integer cur = arr[j];
			List<String> result = mInN(arr,n,m-1,j+1);
			for(String r : result){
				all.add(cur + "," + r);
			}
		}
		
		return all;
	}
	
	/**
	 * 求s和t两个升序数组的共同元素
	 * @param s
	 * @param t
	 * @param sharedEls
	 * @return
	 */
	public static int countSharedEl(Integer []s,Integer []t,List<Integer> sharedEls){
		int len = s.length;
		int count = 0;
		
		for(int i = 0; i < len; i ++){
			for(int j = 0; j < len; j ++){
				if(s[i] == t[j]){
					count ++;
					if(sharedEls != null){
						sharedEls.add(s[i]);
					}
					break;
				}
			}
		}
		
		return count;
	}
}
