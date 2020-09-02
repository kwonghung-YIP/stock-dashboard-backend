package demo.stockdashboard.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Quote {

	@Id @JsonIgnore
	private ObjectId id;
	
	private String symbol;
	
	private Float latestPrice;
	private Float previousClose;
	private Float change;
	private Float changePercent;

}
