/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author IFSP
 */
public class Produto {
    
    private int idproduto;
    private int quant_min;
    private String nome;
    private double precounitario;
    private int quantestoque;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public int getQuant_min() {
        return quant_min;
    }

    public void setQuant_min(int quant_min) {
        this.quant_min = quant_min;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecounitario() {
        return precounitario;
    }

    public void setPrecounitario(double precounitario) {
        this.precounitario = precounitario;
    }

    public int getQuantestoque() {
        return quantestoque;
    }

    public void setQuantestoque(int quantestoque) {
        this.quantestoque = quantestoque;
    }
    
}
