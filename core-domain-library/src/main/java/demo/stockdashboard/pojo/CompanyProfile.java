package demo.stockdashboard.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document("company")
public class CompanyProfile {
	
	@Id @JsonIgnore
	private ObjectId id;
	
	private String symbol;
	
	private String companyName;
	private String exchange;
	private String securityName;
	private String sector;

}
