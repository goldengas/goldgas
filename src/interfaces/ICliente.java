/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import beans.Cliente;

/**
 *
 * @author IFSP
 */
public interface ICliente {
    public void setCliente(Cliente c);
    public Cliente getCliente();
    public String getCadastro();
    
}
