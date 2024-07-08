package com.forohub.domain.topico;

import com.forohub.domain.curso.Curso;
import com.forohub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosListadoTopico(String titulo,
                                 String mensaje,
                                 LocalDateTime fechaCreacion,
                                 Status status,
                                 Usuario autor,
                                 Curso curso) {
    public DatosListadoTopico(Topico topico) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), topico.getAutor(), topico.getCurso());
    }
}
