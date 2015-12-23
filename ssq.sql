create table global_config(
	id integer primary key auto_increment,
	config_code varchar(50) comment '配置标识',
	config_value varchar(200) comment '配置值',
	validity int default 1 comment '开关状态'
)comment = '全局配置';

alter table global_config add(config_desc varchar(200));

/**基本数据表初始化*/
insert into global_config(config_code,config_value)
value('SSQ_DB_INIT_STATUS','0');

/** 数据来源url模式*/
insert into global_config(config_code,config_value)
values('SSQ_DATA_URL_EXPR','http://tubiao.zhcw.com/tubiao/ssqNew/ssqInc/ssqZongHeFengBuTuAsckj_year={YEAR}.html');

/** 数据初始化状态*/
insert into global_config(config_code,config_value)
value('SSQ_DATA_INIT_STATUS','0');

/** 中奖数字出现情况计算状态*/
insert into global_config(config_code,config_value,config_desc)
value('SSQ_NUM_CALC_STATUS',NULL,'记录中奖数字出现次数情况【配置值表示当前计算有效期号】');

update global_config set config_value = NULL where config_code = 'SSQ_NUM_CALC_STATUS';


create table ssq_record(
	qh varchar(12) primary key comment '期号',
	rq varchar(12) comment '开奖日期',
	r1 integer(2),
	r2 integer(2),
	r3 integer(2),
	r4 integer(2),
	r5 integer(2),
	r6 integer(2),
	b integer(2),
	num integer(3)
)comment='双色球开奖记录';

create table ssq_sum(
	s Integer(3) primary key comment '和值',
	c Integer(6) comment '出现次数',
	p Float(5,3) comment '百分比',
	c1 integer(5) comment '实际出现次数',
	p1 Float(5,3) comment '实际出现占比'
)comment='双色球理论和值记录';

drop table if exists ssq_num;
create table ssq_num(
	n Integer(2) primary key comment '中奖数字',
	c Integer(5) comment '中奖次数',
	p Float(5,3) comment '所占百分比'
)comment='中奖数字情况表';

drop table if exists ssq_miss;

create table ssq_miss(
	qh varchar(12) primary key comment '期号',
	miss_info text comment 'json格式的遗漏情况'
)comment='数字遗漏情况表';


update ssq_sum 
set c1 = (select t1.c1 from (select t.num,count(1) c1 from ssq_record t group by t.num) t1 where s = t1.num);

update ssq_sum set c1 = 0 where c1 is null;

select sum(c1) from ssq_sum;

update ssq_sum set p1 = (c1/1818)*100;


select * from ssq_record t order by t.qh desc;

select * from global_config;

/**数字出现次数分析*/
select n,max(c) from ssq_num t group by n order by max(c) desc;

/*和值分析 */

update ssq_record t set t.num = t.r1 + t.r2 + t.r3 + t.r4 + t.r5 + t.r6 where t.num is null;

select t.num,count(1) c1 from ssq_record t group by t.num order by t.num;

/*重复号分析*/

select t1.* from ssq_record t1 ,ssq_record t2 
where t1.qh != t2.qh and t1.r1 = t2.r1 and t1.r2 = t2.r2 and t1.r3 = t2.r3 
and t1.r4 = t2.r4 and t1.r5 = t2.r5 and t1.r6 = t2.r6
order by t1.qh;

/**和值出现次数分析*/
select num,count(1) from ssq_record t group by num order by count(1) desc;

select * from ssq_sum order by c1 desc;

select * from ssq_sum where p1 >= 0.9;

select * from ssq_record order by qh desc;

select sum(p) from ssq_sum t where t.s between 41 and 170;

select sum(t.c) from ssq_sum t; --1107568

update ssq_sum t set p = (t.c/1107568)*100;

/**末位数字相同情况分析*/

/**遗漏情况分析*/
select * from ssq_miss t order by t.qh desc;

select * from ssq_record t where t.r1 = ''


