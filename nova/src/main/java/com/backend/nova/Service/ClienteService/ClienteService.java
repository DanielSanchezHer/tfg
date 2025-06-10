package com.backend.nova.Service.ClienteService;

import com.backend.nova.Entity.Carrito;
import com.backend.nova.Entity.Cliente;
import com.backend.nova.Exception.CreateEntityException;
import com.backend.nova.Exception.DeleteEntityException;
import com.backend.nova.Exception.NotFoundEntityException;
import com.backend.nova.Exception.UpdateEntityException;
import com.backend.nova.Repository.ICarritoRepository;
import com.backend.nova.Repository.IClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository iClienteRepository;
    @Autowired
    private ICarritoRepository iCarritoRepository;

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) iClienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return iClienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(id, Cliente.class.getSimpleName()));
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        try {
            return iClienteRepository.save(cliente);
        } catch (Exception e) {
            throw new CreateEntityException(cliente, e);
        }
    }

    @Override
    public Cliente modificarCliente(Long id, Cliente cliente) {
        try {
            Cliente newCliente =iClienteRepository.findById(id)
                    .orElseThrow(() -> new NotFoundEntityException(id, Cliente.class.getSimpleName()));
            newCliente.setDni(cliente.getDni());
            newCliente.setEmail(cliente.getEmail());
            newCliente.setNombre(cliente.getNombre());
            newCliente.setApellido(cliente.getApellido());

            return iClienteRepository.save(newCliente);
        } catch (Exception e) {
            throw new UpdateEntityException("Error al modificar el cliente: " + id, e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        try {
            iClienteRepository.findById(id)
                    .orElseThrow(() -> new NotFoundEntityException(id, Cliente.class.getSimpleName()));
            iClienteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new DeleteEntityException(id, Cliente.class.getSimpleName(), e);
        }
    }

    @Override
    public Carrito finalizarCarritoActivo(Long clienteId) {
        Cliente cliente = iClienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundEntityException(clienteId, Cliente.class.getSimpleName()));

        Carrito carrito = iClienteRepository.findCarritoNoFinalizadoByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundEntityException(clienteId, Carrito.class.getSimpleName()));

        carrito.setFinalizado(true);
        carrito.setFechaCompra(LocalDateTime.now());

        try {
            return iCarritoRepository.save(carrito);
        } catch (Exception e) {
            throw new UpdateEntityException("Error al finalizar el carrito del cliente: " + clienteId, e);
        }
    }

    @Override
    public List<Carrito> findAllCarritoWithProductoByClienteId(Long clienteId) {
        iClienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundEntityException(clienteId, Cliente.class.getSimpleName()));

        List<Carrito> carritos = iClienteRepository.findAllCarritoWithProductoByClienteId(clienteId);

        if (carritos.isEmpty()) {
            throw new NotFoundEntityException(clienteId, Carrito.class.getSimpleName());
        }

        return carritos;
    }

    @Override
    public Double calcularTotalCarritoActivo(Long clienteId) {
        iClienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundEntityException(clienteId, Cliente.class.getSimpleName()));

        Double total = iClienteRepository.calcularTotalCarritoActivo(clienteId);

        return total != null ? total : 0.0;
    }

}
