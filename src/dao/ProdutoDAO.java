/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Produto;
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
public class ProdutoDAO {
    private Connection con;
    private String erro;
    
    public ProdutoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirProduto(Produto p)
    {
        String inserir = "INSERT INTO produto(idproduto, quant_min, nome, precounitario, quantestoque, status) VALUES(?, ?, ?, ?, ?, ?)"; //lembrar do quantidade maxima
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, p.getIdproduto());
            stmte.setInt(2, p.getQuant_min());
            stmte.setString(3, p.getNome());
            stmte.setDouble(4, p.getPrecounitario());
            stmte.setInt(5, p.getQuantestoque());
            stmte.setString(6, p.getStatus());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public List<Produto> getProduto(String nome)
    {
        String consultar = "SELECT * FROM produto WHERE nome LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Produto> listaProduto = new ArrayList();
            
            while(rs.next())
            {
                Produto p = new Produto();
                p.setIdproduto(rs.getInt("idproduto"));
                p.setQuant_min(rs.getInt("quant_min"));
                p.setNome(rs.getString("nome"));
                p.setPrecounitario(rs.getDouble("precounitario"));
                p.setQuantestoque(rs.getInt("quantestoque"));
                p.setStatus(rs.getString("status"));
                listaProduto.add(p);
            }
            return listaProduto;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public boolean atualizaProduto(Produto p)
    {
        String update = "UPDATE produto SET nome=?,quant_min=?, precounitario=?, quantestoque=?, status=? WHERE nome = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, p.getNome());
            stmte.setInt(2, p.getQuant_min());
            stmte.setDouble(3, p.getPrecounitario());
            stmte.setInt(4, p.getQuantestoque());
            stmte.setString(5, p.getStatus());
            stmte.setString(6, p.getNome());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao atualizar: " +e.getMessage();
            return false;
        }
   
    }
    
    
    public List<Produto> getTodosProdutos()
    {
        String consultar = "SELECT * FROM produto";
        
        try{
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            ResultSet rs = stmte.executeQuery();
            List<Produto> listaProdutos = new ArrayList();
            
            while(rs.next()){
                Produto p = new Produto();
                
                
                p.setNome(rs.getString("nome"));
                p.setQuant_min(rs.getInt("quant_min"));
                p.setPrecounitario(rs.getDouble("precounitario"));
                p.setQuantestoque(rs.getInt("quantestoque"));
                p.setStatus(rs.getString("status"));
                listaProdutos.add(p);
            }
            return listaProdutos;
        }
        catch(Exception e){
            this.erro = "Erro ao buscar os produto";
            return null;
        }
    }
    
    public List<Produto> getProdutoByFiltro(String valor)
    {
        String consulta = "SELECT * FROM produto WHERE nome LIKE ?";
        
        try{
            PreparedStatement stmte = this.con.prepareStatement(consulta);
            stmte.setString(1, "%" + valor + "%");
            ResultSet rs = stmte.executeQuery();
            List<Produto> listaProdutos = new ArrayList();
            
            while(rs.next()){
                Produto p = new Produto();
                
                
                p.setNome(rs.getString("nome"));
                p.setQuant_min(rs.getInt("quant_min"));
                p.setPrecounitario(rs.getDouble("precounitario"));
                p.setQuantestoque(rs.getInt("quantestoque"));
                p.setStatus(rs.getString("status"));
                listaProdutos.add(p);
            }
            return listaProdutos;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            this.erro = "Erro ao buscar os Produto";
            return null;
        }
    }
}
