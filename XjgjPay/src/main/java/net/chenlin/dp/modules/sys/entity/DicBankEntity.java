package net.chenlin.dp.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 银行信息字典
 *
 * @author Andy
 * @email wangdy@mingze.com
 * @url www.mingze.com
 * @date 2017年12月25日 下午3:52:55
 */
public class DicBankEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 银行编号
	 */
	private String bankId;
	
	/**
	 * 银行名称
	 */
	private String bankName;
	

	public DicBankEntity() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankName() {
		return bankName;
	}
	
}
