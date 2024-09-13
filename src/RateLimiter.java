public interface RateLimiter {
    boolean allowRequest(String id);
}
