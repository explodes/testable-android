package io.explod.testable.data.remote.model;


import com.google.gson.annotations.SerializedName;

public class GithubUserRepositoryResponse {

	public static class Owner {
		@SerializedName("login")
		public String username;
	}

	public String name;

	public Owner owner;
}
