package Utilities.Url;

import Utilities.Pattern;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UrlFactoryTest extends TestCase
{
	// scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]'
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UrlFactoryTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UrlFactoryTest.class );
    }

    /**
     * Ensure that an url with just pattern can be parsed
     */
    public void testFromString_SimplePattern(){
    	String stringUrl = "test/subtest";
    	Url url = UrlFactory.FromString(stringUrl);
    	
    	Pattern expected = new Pattern(stringUrl);
    	assertEquals(expected, url.Path);
    }
    
    /**
     * Ensure that an url with just pattern produces empty list and not null as queries
     */
    public void testFromString_QueriesNotNull(){
    	String stringUrl = "test/subtest";
    	Url url = UrlFactory.FromString(stringUrl);
    	
    	assertNotNull(url.Queries);
    	assertEquals(0, url.Queries.size());
    }

    /**
     * Ensure that an url with a slash in front of the path can be parsed
     */
    public void testFromString_PatternWithSlash(){
    	String stringUrl = "test/subtest";
    	Url url = UrlFactory.FromString("/"+stringUrl);
    	
    	Pattern expected = new Pattern(stringUrl);
    	assertEquals(expected, url.Path);
    }
    
    /**
     * Ensure that an url with a pattern and a single query can be parsed
     */
    public void testFromString_PatternAndSingleQuery(){
    	String stringPattern = "test/subtest";
    	String stringQuery = "testq=que";
    	Url url = UrlFactory.FromString(stringPattern+"?"+stringQuery);
    	
    	Pattern expectedPattern = new Pattern(stringPattern);
    	assertEquals(expectedPattern, url.Path);
    	
    	assertEquals(1, url.Queries.size());
    	assertEquals(stringQuery, url.Queries.get(0));
    }
    
    /**
     * Ensure that an url with a pattern and a multiple queries can be parsed
     */
    public void testFromString_PatternAndMultipleQueries(){
    	String stringPattern = "test/subtest";
    	String stringQuery1 = "testq=que";
    	String stringQuery2 = "hax=1,2,3";
    	Url url = UrlFactory.FromString(stringPattern+"?"+stringQuery1+"&"+stringQuery2);
    	
    	Pattern expectedPattern = new Pattern(stringPattern);
    	assertEquals(expectedPattern, url.Path);
    	
    	assertEquals(2, url.Queries.size());
    	assertEquals(stringQuery1, url.Queries.get(0));
    	assertEquals(stringQuery2, url.Queries.get(1));
    }
}
