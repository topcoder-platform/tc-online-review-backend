package com.topcoder.onlinereview.component.grpcclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class GrpcChannelManager {
    private static ManagedChannel channel;
    private static ManagedChannel channelSync;

    @Autowired
    public GrpcChannelManager(@Value("${grpc.client.or.address}") String address,
            @Value("${grpc.client.or.port}") String port, @Value("${grpc.client.orsync.address}") String syncAddress,
            @Value("${grpc.client.orsync.port}") String syncPort,
            @Value("${managerHelper.authClientId}") String authClientId,
            @Value("${managerHelper.authClientSecret}") String authClientSecret,
            @Value("${managerHelper.authAudience}") String authAudience,
            @Value("${managerHelper.authDomain}") String authDomain,
            @Value("${managerHelper.authExpirationTime}") String authExpirationTime,
            @Value("${managerHelper.authProxyURL}") String authProxyURL) {
        channel = ManagedChannelBuilder.forAddress(address, Integer.parseInt(port))
                .usePlaintext()
                .build();
        channelSync = ManagedChannelBuilder.forAddress(syncAddress, Integer.parseInt(syncPort))
                .intercept(new GrpcInterceptor(authClientId, authClientSecret, authAudience, authDomain,
                        authExpirationTime, authProxyURL))
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
