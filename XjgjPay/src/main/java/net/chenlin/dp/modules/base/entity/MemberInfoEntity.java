package net.chenlin.dp.modules.base.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月08日 上午10:57:51
 */
public class MemberInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    private Long id;

    /**
     * 会员编号
     */
    private String memberId;

    /**
     * 会员类型
     */
    private Integer memberType;

    /**
     * 会员名
     */
    private String memberName;

    /**
     * 会员卡号
     */
    private String cardId;

    /**
     * 银行卡绑定手机号
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 身份证类型(默认1为身份证)
     */
    private Integer idCardType;

    /**
     * 可用标识(0-不可用;1-可用)
     */
    private Integer isAvailable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 会员地址
     */
    private String memberAddress;

    /**
     * 商品去向地址
     */
    private String toCorpAddress;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


    public MemberInfoEntity() {
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public void setToCorpAddress(String toCorpAddress) {
        this.toCorpAddress = toCorpAddress;
    }

    public String getToCorpAddress() {
        return toCorpAddress;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

}
