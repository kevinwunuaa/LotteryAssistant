create table global_config(
	id integer primary key auto_increment,
	config_code varchar(50) comment '���ñ�ʶ',
	config_value varchar(200) comment '����ֵ',
	validity int default 1 comment '����״̬'
)comment = 'ȫ������';

alter table global_config add(config_desc varchar(200));

/**�������ݱ��ʼ��*/
insert into global_config(config_code,config_value)
value('SSQ_DB_INIT_STATUS','0');

/** ������Դurlģʽ*/
insert into global_config(config_code,config_value)
values('SSQ_DATA_URL_EXPR','http://tubiao.zhcw.com/tubiao/ssqNew/ssqInc/ssqZongHeFengBuTuAsckj_year={YEAR}.html');

/** ���ݳ�ʼ��״̬*/
insert into global_config(config_code,config_value)
value('SSQ_DATA_INIT_STATUS','0');

/** �н����ֳ����������״̬*/
insert into global_config(config_code,config_value,config_desc)
value('SSQ_NUM_CALC_STATUS',NULL,'��¼�н����ֳ��ִ������������ֵ��ʾ��ǰ������Ч�ںš�');

update global_config set config_value = NULL where config_code = 'SSQ_NUM_CALC_STATUS';


create table ssq_record(
	qh varchar(12) primary key comment '�ں�',
	rq varchar(12) comment '��������',
	r1 integer(2),
	r2 integer(2),
	r3 integer(2),
	r4 integer(2),
	r5 integer(2),
	r6 integer(2),
	b integer(2),
	num integer(3)
)comment='˫ɫ�򿪽���¼';

create table ssq_sum(
	s Integer(3) primary key comment '��ֵ',
	c Integer(6) comment '���ִ���',
	p Float(5,3) comment '�ٷֱ�',
	c1 integer(5) comment 'ʵ�ʳ��ִ���',
	p1 Float(5,3) comment 'ʵ�ʳ���ռ��'
)comment='˫ɫ�����ۺ�ֵ��¼';

drop table if exists ssq_num;
create table ssq_num(
	n Integer(2) primary key comment '�н�����',
	c Integer(5) comment '�н�����',
	p Float(5,3) comment '��ռ�ٷֱ�'
)comment='�н����������';

drop table if exists ssq_miss;

create table ssq_miss(
	qh varchar(12) primary key comment '�ں�',
	miss_info text comment 'json��ʽ����©���'
)comment='������©�����';


update ssq_sum 
set c1 = (select t1.c1 from (select t.num,count(1) c1 from ssq_record t group by t.num) t1 where s = t1.num);

update ssq_sum set c1 = 0 where c1 is null;

select sum(c1) from ssq_sum;

update ssq_sum set p1 = (c1/1818)*100;


select * from ssq_record t order by t.qh desc;

select * from global_config;

/**���ֳ��ִ�������*/
select n,max(c) from ssq_num t group by n order by max(c) desc;

/*��ֵ���� */

update ssq_record t set t.num = t.r1 + t.r2 + t.r3 + t.r4 + t.r5 + t.r6 where t.num is null;

select t.num,count(1) c1 from ssq_record t group by t.num order by t.num;

/*�ظ��ŷ���*/

select t1.* from ssq_record t1 ,ssq_record t2 
where t1.qh != t2.qh and t1.r1 = t2.r1 and t1.r2 = t2.r2 and t1.r3 = t2.r3 
and t1.r4 = t2.r4 and t1.r5 = t2.r5 and t1.r6 = t2.r6
order by t1.qh;

/**��ֵ���ִ�������*/
select num,count(1) from ssq_record t group by num order by count(1) desc;

select * from ssq_sum order by c1 desc;

select * from ssq_sum where p1 >= 0.9;

select * from ssq_record order by qh desc;

select sum(p) from ssq_sum t where t.s between 41 and 170;

select sum(t.c) from ssq_sum t; --1107568

update ssq_sum t set p = (t.c/1107568)*100;

/**ĩλ������ͬ�������*/

/**��©�������*/
select * from ssq_miss t order by t.qh desc;

select * from ssq_record t where t.r1 = ''


