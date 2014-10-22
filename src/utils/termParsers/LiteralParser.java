package utils.termParsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.PatternList;
import utils.Theme;

/**
 * Class LiteralParser
 * 
 * Superclass for classes that extract literals from Wikipedia strings by help
 * of "mapsTo" patterns.
 * 
 * @author Fabian
 */
public abstract class LiteralParser extends TermParser {

	/** Holds the pattern that indicates a result */
	public static final Pattern resultPattern = Pattern
			.compile("_result_([^_]++)_([^_]*+)_");

	/** Holds the pattern list */
	protected final PatternList patternList;

	/**
	 * Constructs a LiteralParser from a theme that contains patterns
	 * 
	 * @throws IOException
	 */
	protected LiteralParser(Theme patterns) throws IOException {
		patternList = new PatternList(patterns, "<mapsTo>");
	}

	/** Produces a result entity from a String */
	public abstract String resultEntity(Matcher resultMatch);

	@Override
	public List<String> extractList(String input) {
		input = patternList.transform(input);
		if (input == null)
			return (Collections.emptyList());
		List<String> result = new ArrayList<>();
		Matcher m = resultPattern.matcher(input);
		while (m.find()) {
			String resultEntity = resultEntity(m);
			if (resultEntity != null)
				result.add(resultEntity);
		}
		return (result);
	}

	/** Test method */
	public static void main(String[] args) throws Exception {
	}
}