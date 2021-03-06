package by.home.service;

import by.home.model.XToken;
import by.home.repository.XTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XTokenService {
    @Autowired
    private XTokenRepository tokenRepository;

    public void addToRepository(XToken token) {
        tokenRepository.save(token);
    }

    public boolean validToken(String token) {
       return tokenRepository.existsByToken(token);
    }
}
