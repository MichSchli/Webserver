package Services.Redirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Services.Common.IRequestHandler;
import Utilities.*;
import Utilities.Request.Request;
import Utilities.Response.IResponse;

public class RedirectionService implements IRequestHandler {

	HashMap<Pattern, Pattern> patterns = new HashMap<Pattern, Pattern>();
	private IRequestHandler next;
	private RedirectionServiceConfiguration _configuration;

	public RedirectionService(IRequestHandler next, RedirectionServiceConfiguration configuration) {
		this.next = next;
		this._configuration = configuration;

		for (String redirection : _configuration.Redirections) {
			addRedirection(redirection);
		}
	}
	
	

	private void addRedirection(String redirection) {
		String[] parts = redirection.split("=>");
		
		Pattern from = addressToPattern(parts[0].trim());
		Pattern to = addressToPattern(parts[1].trim());
		
		patterns.put(from, to);
	}



	private Pattern addressToPattern(String address) {
		String[] parts = address.split("/");
		return new Pattern(parts, 0);
	}



	@Override
	public IResponse Process(Request request) {
		System.out.println("redirect");
		for (Entry<Pattern, Pattern> entry : patterns.entrySet()) {
			if (entry.getKey().Match(request.Address)) {
				request.Address = replace(request.Address, entry.getKey(), entry.getValue());
				return next.Process(request);
			}
		}
		return next.Process(request);
	}

	private Pattern replace(Pattern input, Pattern pattern, Pattern replacement) {
		List<String> starParts = getStars(pattern, input);
		Collections.reverse(starParts);
		return doReplace(replacement, starParts);
	}

	private Pattern doReplace(Pattern newPattern, List<String> starParts) {
		if (newPattern == null) {
			String tba = starParts.remove(0);
			if (!starParts.isEmpty()) {
				return new Pattern(tba, doReplace(newPattern, starParts));
			} else {
				return new Pattern(tba, null);
			}
		} else if (newPattern.part.equals("*")) {
			String tba = starParts.remove(0);
			if (!starParts.isEmpty()) {
				return new Pattern(tba, doReplace(newPattern.next, starParts));
			} else {
				return new Pattern(tba, null);
			}
		} else {
			return new Pattern(newPattern.part, doReplace(newPattern.next, starParts));
		}
	}

	private List<String> getStars(Pattern input, Pattern pattern) {
		if (input == null && pattern == null) {
			return new ArrayList<String>();
		} else if (input == null) {
			List<String> remainders = getStars(input, pattern.next);
			remainders.add(pattern.part);
			return remainders;
		} else if (input.part.equals("*")) {
			List<String> remainders = getStars(input.next, pattern.next);
			remainders.add(pattern.part);
			return remainders;
		} else {
			return getStars(input.next, pattern.next);
		}
	}

}
