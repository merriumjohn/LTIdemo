package ltidemo.lti.launch;

public class LtiVerificationException  extends Exception {
    public LtiVerificationException(String message, Exception exception) {
        super(message, exception);
    }
}
