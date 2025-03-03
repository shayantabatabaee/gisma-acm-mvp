package com.gisma.competition.acm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisma.competition.acm.api.dto.SubmitCompetitionResponseDto;
import com.gisma.competition.acm.api.exception.CompetitionPoolBusyException;
import com.gisma.competition.acm.api.exception.CompilationException;
import com.gisma.competition.acm.api.exception.SubmissionExecutionTimeoutException;
import com.gisma.competition.acm.persistence.entity.Competition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

@Component
public class TestCaseExecutorProcessManager {

    public static final String EXECUTOR_JAR_PATH = getExecutorJarPath();
    private final ThreadPoolExecutor executorService;
    private final Integer timeout;

    public TestCaseExecutorProcessManager(@Value("${executor.max-threads}") Integer maxThreads,
                                          @Value("${executor.timeout}") Integer timeout) {
        this.timeout = timeout;
        this.executorService = new ThreadPoolExecutor(maxThreads, maxThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(maxThreads),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public SubmitCompetitionResponseDto execute(Competition competition, String code)
            throws CompilationException, CompetitionPoolBusyException, SubmissionExecutionTimeoutException {
        Future<SubmitCompetitionResponseDto> future;

        try {
            future = this.executorService.submit(() -> executeOnProcess(competition, code));
        } catch (RejectedExecutionException e) {
            throw new CompetitionPoolBusyException();
        }

        try {
            return future.get(timeout + 5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new SubmissionExecutionTimeoutException(timeout);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            if (e.getCause() instanceof CompilationException) {
                throw (CompilationException) e.getCause();
            } else if (e.getCause() instanceof SubmissionExecutionTimeoutException) {
                throw (SubmissionExecutionTimeoutException) e.getCause();
            }
            throw new CompilationException(e.getCause());
        }
    }

    public SubmitCompetitionResponseDto executeOnProcess(Competition competition, String code)
            throws IOException, InterruptedException, CompilationException, SubmissionExecutionTimeoutException {

        ObjectMapper objectMapper = new ObjectMapper();
        String competitionJson = objectMapper.writeValueAsString(competition);

        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-jar", EXECUTOR_JAR_PATH, competitionJson, code);

        Process process = processBuilder.start();
        boolean isFinished = process.waitFor(timeout, TimeUnit.SECONDS);

        if (!isFinished) {
            process.destroy();
            throw new SubmissionExecutionTimeoutException(timeout);
        }

        int exitCode = process.exitValue();
        if (exitCode == 0) {
            return objectMapper.readValue(process.getInputStream(), SubmitCompetitionResponseDto.class);
        } else {
            if (process.getInputStream().available() > 0)
                throw objectMapper.readValue(process.getInputStream(), CompilationException.class);
            else
                throw new RuntimeException(readError(process));
        }
    }

    private static String readError(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder error = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            error.append(line).append("\n");
        }
        return error.toString();
    }

    private static String getExecutorJarPath() {
        String localPath = "libs/gisma-acm-executor.jar";
        String ideaPath = "gisma-acm-server/libs/gisma-acm-executor.jar";
        String dockerPath = "/app/libs/gisma-acm-executor.jar";

        File localFile = new File(localPath);
        if (!localFile.exists()) {
            localFile = new File(ideaPath);
        }
        return localFile.exists() ? localFile.getAbsolutePath() : dockerPath;
    }
}
