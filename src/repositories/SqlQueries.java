package repositories;

public class SqlQueries {

        final static String tabelaProduto = "[produto]";
        final static String tabelaCategoria = "[categoria]";
        final static String tabelaSubCategoria = "[sub_categoria]";

        final static String tabelaUsuario = "[usuario]";

        /// Queries em [produto]
        // listar
        final static String listarProdutos = "select " + tabelaProduto + ".[id], " + tabelaProduto + ".[nome], "
                        + tabelaProduto + ".[qtd_stock], " + tabelaProduto + ".[data_cadastro], "
                        + tabelaCategoria + ".[nome] as [nome_categoria], "
                        + tabelaSubCategoria + ".[nome] as [nome_sub_categoria] from "
                        + tabelaProduto +
                        " inner join " + tabelaCategoria + " on " + tabelaProduto + ".[categoria_id] = "
                        + tabelaCategoria + ".[id]" +
                        " inner join " + tabelaSubCategoria + " on " + tabelaProduto + ".[sub_categoria_id] = "
                        + tabelaSubCategoria + ".[id]";

        final static String listarProdutosPorCategoria = listarProdutos + " where " + tabelaCategoria
                        + ".[nome] like ?";
        final static String listarProdutosPorSubCategoria = listarProdutos + " where " + tabelaSubCategoria
                        + ".[nome] like ?";
        final static String listarProdutosPorNome = listarProdutos + " where " + tabelaProduto + ".[nome] like ?";
        final static String listarProdutosPorId = listarProdutos + " where " + tabelaProduto + ".[id] = ?";

        // inserir
        final static String inserirProduto = "insert into "
                        + tabelaProduto
                        + "(nome, qtd_stock, categoria_id, sub_categoria_id, data_cadastro) values(?, ?, ?, ?, ?)";
        // atualizar
        final static String atualizarProduto = "update "
                        + tabelaProduto
                        + " set nome = ?, qtd_stock = ?, categoria_id = ?, sub_categoria_id = ?, data_cadastro = ? where id = ?";
        // remover
        final static String removerProduto = "delete from "
                        + tabelaProduto
                        + " where id = ?";

        /// Queries em [categoria]
        // listar
        final static String listarCategorias = "select * from " + tabelaCategoria;
        final static String listarCategoriasPorNome = "select * from " + tabelaCategoria + " where [nome] like ?";
        // inserir
        final static String inserirCategoria = "insert into " + tabelaCategoria + "(nome) values(?); select @@identity";
        // atualizar
        final static String atualizarCategoria = "update " + tabelaCategoria + " set nome = ?, where id = ?";
        // remover
        final static String removerCategoria = "delete from " + tabelaCategoria + " where id = ?";

        /// Queries em [sub_categoria]
        // listar
        final static String listarSubCategorias = "select * from " + tabelaSubCategoria;
        final static String listarSubCategoriasPorNome = "select * from " + tabelaSubCategoria + " where [nome] like ?";
        final static String listarSubCategoriasPorIdCategoria = "select * from " + tabelaSubCategoria + " where [categoria_id] = ?";
        // inserir
        final static String inserirSubCategoria = "insert into " + tabelaSubCategoria + "(nome) values(?)";
        // atualizar
        final static String atualizarSubCategoria = "update " + tabelaSubCategoria
                        + " set nome = ?, categoria_id = ?  where id = ?";
        // remover
        final static String removerSubCategoria = "delete from " + tabelaSubCategoria + " where id = ?";

        // inserir
        final static String inserirUsuario = "insert into "
                        + tabelaUsuario
                        + "(nome_usuario, senha) values(?, ?)";
        // listar
        final static String listarUsuarios = "select * from " + tabelaUsuario;
}
