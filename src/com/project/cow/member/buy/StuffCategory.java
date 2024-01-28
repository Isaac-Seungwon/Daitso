package com.project.cow.member.buy;
import java.util.*;

import com.project.cow.data.MemberData;
import com.project.cow.data.SellingStuffData;
import com.project.cow.data.object.Member;
import com.project.cow.data.object.SellingStuff;
import com.project.cow.admin.AdminMenu;
import com.project.cow.constant.Constant;

public class StuffCategory {
	/**
	 * 	카테고리별 물품 출력 클래스
	 * @author 이정은
	 * 목적 : 물품을 카테고리별로 나누어 출력하고 판매 페이지와 연결시키는 클래스
	 * 기능 : 카테고리를 선택하면 선택사항에 따라 물품을 출력한다.
	 *        : 유효성 검사를 통해 재입력, 이전화면으로 돌아가기, 판매 페이지로 이동하기가 있다.
	 */
	
	/**
	 * 입력받은 번호에 따라 물품 출력 및 판매 페이지 이동, 돌아가기, 재입력받기를 선택하는 메소드
	 */
	public static void Screen() {
		Scanner sc = new Scanner(System.in);
		boolean loop = true;
		ArrayList<SellingStuff> choiceCategory = new ArrayList<SellingStuff>();

		AdminMenu.printMenu("카테고리 선택하기");
		System.out.println("1.가구/인테리어/생활/주방\t\t2.디지털기기\n3.여성잡화\t\t4.남성잡화\t\t5.가공식품\n6.스포츠/레저\t7.취미/게임/음반\t8.뷰티/미용\n9.반려동물용품\t10.티켓/교환권/도서\t11.기타\n0.돌아가기");
		
		while(loop) {
			System.out.print("번호 입력 : ");
			String input = sc.nextLine();

			if (Integer.parseInt(input) == 0) {
				BuyMenu.FirstScreen();
			} else if (Integer.parseInt(input) > 11) {
				System.out.println();
				System.out.println("Enter를 누르고 알맞은 번호를 다시 입력해 주십시오.");
				sc.nextLine();
				Screen();
			} else {
				System.out.println("[번호]               [품명]     [상품품질]      [가격]    [판매자]         [거래방법]       [지불방법]      [판매시작일]     [판매마감일]   [찜횟수]");

				for (SellingStuff s : SellingStuffData.sellingList) {
					if (s.getCategory().equals(input)) {
						choiceCategory.add(s);
						System.out.printf(" %4s  %-16s\t\t%s %10s", s.getNo(), s.getName(), Constant.Condition(s.getCondition()), s.getPrice());
						
						// 해당 물품의 판매자 정보 출력
						for (Member seller : MemberData.list) {
							if (seller.getNo().equals(s.getSellerNo())) {
								System.out.printf(" %6s ", seller.getName());
							}
						}
						System.out.printf("  %-9s  \t %-13s\t%-15s %-15s %3s\r\n", Constant.Method(s.getMethod()), Constant.Payment(s.getPayment()), s.getFrom(), s.getUntil(),
								s.getLike());
					}
				}
				int flag=0;
				while (loop) {
					System.out.println();
					System.out.println("구매하고 싶은 상품의 키워드를 입력하세요.");
					System.out.println("이전 화면으로 돌아가시려면 0을 입력해 주세요.");
					System.out.print("번호 입력 : ");
					String num = sc.nextLine();

					for(SellingStuff stuff : choiceCategory) {
						if (num.equals("0")) {  
							StuffCategory.Screen();  //이전화면
							break;
						} else if(num.equals(stuff.getNo())){
							System.out.println("판매 페이지로 이동하시려면 Enter를 눌러주세요.");
	                    	sc.nextLine();
	                    	AdminMenu.printLine();
	                        BuyPage.buyPage(stuff);
	                        flag=1;
	                        loop = false;  // 루프 종료
						} else continue; flag=0;
					}
					if(flag==0) {
						System.out.println("번호를 바르게 입력해 주십시오.");
						AdminMenu.printLine();
					}
				}
			}
			loop=false;   //추가
		}
	}

}
