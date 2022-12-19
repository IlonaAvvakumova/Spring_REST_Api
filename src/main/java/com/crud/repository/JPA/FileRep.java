package com.crud.repository.JPA;
import com.crud.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRep extends JpaRepository<FileEntity, Integer> {

}
