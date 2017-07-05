package com.yc.dao;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yc.common.DbHelper;

public class OrderDao {
	private static DbHelper db = new DbHelper();

	// 插入订单表
	public boolean insertOrder(int u_id, int o_count, float o_amount, float o_point)
	        throws FileNotFoundException, SQLException {
		String sql = "insert into tOrders values(seq_o_id.nextval,?,?,?,?,sysdate)";
		List<Object> params = new ArrayList<>();
		params.add(u_id);
		params.add(o_count);
		params.add(o_amount);
		params.add(o_point);

		int result = db.doUpdate(sql, params);
		if (result > 0) {
			return true;
		}
		return false;
	}

}
