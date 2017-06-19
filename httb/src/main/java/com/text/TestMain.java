package com.text;


public class TestMain {
	public static void main(String[] args) {


	/*	for (int i = 0; i < 3; i++) {
			InsertThread it = new InsertThread(i);
			it.start();
		}
*/
		BaseDao bd = new BaseDao();
		search(bd);
		/*BaseDao bd = new BaseDao();
		long start = System.currentTimeMillis();
		bd.search("5", 5, "5", null);
		long end = System.currentTimeMillis();
		System.out.println(end-start);*/


		//bd.searchCount();

		//search(bd);

		/*st = System.currentTimeMillis();
		bd.search("0", null, null, null);
		end = System.currentTimeMillis();
		System.out.println(end-st);*/
	}

	private static void search(BaseDao bd) {
		long st = System.currentTimeMillis();
		bd.search();
		long end = System.currentTimeMillis();
		System.out.println(end-st);
	}

}
