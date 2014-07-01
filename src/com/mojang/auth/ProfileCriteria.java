package com.mojang.auth;

public class ProfileCriteria
{
  private final String name;
  private final String agent;

  public ProfileCriteria(String name, String agent)
  {
    this.name = name;
    this.agent = agent;
  }

  public String getAgent() {
    return this.agent;
  }

  public String getName() {
    return this.name;
  }
}