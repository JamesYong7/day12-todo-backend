import com.example.demo.Entity.Todo;
import com.example.demo.Repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.not;

@SpringBootTest(classes = com.example.demo.TodoApplication.class)
@AutoConfigureMockMvc
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    void cleanUp() {
        todoRepository.deleteAll();
    }

    @Test
    void should_response_empty_list_when_index_with_no_todo() throws Exception {
        MockHttpServletRequestBuilder request = get("/todos")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_response_one_todo_when_index_with_one_todo() throws Exception {
        Todo todo = new Todo(null, "Buy Milk", false);
        todoRepository.save(todo);

        MockHttpServletRequestBuilder request = get("/todos")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].text").value("Buy Milk"))
                .andExpect(jsonPath("$[0].done").value(false));
    }

    @Test
    void should_response_todo_when_create_with_todo() throws Exception {
        String json = """
                {
                    "text": "Buy Milk",
                    "done": false
                }
                """;
        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").value("Buy Milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_response_422_when_create_with_empty_todo() throws Exception {
        String json = """
                {
                    "text": "",
                    "done": false
                }
                """;
        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(422));
    }

    @Test
    void should_response_422_when_create_with_no_text_todo() throws Exception {
        String json = """
                {
                    "done": false
                }
                """;
        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(422));
    }

    @Test
    void should_return_todo_with_generated_id_when_create_with_todo_with_id() throws Exception {
        String json = """
                {
                    "id": 100,
                    "text": "Buy Milk",
                    "done": false
                }
                """;
        MockHttpServletRequestBuilder request = post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(not(100)))
                .andExpect(jsonPath("$.text").value("Buy Milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_update_todo_with_response_200_when_update_with_todo() throws Exception {
        Todo todo = new Todo(null, "Buy Milk", false);
        Todo savedTodo = todoRepository.save(todo);

        String json = """
                {
                    "text": "Buy Bread",
                    "done": true
                }
                """;
        MockHttpServletRequestBuilder request = put("/todos/" + savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(savedTodo.getId()))
                .andExpect(jsonPath("$.text").value("Buy Bread"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_update_todo_with_response_200_when_update_with_todo_and_different_id_in_request() throws Exception {
        Todo todo = new Todo(null, "Buy Milk", false);
        Todo savedTodo = todoRepository.save(todo);

        String json = """
                {
                    "id": "1",
                    "text": "Buy Bread",
                    "done": true
                }
                """;
        MockHttpServletRequestBuilder request = put("/todos/" + savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(savedTodo.getId()))
                .andExpect(jsonPath("$.text").value("Buy Bread"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_response_422_when_update_with_empty_todo() throws Exception {
        Todo todo = new Todo(null, "Buy Milk", false);
        Todo savedTodo = todoRepository.save(todo);

        String json = """
                {
                }
                """;
        MockHttpServletRequestBuilder request = put("/todos/" + savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(422));
    }

    @Test
    void should_return_404_when_update_with_not_exist_id() throws Exception {
        String json = """
                {
                    "text": "Buy Bread",
                    "done": true
                }
                """;
        MockHttpServletRequestBuilder request = put("/todos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().is(404));
    }

    @Test
    void should_response_204_when_delete_with_exist_id() throws Exception {
        Todo todo = new Todo(null, "Buy Milk", false);
        Todo savedTodo = todoRepository.save(todo);

        MockHttpServletRequestBuilder request = delete("/todos/" + savedTodo.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().is(204));
    }

    @Test
    void should_response_404_when_delete_with_not_exist_id() throws Exception {
        MockHttpServletRequestBuilder request = delete("/todos/999")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().is(404));
    }
}
