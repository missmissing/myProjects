package com.huatu.analysis.util;

public class ArrayLongIns {
	private long[] theArray;// 定义数组
	private int nElems;// 数组中的元素个数

	/**
	 * 初始化
	 *
	 * @param max
	 */
	public ArrayLongIns(int max) {
		theArray = new long[max];
		nElems = 0;
	}

	/**
	 * 为数组赋值
	 *
	 * @param value
	 */
	public void insert(long value) {
		theArray[nElems] = value;
		nElems++;
	}

	/**
	 * 显示数组元素
	 */
	public void display() {
		System.out.print("A=");
		for (int j = nElems-1; j >= 0 ; j--) {
			System.out.print(theArray[j] + " ");
		}
		System.out.println(theArray.length);
		System.out.println("");
	}

	public int getIndex (int a){
		for (int i = 0; i < theArray.length-1; i++) {
			if(theArray[i] <= a && theArray[i+1] >= a){
				return i;
			}
		}
		return 0;
	}

	/**
	 * 快速排序主方法
	 */
	public void quickSort() {
		recQuickSort(0, nElems - 1);
	}

	/**
	 * 快速排序需递归调用的方法
	 *
	 * @param left
	 * @param right
	 */
	public void recQuickSort(int left, int right) {
		if (right - left <= 0) {
			return;
		} else {
			long pivot = theArray[right];

			int partition = partitionIt(left, right, pivot);
			recQuickSort(left, partition - 1);
			recQuickSort(partition + 1, right);
		}
	}

	/**
	 * 快速排序划分的核心方法
	 *
	 * @param left
	 * @param right
	 * @param pivot
	 * @return
	 */
	public int partitionIt(int left, int right, long pivot) {
		int leftPtr = left - 1;
		int rightPtr = right;
		while (true) {
			while (theArray[++leftPtr] < pivot)
				;
			while (rightPtr > 0 && theArray[--rightPtr] > pivot)
				;
			if (leftPtr >= rightPtr) {
				break;
			} else {
				swap(leftPtr, rightPtr);
			}
		}
		swap(leftPtr, right);
		return leftPtr;
	}

	/**
	 * 交换数据中两个位置的数据
	 *
	 * @param dex1
	 * @param dex2
	 */
	public void swap(int dex1, int dex2) {
		long temp = theArray[dex1];
		theArray[dex1] = theArray[dex2];
		theArray[dex2] = temp;
	}

	/**
	 *
	 * getTheArray(获取数组)
	 * @return
	 */
	public long[] getTheArray() {
		return theArray;
	}
}
