package br.com.hiago.screematch.model.enums;

public enum Categoria {
    ACAO("Action", "Ação"),

    ROMANCE("Romance", "Romance"),

    COMEDIA("Comedy", "Comédia"),

    DRAMA("Drama", "Drama"),

    CRIME("Crime", "Crime"),

    ANIMACAO("Animation", "Animação"),

    AVENTURA("Adventure", "Aventura");

    private String categoriaOmdb;

    private String categoriaPortuges;

    Categoria(String categoriaOmdb, String categoriaPortuges) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortuges = categoriaPortuges;
    }

    public static Categoria fromString (String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria foi encontrada para a série! ");
    }

    public static Categoria fromStringPortuges (String text){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaPortuges.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria foi encontrada para a série! ");
    }
}
