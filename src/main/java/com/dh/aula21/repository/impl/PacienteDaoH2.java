package com.dh.aula21.repository.impl;

import com.dh.aula21.repository.IDao;
import com.dh.aula21.repository.config.ConfiguracaoJDBC;
import com.dh.aula21.model.Endereco;
import com.dh.aula21.model.Paciente;
import com.dh.aula21.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteDaoH2 implements IDao<Paciente> {

    private ConfiguracaoJDBC configuracaoJDBC;
    private EnderecoDaoH2 enderecoDaoH2;

    public PacienteDaoH2(){
        this.configuracaoJDBC = new ConfiguracaoJDBC();
        this.enderecoDaoH2 = new EnderecoDaoH2();
    }

    @Override
    public Paciente salvar(Paciente paciente) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;
        paciente.setEndereco(enderecoDaoH2.salvar(paciente.getEndereco()));

        String query = String.format("INSERT INTO pacientes " +
                        "(nome ,sobrenome, cpf, dataCad, id_endereco) " +
                        "VALUES ('%s','%s','%s','%s', '%s')",
                paciente.getNome(),
                paciente.getSobrenome(),
                paciente.getCpf(),
                Util.dateToTimestamp(paciente.getDataCad()),
                paciente.getEndereco().getId());

        try {
            pstmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next())
                paciente.setId(keys.getInt(1));
            pstmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    @Override
    public Optional<Paciente> getById(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        Paciente paciente = null;

        String query = String.format("SELECT id, nome, sobrenome, cpf, dataCad, id_endereco " +
                "FROM pacientes WHERE id = '%s'", id);

        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                paciente = criarObjetoPaciente(rs);
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente != null ? Optional.of(paciente) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;

        String query = String.format("DELETE FROM pacientes WHERE id = '%s'", id);

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
    public List<Paciente> buscarTodos() {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = "SELECT * FROM pacientes;";
        List<Paciente> pacientes = new ArrayList<>();

        try{
            pstmt = connection.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                pacientes.add(criarObjetoPaciente(result));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return pacientes;
    }

    @Override
    public Paciente atualizar(Paciente paciente) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();

        if(paciente.getEndereco() != null && paciente.getId() != null){
            enderecoDaoH2.atualizar(paciente.getEndereco());
        }

        String query = String.format(
                "UPDATE pacientes SET nome = '%s', sobrenome = '%s', " +
                        "cpf = '%s', dataCad = '%s', id_endereco = '%s' " +
                        "WHERE id = '%s'",
                paciente.getNome(),
                paciente.getSobrenome(),
                paciente.getCpf(),
                Util.dateToTimestamp(paciente.getDataCad()),
                paciente.getEndereco().getId(),
                paciente.getId());
        execute(conexao, query);
        return paciente;
    }

    private void execute(Connection connection, String query){
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.executeUpdate();
            connection.close();
            pstmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Paciente criarObjetoPaciente(ResultSet result) throws SQLException{

        Endereco endereco = enderecoDaoH2.getById(result.getInt("id_endereco")).orElse(null);

        return new Paciente(
                result.getInt("id"),
                result.getString("nome"),
                result.getString("sobrenome"),
                result.getString("cpf"),
                result.getDate("dataCad"),
                endereco
        );
    }



}

