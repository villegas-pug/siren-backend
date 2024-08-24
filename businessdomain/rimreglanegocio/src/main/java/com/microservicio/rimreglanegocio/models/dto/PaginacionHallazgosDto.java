package com.microservicio.rimreglanegocio.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderMethodName = "of", buildMethodName = "get")
@NoArgsConstructor
@AllArgsConstructor
public class PaginacionHallazgosDto {

   private String idJefatura; 
   private int currentPage;
   private int recordsByPages;
   private @Builder.Default String dimension = "";
   
}
