package net.engining.smartstar.config.test.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.MoreObjects;

import net.engining.pg.support.meta.PropertyInfo;

/**
 * 用户补充信息表
 * @author pg-maven-plugin
 */
@Entity
@Table(name="SS_USR_ADDINF")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SsUsrAddinf implements Serializable {
    private static final long serialVersionUID = 1L;

    @PropertyInfo(name="\u7528\u6237\u5E8F\u53F7", length=64)
    @Id
    @GeneratedValue(generator="GEN_SS_USR_ADDINF")
    @GenericGenerator(name="GEN_SS_USR_ADDINF", strategy="uuid2")
    @Column(name="USR_NO", nullable=false, length=64)
    private String usrNo;

    @PropertyInfo(name="\u66F4\u65B0\u7528\u6237", length=50)
    @Column(name="UPDATE_USER", nullable=false, length=50)
    private String updateUser;

    @PropertyInfo(name="\u66F4\u65B0\u65F6\u95F4")
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="UPDATE_TIME", nullable=false)
    private Date updateTime;

    @PropertyInfo(name="\u4E1A\u52A1\u65E5\u671F")
    @Temporal(value=TemporalType.DATE)
    @Column(name="BIZ_DATE", nullable=true)
    private Date bizDate;

    @PropertyInfo(name="JPA_VERSION")
    @Column(name="JPA_VERSION", nullable=false)
    @Version
    private Integer jpaVersion;

    @PropertyInfo(name="\u667A\u80FD\u661F\u7403\u8D26\u53F7", length=64)
    @Column(name="PU_ID", nullable=false, length=64)
    private String puId;

    @PropertyInfo(name="\u5BA2\u6237\u7F16\u53F7", length=64)
    @Column(name="CUST_ID", nullable=false, length=64)
    private String custId;

    @PropertyInfo(name="\u6CE8\u518C\u65F6\u95F4")
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="REG_TIME", nullable=false)
    private Date regTime;

    @PropertyInfo(name="\u6635\u79F0", length=40)
    @Column(name="NICK_NAME", nullable=true, length=40)
    private String nickName;

    @PropertyInfo(name="\u5934\u50CF")
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name="HEAD_ICON", nullable=true)
    private Blob headIcon;

    @PropertyInfo(name="\u624B\u673A\u8BA4\u8BC1\u6807\u5FD7")
    @Column(name="MOBILE_VAIL_FLAG", nullable=false)
    private Boolean mobileVailFlag;

    @PropertyInfo(name="\u90AE\u4EF6\u8BA4\u8BC1\u6807\u5FD7")
    @Column(name="MAIL_VAIL_FLAG", nullable=false)
    private Boolean mailVailFlag;

    @PropertyInfo(name="\u5B89\u5168\u8BC4\u5206")
    @Column(name="SEC_SCORE", nullable=false)
    private Integer secScore;

    @PropertyInfo(name="\u9500\u552E\u4EE3\u7406\u7F16\u53F7")
    @Column(name="SELLER_ID", nullable=true)
    private Integer sellerId;

    @PropertyInfo(name="\u5FAE\u4FE1\u8D26\u53F7", length=50)
    @Column(name="WCHAT_ID", nullable=true, length=50)
    private String wchatId;

    @PropertyInfo(name="QQ\u8D26\u53F7", length=20)
    @Column(name="QQ_ID", nullable=true, length=20)
    private String qqId;

    @PropertyInfo(name="\u65B0\u6D6A\u5FAE\u535A\u8D26\u53F7", length=50)
    @Column(name="WEIBO_ID", nullable=true, length=50)
    private String weiboId;

    @PropertyInfo(name="\u8EAB\u9AD8")
    @Column(name="HEIGHT", nullable=true)
    private Integer height;

    @PropertyInfo(name="\u4F53\u91CD")
    @Column(name="WEIGHT", nullable=true)
    private Integer weight;

    public static final String _usrNo = "usrNo";

    public static final String _updateUser = "updateUser";

    public static final String _updateTime = "updateTime";

    public static final String _bizDate = "bizDate";

    public static final String _jpaVersion = "jpaVersion";

    public static final String _puId = "puId";

    public static final String _custId = "custId";

    public static final String _regTime = "regTime";

    public static final String _nickName = "nickName";

    public static final String _headIcon = "headIcon";

    public static final String _mobileVailFlag = "mobileVailFlag";

    public static final String _mailVailFlag = "mailVailFlag";

    public static final String _secScore = "secScore";

    public static final String _regChanel = "regChanel";

    public static final String _sellerId = "sellerId";

    public static final String _wchatId = "wchatId";

    public static final String _qqId = "qqId";

    public static final String _weiboId = "weiboId";

    public static final String _height = "height";

    public static final String _weight = "weight";

    /**
     * <p>用户序号</p>
     * <p>###uuid2###</p>
     */
    public String getUsrNo() {
        return usrNo;
    }

    /**
     * <p>用户序号</p>
     * <p>###uuid2###</p>
     */
    public void setUsrNo(String usrNo) {
        this.usrNo = usrNo;
    }

    /**
     * <p>更新用户</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新用户</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * <p>业务日期</p>
     */
    public Date getBizDate() {
        return bizDate;
    }

    /**
     * <p>业务日期</p>
     */
    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>智能星球账号</p>
     * <p>ProfileUser表的主键PU_ID</p>
     */
    public String getPuId() {
        return puId;
    }

    /**
     * <p>智能星球账号</p>
     * <p>ProfileUser表的主键PU_ID</p>
     */
    public void setPuId(String puId) {
        this.puId = puId;
    }

    /**
     * <p>客户编号</p>
     * <p>关联CI_CUSTOMER表的主键，主要的客户个人信息在此表中</p>
     */
    public String getCustId() {
        return custId;
    }

    /**
     * <p>客户编号</p>
     * <p>关联CI_CUSTOMER表的主键，主要的客户个人信息在此表中</p>
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * <p>注册时间</p>
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * <p>注册时间</p>
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * <p>昵称</p>
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * <p>昵称</p>
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * <p>头像</p>
     */
    public Blob getHeadIcon() {
        return headIcon;
    }

    /**
     * <p>头像</p>
     */
    public void setHeadIcon(Blob headIcon) {
        this.headIcon = headIcon;
    }

    /**
     * <p>手机认证标志</p>
     */
    public Boolean getMobileVailFlag() {
        return mobileVailFlag;
    }

    /**
     * <p>手机认证标志</p>
     */
    public void setMobileVailFlag(Boolean mobileVailFlag) {
        this.mobileVailFlag = mobileVailFlag;
    }

    /**
     * <p>邮件认证标志</p>
     */
    public Boolean getMailVailFlag() {
        return mailVailFlag;
    }

    /**
     * <p>邮件认证标志</p>
     */
    public void setMailVailFlag(Boolean mailVailFlag) {
        this.mailVailFlag = mailVailFlag;
    }

    /**
     * <p>安全评分</p>
     */
    public Integer getSecScore() {
        return secScore;
    }

    /**
     * <p>安全评分</p>
     */
    public void setSecScore(Integer secScore) {
        this.secScore = secScore;
    }

    /**
     * <p>销售代理编号</p>
     */
    public Integer getSellerId() {
        return sellerId;
    }

    /**
     * <p>销售代理编号</p>
     */
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * <p>微信账号</p>
     */
    public String getWchatId() {
        return wchatId;
    }

    /**
     * <p>微信账号</p>
     */
    public void setWchatId(String wchatId) {
        this.wchatId = wchatId;
    }

    /**
     * <p>QQ账号</p>
     */
    public String getQqId() {
        return qqId;
    }

    /**
     * <p>QQ账号</p>
     */
    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    /**
     * <p>新浪微博账号</p>
     */
    public String getWeiboId() {
        return weiboId;
    }

    /**
     * <p>新浪微博账号</p>
     */
    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    /**
     * <p>身高</p>
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * <p>身高</p>
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * <p>体重</p>
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * <p>体重</p>
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void fillDefaultValues() {
        if (updateUser == null) {
        	updateUser = "";
        }
        if (updateTime == null) {updateTime = new Date();}
        if (jpaVersion == null) {jpaVersion = 0;}
        if (puId == null) {puId = "";}
        if (custId == null) {custId = "";}
        if (regTime == null) {regTime = new Date();}
        if (mobileVailFlag == null) {mobileVailFlag = false;}
        if (mailVailFlag == null) {mailVailFlag = false;}
        if (secScore == null) {secScore = 0;}
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
        	.addValue(this.usrNo)
        	.addValue(this.updateUser)
        	.addValue(this.updateTime)
        	.addValue(this.bizDate)
        	.addValue(this.jpaVersion)
        	.addValue(this.puId)
        	.addValue(this.custId)
        	.addValue(this.regTime)
        	.addValue(this.nickName)
        	.addValue(this.headIcon)
        	.addValue(this.mobileVailFlag)
        	.addValue(this.mailVailFlag)
        	.addValue(this.secScore)
        	.addValue(this.sellerId)
        	.addValue(this.wchatId)
        	.addValue(this.qqId)
        	.addValue(this.weiboId)
        	.addValue(this.height)
        	.addValue(this.weight)
        	.toString();
    }
}