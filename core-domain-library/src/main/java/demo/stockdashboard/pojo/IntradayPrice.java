package demo.stockdashboard.pojo;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document(collection = "intraday-prices")
public class IntradayPrice {

	@Id @JsonIgnore
	private ObjectId id;
	
	@JsonIgnore
	private String symbol;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;
	
	private Float high;
	private Float low;
	private Float open;
	private Float close;
	private Long volume;

}
