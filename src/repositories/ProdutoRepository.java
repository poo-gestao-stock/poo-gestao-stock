package repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import interfaces.IProdutoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Categoria;
import models.Produto;
import models.SubCategoria;

public class ProdutoRepository implements IProdutoRepository {

    // Conexão
    private static final Connection conexao = ConnectionFactory.getConnection();

    public int inserirCategoria(Categoria categoria) {
        int id;

        try {
            PreparedStatement stmt = conexao.prepareStatement(
                    SqlQueries.inserirCategoria,
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, categoria.getNome());
            var affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar Categoria. Nenhuma linha afectada");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar a categoria. Nenhum ID informado");
                }
            }

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw e;
        }
    }

    public int inserirSubCategoria(SubCategoria subCategoria) {
        int id;
        try {
            PreparedStatement stmt = conexao.prepareStatement(
                    SqlQueries.inserirSubCategoria,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, subCategoria.getNome());
            stmt.setInt(1, subCategoria.getCategoriaId());
            var affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar SubCategoria. Nenhuma linha afectada");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar a sub-categoria. Nenhum ID informado");
                }
            }
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizarCategoria(Categoria categoria) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.atualizarCategoria);

            stmt.setString(1, categoria.getNome());
            stmt.setInt(1, categoria.getId());
            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizarSubCategoria(SubCategoria subCategoria) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.atualizarSubCategoria);

            stmt.setString(1, subCategoria.getNome());
            stmt.setInt(2, subCategoria.getCategoriaId());
            stmt.setInt(3, subCategoria.getId());
            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Categoria> listarCategorias() {
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarCategorias);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var categoria = new Categoria();

                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));

                categorias.add(categoria);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return categorias;
    }

    @Override
    public Categoria listarCategoriasPorNome(String kNome) {
        var categoria = new Categoria();

        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarCategoriasPorNome);
            stmt.setString(1, kNome);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return categoria;
    }

    @Override
    public ObservableList<SubCategoria> listarSubCategorias() {
        ObservableList<SubCategoria> subCategorias = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarSubCategorias);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var subCategoria = new SubCategoria();

                subCategoria.setId(rs.getInt("id"));
                subCategoria.setNome(rs.getString("nome"));

                subCategorias.add(subCategoria);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return subCategorias;
    }

    @Override
    public SubCategoria listarSubCategoriasPorNome(String kNome) {
        var subCategoria = new SubCategoria();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarSubCategorias);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                subCategoria.setId(rs.getInt("id"));
                subCategoria.setNome(rs.getString("nome"));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return subCategoria;
    }

    public ObservableList<SubCategoria> listarSubCategoriasPorCatId(int cateId) {
        ObservableList<SubCategoria> subCats = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarSubCategoriasPorIdCategoria);
            stmt.setInt(1, cateId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var subCategoria = new SubCategoria();

                subCategoria.setId(rs.getInt("id"));
                subCategoria.setNome(rs.getString("nome"));

                subCats.add(subCategoria);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return subCats;
    }

    @Override
    public boolean inserirProduto(Produto produto) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.inserirProduto);

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQtdStockItem());
            stmt.setInt(3, produto.getCategoria().getId());
            stmt.setInt(4, produto.getSubCategoria().getId());
            stmt.setDate(5, new Date(System.currentTimeMillis()));

            stmt.execute();

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean atualizarProduto(Produto produto) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.atualizarProduto);

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQtdStockItem());
            stmt.setInt(3, produto.getCategoria().getId());
            stmt.setInt(4, produto.getSubCategoria().getId());
            stmt.setDate(5, Date.valueOf(produto.getCadastradoEm()));
            stmt.setInt(6, produto.getId());

            stmt.execute();

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Produto> listarProdutos() {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarProdutos);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var produto = _setProduto(rs);

                produtos.add(produto);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return produtos;
    }

    @Override
    public boolean removerProduto(int id) {
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.removerProduto);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Produto> listarProdutosPorCategoria(String kCategoria) {
        return _obterProdutos(SqlQueries.listarProdutosPorCategoria, kCategoria);
    }

    @Override
    public ObservableList<Produto> listarProdutosPorSubCategoria(String kSubCategoria) {
        return _obterProdutos(SqlQueries.listarProdutosPorSubCategoria, kSubCategoria);
    }

    @Override
    public Produto listarProdutoPorId(int id) {
        var produto = new Produto();
        try {
            PreparedStatement stmt = conexao.prepareStatement(SqlQueries.listarProdutosPorId);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var categoria = new Categoria();
                var subCategoria = new SubCategoria();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setQtdStockItem(rs.getInt("qtd_stock"));
                produto.setCadastradoEm(rs.getString("data_cadastro"));

                categoria.setNome(rs.getString("nome_categoria"));
                subCategoria.setNome(rs.getString("nome_sub_categoria"));

                produto.setCategoria(categoria);
                produto.setSubCategoria(subCategoria);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return produto;
    }

    @Override
    public ObservableList<Produto> listarProdutosPorNome(String kNome) {
        return _obterProdutos(SqlQueries.listarProdutosPorNome, kNome);
    }

    private ObservableList<Produto> _obterProdutos(String query, String key) {
        ObservableList<Produto> produtos = FXCollections.observableArrayList();

        try {
            PreparedStatement stmt = conexao.prepareStatement(query);
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                var produto = _setProduto(rs);

                produtos.add(produto);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return produtos;
    }

    Produto _setProduto(ResultSet rs) throws SQLException {
        var produto = new Produto();
        var categoria = new Categoria();
        var subCategoria = new SubCategoria();

        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setQtdStockItem(rs.getInt("qtd_stock"));
        produto.setCadastradoEm(rs.getString("data_cadastro"));

        categoria.setNome(rs.getString("nome_categoria"));
        subCategoria.setNome(rs.getString("nome_sub_categoria"));

        produto.setCategoria(categoria);
        produto.setSubCategoria(subCategoria);

        return produto;
    }
}
