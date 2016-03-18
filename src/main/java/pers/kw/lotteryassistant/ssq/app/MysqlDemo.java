package pers.kw.lotteryassistant.ssq.app;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import pers.kw.lotteryassistant.ssq.dao.GlobalConfigDao;
import pers.kw.lotteryassistant.ssq.entity.GlobalConfig;

public class MysqlDemo {

	public static void main(String[] args) throws Exception {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		GlobalConfigDao dao = (GlobalConfigDao) context.getBean("globalConfigDao");
		List<GlobalConfig>  list = dao.find("select c from GlobalConfig c where t.id = 2");
		GlobalConfig c = list.get(0);
		c.setConfigValue("1");
		dao.update(c);

	}

}
