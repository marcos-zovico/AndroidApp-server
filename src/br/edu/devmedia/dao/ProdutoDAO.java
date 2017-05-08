package br.edu.devmedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.devmedia.config.DatabaseConfig;
import br.edu.devmedia.entity.Pagamento;
import br.edu.devmedia.entity.Produto;
import br.edu.devmedia.entity.Venda;

public class ProdutoDAO {


	public List<Produto> listarProdutos() throws Exception {
		List<Produto> lista = new ArrayList<>();

		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "SELECT * FROM TB_PRODUTO";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Produto produto = new Produto();
			produto.setId(rs.getInt("ID_PRODUTO"));
			produto.setTitulo(rs.getString("TITULO"));
			produto.setDescricao(rs.getString("DESCRICAO"));
			produto.setValor(rs.getBigDecimal("VALOR"));
			produto.setUrlImg(rs.getString("URL"));
			produto.setSku(rs.getString("SKU"));

			lista.add(produto);
		}

		return lista;
	}

	public void addVenda(Venda venda) throws Exception {
		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "INSERT INTO TB_VENDA(PRECO, ESTADO, QTDE, PRODUTOID, PAGAMENTOID) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDouble(1, venda.getPreco());
		statement.setString(2, venda.getEstado());
		statement.setInt(3, venda.getQtde());
		statement.setInt(4, venda.getProduto().getId());
		statement.setInt(5, venda.getPagamento().getId());
		statement.execute();
	}

	public int addPagamento(Pagamento pagamento) throws Exception {
		int chavePrimaria = 0;
		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "INSERT INTO TB_PAGAMENTO(PAGTOPAYPALID, VALOR, MOEDA, IDUSUARIO, ESTADO) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, pagamento.getPagtoPayPalId());
		statement.setDouble(2, pagamento.getValor());
		statement.setString(3, pagamento.getMoeda());
		statement.setInt(4, pagamento.getUsuario().getId());
		statement.setString(5, pagamento.getEstado());
		statement.execute();

		ResultSet rs = statement.getGeneratedKeys();

		if (rs.next()) {
			chavePrimaria = rs.getInt(1);
		}

		return chavePrimaria;
	}

}
