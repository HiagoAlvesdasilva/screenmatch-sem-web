package br.com.hiago.screematch.model.enums;

public enum Categoria {
    ACAO("Action"),

    ROMANCE("Romance"),

    COMEDIA("Comedy"),

    DRAMA("Drama"),

    CRIME("Crime"),

    ANIMACAO("Animation"),

    AVENTURA("Adventure");
    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString (String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria foi encontrada para a série! ");
    }
}
