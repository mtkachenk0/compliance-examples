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
package com.saltedge.connector.sdk.api.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saltedge.connector.sdk.config.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class ConfirmPaymentRequest extends PrioraBaseRequest {
    @JsonProperty(Constants.KEY_ORIGINAL_REQUEST)
    @Valid
    @NotNull
    public OriginalRequest originalRequest;

    @JsonProperty(Constants.KEY_PRIORA_PAYMENT_ID)
    @NotNull
    public Long prioraPaymentId;

    @JsonProperty(Constants.KEY_PAYMENT_ID)
    @NotNull
    public String paymentId;

    public static class OriginalRequest {
        @JsonProperty(Constants.KEY_CLIENT_JWT)
        @NotNull
        public String clientJwt;

        @JsonProperty(Constants.KEY_CLIENT_PAYLOAD)
        @Valid
        @NotNull
        public ConfirmPaymentClientPayload payload;
    }

    public Map<String, String> getCredentials() {
        try {
            return originalRequest.payload.data.credentials;
        } catch (Exception e) {
            return null;
        }
    }
}