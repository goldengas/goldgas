/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Endereco;
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
public class EnderecoDAO {
    private Connection con;
    private String erro;
    private int lastId = -1;
    
    public EnderecoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }

    public int getLastId() {
        return lastId;
    }
    
    public boolean inserirEndereco(Endereco e)
    {
        String inserir = "INSERT INTO endereco(rua,num, bairro, referencia, complemento, cidade, estado) VALUES(?,?, ?, ?, ?, ?, ?)";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);
            stmte.setString(1, e.getRua());
            stmte.setInt(2, e.getNum());
            stmte.setString(3, e.getBairro());
            stmte.setString(4, e.getReferencia());
            stmte.setString(5, e.getComplemento());
            stmte.setString(6, e.getCidade());          
            stmte.setString(7, e.getEstado());
            stmte.executeUpdate();
            ResultSet rs = stmte.getGeneratedKeys();
            if(rs.next()){
                this.lastId = rs.getInt(1);
            }
            
            return true;
        }
        catch(Exception ex)
        {
            this.erro = "Erro ao inserir " + ex.getMessage();
            return false;
        }
    }
    
    public List<Endereco> getEndereco(int idendereco)
    {
        String consultar = "SELECT * FROM endereco WHERE idendereco LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+idendereco+"%");
            ResultSet rs = stmte.executeQuery();
            List<Endereco> listaEnderecos = new ArrayList();
            
            while(rs.next())
            {
                Endereco e = new Endereco();
                e.setIdEndereco(rs.getInt("idendereco"));
                e.setRua(rs.getString("rua"));
                e.setNum(rs.getInt("num"));
                e.setBairro(rs.getString("bairro"));
                e.setReferencia(rs.getString("referencia"));                
                e.setComplemento(rs.getString("complemento"));
                e.setCidade(rs.getString("cidade"));
                e.setEstado(rs.getString("estado"));
                listaEnderecos.add(e);
            }
            return listaEnderecos;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao obter " + e.getMessage();
            return null;
        }
    }
    
    public boolean atualizaEndereco(Endereco end)
    {
        String update = "UPDATE endereco SET rua=?, num=?, bairro=?, referencia=?, complemento=?, cidade=?, estado=? WHERE idendereco = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, end.getRua());
            stmte.setInt(2, end.getNum());
            stmte.setString(3, end.getBairro());
            stmte.setString(4, end.getReferencia());
            stmte.setString(5, end.getComplemento());
            stmte.setString(6, end.getCidade());
            stmte.setString(7, end.getEstado());
            stmte.setInt(8, end.getIdEndereco());
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
