package org.hung.iexcloud.pojo;

import lombok.Data;

@Data
public class IEXPrevious {

	private String date;
	private Float open;
	private Float close;
	private Float high;
	private Float low;
	private Long volume;
	private Float uOpen;
	private Float uClose;
	private Float uHigh;
	private Float uLow;
	private Long uVolume;
	private Float change;
	private Float changePercent;
	private Float changeOverTime;
	private String symbol;
	
}
