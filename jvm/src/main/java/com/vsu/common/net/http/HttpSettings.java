package com.vsu.common.net.http;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Server settings as configured by a YAML file.
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 * @author Victor Su
 */
public class HttpSettings {

    /**
     * Host to bind the server to. Defaults to auto-detection.
     */
    public String host = null;

    /**
     * Port to bind the server to.  Defaults to 80.
     */
    public int port = 80;

    /**
     * Size of the worker thread pool.   Defaults to 16.
     */
    public int threadPoolWorker = 16;

    /**
     * Size of the boss thread pool.  Defaults to 1.
     */
    public int threadPoolBoss = 1;


    public static HttpSettings read(final String file) {
        try {
            final InputStream input = new FileInputStream(new File(file));
            return read(input);
        } catch (Exception ex) {
            return null;
        }
    }

    public static HttpSettings read(final InputStream stream) {
        if (stream == null) {
            return null;
        }

        try {
            final Constructor constructor = new Constructor(HttpSettings.class);
            final TypeDescription settingsDescription = new TypeDescription(HttpSettings.class);
            constructor.addTypeDescription(settingsDescription);

            final Yaml yaml = new Yaml(constructor);

            return yaml.loadAs(stream, HttpSettings.class);
        } catch (Exception e) {
            return null;
        }
    }

}
