SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Indexes */

DROP INDEX IDX_TXNSEQ ON BT_DATA_MIGRATION_TEMPDTL;
DROP INDEX IDX_CUST_ID ON BT_DATA_MIGRATION_TEMPDTL;
DROP INDEX IDX_IOU_NO ON BT_DATA_MIGRATION_TEMPDTL;



/* Drop Tables */

DROP TABLE IF EXISTS BT_DATA_MIGRATION_TEMPDTL;
DROP TABLE IF EXISTS BT_EOD_TXN_IMPORT;
DROP TABLE IF EXISTS BT_IMPORT_EXCEPTION;
DROP TABLE IF EXISTS BT_NQH_IMPORT;
DROP TABLE IF EXISTS BT_ZY_IMPORT;
DROP TABLE IF EXISTS CACT_SYS_CHECKLIST;




/* Create Tables */

-- 数据迁移临时流水表
CREATE TABLE BT_DATA_MIGRATION_TEMPDTL
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '序号',
	ORG varchar(12) COMMENT '机构号',
	BRANCH_NO varchar(9) COMMENT '分行号',
	VOUCHER_NUM varchar(64) COMMENT '凭证编号',
	VOL_SEQ int NOT NULL COMMENT '分录序号',
	TRANS_DATE date NOT NULL COMMENT '交易日期',
	POST_CODE varchar(20) NOT NULL COMMENT '入账代码 ',
	VOL_DT date NOT NULL COMMENT '记账日期',
	BRANCH varchar(40) COMMENT '记账机构',
	TXN_BRCD varchar(40) COMMENT '交易机构',
	CURR_CD varchar(3) COMMENT '币种',
	POST_AMOUNT decimal(15,2) NOT NULL COMMENT '入账金额',
	-- ///
	-- @net.engining.gm.infrastructure.enums.TxnDirection
	TXN_DIRECTION char(1) NOT NULL COMMENT '借贷标志 : ///
@net.engining.gm.infrastructure.enums.TxnDirection',
	DBSUBJECT_CD varchar(40) COMMENT '借方科目号',
	CRSUBJECT_CD varchar(40) COMMENT '贷方科目号',
	SUBJECT_NAME varchar(100) NOT NULL COMMENT '科目名称',
	-- ///
	-- A|表内
	-- B|表外
	IN_OUT_FLAG char(1) NOT NULL COMMENT '表内外标志 : ///
A|表内
B|表外',
	-- ///
	-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
	RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
@net.engining.pcx.cc.param.model.enums.RedBlueInd',
	VOL_DESC varchar(80) NOT NULL COMMENT '分录摘要',
	TXN_DETAIL_SEQ varchar(64) NOT NULL COMMENT '来源交易流水号',
	ASSIST_ACCOUNT_DATA mediumtext COMMENT '辅助核算项数据',
	-- ///
	-- 
	-- MANU|手工记账
	-- 
	-- SYSM|系统记账
	POST_TYPE varchar(5) COMMENT '记账类型 : ///

MANU|手工记账

SYSM|系统记账',
	PRODUCT_ID varchar(64) COMMENT '产品参数唯一标识',
	CUST_ID varchar(64) COMMENT '客户编号',
	IOU_NO varchar(64) COMMENT '借据号',
	-- $$$@CreatedDate$$$
	SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
	-- $$$@LastModifiedDate$$$
	LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
	BIZ_DATE date NOT NULL COMMENT '系统业务日期',
	JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
	PRIMARY KEY (ID),
	UNIQUE (ID)
) COMMENT = '数据迁移临时流水表';


-- 日终交易记账数据导入流水表
CREATE TABLE BT_EOD_TXN_IMPORT
(
	ID varchar(64) NOT NULL COMMENT '序号',
	BATCH_SEQ varchar(20) COMMENT '批次号',
	ORG varchar(12) COMMENT '机构号',
	BRANCH_NO varchar(9) COMMENT '分行号',
	-- ///
	-- INTEACC|利息计提
	-- PINTACC|罚息计提
	-- TRANSFO|余额成份结转
	-- BATREPA|批量还款
	-- RECON|对账
	-- 
	TXN_TYPE varchar(8) NOT NULL COMMENT 'TXN_TYPE : ///
INTEACC|利息计提
PINTACC|罚息计提
TRANSFO|余额成份结转
BATREPA|批量还款
RECON|对账
',
	JSON_INPUT_DATA mediumtext COMMENT 'JSON_INPUT_DATA',
	-- $$$@CreatedDate$$$
	SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
	-- $$$@LastModifiedDate$$$
	LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
	BIZ_DATE date NOT NULL COMMENT '系统业务日期',
	JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
	PRIMARY KEY (ID),
	UNIQUE (ID)
) COMMENT = '日终交易记账数据导入流水表';


-- 批量数据导入异常记录表
CREATE TABLE BT_IMPORT_EXCEPTION
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '序号',
	BATCH_SEQ varchar(20) COMMENT '批次号',
	ORG varchar(12) COMMENT '机构号',
	BRANCH_NO varchar(9) COMMENT '分行号',
	-- ///
	-- @net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd
	INSPECTION_CD varchar(20) COMMENT '检查项代码 : ///
@net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd',
	FILE_PATH varchar(200) COMMENT '文件路径',
	EXCEPTION_MSG mediumtext COMMENT '异常信息',
	LINE_CONTENT mediumtext COMMENT '行内容',
	-- $$$@CreatedDate$$$
	SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
	-- $$$@LastModifiedDate$$$
	LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
	BIZ_DATE date NOT NULL COMMENT '系统业务日期',
	JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
	PRIMARY KEY (ID),
	UNIQUE (ID)
) COMMENT = '批量数据导入异常记录表';


-- 拿去花记账数据导入流水表
CREATE TABLE BT_NQH_IMPORT
(
	ID int NOT NULL COMMENT '序号',
	BATCH_SEQ varchar(20) COMMENT '批次号',
	ORG varchar(12) COMMENT '机构号',
	BRANCH_NO varchar(9) COMMENT '分行号',
	POST_CODE varchar(20) COMMENT '入账代码 ',
	POST_AMT decimal(18,2) COMMENT '入账金额',
	CLEAR_DATE date COMMENT '清算日期',
	PROD_ID varchar(10) COMMENT 'PROD_ID',
	CHANNEL_ID varchar(64) COMMENT 'CHANNEL_ID',
	-- $$$@CreatedDate$$$
	SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
	-- $$$@LastModifiedDate$$$
	LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
	BIZ_DATE date NOT NULL COMMENT '系统业务日期',
	JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
	PRIMARY KEY (ID),
	UNIQUE (ID)
) COMMENT = '拿去花记账数据导入流水表';


-- 中银记账数据导入流水表
CREATE TABLE BT_ZY_IMPORT
(
	ID int NOT NULL COMMENT '序号',
	BATCH_SEQ varchar(20) COMMENT '批次号',
	ORG varchar(12) COMMENT '机构号',
	BRANCH_NO varchar(9) COMMENT '分行号',
	POST_CODE varchar(20) COMMENT '入账代码 ',
	POST_AMT decimal(18,2) COMMENT '入账金额',
	CLEAR_DATE date COMMENT '清算日期',
	PROD_ID varchar(10) COMMENT 'PROD_ID',
	CHANNEL_ID varchar(64) COMMENT 'CHANNEL_ID',
	-- $$$@CreatedDate$$$
	SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
	-- $$$@LastModifiedDate$$$
	LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
	BIZ_DATE date NOT NULL COMMENT '系统业务日期',
	JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
	PRIMARY KEY (ID),
	UNIQUE (ID)
) COMMENT = '中银记账数据导入流水表';


-- 系统检查项记录表
CREATE TABLE CACT_SYS_CHECKLIST
(
	SEQ int NOT NULL AUTO_INCREMENT COMMENT '序号',
	BATCH_SEQ varchar(20) COMMENT '批次号',
	ORG varchar(12) COMMENT '机构号',
	BRANCH_NO varchar(9) COMMENT '分行号',
	-- ///
	-- @net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd
	INSPECTION_CD varchar(20) NOT NULL COMMENT '检查项代码 : ///
@net.engining.pcx.cc.infrastructure.shared.enums.InspectionCd',
	CHECK_LIST_DESC varchar(40) COMMENT '检查项描述',
	-- ///
	-- @net.engining.pcx.cc.param.model.enums.CheckListType
	CHECK_LIST_TYPE varchar(20) COMMENT '检查项类型 : ///
@net.engining.pcx.cc.param.model.enums.CheckListType',
	CHECK_TIMES int COMMENT '待检查次数',
	-- ///
	-- WAIT|待处理
	-- SUCCESS|处理成功
	-- FAILED|处理失败
	CHECK_STATUS varchar(16) COMMENT '状态 : ///
WAIT|待处理
SUCCESS|处理成功
FAILED|处理失败',
	SKIPABLE boolean COMMENT '是否可跳过',
	-- ///
	-- TIME|时间
	-- COUNT|次数
	SKIP_CONDITION_TYPE varchar(6) COMMENT '跳过条件类型 : ///
TIME|时间
COUNT|次数',
	SKIP_CON_MAX_COUNT int COMMENT '跳过检查最大次数',
	SKIP_CON_DEADLINE timestamp DEFAULT NOW() COMMENT '跳过检查终结时间',
	CHECK_BIZ_DATA mediumtext COMMENT '检查项业务数据',
	-- $$$@CreatedDate$$$
	SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
	-- $$$@LastModifiedDate$$$
	LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
	BIZ_DATE date NOT NULL COMMENT '系统业务日期',
	JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
	PRIMARY KEY (SEQ),
	UNIQUE (SEQ)
) COMMENT = '系统检查项记录表';



/* Create Indexes */

CREATE INDEX IDX_TXNSEQ ON BT_DATA_MIGRATION_TEMPDTL (TXN_DETAIL_SEQ ASC);
CREATE INDEX IDX_CUST_ID ON BT_DATA_MIGRATION_TEMPDTL (CUST_ID ASC);
CREATE INDEX IDX_IOU_NO ON BT_DATA_MIGRATION_TEMPDTL (IOU_NO ASC);



