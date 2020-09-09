package org.hung.iexcloud;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.hung.iexcloud.pojo.IEXCompany;
import org.hung.iexcloud.pojo.IEXHistoricalPrice;
import org.hung.iexcloud.pojo.DelayedQuote;
import org.hung.iexcloud.pojo.IEXIntradayPrice;
import org.hung.iexcloud.pojo.IEXPrevious;
import org.hung.iexcloud.pojo.IEXQuote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class IEXApiWrapper {

	@Value("${iex-cloud.base-url}")
	private String baseUrl;
	
	@Value("${iex-cloud.api-token}")
	private String apiToken;
	
	@Value("stable")
	private String version;
	
	private WebClient webclient;
	
	@PostConstruct
	public void postContruct() {
		webclient = WebClient
				.builder()
				.baseUrl(baseUrl)
				.build();
	}
	

	public DelayedQuote delayedQuote(String symbol) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
		params.add("token", apiToken);
		
		Mono<DelayedQuote> body = webclient
		  .get()
		  .uri((urlBuilder) -> {
			return urlBuilder
				.path("/stock/{symbol}/delayed-quote")
				.queryParams(params)
			    .build(symbol);
		  })
		  .retrieve()
		  .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			  log.error("Error when get delayedQuote",clientResponse);
			  return Mono.error(new Exception(clientResponse.toString()));
		  })
		  .bodyToMono(DelayedQuote.class);

		return body.block();
	}
	
	public IEXCompany company(String symbol) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
		params.add("token", apiToken);
		
		
		Mono<IEXCompany> body = webclient
		  .get()
		  .uri((urlBuilder) -> {
			return urlBuilder
				.path("/stock/{symbol}/company")
				.queryParams(params)
			    .build(symbol);
		  })
		  .retrieve()
		  .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			  log.error("Error when get delayedQuote",clientResponse);
			  return Mono.error(new Exception(clientResponse.toString()));
		  })
		  .bodyToMono(IEXCompany.class);

		return body.block();		
	}

	public IEXPrevious previous(String symbol) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
		params.add("token", apiToken);
		
		
		Mono<IEXPrevious> body = webclient
		  .get()
		  .uri((urlBuilder) -> {
			return urlBuilder
				.path("/stock/{symbol}/previous")
				.queryParams(params)
			    .build(symbol);
		  })
		  .retrieve()
		  .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			  log.error("Error when get delayedQuote",clientResponse);
			  return Mono.error(new Exception(clientResponse.toString()));
		  })
		  .bodyToMono(IEXPrevious.class);

		return body.block();
	}
	
	public IEXHistoricalPrice[] historicalPrice(String symbol,LocalDate exactDate) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
		params.add("token", apiToken);
		params.add("chartByDay", "true");
		params.add("exactDate", exactDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		
		Mono<IEXHistoricalPrice[]> body = webclient
		  .get()
		  .uri((urlBuilder) -> {
			return urlBuilder
				.path("/stock/{symbol}/chart")
				.queryParams(params)
			    .build(symbol);
		  })
		  .retrieve()
		  .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			  log.error("Error when get delayedQuote",clientResponse);
			  return Mono.error(new Exception(clientResponse.toString()));
		  })
		  .bodyToMono(IEXHistoricalPrice[].class);

		return body.block();		
	}
	
	public IEXIntradayPrice[] intradayPrices(String symbol,LocalDate exactDate) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
		params.add("token", apiToken);
		params.add("chartIEXOnly", "true");
		if (exactDate!=null) {
			params.add("exactDate", exactDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		
		
		Mono<IEXIntradayPrice[]> body = webclient
		  .get()
		  .uri((urlBuilder) -> {
			return urlBuilder
				.path("/stock/{symbol}/intraday-prices")
				.queryParams(params)
			    .build(symbol);
		  })
		  .retrieve()
		  .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			  log.error("Error when get delayedQuote",clientResponse);
			  return Mono.error(new Exception(clientResponse.toString()));
		  })
		  .bodyToMono(IEXIntradayPrice[].class);

		return body.block();
	}
	
	public IEXQuote quote(String symbol) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
		params.add("token", apiToken);
		
		
		Mono<IEXQuote> body = webclient
		  .get()
		  .uri((urlBuilder) -> {
			return urlBuilder
				.path("/stock/{symbol}/quote")
				.queryParams(params)
			    .build(symbol);
		  })
		  .retrieve()
		  .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			  log.error("Error when get delayedQuote",clientResponse);
			  return Mono.error(new Exception(clientResponse.toString()));
		  })
		  .bodyToMono(IEXQuote.class);

		return body.block();
	}	
	
}
