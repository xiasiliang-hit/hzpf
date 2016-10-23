package com.line.bqxd.platform.common;

import com.line.bqxd.platform.client.common.Base;
import com.line.bqxd.platform.client.constant.ResultEnum;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 系统返回结果
 * @author yuanmao.wsj
 * @param <T>
 */
public class BizResult<T>  extends Base implements Serializable  {


	private static final long serialVersionUID = 3141642961415950602L;
	/**
	 * 执行结果，默认为true
	 */
	protected boolean success = false;

	/**
	 * 返回的对象
	 */
	protected T model;
	
	/**
	 * 错误码
	 */
	protected String msgCode;
	
	/**
	 * 错误信息(中文)
	 */
	protected String msgInfo;

	protected String detailInfo;
	public BizResult() {
		super();
	}

	public BizResult(ResultEnum resultEnums) {
		if (resultEnums == ResultEnum.SUCCESS) {
			success = true;
		}
		this.msgCode = resultEnums.getCode();
		this.msgInfo = resultEnums.getDesc();
	}

	public BizResult(T model, boolean success) {
		this.model = model;
		this.success = success;
	}

	public BizResult(String msgCode, String msgInfo, T model) {
		super();
		this.model = model;
		this.msgCode = msgCode;
		this.msgInfo = msgInfo;
	}
	
	public BizResult(boolean success, String msgCode, String msgInfo) {
		super();
		this.success = success;
		this.msgCode = msgCode;
		this.msgInfo = msgInfo;
	}

	public BizResult(boolean success, String msgCode, String msgInfo, T model) {
		this(success,msgCode,msgInfo);
		this.model = model;

	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgInfo() {
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getFormatError(){
		if(StringUtils.isBlank(detailInfo)){
			return msgCode + "-" + msgInfo;
		}else {
			return msgCode + "-" + msgInfo + "-" + detailInfo;
		}
	}
}
