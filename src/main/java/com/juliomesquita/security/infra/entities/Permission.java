package com.juliomesquita.security.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum Permission {

    TEST1_READ("Test1 Leitura"),
    TEST1_WRITE("Test1 Escrita"),
    TEST1_UPDATE("Test1 Atualizar"),
    TEST1_DELETE("Test1 Deletar"),
    TEST2_READ("Test2 Leitura"),
    TEST2_WRITE("Test2 Escrita"),
    TEST2_UPDATE("Test2 Atualizar"),
    TEST2_DELETE("Test2 Deletar");

    private final String description;
}
