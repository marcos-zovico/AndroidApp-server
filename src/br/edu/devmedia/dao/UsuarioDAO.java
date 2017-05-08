package br.edu.devmedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.devmedia.config.DatabaseConfig;
import br.edu.devmedia.entity.Usuario;
import br.edu.devmedia.util.CriptoUtil;

public class UsuarioDAO {
	
	public boolean isLoggedIn(String user, String password) throws Exception{
		
		Connection connection = DatabaseConfig.getInstance().getConnection();
		
		String sql = "SELECT * FROM TB_USUARIO WHERE USUARIO = ? AND SENHA = ? ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user);
		statement.setString(2, CriptoUtil.criptoStringHexMD5(password));
		
		ResultSet resultSet = statement.executeQuery();
		
		
		return resultSet.next();
	}
	
	
	public boolean inserirToken(String login, String token) throws Exception {
		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "UPDATE TB_USUARIO SET TOKEN_GCM = ? WHERE USUARIO = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, token);
		statement.setString(2, login);

		return statement.execute();
	}
	
	
	public List<Usuario> listarUsuarios() throws Exception {
		List<Usuario> lista = new ArrayList<>();

		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "SELECT * FROM TB_USUARIO";

		PreparedStatement statement = conexao.prepareStatement(sql);

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setId(rs.getInt("ID_USUARIO"));
			usuario.setLogin(rs.getString("USUARIO"));
			usuario.setSenha(rs.getString("SENHA"));
			usuario.setToken(rs.getString("TOKEN_GCM"));

			lista.add(usuario);
		}

		return lista;
	}

	public Usuario buscarUsuarioPorId(String login) throws Exception {
		Usuario usuario = null;

		Connection conexao = DatabaseConfig.getInstance().getConnection();

		String sql = "SELECT * FROM TB_USUARIO WHERE USUARIO = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, login);

		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			usuario = new Usuario();
			usuario.setId(rs.getInt("ID_USUARIO"));
			usuario.setLogin(rs.getString("USUARIO"));
			usuario.setSenha(rs.getString("SENHA"));
			usuario.setToken(rs.getString("TOKEN_GCM"));
		}

		return usuario;
	}


}
