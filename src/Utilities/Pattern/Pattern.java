package Utilities.Pattern;

public class Pattern {

	public String part;
	public Pattern next;

	public Pattern(String part) {
		this.part = part;
	}
	
	public Pattern(String part, Pattern next){
		this.part = part;
		this.next = next;
	}
	
	public Pattern(String[] parts, int index) {
		this.part = parts[index];
		
		if (index < parts.length -1){
			this.next = new Pattern(parts, index +1);
		}
	}

	public boolean Match(Pattern pattern){
		boolean thisPartMatches = part.equals("*") || part.equals(pattern.part);
		if (!thisPartMatches){
			return false;
		}
		boolean isFinal = next == null && (pattern.next == null || part.equals("*"));
		if (isFinal){
			System.out.println("final");
			return true;
		}
		
		boolean nextPartMatches = next.Match(pattern.next);
		return nextPartMatches;
	}
	
	@Override
	public String toString() {
		if (next != null){
			return this.part +'/'+next;
		} else{
			return this.part;
		}
	}
}
