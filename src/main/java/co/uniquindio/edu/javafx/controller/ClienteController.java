package co.uniquindio.edu.javafx.controller;

import co.uniquindio.edu.javafx.model.Cliente;
import co.uniquindio.edu.javafx.model.Empresa;

import java.util.Collection;

public class ClienteController {

    Empresa empresa;

    public ClienteController(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean crearCliente(Cliente cliente) {
        return empresa.agregarCliente(cliente);
    }

    public Collection<Cliente> obtenerListaClientes() {
        return empresa.getClientes();
    }

    public boolean eliminarCliente(String cedula) {
        return empresa.eliminarCliente(cedula);
    }

    public boolean actualizarCliente(String cedula, Cliente cliente) {
        return empresa.actualizarCliente(cedula, cliente);
    }
}
