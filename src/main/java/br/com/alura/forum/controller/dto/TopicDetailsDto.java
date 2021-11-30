package br.com.alura.forum.controller.dto;

import br.com.alura.forum.models.Response;
import br.com.alura.forum.models.StatusTopic;
import br.com.alura.forum.models.Topic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicDetailsDto {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private String nameAuthor;
    private StatusTopic status;
    private List<ResponseDto> responses;


    public TopicDetailsDto(Topic topic){
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.createdAt = topic.getCreatedAt();
        this.nameAuthor = topic.getAuthor().getName();
        this.status = topic.getStatus();
        this.responses = new ArrayList<>();
        this.responses.addAll(topic.getResponseList().stream().map(ResponseDto::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public StatusTopic getStatus() {
        return status;
    }

    public List<ResponseDto> getResponses() {
        return responses;
    }
}
