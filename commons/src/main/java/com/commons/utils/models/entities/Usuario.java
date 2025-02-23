package com.commons.utils.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import com.commons.utils.models.enums.RimGrupo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "SidUsuario")
@Data
@Builder(builderClassName = "UsuarioBuilder", builderMethodName = "of", buildMethodName = "get")
@AllArgsConstructor
@EqualsAndHashCode(of = { "idUsuario" })
public class Usuario implements Serializable {

   @Id
   @GeneratedValue(generator = "my-uuid")
   @GenericGenerator(name = "my-uuid", strategy = "org.hibernate.id.UUIDGenerator")
   @Column(name = "uIdUsuario", columnDefinition = "UNIQUEIDENTIFIER")
   private UUID idUsuario;

   @Column(name = "sNombres", length = 100, nullable = false)
   private String nombres;
   
   @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JsonIgnoreProperties(value = { "usuario" }, allowSetters = true)
   @OrderBy("procedimiento ASC")
   private List<UsrProcedimiento> usrProcedimiento;

   @Column(name = "sLogin", length = 25, nullable = false, unique = true)
   private String login;

   @Column(name = "xPassword", columnDefinition = "VARCHAR(MAX)", nullable = false)
   private String password;

   @Column(name = "sDependencia", length= 70, nullable = true)
   private String dependencia;

   @Column(name = "sCargo", length = 70, nullable = false)
   private String cargo;

   @Enumerated(EnumType.STRING)
   @Column(name = "sGrupo", length = 50)
   private RimGrupo grupo;

   @Column(name = "sArea", length = 70, nullable = false)
   private String area;

   @Column(name = "sDni", columnDefinition = "CHAR(8)", nullable = false)
   private String dni;

   @Column(name = "sRegimenLaboral", length = 25, nullable = false)
   private String regimenLaboral;

   @Column(name = "sFoto", columnDefinition = "VARCHAR(MAX) NULL")
   private String foto;

   @Column(name = "sIdJefatura", columnDefinition = "VARCHAR(15) NULL")
   private String idJefatura;

   @Column(name = "bActivo")
   private @Builder.Default boolean activo = true;

   public Usuario() {
      this.usrProcedimiento = new ArrayList<>();
   }

   @PrePersist
   private void prePersist(){
      this.activo = true;
   }

   /**
   *
   */
   private static final long serialVersionUID = 1L;
   
}
