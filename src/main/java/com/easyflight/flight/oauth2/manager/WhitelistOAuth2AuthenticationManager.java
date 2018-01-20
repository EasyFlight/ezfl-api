package com.easyflight.flight.oauth2.manager;

import com.easyflight.flight.entity.User;
import com.easyflight.flight.oauth2.UserInfoPrincipal;
import com.easyflight.flight.request.UserRequest;
import com.easyflight.flight.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Victor Ikoro on 9/16/2017.
 */
public class WhitelistOAuth2AuthenticationManager extends OAuth2AuthenticationManager {

    private ResourceServerTokenServices tokenServices;

    private ClientDetailsService clientDetailsService;

    private String[] validResourceIds;
    private UserService userService;

    public void setResourceIds(String[] resourceIds) {
        this.validResourceIds = resourceIds;
    }

    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    /**
     * @param tokenServices the tokenServices to set
     */
    public void setTokenServices(ResourceServerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    public void afterPropertiesSet() {
        Assert.state(tokenServices != null, "TokenServices are required");
    }

    /**
     * Expects the incoming authentication request to have a principal value that is an access token value (e.g. from an
     * authorization header). Loads an authentication from the {@link ResourceServerTokenServices} and checks that the
     * resource id is contained in the {@link AuthorizationRequest} (if one is specified). Also copies authentication
     * details over from the input to the output (e.g. typically so that the access token value and request details can
     * be reported later).
     *
     * @param authentication an authentication request containing an access token value as the principal
     * @return an {@link OAuth2Authentication}
     *
     * @see org.springframework.security.authentication.AuthenticationManager#authenticate(org.springframework.security.core.Authentication)
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        }
        String token = (String) authentication.getPrincipal();
        OAuth2Authentication auth = tokenServices.loadAuthentication(token);
        if (auth == null) {
            throw new InvalidTokenException("Invalid token: " + token);
        }

        //Add whitelisted client ids as valid resource ids
        Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();

        if (validResourceIds != null && resourceIds != null && !resourceIds.isEmpty() ) {

            int validResourcesCount = 0;
            for (String resourceId : validResourceIds) {
                if(resourceIds.contains(resourceId)) validResourcesCount++;
            }
            if(validResourcesCount == 0) throw new OAuth2AccessDeniedException("Invalid token does not contain a valid resource id");
        }

        checkClientDetails(auth);

        if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            // Guard against a cached copy of the same details
            if (!details.equals(auth.getDetails())) {
                // Preserve the authentication details from the one loaded by token services
                details.setDecodedDetails(auth.getDetails());
            }
        }
        auth.setDetails(authentication.getDetails());
        auth.setAuthenticated(true);

        //Save Principal as user object if contains email && user doesn't exist
        UserInfoPrincipal principal = (UserInfoPrincipal) auth.getPrincipal();
        if (StringUtils.hasText(principal.getEmail())) {
            User user = userService.getUser(principal.getEmail());
            if (user == null) {
                UserRequest request = new UserRequest();
                BeanUtils.copyProperties(principal, request);
                request.setFullName(principal.getName());
                userService.createUser(request);
            }
        }
        return auth;

    }

    public void setUserService(UserService userService) {

        this.userService = userService;
    }

    private void checkClientDetails(OAuth2Authentication auth) {
        if (clientDetailsService != null) {
            ClientDetails client;
            try {
                client = clientDetailsService.loadClientByClientId(auth.getOAuth2Request().getClientId());
            }
            catch (ClientRegistrationException e) {
                throw new OAuth2AccessDeniedException("Invalid token contains invalid client id");
            }
            Set<String> allowed = client.getScope();
            for (String scope : auth.getOAuth2Request().getScope()) {
                if (!allowed.contains(scope)) {
                    throw new OAuth2AccessDeniedException(
                            "Invalid token contains disallowed scope (" + scope + ") for this client");
                }
            }
        }
    }


}
