package com.codingdojo.ForumsApp.auth;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codingdojo.ForumsApp.repository.UserRepo;




//implements UserDetailService and UserDetails is a built-in class from springframework security
@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	 // 1 override method - USER LOGIN ATTEMPT (w/o POST mapping on controller)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepo.findByUserName(username);
        
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        //built-in parameter (.User)
        return new org.springframework.security.core.userdetails.User(user.getUserName() , user.getPassword(), getAuthorities(user));
    }
    
    // 2 GrantedAuthority AND SimpleGratedAuthority / getAuthority is a built-in from springframework security
    private List<GrantedAuthority> getAuthorities(UserModel user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(); //import both List and ArrayList
        for(UserRoleModel role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

}
