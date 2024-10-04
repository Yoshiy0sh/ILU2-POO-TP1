package exceptions;

public class VillageSansChefException extends Exception{

	public VillageSansChefException(){
		super("Le village doit avoir un chef");
	}
}
