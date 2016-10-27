/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Cliente;
import beans.ItensPedido;
import beans.Pedido;
import beans.Produto;
import goldgasagua.Conexao;
import java.lang.reflect.Array;
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
public class PedidoDAO {
    private Connection con;
    private String erro;
    private int lastId = -1;
    
    public int getLastId(){
        return this.lastId;
    }
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
           //pegar o ultimo id
            ResultSet rs = stmte.executeQuery("SELECT MAX(idpedido) AS idpedido FROM pedido");
            
            if(rs.next()){
                this.lastId = rs.getInt("idpedido");
                
            }
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    public Pedido getPedidoById(int idpedido)
    {
        String consultar = "SELECT * FROM pedido p INNER JOIN itens_pedido i ON p.idpedido = i.idpedido WHERE p.idpedido = ?";
        
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setInt(1, idpedido);
            ResultSet rs = stmte.executeQuery();
            rs.first();
            
            Pedido p = new Pedido();
            ItensPedido i = new ItensPedido();
            List<ItensPedido> listaitens = new ArrayList();
            Cliente c = new Cliente();
            p.setIdpedido(rs.getInt("idpedido"));
            p.setPrioridade(rs.getString("prioridade"));
            c.setIdcliente(rs.getInt("idcliente"));
            p.setCliente(c);
            p.setFormapagamento(rs.getString("formapagamento"));
            p.setData(rs.getString("data"));
            p.setStatus(rs.getString("status"));
           
            
            if(rs.next()){
                Produto pro = new Produto();
                pro.setIdproduto(rs.getInt("idproduto"));
                i.setQuantidade(rs.getInt("quantidade"));
                i.setProduto(pro);
                i.setPedido(p);
               
                listaitens.add(i);      
            }
            p.setItens(listaitens);
            
            return p;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public List<Pedido> getPedidoTodos()
    {
        String consultar = "SELECT * FROM pedido WHERE status != 'ninguem'";
        try
        {  
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            ResultSet rs = stmte.executeQuery();
            List<Pedido> listaPedidos = new ArrayList();
       
            while(rs.next())
            {
                Pedido p = new Pedido();
                Cliente c = new Cliente();
                p.setIdpedido(rs.getInt("idpedido"));
                p.setPrioridade(rs.getString("prioridade"));
                p.setFormapagamento(rs.getString("formapagamento"));
                p.setData(rs.getString("data"));
                p.setStatus(rs.getString("status"));  
                c.setIdcliente(rs.getInt("idcliente"));
                p.setCliente(c);
                System.out.println("passou");
                p.setValor(rs.getDouble("valorpedido"));
                
                listaPedidos.add(p);
            }
            
            return listaPedidos;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao obter pedido " + e.getMessage();
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
    
    public void chamarInserirItens(Pedido p)      
    {
        ItemPedidoDAO itensPedidoDAO = new ItemPedidoDAO();
        
        for(ItensPedido i : p.getItens())
        {
            itensPedidoDAO.inserirItens(i, lastId);
        }    
    }
    
}
