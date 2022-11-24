package com.ckt.ecommercecybersoft.role.dto;

import com.ckt.ecommercecybersoft.role.utils.RoleExceptionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;

    @Length(min = 2, max = 18, message = RoleExceptionUtils.NAME_LENGTH)
    @NotBlank(message = RoleExceptionUtils.NAME_NOT_BLANK)
    private String name;

    @Length(min = 2, max = 8, message = RoleExceptionUtils.CODE_LENGTH)
    @NotBlank(message = RoleExceptionUtils.CODE_NOT_BLANK)
    private String code;

    private String description;

}

