import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private Map<Class<? extends Event>, EventHandler<? extends Event>> eventHandlers;

    public EventManager() {
        eventHandlers = new HashMap<>();
    }

    public <T extends Event> void registerEventHandler(Class<T> eventType, EventHandler<T> handler) {
        eventHandlers.put(eventType, handler);
    }

    public <T extends Event> void triggerEvent(T event) {
        EventHandler<T> handler = (EventHandler<T>) eventHandlers.get(event.getClass());
        if (handler != null) {
            handler.handle(event);
        }
    }
}
