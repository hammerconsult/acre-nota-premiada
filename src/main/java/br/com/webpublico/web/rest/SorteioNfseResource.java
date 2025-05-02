package br.com.webpublico.web.rest;

import br.com.webpublico.domain.SorteioDTO;
import br.com.webpublico.domain.SorteioUsuarioDTO;
import br.com.webpublico.service.SorteioNfseService;
import br.com.webpublico.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SorteioNfseResource {

    @Autowired
    SorteioNfseService service;
    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/externo/sorteios")
    @Timed
    public ResponseEntity<List<SorteioDTO>> buscarTodosSorteios() throws Exception {
        return new ResponseEntity<>(service.buscarTodosSorteios(), HttpStatus.OK);
    }

    @GetMapping("/externo/sorteios-realizados")
    @Timed
    public ResponseEntity<List<SorteioDTO>> buscarSorteiosRealizados() throws Exception {
        return new ResponseEntity<>(service.buscarSorteiosRealizados(), HttpStatus.OK);
    }

    @GetMapping("/sorteios-por-usuario")
    @Timed
    public ResponseEntity<List<SorteioUsuarioDTO>> buscarSorteiosPorUsuario(@RequestParam(value = "page", required = false) Integer offset,
                                                                            @RequestParam(value = "per_page", required = false) Integer limit,
                                                                            @RequestParam(value = "login") String login) throws URISyntaxException {
        Pageable pageable = PaginationUtil.generatePageRequest(offset, limit);
        Page<SorteioUsuarioDTO> sorteios = service.buscarSorteiosPorUsuario(pageable, login);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(sorteios, "",
                pageable.getPageNumber(), pageable.getPageSize());
        return new ResponseEntity<>(sorteios.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/externo/comunicado-sorteio")
    @Timed
    public void buscarComunicado(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:pdf/comunicado.pdf");
        byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=comunicado.pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.flush();
        outputStream.close();
    }
}
