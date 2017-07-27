package io.pivotal.project

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.transaction.annotation.Transactional

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProjectControllerIntegrationTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Test
    @Throws(Exception::class)
    fun gettingProjectById_ReturnsTheProject_GivenThereExistsAMatchingProject() {
        val mvcResult = this.mockMvc!!.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"project\": {\n" +
                        "    \"name\": \"ProjectControllerIntegrationTest Project\",\n" +
                        "    \"start_date\": \"2017-03-13\",\n" +
                        "    \"hourly_rate\": 11,\n" +
                        "    \"budget\": 1111\n" +
                        "  }\n" +
                        "}")
        ).andReturn()

        val project: Project = objectMapper!!.readValue<ProjectApiRequestResponseWrapper>(mvcResult.response.contentAsString, ProjectApiRequestResponseWrapper::class.java!!).project ?: throw RuntimeException("what the heck?")
        val projectId = project.id

        this.mockMvc.perform(get("/projects/{projectId}", projectId))
                .andExpect(jsonPath("$.project.id").value(projectId))
                .andExpect(jsonPath("$.project.name").value("ProjectControllerIntegrationTest Project"))
                .andExpect(jsonPath("$.project.start_date").value("2017-03-13"))
                .andExpect(jsonPath("$.project.hourly_rate").value(11))
                .andExpect(jsonPath("$.project.budget").value(1111))
    }

    @Test
    fun projectObject_serializationTest() {
        val mvcResult = this.mockMvc!!.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"project\": {\n" +
                        "    \"name\": \"Serialization Test Project\",\n" +
                        "    \"start_date\": \"2000-01-10\",\n" +
                        "    \"hourly_rate\": 50,\n" +
                        "    \"budget\": 75000\n" +
                        "  }\n" +
                        "}")
        ).andReturn()

        val responseAsString = mvcResult.response.contentAsString

        JSONAssert.assertEquals("{\n" +
                "  \"project\": {\n" +
                "    \"name\": \"Serialization Test Project\",\n" +
                "    \"start_date\": \"2000-01-10\",\n" +
                "    \"hourly_rate\": 50,\n" +
                "    \"budget\": 75000\n" +
                "  }\n" +
                "}", responseAsString, false)
    }
}