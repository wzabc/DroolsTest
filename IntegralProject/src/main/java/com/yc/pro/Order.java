package com.yc.pro;

import java.util.Date;

public class Order {

	private Integer o_id;
	private int u_id;
	private int o_count;
	private float o_amountMoney;
	private float o_point;
	private Date buyDate;

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public void orderPoint(int id, float o_amountMoney, float point) {
		System.out.println("会员:" + id + "本次消费:" + o_amountMoney + ",本次所得积分为:" + o_point);
	}

	public Integer getO_id() {
		return o_id;
	}

	public void setO_id(Integer o_id) {
		this.o_id = o_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getO_count() {
		return o_count;
	}

	public void setO_count(int o_count) {
		this.o_count = o_count;
	}

	public float getO_amountMoney() {
		return o_amountMoney;
	}

	public void setO_amountMoney(float o_amountMoney) {
		this.o_amountMoney = o_amountMoney;
	}

	public float getO_point() {
		return o_point;
	}

	public void setO_point(float o_point) {
		this.o_point = o_point;
	}

	public Order() {
		super();
	}

	@Override
	public String toString() {
		return "Order [o_id=" + o_id + ", u_id=" + u_id + ", o_count=" + o_count + ", o_amountMoney=" + o_amountMoney
		        + ", o_point=" + o_point + ", buyDate=" + buyDate + "]";
	}

	public Order(Integer o_id, int u_id, int o_count, float o_amountMoney, float o_point, Date buyDate) {
		super();
		this.o_id = o_id;
		this.u_id = u_id;
		this.o_count = o_count;
		this.o_amountMoney = o_amountMoney;
		this.o_point = o_point;
		this.buyDate = buyDate;
	}

}
