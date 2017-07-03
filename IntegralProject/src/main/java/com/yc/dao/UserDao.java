package com.yc.dao;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.common.DbHelper;

public class UserDao {
	private static DbHelper db = new DbHelper();

	// 判断会员等级
	public Map<String, Object> doLevel(int member) throws FileNotFoundException, SQLException {
		String sql = "select u_id,u_name,u_level,u_point from tUser where u_id=?";
		List<Object> params = new ArrayList<>();
		params.add(member);

		Map<String, Object> map = db.findSingleObject(sql, params);
		return map;

	}

	// 更新用户表的积分
	public boolean updateUserPoint(int member, float point) throws FileNotFoundException, SQLException {
		String sql = "update tUser set u_point=? where u_id=?";
		List<Object> params = new ArrayList<>();
		params.add(point);
		params.add(member);

		int result = db.doUpdate(sql, params);
		if (result > 0) {
			return true;
		}
		return false;
	}

	// 更新用户表的等级
	public boolean updateUserLevel(int member, String level) throws FileNotFoundException, SQLException {
		String sql = "update tUser set u_level=? where u_id=?";
		List<Object> params = new ArrayList<>();
		params.add(level);
		params.add(member);

		int result = db.doUpdate(sql, params);
		if (result > 0) {
			return true;
		}
		return false;
	}
}
