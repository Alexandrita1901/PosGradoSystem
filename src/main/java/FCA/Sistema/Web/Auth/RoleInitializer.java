package FCA.Sistema.Web.Auth;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import FCA.Sistema.Web.Entity.Role;
import FCA.Sistema.Web.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

	private final RoleRepository roleRepository;

	@PostConstruct
	public void init() {
		if (roleRepository.findByName("USER").isEmpty()) {
			Role userRole = Role.builder().name("USER").build();
			roleRepository.save(userRole);
		}
		if (roleRepository.findByName("ADMIN").isEmpty()) {
			Role adminRole = Role.builder().name("ADMIN").build();
			roleRepository.save(adminRole);
		}
		if (roleRepository.findByName("SUPERADMIN").isEmpty()) {
			Role superadminRole = Role.builder().name("SUPERADMIN").build();
			roleRepository.save(superadminRole);
		}
	}
}
