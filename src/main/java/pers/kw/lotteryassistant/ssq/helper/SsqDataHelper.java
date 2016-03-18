package pers.kw.lotteryassistant.ssq.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pers.kw.lotteryassistant.ssq.entity.SsqRecord;


public class SsqDataHelper {
	public static final String DEFAULT_URL = "http://tubiao.zhcw.com/tubiao/ssqNew/ssqInc/ssqZongHeFengBuTuAsckj_year=2003.html";
	public static void testConn() {
		String url = "http://tubiao.zhcw.com/tubiao/ssqNew/ssqInc/ssqZongHeFengBuTuAsckj_year=2003.html";
		long start = System.currentTimeMillis();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(5000).get();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Time is:"
					+ (System.currentTimeMillis() - start) + "ms");
		}
		Elements elem = doc.getElementsByTag("Title");
		System.out.println("Title is:" + elem.text());
	}
	
	public static List<SsqRecord> getData(String url,String beginDate,String endDate){
		List<SsqRecord> list = new ArrayList<SsqRecord>();
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(5000).get();

			Element content = doc.getElementById("table_ssq");
			Elements trtag = content.getElementsByTag("tr");

			for (Element tritem : trtag) {
				SsqRecord rec = new SsqRecord();

				Element qhInfo = tritem.getElementsByTag("a").first();

				String rqStr = qhInfo.attr("title");
				String rq = rqStr.substring(rqStr.length() - 10);
				
				if(StringUtils.isNotEmpty(beginDate) && rq.compareTo(beginDate) < 0){
					continue;
				}
				
				if(StringUtils.isNotBlank(endDate) && rq.compareTo(endDate) > 0){
					break;
				}
				rec.setQh(qhInfo.text());
				rec.setRq(rq);

				Elements td = tritem.getElementsByClass("redqiu");

				if (td.size() == 0) {
					continue;
				}

				for (int j = 0; j < td.size(); j++) {
					Element tditem = td.get(j);
					Integer value = Integer.valueOf(tditem.text().toString());

					switch (j) {
					case 0:
						rec.setR1(value);
						break;
					case 1:
						rec.setR2(value);
						break;
					case 2:
						rec.setR3(value);
						break;
					case 3:
						rec.setR4(value);
						break;
					case 4:
						rec.setR5(value);
						break;
					case 5:
						rec.setR6(value);
						break;
					default:
						break;
					}
				}

				Element blue = tritem.getElementsByClass("blueqiu3").get(0);
				rec.setB(Integer.valueOf(blue.text()));

				list.add(rec);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		// testConn();
		//getData();
		List<SsqRecord> list = getData(SsqDataHelper.DEFAULT_URL,"2003-05-02","2003-10-01");
		System.out.println(list.size());
	}

}
