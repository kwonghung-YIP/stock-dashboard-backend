package demo.stockdashboard.pojo;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PreviousQuote {
	
	@Id @JsonIgnore
	public ObjectId id;
	
	private String symbol;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	private Float open;
	private Float high;
	private Float low;
	private Float close;
	private Long  volume;

}
