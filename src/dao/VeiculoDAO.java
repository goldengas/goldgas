/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import beans.Veiculo;
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
public class VeiculoDAO {
    private Connection con;
    private String erro;
    
    public VeiculoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirVeiculo(Veiculo v)
    {
        String inserir = "INSERT INTO veiculo(idVeiculo, modelo, marca, tipocombustivel, placa, status) VALUES(?, ?, ?, ?, ?, ?)";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, v.getIdVeiculo());
            stmte.setString(2, v.getModelo());
            stmte.setString(3, v.getMarca());
            stmte.setString(4, v.getTipocombustivel());
            stmte.setString(5, v.getPlaca());
            stmte.setString(6, v.getStatus());
            
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public List<Veiculo> getVeiculo(String nome)
    {
        String consultar = "SELECT * FROM veiculo WHERE idveiculo LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Veiculo> listaVeiculo = new ArrayList();
            
            while(rs.next())
            {
                Veiculo v = new Veiculo();
                v.setIdVeiculo(rs.getInt("idveiculo"));
                v.setModelo(rs.getString("modelo"));
                v.setMarca(rs.getString("marca"));
                v.setTipocombustivel(rs.getString("tipocombustivel"));
                v.setPlaca(rs.getString("placa"));
                v.setStatus(rs.getString("status"));

                listaVeiculo.add(v);
            }
            return listaVeiculo;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public boolean atualizaVeiculo(Veiculo v)
    {
        String update = "UPDATE veiculo SET modelo=?, marca=?, tipocombustivel=?, placa=?, status=? WHERE nome = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, v.getModelo());
            stmte.setString(2, v.getMarca());
            stmte.setString(3, v.getTipocombustivel());
            stmte.setString(4, v.getPlaca());
            stmte.setString(5, v.getStatus());
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
