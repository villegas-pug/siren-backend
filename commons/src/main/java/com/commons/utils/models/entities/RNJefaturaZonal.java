package com.commons.utils.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RimRNJefaturaZonal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = { "idDependencia" })
public class RNJefaturaZonal {

   @Id
   @Column(name = "sIdDependencia", nullable = false)
   private String idDependencia;	
   
   @Column(name = "sIdJefatura")
   private String idJefatura;
   
   @Column(name = "sNombreJefaturaZonal")
   private String nombreJefaturaZonal;	
   
   @Column(name = "sNombreDependencia", nullable = false)
   private String nombreDependencia;

   @Column(name = "bActivo", nullable = false)
   private @Builder.Default boolean activo = true;
   
}