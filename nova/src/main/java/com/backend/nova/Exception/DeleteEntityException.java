package com.backend.nova.Exception;


public class DeleteEntityException extends RuntimeException{
    public <T> DeleteEntityException(Long id,T entityClass, Throwable cause)
    {
        super("Error al borrar la entidad " + entityClass.toString(), cause);
    }
    public <T> DeleteEntityException(String id,T entityClass, Throwable cause)
    {
        super("Error al borrar la entidad " + entityClass.toString(), cause);
    }
}
