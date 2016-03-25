package Utilities;

public class Pattern {

	public String part;
	public Pattern next;

	public Pattern(String part) {
		String[] parts = part.split("/");
		this.part = parts[0];
		
		if (parts.length > 1){
			this.next = new Pattern(parts, 1);
		}
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
			return true;
		}
		
		if (next == null && pattern.next != null){
			return false;
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

	public int Length() {
		if (next != null){
			return 1 + next.Length();
		} else{
			return 1;
		}
	}

	public String GetPart(int i) {
		if (i == 0){
			return part;
		} else{
			return next.GetPart(i-1);
		}
	}
}
