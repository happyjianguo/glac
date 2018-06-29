package net.engining.sccc.accounting.sharding.qsql;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QsqlApGlVolDtlHst is a Querydsl query type for QsqlApGlVolDtlHst
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QsqlApGlVolDtlHst extends com.querydsl.sql.RelationalPathBase<QsqlApGlVolDtlHst> {

    private static final long serialVersionUID = 1255724881;

    public static final QsqlApGlVolDtlHst apGlVolDtlHst = new QsqlApGlVolDtlHst("ap_gl_vol_dtl_hst");

    public final StringPath assistAccountData = createString("assistAccountData");

    public final DatePath<java.sql.Date> bizDate = createDate("bizDate", java.sql.Date.class);

    public final StringPath branch = createString("branch");

    public final StringPath branchNo = createString("branchNo");

    public final StringPath crsubjectCd = createString("crsubjectCd");

    public final StringPath currCd = createString("currCd");

    public final StringPath dbsubjectCd = createString("dbsubjectCd");

    public final StringPath glvSeq = createString("glvSeq");

    public final StringPath inOutFlag = createString("inOutFlag");

    public final NumberPath<Integer> jpaVersion = createNumber("jpaVersion", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastUpdateDate = createDateTime("lastUpdateDate", java.sql.Timestamp.class);

    public final StringPath org = createString("org");

    public final StringPath postGlInd = createString("postGlInd");

    public final StringPath redBlueInd = createString("redBlueInd");

    public final StringPath refNo = createString("refNo");

    public final DateTimePath<java.sql.Timestamp> setupDate = createDateTime("setupDate", java.sql.Timestamp.class);

    public final NumberPath<java.math.BigDecimal> subjAmount = createNumber("subjAmount", java.math.BigDecimal.class);

    public final DatePath<java.sql.Date> transDate = createDate("transDate", java.sql.Date.class);

    public final StringPath txnBrcd = createString("txnBrcd");

    public final StringPath txnDetailSeq = createString("txnDetailSeq");

    public final StringPath txnDetailType = createString("txnDetailType");

    public final StringPath txnDirection = createString("txnDirection");

    public final StringPath volDesc = createString("volDesc");

    public final DatePath<java.sql.Date> volDt = createDate("volDt", java.sql.Date.class);

    public final NumberPath<Integer> volSeq = createNumber("volSeq", Integer.class);

    public final com.querydsl.sql.PrimaryKey<QsqlApGlVolDtlHst> primary = createPrimaryKey(glvSeq);

    public QsqlApGlVolDtlHst(String variable) {
        super(QsqlApGlVolDtlHst.class, forVariable(variable), "null", "ap_gl_vol_dtl_hst");
        addMetadata();
    }

    public QsqlApGlVolDtlHst(String variable, String schema, String table) {
        super(QsqlApGlVolDtlHst.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QsqlApGlVolDtlHst(String variable, String schema) {
        super(QsqlApGlVolDtlHst.class, forVariable(variable), schema, "ap_gl_vol_dtl_hst");
        addMetadata();
    }

    public QsqlApGlVolDtlHst(Path<? extends QsqlApGlVolDtlHst> path) {
        super(path.getType(), path.getMetadata(), "null", "ap_gl_vol_dtl_hst");
        addMetadata();
    }

    public QsqlApGlVolDtlHst(PathMetadata metadata) {
        super(QsqlApGlVolDtlHst.class, metadata, "null", "ap_gl_vol_dtl_hst");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(assistAccountData, ColumnMetadata.named("ASSIST_ACCOUNT_DATA").withIndex(21).ofType(Types.LONGVARCHAR).withSize(16777215));
        addMetadata(bizDate, ColumnMetadata.named("BIZ_DATE").withIndex(24).ofType(Types.DATE).withSize(10).notNull());
        addMetadata(branch, ColumnMetadata.named("BRANCH").withIndex(5).ofType(Types.VARCHAR).withSize(40));
        addMetadata(branchNo, ColumnMetadata.named("BRANCH_NO").withIndex(3).ofType(Types.VARCHAR).withSize(9));
        addMetadata(crsubjectCd, ColumnMetadata.named("CRSUBJECT_CD").withIndex(11).ofType(Types.VARCHAR).withSize(40));
        addMetadata(currCd, ColumnMetadata.named("CURR_CD").withIndex(9).ofType(Types.VARCHAR).withSize(3));
        addMetadata(dbsubjectCd, ColumnMetadata.named("DBSUBJECT_CD").withIndex(10).ofType(Types.VARCHAR).withSize(40));
        addMetadata(glvSeq, ColumnMetadata.named("GLV_SEQ").withIndex(1).ofType(Types.VARCHAR).withSize(64).notNull());
        addMetadata(inOutFlag, ColumnMetadata.named("IN_OUT_FLAG").withIndex(8).ofType(Types.CHAR).withSize(1));
        addMetadata(jpaVersion, ColumnMetadata.named("JPA_VERSION").withIndex(25).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(lastUpdateDate, ColumnMetadata.named("LAST_UPDATE_DATE").withIndex(23).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(org, ColumnMetadata.named("ORG").withIndex(2).ofType(Types.VARCHAR).withSize(12));
        addMetadata(postGlInd, ColumnMetadata.named("POST_GL_IND").withIndex(7).ofType(Types.VARCHAR).withSize(12));
        addMetadata(redBlueInd, ColumnMetadata.named("RED_BLUE_IND").withIndex(14).ofType(Types.CHAR).withSize(1));
        addMetadata(refNo, ColumnMetadata.named("REF_NO").withIndex(16).ofType(Types.VARCHAR).withSize(9));
        addMetadata(setupDate, ColumnMetadata.named("SETUP_DATE").withIndex(22).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(subjAmount, ColumnMetadata.named("SUBJ_AMOUNT").withIndex(12).ofType(Types.DECIMAL).withSize(17).withDigits(4));
        addMetadata(transDate, ColumnMetadata.named("TRANS_DATE").withIndex(20).ofType(Types.DATE).withSize(10));
        addMetadata(txnBrcd, ColumnMetadata.named("TXN_BRCD").withIndex(6).ofType(Types.VARCHAR).withSize(40));
        addMetadata(txnDetailSeq, ColumnMetadata.named("TXN_DETAIL_SEQ").withIndex(17).ofType(Types.VARCHAR).withSize(64));
        addMetadata(txnDetailType, ColumnMetadata.named("TXN_DETAIL_TYPE").withIndex(18).ofType(Types.CHAR).withSize(1));
        addMetadata(txnDirection, ColumnMetadata.named("TXN_DIRECTION").withIndex(19).ofType(Types.CHAR).withSize(1));
        addMetadata(volDesc, ColumnMetadata.named("VOL_DESC").withIndex(15).ofType(Types.VARCHAR).withSize(80));
        addMetadata(volDt, ColumnMetadata.named("VOL_DT").withIndex(4).ofType(Types.DATE).withSize(10));
        addMetadata(volSeq, ColumnMetadata.named("VOL_SEQ").withIndex(13).ofType(Types.INTEGER).withSize(10));
    }

}

