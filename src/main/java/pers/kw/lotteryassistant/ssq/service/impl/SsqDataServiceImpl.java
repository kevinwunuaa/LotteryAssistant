package pers.kw.lotteryassistant.ssq.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import freemarker.template.utility.StringUtil;

import antlr.StringUtils;

import pers.kw.lotteryassistant.dao.BaseDao;
import pers.kw.lotteryassistant.ssq.dao.GlobalConfigDao;
import pers.kw.lotteryassistant.ssq.dao.SsqRecordDao;
import pers.kw.lotteryassistant.ssq.entity.GlobalConfig;
import pers.kw.lotteryassistant.ssq.entity.SsqRecord;
import pers.kw.lotteryassistant.ssq.helper.SsqDataHelper;
import pers.kw.lotteryassistant.ssq.service.SsqDataService;
import pers.kw.lotteryassistant.util.DataUtil;

@Service("ssqDataService")  
public class SsqDataServiceImpl implements SsqDataService {
	private static final Logger log = Logger.getLogger(SsqDataServiceImpl.class);
	
	@Resource
	private BaseDao<Object> baseDao;
	
	@Resource
	private GlobalConfigDao globalConfigDao;
	
	@Resource 
	private SsqRecordDao ssqRecordDao;
	
	@Override
	public void initDB() {
		/**
		 * [ssq_sum]锟斤拷值锟斤拷锟绞硷拷锟�
		 */
		List<GlobalConfig> cs = globalConfigDao.find("select c from GlobalConfig c where c.configCode = 'SSQ_DB_INIT_STATUS' and c.validity = 1");
		if(cs == null || cs.size() <= 0){
			return ;
		}
		GlobalConfig initStatus = cs.get(0);
		if(initStatus.getConfigValue().equals("1")){
			System.err.println("锟斤拷锟斤拷要锟截革拷锟斤拷始锟斤拷DB锟斤拷");
			return ;
		}
		
		Integer a[] = {1,2,3,4,5,6,7,8,9,10,11,
				12,13,14,15,16,17,18,19,20,21,22,
				23,24,25,26,27,28,29,30,31,32,33};
		Map<Integer,Integer> result = DataUtil.sumOfmInN(a, 33, 6);
		for(Map.Entry<Integer, Integer> e : result.entrySet()){
			System.out.println(e.getKey() + " = " + e.getValue());
			baseDao.updateBySql("insert into ssq_sum(s,c) values(?,?)", e.getKey(),e.getValue());
		}
		
		initStatus.setConfigValue("1");
		globalConfigDao.update(initStatus);
		
		log.info("END initDB.");
	}

	@Override
	public int initData() {
		int rowCount = 0;
		List<GlobalConfig> cs = globalConfigDao.find("select c from GlobalConfig c where c.configCode = 'SSQ_DATA_INIT_STATUS' and c.validity = 1");
		if(cs == null || cs.size() <= 0){
			return rowCount;
		}
		GlobalConfig initStatus = cs.get(0);
		if(initStatus.getConfigValue().equals("1")){
			System.err.println("锟斤拷锟斤拷要锟截革拷锟斤拷始锟斤拷DATA锟斤拷");
			return rowCount;
		}
		
		cs = globalConfigDao.find("select c from GlobalConfig c where c.configCode = 'SSQ_DATA_URL_EXPR' and c.validity = 1");
		if(cs == null || cs.size() <= 0){
			return rowCount;
		}
		GlobalConfig dataUrl = cs.get(0);
		
		String urlExpr = dataUrl.getConfigValue();
		
		for(int year = 2003; year <= 2015; year ++){
			String url = urlExpr.replace("{YEAR}", "" + year);
			List<SsqRecord> list = SsqDataHelper.getData(url, null, null);
			if(list == null || list.size() <= 0){
				continue;
			}
			
			for(SsqRecord r : list){
				ssqRecordDao.saveOrUpdate(r);
			}
			rowCount += list.size();
		}
		
		initStatus.setConfigValue("1");
		globalConfigDao.update(initStatus);
		
		return rowCount;
	}

	@Override
	public int synData() {
		int rowCount = 0;
		List<GlobalConfig> cs = globalConfigDao.find("select c from GlobalConfig c where c.configCode = 'SSQ_DATA_INIT_STATUS' and c.validity = 1");
		if(cs == null || cs.size() <= 0){
			return rowCount;
		}
		GlobalConfig config = cs.get(0);
		if(config.getConfigValue().equals("0")){
			return initData();
		}
		
		cs = globalConfigDao.find("select c from GlobalConfig c where c.configCode = 'SSQ_DATA_URL_EXPR' and c.validity = 1");
		if(cs == null || cs.size() <= 0){
			return rowCount;
		}
		GlobalConfig dataUrl = cs.get(0);
		
		String urlExpr = dataUrl.getConfigValue();
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String curYear = sdf.format(d);
		
		String url = urlExpr.replace("{YEAR}", curYear);
		
		String beginDate = null;
		List<SsqRecord> list = ssqRecordDao.find("select s from SsqRecord s order by s.rq desc", new Object[]{}, null, null);
		if(list == null || list.size() <= 0){
			beginDate = null;
		}
		else{
			SsqRecord r = list.get(0);
			String rq = r.getRq();
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = sdf.parse(rq);
				beginDate = sdf.format(new Date(d.getTime() + (long)24 * 60 * 60 * 1000));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		list = SsqDataHelper.getData(url, beginDate, null);
		
		if(list == null || list.size() <= 0){
			return rowCount;
		}
		
		for(SsqRecord r : list){
			ssqRecordDao.saveOrUpdate(r);
		}
		
		baseDao.executeHql("update SsqRecord r set r.num = r.r1 + r.r2 + r.r3 + r.r4 + r.r5 + r.r6 where r.num is null");

		return list.size();
	}

}
