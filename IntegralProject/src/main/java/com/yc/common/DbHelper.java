package com.yc.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbHelper {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private Object columnNames;
	// 加载驱动
	static {
		try {
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(MyProperties.getInstance().getProperty("driverClass"));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取连接对象
	public Connection getConn() throws SQLException {
		// conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl",
		// "scott", "a");
		try {
			conn = DriverManager.getConnection(MyProperties.getInstance().getProperty("url"),
			        MyProperties.getInstance());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	// 关闭所有数据库对象
	public void CloseAll(ResultSet rs, PreparedStatement ptmt, Connection conn) throws SQLException {
		if (null != rs) {

			rs.close();

		}
		if (ptmt != null) {

			ptmt.close();

		}
		if (conn != null) {

			conn.close();

		}
	}

	/*
	 * 查询 ：返回多个对象 Map<K,V> id 123 id 124 List<String> 123
	 */
	// Map<String,List<String>> map;
	public List<Map<String, Object>> findMultiObject(String sql, List<Object> params)
	        throws SQLException, FileNotFoundException {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = null;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			setParames(pstmt, params);
			rs = pstmt.executeQuery();
			List<String> columnNames = getAllColumnNames(rs);
			while (rs.next()) {
				map = new HashMap<>();
				for (String cn : columnNames) {
					map.put(cn, rs.getString(cn));
				}
				list.add(map);
			}
		}

		finally {
			CloseAll(rs, pstmt, conn);
		}
		return list;

	}

	/*
	 * 查询：返回一个map对象，只能查找出一个对象
	 */

	public Map<String, Object> findSingleObject(String sql, List<Object> params)
	        throws SQLException, FileNotFoundException {
		Map<String, Object> map = null;
		conn = getConn();
		try {
			pstmt = conn.prepareStatement(sql);
			setParames(pstmt, params);
			rs = pstmt.executeQuery();
			List<String> columnNmaes = getAllColumnNames(rs);
			if (rs.next()) {
				map = new HashMap<>();
				for (String cn : columnNmaes) {
					map.put(cn, rs.getString(cn));
				}
			}
		} finally {
			CloseAll(rs, pstmt, conn);
		}
		return map;
	}

	// 获取列名

	public List<String> getAllColumnNames(ResultSet rs) throws SQLException {
		List<String> columnNames = new ArrayList<>();
		if (null != rs) {
			int count = rs.getMetaData().getColumnCount();
			for (int i = 0; i < count; i++) {
				columnNames.add(rs.getMetaData().getColumnName(i + 1));
			}
		}

		return columnNames;
	}

	/*
	 * 增删改操作 单条sql语句
	 */

	public int doUpdate(String sql, List<Object> params) throws SQLException, FileNotFoundException {
		conn = this.getConn();
		pstmt = conn.prepareStatement(sql);
		setParames(pstmt, params);
		int result = pstmt.executeUpdate();
		CloseAll(null, pstmt, conn);
		return result;
	}
	/*
	 * 设置参数值 注意：出入的参数值：参数值添加到params中时，一定要与？顺序一致
	 */

	public void setParames(PreparedStatement pstmt, List<Object> params) throws SQLException, FileNotFoundException {
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				if (params.get(i) instanceof File) {// 是否为对象文件
					File file = (File) params.get(i);
					InputStream in = new FileInputStream(file);
					pstmt.setBinaryStream(i + 1, in, (int) (file.length()));
				} else {
					pstmt.setString(i + 1, params.get(i).toString());
				}

			}
		}

	}

	/*
	 * 批处理 一次执行多条sql语句 要么一起成功 要么一起失败
	 */
	public int doUpdate(List<String> sqls, List<List<Object>> params) throws SQLException, FileNotFoundException {
		conn = this.getConn();
		int result = -1;
		try {
			conn.setAutoCommit(false);// 关闭隐式事务
			if (null != sqls && sqls.size() > 0) {
				for (int i = 0; i < sqls.size(); i++) {
					String sql = sqls.get(i);
					pstmt = conn.prepareStatement(sql);
					// 设置参数
					setParames(pstmt, params.get(i));
					result = pstmt.executeUpdate();
				}
			}
			conn.commit();// 手动提交
		} catch (SQLException e) {
			conn.rollback();// 事务回滚
			throw e;

		} finally {
			conn.setAutoCommit(true);// 恢复现场
			CloseAll(null, pstmt, conn);
		}
		return result;

	}

	/*
	 * 聚合函数查询 select count(*) from userinfo
	 */
	public double getCount(String sql, List<Object> params) throws SQLException, FileNotFoundException {
		double result = 0;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			setParames(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} finally {
			CloseAll(rs, pstmt, conn);
		}
		return result;
	}
}
