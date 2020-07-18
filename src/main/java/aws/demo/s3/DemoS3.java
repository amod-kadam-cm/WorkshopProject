package aws.demo.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

public class DemoS3 {

    static Region region = Region.US_WEST_2;
    static S3Client s3 = S3Client.builder().region(region).build();

    public static void main(String[] args) {
        // createBucket();
        uploadObject();
    }

    private static void uploadObject() {
        String bucketName = "bucket1594894063311";

        s3.putObject(PutObjectRequest.builder().
                        bucket(bucketName).
                        key("amod_photo.jpg").
                        build(),
                RequestBody.fromFile(new File("D:/amod/amod_photo.jpg")));
    }

    private static void createBucket() {
        String bucket = "bucket" + System.currentTimeMillis();
        System.out.println(bucket);

        // Create bucket
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(region.id())
                        .build())
                .build();
        s3.createBucket(createBucketRequest);

    }
}
