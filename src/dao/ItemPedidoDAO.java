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

/**
 *
 * @author IFSP
 */
public class ItemPedidoDAO {
    private Connection con;
    private String erro;
    private PedidoDAO pedidoDAO;
    public ItemPedidoDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirItens(ItensPedido itens, int lastId)
    {

        String inserir = "INSERT INTO itens_pedido (idpedido, idproduto , quantidade) VALUES (?, ?, ?)";
        try
        { 
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, lastId);
            stmte.setInt(2, itens.getProduto().getIdproduto());
            stmte.setInt(3, itens.getQuantidade());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
}
