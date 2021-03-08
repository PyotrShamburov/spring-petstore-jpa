package by.home.service;


import by.home.model.Role;
import by.home.model.User;
import by.home.model.XToken;
import by.home.repository.XTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class XTokenService {
    @Autowired
    private XTokenRepository tokenRepository;
    @Autowired
    private UserService userService;

    public String generateToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String addToRepository(long userId) {
        XToken xToken = new XToken();
        String key = generateToken();
        xToken.setUserId(userId);
        xToken.setToken(key);
        tokenRepository.save(xToken);
        return key;
    }

    public boolean isUser(String token) {
        Optional<XToken> xToken = tokenRepository.findByToken(token);
        return xToken.isPresent();
    }

    public boolean isAdmin(String token) {
        Optional<XToken> xToken = tokenRepository.findByToken(token);
        if (xToken.isPresent()) {
            Optional<User> userById = userService.getUserById(xToken.get().getUserId());
            if (userById.isPresent()) {
                return userById.get().getRole().equals(Role.ADMIN);
            }
        }
        return false;
    }

    public boolean validToken(String token) {
       return tokenRepository.existsByToken(token);
    }
}
