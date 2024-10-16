package org.example.DevSync1.service;

import org.example.DevSync1.entity.Token;
import org.example.DevSync1.repository.TokenRepository;

import java.util.List;
import java.util.Optional;

public class TokenService {

    private final TokenRepository tokenRepository = new TokenRepository();

    public Token save(Token token) {
        return tokenRepository.save(token);
    }


    public boolean update(Token token) {
        return tokenRepository.update(token);
    }

    public boolean delete(Token token) {
        return tokenRepository.delete(token);
    }

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }

    public Optional<Token> findById(Long id) {
        return tokenRepository.findById(id);
    }

    public Optional<Token> findByUserId(Long id) {
        return tokenRepository.findByUserId(id);
    }

}