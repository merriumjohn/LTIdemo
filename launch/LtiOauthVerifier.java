package ltidemo.lti.launch;

import net.oauth.*;
import net.oauth.server.OAuthServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

public class LtiOauthVerifier implements LtiVerifier {

    public static final String OAUTH_KEY_PARAMETER= "oauth_consumer_key";

    private final static Logger logger = Logger.getLogger(LtiOauthVerifier.class.getName());

    /**
     * This method verifies the signed HttpServletRequest
     * @param request the HttpServletRequest that will be verified
     * @param secret the secret to verify the properties with
     * @return the result of the verification, along with contextual
     * information
     * @throws LtiVerificationException
     */
    @Override
    public LtiVerificationResult verify(HttpServletRequest request, String secret) throws LtiVerificationException {
        OAuthMessage oam = OAuthServlet.getMessage(request, OAuthServlet.getRequestURL(request));
        String oauth_consumer_key = null;
        try {
            oauth_consumer_key = oam.getConsumerKey();
        } catch (Exception e) {
            return new LtiVerificationResult(false, LtiError.BAD_REQUEST, "Unable to find consumer key in message");
        }

        OAuthValidator oav = new SimpleOAuthValidator();
        OAuthConsumer cons = new OAuthConsumer(null, oauth_consumer_key, secret, null);
        OAuthAccessor acc = new OAuthAccessor(cons);

        try {
            oav.validateMessage(oam, acc);
        } catch (Exception e) {
            return new LtiVerificationResult(false, LtiError.BAD_REQUEST, "Failed to validate: " + e.getLocalizedMessage());
        }
        return new LtiVerificationResult(true, new LtiLaunch(request));
    }
  
}
