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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Postmark for Java
 * <p>
 * This library can be used to leverage the postmarkapp.com functionality from a Java client
 * </p>
 *
 * @see <a href="http://github.com/jaredholdcroft/postmark-java"></a>
 */

// Class that does the heavy lifting
public class PostmarkClient {

    private static Logger logger = Logger.getLogger("com.postmark.java");
    private String serverToken;

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new SkipMeExclusionStrategy(Boolean.class));
        gsonBuilder.disableHtmlEscaping();

        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.ALL);
    }


    /**
     * Initializes a new instance of the PostmarkClient class.
     *
     * If you do not have a server token you can request one by signing up to
     * use Postmark: http://postmarkapp.com.
     *
     * @param serverToken the postmark server token
     */
    public PostmarkClient(String serverToken) {

        this.serverToken = serverToken;

    }

    /**
     * Sends a message through the Postmark API.
     * All email addresses must be valid, and the sender must be
     * a valid sender signature according to Postmark. To obtain a valid
     * sender signature, log in to Postmark and navigate to:
     * http://postmarkapp.com/signatures.
     *
     * @param from    An email address for a sender
     * @param to      An email address for a recipient
     * @param replyTo An email address for the reply-to
     * @param cc      An email address for CC
     * @param subject The message subject line
     * @param body    The message body
     * @param isHTML  Is the body text HTML
     * @param tag     A tag to identify the message in postmark
     * @throws PostmarkException if unable to send message
     * @return {@link PostmarkResponse} with details about the transaction
     */
    public PostmarkResponse sendMessage(String from, String to, String replyTo, String cc, String subject, String body, boolean isHTML, String tag) throws PostmarkException {
        return sendMessage(from, to, replyTo, cc, null, subject, body, isHTML, tag, null);
    }

    /**
     * Sends a message through the Postmark API.
     * All email addresses must be valid, and the sender must be
     * a valid sender signature according to Postmark. To obtain a valid
     * sender signature, log in to Postmark and navigate to:
     * http://postmarkapp.com/signatures.
     *
     * @param from    An email address for a sender
     * @param to      An email address for a recipient
     * @param replyTo An email address for the reply-to
     * @param cc      An email address for CC
     * @param subject The message subject line
     * @param body    The message body
     * @param isHTML  Is the body text HTML
     * @param tag     A tag to identify the message in postmark
     * @param headers A collection of additional mail headers to send with the message
     * @throws PostmarkException if unable to send message
     * @return {@link PostmarkResponse} with details about the transaction
     */
    public PostmarkResponse sendMessage(String from, String to, String replyTo, String cc, String subject, String body, boolean isHTML, String tag, List<NameValuePair> headers) throws PostmarkException {
        return sendMessage(from, to, replyTo, cc, null, subject, body, isHTML, tag, headers);
    }

    /**
     * Sends a message through the Postmark API.
     * All email addresses must be valid, and the sender must be
     * a valid sender signature according to Postmark. To obtain a valid
     * sender signature, log in to Postmark and navigate to:
     * http://postmarkapp.com/signatures.
     *
     * @param from    An email address for a sender
     * @param to      An email address for a recipient
     * @param replyTo An email address for the reply-to
     * @param cc      An email address for CC
     * @param bcc     An email address for BCC
     * @param subject The message subject line
     * @param body    The message body
     * @param isHTML  Is the body text HTML
     * @param tag     A tag to identify the message in postmark
     * @param headers A collection of additional mail headers to send with the message
     * @throws PostmarkException if unable to send message
     * @return {@link PostmarkResponse} with details about the transaction
     */
    public PostmarkResponse sendMessage(String from, String to, String replyTo, String cc, String bcc, String subject, String body, boolean isHTML, String tag, List<NameValuePair> headers) throws PostmarkException {
        PostmarkMessage message = new PostmarkMessage(from, to, replyTo, subject, bcc, cc, body, isHTML, tag, headers);
        return sendMessage(message);
    }

    /**
     * Sends a message through the Postmark API.
     * All email addresses must be valid, and the sender must be
     * a valid sender signature according to Postmark. To obtain a valid
     * sender signature, log in to Postmark and navigate to:
     * http://postmarkapp.com/signatures.
     *
     * @param message A prepared message instance.
     * @throws PostmarkException if unable to send message
     * @return A response object
     */
    public PostmarkResponse sendMessage(PostmarkMessage message) throws PostmarkException {

        HttpClient httpClient = new DefaultHttpClient();
        PostmarkResponse theResponse = new PostmarkResponse();

        try {

            // Create post request to Postmark API endpoint
            HttpPost method = new HttpPost("https://api.postmarkapp.com/email");

            // Add standard headers required by Postmark
            method.addHeader("Accept", "application/json");
            method.addHeader("Content-Type", "application/json");
            method.addHeader("X-Postmark-Server-Token", serverToken);
            method.addHeader("User-Agent", "Postmark-Java");

            // Validate and clean the message
            message.clean();// we should try and clean it before we validate.
            message.validate();

            // Convert the message into JSON content
            Gson gson = gsonBuilder.create();
            String messageContents = gson.toJson(message);
            logger.info("Message contents: " + messageContents);

            // Add JSON as payload to post request
            StringEntity payload = new StringEntity(messageContents, "UTF-8");
            method.setEntity(payload);

            // recoded to use pure response object instead of BasicResponseHandler, so as to attain message in event of error header.
            HttpResponse httpResponse = httpClient.execute(method);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(entity); //entity.getContent()

            logger.info("Message response: " + responseBody);
            theResponse = gson.fromJson(responseBody, PostmarkResponse.class);
            theResponse.updateStatus(); // update message & status based upon it's deserialized contents.
            if (statusCode!=200) {
                ProcessAndThrowResponseException(theResponse, statusCode, false);
            }
        } catch (PostmarkException e) {
            //Log it and rethrow it, don't wrap it
            logger.log(Level.SEVERE, "There has been an error sending your email: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There has been an error sending your email: " + e.getMessage());
            throw new PostmarkException(e);
        }
        finally {
            httpClient.getConnectionManager().shutdown();
        }

        return theResponse;
    }

    private static void ProcessAndThrowResponseException(iPostmarkResponse robj, int statusCode, boolean isBatch) throws PostmarkException {
        robj.setStatus(PostmarkStatus.SERVERERROR);
        if (!isBatch && statusCode==422) {
            robj.setStatus(PostmarkStatus.USERERROR);
            // message was already set via _ref.updateStatus()
        }
        switch(statusCode) {
            case 401:
                logger.log(Level.SEVERE, robj.getMessage()+": "+" Missing or incorrect API token in header.");
                robj.setStatus(PostmarkStatus.USERERROR);

            /*case 422:
                this is handled differently in batch (ignored)..
                    individual response objects will have the details of the failure.
                    Response header is still valid(200), even if all objects failed.
             */

            case 500:
                logger.log(Level.SEVERE, "Internal Server Error: " + statusCode);
            case 503:
                logger.log(Level.SEVERE, "Service Unavailable: " + statusCode);
            default:
                logger.log(Level.SEVERE, "There has been an unknown error("+statusCode+") sending your email: " + robj.getMessage());
                robj.setStatus(PostmarkStatus.UNKNOWN);
        }
        throw new PostmarkException(robj.getMessage());
    }

    /**
     * Sends a list of messages through the Postmark API.
     * All email addresses must be valid, and the sender must be
     * a valid sender signature according to Postmark. To obtain a valid
     * sender signature, log in to Postmark and navigate to:
     * http://postmarkapp.com/signatures.
     * Maximum of 500 per batch. Code does not handle business logic of splitting batches, throws error if above 500.
     *
     * @param messages A prepared message instance.
     * @throws PostmarkException if unable to send message
     * @return A response object
     */
    public PostmarkResponseForBatch sendMessages(List<PostmarkMessage> messages) throws PostmarkException {
        // response object will indicate entire status of operation given results found.
        PostmarkResponseForBatch thisResponse = new PostmarkResponseForBatch();
        Gson gson = gsonBuilder.create(); // user guide:  The Gson instance does not maintain any state while invoking Json operations. So, you are free to reuse the same object for multiple Json serialization and deserialization operations.

        if (messages.size()>500) {
            thisResponse.message="Too many messages. API is limited to 500.";
            throw new PostmarkException(thisResponse.message);
        }
        // pre-validate, to ensure ALL of them have no validation issues.
        for (int i=0; i<messages.size();i++) {
            // Validate and clean the message
            messages.get(i).clean(); // we should try and clean it before we validate.
            try {
                messages.get(i).validate(); // throws PostmarkException
            } catch(PostmarkException e) {
                thisResponse.message=e.getMessage();
                throw new PostmarkException(thisResponse.message);
            }
        }

        // now we begin request..
        HttpClient httpClient = new DefaultHttpClient();
        try {
            // Create post request to Postmark API endpoint
            HttpPost method = new HttpPost("https://api.postmarkapp.com/email/batch");

            // Add standard headers required by Postmark
            method.addHeader("Accept", "application/json");
            method.addHeader("Content-Type", "application/json");
            method.addHeader("X-Postmark-Server-Token", serverToken);
            method.addHeader("User-Agent", "Postmark-Java");

            // todo: use gson to serialize json using class object array, as opposed to literal string building (which may not properly handle quote escaping).
            String the_message="[";
            for (int i=0; i<messages.size();i++) {
                PostmarkMessage message = messages.get(i);
                // Convert the message into JSON content
                String messageContents = gson.toJson(message);
                // append to built message.
                the_message += messageContents+", ";
            }
            the_message+="]";

            // Add JSON as payload to post request
            StringEntity payload = new StringEntity(the_message, "UTF-8");
            method.setEntity(payload);

            // recoded to use pure response object instead of BasicResponseHandler, so as to attain message in event of error header.
            HttpResponse httpResponse = httpClient.execute(method);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            String responseBody = EntityUtils.toString(entity); //entity.getContent()

            // it should always be 200 in batch mode..
            if (statusCode!=200) {
                ProcessAndThrowResponseException(thisResponse, statusCode, true);
            }

            //logger.info("Message batch response: " + response);
            Type collectionType = new TypeToken<List<PostmarkResponse>>() {}.getType();
            thisResponse.responses = gson.fromJson(responseBody, collectionType);
            for (int i=0;i<thisResponse.responses.size();i++) {
                PostmarkResponse a_response = thisResponse.responses.get(i);
                a_response.updateStatus(); // update message & status based upon it's deserialized contents.
                // if the particular message failed for any reason, then our global response for the operation should indicate failure.
                if (a_response.status!=PostmarkStatus.SUCCESS) {
                    thisResponse.message += a_response.getMessage();
                    thisResponse.setStatus(PostmarkStatus.PARTIALFAILURE);
                }
            }
            if (thisResponse.getStatus()!=PostmarkStatus.PARTIALFAILURE) {
                thisResponse.setStatus(PostmarkStatus.SUCCESS);
            }

        } catch (PostmarkException e) {
            //Log it and rethrow it, don't wrap it
            logger.log(Level.SEVERE, "There has been an error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "There has been an error: " + e.getMessage());
            throw new PostmarkException(e);
        }
        finally {
            httpClient.getConnectionManager().shutdown();
        }

        return thisResponse;
    }
}
