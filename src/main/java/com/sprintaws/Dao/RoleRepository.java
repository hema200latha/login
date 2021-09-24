package com.sprintaws.Dao;


	import java.util.Optional;
	import org.springframework.data.jpa.repository.JpaRepository;
	import com.sprintaws.Entity.Role;
	import com.sprintaws.Entity.Roles;

	public interface RoleRepository extends JpaRepository<Role, Integer>{
		Optional<Role> findByRoleName(Roles role);
	}


