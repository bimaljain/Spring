package _001;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class ClientDetailsServiceDAO implements ClientDetailsService {

	@Autowired
	UserDAO userDAO;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException{
		System.out.println("loadUserByUsername username="+clientId);	

		return new ClientDetails() {

				@Override
				public String getClientId() {
					return "trusted-client";
				}

				@Override
				public Set<String> getResourceIds() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public boolean isSecretRequired() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public String getClientSecret() {
					return "secret";
				}

				@Override
				public boolean isScoped() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public Set<String> getScope() {
					Set<String> x = new TreeSet<String>();
					x.add("read");
					x.add("write");
					x.add("trust");
					return x;
				}

				@Override
				public Set<String> getAuthorizedGrantTypes() {
					Set<String> x = new TreeSet<String>();
					x.add("password");
					x.add("refresh_token");
					x.add("client_credentials");
					return x;
				}

				@Override
				public Set<String> getRegisteredRedirectUri() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Collection<GrantedAuthority> getAuthorities() {
					List<GrantedAuthority> auths = new java.util.ArrayList<GrantedAuthority>();
					auths.add(new SimpleGrantedAuthority("ROLE_OAUTH_CLIENT"));
					return auths;
				}

				@Override
				public Integer getAccessTokenValiditySeconds() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Integer getRefreshTokenValiditySeconds() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public boolean isAutoApprove(String scope) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public Map<String, Object> getAdditionalInformation() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
	}
