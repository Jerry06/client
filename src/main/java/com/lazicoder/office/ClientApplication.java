package com.lazicoder.office;

import com.lazicoder.office.dao.OfficeRepository;
import com.lazicoder.office.domain.OfficeFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;

@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class
})
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private MessageSource resources;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<OfficeFile> all = officeRepository.findAll();
        Locale defaultLocale = new Locale("vi");
        MainDialog.resources = resources;
        MainDialog.main(args);
    }

}
