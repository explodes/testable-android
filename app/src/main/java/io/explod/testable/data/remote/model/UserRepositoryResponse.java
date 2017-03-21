package io.explod.testable.data.remote.model;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserRepositoryResponse {

	public static class Owner {
		@SerializedName("login")
		public String username;
	}

	public String name;

	@Nullable
	public String description;

	public Owner owner;

	@SerializedName("forks_count")
	public int forks;

	@SerializedName("stargazers_count")
	public int stars;

	@SerializedName("watchers_count")
	public int watchers;
}
