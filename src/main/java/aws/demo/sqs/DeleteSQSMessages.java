package aws.demo.sqs;

import java.util.List;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class DeleteSQSMessages {
	
	public static void main(String args[]) {
		
		SqsClient sqsClient = SqsClient.builder().build() ;
		String queueUrl= "https://sqs.ap-south-1.amazonaws.com/297106433303/temp-amod-kadam";
		
		
		ReceiveMessageRequest receiveMessageRequest = null;
		ReceiveMessageResponse messagereponse = sqsClient.receiveMessage(receiveMessageRequest.builder()
				.queueUrl(queueUrl)
				.maxNumberOfMessages(10)
				.build()
				);
		
		List<Message> msgList  = messagereponse.messages();
		
		for (Message msg: msgList) {
			
			System.out.println(
					" Deleting message with message id : " + msg.messageId() +
					" Message Body: " + msg.body()
			);
			DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
		            .queueUrl(queueUrl)
		            .receiptHandle(msg.receiptHandle())
		            .build();
		    DeleteMessageResponse response =  sqsClient.deleteMessage(deleteMessageRequest);
		    
		    
		}
			
		
		sqsClient.sendMessage(SendMessageRequest.builder()
		        .queueUrl(queueUrl)
		        .messageBody("Hello world!")
		        .delaySeconds(10)
		        .build());
	}

}
