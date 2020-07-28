package aws.demo.sns;

import javax.xml.ws.Response;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

public class PublishMessage {

	public static void main(String[] args) {
		
		 SnsClient snsClient = SnsClient.builder().build();
		 
		 String topicArn = System.getenv("TOPIC_ARN");
		 
		 PublishRequest publishRequest = PublishRequest.builder()
				 .topicArn(topicArn)
				 .message("Welcome to AWS Session ")
				 .build();
		snsClient.publish(publishRequest );
				 
				 		 

	}

}
