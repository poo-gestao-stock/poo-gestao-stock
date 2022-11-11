package interfaces;

import java.util.List;

import models.Usuario;

public interface IUsuarioRepository {
    public boolean inserirUsuario(Usuario usuario);

    public List<Usuario> listarUsuarios();
}
