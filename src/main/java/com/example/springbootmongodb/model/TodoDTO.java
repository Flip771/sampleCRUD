package com.example.springbootmongodb.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos") // this will be name of new collection in MONGODB
public class TodoDTO {

    @Id
    private String id;
    @NotNull(message = "Todo cant be empty")
    private String todo;
    @NotNull(message = "Description cant be empty")
    private String description;
    @NotNull(message = "Completed cant be empty")
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;

}
