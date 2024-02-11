package org.mosestream.iterator;

import org.jetbrains.annotations.ApiStatus;
import org.mosestream.AbstractStream;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.stream.AbstractActionStream;
import org.mosestream.base.AbstractBaseStream;
import org.mosestream.lamda.ThrowableSingleton;

import java.util.LinkedList;
import java.util.List;

@ApiStatus.Internal
public class MoseStreamIterator<Value> implements ThrowableIterator<Value> {

    private final List<AbstractStream<?, ?>> all = new LinkedList<>();
    private final ThrowableSingleton<ThrowableIterator<?>, ?> iterator;

    private boolean hasCached;
    private Object cached;
    private Throwable cachedException;

    public MoseStreamIterator(AbstractStream<Value, ?> original) {
        AbstractStream<?, ?> stream = original;
        do {
            all.add(stream);
            if (stream instanceof AbstractActionStream) {
                stream = ((AbstractActionStream<?, ?>) stream).parent();
                continue;
            }
            break;
        } while (stream instanceof AbstractBaseStream || stream.preAction() != null);
        AbstractStream<?, ?> finalStream = stream;
        if (stream.preAction() != null && stream instanceof AbstractBaseStream) {
            this.iterator = new ThrowableSingleton<>(() -> getPreBase(finalStream));
        } else {
            this.iterator = new ThrowableSingleton<>(finalStream::iterator);
        }
        all.remove(0);
        if (stream.preAction() != null) {
            all.remove(stream);
        }
    }

    private <A, M> ThrowableIterator<M> getPreBase(AbstractStream<A, ?> stream) throws Throwable {
        ThrowableIterator<A> iterator = stream.iterator();
        Iterable<M> mapped = (Iterable<M>) stream.preAction().apply(iterator).result();
        return new ThrowableIteratorWrapper<>(mapped.iterator());
    }

    private void cache() {
        if (this.hasCached) {
            return;
        }
        this.hasCached = true;
        ThrowableIterator<?> current;
        try {
            current = this.iterator.get();
        } catch (Throwable e) {
            this.cachedException = e;
            return;
        }
        while (current.hasNext()) {
            try {
                Object value = current.next();
                if (cache(value)) {
                    break;
                }
            } catch (Throwable e) {
                this.cachedException = e;
            }
        }
    }

    private boolean cache(Object value) {
        for (int i = all.size() - 1; i >= 0; i--) {
            StreamAction<?, ?, ?> action = all.get(i).action();
            if (action == null) {
                throw new RuntimeException("Action '" + i + "' did not have a action");
            }
            try {
                StreamActionResult<?> result = apply(action, value);
                if (result.shouldConsume()) {
                    return false;
                }
                value = result.result();
            } catch (Throwable e) {
                cachedException = e;
                return true;
            }
        }
        cached = value;
        return true;
    }

    private <V, T extends Throwable> StreamActionResult<?> apply(StreamAction<V, ?, T> action, Object value) throws T {
        return action.apply((V) value);
    }

    public boolean hasNext() {
        if (!hasCached) {
            cache();
        }
        if (cached != null) {
            return true;
        }
        return cachedException != null;

    }

    public Value next() throws Throwable {
        if (!this.hasCached) {
            cache();
        }
        this.hasCached = false;
        if (this.cachedException != null) {
            Throwable throwable = cachedException;
            this.cachedException = null;
            throw throwable;
        }

        Value value = (Value) this.cached;
        this.cached = null;
        return value;
    }
}
