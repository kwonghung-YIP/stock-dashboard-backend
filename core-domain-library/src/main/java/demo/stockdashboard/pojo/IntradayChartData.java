package demo.stockdashboard.pojo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class IntradayChartData {

	private String symbol;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate tradeDate;
	
	private PreviousQuote previous;
	
	private List<IntradayPrice> quote;
	
}
