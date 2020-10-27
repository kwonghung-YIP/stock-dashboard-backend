package org.hung.iexcloud.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class IEXIntradayPrice {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime minute;
	
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
