/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import beans.Endereco;
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
    
    
    public Fornecedor getFornecedorById(int idfornecedor)
    {
        String consultar = "SELECT * FROM fornecedor f INNER JOIN endereco e ON f.idendereco = e.idendereco WHERE f.idfornecedor = ?";
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setInt(1, idfornecedor);
            ResultSet rs = stmte.executeQuery();
            rs.first();
            
            Fornecedor f = new Fornecedor();
            Endereco end = new Endereco();
            f.setIdfornecedor(rs.getInt("idfornecedor"));
            f.setNome(rs.getString("nome"));
            f.setCnpj(rs.getString("cnpj"));
            f.setEmail(rs.getString("email"));
            f.setTelefone(rs.getString("telefone"));
            end.setIdEndereco(rs.getInt("idendereco"));
            end.setRua(rs.getString("rua"));
            end.setNum(rs.getInt("num"));
            end.setBairro(rs.getString("bairro"));
            end.setReferencia(rs.getString("referencia"));
            end.setComplemento(rs.getString("complemento"));
            end.setCidade(rs.getString("cidade"));
            end.setEstado(rs.getString("estado"));
            f.setEndereco(end);
            
            return f;
            
        }
        catch(Exception e)
        {
            this.erro = "Erro ao buscar Fornecedor " + e.getMessage();
            return null;
        }
    }
    public List<Fornecedor> getFornecedor(String nome)
    {
  
        String consultar = "SELECT * FROM fornecedor INNER JOIN endereco ON fornecedor.idendereco = endereco.idendereco && fornecedor.nome LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Fornecedor> listaFornecedor = new ArrayList();
            
            
            while(rs.next())
            {
                Fornecedor f = new Fornecedor();
                Endereco end = new Endereco();
                f.setIdfornecedor(rs.getInt("idfornecedor"));
                f.setNome(rs.getString("nome"));
                f.setCnpj(rs.getString("cnpj"));
                f.setEmail(rs.getString("email"));
                f.setTelefone(rs.getString("telefone"));
                end.setIdEndereco(rs.getInt("idendereco"));
                end.setRua(rs.getString("rua"));
                end.setNum(rs.getInt("num"));
                end.setBairro(rs.getString("bairro"));
                end.setReferencia(rs.getString("referencia"));
                end.setComplemento(rs.getString("complemento"));
                end.setCidade(rs.getString("cidade"));
                end.setEstado(rs.getString("estado"));
                f.setEndereco(end);
                
                listaFornecedor.add(f);
            }
            return listaFornecedor;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    
    
    
    public boolean atualizaFornecedor(Fornecedor f)
    {
        EnderecoDAO enderecoDAO = new EnderecoDAO(); 
        if(enderecoDAO.atualizaEndereco(f.getEndereco()) == false){
            this.erro = "Erro ao atualizar endereço: " + enderecoDAO.getErro();
            return false;
        }
        
       // f.getEndereco().setIdEndereco(enderecoDAO.getLastId());
        
        String update = "UPDATE fornecedor SET cnpj=?, email=?, telefone=? WHERE nome = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            
            stmte.setString(1, f.getCnpj());
            stmte.setString(2, f.getEmail());
            stmte.setString(3, f.getTelefone());
            stmte.setString(4, f.getNome());
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
