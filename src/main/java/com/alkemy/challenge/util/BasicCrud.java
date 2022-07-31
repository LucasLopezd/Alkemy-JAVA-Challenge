package com.alkemy.challenge.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface BasicCrud <E, U, N>{
    
    E create(N dto, MultipartFile image);

    E update(U dto, Long id, MultipartFile image);

    void delete(Long id);

    E getById(Long id);

    List<E> getAll();

    E checkAndSetAtributesForUpdate(U dto, Long id, MultipartFile image);
}
