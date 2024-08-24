package com.microservicio.rimreglanegocio.repositories;

import com.commons.utils.models.entities.RNProceso;
import com.commons.utils.models.entities.ReglaNegocio;
import com.microservicio.rimreglanegocio.models.dto.MetricaDatoInvalidoJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaDependenciaJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaOperadorJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaResumenJZ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import javax.persistence.Tuple;


@Repository
public interface ReglaNegocioRepository extends JpaRepository<ReglaNegocio, String> {

   List<ReglaNegocio> findByProceso(RNProceso proceso);

   @Query(value = "{CALL dbo.usp_Rim_RN_InsertaRegistroEjecucionScript(?, ?)}", nativeQuery = true)
   Long createOneRegistroEjecucionScript(int idProceso, Long idRNControlCambio);


   @Query(value = "{CALL dbo.usp_Rim_RN_ListaHallazgosPorPaginacionv2(?, ?, ?, ?)}", nativeQuery = true)
   List<Tuple> findAllHallazgosByPaginacion(String idJefatura, int currentPage, int recordsByPages, String dimension);

   // Métricas:

   @Query(value = "{CALL dbo.usp_Rim_RN_MetricasResumenJefeZonalv2(?)}", nativeQuery = true)
   List<MetricaResumenJZ> getMetricasResumenJefeZonal(String idJefatura);

   @Query(value = "{CALL dbo.usp_Rim_RN_MetricasOperadorJefeZonalv2(?, ?)}", nativeQuery = true)
   List<MetricaOperadorJZ> getMetricasOperadorJefeZonal(Long idProceso, String idJefatura);

   @Query(value = "{Call dbo.usp_Rim_RN_MetricasDependenciaJefeZonalv2(?, ?)}", nativeQuery = true)
   List<MetricaDependenciaJZ> getMetricasDependenciaJefeZonal(Long idProceso, String idJefatura);
   
   @Query(value = "{Call dbo.usp_Rim_RN_MetricasDatosInvalidosJefeZonalv2(?, ?)}", nativeQuery = true)
   List<MetricaDatoInvalidoJZ> getMetricasDatosInvalidosJefeZonal(Long idProceso, String idJefatura);

}
