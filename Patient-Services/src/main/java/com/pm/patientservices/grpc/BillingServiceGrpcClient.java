package com.pm.patientservices.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingstub blockingstub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001") int serverPort) {
        log.info("Connecting to Billing Service GRPC at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        blockingstub = BillingServiceGrpc.newBlockingstub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {

        BillingRequest request = BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email)
                .build();

        BillingResponse response = blockingstub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;

    }
}
