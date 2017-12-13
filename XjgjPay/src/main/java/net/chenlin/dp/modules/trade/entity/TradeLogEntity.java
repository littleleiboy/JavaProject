package net.chenlin.dp.modules.trade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 交易记录
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月12日 下午4:59:03
 */
public class TradeLogEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 交易记录id
	 */
	private Long id;
	
	/**
	 * 交易流水号
	 */
	private String transSn;
	
	/**
	 * 交易类型(1-充值;2-提款;3-消费;4-转账;5-收款)
	 */
	private Integer transType;
	
	/**
	 * 商户订单号
	 */
	private String sellerOrderId;
	
	/**
	 * 交易金额
	 */
	private BigDecimal amtMoney;
	
	/**
	 * 支付方式(1-宝付;2-西郊结算系统)
	 */
	private Integer payModeId;
	
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
	 * 备注
	 */
	private String remark;
	
	/**
	 * 交易时间
	 */
	private Date gmtCreate;
	

	public TradeLogEntity() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setTransSn(String transSn) {
		this.transSn = transSn;
	}
	
	public String getTransSn() {
		return transSn;
	}
	
	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	
	public Integer getTransType() {
		return transType;
	}
	
	public void setSellerOrderId(String sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}
	
	public String getSellerOrderId() {
		return sellerOrderId;
	}
	
	public void setAmtMoney(BigDecimal amtMoney) {
		this.amtMoney = amtMoney;
	}
	
	public BigDecimal getAmtMoney() {
		return amtMoney;
	}
	
	public void setPayModeId(Integer payModeId) {
		this.payModeId = payModeId;
	}
	
	public Integer getPayModeId() {
		return payModeId;
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
	
}
