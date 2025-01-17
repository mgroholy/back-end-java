package com.codecool.peermentoringbackend;

import com.codecool.peermentoringbackend.entity.ProjectEntity;
import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.TechnologyEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.repository.ProjectTagRepository;
import com.codecool.peermentoringbackend.repository.QuestionRepository;
import com.codecool.peermentoringbackend.repository.TechnologyTagRepository;
import com.codecool.peermentoringbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class PeerMentoringBackEndApplication {

    private TechnologyTagRepository technologyTagRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private ProjectTagRepository projectTagRepository;

    @Autowired
    public PeerMentoringBackEndApplication(TechnologyTagRepository technologyTagRepository, UserRepository userRepository, QuestionRepository questionRepository, ProjectTagRepository projectTagRepository) {
        this.technologyTagRepository = technologyTagRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.projectTagRepository = projectTagRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PeerMentoringBackEndApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {

            TechnologyEntity py = TechnologyEntity.builder().technologyTag("py").build();
            TechnologyEntity python = TechnologyEntity.builder().technologyTag("Python").build();
            TechnologyEntity flask = TechnologyEntity.builder().technologyTag("Flask").build();
            TechnologyEntity postgreSQL = TechnologyEntity.builder().technologyTag("PostgreSQL").build();
            TechnologyEntity java = TechnologyEntity.builder().technologyTag("Java").build();
            TechnologyEntity jdbc = TechnologyEntity.builder().technologyTag("JDBC").build();
            TechnologyEntity spring = TechnologyEntity.builder().technologyTag("Spring").build();
            TechnologyEntity lombok = TechnologyEntity.builder().technologyTag("Lombok").build();
            technologyTagRepository.saveAll(Arrays.asList(py, python, flask, postgreSQL, java, jdbc, spring, lombok));

            UserEntity testUser = UserEntity.builder().firstName("test").lastName("test").email("test@codecool.com").city("Budapest").country("Hungary").username("test").password("{bcrypt}$2a$10$/A5s3AjcKnRem6lS3uwn5.hV.xZzqy6SK8mn4uRyBQXMj5RcHIUG6").build();
            userRepository.save(testUser);

            ProjectEntity askMate = ProjectEntity.builder().projectTag("Ask Mate").build();
            ProjectEntity sixHandshakes = ProjectEntity.builder().projectTag("Six Handshakes").build();
            ProjectEntity hangman = ProjectEntity.builder().projectTag("Hangman").build();

            projectTagRepository.saveAll(Arrays.asList(askMate, sixHandshakes, hangman));

        };
    }


}
