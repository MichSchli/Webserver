package Utilities;

import org.junit.Assert;
import org.junit.Test;

public class PatternTest {
	
	@Test
	public void Create_FromUrl(){
		String string = "abc/def/12345/*/*";
		Pattern p = new Pattern(string);
		
		Assert.assertEquals(5, p.Length());
		
		Assert.assertEquals("abc", p.GetPart(0));
		Assert.assertEquals("def", p.GetPart(1));
		Assert.assertEquals("12345", p.GetPart(2));
		Assert.assertEquals("*", p.GetPart(3));
		Assert.assertEquals("*", p.GetPart(4));
	}
	
	@Test
	public void Create_IgnoreTrailingSlash(){
		String string = "abc/def/12345/*/*/";
		Pattern p = new Pattern(string);
		
		Assert.assertEquals(5, p.Length());
		
		Assert.assertEquals("abc", p.GetPart(0));
		Assert.assertEquals("def", p.GetPart(1));
		Assert.assertEquals("12345", p.GetPart(2));
		Assert.assertEquals("*", p.GetPart(3));
		Assert.assertEquals("*", p.GetPart(4));
	}
	
	@Test
	public void Create_IgnoreLeadingSlash(){
		String string = "/abc/def/12345/*/*";
		Pattern p = new Pattern(string);
		
		Assert.assertEquals(5, p.Length());
		
		Assert.assertEquals("abc", p.GetPart(0));
		Assert.assertEquals("def", p.GetPart(1));
		Assert.assertEquals("12345", p.GetPart(2));
		Assert.assertEquals("*", p.GetPart(3));
		Assert.assertEquals("*", p.GetPart(4));
	}
	
	
	@Test
	public void Create_WithoutSlashes(){
		String string = "abcdef/";
		Pattern p = new Pattern(string);
		
		Assert.assertEquals(1, p.Length());
		
		Assert.assertEquals("abcdef", p.GetPart(0));
	}
	
	@Test
	public void Equals_Null(){
		Pattern p1 = new Pattern("test");
		Pattern p2 = null;
		
		Assert.assertFalse(p1.equals(p2));
	}
	
	@Test
	public void Equals_DifferentClass(){
		Pattern p1 = new Pattern("test/abc/*/1234");
		Object p2 = new Integer(5);
		
		Assert.assertFalse(p1.equals(p2));		
	}
	
	@Test
	public void Equals_SamePattern(){
		Pattern p1 = new Pattern("test/abc/*/1234");
		Pattern p2 = new Pattern("test/abc/*/1234");
		
		Assert.assertTrue(p1.equals(p2));		
	}
	
	@Test
	public void Equals_FirstLonger(){
		Pattern p1 = new Pattern("test/abc/*");
		Pattern p2 = new Pattern("test/abc/*/1234");
		
		Assert.assertFalse(p1.equals(p2));
		
	}
	
	@Test
	public void Equals_SecondLonger(){
		Pattern p1 = new Pattern("test/abc/*/1234");
		Pattern p2 = new Pattern("test/abc/*");
		
		Assert.assertFalse(p1.equals(p2));
	}
	
	@Test
	public void Equals_DifferentPart(){
		Pattern p1 = new Pattern("test/abc/*/1234");
		Pattern p2 = new Pattern("test/*/hej/1234");
		
		Assert.assertFalse(p1.equals(p2));		
	}
	
	@Test
	public void Match_Agreement(){
		String a = "abc/def";
		
		Pattern p1 = new Pattern(a);
		Pattern p2 = new Pattern(a);
		
		Assert.assertTrue(p1.Match(p2));
	}
	
	@Test
	public void Match_StarInTemplate(){
		String a1 = "abc/*/ghj";
		String a2 = "abc/def/ghj";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertTrue(p1.Match(p2));
	}
	
	@Test
	public void Match_MultipleStars(){
		String a1 = "abc/*/ghj/*/*";
		String a2 = "abc/def/ghj/123/456";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertTrue(p1.Match(p2));
	}
	
	@Test
	public void Match_Disagreement(){
		String a1 = "abc/defg";
		String a2 = "abc/def";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertFalse(p1.Match(p2));
	}
	
	@Test
	public void Match_DisagreementWithStar(){
		String a1 = "abc/*/def";
		String a2 = "abc/def/ghj";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertFalse(p1.Match(p2));
	}
	
	@Test
	public void Match_DifferentLength(){
		String a1 = "abc/*/def";
		String a2 = "abc/123/def/456";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertFalse(p1.Match(p2));
	}
	
	@Test
	public void Match_DifferentLengthReverse(){
		String a1 = "abc/*/def/456";
		String a2 = "abc/123/def";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertFalse(p1.Match(p2));
	}
	
	@Test
	public void Match_DifferentLengthWithFinalStar(){
		String a1 = "abc/*/def/*";
		String a2 = "abc/123/def/456/789";
		
		Pattern p1 = new Pattern(a1);
		Pattern p2 = new Pattern(a2);
		
		Assert.assertTrue(p1.Match(p2));
	}
}
