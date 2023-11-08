package com.example.demo;

import java.util.concurrent.Callable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

/**
 * @author Ryan Baxter
 */
@RestController
public class WebController {

	private static final Logger LOG = Logger.getLogger(WebController.class.getName());

	private NameService nameService;
	private GreetingService greetingService;

	public WebController(NameService nameService, GreetingService greetingService) {
		this.nameService = nameService;
		this.greetingService = greetingService;
	}

	@RequestMapping
	public String index(HttpServletRequest request) {
		String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toLanguageTag();

		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
		CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("web-controller");
		Callable<String> decoratedRunnable = CircuitBreaker.decorateCallable(circuitBreaker, () -> buildMessage(locale));
		
		String result = Try.ofCallable(decoratedRunnable).recover(throwable -> "Returning default greeting: 'Hello World'").get();
		return result;
	}
	
	public String buildMessage(String locale) {
		String greeting =  new StringBuilder().append(greetingService.getGreeting(locale)).
				append(" ").append(nameService.getName()).toString();
		LOG.info("Greeting: " + greeting);
		LOG.info("Locale: " + locale);
		return greeting;
	}
}
