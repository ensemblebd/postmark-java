package com.postmark.java;

import java.util.HashMap;

public final class PostmarkErrorCodes {

    private static HashMap<Integer, PostmarkErrorCode> codes;

    private PostmarkErrorCodes() {
        codes = new HashMap<Integer, PostmarkErrorCode>();
        codes.put(10,new PostmarkErrorCode("Bad or missing API token","Your request did not contain the correct API token in the header. Refer to the request’s API reference page to see which API token is required or learn more about authenticating with Postmark."));
        codes.put(100,new PostmarkErrorCode("Maintenance","The Postmark API is offline for maintenance."));
        codes.put(300,new PostmarkErrorCode("Invalid email request","Validation failed for the email request JSON data that you provided."));
        codes.put(400,new PostmarkErrorCode("Sender Signature not found","You’re trying to send email with a From address that doesn’t have a sender signature. Refer to your existing list of Sender Signatures or add a new one."));
        codes.put(401,new PostmarkErrorCode("Sender signature not confirmed","You’re trying to send email with a From address that doesn’t have a confirmed sender signature. You can resend the confirmation email on the Sender Signatures page."));
        codes.put(402,new PostmarkErrorCode("Invalid JSON","The JSON data you provided is syntactically incorrect. We recommend running your JSON through a validator before issuing another request."));
        codes.put(403,new PostmarkErrorCode("Incompatible JSON","The JSON data you provided is syntactically correct, but still doesn’t contain the fields we expect. Refer to the request's API reference page to see a list of required JSON body parameters."));
        codes.put(405,new PostmarkErrorCode("Not allowed to send","Your account has run out of credits. You can purchase more on the Credits page."));
        codes.put(406,new PostmarkErrorCode("Inactive recipient","You tried to send email to a recipient that has been marked as inactive. Inactive recipients have either generated a hard bounce or a spam complaint. In this case, only hard bounce recipients can be reactivated by searching for them on your server’s Activity page and clicking the “Reactivate” button."));
        codes.put(409,new PostmarkErrorCode("JSON required","Your HTTP request doesn’t have the Accept and Content-Type headers set to application/json."));
        codes.put(410,new PostmarkErrorCode("Too many batch messages","Your batched request contains more than 500 messages."));
        codes.put(411,new PostmarkErrorCode("Forbidden attachment type","The file type of the attachment isn’t allowed. Refer to our list on forbidden file types."));
        codes.put(412,new PostmarkErrorCode("Account is Pending","The account that is associated with the send request is still pending approval. While an account is pending approval, email recipients must have the same domain as the one found in the email's from address."));
        codes.put(413,new PostmarkErrorCode("Account May Not Send","The account that is associated with the send request is not approved for sending."));
        codes.put(500,new PostmarkErrorCode("Sender signature query exception","You provided invalid querystring parameters in your request. Refer to the Sender Signatures API reference for a list of accepted querystring parameters."));
        codes.put(501,new PostmarkErrorCode("Sender Signature not found by id","We couldn’t locate the Sender Signature you’re trying to manage from the id passed in."));
        codes.put(502,new PostmarkErrorCode("No updated Sender Signature data received","You didn’t pass in any valid updated Sender Signature data."));
        codes.put(503,new PostmarkErrorCode("You cannot use a public domain","You tried to create a Sender Signature with a public domain which isn’t allowed."));
        codes.put(504,new PostmarkErrorCode("Sender Signature already exists","You tried to create a Sender Signature that already exists on Postmark."));
        codes.put(505,new PostmarkErrorCode("DKIM already scheduled for renewal","The DKIM you tried to renew is already scheduled to be renewed."));
        codes.put(506,new PostmarkErrorCode("This Sender Signature already confirmed","The signature you tried to resend a confirmation to has already been confirmed by a user."));
        codes.put(507,new PostmarkErrorCode("You do not own this Sender Signature","This Sender Signature cannot be found using your credentials."));
        codes.put(510,new PostmarkErrorCode("This domain was not found","We couldn’t locate the Domain you’re trying to manage from the id passed in."));
        codes.put(511,new PostmarkErrorCode("Invalid fields supplied","You didn’t pass in any valid Domain data."));
        codes.put(512,new PostmarkErrorCode("Domain already exists","You tried to create a Domain that already exists on your account."));
        codes.put(513,new PostmarkErrorCode("You do not own this Domain","This Domain cannot be found using your credentials."));
        codes.put(514,new PostmarkErrorCode("Name is a required field to create a Domain","You must set the Name parameter to create a Damain."));
        codes.put(515,new PostmarkErrorCode("Name field must be less than or equal to 255 characters","The Name you have specified for this Domain is too long."));
        codes.put(516,new PostmarkErrorCode("Name format is invalid","The Name you have specified for this Domain is formatted incorrectly."));
        codes.put(520,new PostmarkErrorCode("You are missing a required field to create a Sender Signature.","When creating a Sender Signature, you must supply a value for Name and FromEmail."));
        codes.put(521,new PostmarkErrorCode("A field in the Sender Signature request is too long.","View the Message property of the response for details."));
        codes.put(522,new PostmarkErrorCode("Value for field is invalid.","Value might be an invalid email address or domain. View the Message property of the response for details."));
        codes.put(600,new PostmarkErrorCode("Server query exception","You provided invalid querystring parameters in your request. Refer to the Servers API reference for a list of accepted querystring parameters."));
        codes.put(601,new PostmarkErrorCode("Server does not exist","You tried to manage a server that doesn’t exist with your credentials."));
        codes.put(602,new PostmarkErrorCode("Duplicate Inbound Domain","The Inbound Domain you specified is already in use on Postmark."));
        codes.put(603,new PostmarkErrorCode("Server name already exists","You tried to create a Server name that already exists in your list."));
        codes.put(604,new PostmarkErrorCode("You don’t have delete access","You don’t have permission to delete Servers through the API. Please contact support if you wish to have this functionality."));
        codes.put(605,new PostmarkErrorCode("Unable to delete Server","We couldn’t delete this Server. Please contact support."));
        codes.put(606,new PostmarkErrorCode("Invalid webhook URL","The webhook URL you’re trying to use is invalid or contains an internal IP range."));
        codes.put(607,new PostmarkErrorCode("Invalid Server color","The server color you specified isn't supported. Please choose either Purple, Blue, Turqoise, Green, Red, Orange, Yellow, or Grey for server color."));
        codes.put(608,new PostmarkErrorCode("Server name missing or invalid","The Server name you provided is invalid or missing."));
        codes.put(609,new PostmarkErrorCode("No updated Server data received","You didn’t pass in any valid updated Server data."));
        codes.put(610,new PostmarkErrorCode("Invalid MX record for Inbound Domain","The Inbound Domain provided doesn’t have an MX record value of inbound.postmarkapp.com."));
        codes.put(611,new PostmarkErrorCode("InboundSpamThreshold value is invalid.","Please use a number between 0 and 30 in incrememts of 5."));
        codes.put(700,new PostmarkErrorCode("Messages query exception","You provided invalid querystring parameters in your request. Refer to the Messages API reference for a list of accepted querystring parameters."));
        codes.put(701,new PostmarkErrorCode("Message doesn’t exist","This message doesn’t exist."));
        codes.put(702,new PostmarkErrorCode("Could not bypass this blocked inbound message, please contact support.","There was a problem processing this bypass request. Please contact support to fix the issue."));
        codes.put(703,new PostmarkErrorCode("Could not retry this failed inbound message, please contact support.","There was a problem processing this retry request. Please contact support to fix the issue."));
        codes.put(800,new PostmarkErrorCode("Trigger query exception","You provided invalid querystring parameters in your request. Refer to the Triggers API reference for a list of accepted querystring parameters."));
        codes.put(801,new PostmarkErrorCode("Trigger for this tag doesn’t exist","You tried to manage a trigger that doesn’t exist in your server."));
        codes.put(803,new PostmarkErrorCode("Tag with this name already has trigger associated with it","The server already has a trigger associated with the Tag name you provided."));
        codes.put(808,new PostmarkErrorCode("Name to match is missing","MatchName property is required in request JSON body. Refer to the Triggers API reference for more details."));
        codes.put(809,new PostmarkErrorCode("No trigger data received","You didn’t provide JSON body parameters in your request. Refer to the Triggers API reference for more details on required parameters."));
        codes.put(810,new PostmarkErrorCode("This inbound rule already exists.","You tried to add a rule that already exists for thie server. Please choose a unique rule to add."));
        codes.put(811,new PostmarkErrorCode("Unable to remove this inbound rule, please contact support.","We weren't able to remove this rule from your server. Please contact support to resolve the issue."));
        codes.put(812,new PostmarkErrorCode("This inbound rule was not found.","The inbound rule you are trying to administer does not exist for this server."));
        codes.put(813,new PostmarkErrorCode("Not a valid email address or domain.","Please use a valid email address or valid domain to setup an inbound domain rule."));
        codes.put(900,new PostmarkErrorCode("Stats query exception","You provided invalid querystring parameters in your request. Refer to the Stats API reference for a list of accepted querystring parameters."));
        codes.put(1000,new PostmarkErrorCode("Bounces query exception","You provided invalid querystring parameters in your request. Refer to the Bounces API reference for a list of accepted querystring parameters."));
        codes.put(1001,new PostmarkErrorCode("Bounce was not found.","The BounceID or MessageID you are searching with is invalid."));
        codes.put(1002,new PostmarkErrorCode("BounceID parameter required.","You must supply a BounceID to get the bounce dump."));
        codes.put(1003,new PostmarkErrorCode("Cannot activate bounce.","Certain bounces and SPAM complaints cannot be activated by the user."));
        codes.put(1100,new PostmarkErrorCode("Template query exception.","The value of a GET parameter for the request is not valid."));
        codes.put(1101,new PostmarkErrorCode("TemplateId not found.","The TemplateId references a Template that does not exist, or is not associated with the Server specified for this request."));
        codes.put(1105,new PostmarkErrorCode("Template limit would be exceeded.","A Server may have up to 300 active templates, processing this request would exceed this limit."));
        codes.put(1109,new PostmarkErrorCode("No Template data received.","You didn’t provide JSON body parameters in your request. Refer to the Template API reference for more details on required parameters."));
        codes.put(1120,new PostmarkErrorCode("A required Template field is missing.","A required field is missing from the body of the POST request."));
        codes.put(1121,new PostmarkErrorCode("Template field is too large.","One of the values of the request's body exceeds our size restrictions for that field."));
        codes.put(1122,new PostmarkErrorCode("A Templated field has been submitted that is invalid.","One of the fields of the request body is invalid."));
        codes.put(1123,new PostmarkErrorCode("A field was included in the request body that is not allowed.","A field is included in the request that will be ignored, or is not applicable to the endpoint to which it has been sent."));
    }

    public static PostmarkErrorCode get(String id) {
        return get(Integer.parseInt(id));
    }
    public static PostmarkErrorCode get(int id) {
        return codes.get(id);
    }


}

