package net.engining.sccc.accounting.sharding.qsql;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QsqlApGlVolDtlAssHst is a Querydsl query type for QsqlApGlVolDtlAssHst
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QsqlApGlVolDtlAssHst extends com.querydsl.sql.RelationalPathBase<QsqlApGlVolDtlAssHst> {

    private static final long serialVersionUID = 401864624;

    public static final QsqlApGlVolDtlAssHst apGlVolDtlAssHst = new QsqlApGlVolDtlAssHst("ap_gl_vol_dtl_ass_hst");

    public final StringPath assistAccountValue = createString("assistAccountValue");

    public final StringPath assistType = createString("assistType");

    public final StringPath assSeq = createString("assSeq");

    public final DatePath<java.sql.Date> bizDate = createDate("bizDate", java.sql.Date.class);

    public final StringPath branch = createString("branch");

    public final StringPath branchNo = createString("branchNo");

    public final StringPath currCd = createString("currCd");

    public final NumberPath<Integer> jpaVersion = createNumber("jpaVersion", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastUpdateDate = createDateTime("lastUpdateDate", java.sql.Timestamp.class);

    public final StringPath org = createString("org");

    public final StringPath redBlueInd = createString("redBlueInd");

    public final StringPath refNo = createString("refNo");

    public final DateTimePath<java.sql.Timestamp> setupDate = createDateTime("setupDate", java.sql.Timestamp.class);

    public final NumberPath<java.math.BigDecimal> subjAmount = createNumber("subjAmount", java.math.BigDecimal.class);

    public final StringPath subjectCd = createString("subjectCd");

    public final DatePath<java.sql.Date> transDate = createDate("transDate", java.sql.Date.class);

    public final StringPath txnBrcd = createString("txnBrcd");

    public final StringPath txnDetailSeq = createString("txnDetailSeq");

    public final StringPath txnDetailType = createString("txnDetailType");

    public final StringPath txnDirection = createString("txnDirection");

    public final StringPath volDesc = createString("volDesc");

    public final DatePath<java.sql.Date> volDt = createDate("volDt", java.sql.Date.class);

    public final NumberPath<Integer> volSeq = createNumber("volSeq", Integer.class);

    public final com.querydsl.sql.PrimaryKey<QsqlApGlVolDtlAssHst> primary = createPrimaryKey(assSeq);

    public QsqlApGlVolDtlAssHst(String variable) {
        super(QsqlApGlVolDtlAssHst.class, forVariable(variable), "null", "ap_gl_vol_dtl_ass_hst");
        addMetadata();
    }

    public QsqlApGlVolDtlAssHst(String variable, String schema, String table) {
        super(QsqlApGlVolDtlAssHst.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QsqlApGlVolDtlAssHst(String variable, String schema) {
        super(QsqlApGlVolDtlAssHst.class, forVariable(variable), schema, "ap_gl_vol_dtl_ass_hst");
        addMetadata();
    }

    public QsqlApGlVolDtlAssHst(Path<? extends QsqlApGlVolDtlAssHst> path) {
        super(path.getType(), path.getMetadata(), "null", "ap_gl_vol_dtl_ass_hst");
        addMetadata();
    }

    public QsqlApGlVolDtlAssHst(PathMetadata metadata) {
        super(QsqlApGlVolDtlAssHst.class, metadata, "null", "ap_gl_vol_dtl_ass_hst");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(assistAccountValue, ColumnMetadata.named("ASSIST_ACCOUNT_VALUE").withIndex(4).ofType(Types.VARCHAR).withSize(20));
        addMetadata(assistType, ColumnMetadata.named("ASSIST_TYPE").withIndex(3).ofType(Types.VARCHAR).withSize(10));
        addMetadata(assSeq, ColumnMetadata.named("ASS_SEQ").withIndex(1).ofType(Types.VARCHAR).withSize(64).notNull());
        addMetadata(bizDate, ColumnMetadata.named("BIZ_DATE").withIndex(23).ofType(Types.DATE).withSize(10).notNull());
        addMetadata(branch, ColumnMetadata.named("BRANCH").withIndex(8).ofType(Types.VARCHAR).withSize(40));
        addMetadata(branchNo, ColumnMetadata.named("BRANCH_NO").withIndex(5).ofType(Types.VARCHAR).withSize(9));
        addMetadata(currCd, ColumnMetadata.named("CURR_CD").withIndex(10).ofType(Types.VARCHAR).withSize(3));
        addMetadata(jpaVersion, ColumnMetadata.named("JPA_VERSION").withIndex(20).ofType(Types.INTEGER).withSize(10));
        addMetadata(lastUpdateDate, ColumnMetadata.named("LAST_UPDATE_DATE").withIndex(22).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(org, ColumnMetadata.named("ORG").withIndex(6).ofType(Types.VARCHAR).withSize(12));
        addMetadata(redBlueInd, ColumnMetadata.named("RED_BLUE_IND").withIndex(13).ofType(Types.CHAR).withSize(1));
        addMetadata(refNo, ColumnMetadata.named("REF_NO").withIndex(15).ofType(Types.VARCHAR).withSize(9));
        addMetadata(setupDate, ColumnMetadata.named("SETUP_DATE").withIndex(21).ofType(Types.TIMESTAMP).withSize(19).notNull());
        addMetadata(subjAmount, ColumnMetadata.named("SUBJ_AMOUNT").withIndex(11).ofType(Types.DECIMAL).withSize(17).withDigits(4));
        addMetadata(subjectCd, ColumnMetadata.named("SUBJECT_CD").withIndex(2).ofType(Types.VARCHAR).withSize(40).notNull());
        addMetadata(transDate, ColumnMetadata.named("TRANS_DATE").withIndex(19).ofType(Types.DATE).withSize(10));
        addMetadata(txnBrcd, ColumnMetadata.named("TXN_BRCD").withIndex(9).ofType(Types.VARCHAR).withSize(40));
        addMetadata(txnDetailSeq, ColumnMetadata.named("TXN_DETAIL_SEQ").withIndex(16).ofType(Types.VARCHAR).withSize(64));
        addMetadata(txnDetailType, ColumnMetadata.named("TXN_DETAIL_TYPE").withIndex(17).ofType(Types.CHAR).withSize(1));
        addMetadata(txnDirection, ColumnMetadata.named("TXN_DIRECTION").withIndex(18).ofType(Types.CHAR).withSize(1));
        addMetadata(volDesc, ColumnMetadata.named("VOL_DESC").withIndex(14).ofType(Types.VARCHAR).withSize(80));
        addMetadata(volDt, ColumnMetadata.named("VOL_DT").withIndex(7).ofType(Types.DATE).withSize(10));
        addMetadata(volSeq, ColumnMetadata.named("VOL_SEQ").withIndex(12).ofType(Types.INTEGER).withSize(10));
    }

}

