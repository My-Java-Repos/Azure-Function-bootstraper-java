package com.function;

import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

import com.microsoft.azure.functions.*;

/**
 * Hello function with HTTP Trigger.
 */
public class HttpTrigger1 {
    @FunctionName("http-trigger1")
    public String httpTrigger1(@HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> req,
                        ExecutionContext context) {
        return String.format("Hello");
    }
}
