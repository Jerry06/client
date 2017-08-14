package com.lazicoder.office;

import com.lazicoder.office.dao.OfficeRepository;
import com.lazicoder.office.domain.OfficeFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

import javax.swing.*;
import java.util.List;

@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class,
        WebMvcAutoConfiguration.class})
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private OfficeRepository officeRepository;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<OfficeFile> all = officeRepository.findAll();
        FileChooserSample.main(args);
    }
}
