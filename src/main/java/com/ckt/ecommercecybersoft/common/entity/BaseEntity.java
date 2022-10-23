package com.ckt.ecommercecybersoft.common.entity;

import com.ckt.ecommercecybersoft.common.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @Column(name = Columns.ID)
    protected UUID id;

    @CreatedBy
    @Column(name = Columns.CREATED_BY)
    protected String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATE_TIME_FORMAT)
    @CreatedDate
    @Column(name = Columns.CREATED_AT)
    protected LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = Columns.LAST_MODIFIED_BY)
    protected String lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtils.DATE_TIME_FORMAT)
    @LastModifiedDate
    @Column(name = Columns.LAST_MODIFIED_AT)
    protected LocalDateTime lastModifiedAt;

    @UtilityClass
    private static class Columns {
        static final String ID = "id";
        static final String VERSION = "version";
        static final String CREATED_BY = "created_by";
        static final String CREATED_AT = "created_at";
        static final String LAST_MODIFIED_BY = "last_modified_by";
        static final String LAST_MODIFIED_AT = "last_modified_at";
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((BaseEntity) obj).id);
    }

}
