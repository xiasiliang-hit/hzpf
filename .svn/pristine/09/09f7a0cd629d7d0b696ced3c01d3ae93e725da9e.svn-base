package com.line.bqxd.platform.client.dto;

import com.line.bqxd.platform.client.common.Base;

import java.io.Serializable;

/**
 * 系统返回结果
 * @author huangjianfei on 16/5/16.
 * @param <T>
 */
public class Result<T>  extends Base implements Serializable  {

	private static final long serialVersionUID = 6524436118508491059L;

	/**
	 * 错误码
	 */
	protected String code;
	
	/**
	 * 错误信息(中文)
	 */
	protected String desc;

	/**
	 * 返回的对象
	 */
	protected T data;


	public Result() {
		super();
	}

	public Result(String code, String desc, T data) {
		super();
		this.data = data;
		this.code = code;
		this.desc = desc;
	}
	
	public Result(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Result of(String code, String desc){
		return new Result(code,desc);
	}

	public static Result of(String code, String desc,Object data){
		return new Result(code,desc,data);
	}

}
