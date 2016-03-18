package pers.kw.lotteryassistant.ssq.app;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import pers.kw.lotteryassistant.ssq.service.SsqCalcService;
import pers.kw.lotteryassistant.ssq.service.SsqDataService;
import pers.kw.lotteryassistant.util.DataUtil;

public class SsqClient {
	public static ClassPathXmlApplicationContext context;
	
	static{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}
	
	public static void initDB(){
		/**
		 * 初始化基本表
		 */
		SsqDataService service = (SsqDataService) context.getBean("ssqDataService");
		service.initDB();
	}
	
	public static void initData(){
		SsqDataService service = (SsqDataService) context.getBean("ssqDataService");
		System.out.println("初始化数据 条数: " + service.initData());
	}
	
	public static void synData(){
		SsqDataService service = (SsqDataService) context.getBean("ssqDataService");
		System.out.println("同步数据 条数: " + service.synData());
	}
	
	public static void calcCountOfNum(){
		SsqCalcService service = (SsqCalcService) context.getBean("ssqCalcService");
		System.out.println("当前期号: " + service.calcCountOfNum());
	}
	
	public static void calcMiss(){
		SsqCalcService service = (SsqCalcService) context.getBean("ssqCalcService");
		System.out.println("当前期号: " + service.calcMiss());
	}
	
	public static boolean exist(String recordStr){
		SsqCalcService service = (SsqCalcService) context.getBean("ssqCalcService");
		String []r = recordStr.split(",");
		Integer []record = new Integer[r.length];
		for(int i = 0; i < r.length; i ++){
			record[i] = Integer.parseInt(r[i]);
		}
		return service.exist(record);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initDB();
		synData();
		calcMiss();
		calcCountOfNum();
		
//		Integer a[] = {2,6,7,8,23,25,33};
//		List<String> result = DataUtil.mInN(a, 7, 6,0);
//		for(String r : result){
//			if(exist(r)){
//				System.out.println(r + " is find.");
//			}
//		}
	}

}
