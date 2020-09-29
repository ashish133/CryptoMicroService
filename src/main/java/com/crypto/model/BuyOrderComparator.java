package com.crypto.model;

import java.util.Comparator;

public class BuyOrderComparator implements Comparator<CryptoOrder> {

	@Override
	public int compare(CryptoOrder o1, CryptoOrder o2) {
		int comparision = 0;
		if( o1.getPricePerCoin().subtract( o2.getPricePerCoin()).doubleValue() > 0){
			comparision  = -1;
		}else if (o1.getPricePerCoin().subtract(o2.getPricePerCoin()).doubleValue() < 0){
			comparision = 1;
		}
		return comparision;
	}

}
