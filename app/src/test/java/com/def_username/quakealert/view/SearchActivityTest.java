package com.def_username.quakealert.view;

import org.junit.Assert;
import org.junit.Test;

public class SearchActivityTest {

	@Test
	public void getSortMethodTestCase1() {
		SearchActivityMock.setSortMethod("time", "asc");
		String returned = SearchActivityMock.getSortMethod();
		Assert.assertEquals("time-asc", returned);
	}

	@Test
	public void getSortMethodTestCase2() {
		SearchActivityMock.setSortMethod("magnitude", "asc");
		String returned = SearchActivityMock.getSortMethod();
		Assert.assertEquals("magnitude-asc", returned);
	}

	@Test
	public void getSortMethodTestCase3() {
		SearchActivityMock.setSortMethod("time", "");
		String returned = SearchActivityMock.getSortMethod();
		Assert.assertEquals("time", returned);
	}

	@Test
	public void getSortMethodTestCase4() {
		SearchActivityMock.setSortMethod("magnitude", "");
		String returned = SearchActivityMock.getSortMethod();
		Assert.assertEquals("magnitude", returned);
	}

}

class SearchActivityMock {

	private static String sortByCategory = "", sortByOrder = "";

	public static String getSortMethod() {
		return (sortByOrder.equals("") ? sortByCategory : sortByCategory + "-" + sortByOrder);
	}

	public static void setSortMethod(String s1, String s2) {
		sortByCategory = s1;
		sortByOrder = s2;
	}
	
}
