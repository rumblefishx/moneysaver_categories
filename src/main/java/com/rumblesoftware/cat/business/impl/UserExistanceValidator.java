package com.rumblesoftware.cat.business.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.rumblesoftware.cat.exceptions.InvalidDataException;
import com.rumblesoftware.cat.exceptions.ValidationException;
import com.rumblesoftware.cat.io.CandidateToValidationData;

@Component
@PropertySource(value = "classpath:application.properties")
public class UserExistanceValidator extends BaseValidator<CandidateToValidationData> {
	
	@Value(value = "${ms.customer.profile.service.url}")
	public String url;
	
	private static final String DEFAULT_ERROR_MSG_CODE = "{ms.customer.not.found}";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public void validate(CandidateToValidationData input) throws InvalidDataException, ValidationException {
		if(input.getCustomerId() == null)
			throw new InvalidDataException();
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", input.getCustomerId().toString());	
	    	    
	    try {
	    	restTemplate.getForEntity(url,String.class,params);
	    } catch(HttpClientErrorException error) {
	    	if(error.getStatusCode() == HttpStatus.NOT_FOUND)
	    		throw new ValidationException(DEFAULT_ERROR_MSG_CODE);
	    		
	    }
	    		
		if(this.nextValidator != null)
			this.nextValidator.validate(input);
	}

}
