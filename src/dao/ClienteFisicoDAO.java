/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import beans.Cliente;
import beans.ClienteFisico;
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
public class ClienteFisicoDAO {
    private Connection con;
    private String erro;
    
    public ClienteFisicoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirClienteFisico(ClienteFisico c)
    {
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.inserirCliente(c.getCliente()) == false){
            this.erro = "Erro ao inserir cliente: " + clienteDAO.getErro();
            return false;
        }
        c.setIdcliente(clienteDAO.getLastId());
        
        String inserir = "INSERT INTO clientefisico(idcliente, cpf) VALUES(?, ?)";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, c.getIdcliente());
            stmte.setString(2, c.getCpf());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public List<ClienteFisico> getClienteFisico(int idcliente)
    {
        String consultar = "SELECT * FROM clientefisico WHERE idcliente LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+idcliente+"%");
            ResultSet rs = stmte.executeQuery();
            List<ClienteFisico> listaClientes = new ArrayList();
            
            while(rs.next())
            {
                ClienteFisico c = new ClienteFisico();
                c.setIdcliente(rs.getInt("idcliente"));
                c.setCpf(rs.getString("cpf"));
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
    public ICliente getClienteFisicoById(int idcliente)
    {
        String consultar = "SELECT * FROM cliente c INNER JOIN endereco e ON c.idendereco = e.idendereco INNER JOIN clientefisico cf on c.idcliente = cf.idcliente WHERE c.idcliente = ?";
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setInt(1, idcliente);
            ResultSet rs = stmte.executeQuery();
            rs.first();
            
            Cliente c = new Cliente();
            Endereco end = new Endereco();
            ClienteFisico cf = new ClienteFisico();
            c.setIdcliente(rs.getInt("idcliente"));
            c.setNome(rs.getString("nome"));
            c.setTipocliente(rs.getString("tipocliente"));
            c.setStatus(rs.getString("status"));
            
            c.setEmail(rs.getString("email"));
            c.setTelefone(rs.getString("telefone"));
            end.setIdEndereco(rs.getInt("idendereco"));
            end.setRua(rs.getString("rua"));
            end.setNum(rs.getInt("num"));
            end.setBairro(rs.getString("bairro"));
            end.setReferencia(rs.getString("referencia"));
            end.setComplemento(rs.getString("complemento"));
            end.setCidade(rs.getString("cidade"));
            end.setEstado(rs.getString("estado"));
            c.setEndereco(end);
            cf.setCliente(c);
            cf.setCpf(rs.getString("cpf"));
            return cf;
            
        }
        catch(Exception e)
        {
            this.erro = "Erro ao buscar Cliente " + e.getMessage();
            return null;
        }
    }
}
