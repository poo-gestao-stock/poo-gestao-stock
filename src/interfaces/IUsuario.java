package interfaces;

import java.util.List;

import models.Usuario;

//Contrato da regra de negócio
public interface IUsuario {
    public boolean cadastrarUsuario(Usuario usuario);

    public List<Usuario> listarUsuarios();
}
