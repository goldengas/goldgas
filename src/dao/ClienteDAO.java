/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Cliente;
import beans.ClienteFisico;
import beans.ClienteJuridico;
import beans.Endereco;
import goldgasagua.Conexao;
import interfaces.ICliente;
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
    public List<ICliente> getCliente(String nome)
    {
        String consultar = "SELECT * FROM clientefisico cf INNER JOIN cliente c ON cf.idcliente = c.idcliente\n" +
        "UNION \n" +
        "SELECT * FROM clientejuridico cj INNER JOIN cliente c ON cj.idcliente = c.idcliente";
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            //stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<ICliente> listaClientes = new ArrayList();
            
            while(rs.next())
            {
                Cliente c = new Cliente();
                
                
                c.setIdcliente(rs.getInt("idcliente"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setTipocliente(rs.getString("tipocliente"));
                c.setEmail(rs.getString("email"));
                c.setStatus(rs.getString("status"));
                if(c.getTipocliente().equals("fisico"))
                {
                    ClienteFisico cf = new ClienteFisico();
                    cf.setCpf(rs.getString("cpf"));
                    cf.setCliente(c);
                    listaClientes.add(cf);
                }
                else
                {
                    ClienteJuridico cj = new ClienteJuridico();
                     cj.setCnpj(rs.getString("cpf"));
                    cj.setCliente(c);
                    listaClientes.add(cj);
                }
            }
            return listaClientes;
        }
        catch(Exception e)
        {
         
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public boolean atualizaCliente(ICliente c)
    {
        EnderecoDAO enderecoDAO = new EnderecoDAO(); 
        if(enderecoDAO.atualizaEndereco(c.getCliente().getEndereco()) == false){
            this.erro = "Erro ao atualizar endereço: " + enderecoDAO.getErro();
            return false;
        }
        String update = "UPDATE cliente SET nome=?,telefone=?, email=?, status=? WHERE idcliente = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, c.getCliente().getNome());
            stmte.setString(2, c.getCliente().getTelefone());
            stmte.setString(3, c.getCliente().getEmail());
            stmte.setString(4, c.getCliente().getStatus());
            stmte.setInt(5, c.getCliente().getIdcliente());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao atualizar: " +e.getMessage();
            return false;
        }
    }
    public Cliente getClienteById(int idcliente)
    {
        String consultar = "SELECT * FROM cliente c INNER JOIN endereco e ON c.idendereco = e.idendereco WHERE c.idcliente = ?";
        
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setInt(1, idcliente);
            ResultSet rs = stmte.executeQuery();
            rs.first();
            
            Cliente c = new Cliente();
            Endereco end = new Endereco();
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setTipocliente(rs.getString("tipocliente"));
            c.setEmail(rs.getString("email"));
            c.setStatus(rs.getString("status"));
                
            end.setRua(rs.getString("rua"));
            end.setNum(rs.getInt("num"));
            end.setBairro(rs.getString("bairro"));
            end.setReferencia(rs.getString("referencia"));
            end.setComplemento(rs.getString("complemento"));
            end.setCidade(rs.getString("cidade"));
            end.setEstado(rs.getString("estado"));
            c.setEndereco(end);
            
            
            return c;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }

}
