package com.function;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import java.util.Optional;

/**
 * Unit test for HttpTrigger1 class.
 */
public class HttpTrigger1Test {
    /**
     * Unit test for hello method.
     */
    @Test
    public void testHello() throws Exception {
       final HttpTrigger1 function = new HttpTrigger1(); 
       @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);
      
        final ExecutionContext context = mock(ExecutionContext.class);
     
        // Invoke
        final String ret = function.httpTrigger1(req, context);
        // Verify
        assertEquals("Hello", ret);
    }
}
