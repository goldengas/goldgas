/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Endereco;
import beans.Fornecedor;
import beans.Funcionario;
import goldgasagua.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author IFSP
 */
public class FuncionarioDAO {
    private Connection con;
    private String erro;
     private int lastId = -1;
    
    public FuncionarioDAO()
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
    
    public boolean inserirFuncionario(Funcionario f)
    {
        EnderecoDAO enderecoDAO = new EnderecoDAO(); 
        if(enderecoDAO.inserirEndereco(f.getEndereco()) == false){
            this.erro = "Erro ao salvar endereço: " + enderecoDAO.getErro();
            return false;
        }
        
        f.getEndereco().setIdEndereco(enderecoDAO.getLastId());
        
        
        String inserir = "INSERT INTO funcionario(nome, rg, datanascimento, cnh, login, senha, cpf, cargo, idendereco, email, telefone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try
        {
            
            PreparedStatement stmte = this.con.prepareStatement(inserir, Statement.RETURN_GENERATED_KEYS);

            stmte.setString(1, f.getNome());
            stmte.setString(2, f.getRg());
            stmte.setDate(3, (Date) f.getNascimento());
            stmte.setString(4, f.getCnh());
            stmte.setString(5, f.getLogin());
            stmte.setString(6, f.getSenha());  
            stmte.setString(7, f.getCpf());
            stmte.setString(8, f.getCargo());
            stmte.setInt(9, f.getEndereco().getIdEndereco());
            stmte.setString(10, f.getEmail());
            stmte.setString(11, f.getTelefone());
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
    
    public List<Funcionario> getFuncionario(String nome)
    {
        String consultar = "SELECT * FROM funcionario INNER JOIN endereco ON funcionario.idfuncionario = endereco.idendereco && funcionario.nome LIKE ?";
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setString(1, "%"+nome+"%");
            ResultSet rs = stmte.executeQuery();
            List<Funcionario> listaFuncionarios = new ArrayList();
            
            while(rs.next())
            {
                Funcionario f = new Funcionario();
                Endereco end = new Endereco();
                //pegar todos os campos
                f.setIdfuncionario(rs.getInt("idfuncionario"));
                f.setNome(rs.getString("nome"));
                f.setRg(rs.getString("rg"));
                f.setNascimento(rs.getDate("datanascimento"));
                f.setCnh(rs.getString("cnh"));
                f.setLogin(rs.getString("login"));
                f.setSenha(rs.getString("senha"));
                f.setCpf(rs.getString("cpf"));
                f.setCargo(rs.getString("cargo"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                end.setIdEndereco(rs.getInt("idendereco"));
                end.setRua(rs.getString("rua"));
                end.setNum(rs.getInt("num"));
                end.setBairro(rs.getString("bairro"));
                end.setReferencia(rs.getString("referencia"));
                end.setComplemento(rs.getString("complemento"));
                end.setCidade(rs.getString("cidade"));
                end.setEstado(rs.getString("estado"));
                f.setEndereco(end);
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
    
    public Funcionario getFuncionarioById(int idfuncionario)
    {
        String consultar = "SELECT * FROM funcionario f INNER JOIN endereco e ON f.idendereco = e.idendereco WHERE f.idfuncionario = ?";
        
        
        try
        {
            PreparedStatement stmte = this.con.prepareStatement(consultar);
            stmte.setInt(1, idfuncionario);
            ResultSet rs = stmte.executeQuery();
            rs.first();
            
            Funcionario f = new Funcionario();
            Endereco end = new Endereco();
            f.setIdfuncionario(rs.getInt("idfuncionario"));
            f.setNome(rs.getString("nome"));
            f.setRg(rs.getString("rg"));
            f.setNascimento(rs.getDate("datanascimento"));
            f.setCnh(rs.getString("cnh"));
            f.setLogin(rs.getString("login"));
            f.setSenha(rs.getString("senha"));
            f.setCpf(rs.getString("cpf"));
            f.setCargo(rs.getString("cargo"));
            end.setIdEndereco(rs.getInt("idendereco"));
            f.setTelefone(rs.getString("telefone"));
            f.setEmail(rs.getString("email"));
            end.setRua(rs.getString("rua"));
            end.setNum(rs.getInt("num"));
            end.setBairro(rs.getString("bairro"));
            end.setReferencia(rs.getString("referencia"));
            end.setComplemento(rs.getString("complemento"));
            end.setCidade(rs.getString("cidade"));
            end.setEstado(rs.getString("estado"));
            f.setEndereco(end);
            
            System.out.println(f.getIdfuncionario());
            return f;
        }
        catch(Exception e)
        {
            this.erro = "Erro ao inserir " + e.getMessage();
            return null;
        }
    }
    
    
    public boolean atualizaFuncionario(Funcionario f)
    {
        EnderecoDAO enderecoDAO = new EnderecoDAO(); 
        if(enderecoDAO.atualizaEndereco(f.getEndereco()) == false){
            this.erro = "Erro ao atualizar endereço: " + enderecoDAO.getErro();
            return false;
        }
        
       // f.getEndereco().setIdEndereco(enderecoDAO.getLastId());
        
        String update = "UPDATE funcionario SET nome=?, rg=?, datanascimento=?, cnh=?, login=?, senha=?, cpf=?, cargo=?,  email=?, telefone=?  WHERE idfuncionario = ?";
        try
        {
        PreparedStatement stmte = con.prepareStatement(update);
            
            stmte.setString(1, f.getNome());
            stmte.setString(2, f.getRg());
            stmte.setDate(3, (Date) f.getNascimento());
            stmte.setString(4, f.getCnh());
            stmte.setString(5, f.getLogin());
            stmte.setString(6, f.getSenha());  
            stmte.setString(7, f.getCpf());
            stmte.setString(8, f.getCargo());
            stmte.setString(9, f.getEmail());
            stmte.setString(10, f.getTelefone());
            stmte.setInt(11, f.getIdfuncionario());
            
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
