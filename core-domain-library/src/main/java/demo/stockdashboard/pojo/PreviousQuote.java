package demo.stockdashboard.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PreviousQuote {
	
	@Id @JsonIgnore
	public ObjectId id;
	
	private String symbol;
	private String date;
	private Float close;

}
