package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

import static com.craftinginterpreters.lox.TokenType.COMMA;
import static com.craftinginterpreters.lox.TokenType.DOT;
import static com.craftinginterpreters.lox.TokenType.EOF;
import static com.craftinginterpreters.lox.TokenType.LEFT_BRACE;
import static com.craftinginterpreters.lox.TokenType.LEFT_PAREN;
import static com.craftinginterpreters.lox.TokenType.MINUS;
import static com.craftinginterpreters.lox.TokenType.PLUS;
import static com.craftinginterpreters.lox.TokenType.RIGHT_BRACE;
import static com.craftinginterpreters.lox.TokenType.RIGHT_PAREN;
import static com.craftinginterpreters.lox.TokenType.SEMICOLON;
import static com.craftinginterpreters.lox.TokenType.STAR;

class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();
  private int start = 0;
  private int current = 0;
  private int line = 1;
  
  Scanner(String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    while (!isAtEnd()) {
      // We are at the beginning of the next lexeme.
      start = current;
      scanToken();
    }

    tokens.add(new Token(EOF, "", null, line));
    return tokens;
  }  

  private void scanToken() {
    char c = advance();
    switch (c) {
      case '(': addToken(LEFT_PAREN); break;
      case ')': addToken(RIGHT_PAREN); break;
      case '{': addToken(LEFT_BRACE); break;
      case '}': addToken(RIGHT_BRACE); break;
      case ',': addToken(COMMA); break;
      case '.': addToken(DOT); break;
      case '-': addToken(MINUS); break;
      case '+': addToken(PLUS); break;
      case ';': addToken(SEMICOLON); break;
      case '*': addToken(STAR); break; 
      default:
        Lox.error(line, "Unexpected character.");
        break;      
    }
  }  

  private boolean isAtEnd() {
    return current >= source.length();
  }

  private char advance() {
    return source.charAt(current++);
  }

  private void addToken(TokenType type) {
    addToken(type, null);
  }

  private void addToken(TokenType type, Object literal) {
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }  
}