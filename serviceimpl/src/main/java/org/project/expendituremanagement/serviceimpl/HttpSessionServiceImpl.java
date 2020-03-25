package org.project.expendituremanagement.serviceimpl;

import org.project.expendituremanagement.dto.HttpSession;
import org.project.expendituremanagement.dto.ValidateSessionResponse;
import org.project.expendituremanagement.serviceinterface.HttpSessionService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HttpSessionServiceImpl implements HttpSessionService {

    private RedisTemplate<String,HttpSession> redisTemplate;
    private HashOperations hashOperations;
    private final String REDIS_INDEX="HttpSession";

    public HttpSessionServiceImpl(RedisTemplate<String,HttpSession> redisTemplate){
        this.redisTemplate=redisTemplate;
        this.hashOperations=this.redisTemplate.opsForHash();
    }

    @Override
    public UUID createHttpSession(String userId) {
        HttpSession httpSession=new HttpSession();
        httpSession.setUserId(userId);
        httpSession.setSessionId(UUID.randomUUID());
        httpSession.setSessionActiveOrNot(true);

        hashOperations.put(REDIS_INDEX,httpSession.getUserId(),httpSession);

        return httpSession.getSessionId();
    }

    @Override
    public ValidateSessionResponse validateSession(HttpSession httpSession) {
        System.out.println("request :"+httpSession);
        ValidateSessionResponse validateSessionResponse=new ValidateSessionResponse();

        HttpSession redisResponse=getHttpSession(httpSession.getUserId());
        System.out.println("redis:"+redisResponse);
        if(redisResponse!=null){
            //session for user is present
            if(httpSession.getSessionId().toString().equals(redisResponse.getSessionId().toString())){
                //session is matched
                if(redisResponse.getSessionActiveOrNot()){
                    validateSessionResponse.setSessionActiveOrNot(true);
                    validateSessionResponse.setMessage("success");
                }
                else{
                    validateSessionResponse.setSessionActiveOrNot(false);
                    validateSessionResponse.setMessage("Session Expired");
                }
            }
            else {
                validateSessionResponse.setSessionActiveOrNot(false);
                validateSessionResponse.setMessage("Session Expired");
            }
        }
        else {
            validateSessionResponse.setSessionActiveOrNot(false);
            validateSessionResponse.setMessage("Please Sign In first");
        }

        System.out.println("response :"+validateSessionResponse);
        return validateSessionResponse;
    }
    private HttpSession getHttpSession(String userId) {
        return (HttpSession) hashOperations.get(REDIS_INDEX,userId);
    }

    @Override
    public boolean updateHttpSession(String userId) {
        HttpSession redisResponse=getHttpSession(userId);

        if(redisResponse!=null) {
            redisResponse.setSessionActiveOrNot(false);
            hashOperations.put(REDIS_INDEX, userId, redisResponse);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteHttpSession(HttpSession httpSession) {
        if(validateSession(httpSession).getSessionActiveOrNot()) {
            hashOperations.delete(REDIS_INDEX, httpSession.getUserId());
            return true;
        }
        return false;
    }
}
