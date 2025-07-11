package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbeddingRepository extends JpaRepository<Embedding, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find embeddings by a specific attribute:
    // List<Embedding> findBySomeAttribute(String someAttribute);
}
