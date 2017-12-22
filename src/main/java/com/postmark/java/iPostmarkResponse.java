package com.postmark.java;

public interface iPostmarkResponse {


    public void setMessage(String message);
    public String getMessage();

    public PostmarkStatus getStatus();
    public void setStatus(PostmarkStatus status);

    public void updateStatus();

}
