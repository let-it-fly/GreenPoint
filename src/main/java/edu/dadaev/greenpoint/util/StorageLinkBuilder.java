package edu.dadaev.greenpoint.util;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageLinkBuilder {

    @Value("${s3.endpoint}")
    private String endpoint;

    @Value("${s3.bucket}")
    private String bucket;

    @Named("buildFullUrl")
    public String buildFullUrl(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            return null;
        }

        String cleanEndpoint = endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;

        String cleanKey = objectKey.startsWith("/") ? objectKey.substring(1) : objectKey;

        return String.format("%s/%s/%s", cleanEndpoint, bucket, cleanKey);
    }
}
