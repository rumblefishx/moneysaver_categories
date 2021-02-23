package com.rumblesoftware.cat.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.rumblesoftware.cat.exceptions.CustomerNotFound;
import com.rumblesoftware.cat.exceptions.InvalidDataException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.CandidateToValidationData;
import com.rumblesoftware.utils.PostOfficer;

@Component
@PropertySource(value = "classpath:application.properties")
public class UserExistanceValidator extends BaseValidator<CandidateToValidationData> {
	
	@Value(value = "${ms.customer.profile.service.url}")
	public String url;
	
	private Logger log = LogManager.getLogger(UserExistanceValidator.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PostOfficer po;
	
	@Override
	public void validate(CandidateToValidationData input) throws InvalidDataException, ValidationException {
		if(input.getCustomerId() == null)
			throw new InvalidDataException();
		
		log.info("[Validation Layer] - Starting Customer Validation...");
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", input.getCustomerId().toString());	
	    	    
	    try {
	    	restTemplate.getForEntity(url,String.class,params);
	    } catch(HttpClientErrorException error) {
	    	if(error.getStatusCode() == HttpStatus.NOT_FOUND) {
	    		log.error("[validator layer] customer does not exists");
	    		throw new CustomerNotFound();
	    	} else {
	    		log.error("[validator layer] An error occurred during customer validation...");
	    		log.error(error.getMessage());
	    		error.printStackTrace();
	    		throw new InvalidDataException();
	    	}    		
	    }
	    		
		if(this.nextValidator != null)
			this.nextValidator.validate(input);
	}

}
