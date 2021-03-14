package by.home.service;

import by.home.entity.Role;
import by.home.entity.User;
import by.home.entity.XToken;
import by.home.repository.XTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class XTokenService {
    @Autowired
    private XTokenRepository tokenRepository;
    @Autowired
    private UserService userService;

    public String generateToken() {
        UUID uuid = UUID.randomUUID();
        log.info("New xtoken has been generated "+uuid.toString()+".");
        return uuid.toString();
    }

    public String addToRepository(long userId) {
        XToken xToken = new XToken();
        String key = generateToken();
        xToken.setUserId(userId);
        xToken.setToken(key);
        tokenRepository.save(xToken);
        log.info("Xtoken has been added to database. Key = "+key+".");
        return key;
    }

    public boolean isUser(String token) {
        Optional<XToken> xToken = tokenRepository.findByToken(token);
        log.info("Is user check by token! Token ="+token+".");
        return xToken.isPresent();
    }

    public boolean isAdmin(String token) {
        log.info("Is admin check by token! Token ="+token+".");
        Optional<XToken> xToken = tokenRepository.findByToken(token);
        if (xToken.isPresent()) {
            Optional<User> userById = userService.getUserById(xToken.get().getUserId());
            if (userById.isPresent()) {
                return userById.get().getRole().equals(Role.ADMIN);
            }
        }
        log.error("Token not found! Token ="+token+".");
        return false;
    }

    public boolean validToken(String token) {
        log.info("Token validation check! Token ="+token+".");
       return tokenRepository.existsByToken(token);
    }
}
