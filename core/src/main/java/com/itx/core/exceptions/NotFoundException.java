package com.itx.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    ErrorEnum error;
}
