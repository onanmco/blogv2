package com.cemonan.blog.repository;

import com.cemonan.blog.dao.UserDao;
import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.lib.redis.RedisConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.Instant;
import java.util.UUID;

@Component
public class AuthTokenRepositoryImpl implements AuthTokenRepository{

    private final UserDao userDao;

    private final RedisConnector redisConnector;

    @Value("${token.expires.after.seconds}")
    private String DEFAULT_EXPIRATION;

    @Autowired
    public AuthTokenRepositoryImpl(UserDao userDao, RedisConnector redisConnector) {
        this.userDao = userDao;
        this.redisConnector = redisConnector;
    }

    @Override
    public Token getTokenByUUID(UUID uuid) {
        Jedis connection = null;
        try {
            connection = redisConnector.getConnection();

            Token token = new Token();

            Long ttl = connection.ttl(getKeyByToken(uuid));

            if (ttl == null) {
                return null;
            }

            token.setToken(uuid);
            token.setExpiresAt(Instant.now().getEpochSecond() + ttl);

            return  token;

        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public Token create(User user) {
        Jedis connection = null;
        try {
            connection = redisConnector.getConnection();

            Token token = new Token();
            token.setToken(UUID.randomUUID());
            token.setExpiresAt(Instant.now().getEpochSecond() + Long.valueOf(DEFAULT_EXPIRATION));

            connection.setex(getKeyByToken(token), Long.valueOf(DEFAULT_EXPIRATION), user.getId().toString());

            return token;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public User getUserByToken(Token token) {
        Jedis connection = null;
        try {
            connection = redisConnector.getConnection();

            String userId = connection.get(getKeyByToken(token));
            // TODO implement UserDao
            return userDao.getById(UUID.fromString(userId));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public Token update(Token token) {
        Jedis connection = null;
        try {
            connection = redisConnector.getConnection();

            String key = getKeyByToken(token);
            User user = getUserByToken(token);
            token.setExpiresAt(Instant.now().getEpochSecond());
            connection.del(key);
            connection.setex(key, Long.valueOf(DEFAULT_EXPIRATION), user.getId().toString());
            return token;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        finally {
            closeConnection(connection);
        }
        return null;
    }

    @Override
    public void delete(Token token) {
        Jedis connection = null;
        try {
            connection = redisConnector.getConnection();

            connection.del(getKeyByToken(token));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    private void closeConnection(Jedis connection) {
        if (connection != null) {
            connection.close();
        }
    }

    private String getKeyByToken(Token token) {
        return "tokens/" + token.getToken().toString() + "/user_id";
    }

    private String getKeyByToken(UUID uuid) {
        return "tokens/" + uuid.toString() + "/user_id";
    }
}
