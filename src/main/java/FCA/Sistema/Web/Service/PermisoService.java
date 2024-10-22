package FCA.Sistema.Web.Service;

import org.springframework.stereotype.Service;

import FCA.Sistema.Web.Entity.Permiso;
import FCA.Sistema.Web.Entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermisoService {

	public boolean tienePermisoCrear(User usuarioLogueado) {
		return usuarioLogueado.getPermisos().stream().anyMatch(Permiso::isPermisoCrear);
	}

	public boolean tienePermisoListar(User usuarioLogueado) {
		return usuarioLogueado.getPermisos().stream().anyMatch(Permiso::isPermisoListar);
	}

	public boolean tienePermisoEditar(User usuarioLogueado) {
		return usuarioLogueado.getPermisos().stream().anyMatch(Permiso::isPermisoEditar);
	}

	public boolean tienePermisoEliminar(User usuarioLogueado) {
		return usuarioLogueado.getPermisos().stream().anyMatch(Permiso::isPermisoEliminar);
	}

}
