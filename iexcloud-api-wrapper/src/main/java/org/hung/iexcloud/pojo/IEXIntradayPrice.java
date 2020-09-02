package org.hung.iexcloud.pojo;

import lombok.Data;

@Data
public class IEXIntradayPrice {

	private String date;
	private String minute;
	private String label;
	private Float high;
	private Float low;
	private Float open;
	private Float close;
	private Float average;
	private Long volume;
	private Double notional;
	private Long numberOfTrades;
		
}
