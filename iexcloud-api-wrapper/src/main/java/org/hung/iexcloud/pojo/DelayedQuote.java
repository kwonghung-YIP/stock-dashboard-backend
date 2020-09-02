package org.hung.iexcloud.pojo;

import lombok.Data;

@Data
public class DelayedQuote {

	private String symbol;
	private Float delayedPrice;
	private Float high;
	private Float low;
	private Long delayedSize;
	private Long delayedPriceTime;
	private Long totalVolume;
	private Long processedTime;
	
}
