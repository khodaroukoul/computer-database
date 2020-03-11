package fr.excilys.formation.cdb.service;

import fr.excilys.formation.cdb.models.Pagination;

public class PageCreator {

	public static Pagination pageCreate(int currentPage, int computersPerPage, int noOfComputers) {
		Pagination displayPage = new Pagination();
		
		int delta = 2;
		int noOfPages = (int) Math.ceil(noOfComputers * 1.0 / computersPerPage);
		displayPage.setNoOfPages(noOfPages);
		
		int previous = (currentPage>=2) ? currentPage-1 : 1;
		displayPage.setPreviousPage(previous);
		int next = (currentPage<=noOfPages-1) ? currentPage+1 : noOfPages;
		displayPage.setNextPage(next);
		
		int begin = currentPage - delta;		
		if (begin < 1) {
			begin = 1;
		}
		displayPage.setPageBegin(begin);

		int end = currentPage + delta;
		if (end > noOfPages) {
			end = noOfPages;
		}
		displayPage.setPageEnd(end);
		
		return displayPage;	
	}
	
	public static int[] pageData(String currentPage, String computersPerPage) {
		int[] pageData = {1,10};
		
		if(currentPage != null) {
			pageData[0] = Integer.parseInt(currentPage);
		}

		if(computersPerPage!= null) {
			pageData[1] = Integer.parseInt(computersPerPage);
		}
		return pageData;
		
	}
}