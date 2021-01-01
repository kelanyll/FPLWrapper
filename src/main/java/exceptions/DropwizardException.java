package exceptions;

public class DropwizardException extends RuntimeException {
	public DropwizardException(String message) {
		super(message);
	}
}
