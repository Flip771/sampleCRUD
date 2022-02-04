package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.exceptions.TodoCollectionException;
import com.example.springbootmongodb.model.TodoDTO;
import com.example.springbootmongodb.services.TodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


@RestController
public class TodoController {


    @Autowired
    private TodoServices todoServices;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todos = todoServices.getAllTodos();
        return new ResponseEntity<>(todos,todos.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
         try{
             todoServices.createTodo(todo);
             return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
         } catch (ConstraintViolationException e) {
             return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
         } catch (TodoCollectionException e) {
             return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
         }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable String id){
        try {
            return new ResponseEntity<>(todoServices.getSingleTodo(id),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String id,@RequestBody TodoDTO todo){
        try {
            todoServices.updateTodo(id,todo);
            return new ResponseEntity<>("Updated todo with id "+id,HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);

        }catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id){
         try{
             todoServices.deleteTodo(id);
             return new ResponseEntity<>("Deleted todo with id "+id,HttpStatus.OK);
         }catch(TodoCollectionException e){
             return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
         }

    }

    @DeleteMapping("/todos/")
    public ResponseEntity<?> deleteAllTodo(){
        try {
            todoServices.deleteAllTodo();
            return new ResponseEntity<>("Deleted all Todos",HttpStatus.OK);
        }catch(TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
