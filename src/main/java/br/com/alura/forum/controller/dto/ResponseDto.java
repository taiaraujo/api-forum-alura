package br.com.alura.forum.controller.dto;

import br.com.alura.forum.models.Response;

import java.time.LocalDateTime;

public class ResponseDto {

    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private String nameAuthor;

    public ResponseDto(Response response) {
        this.id = response.getId();
        this.message = response.getMessage();
        this.createdAt = response.getCreatedAt();
        this.nameAuthor = response.getAuthor().getName();
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }
}
