package net.engining.smartstar.config.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QSsUsrAddinf is a Querydsl query type for SsUsrAddinf
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSsUsrAddinf extends EntityPathBase<SsUsrAddinf> {

    private static final long serialVersionUID = 408555797L;

    public static final QSsUsrAddinf ssUsrAddinf = new QSsUsrAddinf("ssUsrAddinf");

    public final DatePath<java.util.Date> bizDate = createDate("bizDate", java.util.Date.class);

    public final StringPath custId = createString("custId");

    public final SimplePath<java.sql.Blob> headIcon = createSimple("headIcon", java.sql.Blob.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Integer> jpaVersion = createNumber("jpaVersion", Integer.class);

    public final BooleanPath mailVailFlag = createBoolean("mailVailFlag");

    public final BooleanPath mobileVailFlag = createBoolean("mobileVailFlag");

    public final StringPath nickName = createString("nickName");

    public final StringPath puId = createString("puId");

    public final StringPath qqId = createString("qqId");

    public final DateTimePath<java.util.Date> regTime = createDateTime("regTime", java.util.Date.class);

    public final NumberPath<Integer> secScore = createNumber("secScore", Integer.class);

    public final NumberPath<Integer> sellerId = createNumber("sellerId", Integer.class);

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public final StringPath updateUser = createString("updateUser");

    public final StringPath usrNo = createString("usrNo");

    public final StringPath wchatId = createString("wchatId");

    public final StringPath weiboId = createString("weiboId");

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QSsUsrAddinf(String variable) {
        super(SsUsrAddinf.class, forVariable(variable));
    }

    public QSsUsrAddinf(Path<? extends SsUsrAddinf> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSsUsrAddinf(PathMetadata metadata) {
        super(SsUsrAddinf.class, metadata);
    }

}

