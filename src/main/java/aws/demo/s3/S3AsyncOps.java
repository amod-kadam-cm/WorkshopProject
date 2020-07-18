package aws.demo.s3;

import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class S3AsyncOps {

    private static final String BUCKET = "bucket1594894063311";
    private static final String KEY = "AWSCLIV2.msi";

    public static void main(String[] args) {
        S3AsyncClient client = S3AsyncClient.builder().region(Region.US_WEST_2).build();
        CompletableFuture<PutObjectResponse> future = client.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(KEY)
                        .build(),
                AsyncRequestBody.fromFile(Paths.get("D:/Amod/AWSCLIV2.msi"))
        );

        System.out.println("Started putting object to S3 bucket");
        future.whenComplete((resp, err) -> {
            try {
                if (resp != null) {
                    System.out.println("my response: " + resp);
                } else {
                    // Handle error
                    err.printStackTrace();
                }
            } finally {
                // Lets the application shut down. Only close the client when you are completely done with it.
                client.close();
            }
        });
        System.out.println("Before future.join");
        future.join();
    }
}

