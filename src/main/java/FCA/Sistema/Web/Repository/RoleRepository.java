package FCA.Sistema.Web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);
}
