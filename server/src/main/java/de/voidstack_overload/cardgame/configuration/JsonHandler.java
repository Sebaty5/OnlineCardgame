package de.voidstack_overload.cardgame.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public abstract class JsonHandler<T> {
    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    protected final Path file;
    protected final Class<T> dataClass;
    protected final Supplier<T> dataFactory;
    protected final AtomicBoolean needsSaving;
    protected final ReentrantReadWriteLock lock;
    protected final ReentrantReadWriteLock.ReadLock readLock;
    protected final ReentrantReadWriteLock.WriteLock writeLock;
    protected final long saveDelay;

    private boolean init = false;
    private T data = null;

    protected JsonHandler(Path filePath, long saveDelay, Class<T> dataClass, Supplier<T> dataFactory) throws IOException {
        this.file = filePath.toFile().getCanonicalFile().toPath();
        this.dataFactory = dataFactory;
        this.saveDelay = saveDelay;
        this.dataClass = dataClass;
        this.lock = new ReentrantReadWriteLock();
        this.readLock = this.lock.readLock();
        this.writeLock = this.lock.writeLock();
        this.needsSaving = new AtomicBoolean(false);
    }

    private void saveCheck() {
        while (true) {
            if (this.needsSaving.get()) forceSave();
            try {
                Thread.sleep(this.saveDelay);
            } catch (InterruptedException ex) {
                // NO-OP
            }
        }
    }

    public void init() throws IOException {
        try {
            this.writeLock.lock();
            if (this.init) {
                throw new IllegalStateException("Init was already called");
            }
            this.init = true;
            File file = new File(this.file.toString());
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            Files.createDirectories(this.file.getParent());

            this.data = readFile(this.file, this.dataClass).orElseGet(() -> {
                this.needsSaving.set(true);
                return this.dataFactory.get();
            });

            Thread saveChecker = new Thread(this::saveCheck);
            saveChecker.start();
        } finally {
            this.writeLock.unlock();
        }
    }

    public void scheduleSave() {
        this.needsSaving.set(true);
    }

    public void forceSave() {
        try {
            this.needsSaving.set(false);
            this.saveFile();
        } catch (IOException ex) {
            System.exit(2);
        }
    }

    protected T getData() {
        if (this.data == null) {
            System.exit(2);
        }
        return this.data;
    }

    private void saveFile() throws IOException {
        try {
            this.writeLock.lock();
            writeFile(this.file, this.getData());
        } finally {
            this.writeLock.unlock();
        }
    }

    private static <T> Optional<T> readFile(Path file, Class<T> dataClass) {
        if (!Files.isRegularFile(file))
            return Optional.empty();

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return Optional.of(GSON.fromJson(reader, dataClass));
        } catch (Exception ex) {
            System.exit(2);
            return Optional.empty();
        }
    }

    private static <T> void writeFile(Path file, T config) {
        try {
            Files.createDirectories(file.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                GSON.toJson(config, writer);
            }
        } catch (Exception ex) {
            System.exit(2);
        }
    }

}
