package samples.authcode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import samples.authcode.service.DownstreamServiceHandler;

@SpringBootApplication
public class Application {

	@Value("${resourceServerUrl}")
	private String resourceServerUrl;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
			OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}

	@Bean
	public DownstreamServiceHandler downstreamServiceHandler(
			OAuth2RestTemplate oAuth2RestTemplate) {
		return new DownstreamServiceHandler(oAuth2RestTemplate, resourceServerUrl);
	}

}
