package com.mojang.auth;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

@SuppressWarnings("unused")
public class HttpProfileRepository
  implements ProfileRepository
{
  private static final int MAX_PAGES_TO_CHECK = 100;
  private static Gson gson = new Gson();
  private HttpClient client;

  public HttpProfileRepository()
  {
    this(BasicHttpClient.getInstance());
  }

  public HttpProfileRepository(HttpClient client) {
    this.client = client;
  }

  public Profile[] findProfilesByCriteria(ProfileCriteria[] criteria)
  {
    try {
      HttpBody body = new HttpBody(gson.toJson(criteria));
      List<HttpHeader> headers = new ArrayList<HttpHeader>();
      headers.add(new HttpHeader("Content-Type", "application/json"));
      List<Profile> profiles = new ArrayList<Profile>();
      for (int i = 1; i <= 100; i++) {
        ProfileSearchResult result = post(new URL("https://api.mojang.com/profiles/page/" + i), body, headers);
        if (result.getSize() == 0) {
          break;
        }
        profiles.addAll(Arrays.asList(result.getProfiles()));
      }
      return (Profile[])profiles.toArray(new Profile[profiles.size()]);
    } catch (Exception e) {
    }
    return new Profile[0];
  }

  private ProfileSearchResult post(URL url, HttpBody body, List<HttpHeader> headers) throws IOException
  {
    String response = this.client.post(url, body, headers);
    return (ProfileSearchResult)gson.fromJson(response, ProfileSearchResult.class);
  }
}