package demo.stockdashboard.sched;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hung.iexcloud.IEXApiWrapper;
import org.hung.iexcloud.pojo.IEXCompany;
import org.hung.iexcloud.pojo.IEXHistoricalPrice;
import org.hung.iexcloud.pojo.IEXIntradayPrice;
import org.hung.iexcloud.pojo.IEXPrevious;
import org.hung.iexcloud.pojo.IEXQuote;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;

import demo.stockdashboard.pojo.CompanyProfile;
import demo.stockdashboard.pojo.IntradayPrice;
import demo.stockdashboard.pojo.PreviousQuote;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataFeedSchedTask {

	@Value("${stock-dashboard.stock-list}")
	private List<String> stocklist;
	
	@Autowired
	private IEXApiWrapper iexWrapper;
	
	@Autowired
	private MongoTemplate template;	
	
	//@Scheduled(initialDelay = 1000 * 0, fixedDelay = 1000 * 60 * 5)
	public void syncCompany() {
		log.info("Start sync company profile...");
		
		stocklist
			.stream()
			.forEach(symbol -> {
				IEXCompany iexCompany = iexWrapper.company(symbol);
				
				if (iexCompany != null) {
					
					CompanyProfile profile = new CompanyProfile();
					BeanUtils.copyProperties(iexCompany, profile);
					profile.setSymbol(symbol);
					
					Optional<CompanyProfile> result = template
						.update(CompanyProfile.class)
						.matching(query(where("symbol").regex("^"+symbol+"$", "i")))
						.replaceWith(profile)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();
				}
			});
	}
	
	//@Scheduled(initialDelay = 1000 * 2, fixedDelay = 1000 * 60 * 5)
	public void syncQuote() {
		log.info("Start sync quote...");
		
		stocklist
			.stream()
			.forEach(symbol -> {
				IEXQuote quote = iexWrapper.quote(symbol);
				
				if (quote != null) {
					
					Optional<IEXQuote> result = template
						.update(IEXQuote.class)
						.inCollection("quote")
						.matching(query(where("symbol").regex("^"+symbol+"$", "i")))
						.replaceWith(quote)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();
				}
			});		
	}
	
	@Scheduled(initialDelay = 1000 * 4, fixedDelay = 1000 * 60 * 5)
	public void syncPrevious() {
		log.info("Start sync previous...");
		
		stocklist
			.stream()
			.forEach(symbol -> {

				//IEXPrevious iexPrevious = iexWrapper.previous(symbol);
				IEXHistoricalPrice[] iexHisorical = iexWrapper.historicalPrice(symbol,LocalDate.of(2020,9,24));
				
				if (iexHisorical != null && iexHisorical.length > 0) {
					PreviousQuote prev = new PreviousQuote();
					BeanUtils.copyProperties(iexHisorical[0], prev);
					prev.setSymbol(symbol);

					Optional<PreviousQuote> result = template
						.update(PreviousQuote.class)
						.inCollection("previous")
						.matching(query(where("symbol").regex("^"+symbol+"$", "i").and("date").is(prev.getDate())))
						.replaceWith(prev)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();
				}
			});		
	}
	
	//@Scheduled(initialDelay = 1000 * 6, fixedDelay = 1000 * 60 * 5)
	public void syncIntradayPrice() {
		log.info("Start sync intraday-price...");
		
		stocklist
			.stream()
			.forEach(symbol -> {
				log.info("Get intraday-price for {} ...",symbol);
				IEXIntradayPrice[] iexIntradayPrices = iexWrapper.intradayPrices(symbol,LocalDate.of(2020,9,24));
				
				/*template
				  .getCollectionNames()
				  .forEach(name -> {
					  log.info(name);					  
					  if (name.startsWith("intraday-prices-")) {
						  template.dropCollection(name);
					  }
				  });*/
				
				/*String collectionName = "intraday-prices-"+symbol;
				
				if (template.collectionExists(collectionName)) {
					template.dropCollection(collectionName);
				}
				MongoCollection collection = template.createCollection(collectionName);
				collection.createIndex(Indexes.descending("symbol",""));*/
				
				for (IEXIntradayPrice iexPrice:iexIntradayPrices) {
					IntradayPrice price = new IntradayPrice();//BeanUtils.findPrimaryConstructor(IntradayPrice.class).newInstance();
					BeanUtils.copyProperties(iexPrice, price);
					price.setSymbol(symbol);
					price.setTimestamp(LocalDateTime.of(iexPrice.getDate(), iexPrice.getMinute()));
					
					Optional<IntradayPrice> result = template
						.update(IntradayPrice.class)
						.matching(query(where("symbol").is(price.getSymbol()).and("timestamp").is(price.getTimestamp())))
						.replaceWith(price)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();					
				}



		});		
		
	}
	
}
