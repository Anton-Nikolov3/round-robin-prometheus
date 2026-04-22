import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RoundRobinService {

    private final String[] urls = {
        "https://jsonplaceholder.typicode.com/todos",
        "https://dummyjson.com/todos"
    };

    private final AtomicInteger counter = new AtomicInteger(0);

    public String getNextUrl() {
        int index = Math.abs(counter.getAndIncrement() % urls.length);
        return urls[index];
    }
}
