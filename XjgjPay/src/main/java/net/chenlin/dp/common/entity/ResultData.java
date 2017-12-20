package net.chenlin.dp.common.entity;

import java.io.Serializable;

/**
 * 接口调用返回结果模型类
 *
 * @author Andy Wang
 */
public class ResultData implements Serializable {
    private static final long serialVersionUID = 1L;

    public ResultData() {
        super();
    }

    /**
     * 初始化返回结果
     *
     * @param code    约定的返回码
     * @param success 请求是否成功
     * @param msg     详细消息
     * @param data    返回的数据
     */
    public ResultData(String code, Boolean success, String msg, Object data) {
        super();

        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 初始化返回结果，仅返回状态消息，返回空数据
     *
     * @param code    约定的返回码
     * @param success 请求是否成功
     * @param msg     详细消息
     */
    public ResultData(String code, Boolean success, String msg) {
        super();

        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    private String code; // 约定的返回 code
    private Boolean success; // 请求是否成功
    private String msg; // 详细消息
    private Object data; // 返回的数据

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder("{");
        strb.append("\"code\":").append("\"").append(code).append("\",");
        strb.append("\"success\":").append("\"").append(success).append("\",");
        strb.append("\"msg\":").append("\"").append(msg).append("\",");
        strb.append("\"data\":").append("\"").append(data).append("\"}");
        return strb.toString();
    }

    /**
     * 获取约定的返回 code
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置约定的返回 code
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取请求是否成功
     *
     * @return
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置请求是否成功
     *
     * @param success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 获取详细消息
     *
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置详细消息
     *
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取返回的值
     *
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置返回的值
     *
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }
}
