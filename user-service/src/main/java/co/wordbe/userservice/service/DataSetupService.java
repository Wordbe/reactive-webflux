package co.wordbe.userservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

@Service
public class DataSetupService implements CommandLineRunner {

    private final Resource initSql;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public DataSetupService(@Value("classpath:h2/init.sql") Resource initSql,
                            R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.initSql = initSql;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        System.out.println(query);

        r2dbcEntityTemplate.getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();
    }
}
