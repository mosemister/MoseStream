package org.mosestream.stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StreamException extends RuntimeException {

    private final @Nullable Object value;

    public StreamException(@NotNull Throwable throwable) {
        super("A exception occurred when processing unknown", throwable);
        this.value = null;
    }

    public StreamException(@NotNull Object value, @NotNull Throwable throwable) {
        super("A exception occurred when processing " + value, throwable);
        this.value = value;
    }

    public Optional<Object> value() {
        return Optional.ofNullable(this.value);
    }
}
