package demo.stockdashboard.webflux;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.time.LocalTime;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import demo.stockdashboard.pojo.CompanyProfile;
import demo.stockdashboard.pojo.IntradayQuote;
import demo.stockdashboard.pojo.IntradayQuote.IntradayPrice;
import demo.stockdashboard.pojo.PreviousQuote;
import demo.stockdashboard.pojo.Quote;
import demo.stockdashboard.repo.CompanyProfileRepo;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class StockQuoteHandler {
	
	@Autowired
	private MongoTemplate template;

	@Autowired
	private CompanyProfileRepo companyRepo;
	
	public Mono<ServerResponse> company(ServerRequest request) {
		Optional<String> symbolParam = request.queryParam("symbol");
		if (symbolParam.isPresent()) {
			String symbol = symbolParam.get();
			Optional<CompanyProfile> profile = companyRepo.findBySymbol(symbol);
			if (profile.isPresent()) {
				return ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(profile.get());
			} else {
				return ServerResponse
						.notFound()
						.build();
			}
		} else {
			return ServerResponse
					.status(HttpStatus.BAD_REQUEST)
					.bodyValue("parameter symbol is missing");
		}		
	}
	
	public Mono<ServerResponse> previous(ServerRequest request) {
		Optional<String> symbolParam = request.queryParam("symbol");
		if (symbolParam.isPresent()) {
			String symbol = symbolParam.get();
			PreviousQuote previous = template.findOne(query(where("symbol").is(symbol.toUpperCase())), PreviousQuote.class, "previous");
			if (previous!=null) {
				return ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(previous);
			} else {
				return ServerResponse
						.notFound()
						.build();
			}
		} else {
			return ServerResponse
					.status(HttpStatus.BAD_REQUEST)
					.bodyValue("parameter symbol is missing");
		}		
	}
	
	public Mono<ServerResponse> intraday(ServerRequest request) {
		Optional<String> symbolParam = request.queryParam("symbol");
		if (symbolParam.isPresent()) {
			String symbol = symbolParam.get();
			IntradayQuote intraday = template.findOne(query(where("symbol").is(symbol.toUpperCase())), IntradayQuote.class, "intraday-prices");
			if (intraday!=null) {
				LocalTime now = LocalTime.now();
				int limit = (now.getMinute()*60+now.getSecond())%300;
				intraday.setPrices(
					Stream.of(intraday.getPrices()).limit(limit).toArray(IntradayPrice[]::new));
					/*.filter(price -> {
					  return LocalTime.parse(price.getMinute(),DateTimeFormatter.ofPattern("H:m")).isBefore(now);
				  })
				  .toArray(IntradayPrice[]::new));*/
				
				return ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(intraday);
			} else {
				return ServerResponse
						.notFound()
						.build();
			}
		} else {
			return ServerResponse
					.status(HttpStatus.BAD_REQUEST)
					.bodyValue("parameter symbol is missing");
		}		
	}
	
	private SplittableRandom seed = new SplittableRandom();
			
	public Mono<ServerResponse> quote(ServerRequest request) {
		Optional<String> symbolParam = request.queryParam("symbol");
		if (symbolParam.isPresent()) {
			String symbol = symbolParam.get();
			Quote quote = template.findOne(query(where("symbol").is(symbol.toUpperCase())), Quote.class, "quote");
			if (quote!=null) {
				float priceWithNoise = (float)(quote.getLatestPrice() * (1 + seed.nextDouble(-0.15d, 0.15d)));
				float prevClose = quote.getPreviousClose();
				float change = priceWithNoise-prevClose;
				quote.setLatestPrice(priceWithNoise);
				quote.setChange(change);
				quote.setChangePercent(change/prevClose*100);
				return ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(quote);
			} else {
				return ServerResponse
						.notFound()
						.build();
			}
		} else {
			return ServerResponse
					.status(HttpStatus.BAD_REQUEST)
					.bodyValue("parameter symbol is missing");
		}		
	}
}
