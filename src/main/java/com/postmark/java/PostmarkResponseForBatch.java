package com.postmark.java;

import java.util.ArrayList;
import java.util.List;

public class PostmarkResponseForBatch implements iPostmarkResponse {

    public List<PostmarkResponse> responses;
    public String message;
    public PostmarkStatus status;

    public PostmarkResponseForBatch() {
        this.message="";
        this.responses = new ArrayList<PostmarkResponse>();
    }

    public void setMessage(String message) {
        this.message=message;
    }
    public String getMessage() {
        return this.message;
    }

    public PostmarkStatus getStatus() {
        return this.status;
    }
    public void setStatus(PostmarkStatus status) {
        this.status=status;
    }

    public void updateStatus() {

    }


}
