/*
 * @author Constantin Chelban (constantink@saltedge.com)
 * Copyright (c) 2020 Salt Edge.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.saltedge.connector.sdk.callback;

import com.saltedge.connector.sdk.callback.mapping.BaseCallbackRequest;
import com.saltedge.connector.sdk.callback.services.SessionsCallbackService;
import com.saltedge.connector.sdk.config.ApplicationProperties;
import com.saltedge.connector.sdk.config.Constants;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionCallbackServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @Autowired
    ApplicationProperties applicationProperties;
    @InjectMocks
    private SessionsCallbackService service = new SessionsCallbackService();

    @Before
    public void setUp() throws Exception {
        service.applicationProperties = applicationProperties;
        given(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .willReturn(new ResponseEntity<>("{}", HttpStatus.OK));
    }

    @Test
    public void givenMockingRestTemplate_whenSendUpdateCallback_shouldBeCalledExchangeWithParams() {
        // when
        service.sendUpdateCallback("sessionSecret", new BaseCallbackRequest());

        // then
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(urlCaptor.capture(), eq(HttpMethod.POST), entityCaptor.capture(), eq(Object.class));
        assertThat(urlCaptor.getValue()).isEqualTo("http://localhost/api/connectors/v1/sessions/update");
        assertThat(entityCaptor.getValue().getHeaders().getAccept()).isEqualTo(Lists.list(MediaType.APPLICATION_JSON));
        assertThat(entityCaptor.getValue().getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(entityCaptor.getValue().getHeaders().get("App-id")).isEqualTo(Lists.list("QWERTY"));
        assertThat(entityCaptor.getValue().getHeaders().get("App-secret")).isEqualTo(Lists.list("ASDFG"));
        assertThat(entityCaptor.getValue().getHeaders().get(Constants.HEADER_AUTHORIZATION).get(0)).startsWith("Bearer ");
    }

    @Test
    public void givenMockingRestTemplate_whenSendSuccessCallback_shouldBeCalledExchangeWithParams() {
        // when
        service.sendSuccessCallback("sessionSecret", new BaseCallbackRequest());

        // then
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(urlCaptor.capture(), eq(HttpMethod.POST), entityCaptor.capture(), eq(Object.class));
        assertThat(urlCaptor.getValue()).isEqualTo("http://localhost/api/connectors/v1/sessions/success");
        assertThat(entityCaptor.getValue().getHeaders().getAccept()).isEqualTo(Lists.list(MediaType.APPLICATION_JSON));
        assertThat(entityCaptor.getValue().getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(entityCaptor.getValue().getHeaders().get("App-id")).isEqualTo(Lists.list("QWERTY"));
        assertThat(entityCaptor.getValue().getHeaders().get("App-secret")).isEqualTo(Lists.list("ASDFG"));
        assertThat(entityCaptor.getValue().getHeaders().get(Constants.HEADER_AUTHORIZATION).get(0)).startsWith("Bearer ");
    }

    @Test
    public void givenMockingRestTemplate_whenSendFailCallback_shouldBeCalledExchangeWithParams() {
        // when
        service.sendFailCallback("sessionSecret", new BaseCallbackRequest());

        // then
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity> entityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(urlCaptor.capture(), eq(HttpMethod.POST), entityCaptor.capture(), eq(Object.class));
        assertThat(urlCaptor.getValue()).isEqualTo("http://localhost/api/connectors/v1/sessions/fail");
        assertThat(entityCaptor.getValue().getHeaders().getAccept()).isEqualTo(Lists.list(MediaType.APPLICATION_JSON));
        assertThat(entityCaptor.getValue().getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(entityCaptor.getValue().getHeaders().get("App-id")).isEqualTo(Lists.list("QWERTY"));
        assertThat(entityCaptor.getValue().getHeaders().get("App-secret")).isEqualTo(Lists.list("ASDFG"));
        assertThat(entityCaptor.getValue().getHeaders().get(Constants.HEADER_AUTHORIZATION).get(0)).startsWith("Bearer ");
    }
}
