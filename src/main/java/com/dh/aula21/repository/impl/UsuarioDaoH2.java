package com.dh.aula21.repository.impl;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.repository.config.ConfiguracaoJDBC;
import com.dh.aula21.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDaoH2 implements IDao<Usuario> {

    private ConfiguracaoJDBC configuracaoJDBC;

    public UsuarioDaoH2(){
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = String.format("INSERT INTO usuarios " +
                        "(nome, email, senha, acesso) " +
                        "VALUES ('%s','%s','%s','%s')",
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getAcesso());

        try {
            pstmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next())
                usuario.setId(keys.getInt(1));
            pstmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Optional<Usuario> getById(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        Usuario usuario = null;

        String query = String.format("SELECT *" +
                "FROM usuarios WHERE id = '%s' ", id);

        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                usuario = criarObjetoUsuario(rs);
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario != null ? Optional.of(usuario) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;

        String query = String.format("DELETE FROM usuarios WHERE id = '%s'", id);

        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Usuario> buscarTodos() {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = "SELECT * FROM usuarios;";
        List<Usuario> usuarios = new ArrayList<>();

        try{
            pstmt = connection.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                usuarios.add(criarObjetoUsuario(result));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        String query = String.format(
                "UPDATE usuarios SET nome = '%s', email = '%s', " +
                        "senha = '%s', acesso = '%s'" +
                        "WHERE id = '%s'",
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getAcesso(),
                usuario.getId());

        execute(conexao, query);

        return usuario;
    }

    private void execute(Connection connection, String query){
        try {
            PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
            connection.close();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Usuario criarObjetoUsuario(ResultSet result) throws SQLException{
        return new Usuario(
                result.getInt("id"),
                result.getString("nome"),
                result.getString("email"),
                result.getString("senha"),
                result.getInt("acesso")
        );
    }

}
