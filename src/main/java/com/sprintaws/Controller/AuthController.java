package com.sprintaws.Controller;
	import java.util.HashSet;
	import java.util.Set;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import com.sprintaws.Dao.RoleRepository;
	import com.sprintaws.Dao.UserRepository;
	import com.sprintaws.Entity.Role;
	import com.sprintaws.Entity.Roles;
	import com.sprintaws.Entity.UserEntity;
    import com.sprintaws.model.signupRequest;

	@RestController
	@RequestMapping("/auth")
	public class AuthController {
		@Autowired
		UserRepository userRepository;
		@Autowired
		RoleRepository roleRepository;
		@Autowired
		PasswordEncoder encoder;
		
		@PostMapping("/signup")
		public ResponseEntity<?> userSignup(@RequestBody signupRequest signupRequest) {
			if(userRepository.existsByUserName(signupRequest.getUserName())){
		      return ResponseEntity.badRequest().body("Customername is already taken");
		    }
		   
		    UserEntity user = new UserEntity();
		    Set<Role> roles = new HashSet<>();
		    user.setUserName(signupRequest.getUserName());
		    user.setPassword(encoder.encode(signupRequest.getPassword()));
		    System.out.println("Encoded password--- " + user.getPassword());
		    String[] roleArr = signupRequest.getRoles();
		    
		    if(roleArr == null) {
		      roles.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
		    }
		    for(String role: roleArr) {
		      switch(role) {
		        case "admin":
		          roles.add(roleRepository.findByRoleName(Roles.ROLE_ADMIN).get());
		          break;
		        case "user":
		          roles.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
		          break;  
		        default:
		          return ResponseEntity.badRequest().body("Specified role not found");
		      }
		    }
		    user.setRoles(roles);
		    userRepository.save(user);
		    return ResponseEntity.ok("Customer signed up successfully");
		}
	}


