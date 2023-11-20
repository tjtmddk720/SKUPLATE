package com.skuplate.server.review.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class ReviewForm {

    @NotNull(message = "starRate must not be null")
    @Min(value = 1, message = "starRate must be greater than or equal to 1")
    @Max(value = 5, message = "starRate must be less than or equal to 5")
    private long starRate;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
