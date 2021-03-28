package com.github.zeldigas.rtlogs;

interface LibraryLoggerFactory {
    static LibraryLoggerFactory create(boolean debug) {
        return (clazz -> new StdoutLogger(debug, clazz.getSimpleName()));
    }

    LibraryLogger get(Class<?> clazz);
}
