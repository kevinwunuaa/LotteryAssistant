package pers.kw.lotteryassistant.ssq.dao.impl;

import org.springframework.stereotype.Repository;

import pers.kw.lotteryassistant.dao.impl.BaseDaoImpl;
import pers.kw.lotteryassistant.ssq.dao.GlobalConfigDao;
import pers.kw.lotteryassistant.ssq.entity.GlobalConfig;

@Repository("globalConfigDao")
public class GlobalConfigDaoImpl extends BaseDaoImpl<GlobalConfig> implements GlobalConfigDao {

}
