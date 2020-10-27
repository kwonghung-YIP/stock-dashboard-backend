package org.hung.iexcloud.pojo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class IEXHistoricalPrice {

	//The API Reference
	//https://iexcloud.io/docs/api/#historical-prices
	private LocalDate date;
	
	private Float open;
	private Float high;
	private Float low;
	private Float close;
	private Long volume;
	
	private Float uOpen;
	private Float uHigh;
	private Float uLow;
	private Float uClose;
	private Long uVolume;
	
	private String currency;
	private String label;
	private Float change;
	private Float changePercent;
	private Float changeOverTime;
}
