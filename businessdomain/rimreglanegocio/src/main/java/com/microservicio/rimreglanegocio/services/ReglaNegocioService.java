package com.microservicio.rimreglanegocio.services;

import java.util.List;
import java.util.Map;
import com.commons.utils.models.entities.RNProceso;
import com.commons.utils.models.entities.ReglaNegocio;
import com.microservicio.rimreglanegocio.models.dto.MetricaDatoInvalidoJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaDependenciaJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaOperadorJZ;
import com.microservicio.rimreglanegocio.models.dto.MetricaResumenJZ;
import com.microservicio.rimreglanegocio.models.dto.PaginacionHallazgosDto;

public interface ReglaNegocioService {
   
   List<ReglaNegocio> findReglasNegocioByProceso(RNProceso proceso);
   ReglaNegocio findReglaNegocioById(String idRN);
   Long createOneRegistroEjecucionScript(int idProceso, Long idRNControlCambio);
   List<Map<String, Object>> findAllHallazgosByPaginacion(PaginacionHallazgosDto paginacion);
   

   // Métricas:
   List<MetricaResumenJZ> getMetricasResumenJefeZonal(String idJefatura);
   List<MetricaOperadorJZ> getMetricasOperadorJefeZonal(Long idProceso, String idJefatura);
   List<MetricaDependenciaJZ> getMetricasDependenciaJefeZonal(Long idProceso, String idJefatura);
   List<MetricaDatoInvalidoJZ> getMetricasDatosInvalidosJefeZonal(Long idProceso, String idJefatura);

}
