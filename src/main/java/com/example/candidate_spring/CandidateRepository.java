package com.example.candidate_spring;

import org.springframework.data.jpa.repository.JpaRepository;

interface CandidateRepository extends JpaRepository<Candidate, Long> {
   
}