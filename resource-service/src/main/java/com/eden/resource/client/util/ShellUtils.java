package com.eden.resource.client.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ShellUtils {

    public static String getNginxPath() {
        String cmd = "find / -name nginx.conf";
        String result = execute(cmd, "/");
        String[] paths = result.split("/n");
        return paths[0];
    }

    public static String execute(String cmd, String path) {
        return execute(cmd, new File(path));
    }

    public static String execute(String cmd, File dir) {
        StringBuilder result = new StringBuilder();

        Process process = null;
        BufferedReader bufferIn = null;
        BufferedReader bufferError = null;

        try {
            String[] command = {"/bin/sh", "-c", cmd};
            process = Runtime.getRuntime().exec(command, null, dir);
            process.waitFor();
            bufferIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufferError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            String line;
            while ((line = bufferIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufferError.readLine()) != null) {
                result.append(line).append('\n');
            }

        } catch (Exception e) {
            log.error("Execute shell exception!", e);
        } finally {
            closeStream(bufferIn);
            closeStream(bufferError);

            if (process != null) {
                process.destroy();
            }
        }

        return result.toString();
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
            // nothing
            }
        }
    }
}
