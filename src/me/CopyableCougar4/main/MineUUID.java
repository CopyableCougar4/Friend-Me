package me.CopyableCougar4.main;

import java.util.UUID;

import com.mojang.auth.HttpProfileRepository;
import com.mojang.auth.Profile;
import com.mojang.auth.ProfileCriteria;

public class MineUUID {
	
	private static final HttpProfileRepository profileRepository = new HttpProfileRepository();
	private static final String AGENT = "minecraft";
	
	public static UUID getUUID(String name)
	  {
	    Profile[] profiles = profileRepository.findProfilesByCriteria(new ProfileCriteria[] { new ProfileCriteria(name, AGENT) });

	    if (profiles.length == 1) {
	    	UUID uuid = UUID.fromString(profiles[0].getId());
	    	DatabaseConn.addRecord(uuid, name);
	      return uuid;
	    }
	    return UUID.fromString("");
	  }

}
