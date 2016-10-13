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
        String inserir = "INSERT INTO produto(idproduto, quant_min, nome, precounitario, quantestoque) VALUES(?, ?, ?, ?, ?)"; //lembrar do quantidade maxima
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, p.getIdproduto());
            stmte.setInt(2, p.getQuant_min());
            stmte.setString(3, p.getNome());
            stmte.setDouble(4, p.getPrecounitario());
            stmte.setInt(5, p.getQuantestoque());

            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public List<Produto> getProduto(int idproduto)
    {
        String consultar = "SELECT * FROM produto WHERE idproduto LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+idproduto+"%");
            ResultSet rs = stmte.executeQuery();
            List<Produto> listaProduto = new ArrayList();
            
            while(rs.next())
            {
                Produto p = new Produto();
                p.setIdproduto(rs.getInt("idcliente"));
                p.setQuant_min(rs.getInt("quant_min"));
                p.setNome(rs.getString("nome"));
                p.setPrecounitario(rs.getDouble("precounitario"));
                p.setQuantestoque(rs.getInt("quantestoque"));

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
        String update = "UPDATE produto SET quant_min=?,nome=?, precounitario=?, quantestoque=? WHERE idproduto = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, p.getNome());
            stmte.setInt(2, p.getQuant_min());
            stmte.setDouble(3, p.getPrecounitario());
            stmte.setInt(4, p.getQuantestoque());
            stmte.setInt(5, p.getIdproduto());
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
