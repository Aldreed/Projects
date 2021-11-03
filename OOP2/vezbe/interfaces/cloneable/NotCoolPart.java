package interfaces.cloneable;

public class NotCoolPart extends Part {

	@Override
	public Part clone() {
		return super.clone();
	}
	
	@Override
	public String toString() {
		return "Not \"Cool\"" + super.toString();
	}
}
