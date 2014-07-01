package com.mojang.auth;

public class HttpBody
{
  private String bodyString;

  public HttpBody(String bodyString)
  {
    this.bodyString = bodyString;
  }

  public byte[] getBytes() {
    return this.bodyString != null ? this.bodyString.getBytes() : new byte[0];
  }
}
