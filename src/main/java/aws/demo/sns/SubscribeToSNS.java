package aws.demo.sns;

import javax.xml.ws.Response;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

public class SubscribeToSNS {

	public static void main(String[] args) {

		SnsClient snsClient = SnsClient.builder().build();

		String topicArn = System.getenv("TOPIC_ARN");

		// Subscribe an email endpoint to an Amazon SNS topic.
		final SubscribeRequest subscribeRequest = SubscribeRequest.builder().topicArn(topicArn).protocol("email")
				.endpoint("valid@email.com").build();

		SubscribeResponse snsSubResponse = snsClient.subscribe(subscribeRequest);

		System.out.println(snsSubResponse.responseMetadata().requestId());

		System.out.println("To confirm the subscription, check your email.");

	}

}
