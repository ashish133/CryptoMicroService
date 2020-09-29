package com.crypto.model;

import java.util.Comparator;

public class SellSummaryComparator implements Comparator<SummaryOrder> {

	@Override
	public int compare(SummaryOrder o1, SummaryOrder o2) {
		int comparision = 0;
		if( o1.getPrice().subtract( o2.getPrice()).doubleValue() > 0){
			comparision  = 1;
		}else if (o1.getPrice().subtract( o2.getPrice()).doubleValue() < 0){
			comparision = -1;
		}
		return comparision;
	}

}
