/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Cliente;
import goldgasagua.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IFSP
 */
public class ClienteDAO {
    private Connection con;
    private String erro;
    
    public ClienteDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirCliente(Cliente c)
    {
        String inserir = "INSERT INTO cliente(idcliente, nome, telefone, tipocliente, email, idendereco, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, c.getIdcliente());
            stmte.setString(2, c.getNome());
            stmte.setString(3, c.getTelefone());
            stmte.setString(4, c.getTipocliente());
            stmte.setString(5, c.getEmail());
            stmte.setInt(6, c.getEndereco().getIdEndereco());
            stmte.setInt(7, c.getStatus());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public List<Cliente> getCliente(String nome)
    {
        String consultar = "SELECT * FROM cliente WHERE nome LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Cliente> listaClientes = new ArrayList();
            
            while(rs.next())
            {
                Cliente c = new Cliente();
                c.setIdcliente(rs.getInt("idcliente"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setTipocliente(rs.getString("tipocliente"));
                c.setEmail(rs.getString("email"));
                c.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                c.setStatus(rs.getInt("status"));
                listaClientes.add(c);
            }
            return listaClientes;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public boolean atualizaCliente(Cliente c)
    {
        String update = "UPDATE cliente SET nome=?,telefone=?, tipocliente=?, email=?, status=? WHERE idcliente = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, c.getNome());
            stmte.setString(2, c.getTelefone());
            stmte.setString(3, c.getTipocliente());
            stmte.setString(4, c.getEmail());
            stmte.setInt(5, c.getStatus());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao atualizar: " +e.getMessage();
            return false;
        }
    }
}
