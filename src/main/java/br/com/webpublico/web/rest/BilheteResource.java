package br.com.webpublico.web.rest;

import br.com.webpublico.domain.BilheteDTO;
import br.com.webpublico.domain.SorteioDTO;
import br.com.webpublico.service.CupomCampanhaNfseService;
import br.com.webpublico.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BilheteResource {
    private final Logger log = LoggerFactory.getLogger(BilheteResource.class);

    @Autowired
    CupomCampanhaNfseService service;

    @GetMapping("/cupons-sorteio-usuario")
    @Timed
    public ResponseEntity<List<BilheteDTO>> buscarCuponsPorSorteioAndUsuario(@RequestParam(value = "page", required = false) Integer offset,
                                                                             @RequestParam(value = "per_page", required = false) Integer limit,
                                                                             @RequestParam Long idSorteio,
                                                                             @RequestParam String login) throws Exception {
        SorteioDTO sorteio = service.getSorteioNfseService().findById(idSorteio);
        Pageable pageable = PaginationUtil.generatePageRequest(offset, limit);
        Page<BilheteDTO> cupons = service.buscarCuponsPorSorteioAndLogin(pageable, sorteio, login);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(cupons, "",
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity(cupons.getContent(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/cupom/{id}")
    @Timed
    public ResponseEntity<List<BilheteDTO>> findById(@PathVariable Long id) throws Exception {
        BilheteDTO cupom = service.findById(id);
        return new ResponseEntity(cupom, HttpStatus.OK);
    }

    @GetMapping("/externo/cupons-premiados-sorteio")
    @Timed
    public ResponseEntity<List<BilheteDTO>> buscarBilhetesPremiadosPorSorteio(@RequestParam Long idSorteio) throws Exception {
        SorteioDTO sorteio = service.getSorteioNfseService().findById(idSorteio);
        List<BilheteDTO> cupons = service.buscarCuponsPremiadosPorSorteio(sorteio);
        return new ResponseEntity(cupons, HttpStatus.OK);
    }

    @GetMapping("/externo/relatorio-cupons-sorteio/{idSorteio}")
    public byte[] gerarRelatorioCupons(@PathVariable Long idSorteio) {
        try {
            return service.gerarRelatorioBilhetesSorteio(idSorteio);
        } catch (Exception e) {
            log.error("Erro ao imprimir o relatório de cupons do sorteio {}", e);
        }
        return null;
    }
}
