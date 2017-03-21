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
}
