/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.ItensPedido;
import beans.Pedido;
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
public class PedidoDAO {
    private Connection con;
    private String erro;
    
    public PedidoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirPedido(Pedido p)
    {
       
        String inserir = "INSERT INTO pedido(prioridade, formapagamento, data,  idcliente, valorpedido, status) VALUES( ?, ?,  ?, ?, ?, ?)";
        try
        { 
             
           
            PreparedStatement stmte = this.con.prepareStatement(inserir);
           
            stmte.setString(1, p.getPrioridade());
            stmte.setString(2, p.getFormapagamento());  
            stmte.setString(3, p.getData());
            stmte.setInt(4, p.getCliente().getIdcliente());
            stmte.setDouble(5, p.getValor());
            stmte.setString(6, p.getStatus());
            
            stmte.execute();
            
            
            
            ItemPedidoDAO itensDAO = new ItemPedidoDAO();
            
            for(ItensPedido i : p.getItens())
            {
                itensDAO.inserirItens(i);
                
            }
            
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
     public List<Pedido> getPedido(String idpedido)
    {
        String consultar = "SELECT * FROM pedido WHERE idpedido LIKE ?";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+idpedido+"%");
            ResultSet rs = stmte.executeQuery();
            List<Pedido> listaPedidos = new ArrayList();
            
            while(rs.next())
            {
                Pedido p = new Pedido();
                p.setIdpedido(rs.getInt("idpedido"));
                p.setPrioridade(rs.getString("prioridade"));
                p.setFormapagamento(rs.getString("formapagamento"));
                p.setData(rs.getString("data"));
                p.setStatus(rs.getString("status"));
                p.getCliente().setIdcliente(rs.getInt("idcliente"));
                listaPedidos.add(p);
            }
            return listaPedidos;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public boolean atualizaPedido(Pedido p)
    {
        String update = "UPDATE pedido SET prioridade=?, formapagamento=?, data=?, status=? WHERE idcliente = ?";
        try
        {
 
        PreparedStatement stmte = con.prepareStatement(update);
            stmte.setString(1, p.getPrioridade());
            stmte.setString(2, p.getFormapagamento());
            stmte.setString(3, p.getData());
            stmte.setString(4, p.getStatus());
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
