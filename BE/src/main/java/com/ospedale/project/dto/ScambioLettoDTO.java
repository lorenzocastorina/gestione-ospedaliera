package com.ospedale.project.dto;

public class ScambioLettoDTO {
    private Long idLettoVecchio;
    private Long idLettoNuovo;

    public ScambioLettoDTO () {}

    public ScambioLettoDTO(Long idLettoVecchio, Long idLettoNuovo) {
        this.idLettoVecchio = idLettoVecchio;
        this.idLettoNuovo = idLettoNuovo;
    }

    public Long getIdLettoVecchio() {
        return idLettoVecchio;
    }

    public void setIdLettoVecchio(Long idLettoVecchio) {
        this.idLettoVecchio = idLettoVecchio;
    }

    public Long getIdLettoNuovo() {
        return idLettoNuovo;
    }

    public void setIdLettoNuovo(Long idLettoNuovo) {
        this.idLettoNuovo = idLettoNuovo;
    }
}
