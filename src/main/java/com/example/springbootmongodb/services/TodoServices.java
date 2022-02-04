package com.example.springbootmongodb.services;

import com.example.springbootmongodb.exceptions.TodoCollectionException;
import com.example.springbootmongodb.model.TodoDTO;
import com.example.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServices {

    @Autowired
    private TodoRepository todoRespository;

    ////GET ALL TODO S  Refactored

    public List<TodoDTO> getAllTodos(){
        List<TodoDTO> todos = todoRespository.findAll();
        if(todos.size()>0){
            return todos;
        }else{
            return new ArrayList<TodoDTO>();
        }

    }


//    ////GET ALL TODO S
//    public ResponseEntity<?> getAllTodos(){
//
//        List<TodoDTO> todos = todoRespository.findAll();
//        if(todos.size()>0){
//            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("No todos Available", HttpStatus.NOT_FOUND);
//        }
//    }


    ////INSERT TODO With Validation , Refactored
    public void createTodo(TodoDTO todo) throws ConstraintViolationException,TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRespository.findByTodo(todo.getTodo());
        if(todoOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        }else{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRespository.save(todo);
        }
    }

//    ////INSERT TODO
//    public ResponseEntity<?> createTodo(TodoDTO todo){
//        try{
//            todo.setCreatedAt(new Date(System.currentTimeMillis()));
//            todoRespository.save(todo);
//            return new ResponseEntity<TodoDTO>(todo, HttpStatus.ACCEPTED);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    //// GET SINGLE TODO BY ID Validation , REFACTORED
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException{
        Optional<TodoDTO> todoDTO = todoRespository.findById(id);

        if(!todoDTO.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));


        }else{
            return  todoDTO.get();
        }

    }

//    ////GET SINGLE TODO BY ID
//    public ResponseEntity<?> getSingleTodo(String id){
//
//        Optional<TodoDTO> todoDTO =  todoRespository.findById(id);
//        if(todoDTO.isPresent()){
//
//            return new ResponseEntity<TodoDTO>(todoDTO.get(),HttpStatus.OK);
//
//        }else{
//            return new ResponseEntity<>("Todo not found of id "+id , HttpStatus.NOT_FOUND);
//        }
//    }


    ////UPDATE a TODO Refactored, Validation
    public void updateTodo(String id,TodoDTO todo) throws TodoCollectionException{

        Optional<TodoDTO> todoDTO = todoRespository.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRespository.findByTodo(todo.getTodo());



        if(todoDTO.isPresent()){

            if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }

            TodoDTO tobesavedTodo = todoDTO.get();
            tobesavedTodo.setCompleted(todo.getCompleted());
            tobesavedTodo.setTodo(todo.getTodo());
            tobesavedTodo.setDescription(todo.getDescription());
            tobesavedTodo.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRespository.save(tobesavedTodo);
        }else{
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));

        }
    }



//    ////Update a TODO
//    public ResponseEntity<?> updateTodo(String id,TodoDTO todo){
//
//        Optional<TodoDTO> todoDTO = todoRespository.findById(id);
//        if(todoDTO.isPresent()){
//            TodoDTO tobesavedTodo = todoDTO.get();
//            tobesavedTodo.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : tobesavedTodo.getCompleted());
//            tobesavedTodo.setTodo(todo.getTodo() != null ? todo.getTodo() : tobesavedTodo.getTodo());
//            tobesavedTodo.setDescription(todo.getDescription() != null ? todo.getDescription() : tobesavedTodo.getDescription());
//            tobesavedTodo.setUpdatedAt(new Date(System.currentTimeMillis()));
//            todoRespository.save(tobesavedTodo);
//            return new ResponseEntity<TodoDTO>(tobesavedTodo,HttpStatus.OK);
//
//        }else{
//            return new ResponseEntity<>("Todo not found of id "+id , HttpStatus.NOT_FOUND);
//        }
//    }

    ////DELETE A TODO Refactored Validated
    public void deleteTodo(String id) throws TodoCollectionException{
       Optional<TodoDTO> todoDTOOptional = todoRespository.findById(id);
       if(!todoDTOOptional.isPresent()){
           throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
       }else{
           todoRespository.deleteById(id);
       }
    }

//    ////DELETE A TODO
//    public ResponseEntity<?> deleteTodo(String id){
//        try{
//            todoRespository.deleteById(id);
//            return new ResponseEntity<>("Successfully deleted with Id "+id , HttpStatus.OK);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//        }
//
//    }


    ////DELETE ALL TODO S Refactored Validated
    public void deleteAllTodo() throws TodoCollectionException {
        if(todoRespository.count()==0){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException());
        }else{
            todoRespository.deleteAll();
        }
    }

//    ////DELETE ALL TODO S
//    public ResponseEntity<?> deleteAllTodo(){
//        try{
//            if(todoRespository.count()!=0) {
//                todoRespository.deleteAll();
//                return new ResponseEntity<>("Successfully deleted ALL Todos", HttpStatus.OK);
//            }else{
//                return new ResponseEntity<>("Nothing to delete",HttpStatus.NOT_FOUND);
//            }
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }


}
