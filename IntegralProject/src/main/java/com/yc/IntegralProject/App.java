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

		// KieServices就是一个中心，通过它来获取的各种对象来完成规则构建、管理和执行等操作
		KieServices ks = KieServices.Factory.get();
		// 可以理解KieContainer就是一个KieBase的容器,KieBase就是一个知识仓库，包含了若干的规则、流程、方法等
		KieContainer kContainer = ks.getKieClasspathContainer();
		// 我们通过KieContainer创建KieSession是一种较为方便的做法，其实他本质上是从KieBase中创建出来
		// KieSession就是应用程序跟规则引擎进行交互的会话通道。
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
			user.setU_id(member);
			user.setU_name((String) (map.get("U_NAME")));
			user.setU_point(Float.parseFloat((String) (map.get("U_POINT"))));
			user.setFlag(Integer.parseInt((String) map.get("TFLAG")));
		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Order order = new Order();
		order.setO_count(number);
		order.setO_amountMoney(money);

		kSession.insert(user);
		kSession.insert(order);

		kSession.fireAllRules();

		// 这里是规则里面传出来的数据一定会做的操作
		OrderDao orderDao = new OrderDao();
		// System.out.println(";" + user.getU_point());

		int u_id = user.getU_id();
		float u_point = user.getU_point();
		try {
			// 改变会员的积分
			boolean flag = useDao.updateUserPoint(u_id, u_point);
			/*
			 * if (flag) { System.out.println("插入成功"); }
			 */
			int o_count = order.getO_count();
			float o_amount = order.getO_amountMoney();
			float o_point = order.getO_point();
			// 插入会员的购买记录
			boolean flag1 = orderDao.insertOrder(u_id, o_count, o_amount, o_point);
			/*
			 * if (flag1) { System.out.println("插入成功"); }
			 */
		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block6
			e.printStackTrace();
		}

	}
}
