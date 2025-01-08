package com.rian.socialmedia;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

public class DynamoDBConfig {
    
    public static DynamoDB getConnection() {
        try {
            // Inisialisasi kredensial AWS secara langsung
            BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAZAI4HB6HIQOTKD5L", "Cw/GSAZpagvky2+xjBPO2QuwXLrdgY4DNAikd25N");
            
            // Membangun klien DynamoDB dengan region dan kredensial yang ditentukan
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("ap-southeast-1") 
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
            
            // Membuat instance DynamoDB dan mengembalikannya
            return new DynamoDB(client);
            
        } catch (Exception e) {
            throw new RuntimeException("Gagal menginisialisasi koneksi DynamoDB: " + e.getMessage(), e);
        }
    }
}
