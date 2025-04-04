package com.backend.nova.Exception;

public class UpdateEntityException extends RuntimeException
{
    public <T> UpdateEntityException(T entityClass, Throwable cause)
    {
        super("Error al modificar la entidad " + entityClass.toString(), cause);
    }
}

