package io.explod.testable.data.local;

public class AppLocalRepo {

	public AppLocalRepo() {
	}

	public final LocalGithubUserRepo Users = new LocalGithubUserRepo();
	public final LocalGithubRepositoryRepo Repositories = new LocalGithubRepositoryRepo();

}
