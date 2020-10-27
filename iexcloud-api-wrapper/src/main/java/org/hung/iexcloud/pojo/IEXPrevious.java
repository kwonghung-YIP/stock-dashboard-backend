package org.hung.iexcloud.pojo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class IEXPrevious {

	private String symbol;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
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
	
	
}
