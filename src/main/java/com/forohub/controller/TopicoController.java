package com.forohub.controller;

import com.forohub.domain.curso.Curso;
import com.forohub.domain.curso.CursoRepository;
import com.forohub.domain.topico.*;
import com.forohub.domain.usuario.Usuario;
import com.forohub.domain.usuario.UsuarioRepository;
import com.forohub.infra.errores.ValidacionDeIntegridad;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Topico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        Optional<Usuario> autor = usuarioRepository.findById(datosRegistroTopico.autor());
        Optional<Curso> curso = cursoRepository.findById(datosRegistroTopico.curso());
        if (autor.isEmpty()) {
            throw new ValidacionDeIntegridad("el id para el autor no fue encontrado");
        }

        if (curso.isEmpty()) {
            throw new ValidacionDeIntegridad("el id para el curso no fue encontrado");
        }

        Topico topico = new Topico(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje(), autor.get(), curso.get());
        topicoRepository.save(topico);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @GetMapping
    public List<DatosListadoTopico> listadoMedicos(){
        List<DatosListadoTopico> lista = topicoRepository.findAll().stream()
                .filter(topico -> topico.getStatus() == Status.activo)
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
        if (lista.isEmpty()){
            throw new ValidacionDeIntegridad("No hay topicos registrados");
        }
        return lista;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> retornarDatosTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("El tópico no fue encontrado");
        }
        Topico topico = topicoOptional.get();
        return ResponseEntity.ok(new DatosListadoTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        Optional<Usuario> autor = usuarioRepository.findById(datosActualizarTopico.autor());
        Optional<Curso> curso = cursoRepository.findById(datosActualizarTopico.curso());
        if (autor.isPresent()) {
            topico.setAutor(autor.get());
        }
        if (curso.isPresent()) {
            topico.setCurso(curso.get());
        }

        return ResponseEntity.ok(new DatosListadoTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("El tópico no fue encontrado");
        }
        Topico topico = topicoOptional.get();
        topicoRepository.delete(topico);
    }

    @DeleteMapping("/desactivar-{id}")
    @Transactional
    public ResponseEntity desactivarTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            throw new ValidacionDeIntegridad("El tópico no fue encontrado");
        }
        Topico topico = topicoOptional.get();
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }
}
