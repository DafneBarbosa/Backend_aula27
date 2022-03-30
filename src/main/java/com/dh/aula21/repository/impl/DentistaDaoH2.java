package com.dh.aula21.repository.impl;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.repository.config.ConfiguracaoJDBC;
import com.dh.aula21.model.Dentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DentistaDaoH2 implements IDao<Dentista> {

    private ConfiguracaoJDBC configuracaoJDBC;

    public DentistaDaoH2(){
        this.configuracaoJDBC = new ConfiguracaoJDBC();
    }

    @Override
    public Dentista salvar(Dentista dentista) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = String.format("INSERT INTO dentistas " +
                        "(nome ,email, numMatricula, atendeConvenio) " +
                        "VALUES ('%s','%s','%s','%s')",
                dentista.getNome(),
                dentista.getEmail(),
                dentista.getNumMatricula(),
                dentista.getAtendeConvenio());

        try {
            pstmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next())
                dentista.setId(keys.getInt(1));
            pstmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentista;
    }

    @Override
    public Optional<Dentista> getById(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        Dentista dentista = null;

        String query = String.format("SELECT id, nome, email, numMatricula, atendeConvenio " +
                        "FROM dentistas WHERE id = '%s'", id);

        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                dentista = criarObjetoDenstista(rs);
            }


            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dentista != null ? Optional.of(dentista) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;

        String query = String.format("DELETE FROM dentistas WHERE id = '%s'", id);

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
    public List<Dentista> buscarTodos() {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = "SELECT * FROM dentistas;";
        List<Dentista> dentistas = new ArrayList<>();

        try{
            pstmt = connection.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                dentistas.add(criarObjetoDenstista(result));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return dentistas;
    }

    @Override
    public Dentista atualizar(Dentista dentista) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        String query = String.format(
                "UPDATE dentistas SET nome = '%s', email = '%s', " +
                        "numMatricula = '%s', atendeConvenio = '%s' " +
                        "WHERE id = '%s'",
                dentista.getNome(),
                dentista.getEmail(),
                dentista.getNumMatricula(),
                dentista.getAtendeConvenio(),
                dentista.getId());
        execute(conexao, query);

        return dentista;
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

    private Dentista criarObjetoDenstista(ResultSet result) throws SQLException{
        return new Dentista(
                result.getInt("id"),
                result.getString("nome"),
                result.getString("email"),
                result.getInt("numMatricula"),
                result.getInt("atendeConvenio")
        );
    }



}
