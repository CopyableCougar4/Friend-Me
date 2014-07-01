package com.mojang.auth;

public class ProfileSearchResult
{
  private Profile[] profiles;
  private int size;

  public Profile[] getProfiles()
  {
    return this.profiles;
  }

  public void setProfiles(Profile[] profiles) {
    this.profiles = profiles;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}