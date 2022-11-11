package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import interfaces.IUsuarioRepository;
import models.Usuario;

public class UsuarioRepository implements IUsuarioRepository {

    // Conexão
    private static final Connection conexao = ConnectionFactory.getConnection();

    @Override
    public boolean inserirUsuario(Usuario usuario) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.inserirUsuario);

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenha());

            stmt.execute();

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        var usuarios = new ArrayList<Usuario>();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarUsuarios);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNomeUsuario(rs.getString("nome_usuario"));
                usuario.setSenha(rs.getString("senha"));

                usuarios.add(usuario);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }
}
