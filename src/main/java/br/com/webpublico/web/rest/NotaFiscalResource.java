package br.com.webpublico.web.rest;

import br.com.webpublico.domain.CompetenciaNotaFiscalDTO;
import br.com.webpublico.domain.ConsultaGenericaNfseDTO;
import br.com.webpublico.domain.NotaFiscalSearchDTO;
import br.com.webpublico.service.NotaFiscalService;
import br.com.webpublico.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotaFiscalResource implements Serializable {

    @Autowired
    NotaFiscalService notaFiscalService;

    @RequestMapping(value = "/buscar-notas-fiscais",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NotaFiscalSearchDTO>> buscarNotasFiscais(@RequestBody ConsultaGenericaNfseDTO consultaGenerica) throws Exception {
        Pageable pageable = PaginationUtil.generatePageRequest(consultaGenerica.getOffset(), consultaGenerica.getLimit());
        Page<NotaFiscalSearchDTO> notas = notaFiscalService.consultarNotasFiscais(pageable,
                consultaGenerica.getParametrosQuery(), consultaGenerica.getOrderBy());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(notas, "",
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(notas.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/competencias-notas-fiscais",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CompetenciaNotaFiscalDTO>> buscarCompetenciasNotasFiscaisPorUsuario(@RequestParam(value = "page", required = false) Integer offset,
                                                                                                   @RequestParam(value = "per_page", required = false) Integer limit,
                                                                                                   @RequestParam String login) throws Exception {
        Pageable pageable = PaginationUtil.generatePageRequest(offset, limit);
        Page<CompetenciaNotaFiscalDTO> notas = notaFiscalService.consultarCompetenciasNotasFiscaisPorUsuario(pageable, login);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(notas, "",
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(notas.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/notas-fiscais-por-competencia",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NotaFiscalSearchDTO>> buscarNotasFiscaisPorCompetenciaUsuario(@RequestParam(value = "page", required = false) Integer offset,
                                                                                             @RequestParam(value = "per_page", required = false) Integer limit,
                                                                                             @RequestParam Integer ano,
                                                                                             @RequestParam Integer mes,
                                                                                             @RequestParam String login) throws Exception {
        Pageable pageable = PaginationUtil.generatePageRequest(offset, limit);
        Page<NotaFiscalSearchDTO> notas = notaFiscalService.consultarNotasFiscaisPorCompetenciaUsuario(pageable, ano, mes, login);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(notas, "",
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(notas.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/notas-fiscais-por-sorteio",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NotaFiscalSearchDTO>> buscarNotasFiscaisPorSorteioUsuario(@RequestParam(value = "page", required = false) Integer offset,
                                                                                         @RequestParam(value = "per_page", required = false) Integer limit,
                                                                                         @RequestParam Long idSorteio,
                                                                                         @RequestParam String login) throws Exception {
        Pageable pageable = PaginationUtil.generatePageRequest(offset, limit);
        Page<NotaFiscalSearchDTO> notas = notaFiscalService.consultarNotasFiscaisPorSorteioUsuario(pageable, idSorteio, login);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(notas, "",
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(notas.getContent(), headers, HttpStatus.OK);
    }

}
