DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_0;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_1;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_2;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_3;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_4;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_5;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_6;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_7;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_8;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_9;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_10;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_11;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_12;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_13;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_ASS_HST_14;

-- 辅助核算拆分历史表
CREATE TABLE AP_GL_VOL_DTL_ASS_HST_0
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_1
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_2
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_3
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_4
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_5
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_6
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_7
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_8
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_9
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_10
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_11
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_12
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_13
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';

CREATE TABLE AP_GL_VOL_DTL_ASS_HST_14
(
		ASS_SEQ varchar(64) NOT NULL COMMENT '辅助拆分流水号',
		SUBJECT_CD varchar(40) NOT NULL COMMENT '科目号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType
		ASSIST_TYPE varchar(10) COMMENT '辅助核算类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.AssistAccountingType',
		ASSIST_ACCOUNT_VALUE varchar(20) COMMENT '辅助核算项值',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ORG varchar(12) COMMENT '机构号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		CURR_CD varchar(3) COMMENT '币种',
		SUBJ_AMOUNT decimal(17,4) COMMENT '金额',
		VOL_SEQ int COMMENT '分录序号',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.RedBlueInd
		RED_BLUE_IND char(1) COMMENT '红蓝字标识 : ///
	@net.engining.pcx.cc.param.model.enums.RedBlueInd',
		VOL_DESC varchar(80) COMMENT '分录摘要',
		REF_NO varchar(9) COMMENT '关联参考号',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		TRANS_DATE date COMMENT '交易日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		PRIMARY KEY (ASS_SEQ),
		UNIQUE (ASS_SEQ)
) COMMENT = '辅助核算拆分历史表';