package com.project.cow.member.buy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.project.cow.admin.AdminMenu;
import com.project.cow.constant.Constant;
import com.project.cow.data.LikeItemData;
import com.project.cow.data.MemberData;
import com.project.cow.data.TradeStuffData;
import com.project.cow.data.object.Member;
import com.project.cow.data.object.SellingStuff;
import com.project.cow.data.object.TradeStuff;
import com.project.cow.login.Login;

public class BuyPage {

	public static void buyPage(SellingStuff stuff) {
		Scanner sc = new Scanner(System.in);
		String startDateStr = "", endDateStr = "";
		Member buyer = Login.login;
		com.project.cow.data.object.LikeItem likeItem;

		AdminMenu.printMenu("구매 페이지");
		System.out.println();
		System.out.println("[번호]           [품명]             [상품품질]  [가격]  [판매자]    [거래방법]            [지불방법]           [판매시작일]    [판매마감일]   [찜횟수]");

		System.out.printf(" %4s  %-16s\t\t%s %10s", stuff.getNo(), stuff.getName(), Constant.Condition(stuff.getCondition()), stuff.getPrice());
		
		// 해당 물품의 판매자 정보 출력
		for (Member seller : MemberData.list) {
			if (seller.getNo().equals(stuff.getSellerNo())) {
				System.out.printf(" %6s ", seller.getName());
			}
		}
		System.out.printf("  %-9s  \t %-13s\t%-15s %-15s %3s\r\n", Constant.Method(stuff.getMethod()), Constant.Payment(stuff.getPayment()), stuff.getFrom(), stuff.getUntil(),
				stuff.getLike());

		startDateStr = stuff.getFrom();
		endDateStr = stuff.getUntil();

		boolean loop = true;
		while (loop) {
			AdminMenu.printLine();
			System.out.println("1. 구매하기");
			System.out.println("2. 찜하기");
			System.out.print("번호 입력: ");
			String choice = sc.nextLine();

			if (choice.equals("1")) { // 구매하기
				boolean check=true;
				String checkDateStr="";
				
				while(check) {
				AdminMenu.printLine();
				System.out.println("물품 거래 날짜를 선택하세요.");
				System.out.println("날짜 설정하기");
				AdminMenu.printLine();
				System.out.println("구매가 가능하신 날짜는 " + startDateStr + "부터 " + endDateStr + "까지입니다.");
				System.out.print("구매하고 싶은 날짜 (yyyy-MM-dd): "); // 유효성 검사
				checkDateStr = sc.nextLine();

				boolean flag = checkDateStr.matches("\\d{4}-\\d{2}-\\d{2}");
					if(flag==false) {
						System.out.println("날짜를 올바른 형식으로 입력해 주시기 바랍니다.");
					}else check=false;  //loop 벗어남
				}

				LocalDate startDate = LocalDate.parse(startDateStr);
				LocalDate endDate = LocalDate.parse(endDateStr);
				LocalDate checkDate = LocalDate.parse(checkDateStr);

				if (isWithinDateRange(checkDate, startDate, endDate)) {
					System.out.println("물품 구매를 진행합니다. 선택하신 제품이 마이페이지 알림설정에 등록되었습니다.");

					TradeStuff tradeStuff = new TradeStuff(stuff.getNo(), stuff.getName(), stuff.getCategory(),
							stuff.getPrice(), stuff.getMethod(), stuff.getPayment(), stuff.getCondition(), checkDateStr,
							stuff.getSellerNo(), buyer.getNo());

					TradeStuffData.tradeList.add(tradeStuff); // 한 번에 tradeList에 추가
					TradeStuffData.tradeStuffSave();
					// ->마이페이지 알림설정에 저장되게 하기
					while (loop) {
						AdminMenu.printLine();
						System.out.println("0을 입력하면 이전 화면으로 돌아갑니다.");
						System.out.print("번호 입력 : ");
						String input = sc.nextLine();
						if (input.equals("0")) {
							BuyMenu.FirstScreen(); // 구매자의 처음화면
							loop = false;
							break;
						} else
							System.out.println("올바른 번호를 입력해 주십시오.");
					}

				} else
					System.out.println("해당 기간에 물품을 판매하지 않습니다.");

			} else if (choice.equals("2")) { 
				likeItem = new com.project.cow.data.object.LikeItem((LikeItemData.likeList.size() + 1) + "", stuff.getNo(), buyer.getNo());
				LikeItemData.likeList.add(likeItem);
				LikeItemData.likeItemSave();
				System.out.println("찜 목록에 추가되었습니다.");
				AdminMenu.printLine();
				System.out.println("Enter를 누르면 이전 화면으로 돌아갑니다.");
				sc.nextLine();
				BuyMenu.FirstScreen();

			} else {
				System.out.println("번호를 올바르게 입력해 주십시오.");
			}
		}

	}

	private static boolean isWithinDateRange(LocalDate checkDate, LocalDate startDate, LocalDate endDate) {
		return !checkDate.isBefore(startDate) && !checkDate.isAfter(endDate);
	}

}
