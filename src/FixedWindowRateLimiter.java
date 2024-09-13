import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowRateLimiter implements RateLimiter{
    private final int maxRequest;
    private final long windowSizeInMillis;
    private ConcurrentHashMap<String,Integer> requestCounts;
    private ConcurrentHashMap<String, Long> windowStartTimes;
    private final Lock lock = new ReentrantLock();

    public FixedWindowRateLimiter(int maxRequest, long windowSizeInMillis){
        this.maxRequest = maxRequest;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requestCounts = new ConcurrentHashMap<>();
        this.windowStartTimes = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String Id){
        lock.lock();
        try{
            long currentTime = System.currentTimeMillis();
            windowStartTimes.putIfAbsent(Id, currentTime);
            requestCounts.putIfAbsent(Id, 0);
            long windowStartTime = windowStartTimes.get(Id);
            if(currentTime - windowStartTime >= windowSizeInMillis){
                windowStartTimes.put(Id, currentTime);
                requestCounts.put(Id,1);
                return true;
            }
            int requestCount = requestCounts.get(Id);
            if(requestCount < maxRequest){
                requestCounts.put(Id, requestCount+1);
                return true;
            }
            return false;
        }
        finally{
            lock.unlock();
        }
    }
}
