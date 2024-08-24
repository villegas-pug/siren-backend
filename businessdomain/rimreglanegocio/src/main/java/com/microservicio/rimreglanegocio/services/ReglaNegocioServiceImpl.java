package com.microservicio.rimreglanegocio.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.commons.utils.errors.DataAccessEmptyWarning;
import com.commons.utils.helpers.DataModelHelper;
import com.commons.utils.models.entities.RNProceso;
import com.commons.utils.models.entities.ReglaNegocio;
import com.microservicio.rimreglanegocio.models.dto.MetricaDatoInvalidoJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaDependenciaJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaOperadorJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaResumenJZ;
import com.microservicio.rimreglanegocio.models.dto.PaginacionHallazgosDto;
import com.microservicio.rimreglanegocio.repositories.ReglaNegocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReglaNegocioServiceImpl implements ReglaNegocioService {

   @Autowired
   private ReglaNegocioRepository repository;

   @Override
   @Transactional(readOnly = true)
   public List<ReglaNegocio> findReglasNegocioByProceso(RNProceso proceso) {
      if (proceso.getIdProceso() == null)
         throw new DataAccessEmptyWarning();
      
      List<ReglaNegocio> reglaNegociosDb = this.repository
            .findByProceso(proceso)
            .stream()
            .filter(ReglaNegocio::isActivo)
            .collect(Collectors.toList());
                                                         
      if (reglaNegociosDb.size() == 0)
         throw new DataAccessEmptyWarning();

      return reglaNegociosDb;
   }

   @Override
   @Transactional
   public Long createOneRegistroEjecucionScript(int idProceso, Long idRNControlCambio) {
      return this.repository.createOneRegistroEjecucionScript(idProceso, idRNControlCambio);
   }

   @Override
   @Transactional(readOnly = true)
   public ReglaNegocio findReglaNegocioById(String idRN) {

      ReglaNegocio reglaNegocio = this.repository.findById(idRN)
                                                 .orElseThrow(() -> new DataAccessEmptyWarning());

      return reglaNegocio;
   }

   @Override
   @Transactional(readOnly = true)
   public List<Map<String, Object>> findAllHallazgosByPaginacion(PaginacionHallazgosDto paginacion) {
      List<Map<String, Object>> hallazgoDb = DataModelHelper
            .convertTuplesToJson(this.repository.findAllHallazgosByPaginacion(
                                                            paginacion.getIdJefatura(),
                                                            paginacion.getCurrentPage(),
                                                            paginacion.getRecordsByPages(),
                                                            paginacion.getDimension()
                                                         )
                                 , false);
                                 
      if (hallazgoDb.size() == 0)
         throw new DataAccessEmptyWarning();
      return hallazgoDb;
   }

   // Métricas
   
   @Override
   @Transactional(readOnly = true)
   public List<MetricaResumenJZ> getMetricasResumenJefeZonal(String idJefatura) {
      List<MetricaResumenJZ> metricas = this.repository.getMetricasResumenJefeZonal(idJefatura);
      if (metricas.size() == 0)
         throw new DataAccessEmptyWarning();
      return metricas;
   }

   @Override
   @Transactional(readOnly = true)
   public List<MetricaOperadorJZ> getMetricasOperadorJefeZonal(Long idProceso, String idJefatura) {
      List<MetricaOperadorJZ> metricas = this.repository.getMetricasOperadorJefeZonal(idProceso, idJefatura);
      if (metricas.size() == 0)
         throw new DataAccessEmptyWarning();
      return metricas;
   }

   @Override
   @Transactional(readOnly = true)
   public List<MetricaDependenciaJZ> getMetricasDependenciaJefeZonal(Long idProceso, String idJefatura) {
      List<MetricaDependenciaJZ> metricas = this.repository.getMetricasDependenciaJefeZonal(idProceso, idJefatura);
      if (metricas.size() == 0)
         throw new DataAccessEmptyWarning();
      return metricas;
   }

   @Override
   @Transactional(readOnly = true)
   public List<MetricaDatoInvalidoJZ> getMetricasDatosInvalidosJefeZonal(Long idProceso, String idJefatura) {
      List<MetricaDatoInvalidoJZ> metricas = this.repository.getMetricasDatosInvalidosJefeZonal(idProceso, idJefatura);
      if (metricas.size() == 0)
         throw new DataAccessEmptyWarning();
      
      return metricas;
   }
   
}
