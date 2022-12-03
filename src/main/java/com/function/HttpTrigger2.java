package com.function;

import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

import com.microsoft.azure.functions.*;

/**
 * Hello function with HTTP Trigger.
 */
public class HttpTrigger2 {
    @FunctionName("http-trigger2")
    public HttpResponseMessage httpTrigger2(@HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> req,
                        ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String query = req.getQueryParameters().get("name");
        final String name = req.getBody().orElse(query);
                    
        if (name == null) {
            return req.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return req.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }
}
