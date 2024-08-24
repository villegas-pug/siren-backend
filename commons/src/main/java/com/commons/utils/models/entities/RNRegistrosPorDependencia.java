package com.commons.utils.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RimRNRegistrosPorDependencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = { "idRegistrosPorDependencia" })
public class RNRegistrosPorDependencia {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "nIdRegistrosPorDependencia")
   private Long idRegistrosPorDependencia;
   
   @Column(name = "sTabla", nullable = false)
   private String tabla;

   @Column(name = "sIdDependencia", nullable = false)
   private String idDependencia;

   @Column(name = "sIdJefatura")
   private String idJefatura;
   
   @Column(name = "nTotalRegistros", nullable = false)
   private Long totalRegistros;
   
}
