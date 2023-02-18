package com.example.candidate_spring;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateController {

  private final CandidateRepository repository;

  @PersistenceContext
  private EntityManager em;

  CandidateController(CandidateRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/candidates")
  List<Candidate> all(@RequestParam(required = false) String fname, @RequestParam(required = false) String lname,
      @RequestParam(required = false) String email) {

    var result = repository.findAll();

    if (fname != null)
      result = result.stream().filter(c -> c.getFname().equals(fname)).collect(Collectors.toList());
    if (lname != null)
      result = result.stream().filter(c -> c.getLname().equals(lname)).collect(Collectors.toList());
    if (email != null)
      result = result.stream().filter(c -> c.getEmail().equals(email)).collect(Collectors.toList());

    return result;
  }

  @GetMapping("/candidates/{id}")
  Candidate findById(@PathVariable Long id) {

    return repository.findById(id)
        .orElseThrow(() -> new CandidateNotFoundException(id));
  }

  @PostMapping("/candidates")
  Candidate newCandidate(@RequestBody Candidate newCandidate) {
    return repository.save(newCandidate);
  }

  @GetMapping("/candidates/scoregreater/{floor}")

  List<Candidate> findByScore(@PathVariable Integer floor) {

    TypedQuery<Candidate> query = em.createQuery("select c from Candidate c where c.score >= ?1 ",
        Candidate.class);

    query.setParameter(1, floor);

    List<Candidate> result = query.getResultList();

    if (result.size() > 0) {
      return result;
    } else {
      throw new CandidateNotFoundException(floor);
    }
  }

  @PutMapping("/candidates/{id}")
  Candidate updateCandidate(@RequestBody Candidate newCandidate, @PathVariable Long id) {

    return repository.findById(id)
        .map(Candidate -> {
          Candidate.setFname(newCandidate.getFname());
          Candidate.setLname(newCandidate.getLname());
          Candidate.setEmail(newCandidate.getEmail());
          Candidate.setScore(newCandidate.getScore());
          return repository.save(Candidate);
        })
        .orElseGet(() -> {
          newCandidate.setId(id);
          return repository.save(newCandidate);
        });
  }

  @DeleteMapping("/candidates/{id}")
  ResponseEntity<?> deleteCandidate(@PathVariable Long id) {

    repository.findById(id).orElseThrow(() -> new CandidateNotFoundException(id));
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
