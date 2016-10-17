/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.ClienteJuridico;
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
public class ClienteJuridicoDAO {
    private Connection con;
    private String erro;
    
    public ClienteJuridicoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirClienteJuridico(ClienteJuridico c)
    {
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.inserirCliente(c.getCliente()) == false){
            this.erro = "Erro ao inserir cliente: " + clienteDAO.getErro();
            return false;
        }
        c.setIdcliente(clienteDAO.getLastId());
        
        String inserir = "INSERT INTO clientejuridico(idcliente, cnpj) VALUES(?, ?)";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, c.getIdcliente());
            stmte.setString(2, c.getCnpj());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public List<ClienteJuridico> getClienteJuridico(String idcliente)
    {
        String consultar = "SELECT * FROM clientejuridico WHERE idcliente LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+idcliente+"%");
            ResultSet rs = stmte.executeQuery();
            List<ClienteJuridico> listaClientes = new ArrayList();
            
            while(rs.next())
            {
                ClienteJuridico c = new ClienteJuridico();
                c.setIdcliente(rs.getInt("idcliente"));
                c.setCnpj(rs.getString("cnpj"));
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
}
