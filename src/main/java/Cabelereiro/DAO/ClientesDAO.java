/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cabelereiro.DAO;

import Cabelereiro.DTO.ClientesDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClientesDAO {
    private Connection connection;

    public ClientesDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrar(ClientesDTO cliente) throws SQLException {
        String sql = "INSERT INTO clientes (cpf, nome, horaAtendimento, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getHorarioDeAtendimento());
            stmt.setString(4, cliente.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void atualizar(ClientesDTO cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome=?, horaAtendimento=?, telefone=? WHERE cpf=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getHorarioDeAtendimento());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(6, cliente.getCpf()); // Usando cpf como chave primária
            stmt.executeUpdate();
        }
    }

public boolean excluir(String cpf) throws SQLException {
    String sql = "DELETE FROM clientes WHERE cpf=?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, cpf);
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0; // Retorna true se pelo menos uma linha foi excluída
    }
}

    public List<ClientesDTO> listarTodos() {
        List<ClientesDTO> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new ClientesDTO(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("horaAtendimento"),
                    rs.getString("telefone")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }
}