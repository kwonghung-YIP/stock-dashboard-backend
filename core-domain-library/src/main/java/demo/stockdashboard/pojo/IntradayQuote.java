package demo.stockdashboard.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class IntradayQuote {

	@Id @JsonIgnore
	private ObjectId id;
	
	private String symbol;
	private String tradeDate;
	private IntradayPrice[] prices;
	
	@Data
	static public class IntradayPrice {
		
		private String date;
		private String minute;
		private Float close;
		private Long volume;
		
	}	
}
