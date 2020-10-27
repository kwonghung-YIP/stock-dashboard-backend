/**
 * 
 */
package org.hung.iexcloud.pojo;

import lombok.Data;

/**
 * @author kwong
 *
 */
@Data
public class IEXQuote {

	private String symbol;
	private String companyName;
	private String primaryExchange;
	private String calculationPrice;
	private Float open;
	private Long openTime;
	private String openSource;
	private Float close;
	private Long closeTime;
	private String closeSource;
	private Float high;
	private Long highTime;
	private String highSource;
	private Float low;
	private Long lowTime;
	private String lowSource;
	private Float latestPrice;
	private String latestSource;
	private String latestTime;
	private Long latestUpdate;
	private Long latestVolume;
	private Float iexRealtimePrice;
	private Long iexRealtimeSize;
	private Long iexLastUpdated;
	private Float delayedPrice;
	private Long delayedPriceTime;
	private Float oddLotDelayedPrice;
	private Long oddLotDelayedPriceTime;
	private Float extendedPrice;
	private Float extendedChange;
	private Float extendedChangePercent;
	private Long extendedPriceTime;
	private Float previousClose;
	private Long previousVolume;
	private Float change;
	private Float changePercent;
	private Long volume;
	private Double iexMarketPercent;
	private Long iexVolume;
	private Long avgTotalVolume;
	private Float iexBidPrice;
	private Long iexBidSize;
	private Long iexAskPrice;
	private Long iexAskSize;
	private Float iexOpen;
	private Long iexOpenTime;
	private Float iexClose;
	private Long iexCloseTime;
	private Long marketCap;
	private Float peRatio;
	private Float week52High;
	private Float week52Low;
	private Float ytdChange;
	private Long lastTradeTime;
	private Boolean isUSMarketOpen;
	
}
