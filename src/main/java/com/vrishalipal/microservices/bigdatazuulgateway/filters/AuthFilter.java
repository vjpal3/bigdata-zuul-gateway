package com.vrishalipal.microservices.bigdatazuulgateway.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


public class AuthFilter extends ZuulFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
	      String requestURL = ctx.getRequest().getRequestURL().toString();
	      logger.info("requestURL: ^^^ {}" , requestURL);
	      return (requestURL.contains("/api/bigdata/"));
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String xAuth = request.getHeader("Authorization");
		logger.info("xAuth: {}" , xAuth);
	 		 
		String url = "http://localhost:8200/api/users/validateToken";
	 
		HttpHeaders requestHeaders = new HttpHeaders();
	    requestHeaders.add("Authorization", xAuth);
	    requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
	    HttpEntity<Boolean> requestEntity = new HttpEntity<> (requestHeaders);
			   
	    ResponseEntity<Boolean> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity, Boolean.class);
	
	    if(!response.getBody()) {
	        ctx.setSendZuulResponse(false); //This makes request not forwarding to micro services
	        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());           
	        ctx.setResponseBody("Invalid Access...");
	        ctx.getResponse().setContentType("application/json");
	    }
	    return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
