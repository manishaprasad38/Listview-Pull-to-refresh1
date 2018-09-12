package com.demo.app.assignment.viewmodel.callback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by manisha prasad
 */

// interceptor interface for communication with service
public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", "bearer " + authToken);
        Request request = builder.build();
        return chain.proceed(request);
    }
}

