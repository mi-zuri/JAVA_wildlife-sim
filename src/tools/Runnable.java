package tools;

import java.io.Serializable;

public interface Runnable<T, K> extends Serializable {
    void run(T t, K k);
}
