package net.chenlin.dp.modules.base.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员绑定银行卡信息
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:16:21
 */
public class MemberBankcardEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 银行账户姓名
     */
    private String bankAccName;

    /**
     * 银行卡号
     */
    private String bankAccCard;

    /**
     * 发卡行编号
     */
    private String bankCode;

    /**
     * 宝付绑定标识号
     */
    private String bfBindId;

    /**
     * 会员id
     */
    private Long memberInfoId;

    /**
     * 是否可充值(0-否；1-是)
     */
    private Integer isRecharge;

    /**
     * 是否可提现(0-否；1-是)
     */
    private Integer isWithdraw;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


    public MemberBankcardEntity() {
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccCard(String bankAccCard) {
        this.bankAccCard = bankAccCard;
    }

    public String getBankAccCard() {
        return bankAccCard;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBfBindId(String bfBindId) {
        this.bfBindId = bfBindId;
    }

    public String getBfBindId() {
        return bfBindId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public Integer getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(Integer isRecharge) {
        this.isRecharge = isRecharge;
    }

    public Integer getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(Integer isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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
