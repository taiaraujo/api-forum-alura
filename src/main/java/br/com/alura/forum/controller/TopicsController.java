package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicDetailsDto;
import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.form.TopicForm;
import br.com.alura.forum.controller.form.TopicUpdateForm;
import br.com.alura.forum.models.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/topics")
public class TopicsController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDto> findOne(@PathVariable Long id) {

        Optional<Topic> topic = topicRepository.findById(id);
        return topic.map(value -> ResponseEntity.ok(new TopicDetailsDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Cacheable(value = "topicsList")
    public Page<TopicDto> findList(@RequestParam(required = false) String nameCourse,
                                   @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable pageable) {

        if (nameCourse == null) {
            Page<Topic> topics = topicRepository.findAll(pageable);
            return TopicDto.convert(topics);
        }
        Page<Topic> topics = topicRepository.findByCourse_Name(nameCourse, pageable);
        return TopicDto.convert(topics);
    }

    @PostMapping
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicDto> create(@RequestBody @Valid TopicForm topicForm, UriComponentsBuilder uriBuilder) {
        Topic topic = topicForm.convert(courseRepository);
        topicRepository.save(topic);

        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicDto> update(@PathVariable Long id, @RequestBody @Valid TopicUpdateForm topicForm) {
        Optional<Topic> optional = topicRepository.findById(id);

        if(optional.isPresent()){
            Topic topic = topicForm.update(id, topicRepository);
            return ResponseEntity.ok(new TopicDto(topic));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Topic> optional = topicRepository.findById(id);

        if(optional.isPresent()) {
            topicRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


}
