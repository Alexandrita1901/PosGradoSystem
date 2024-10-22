package FCA.Sistema.Web.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

	Optional<User> findByRefreshToken(String refreshToken);

	@Modifying
	@Query("update User u set u.nombres = :nombres, u.apellidos = :apellidos where u.id = :id")
	void updateUser(@Param("id") Integer id, @Param("nombres") String nombres, @Param("apellidos") String apellidos);

	List<User> findByRoleName(String roleName);

	@Query("SELECT u FROM User u WHERE u.unidadPosgrado.id = :unidadId AND u.role.name = :roleName")
	List<User> findByUnidadPosgradoIdAndRoleName(@Param("unidadId") Integer unidadId,
			@Param("roleName") String roleName);

	List<User> findByUnidadPosgrado(UnidadPosgrado unidadPosgrado);

	Optional<User> findByIdAndRoleName(Integer adminId, String string);

}
