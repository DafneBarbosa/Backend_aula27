package com.dh.aula21.repository.impl;

import com.dh.aula21.model.*;
import com.dh.aula21.repository.IDao;
import com.dh.aula21.repository.config.ConfiguracaoJDBC;
import com.dh.aula21.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaDaoH2 implements IDao<Consulta> {

    private ConfiguracaoJDBC configuracaoJDBC;
    private PacienteDaoH2 pacienteDaoH2;
    private DentistaDaoH2 dentistaDaoH2;
    private UsuarioDaoH2 usuarioDaoH2;

    public ConsultaDaoH2(){
        this.configuracaoJDBC = new ConfiguracaoJDBC();
        this.pacienteDaoH2 = new PacienteDaoH2();
        this.dentistaDaoH2 = new DentistaDaoH2();
        this.usuarioDaoH2 = new UsuarioDaoH2();
    }

    @Override
    public Consulta salvar(Consulta consulta) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        if(consulta.getPaciente().getId()==null){
            consulta.setPaciente(pacienteDaoH2.salvar(consulta.getPaciente()));
        }
        if(consulta.getDentista().getId()==null){
            consulta.setDentista(dentistaDaoH2.salvar(consulta.getDentista()));
        }
        if(consulta.getUsuario().getId()==null){
            consulta.setUsuario(usuarioDaoH2.salvar(consulta.getUsuario()));
        }

        String query = String.format("INSERT INTO consultas " +
                        "(id_paciente ,id_dentista, id_usuario, dataCad, dataAtend) " +
                        "VALUES ('%s','%s','%s','%s', '%s')",
                consulta.getPaciente().getId(),
                consulta.getDentista().getId(),
                consulta.getUsuario().getId(),
                Util.dateToTimestamp(consulta.getDataCad()),
                Util.dateToTimestamp(consulta.getDataAtend()));

        try {
            pstmt = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next())
                consulta.setId(keys.getInt(1));
            pstmt.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consulta;
    }

    @Override
    public Optional<Consulta> getById(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;
        Consulta consulta = null;

        String query = String.format("SELECT id, id_paciente ,id_dentista, id_usuario, dataCad, dataAtend " +
                "FROM consultas WHERE id = '%s'", id);

        try{
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                consulta = criarObjetoConsulta(rs);
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consulta != null ? Optional.of(consulta) : Optional.empty();
    }

    @Override
    public void excluir(Integer id) {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        Statement stmt = null;

        String query = String.format("DELETE FROM consultas WHERE id = '%s'", id);

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
    public List<Consulta> buscarTodos() {
        Connection connection = configuracaoJDBC.conectarComBancoDeDados();
        PreparedStatement pstmt = null;

        String query = "SELECT * FROM consultas;";
        List<Consulta> consultas = new ArrayList<>();

        try{
            pstmt = connection.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){
                consultas.add(criarObjetoConsulta(result));
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return consultas;
    }

    @Override
    public Consulta atualizar(Consulta consulta) {
        Connection conexao = configuracaoJDBC.conectarComBancoDeDados();

        if(consulta.getPaciente() != null && consulta.getId() != null){
            pacienteDaoH2.atualizar(consulta.getPaciente());
        }
        if(consulta.getDentista() != null && consulta.getId() != null){
            dentistaDaoH2.atualizar(consulta.getDentista());
        }
        if(consulta.getUsuario() != null && consulta.getId() != null){
            usuarioDaoH2.atualizar(consulta.getUsuario());
        }

        String query = String.format(
                "UPDATE consultas SET id_paciente = '%s', id_dentista = '%s', " +
                        "id_usuario = '%s', dataCad = '%s', dataAtend = '%s' " +
                        "WHERE id = '%s'",
                consulta.getPaciente().getId(),
                consulta.getDentista().getId(),
                consulta.getUsuario().getId(),
                Util.dateToTimestamp(consulta.getDataCad()),
                Util.dateToTimestamp(consulta.getDataAtend()),
                consulta.getId());
        execute(conexao, query);
        return consulta;
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

    private Consulta criarObjetoConsulta(ResultSet result) throws SQLException{

        Paciente paciente = pacienteDaoH2.getById(result.getInt("id_paciente")).orElse(null);
        Dentista dentista = dentistaDaoH2.getById(result.getInt("id_dentista")).orElse(null);
        Usuario usuario = usuarioDaoH2.getById(result.getInt("id_usuario")).orElse(null);

        return new Consulta(
                result.getInt("id"),
                paciente,
                dentista,
                usuario,
                result.getDate("dataCad"),
                result.getDate("dataAtend")
        );
    }
}
