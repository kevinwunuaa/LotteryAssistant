package pers.kw.lotteryassistant.ssq.algorithm;

import java.util.List;
import java.util.Map;

import pers.kw.lotteryassistant.ssq.entity.SsqRecord;

public interface MinorTrendProcessor {
	public Map<String,Integer> calcResult(List<SsqRecord> records);
}
