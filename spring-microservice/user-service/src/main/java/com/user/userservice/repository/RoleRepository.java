package com.user.userservice.repository;


import com.user.userservice.enums.RoleName;
import com.user.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByRole(RoleName roleName);
}
