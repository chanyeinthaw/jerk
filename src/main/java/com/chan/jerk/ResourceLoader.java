package com.chan.jerk;

import java.net.URL;

public class ResourceLoader {
    public static URL getResource(String path) {
        return Thread.currentThread().getContextClassLoader().getResource(path);
    }
}
