package com.sushil.numgen.service;

import com.sushil.numgen.model.NumGenRequest;
import com.sushil.numgen.model.ResponseResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NumgenServiceTest {

    private NumgenService numgenService;
    private NumGenRequest numGenRequest;
    private static final String ACTUAL_NUM_LIST = "10,8,6,4,2,0";

    @Before
    public void setup() {
        numgenService = new NumgenService();
        numGenRequest = new NumGenRequest();
        numGenRequest.setStep("2");
        numGenRequest.setGoal("10");
    }


    @Test
    public void testAll() throws IOException {

        UUID uuid = numgenService.generateNumbers(numGenRequest);
        Assert.assertNotNull(uuid);

        testGetStatus(uuid);

        testGetNumList(uuid);

    }

    private void testGetStatus(UUID uuid) {
        ResponseResult status = numgenService.getStatus(uuid.toString());
        //Assert.assertEquals(Status.IN_PROGRESS.toString(), status.getResult());
        Assert.assertNotNull(status.getResult());
    }


    private void testGetNumList(UUID uuid) throws IOException {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        ResponseResult numListResult = numgenService.getNumList(uuid.toString());
        Assert.assertEquals(ACTUAL_NUM_LIST, numListResult.getResult());
    }


}
