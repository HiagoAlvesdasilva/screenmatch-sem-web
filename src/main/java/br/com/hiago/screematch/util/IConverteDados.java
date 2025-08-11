package br.com.hiago.screematch.util;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
