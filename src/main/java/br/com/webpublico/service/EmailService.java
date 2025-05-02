package br.com.webpublico.service;

import br.com.webpublico.domain.ConfiguracaoEmailDTO;
import br.com.webpublico.repository.EmailJDBCRepository;
import com.sun.mail.smtp.SMTPTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

@Service
@Transactional
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private EmailJDBCRepository emailJDBCRepository;


    private ConfiguracaoEmailDTO configuracaoEmail;

    public ConfiguracaoEmailDTO getConfiguracaoEmail() {
        if (configuracaoEmail == null) {
            configuracaoEmail = emailJDBCRepository.find();
        }
        return configuracaoEmail;
    }

    public void enviarEmail(String mail, String titulo, String conteudo) {
        if (isEmailValido(mail)) {
            Properties prop = getProperties();
            Session session = Session.getInstance(prop, null);
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress(getConfiguracaoEmail().getEmail(), "Município de Rio Branco"));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail, false));
                msg.setSubject(titulo);
                msg.setDataHandler(new DataHandler(new HTMLDataSource(conteudo)));
                SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
                t.connect(getConfiguracaoEmail().getHost(), getConfiguracaoEmail().getUsername(), getConfiguracaoEmail().getPassword());
                t.sendMessage(msg, msg.getAllRecipients());
                t.close();
            } catch (MessagingException | UnsupportedEncodingException ex) {
                logger.error("Erro ao enviar email ", ex);
            }
        }
    }

    private Properties getProperties() {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", getConfiguracaoEmail().getPort());
        prop.put("mail.smtp.starttls.enable", "true");
        return prop;
    }

    public boolean isEmailValido(String mail) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(mail);
            emailAddr.validate();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}
