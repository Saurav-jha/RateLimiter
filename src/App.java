public class App {
    public static void main(String[] args) {
        RateLimiter fixedWindowRateLimiter = RateLimiterFactory.createRateLimiter("fixed", 8, 600000);
        System.out.println("Fixed Window Rate Limiter");
        for(int i=0; i<15; i++){
            System.out.println(fixedWindowRateLimiter.allowRequest("CLIENT"));
        }
    }
}
