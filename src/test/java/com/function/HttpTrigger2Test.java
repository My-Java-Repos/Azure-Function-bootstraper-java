package com.function;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.logging.Logger;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;

import java.util.Optional;

/**
 * Unit test for HttpTrigger1 class.
 */
public class HttpTrigger2Test {
    /**
     * Unit test for hello method.
     */
    @Test
    public void testHello() throws Exception {
       final HttpTrigger2 function = new HttpTrigger2(); 
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        String paramValue="Azure";
        queryParams.put("name", paramValue);
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        //Invoke
        HttpResponseMessage responseMessage=function.httpTrigger2(req, context);
        // Verify
        assertEquals("Hello, " + paramValue, responseMessage.getBody().toString());
    }
}
