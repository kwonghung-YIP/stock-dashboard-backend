package demo.stockdashboard.config;

import org.hung.iexcloud.IEXApiWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
	
	@Bean
	public IEXApiWrapper iexApiWrapper() {
		IEXApiWrapper wrapper = new IEXApiWrapper();
		return wrapper;
	}

}
