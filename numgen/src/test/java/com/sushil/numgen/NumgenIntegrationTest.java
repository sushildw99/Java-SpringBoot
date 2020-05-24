package com.sushil.numgen;

import com.sushil.numgen.model.NumGenRequest;
import com.sushil.numgen.model.ResponseResult;
import com.sushil.numgen.model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NumgenIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /*
    @InjectMocks
    private NumgenService numgenService = new NumgenService();*/

  /*  @BeforeEach
    void setMockOutput() throws IOException {
        UUID uuid = mock(UUID.class);
        NumGenRequest numGenRequest = mock(NumGenRequest.class);
        when(numGenRequest.getGoalVal()).thenReturn(10);
        when(numGenRequest.getStepVal()).thenReturn(2);
        when(numgenService.generateNumbers(numGenRequest)).thenReturn(uuid);

        ResponseResult responseResult = new ResponseResult();
        responseResult.setResult(Status.SUCCESS.toString());
        when(numgenService.getStatus(anyString())).thenReturn(responseResult);

    }
*/


    @Test
    public void testGenerarteNumber() {

        NumGenRequest numGenRequest = new NumGenRequest();
        numGenRequest.setGoal("10");
        numGenRequest.setStep("2");

        ResponseEntity<Task> response =
                this.restTemplate.postForEntity("http://localhost:" + port + "/api/generate/", numGenRequest, Task.class);

        Task actual = response.getBody();
        Assert.assertNotNull(actual.getTask());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));
    }

    @Test
    public void testGetStatus() {

        ResponseEntity<ResponseResult> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/tasks/911a47a6-722c-4666-9b94-90c2c6a39169/status/", ResponseResult.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }


    @Test
    public void testGetNumList() {

        ResponseEntity<ResponseResult> response =
                this.restTemplate.getForEntity("http://localhost:" + port + "/api/tasks/911a47a6-722c-4666-9b94-90c2c6a39169?action=get_numlist", ResponseResult.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}

