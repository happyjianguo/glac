DROP TABLE IF EXISTS AP_GL_VOL_DTL_HST_0;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_HST_1;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_HST_2;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_HST_3;
DROP TABLE IF EXISTS AP_GL_VOL_DTL_HST_4;

-- 会计分录拆分交易流水历史表
CREATE TABLE AP_GL_VOL_DTL_HST_0
(
		GLV_SEQ varchar(64) NOT NULL COMMENT '分录流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		CURR_CD varchar(3) COMMENT '币种',
		DBSUBJECT_CD varchar(40) COMMENT '借方科目号',
		CRSUBJECT_CD varchar(40) COMMENT '贷方科目号',
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
		ASSIST_ACCOUNT_DATA mediumtext COMMENT '辅助核算项数据',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLV_SEQ)
) COMMENT = '会计分录拆分交易流水历史表';

CREATE TABLE AP_GL_VOL_DTL_HST_1
(
		GLV_SEQ varchar(64) NOT NULL COMMENT '分录流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		CURR_CD varchar(3) COMMENT '币种',
		DBSUBJECT_CD varchar(40) COMMENT '借方科目号',
		CRSUBJECT_CD varchar(40) COMMENT '贷方科目号',
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
		ASSIST_ACCOUNT_DATA mediumtext COMMENT '辅助核算项数据',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLV_SEQ)
) COMMENT = '会计分录拆分交易流水历史表';

CREATE TABLE AP_GL_VOL_DTL_HST_2
(
		GLV_SEQ varchar(64) NOT NULL COMMENT '分录流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		CURR_CD varchar(3) COMMENT '币种',
		DBSUBJECT_CD varchar(40) COMMENT '借方科目号',
		CRSUBJECT_CD varchar(40) COMMENT '贷方科目号',
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
		ASSIST_ACCOUNT_DATA mediumtext COMMENT '辅助核算项数据',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLV_SEQ)
) COMMENT = '会计分录拆分交易流水历史表';

CREATE TABLE AP_GL_VOL_DTL_HST_3
(
		GLV_SEQ varchar(64) NOT NULL COMMENT '分录流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		CURR_CD varchar(3) COMMENT '币种',
		DBSUBJECT_CD varchar(40) COMMENT '借方科目号',
		CRSUBJECT_CD varchar(40) COMMENT '贷方科目号',
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
		ASSIST_ACCOUNT_DATA mediumtext COMMENT '辅助核算项数据',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLV_SEQ)
) COMMENT = '会计分录拆分交易流水历史表';

CREATE TABLE AP_GL_VOL_DTL_HST_4
(
		GLV_SEQ varchar(64) NOT NULL COMMENT '分录流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		VOL_DT date COMMENT '记账日期',
		BRANCH varchar(40) COMMENT '记账机构',
		TXN_BRCD varchar(40) COMMENT '交易机构',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		CURR_CD varchar(3) COMMENT '币种',
		DBSUBJECT_CD varchar(40) COMMENT '借方科目号',
		CRSUBJECT_CD varchar(40) COMMENT '贷方科目号',
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
		ASSIST_ACCOUNT_DATA mediumtext COMMENT '辅助核算项数据',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLV_SEQ)
) COMMENT = '会计分录拆分交易流水历史表';

