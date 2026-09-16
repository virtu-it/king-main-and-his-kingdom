package com.bnp.kingmainandhiskingdom.exception;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

@Getter
@Setter
public class ValidationError {
    private String field;
    private String reason;

    public ValidationError(String field, @Nullable String defaultMessage) {
        this.field = field;
        this.reason = defaultMessage;
    }
}
