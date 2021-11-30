package br.com.alura.forum.controller.form;

import br.com.alura.forum.models.Topic;
import br.com.alura.forum.repository.TopicRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TopicUpdateForm {

    @NotNull
    @NotEmpty
    @Length(min=5)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NotNull @NotEmpty @Length(min=10)
    private String message;

    public Topic update(Long id, TopicRepository topicRepository) {
        Topic topic = topicRepository.getById(id);

        topic.setTitle(this.title);
        topic.setMessage(this.message);

        return topic;
    }
}
