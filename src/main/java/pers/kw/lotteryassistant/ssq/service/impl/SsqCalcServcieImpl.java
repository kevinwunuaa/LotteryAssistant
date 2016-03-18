package pers.kw.lotteryassistant.ssq.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import pers.kw.lotteryassistant.dao.BaseDao;
import pers.kw.lotteryassistant.ssq.algorithm.MinorTrendProcessor;
import pers.kw.lotteryassistant.ssq.entity.GlobalConfig;
import pers.kw.lotteryassistant.ssq.entity.SsqMiss;
import pers.kw.lotteryassistant.ssq.entity.SsqNum;
import pers.kw.lotteryassistant.ssq.entity.SsqRecord;
import pers.kw.lotteryassistant.ssq.service.SsqCalcService;

@Service("ssqCalcService")  
public class SsqCalcServcieImpl implements SsqCalcService {
	
	@Resource
	private BaseDao<Object> baseDao;
	
	/**
	 * 计算各数字在所有开奖记录中出现的次数
	 */
	@Override
	public String calcCountOfNum() {
		List cfgs = baseDao.find("select c from GlobalConfig c where c.configCode = 'SSQ_NUM_CALC_STATUS' and c.validity = 1");
		if(cfgs == null || cfgs.size() <= 0){
			return null;
		}
		GlobalConfig statusCfg = (GlobalConfig)cfgs.get(0);
		
		String qh = statusCfg.getConfigValue();
		
		String hql = "select t from SsqRecord t where t.qh > ? order by t.qh";
		
		List records = null;
		
		if(StringUtils.isEmpty(qh)){
			for(Integer n = 1; n <= 33; n ++){
				SsqNum num = new SsqNum();
				num.setN(n);
				num.setC(0);
				baseDao.saveOrUpdate(num);
			}
			records = baseDao.find("select t from SsqRecord t order by t.qh");
		}else{
			records = baseDao.find(hql,new Object[]{qh});
		}
		
		if(records == null || records.size() <= 0){
			return qh;
		}
		
		List ns = baseDao.find("select n from SsqNum n");
		
		Map<Integer,Integer> nMap = new HashMap<Integer,Integer>();
		
		for(Object on : ns){
			SsqNum n = (SsqNum)on;
			nMap.put(n.getN(),n.getC());
		}
		
		for(Object record : records){
			SsqRecord r = (SsqRecord)record;
			
			nMap.put(r.getR1(), nMap.get(r.getR1()) + 1);
			nMap.put(r.getR2(), nMap.get(r.getR2()) + 1);
			nMap.put(r.getR3(), nMap.get(r.getR3()) + 1);
			nMap.put(r.getR4(), nMap.get(r.getR4()) + 1);
			nMap.put(r.getR5(), nMap.get(r.getR5()) + 1);
			nMap.put(r.getR6(), nMap.get(r.getR6()) + 1);

			qh = r.getQh();
		}
		
		for(Map.Entry<Integer, Integer> e : nMap.entrySet()){
			baseDao.executeHql("update SsqNum n set n.c = ? where n.n = ?", new Object[]{e.getValue(),e.getKey()});
		}
		
		if(StringUtils.isNotEmpty(qh)){
			statusCfg.setConfigValue(qh);
			baseDao.saveOrUpdate(statusCfg);
		}
		
		return qh;
	}



	/**
	 * 计算各期数字遗漏情况,计算截止时的期号
	 */
	@Override
	public String calcMiss() {
		String curQh = (String) baseDao.uniqueResult("select max(t.qh) from SsqMiss t");
		if(curQh == null){
			SsqMiss mi = new SsqMiss();
			mi.setQh("00000");
			JSONObject o = new JSONObject();
			for(Integer n = 1; n <= 33; n ++){
				o.put(n, 1000);
			}
			
			mi.setMissInfo(o.toString());
			baseDao.save(mi);
			curQh = mi.getQh();
		}
		
		SsqMiss curMi = (SsqMiss) baseDao.get("select t from SsqMiss t where t.qh = ?", new Object[]{curQh});

		String hql = "select t from SsqRecord t where t.qh > ? order by t.qh";
		
		List records = baseDao.find(hql,new Object[]{curQh});
		
		if(records == null || records.size() <= 0){
			return null;
		}
		
		for(Object record : records){
			SsqRecord r = (SsqRecord)record;
			SsqMiss nextMi = new SsqMiss();
			try {
				BeanUtils.copyProperties(nextMi, curMi);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			nextMi.setQh(r.getQh());
			JSONObject mi = JSONObject.fromObject(curMi.getMissInfo());
			for(Object key : mi.keySet()){
				Object value = mi.get(key);
				mi.put(key, (Integer)value + 1);
			}
			
			mi.put(r.getR1(), 0);
			mi.put(r.getR2(), 0);
			mi.put(r.getR3(), 0);
			mi.put(r.getR4(), 0);
			mi.put(r.getR5(), 0);
			mi.put(r.getR6(), 0);
			
			nextMi.setMissInfo(mi.toString());
			
			baseDao.save(nextMi);
			
			curMi = nextMi;
		}
		
		return curMi.getQh();
	}



	@Override
	public Map<String, Integer> calcMinorTrend(List<SsqRecord> records,
			MinorTrendProcessor processor) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean exist(Integer[] record) {
		String hql = "select t from SsqRecord t where t.r1 = ? and t.r2 = ? and t.r3 = ? and t.r4 = ? and t.r5 = ? and t.r6 = ?";
		List list = baseDao.find(hql,record);
		return list != null && list.size() > 0;
	}
	
}
