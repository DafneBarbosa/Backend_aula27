package com.dh.aula21.repository.impl;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.repository.config.ConfiguracaoJDBC;
import com.dh.aula21.model.Endereco;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderecoDaoH2 implements IDao<Endereco> {

    private ConfiguracaoJDBC configuracaoJDBC;

    public EnderecoDaoH2(){
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    @Override
    public Endereco salvar(Endereco endereco) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = String.format("INSERT INTO enderecos " +
                        "(rua ,numero, cidade, bairro, estado) " +
                        "VALUES ('%s','%s','%s','%s', '%s')",
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getEstado());

        try {
            pstmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next())
                endereco.setId(keys.getInt(1));
            pstmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endereco;
    }

    @Override
    public Optional<Endereco> getById(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        Endereco endereco = null;

        String query = String.format("SELECT id, rua, numero, cidade, bairro, estado " +
                "FROM enderecos WHERE id = '%s'", id);

        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                endereco = criarObjetoEndereco(rs);
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endereco != null ? Optional.of(endereco) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;

        String query = String.format("DELETE FROM enderecos WHERE id = '%s'", id);

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
    public List<Endereco> buscarTodos() {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = "SELECT * FROM enderecos;";
        List<Endereco> enderecos = new ArrayList<>();

        try{
            pstmt = connection.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                enderecos.add(criarObjetoEndereco(result));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return enderecos;
    }

    @Override
    public Endereco atualizar(Endereco endereco) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        String query = String.format(
                "UPDATE enderecos SET rua = '%s', numero = '%s', " +
                        "cidade = '%s', bairro = '%s', estado = '%s' " +
                        "WHERE id = '%s'",
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getEstado(),
                endereco.getId());

        execute(conexao, query);

        return endereco;
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

    private Endereco criarObjetoEndereco(ResultSet result) throws SQLException{
        return new Endereco(
                result.getInt("id"),
                result.getString("rua"),
                result.getString("numero"),
                result.getString("cidade"),
                result.getString("bairro"),
                result.getString("estado")
        );
    }

}
