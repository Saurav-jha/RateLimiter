public class RateLimiterFactory {
    public static RateLimiter createRateLimiter(String type, int maxRequest , long windowSizeInMillis){
        switch (type.toLowerCase()) {
            case "fixed":
                return new FixedWindowRateLimiter(maxRequest, windowSizeInMillis);
            default:
                throw new IllegalArgumentException("Unknown rate limiter type");
        }
    }
}
