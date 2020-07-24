package aws.demo.sqs;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SQSSendMessage {
	
	public static void main(String args[]) {
		
		SqsClient sqsClient = SqsClient.builder().build() ;
		String queueUrl= "https://sqs.ap-south-1.amazonaws.com/297106433303/temp-amod-kadam";
		/*String queueName = "https://sqs.ap-south-1.amazonaws.com/297106433303/temp-amod-kadam";
		CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
		        .queueName(queueName)
		        .build();
		sqsClient.createQueue(createQueueRequest);
		
		*/
		
		sqsClient.sendMessage(SendMessageRequest.builder()
		        .queueUrl(queueUrl)
		        .messageBody("Hello world!")
		        .delaySeconds(10)
		        .build());
	}

}
