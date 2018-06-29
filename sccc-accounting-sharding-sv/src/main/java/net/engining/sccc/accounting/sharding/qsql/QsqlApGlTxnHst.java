package net.engining.sccc.accounting.sharding.qsql;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QsqlApGlTxnHst is a Querydsl query type for QsqlApGlTxnHst
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QsqlApGlTxnHst extends com.querydsl.sql.RelationalPathBase<QsqlApGlTxnHst> {

    private static final long serialVersionUID = 890282062;

    public static final QsqlApGlTxnHst apGlTxnHst = new QsqlApGlTxnHst("ap_gl_txn_hst");

    public final StringPath accountDesc = createString("accountDesc");

    public final NumberPath<Integer> acctSeq = createNumber("acctSeq", Integer.class);

    public final StringPath acqBranch = createString("acqBranch");

    public final StringPath ageGroupCd = createString("ageGroupCd");

    public final DatePath<java.sql.Date> bizDate = createDate("bizDate", java.sql.Date.class);

    public final StringPath branchNo = createString("branchNo");

    public final DatePath<java.sql.Date> clearDate = createDate("clearDate", java.sql.Date.class);

    public final StringPath currCd = createString("currCd");

    public final StringPath gltSeq = createString("gltSeq");

    public final StringPath inOutFlag = createString("inOutFlag");

    public final NumberPath<Integer> jpaVersion = createNumber("jpaVersion", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastUpdateDate = createDateTime("lastUpdateDate", java.sql.Timestamp.class);

    public final StringPath org = createString("org");

    public final StringPath owingBranch = createString("owingBranch");

    public final NumberPath<java.math.BigDecimal> postAmount = createNumber("postAmount", java.math.BigDecimal.class);

    public final StringPath postCode = createString("postCode");

    public final DatePath<java.sql.Date> postDate = createDate("postDate", java.sql.Date.class);

    public final StringPath postDesc = createString("postDesc");

    public final StringPath postGlInd = createString("postGlInd");

    public final StringPath postType = createString("postType");

    public final DateTimePath<java.sql.Timestamp> setupDate = createDateTime("setupDate", java.sql.Timestamp.class);

    public final DatePath<java.sql.Date> transDate = createDate("transDate", java.sql.Date.class);

    public final StringPath txnDetailSeq = createString("txnDetailSeq");

    public final StringPath txnDetailType = createString("txnDetailType");

    public final StringPath txnDirection = createString("txnDirection");

    public final com.querydsl.sql.PrimaryKey<QsqlApGlTxnHst> primary = createPrimaryKey(gltSeq);

    public QsqlApGlTxnHst(String variable) {
        super(QsqlApGlTxnHst.class, forVariable(variable), "null", "ap_gl_txn_hst");
        addMetadata();
    }

    public QsqlApGlTxnHst(String variable, String schema, String table) {
        super(QsqlApGlTxnHst.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QsqlApGlTxnHst(String variable, String schema) {
        super(QsqlApGlTxnHst.class, forVariable(variable), schema, "ap_gl_txn_hst");
        addMetadata();
    }

    public QsqlApGlTxnHst(Path<? extends QsqlApGlTxnHst> path) {
        super(path.getType(), path.getMetadata(), "null", "ap_gl_txn_hst");
        addMetadata();
    }

    public QsqlApGlTxnHst(PathMetadata metadata) {
        super(QsqlApGlTxnHst.class, metadata, "null", "ap_gl_txn_hst");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(accountDesc, ColumnMetadata.named("ACCOUNT_DESC").withIndex(19).ofType(Types.VARCHAR).withSize(80));
        addMetadata(acctSeq, ColumnMetadata.named("ACCT_SEQ").withIndex(4).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(acqBranch, ColumnMetadata.named("ACQ_BRANCH").withIndex(13).ofType(Types.VARCHAR).withSize(9));
        addMetadata(ageGroupCd, ColumnMetadata.named("AGE_GROUP_CD").withIndex(14).ofType(Types.CHAR).withSize(1));
        addMetadata(bizDate, ColumnMetadata.named("BIZ_DATE").withIndex(24).ofType(Types.DATE).withSize(10).notNull());
        addMetadata(branchNo, ColumnMetadata.named("BRANCH_NO").withIndex(3).ofType(Types.VARCHAR).withSize(9));
        addMetadata(clearDate, ColumnMetadata.named("CLEAR_DATE").withIndex(20).ofType(Types.DATE).withSize(10));
        addMetadata(currCd, ColumnMetadata.named("CURR_CD").withIndex(5).ofType(Types.VARCHAR).withSize(3).notNull());
        addMetadata(gltSeq, ColumnMetadata.named("GLT_SEQ").withIndex(1).ofType(Types.VARCHAR).withSize(64).notNull());
        addMetadata(inOutFlag, ColumnMetadata.named("IN_OUT_FLAG").withIndex(17).ofType(Types.CHAR).withSize(1));
        addMetadata(jpaVersion, ColumnMetadata.named("JPA_VERSION").withIndex(25).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(lastUpdateDate, ColumnMetadata.named("LAST_UPDATE_DATE").withIndex(23).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(org, ColumnMetadata.named("ORG").withIndex(2).ofType(Types.VARCHAR).withSize(12));
        addMetadata(owingBranch, ColumnMetadata.named("OWING_BRANCH").withIndex(12).ofType(Types.VARCHAR).withSize(9));
        addMetadata(postAmount, ColumnMetadata.named("POST_AMOUNT").withIndex(10).ofType(Types.DECIMAL).withSize(15).withDigits(2).notNull());
        addMetadata(postCode, ColumnMetadata.named("POST_CODE").withIndex(6).ofType(Types.VARCHAR).withSize(20).notNull());
        addMetadata(postDate, ColumnMetadata.named("POST_DATE").withIndex(9).ofType(Types.DATE).withSize(10));
        addMetadata(postDesc, ColumnMetadata.named("POST_DESC").withIndex(7).ofType(Types.VARCHAR).withSize(80));
        addMetadata(postGlInd, ColumnMetadata.named("POST_GL_IND").withIndex(11).ofType(Types.VARCHAR).withSize(12));
        addMetadata(postType, ColumnMetadata.named("POST_TYPE").withIndex(18).ofType(Types.VARCHAR).withSize(5));
        addMetadata(setupDate, ColumnMetadata.named("SETUP_DATE").withIndex(22).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(transDate, ColumnMetadata.named("TRANS_DATE").withIndex(21).ofType(Types.DATE).withSize(10));
        addMetadata(txnDetailSeq, ColumnMetadata.named("TXN_DETAIL_SEQ").withIndex(16).ofType(Types.VARCHAR).withSize(64));
        addMetadata(txnDetailType, ColumnMetadata.named("TXN_DETAIL_TYPE").withIndex(15).ofType(Types.CHAR).withSize(1));
        addMetadata(txnDirection, ColumnMetadata.named("TXN_DIRECTION").withIndex(8).ofType(Types.CHAR).withSize(1));
    }

}

