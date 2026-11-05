package co.uniquindio.edu.javafx.viewController;

import co.uniquindio.edu.javafx.App;
import co.uniquindio.edu.javafx.controller.ClienteController;
import co.uniquindio.edu.javafx.model.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteViewController {

    ClienteController clienteController;
    ObservableList<Cliente> listClientes = FXCollections.observableArrayList();
    Cliente selectedCliente;

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    @FXML private TextField txtNombre;
    @FXML private Button btnLimpiar;
    @FXML private TableView<Cliente> tblListCliente;
    @FXML private Button btnEliminar;
    @FXML private Button btnActualizarCliente;
    @FXML private TableColumn<Cliente, String> tbcNombre;
    @FXML private TextField txtApellido;
    @FXML private TableColumn<Cliente, String> tbcApellido;
    @FXML private Button btbAgregarCliente;
    @FXML private TableColumn<Cliente, String> tbcCedula;
    @FXML private TextField txtCedula;
    private App app;

    @FXML
    void onAgregarCliente() {
        agregarCliente();
    }

    @FXML
    void onActualizarCliente() {
        actualizarCliente();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onEliminar() {
        eliminarCliente();
    }

    @FXML
    void initialize() {
        // Se usa App.empresa (static): en initialize() la variable "app" aun es null
        clienteController = new ClienteController(App.empresa);
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerClientes();
        tblListCliente.getItems().clear();
        tblListCliente.setItems(listClientes);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
    }

    private void obtenerClientes() {
        listClientes.addAll(clienteController.obtenerListaClientes());
    }

    private void listenerSelection() {
        tblListCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedCliente = newSelection;
            mostrarInformacionCliente(selectedCliente);
        });
    }

    private void mostrarInformacionCliente(Cliente cliente) {
        if (cliente != null) {
            txtCedula.setText(cliente.getCedula());
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
        }
    }

    private void agregarCliente() {
        Cliente cliente = buildCliente();
        if (clienteController.crearCliente(cliente)) {
            listClientes.add(cliente);
            limpiarCamposCliente();
        }
    }

    private Cliente buildCliente() {
        return new Cliente(txtCedula.getText(), txtNombre.getText(), txtApellido.getText());
    }

    private void eliminarCliente() {
        if (clienteController.eliminarCliente(txtCedula.getText())) {
            listClientes.remove(selectedCliente);
            limpiarCamposCliente();
            limpiarSeleccion();
        }
    }

    private void actualizarCliente() {
        if (selectedCliente != null &&
                clienteController.actualizarCliente(selectedCliente.getCedula(), buildCliente())) {
            // El modelo ya modifico el objeto seleccionado; solo se refresca la tabla
            tblListCliente.refresh();
            limpiarSeleccion();
            limpiarCamposCliente();
        }
    }

    private void limpiarSeleccion() {
        tblListCliente.getSelectionModel().clearSelection();
        limpiarCamposCliente();
    }

    private void limpiarCamposCliente() {
        txtCedula.clear();
        txtNombre.clear();
        txtApellido.clear();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
