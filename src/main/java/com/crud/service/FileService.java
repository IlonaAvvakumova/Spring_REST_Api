package com.crud.service;

import com.crud.model.FileEntity;
import com.crud.repository.JPA.FileRep;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class FileService {

    private final FileRep fileRep;


    @Autowired
    public FileService(FileRep fileRep) {
        this.fileRep = fileRep;
    }


    public List<FileEntity> getAll() {
        return fileRep.findAll();
    }


    public FileEntity getById(Integer id) {
        Optional<FileEntity> optional = fileRep.findById(id);
        return optional.orElse(null);
    }


    public FileEntity create(FileEntity fileEntity) {
        return fileRep.save(fileEntity);
    }


    public void deleteById(Integer id) {
        fileRep.deleteById(id);
    }
}
