// The MIT License
//
// Copyright (c) 2010 Jared Holdcroft
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.postmark.java;

import java.util.*;

/**
 * Postmark for Java
 * <p/>
 * This library can be used to leverage the postmarkapp.com functionality from a Java client
 * <p/>
 * http://github.com/jaredholdcroft/postmark-java
 */

public class TestClient {

    public static void main(String[] args)
    {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();

        headers.add(new NameValuePair("HEADER", "test"));

        PostmarkMessage message = new PostmarkMessage(
            "from@test.com", "to@test.com", "replyTo@test.com",
            "cc@test.com", "bcc@test.com", "Test Subject", "Hello", false, null, headers
        );

        String testAPIKey = "POSTMARK_API_TEST";

        PostmarkClient client = new PostmarkClient(testAPIKey);

        try {
            PostmarkResponse response = client.sendMessage(message);
            System.out.println(response.toString());
        } catch (PostmarkException pe) {
            System.out.println("An error has occurred : " + pe.getMessage());
        }
    }
}
