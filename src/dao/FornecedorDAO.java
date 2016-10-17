/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import beans.Fornecedor;
import goldgasagua.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class FornecedorDAO {
    private Connection con;
    private String erro;
    private int lastId = -1;
    
    public FornecedorDAO()
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
    public boolean inserirFornecedor(Fornecedor f)
    {
        EnderecoDAO enderecoDAO = new EnderecoDAO(); 
        if(enderecoDAO.inserirEndereco(f.getEndereco()) == false){
            this.erro = "Erro ao salvar endereço: " + enderecoDAO.getErro();
            return false;
        }
        
        f.getEndereco().setIdEndereco(enderecoDAO.getLastId());
        String inserir = "INSERT INTO fornecedor(nome, cnpj, email, telefone, idendereco) VALUES( ?, ?, ?, ?, ?)";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            stmte.setString(1, f.getNome());
            stmte.setString(2, f.getCnpj());
            stmte.setString(3, f.getEmail());
            stmte.setString(4, f.getTelefone());
            stmte.setInt(5, f.getEndereco().getIdEndereco());
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
    
    
    public List<Fornecedor> getFornecedor(String nome)
    {
        String consultar = "SELECT * FROM fornecedor WHERE nome LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Fornecedor> listaClientes = new ArrayList();
            
            while(rs.next())
            {
                Fornecedor f = new Fornecedor();
                f.setIdfornecedor(rs.getInt("idfornecedor"));
                f.setNome(rs.getString("nome"));
                f.setCnpj(rs.getString("cnpj"));
                f.setEmail(rs.getString("email"));
                f.setTelefone(rs.getString("telefone"));
                f.getEndereco().setIdEndereco(rs.getInt("idendereco"));
                listaClientes.add(f);
            }
            return listaClientes;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    
    
        public boolean atualizaCliente(Fornecedor f)
    {
        String update = "UPDATE fornecedor SET nome=?, cnpj=?, email=?, telefone=? WHERE idcliente = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, f.getNome());
            stmte.setString(2, f.getCnpj());
            stmte.setString(3, f.getEmail());
            stmte.setString(4, f.getTelefone());
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
