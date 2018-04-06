package mp.cariaso.hr.security.oauth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/*
 * This class is used for the infrastructure Client. NOT the USER
 */
//@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private String id;
    private String secretKey;

    public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {

    	/*
    	 * TODO: QUERY THE DATABASE FOR CLIENT DETAILS THAT WILL BE USED HERE
    	 * SUCH AS AUTH GRANT AND ROLES/AUTHORITIES
    	 */
        if (clientId.equals(id))
        {
            List<String> authorizedGrantTypes = new ArrayList<String>();
            authorizedGrantTypes.add("password");
            authorizedGrantTypes.add("refresh_token");
            authorizedGrantTypes.add("client_credentials");
            authorizedGrantTypes.add("authorization_code");

            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(id);
            clientDetails.setClientSecret(secretKey);
            clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);

            return clientDetails;
        }
        else {
            throw new NoSuchClientException("No client recognized with id: "
                    + clientId);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}