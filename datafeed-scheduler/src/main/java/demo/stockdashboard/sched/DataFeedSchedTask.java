package demo.stockdashboard.sched;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Optional;

import org.hung.iexcloud.IEXApiWrapper;
import org.hung.iexcloud.pojo.IEXCompany;
import org.hung.iexcloud.pojo.IEXIntradayPrice;
import org.hung.iexcloud.pojo.IEXPrevious;
import org.hung.iexcloud.pojo.IEXQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.stockdashboard.pojo.IntradayQuote;
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
	
	@Scheduled(initialDelay = 1000 * 0, fixedDelay = 1000 * 60 * 5)
	public void syncCompany() {
		log.info("Start sync company...");
		
		stocklist
			.stream()
			.forEach(symbol -> {
				IEXCompany company = iexWrapper.company(symbol);
				
				if (company != null) {
					
					Optional<IEXCompany> result = template
						.update(IEXCompany.class)
						.inCollection("company")
						.matching(query(where("symbol").is(symbol.toUpperCase())))
						.replaceWith(company)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();
				}
			});
	}
	
	@Scheduled(initialDelay = 1000 * 2, fixedDelay = 1000 * 60 * 5)
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
						.matching(query(where("symbol").is(symbol.toUpperCase())))
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

				IEXPrevious previous = iexWrapper.previous(symbol);
				
				if (previous != null) {

					Optional<IEXPrevious> result = template
						.update(IEXPrevious.class)
						.inCollection("previous")
						.matching(query(where("symbol").is(symbol.toUpperCase()).and("date").is(previous.getDate())))
						.replaceWith(previous)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();
				}
			});		
	}
	
	@Scheduled(initialDelay = 1000 * 6, fixedDelay = 1000 * 60 * 5)
	public void syncIntradayPrice() {
		log.info("Start sync intraday-price...");
		
		stocklist
			.stream()
			.forEach(symbol -> {
				log.info("Get intraday-price for {} ...",symbol);
				IEXIntradayPrice[] intradayPrices = iexWrapper.intradayPrices(symbol);
				
				for (IEXIntradayPrice price:intradayPrices) {
					Optional<IEXIntradayPrice> result = template
						.update(IEXIntradayPrice.class)
						.inCollection("intraday-prices-"+symbol)
						.matching(query(where("date").is(price.getDate()).and("minute").is(price.getMinute())))
						.replaceWith(price)
						.withOptions(FindAndReplaceOptions.options().upsert().returnNew())
						.findAndReplace();					
				}



		});		
		
	}
	
}
