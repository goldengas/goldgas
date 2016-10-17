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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author IFSP
 */
public class ClienteDAO {
    private Connection con;
    private String erro;
    private int lastId = -1;
    
    public ClienteDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public int getLastId(){
        return this.lastId;
    }
    public boolean inserirCliente(Cliente c)
    {
        EnderecoDAO enderecoDAO = new EnderecoDAO(); 
        if(enderecoDAO.inserirEndereco(c.getEndereco()) == false){
            this.erro = "Erro ao salvar endereço: " + enderecoDAO.getErro();
            return false;
        }
        
        c.getEndereco().setIdEndereco(enderecoDAO.getLastId());
        
        String inserir = "INSERT INTO cliente(nome, telefone, tipocliente, email, idendereco, status) VALUES(?, ?, ?, ?, ?, ?)";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            stmte.setString(1, c.getNome());
            stmte.setString(2, c.getTelefone());
            stmte.setString(3, c.getTipocliente());
            stmte.setString(4, c.getEmail());
            stmte.setInt(5, c.getEndereco().getIdEndereco());
            stmte.setString(6, c.getStatus());
            stmte.executeUpdate();
            ResultSet rs = stmte.getGeneratedKeys();
            if(rs.next()){
                this.lastId = rs.getInt(1);
            }
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
                c.setStatus(rs.getString("status"));
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
            stmte.setString(5, c.getStatus());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao atualizar: " +e.getMessage();
            return false;
        }
    }
    
     public List<Cliente> getTodosClientes()
    {
        String consultar = "SELECT * FROM cliente";
        
        try{
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            ResultSet rs = stmte.executeQuery();
            List<Cliente> listaClientes = new ArrayList();
            
            while(rs.next()){
                Cliente c = new Cliente();
                
                
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setTipocliente(rs.getString("tipocliente"));
                c.setEmail(rs.getString("email"));
                c.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                c.setStatus(rs.getString("status"));
                listaClientes.add(c);
            }
            return listaClientes;
        }
        catch(Exception e){
            this.erro = "Erro ao buscar os carros";
            return null;
        }
    }
    
    public List<Cliente> getClienteByFiltro(String valor)
    {
        String consulta = "SELECT * FROM cliente WHERE LIKE ?";
        
        try{
            PreparedStatement stmte = this.con.prepareStatement(consulta);
            stmte.setString(1, "%" + valor + "%");
            ResultSet rs = stmte.executeQuery();
            List<Cliente> listaClientes = new ArrayList();
            
            while(rs.next()){
                Cliente c = new Cliente();
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setTipocliente(rs.getString("tipocliente"));
                c.setEmail(rs.getString("email"));
                c.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                c.setStatus(rs.getString("status"));
                listaClientes.add(c);
            }
            return listaClientes;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            this.erro = "Erro ao buscar os carros";
            return null;
        }
    }
    
    
    
}
