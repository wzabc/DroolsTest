package com.yc.pro;

public class User {

	private int u_id;
	private String u_name;
	private String u_level;
	private float u_point;

	public void recordPointLog(int u_id, String type, float u_point) {
		System.err.println("会员：" + u_id + "的类型为" + type + "的积分操作记录" + ",总共的积分：" + u_point);

	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_level() {
		return u_level;
	}

	public void setU_level(String u_level) {
		this.u_level = u_level;
	}

	public float getU_point() {
		return u_point;
	}

	public void setU_point(float u_point) {
		this.u_point = u_point;
	}

	public User(int u_id, String u_name, String u_level, float u_point) {
		super();
		this.u_id = u_id;
		this.u_name = u_name;
		this.u_level = u_level;
		this.u_point = u_point;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [u_id=" + u_id + ", u_name=" + u_name + ", u_level=" + u_level + ", u_point=" + u_point + "]";
	}
}
