package com.project.cow;

import java.util.Scanner;

import com.project.cow.admin.AdminMenu;
import com.project.cow.admin.MemberQuestion;
import com.project.cow.data.BlackListData;
import com.project.cow.data.Data;
import com.project.cow.data.KeyWordData;
import com.project.cow.data.LikeItemData;
import com.project.cow.data.MemberData;
import com.project.cow.data.SellingStuffData;
import com.project.cow.data.SoldOutStuffData;
import com.project.cow.data.TradeStuffData;
import com.project.cow.find.FindID;
import com.project.cow.find.FindPW;
import com.project.cow.join.Join;
import com.project.cow.login.Login;

public class Main {

	public static void main(String[] args) {

		Data.memberLoad();
		SellingStuffData.load();
		SoldOutStuffData.load();
		LikeItemData.likeItemLoad();
		KeyWordData.keyWordListLoad();
		BlackListData.blackListLoad();
		MemberQuestion.loadFAQInfo();
		Data.soldOutLoad();
		Data.keyWordListLoad();
		Data.reviewLoad();
		Data.badReviewLoad();
		Data.tradeStuffLoad();
		TradeStuffData.tradeStuffLoad();
		MemberData.load();

		// 메인 화면 시작
		MainScreen();
	}

	/**
	 * 메인 화면을 표시하고 사용자 입력을 처리하는 메소드
	 */
	public static void MainScreen() {
		boolean loop = true;
		Scanner scan = new Scanner(System.in);

		Join joinInstance = new Join();

		while (loop) {

			System.out.println("" + "\t\t\t\t\t _______       ___       __  .___________.     _______.  ______   \r\n"
					+ "\t\t\t\t\t|       \\     /   \\     |  | |           |    /       | /  __  \\  \r\n"
					+ "\t\t\t\t\t|  .--.  |   /  ^  \\    |  | `---|  |----`   |   (----`|  |  |  | \r\n"
					+ "\t\t\t\t\t|  |  |  |  /  /_\\  \\   |  |     |  |         \\   \\    |  |  |  | \r\n"
					+ "\t\t\t\t\t|  '--'  | /  _____  \\  |  |     |  |     .----)   |   |  `--'  | \r\n"
					+ "\t\t\t\t\t|_______/ /__/     \\__\\ |__|     |__|     |_______/     \\______/  \r\n"
					+ "\t\t\t\t\t                                                                  ");
			AdminMenu.printMenu("ＤＡＩＴＳＯ");

			if (Login.login == null) {
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");
				System.out.println("3. 아이디 찾기");
				System.out.println("4. 비밀번호 찾기");
				System.out.println("0. 프로그램 종료");
			}

			AdminMenu.printLine();
			System.out.print("번호 입력: ");
			String sel = scan.nextLine();
			System.out.println();

			if (sel.equals("1")) {
				Login.login();
			} else if (sel.equals("2")) {
				joinInstance.join();
			} else if (sel.equals("3")) {
				FindID.findId();
			} else if (sel.equals("4")) {
				FindPW.findpw();
			} else if (sel.equals("5")) {
				System.exit(0);
			} else {

				System.out.println("번호를 확인하고 다시 입력해주세요.");
				continue; // 잘못된 입력일 경우 루프의 처음으로 다시 돌아간다

			}
			// loop = false;
		}
		System.out.println("프로그램 종료");
	}

}
