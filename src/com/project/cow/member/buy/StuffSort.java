package com.project.cow.member.buy;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.project.cow.admin.AdminMenu;
import com.project.cow.constant.Constant;
import com.project.cow.data.MemberData;
import com.project.cow.data.SellingStuffData;
import com.project.cow.data.object.Member;
import com.project.cow.data.object.SellingStuff;

public class StuffSort {
	/**
	 * 	물품을 원하는 기준으로 정렬하는 클래스
	 * @author 이정은
	 * 목적 : 물품을 가격낮은순, 가격높은순, 날짜별, 인기별, 나눔으로 나누어 출력하고 판매 페이지와 연결시키는 클래스
	 * 기능 : 유효한 번호를 입력하면 기준에 따른 정렬을 하고, 정렬된 물품을 출력한다.
	 *        : 유효성 검사를 통해 재입력, 이전화면으로 돌아가기, 판매 페이지로 이동하기가 있다.
	 */
	
	/**
	 * 입력받은 번호에 따라 원하는 정렬의 기준을 선택하고, 그에따른 물품을 선택 및 판매 페이지로 이동하는 메소드
	 */
	public static void stuffSortChoice() {
		Scanner sc = new Scanner(System.in);
		
		AdminMenu.printLine();
        System.out.println("물품을 정렬할 기준을 선택하세요.");
        AdminMenu.printOption("가격 낮은 순 정렬", "가격 높은 순 정렬", "날짜별 정렬", "인기별 정렬", "나눔만 보기");
        String input = sc.nextLine();
        AdminMenu.printLine();
		
        if(input.equals("1")) {
    		System.out.println();
    		//Collections.sort(SellingStuffData.sellingList, (a, b) -> Integer.compare(Integer.parseInt(a.getPrice()), Integer.parseInt(b.getPrice())));
			//SellingStuffData.sellingList.sort((a,b) -> Integer.parseInt(a.getPrice()) - Integer.parseInt(b.getPrice()));
			
    		// 가격이 0인 것을 필터링한 후 정렬
    		List<SellingStuff> filteredList = SellingStuffData.sellingList.stream()
    	        .filter(stuff -> Integer.parseInt(stuff.getPrice()) > 0)
    	        .collect(Collectors.toList());
    	    
    	    filteredList.sort((a, b) -> Integer.parseInt(a.getPrice()) - Integer.parseInt(b.getPrice()));
    	    SellingStuffData.sellingList = (ArrayList<SellingStuff>) filteredList; // 필터링된 리스트로 대체
    	}else if(input.equals("2")) {
    		System.out.println();
    		Collections.sort(SellingStuffData.sellingList, (a, b) -> Integer.compare(Integer.parseInt(b.getPrice()), Integer.parseInt(a.getPrice())));
    	}else if(input.equals("3")) {
    		System.out.println();
    		Collections.sort(SellingStuffData.sellingList, new DateComparator());
    		//SellingStuffData.sellingList.sort((a,b) -> Integer.parseInt(b.getFrom().replace("-","")) - Integer.parseInt(a.getFrom().replace("-","")));
    	}else if(input.equals("4")) {
    		System.out.println();
    		SellingStuffData.sellingList.sort((a,b) -> Integer.parseInt(b.getLike()) - Integer.parseInt(a.getLike()));
    	}else if(input.equals("5")) {
    		System.out.println();
    		// 가격이 0인 것만 필터링한 후 정렬
    		List<SellingStuff> filteredList2 = SellingStuffData.sellingList.stream()
    	        .filter(stuff -> Integer.parseInt(stuff.getPrice()) == 0)
    	        .collect(Collectors.toList());
    	    
    	    filteredList2.sort((a, b) -> Integer.parseInt(a.getPrice()) - Integer.parseInt(b.getPrice()));
    	    SellingStuffData.sellingList = (ArrayList<SellingStuff>) filteredList2; // 필터링된 리스트로 대체
    	}else if(input.equals("0")) {
    		System.out.println();
    		BuyMenu.Screen();
   		}else {
   			System.out.println();
   			System.out.println("올바른 번호를 입력하세요.");
   			stuffSortChoice();
   		}

        System.out.println("[번호]           [품명]             [상품품질]  [가격]  [판매자]    [거래방법]            [지불방법]           [판매시작일]    [판매마감일]   [찜횟수]");
		int index=0;
		for (SellingStuff s : SellingStuffData.sellingList) {
				System.out.printf(" %4s  %-16s\t\t%s %10s", s.getNo(), s.getName(), Constant.Condition(s.getCondition()), s.getPrice());
				
				// 해당 물품의 판매자 이름 출력
				for (Member seller : MemberData.list) {
					if (seller.getNo().equals(s.getSellerNo())) {
						System.out.printf(" %6s ", seller.getName());
					}
				}
				System.out.printf("  %-9s  \t %-13s\t%-15s %-15s %3s\r\n", Constant.Method(s.getMethod()), Constant.Payment(s.getPayment()), s.getFrom(), s.getUntil(),
						s.getLike());
				
				
				index++;
				if(index==100) break;
		}
		
       	int flag=0;
       	boolean loop = true;
		while (loop) {
			System.out.println();
			System.out.println("구매하고 싶은 상품의 상품 번호를 입력하세요.");
			System.out.println("이전 화면으로 돌아가시려면 0을 입력해 주세요.");
			System.out.print("번호 입력: ");
			String num = sc.nextLine();

			for(SellingStuff stuff : SellingStuffData.sellingList) {
				if (num.equals("0")) {  
					StuffSort.stuffSortChoice();  //이전화면
					break;
				} else if(num.equals(stuff.getNo())){
					AdminMenu.printLine();
					System.out.println("Enter를 입력하면 구매 페이지로 이동합니다.");
                	sc.nextLine();
                    BuyPage.buyPage(stuff);  //구매 페이지로 이동.
                    flag=1;
                    loop = false;  // 루프 종료
				} else continue; flag=0;
			}
			if(flag==0) {
				System.out.println("번호를 바르게 입력해 주십시오.");
				AdminMenu.printLine();
				stuffSortChoice();
			}
		}
       
	}
	/**
	 * 유효하지 않은 번호를 입력 받을 시 처리해주는 예외 메소드
	 */
	public static void Exception() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			AdminMenu.printLine();
			System.out.println("이전화면으로 돌아가시려면 0을 입력해주세요.");
			System.out.print("번호 입력: ");
			int num=sc.nextInt();
			
			if(num==0) {
				stuffSortChoice();
			}else System.out.println("알맞은 번호를 입력하십시오.");
		}
	}
	/**
	 * 
	 * @author leeje
	 * 날짜별 정렬을 택할 시, 물품이 가지고 있는 날짜들을 비교해서 정렬해주는 메소드
	 */
	static class DateComparator implements Comparator<SellingStuff> {
	    @Override
	    public int compare(SellingStuff a, SellingStuff b) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date dateA = dateFormat.parse(a.getFrom());
	            Date dateB = dateFormat.parse(b.getFrom());
	            return dateA.compareTo(dateB);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0; // 날짜 변환 실패 시 같은 것으로 취급
	        }
	    }
	}
	
}
