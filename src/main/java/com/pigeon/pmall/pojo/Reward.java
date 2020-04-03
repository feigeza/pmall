package com.pigeon.pmall.pojo;

import java.util.Date;

public class Reward {
    private Integer id;

    private Integer uid;

    private String operation;

    private Float total;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	@Override
	public String toString() {
		return "Reward [id=" + id + ", uid=" + uid + ", operation=" + operation + ", total=" + total + ", createDate="
				+ createDate + "]";
	}
    
}