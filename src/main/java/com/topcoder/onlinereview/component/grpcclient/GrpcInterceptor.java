package com.topcoder.onlinereview.component.grpcclient;

import com.topcoder.onlinereview.component.jwt.JWTTokenGenerator;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * A interceptor to handle client header.
 */
public class GrpcInterceptor implements ClientInterceptor {
    private String authClientId;
    private String authClientSecret;
    private String authAudience;
    private String authDomain;
    private String authExpirationTime;
    private String authProxyURL;

    static final Metadata.Key<String> Token = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
    static final Metadata.Key<String> Handle = Metadata.Key.of("handle", Metadata.ASCII_STRING_MARSHALLER);

    public GrpcInterceptor(String authClientId, String authClientSecret, String authAudience, String authDomain,
            String authExpirationTime, String authProxyURL) {
        this.authClientId = authClientId;
        this.authClientSecret = authClientSecret;
        this.authAudience = authAudience;
        this.authDomain = authDomain;
        this.authExpirationTime = authExpirationTime;
        this.authProxyURL = authProxyURL;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions, Channel next) {
        return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                String token = "";
                try {
                    token = JWTTokenGenerator.getInstance(authClientId,
                            authClientSecret,
                            authAudience,
                            authDomain,
                            Integer.parseInt(authExpirationTime),
                            authProxyURL).getMachineToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                headers.put(Token, token);
                headers.put(Handle, "tcwebservice");
                super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}