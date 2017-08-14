package com.lazicoder.office.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * Created by Viet on 16/07/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeFile {
    private String id;

    @Indexed(unique = true)
    private String name;
    private byte[] content;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
}
