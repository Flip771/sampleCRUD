package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO,String> {

     @Query(" {'todo' : ?0} ") // {parameter} : ? 0 means first parameter in the below method
     Optional<TodoDTO> findByTodo(String todo);
}
