package com.johan.artere.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException category(Long id) {
        return new ResourceNotFoundException("Catégorie introuvable avec l'id : " + id);
    }

    public static ResourceNotFoundException product(Long id) {
        return new ResourceNotFoundException("Produit introuvable avec l'id : " + id);
    }
}
