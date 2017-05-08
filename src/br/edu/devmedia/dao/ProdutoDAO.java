package br.edu.devmedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.devmedia.config.DatabaseConfig;
import br.edu.devmedia.entity.Produto;

public class ProdutoDAO {

	public List<Produto> listarProdutos() throws Exception {
		List<Produto> lista = new ArrayList<>();

		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "SELECT * FROM tb_produto";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Produto produto = new Produto();
			produto.setId(rs.getInt("ID_PRODUTO"));
			produto.setTitulo(rs.getString("TITULO"));
			produto.setDescricao(rs.getString("DESCRICAO"));
			produto.setValor(rs.getBigDecimal("VALOR"));
			produto.setUrlImg(rs.getString("URL"));

			lista.add(produto);
		}

		return lista;
	}

}
