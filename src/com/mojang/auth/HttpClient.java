package com.mojang.auth;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

public abstract interface HttpClient
{
  public abstract String post(URL paramURL, HttpBody paramHttpBody, List<HttpHeader> paramList)
    throws IOException;

  public abstract String post(URL paramURL, Proxy paramProxy, HttpBody paramHttpBody, List<HttpHeader> paramList)
    throws IOException;
}