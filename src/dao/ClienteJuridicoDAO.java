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
    public ICliente getClienteJuridicoById(int idcliente)
    {
        String consultar = "SELECT * FROM cliente c INNER JOIN endereco e ON c.idendereco = e.idendereco INNER JOIN clientejuridico cj on c.idcliente = cj.idcliente WHERE c.idcliente = ?";
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setInt(1, idcliente);
            ResultSet rs = stmte.executeQuery();
            rs.first();
            
            Cliente c = new Cliente();
            Endereco end = new Endereco();
            ClienteJuridico cj = new ClienteJuridico();
            c.setIdcliente(rs.getInt("idcliente"));
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setEmail(rs.getString("email"));
            c.setTipocliente(rs.getString("tipocliente"));
            c.setStatus(rs.getString("status"));
            
            end.setIdEndereco(rs.getInt("idendereco"));
            end.setRua(rs.getString("rua"));
            end.setNum(rs.getInt("num"));
            end.setBairro(rs.getString("bairro"));
            end.setReferencia(rs.getString("referencia"));
            end.setComplemento(rs.getString("complemento"));
            end.setCidade(rs.getString("cidade"));
            end.setEstado(rs.getString("estado"));
            
            c.setEndereco(end);
            cj.setCliente(c);
            cj.setCnpj(rs.getString("cnpj"));
            
            return cj;
            
        }
        catch(Exception e)
        {
            this.erro = "Erro ao buscar Cliente " + e.getMessage();
            return null;
        }
    }
}
