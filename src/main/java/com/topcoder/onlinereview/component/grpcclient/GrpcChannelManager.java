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
    @Value("${grpc.client.orsync.address}")
    private String orSyncAddress;
    @Value("${grpc.client.orsync.port}")
    private String orSyncPort;

    private static ManagedChannel channel;
    private static ManagedChannel channelSync;

    @Autowired
    public GrpcChannelManager(@Value("${grpc.client.or.address}") String address,
            @Value("${grpc.client.or.port}") String port, @Value("${grpc.client.orsync.address}") String syncAddress,
            @Value("${grpc.client.orsync.port}") String syncPort) {
        channel = ManagedChannelBuilder.forAddress(address, Integer.parseInt(port))
                .usePlaintext()
                .build();
        channelSync = ManagedChannelBuilder.forAddress(syncAddress, Integer.parseInt(syncPort))
                .usePlaintext()
                .build();
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public ManagedChannel getChannelSync() {
        return channelSync;
    }
}
