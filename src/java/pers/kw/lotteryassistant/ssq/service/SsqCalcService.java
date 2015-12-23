package pers.kw.lotteryassistant.ssq.service;

import java.util.List;
import java.util.Map;

import pers.kw.lotteryassistant.ssq.algorithm.MinorTrendProcessor;
import pers.kw.lotteryassistant.ssq.entity.SsqRecord;

public interface SsqCalcService {
	public String calcCountOfNum();
	public String calcMiss();
	public Map<String,Integer> calcMinorTrend(List<SsqRecord> records,MinorTrendProcessor processor);
	public boolean exist(Integer []record);
}
