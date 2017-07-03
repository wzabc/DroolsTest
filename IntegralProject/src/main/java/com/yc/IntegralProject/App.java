package com.yc.IntegralProject;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.yc.common.DbHelper;
import com.yc.dao.OrderDao;
import com.yc.dao.UserDao;
import com.yc.pro.Order;
import com.yc.pro.User;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		DbHelper db = new DbHelper();

		// try {
		// String sql = "insert into tUser values(?,?,?,?)";
		// User user = new User();
		// List<Object> params = new ArrayList<>();
		// params.add("9871");
		// params.add("小明");
		// params.add("初级会员");
		// params.add("990");
		// db.doUpdate(sql, params);
		// String sql2 = "select * from tUser";
		// Map<String, Object> map = db.findSingleObject(sql2, null);
		// System.out.println(map.get("U_NAME"));
		//
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules");

		Scanner scan = new Scanner(System.in);
		System.out.println("请输入单价：");
		String danjia = scan.nextLine();
		float price = Float.valueOf(danjia);
		System.out.println();
		Scanner scan2 = new Scanner(System.in);
		System.out.println("请输入个数：");
		String numbers = scan2.nextLine();
		int number = Integer.parseInt(numbers);
		Scanner scan3 = new Scanner(System.in);
		System.out.println("请输会员号：");
		String members = scan3.nextLine();
		int member = Integer.parseInt(members);

		float money = price * number;
		User user = new User();
		UserDao useDao = new UserDao();
		String level = null;
		try {
			Map<String, Object> map = useDao.doLevel(member);
			user.setU_level((String) (map.get("U_LEVEL")));
			// user.setU_id(Integer.parseInt((String) map.get("U_ID")));
			user.setU_id(member);
			user.setU_name((String) (map.get("U_NAME")));
			user.setU_point(Float.parseFloat((String) (map.get("U_POINT"))));
		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Order order = new Order();
		order.setO_count(number);
		order.setO_amountMoney(money);

		Integer flag02 = null;
		if (user.getU_level() == "初级会员" && user.getU_point() >= 500) {
			System.out.println("您的积分已超过500，是否愿意花500积分升级为高级会员？");
			Scanner flag03 = new Scanner(System.in);
			if (flag03.equals("是") || flag03.equals("y") || flag03.equals("Y")) {
				flag02 = 1;
			} else {
				flag02 = 0;
			}

		}

		kSession.insert(flag02);
		kSession.insert(user);
		kSession.insert(order);

		kSession.fireAllRules();

		// 这里是规则里面传出来的数据一定会做的操作
		OrderDao orderDao = new OrderDao();
		// System.out.println(";" + user.getU_point());
		int u_id = user.getU_id();
		float u_point = user.getU_point();
		try {
			boolean flag = useDao.updateUserPoint(u_id, u_point);
			if (flag) {
				System.out.println("插入成功");
			}
			int o_count = order.getO_count();
			float o_amount = order.getO_amountMoney();
			float o_point = order.getO_point();
			boolean flag1 = orderDao.insertOrder(u_id, o_count, o_amount, o_point);
			if (flag1) {
				System.out.println("插入成功");
			}
		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block6
			e.printStackTrace();
		}

	}
}
