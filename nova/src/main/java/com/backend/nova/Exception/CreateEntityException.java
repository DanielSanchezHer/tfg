package com.backend.nova.Exception;

public class CreateEntityException extends RuntimeException
{
    public <T> CreateEntityException(T entityClass, Throwable cause)
    {
        super("Error al crear la entidad " + entityClass.toString(), cause);
    }
}
