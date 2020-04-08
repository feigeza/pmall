package com.pigeon.pmall.pojo;

import java.util.Date;

import com.pigeon.pmall.service.OrderService;

public class Order {
    private Integer id;

    private String orderCode;

    private String address;

    private String post;

    private String receiver;

    private String mobile;

    private String userMessage;

    private Date createDate;

    private Date payDate;

    private Date deliveryDate;

    private Date confirmDate;

    private Integer uid;

    private String status;
    
    private Float total;
    
    private Float postage;

    private Float reduce;
    
    /**
     * 非数据库字段
     */
    private int totalNumber;//总计数量
    
    /**
     * 将数据库里的status，转换成中文。
     * @return
     */
    public String getStatusDesc(){
        String desc ="未知";
        switch(status){
            case OrderService.WAITPAY:
                desc="待付款";
                break;
            case OrderService.WAITDELIVERY:
                desc="待发货";
                break;
            case OrderService.WAITCONFIRM:
                desc="待收货";
                break;
            case OrderService.WAITREVIEW:
                desc="待评价";
                break;
            case OrderService.FINISH:
                desc="完成";
                break;
            case OrderService.DELETE:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getPostage() {
        return postage;
    }

    public void setPostage(Float postage) {
        this.postage = postage;
    }

    public Float getReduce() {
        return reduce;
    }

    public void setReduce(Float reduce) {
        this.reduce = reduce;
    }

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderCode=" + orderCode + ", address=" + address + ", post=" + post
				+ ", receiver=" + receiver + ", mobile=" + mobile + ", userMessage=" + userMessage + ", createDate="
				+ createDate + ", payDate=" + payDate + ", deliveryDate=" + deliveryDate + ", confirmDate="
				+ confirmDate + ", uid=" + uid + ", status=" + status + ", total=" + total + ", totalNumber="
				+ totalNumber + "]";
	}
    
}