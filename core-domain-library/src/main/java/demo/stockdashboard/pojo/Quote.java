package demo.stockdashboard.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Quote {

	@Id @JsonIgnore
	private ObjectId id;
	
	@Indexed
	private String symbol;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime latestUpdate;
	
	private Float latestPrice;
	private Float previousClose;
	private Float change;
	private Float changePercent;

}
