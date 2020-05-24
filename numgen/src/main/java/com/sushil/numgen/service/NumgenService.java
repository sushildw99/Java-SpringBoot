package com.sushil.numgen.service;

import com.sushil.numgen.model.NumGenRequest;
import com.sushil.numgen.model.ResponseResult;
import com.sushil.numgen.util.SingleThreadPool;
import com.sushil.numgen.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sushil.numgen.util.Constants.OUTPUT_FILE_PATH;

@Service
public class NumgenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumgenService.class);
    private static final Map<String, String> LOOKUP = new ConcurrentHashMap<>();

    /**
     * This method spawns new thread to write numbers into a file.
     *
     * @param request
     * @return
     * @throws UncheckedIOException
     */
    public UUID generateNumbers(NumGenRequest request) throws UncheckedIOException {
        UUID uuid = UUID.randomUUID();
        LOOKUP.put(uuid.toString(), Status.IN_PROGRESS.toString());
        generateNumbersAsynchronously(request, uuid);
        return uuid;

    }

    /**
     *
     * @param request
     * @param uuid
     * @throws UncheckedIOException
     */
    private void generateNumbersAsynchronously(NumGenRequest request, UUID uuid) throws UncheckedIOException {
        SingleThreadPool.INSTANCE.getInstance().submit(() -> {
            try {
                writeInFile(request, uuid);
            } catch (IOException e) {
                LOGGER.error("There is an error while writing to a file: {}", e);
                throw new UncheckedIOException(e);
            }
        });

    }

    /**
     *
     * @param request
     * @param uuid
     * @throws IOException
     */
    private void writeInFile(NumGenRequest request, UUID uuid) throws IOException {
        int goal = request.getGoalVal();
        int step = request.getStepVal();
        List<String> nums = new ArrayList<>();
        while (goal >= 0) {
            nums.add(String.valueOf(goal));
            goal = goal - step;
        }

        LOGGER.debug("Writing to file with UUID: {}", uuid.toString());
        Files.write(Paths.get(getFilePath(uuid.toString())),
                nums.stream().collect(Collectors.joining(",")).getBytes(),
                StandardOpenOption.CREATE_NEW);

        LOGGER.debug("Writing to file is completed and IN_PROGRESS status is going to be removed from Lookup for UUID: {}", uuid.toString());
        LOOKUP.remove(uuid.toString());

    }

    /**
     *
     * This method return the current status of corresponding file, if write operation is completed then it returns SUCCESS,
     * if still it is being written then returns IN_PROGRESS otherwise ERROR.
     * @param uuid
     * @return
     */
    public ResponseResult getStatus(String uuid) {
        File file = getFile(uuid);
        ResponseResult response = new ResponseResult();
        if (LOOKUP.containsKey(uuid)) {
            LOGGER.debug("UUID is found in LOOKUP, Status in IN_PROGRESS state.");
            response.setResult(Status.IN_PROGRESS.toString());
        } else if (file.exists()) {
            LOGGER.debug("File is found, Status is in SUCCESS state.");
            response.setResult(Status.SUCCESS.toString());

        } else {
            LOGGER.debug("Neither IN_PROGRESS nor SUCCESS state, so setting status ERROR.");
            response.setResult(Status.ERROR.toString());
        }
        return response;
    }


    /**
     * This method read the numbers in corresponding file.
     * @param uuid
     * @return
     * @throws IOException
     */
    public ResponseResult getNumList(String uuid) throws IOException {
        ResponseResult response = new ResponseResult();
        if (LOOKUP.containsKey(uuid)) {
            response.setResult(String.format("Processing of the file %s is still in progress.", getFilePath(uuid)));
        } else if (getFile(uuid).exists()) {
            StringBuilder builder = new StringBuilder();
            try (Stream<String> lines = Files.lines(Paths.get(getFilePath(uuid)))) {
                lines.forEach(l -> builder.append(l));
            }
            response.setResult(builder.toString());
        } else {
            response.setResult(String.format("Corresponding file %s is not available.", getFilePath(uuid)));
        }

        return response;
    }


    /**
     *
     * @param uuid
     * @return
     */
    private File getFile(String uuid) {
        return new File(getFilePath(uuid));
    }

    /**
     *
     * @param uuid
     * @return
     */
    private String getFilePath(String uuid) {
        return String.format(OUTPUT_FILE_PATH, uuid);
    }

}
