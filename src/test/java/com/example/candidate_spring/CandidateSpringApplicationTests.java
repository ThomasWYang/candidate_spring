package com.example.candidate_spring;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TpgDemoApplicationTests {

	@Autowired
	private CandidateController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() throws Exception {
		assertNotNull(controller);
	}

	@Test
	void getAllShouldReturnAllCandidates() throws Exception {
		this.mockMvc.perform(get("/candidates"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}

	@Test
	void getByFnameShouldReturnFilteredCandidate() throws Exception {
		this.mockMvc.perform(get("/candidates?fname=Thomas"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.composers[?(@.fname != 'Thomas')]").doesNotExist());

	}

	@Test
	void getByEmailShouldReturnFilteredCandidate() throws Exception {
		this.mockMvc.perform(get("/candidates?fname=thomas.y2022@gmail.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.composers[?(@.fname != 'thomas.y2022@gmail.com')]").doesNotExist());

	}

	@Test
	void getOneShouldReturnFirstCandidate() throws Exception {
		this.mockMvc.perform(get("/candidates/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fname").value("Thomas"))
				.andExpect(jsonPath("$.lname").value("Yang"))
				.andExpect(jsonPath("$.email").value("thomas.y2022@gmail.com"));
	}

	@Test
	void getOneShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(get("/candidates/99"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	void createEmployeeShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(
				post("/candidates")
						.content("{ \"fname\":\"xxx\", \"lname\":\"yyy\", \"email\":\"xxx.yyy@gmail.com\"}")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fname").value("xxx"))
				.andExpect(jsonPath("$.lname").value("yyy"))
				.andExpect(jsonPath("$.email").value("xxx.yyy@gmail.com"))
				.andExpect(jsonPath("$.id").exists());
	}

	@Test
	void getByScoreShouldReturnFiltered() throws Exception {
		this.mockMvc.perform(get("/candidates/scoregreater/70"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.composers[?(@.score < 70)]").doesNotExist());
	}

	@Test
	void updateEmployeeShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/candidates/3"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fname").value("Demo"))
				.andExpect(jsonPath("$.lname").value("Yang"))
				.andExpect(jsonPath("$.email").value("demo.yang@gmail.com"));

		this.mockMvc.perform(
				put("/candidates/3")
						.content("{ \"fname\":\"mmm\", \"lname\":\"nnn\", \"email\":\"mmm.nnn@gmail.com\"}")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fname").value("mmm"))
				.andExpect(jsonPath("$.lname").value("nnn"))
				.andExpect(jsonPath("$.email").value("mmm.nnn@gmail.com"));

	}

	@Test
	void shouldDelete() throws Exception {
		this.mockMvc.perform(delete("/candidates/4"))
				.andDo(print())
				.andExpect(status().isNoContent());

		this.mockMvc.perform(get("/candidates/4"))
				.andDo(print())
				.andExpect(status().isNotFound());

	}

}
