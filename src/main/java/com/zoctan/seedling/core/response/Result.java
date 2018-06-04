package com.zoctan.seedling.core.response;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 *
 * @author Zoctan
 * @date 2018/6/4
 */
public class Result {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据内容，比如列表，实体
     */
    private Object data;

    private Result(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }

    public static class Builder {
        private Integer code;
        private String msg;
        private Object data;

        public Builder(Integer code) {
            this.code = code;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
