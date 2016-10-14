/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Funcionario;
import goldgasagua.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author IFSP
 */
public class FuncionarioDAO {
    private Connection con;
    private String erro;
    
    public FuncionarioDAO()
    {
        this.con = Conexao.getConnection();
    }
    public String getErro()
    {
        return this.erro;
    }
    public boolean inserirFuncionario(Funcionario f)
    {
        String inserir = "INSERT INTO funcionario(idfuncionario, login, senha, cpf, cnh, nome, nascimento, telefone, email, idendereco) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir);
            stmte.setInt(1, f.getIdfuncionario());
            stmte.setString(2, f.getLogin());
            stmte.setString(3, f.getSenha());
            stmte.setString(4, f.getCpf());
            stmte.setString(5, f.getCnh());
            stmte.setString(6, f.getNome());
            stmte.setDate(7, (Date) f.getNascimento());
            stmte.setString(8, f.getTelefone());
            stmte.setString(9, f.getEmail());
            stmte.setInt(10, f.getEndereco().getIdEndereco());
            stmte.execute();
            return true;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return false;
        }
    }
    
    public List<Funcionario> getFuncionario(String nome)
    {
        String consultar = "SELECT * FROM funcionario WHERE nome LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Funcionario> listaFuncionarios = new ArrayList();
            
            while(rs.next())
            {
                Funcionario f = new Funcionario();
                //pegar todos os campos
                f.setIdfuncionario(rs.getInt("idfuncionario"));
                f.setNome(rs.getString("nome"));
                listaFuncionarios.add(f);
            }
            return listaFuncionarios;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    public boolean autenticar(Funcionario f)
    {
        String autenticar = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
        
        try{
            PreparedStatement stmte = this.con.prepareStatement(autenticar);
            stmte.setString(1, f.getLogin());
            stmte.setString(2, f.getSenha());
            ResultSet rs = stmte.executeQuery();
            
            while(rs.next()){
                return true;
            }
        }
        catch(Exception e){
            return false;
        }
        return false;
    }
    public Funcionario validaLogin(String login)
    {
        String validar = "SELECT * FROM funcionario WHERE login = ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(validar);
            stmte.setString(1, login);
            ResultSet rs = stmte.executeQuery();
            rs.first();
         
            Funcionario f2 = new Funcionario();
            f2.setLogin(rs.getString("login"));
            f2.setCargo(rs.getString("cargo"));
            return f2;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    
}
