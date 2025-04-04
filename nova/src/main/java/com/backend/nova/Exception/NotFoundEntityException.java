package com.backend.nova.Exception;

public class NotFoundEntityException extends RuntimeException
{
    public NotFoundEntityException(Long id, String entity)
    {
        super("la entidad " + entity + " buscada con id: " + id + " no existe");
    }

    public <T> NotFoundEntityException(Long id, Class<T> entity)
    {
        super("la entidad " + entity.toString() + " buscada con id: " + id + " no existe");
    }
    public NotFoundEntityException(String id, String entity)
    {
        super("la entidad " + entity + " buscada con id: " + id + " no existe");
    }

    public <T> NotFoundEntityException(String id, Class<T> entity)
    {
        super("la entidad " + entity.toString() + " buscada con id: " + id + " no existe");
    }
}