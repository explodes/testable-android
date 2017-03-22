package io.explod.testable.data.remote;

import org.junit.Test;

import meta.BaseRoboTest;

import static org.junit.Assert.assertEquals;

public class LinkParserTest extends BaseRoboTest {

	@Test
	public void parseLink() throws Exception {
		String[][] cases = new String[][]{
			// case: only 1 next
			{"<https://api.github.com/user/782367/repos?page=2>; rel=\"next\"", "https://api.github.com/user/782367/repos?page=2"},
			// case: next first
			{"<https://api.github.com/user/782367/repos?page=2>; rel=\"next\", <https://api.github.com/user/782367/repos?page=2>; rel=\"last\"", "https://api.github.com/user/782367/repos?page=2"},
			// case: next last
			{"<https://api.github.com/user/782367/repos?page=2>; rel=\"last\", <https://api.github.com/user/782367/repos?page=2>; rel=\"next\"", "https://api.github.com/user/782367/repos?page=2"},
			// case: no next
			{"<https://api.github.com/user/782367/repos?page=2>; rel=\"another\", <https://api.github.com/user/782367/repos?page=2>; rel=\"last\"", null},
			// case: empty
			{"", null},
			// case: null
			{null, null},
		};

		for (String[] testCase : cases) {
			String result = LinkParser.parseLink(testCase[0], "next");
			assertEquals("Invalid result for case: " + testCase[0], testCase[1], result);
		}

	}

}