package demo.stockdashboard.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StockQuoteRouter {

	@Autowired
	private StockQuoteHandler handler;
	
	@Bean
	public RouterFunction<ServerResponse> route() {
		//https://www.callicoder.com/spring-5-reactive-webclient-webtestclient-examples/
		return RouterFunctions.route()
			.GET("/company", handler::company)
			.GET("/previous", handler::previous)
			.GET("/intraday-demo", handler::intradayDemo)
			.GET("/quote-demo", handler::realTimeQuoteDemo)
			.build();
		
	}
	
	@Bean
	StockQuoteHandler handler() {
		return new StockQuoteHandler();
	}
	

}
