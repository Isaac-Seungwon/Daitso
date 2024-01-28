package com.project.cow.member.buy;
import java.util.*;

import com.project.cow.admin.AdminMenu;
import com.project.cow.constant.Constant;
import com.project.cow.data.MemberData;
import com.project.cow.data.SellingStuffData;
import com.project.cow.data.object.Member;
import com.project.cow.data.object.SellingStuff;
import com.project.cow.mypage.BuyList;

public class StuffSearch {
	/**
	 * 	원하는 물품 검색 클래스
	 * @author 이정은
	 * 목적 : 원하는 물품의 키워드를 입력, 관련 물품들을 출력하여 판매 페이지와 연결시키는 클래스
	 * 기능 : 원하는 키워드를 입력하면 관련 물품들을 출력한다.
	 *        : 유효성 검사를 통해 이전화면으로 돌아가기, 판매 페이지로 이동하기가 있다.
	 *        : 또한 입력을 받을 시 띄어쓰기나 대소문자 상관없이 입력받게 한다.
	 */
	
	/**
	 * 입력받은 키워드에 따라 물품 출력 및 판매 페이지 이동, 돌아가기, 재입력받기를 선택하는 메소드
	 */
    public static void search() {
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        ArrayList<SellingStuff> sellChoice = new ArrayList<SellingStuff>();

        AdminMenu.printLine();
        System.out.println("구매하고 싶은 상품의 키워드를 입력하세요.");
        System.out.println("0을 입력하면 이전 화면으로 돌아갑니다.");
        System.out.print("검색 키워드: ");
        String input = sc.nextLine();
        AdminMenu.printLine();

        for (SellingStuff s : SellingStuffData.sellingList) {
        	if (input.equals("0")) {
                BuyMenu.Screen();
                sc.close();  
            }
            if (s.getName().toUpperCase().replace(" ", "").contains(input.toUpperCase().replace(" ",""))) {
                sellChoice.add(s);
                System.out.println("[번호]           [품명]             [상품품질]  [가격]  [판매자]    [거래방법]            [지불방법]           [판매시작일]    [판매마감일]   [찜횟수]");
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
        while (loop) {
            int flag = 0;
            if (sellChoice.isEmpty()) {
                System.out.println("검색 결과가 없습니다. 알맞은 키워드를 입력하세요.");
                search();
            } else {
            	System.out.println();
            	AdminMenu.printLine();
                System.out.println("구매하고 싶은 상품의 번호를 입력하세요.");
                System.out.println("0을 입력하면 이전 화면으로 돌아갑니다.");
                System.out.print("번호 입력: ");
                String num = sc.nextLine();
                for (SellingStuff stuff : sellChoice) {
                   /*
                    BuyList buyList = new BuyList();
                    buyList.buyTest(stuff);
*/
                    if (num.equals("0")) {
                    	search();   //이전화면
                        break;  
                    } else if (num.equals(stuff.getNo())) {

                    	System.out.println("Enter를 누르면 이전 화면으로 돌아갑니다.");
                    	sc.nextLine();
                        BuyMenu.FirstScreen(); //구매 페이지로 이동.
                        flag=1;
                        loop = false;  // 루프 종료
                        //break; // 검색 루프도 종료
                    } else continue; flag=0;   
                }
                if(flag==0) System.out.println("\n알맞은 판매 번호를 다시 입력하십시오.");
            }
        }
        
        sc.close();  
    }
}
