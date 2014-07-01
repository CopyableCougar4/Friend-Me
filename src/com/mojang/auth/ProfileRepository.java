package com.mojang.auth;

public abstract interface ProfileRepository
{
  public abstract Profile[] findProfilesByCriteria(ProfileCriteria[] paramArrayOfProfileCriteria);
}