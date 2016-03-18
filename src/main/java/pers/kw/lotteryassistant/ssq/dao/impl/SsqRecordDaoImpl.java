package pers.kw.lotteryassistant.ssq.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import pers.kw.lotteryassistant.dao.impl.BaseDaoImpl;
import pers.kw.lotteryassistant.ssq.dao.SsqRecordDao;
import pers.kw.lotteryassistant.ssq.entity.SsqRecord;

@Repository("ssqRecordDao")
public class SsqRecordDaoImpl extends BaseDaoImpl<SsqRecord> implements
		SsqRecordDao {
}
