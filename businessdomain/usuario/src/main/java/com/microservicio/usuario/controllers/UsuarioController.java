package com.microservicio.usuario.controllers;

import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import com.commons.utils.constants.Messages;
import com.commons.utils.errors.DataAccessEmptyWarning;
import com.commons.utils.errors.UserNotFoundWarning;
import com.commons.utils.models.dto.UsuarioDto;
import com.commons.utils.models.entities.Usuario;
import com.commons.utils.utils.Response;
import com.google.common.base.Strings;
import com.microservicio.usuario.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin(origins = { "*" })
public class UsuarioController {

   @Autowired
   private UsuarioService service;

   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @GetMapping(path = "/findAll")
   public ResponseEntity<?> findAll() {
      List<Usuario> usuarioDb = this.service.findAll();
      if(usuarioDb.size() == 0) throw new DataAccessEmptyWarning();

      return ResponseEntity.ok().body(
                                    Response
                                       .builder()
                                       .message(Messages.MESSAGE_SUCCESS_LIST_ENTITY)
                                       .data(usuarioDb)
                                       .build());
   }

   @PostMapping(path = "/createAccount")
   public ResponseEntity<?> createAccount(@RequestBody Usuario newUser) {
      newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
      Usuario userCreated = service.save(newUser);
      UsuarioDto userDto = new ModelMapper().map(userCreated, UsuarioDto.class);

      return ResponseEntity.ok().body(
         Response
            .builder()
            .message(Messages.MESSAGE_SUCCESS_CREATE_USER(newUser.getLogin()))
            .data(Arrays.asList(userDto))
            .build()
      );
   }

   @PutMapping(path = { "/updateAccount" })
   @ResponseStatus(value = HttpStatus.OK)
   public Response<UsuarioDto> updateAccount(@RequestBody UsuarioDto user) {

      // ► Repo dep's ...
      Usuario userUpdated = this.service.findByLogin(user.getLogin())
                                        .orElseThrow(() -> new UserNotFoundWarning(user.getLogin()));

      // ► ...
      if (!Strings.isNullOrEmpty(user.getPassword())) {
         userUpdated.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      } else if (!Strings.isNullOrEmpty(user.getFoto())) {
         userUpdated.setFoto(user.getFoto());
      }

      // ► Save ...
      this.service.save(userUpdated);

      return Response
               .<UsuarioDto>builder()
               .message(Messages.MESSAGE_SUCCESS_UPDATE_USER(user.getLogin()))
               .data(new ModelMapper().map(userUpdated, UsuarioDto.class))
               .build();
   }
   
   @GetMapping(path = "/findByLogin/{login}")
   @ResponseStatus(code = HttpStatus.OK)
   public Response<Usuario> findByLogin(@PathVariable String login) {
      Usuario usuario = service.findByLogin(login).orElseThrow(() -> new UserNotFoundWarning(login));

      return Response
               .<Usuario>builder()
               .data(usuario)
               .build();
      }

   @GetMapping(path="/findByUserAuth/{userAuth}")
   public ResponseEntity<?> findByUserAuth(@PathVariable String userAuth) {
      Usuario user = this.service.findByLogin(userAuth)
                                    .orElseThrow(() -> new UserNotFoundWarning(userAuth));
      return ResponseEntity.ok().body(user);
   }
      
   @GetMapping(path = "/toListProperties")
   public String toListProperties(@Value("${spring.profiles.active}") String profile) {
         return profile;
   }
      
   }