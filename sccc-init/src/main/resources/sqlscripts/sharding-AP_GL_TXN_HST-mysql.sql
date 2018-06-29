DROP TABLE IF EXISTS AP_GL_TXN_HST_0;
DROP TABLE IF EXISTS AP_GL_TXN_HST_1;

-- 总账交易流水历史表
CREATE TABLE AP_GL_TXN_HST_0
(
		GLT_SEQ varchar(64) NOT NULL COMMENT '总账交易流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ACCT_SEQ int NOT NULL COMMENT '账户编号',
		CURR_CD varchar(3) NOT NULL COMMENT '币种',
		POST_CODE varchar(20) NOT NULL COMMENT '入账代码',
		POST_DESC varchar(80) COMMENT '入账描述',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		POST_DATE date COMMENT '入账日期',
		POST_AMOUNT decimal(15,2) NOT NULL COMMENT '入账金额',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		OWING_BRANCH varchar(9) COMMENT '所属分支行',
		ACQ_BRANCH varchar(9) COMMENT '受理所属分支行',
		-- ///
		-- @net.engining.gm.infrastructure.enums.AgeGroupCd
		AGE_GROUP_CD char(1) COMMENT '账龄组代码 : ///
	@net.engining.gm.infrastructure.enums.AgeGroupCd',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		-- ///
		-- 
		-- MANU|手工记账
		-- 
		-- SYSM|系统记账
		POST_TYPE varchar(5) COMMENT '记账类型 : ///

	MANU|手工记账

	SYSM|系统记账',
		ACCOUNT_DESC varchar(80) COMMENT '记账说明',
		CLEAR_DATE date COMMENT '清算日期',
		TRANS_DATE date COMMENT '交易日期',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLT_SEQ)
) COMMENT = '总账交易流水历史表';

CREATE TABLE AP_GL_TXN_HST_1
(
		GLT_SEQ varchar(64) NOT NULL COMMENT '总账交易流水号',
		ORG varchar(12) COMMENT '机构号',
		BRANCH_NO varchar(9) COMMENT '分行号',
		ACCT_SEQ int NOT NULL COMMENT '账户编号',
		CURR_CD varchar(3) NOT NULL COMMENT '币种',
		POST_CODE varchar(20) NOT NULL COMMENT '入账代码',
		POST_DESC varchar(80) COMMENT '入账描述',
		-- ///
		-- @net.engining.gm.infrastructure.enums.TxnDirection
		TXN_DIRECTION char(1) COMMENT '借贷标志 : ///
	@net.engining.gm.infrastructure.enums.TxnDirection',
		POST_DATE date COMMENT '入账日期',
		POST_AMOUNT decimal(15,2) NOT NULL COMMENT '入账金额',
		-- ///
		-- @net.engining.pcx.cc.param.model.enums.PostGlInd
		POST_GL_IND varchar(12) COMMENT '总账入账方式 : ///
	@net.engining.pcx.cc.param.model.enums.PostGlInd',
		OWING_BRANCH varchar(9) COMMENT '所属分支行',
		ACQ_BRANCH varchar(9) COMMENT '受理所属分支行',
		-- ///
		-- @net.engining.gm.infrastructure.enums.AgeGroupCd
		AGE_GROUP_CD char(1) COMMENT '账龄组代码 : ///
	@net.engining.gm.infrastructure.enums.AgeGroupCd',
		-- ///
		-- @net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType
		TXN_DETAIL_TYPE char(1) COMMENT '来源交易流水类型 : ///
	@net.engining.pcx.cc.infrastructure.shared.enums.TxnDetailType',
		TXN_DETAIL_SEQ varchar(64) COMMENT '来源交易流水号',
		-- ///
		-- A|表内
		-- B|表外
		IN_OUT_FLAG char(1) COMMENT '表内表外标志 : ///
	A|表内
	B|表外',
		-- ///
		-- 
		-- MANU|手工记账
		-- 
		-- SYSM|系统记账
		POST_TYPE varchar(5) COMMENT '记账类型 : ///

	MANU|手工记账

	SYSM|系统记账',
		ACCOUNT_DESC varchar(80) COMMENT '记账说明',
		CLEAR_DATE date COMMENT '清算日期',
		TRANS_DATE date COMMENT '交易日期',
		-- $$$@CreatedDate$$$
		SETUP_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '创建日期 : $$$@CreatedDate$$$',
		-- $$$@LastModifiedDate$$$
		LAST_UPDATE_DATE timestamp DEFAULT NOW() NOT NULL COMMENT '最后更新日期 : $$$@LastModifiedDate$$$',
		BIZ_DATE date NOT NULL COMMENT '系统业务日期',
		JPA_VERSION int NOT NULL COMMENT '乐观锁版本号',
		PRIMARY KEY (GLT_SEQ)
) COMMENT = '总账交易流水历史表';
