package _001;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {		
		System.out.println("loadUserByUsername username="+username);	
		User user = userDAO.getUserByUserId(username);

		if(user.getUserid() == null){
			throw new UsernameNotFoundException(username + " not found");
		} else {

			return new UserDetails() {
				@Override
				public String getUsername() {
					return user.getUserid();
				}

				@Override
				public String getPassword() {
					return user.getPassword();
				}

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
					auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
					return auths;
				}

				@Override
				public boolean isEnabled() {
					return true;
				}

				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}

				@Override
				public boolean isAccountNonLocked() {
					return true;
				}

				@Override
				public boolean isAccountNonExpired() {
					return true;
				}
			};
		}
	}
}