package com.topcoder.onlinereview.component.grpcclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class GrpcChannelManager {
    @Value("${grpc.client.or.address}")
    private String orAddress;
    @Value("${grpc.client.or.port}")
    private String orPort;

    private static ManagedChannel channel;

    @Autowired
    public GrpcChannelManager(@Value("${grpc.client.or.address}") String address,
            @Value("${grpc.client.or.port}") String port) {
        channel = ManagedChannelBuilder.forAddress(address, Integer.parseInt(port))
                .usePlaintext()
                .build();
    }

    public ManagedChannel getChannel() {
        return channel;
    }
}
