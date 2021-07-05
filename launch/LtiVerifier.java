package ltidemo.lti.launch;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LtiVerifier {

 
    public LtiVerificationResult verify(HttpServletRequest request, String secret) throws LtiVerificationException;

  
}
