package apple.mint.agent.core.service;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;

public class RestServiceClient {

	Logger logger = LoggerFactory.getLogger(RestServiceClient.class);

	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();

	String httpMessageConverterCharset;

	public RestServiceClient() {
		this("UTF-8");
	}

	public RestServiceClient(String httpMessageConverterCharset) {
		this.httpMessageConverterCharset = httpMessageConverterCharset;
		restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter());
		headers.setContentType(new MediaType("application", "json", Charset.forName(httpMessageConverterCharset)));
	}

	public <T> T call2(String callUrl, T request, ParameterizedTypeReference<T> type) throws Exception {
		HttpEntity<T> requestEntity = new HttpEntity<T>(request, headers);
		ResponseEntity<?> responseEntity = restTemplate.exchange(callUrl, HttpMethod.POST, requestEntity, type);
		if (responseEntity == null)
			throw new Exception("RestServiceClient response is null.");
		T response = (T) responseEntity.getBody();
		return response;
	}

	public ComMessage<?, ?> call(String callUrl, ComMessage<?, ?> request, ParameterizedTypeReference<?> type)
			throws Exception {

		HttpEntity<ComMessage<?, ?>> requestEntity = new HttpEntity<ComMessage<?, ?>>(request, headers);
		ResponseEntity<?> responseEntity = restTemplate.exchange(callUrl, HttpMethod.POST, requestEntity, type);
		if (responseEntity == null)
			throw new Exception("RestServiceClient response is null.");
		ComMessage<?, ?> response = (ComMessage<?, ?>) responseEntity.getBody();
		
		// if(response == null) {
		// throw new Exception("ComMessage response is null.");
		// }else{
		// if(!Environments.SUCCESS.equals(response.getErrorCd())){
		// String errorMsg = response.getErrorMsg();
		// String errorDetail = response.getErrorDetail();
		// throw new Exception(Util.join(errorMsg + "detail msg:(",errorDetail, ")"));
		// }
		// }

		return response;

	}

}
