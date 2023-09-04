package com.project.cow.member;

import java.util.Scanner;

import com.project.cow.admin.AdminMenu;
import com.project.cow.data.object.Member;
import com.project.cow.login.Login;
import com.project.cow.member.buy.BuyMenu;
import com.project.cow.member.sell.SellMenu;
import com.project.cow.mypage.MyPageList;
import com.project.cow.mypage.User;

public class MemberMenu {
	
	Member member = Login.login;
	static User user = Login.user;
		
	public static void membermenu() {

		SellMenu sellMenu = new SellMenu();
		Scanner scan = new Scanner(System.in);
		
		AdminMenu.printMenu("회원 메뉴");
		
		System.out.println("1. 구매하기");
		System.out.println("2. 판매하기");
		System.out.println("3. 마이페이지");
		System.out.println("4. 제3자 보관함 위치 보기");
		System.out.println("0. 로그아웃");
		
		AdminMenu.printLine();
		System.out.print("번호 입력:");
		String sel = scan.nextLine();
		
		if(sel.equals("1")) {	
			BuyMenu.FirstScreen();
		}else if (sel.equals("2")) {
			sellMenu.Screen();
		}else if (sel.equals("3")) {
			MyPageList myPageList = new MyPageList(user);
	        myPageList.myPageScreen();
		}else if (sel.equals("4")) {
			Locker locker = new Locker();
			locker.screen();
		}else if(sel.equals("0")){
			Login.logout();
		}
		else {
			System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			membermenu();
		}
		
	}

}
