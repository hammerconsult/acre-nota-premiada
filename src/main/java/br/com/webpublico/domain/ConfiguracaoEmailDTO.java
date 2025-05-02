package br.com.webpublico.domain;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracaoEmailDTO implements Serializable, RowMapper<ConfiguracaoEmailDTO> {
    private Long id;
    private String host;
    private String port;
    private String username;
    private String email;
    private String password;
    private String protocol;
    private String tls;

    public ConfiguracaoEmailDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTls() {
        return tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }

    @Override
    public ConfiguracaoEmailDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        ConfiguracaoEmailDTO dto = new ConfiguracaoEmailDTO();
        dto.setId(resultSet.getLong("ID"));
        if (!StringUtils.isEmpty(resultSet.getString("HOST"))) {
            dto.setHost(resultSet.getString("HOST"));
        }
        if (!StringUtils.isEmpty(resultSet.getString("PORT"))) {
            dto.setPort(resultSet.getString("PORT"));
        }
        if (!StringUtils.isEmpty(resultSet.getString("USERNAME"))) {
            dto.setUsername(resultSet.getString("USERNAME"));
        }
        if (!StringUtils.isEmpty(resultSet.getString("EMAIL"))) {
            dto.setEmail(resultSet.getString("EMAIL"));
        }
        if (!StringUtils.isEmpty(resultSet.getString("PASSWORD"))) {
            dto.setPassword(resultSet.getString("PASSWORD"));
        }
        if (!StringUtils.isEmpty(resultSet.getString("PROTOCOL"))) {
            dto.setProtocol(resultSet.getString("PROTOCOL"));
        }
        if (!StringUtils.isEmpty(resultSet.getString("TLS"))) {
            dto.setTls(resultSet.getString("TLS"));
        }
        return dto;
    }
}
